package com.shruti.pupwiki.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.shruti.pupwiki.model.PupDatabase
import com.shruti.pupwiki.model.PuppyBreedItem
import com.shruti.pupwiki.service.PuppiesApiService
import com.shruti.pupwiki.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch


class PuppyListViewModel(application: Application) : BaseViewModel(application) {

    private var prefHelper = SharedPreferencesHelper(getApplication())

    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L  // 5 min

    private val puppiesService = PuppiesApiService() // service

    private val disposable = CompositeDisposable()  // to observe the observable that api gives not worry about get rid of it

    val puppies = MutableLiveData<List<PuppyBreedItem>>()

    val puppiesLoadError = MutableLiveData<Boolean>() // error while retrieving

    val loading = MutableLiveData<Boolean>() // the data is still loading

    val dao = PupDatabase(getApplication()).pupDao()

    fun refresh(){
        val updateTime = prefHelper.getUpdateTime()
        if (updateTime!= null && updateTime!=0L && (System.nanoTime() - updateTime) < refreshTime){

            fetchFromDB()
        } else {
            fetchFromRemote()
        }
    }

    fun refreshByPassCache() {
        fetchFromRemote()
    }

    private fun fetchFromDB() {
        loading.value = true
        launch {
            val pupList = dao.getAllPups()
            updateUIOnSuccess(pupList)
            Toast.makeText(getApplication(), "Pups retrieved from DB", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchFromRemote(){

        loading.value = true

        // don't run the network call on API UI thread to avoid the crash or ANR
        disposable.add(
            puppiesService.getPuppies()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<PuppyBreedItem>>() {
                    override fun onSuccess(puppyList: List<PuppyBreedItem>) {
                        storeDogsInDB(puppyList)
                        Toast.makeText(getApplication(), "Pups retrieved from Remote", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        puppiesLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun updateUIOnSuccess(puppyList:List<PuppyBreedItem>) {
        puppies.value = puppyList
        puppiesLoadError.value = false
        loading.value = false
    }

    private fun storeDogsInDB(list: List<PuppyBreedItem>){
        launch {

            dao.deleteAllPups()
            val result = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size){
                list[i].id = result[i].toInt()
                i++
            }
            updateUIOnSuccess(list)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}