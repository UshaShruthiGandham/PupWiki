package com.shruti.pupwiki.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.shruti.pupwiki.model.PupDatabase
import com.shruti.pupwiki.model.PuppyBreedItem
import kotlinx.coroutines.launch

class PuppyDetailViewModel(application: Application) : BaseViewModel(application) {

    val puppies = MutableLiveData<PuppyBreedItem>()

    val dao = PupDatabase(getApplication()).pupDao()

    fun getPupData(pupId: Int){

        launch {
            puppies.value = dao.getPup(pupId)
        }

    }
}