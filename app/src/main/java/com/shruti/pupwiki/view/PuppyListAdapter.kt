package com.shruti.pupwiki.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.shruti.pupwiki.R
import com.shruti.pupwiki.databinding.ItemPupBinding
import com.shruti.pupwiki.model.PuppyBreedItem
import com.shruti.pupwiki.view.PuppyListAdapter.PuppyListHolderView
import kotlinx.android.synthetic.main.item_pup.view.*

class PuppyListAdapter(val pupList: ArrayList<PuppyBreedItem>) :
    RecyclerView.Adapter<PuppyListHolderView>(),PupClickListener {
    //class PuppyListHolderView(view : View) : RecyclerView.ViewHolder(view)
    class PuppyListHolderView(var view: ItemPupBinding) : RecyclerView.ViewHolder(view.root)

    fun updateDogsList(newList: List<PuppyBreedItem>) {
        pupList.clear()
        pupList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PuppyListHolderView {
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pup,parent,false)
        val view = DataBindingUtil.inflate<ItemPupBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_pup,
            parent,
            false
        )
        return PuppyListHolderView(view)
    }

    override fun onBindViewHolder(holder: PuppyListHolderView, position: Int) {

        // binds the values to the views from layout
        holder.view.pup = pupList[position]
        holder.view.listener = this
        /*holder.itemView.pupName.text = pupList[position].name
        holder.itemView.pupAge.text = pupList[position].lifeSpan

       //Kotlin ext function to Imageview
        holder.itemView.pupImage.loadImage(
            pupList[position].url,
            getProgressDrawable(holder.itemView.pupImage.context)
        )
        holder.itemView.cardView.setOnClickListener {
            val action = PuppyListFragmentDirections.actionToDetailFragment()
            pupList[position].id?.let {
                action.puppyUuid = it
            }
            Navigation.findNavController(it).navigate(action)
        }
*/
    }

    override fun getItemCount() = pupList.size

    override fun onPupCLicked(view: View) {
        val id = view.pupId.text.toString().toInt()
        val action = PuppyListFragmentDirections.actionToDetailFragment()
        action.puppyUuid = id
        Navigation.findNavController(view).navigate(action)
    }

}