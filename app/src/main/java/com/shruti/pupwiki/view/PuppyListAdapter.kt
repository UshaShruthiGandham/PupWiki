package com.shruti.pupwiki.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.shruti.pupwiki.R
import com.shruti.pupwiki.model.PuppyBreedItem
import com.shruti.pupwiki.util.getProgressDrawable
import com.shruti.pupwiki.util.loadImage
import com.shruti.pupwiki.view.PuppyListAdapter.PuppyListHolderView
import kotlinx.android.synthetic.main.pup_item.view.*

class PuppyListAdapter (val pupList : ArrayList<PuppyBreedItem>) : RecyclerView.Adapter<PuppyListHolderView>() {
    class PuppyListHolderView(view : View) : RecyclerView.ViewHolder(view)

    fun updateDogsList(newList: List<PuppyBreedItem>){
        pupList.clear()
        pupList.addAll(newList)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PuppyListHolderView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pup_item,parent,false)
        return  PuppyListHolderView(view)
    }

    override fun onBindViewHolder(holder: PuppyListHolderView, position: Int) {
        holder.itemView.pupName.text = pupList[position].name
        holder.itemView.pupAge.text = pupList[position].lifeSpan
        holder.itemView.pupImage.loadImage(pupList[position].url, getProgressDrawable( holder.itemView.pupImage.context))
        holder.itemView.cardView.setOnClickListener {
            val action = PuppyListFragmentDirections.actionToDetailFragment()
            pupList[position].id?.let {
                action.puppyUuid = it
            }
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount() = pupList.size
}