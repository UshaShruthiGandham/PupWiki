package com.shruti.pupwiki.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.shruti.pupwiki.R
import com.shruti.pupwiki.databinding.PuppyDetailFragmentBinding
import com.shruti.pupwiki.model.PupPalette
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
                it.url?.let {
                    setUpBG(it)
                }
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

    private fun setUpBG(url : String){
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate {palette ->
                            val intColor = palette?.mutedSwatch?.rgb ?: 0
                            val myPalette = PupPalette(intColor)
                            dataBinding.palette = myPalette
                        }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

            })
    }

}