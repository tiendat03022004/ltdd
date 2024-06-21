package com.example.btck1.Adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.btck1.R
import com.example.btck1.databinding.ViewholderColorBinding






class ColorAdapter(val items:MutableList<String>):
    RecyclerView.Adapter<ColorAdapter.Viewholder>() {
    private var selectedPosition=-1
    private var lastSelectedPosition=-1
    private lateinit var context: Context
    inner class Viewholder(val binding:ViewholderColorBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        context=parent.context
        val binding=ViewholderColorBinding.inflate(LayoutInflater.from(context),parent,false)
        return Viewholder(binding)
    }
    override fun getItemCount(): Int=items.size
    override fun onBindViewHolder(holder: Viewholder, position:Int) {
        val item=items[position]


        Glide.with(holder.itemView.context)
            .load(items[position])
            .into(holder.binding.pic)

        holder.binding.root.setOnClickListener {
            lastSelectedPosition=selectedPosition
            selectedPosition=position
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
        }
        if(selectedPosition==position){

            holder.binding.ColorLayout.setBackgroundResource(R.drawable.grey_bg_selected)
        }else{
            holder.binding.ColorLayout.setBackgroundResource(R.drawable.grey_bg)

        }
    }


}
//
//
//class ColorAdapter(val items:MutableList<String>):
//    RecyclerView.Adapter<BrandAdapter.Viewholder>() {
//    private var selectedPosition=-1
//    private var lastSelectedPosition=-1
//    private lateinit var context: Context
//    inner class Viewholder(val binding:ViewholderColorBinding):
//        RecyclerView.ViewHolder(binding.root) {
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
//        context=parent.context
//        val binding=ViewholderColorBinding.inflate(LayoutInflater.from(context),parent,false)
//        return Viewholder(binding)
//    }
//
//    override fun getItemCount(): Int=items.size
//    override fun onBindViewHolder(holder: Viewholder, position: Int) {
//        val item=items[position]
//        holder.binding.title.text=item.title
//
//        Glide.with(holder.itemView.context)
//            .load(item.picUrl)
//            .into(holder.binding.pic)
//        holder.binding.root.setOnClickListener {
//            lastSelectedPosition=selectedPosition
//            selectedPosition=position
//            notifyItemChanged(lastSelectedPosition)
//            notifyItemChanged(selectedPosition)
//        }
//        holder.binding.title.setTextColor(context.resources.getColor(R.color.white))
//        if(selectedPosition==position){
//            holder.binding.pic.setBackgroundColor(0)
//            holder.binding.mainLayout.setBackgroundResource(R.drawable.purple_button_bg)
//            ImageViewCompat.setImageTintList(holder.binding.pic, ColorStateList.valueOf(context.getColor(R.color.white)))
//            holder.binding.title.visibility= View.VISIBLE
//        }else{
//            holder.binding.pic.setBackgroundResource(R.drawable.grey_bg)
//            holder.binding.mainLayout.setBackgroundResource(0)
//            ImageViewCompat.setImageTintList(holder.binding.pic, ColorStateList.valueOf(context.getColor(R.color.black)))
//            holder.binding.title.visibility= View.GONE
//
//        }
//    }


