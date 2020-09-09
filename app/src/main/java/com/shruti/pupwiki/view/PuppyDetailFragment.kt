package com.shruti.pupwiki.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.shruti.pupwiki.R
import com.shruti.pupwiki.databinding.PuppyDetailFragmentBinding
import com.shruti.pupwiki.viewmodel.PuppyDetailViewModel

class PuppyDetailFragment : Fragment() {

    private lateinit var viewModel: PuppyDetailViewModel

    private var pupUuid = 0

    private lateinit var dataBinding :PuppyDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.puppy_detail_fragment,
            container,
            false
        )
        return dataBinding.root
        // return inflater.inflate(R.layout.puppy_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PuppyDetailViewModel::class.java)
        arguments?.let {
            pupUuid = PuppyDetailFragmentArgs.fromBundle(it).puppyUuid
        }
        viewModel.getPupData(pupUuid)

        observeViewModel(view)
    }

    private fun observeViewModel(view: View) {
        viewModel.puppies.observe(viewLifecycleOwner, Observer { puppies ->

            puppies?.let {
                dataBinding.pup = puppies
                /*pupDesc.text = puppies.name
                if (!puppies.breedGroup.isNullOrEmpty()) {
                    pupPurpose.visibility = View.VISIBLE
                    pupPurpose.text = puppies.breedGroup
                }
                if (!puppies.temperament.isNullOrEmpty()) {
                    pupTemperment.visibility = View.VISIBLE
                    pupTemperment.text = puppies.temperament
                }
                if (!puppies.lifeSpan.isNullOrEmpty()) {
                    pupLifeSpan.visibility = View.VISIBLE
                    pupLifeSpan.text = puppies.lifeSpan
                }
                puppyImage.loadImage(puppies.url, getProgressDrawable(puppyImage.context))*/
            }
        })
    }

}