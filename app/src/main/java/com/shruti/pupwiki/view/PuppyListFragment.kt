package com.shruti.pupwiki.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.shruti.pupwiki.R
import com.shruti.pupwiki.viewmodel.PuppyListViewModel
import kotlinx.android.synthetic.main.puppy_list_fragment.*

class PuppyListFragment : Fragment() {

    private lateinit var viewModel: PuppyListViewModel

    private val pupAdapter = PuppyListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.puppy_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init ViewModel
        viewModel = ViewModelProviders.of(requireActivity()).get(PuppyListViewModel::class.java)
        viewModel.refresh()

        pupListView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = pupAdapter

            refreshLayout.setOnRefreshListener {
                visibility = View.GONE
                listError.visibility = View.GONE
                progressBar.visibility = View.GONE
                viewModel.refreshByPassCache()
                refreshLayout.isRefreshing = false
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {

        viewModel.puppies.observe(viewLifecycleOwner, Observer { puppies ->
            puppies?.let {
                listError.visibility = View.GONE
                pupListView.visibility = View.VISIBLE
                pupAdapter.updateDogsList(puppies)
            }
        })
        viewModel.puppiesLoadError.observe(viewLifecycleOwner, Observer {
            it?.let {
                listError.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            it?.let {
                progressBar.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    listError.visibility = View.GONE
                    pupListView.visibility = View.GONE
                }
            }
        })
    }


}