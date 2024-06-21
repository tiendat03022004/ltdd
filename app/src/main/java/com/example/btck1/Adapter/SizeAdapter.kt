package com.example.btck1.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btck1.R
import com.example.btck1.databinding.ViewholderSizeBinding


    class SizeAdapter(val items:MutableList<String>):
    RecyclerView.Adapter<SizeAdapter.Viewholder>() {
    private var selectedPosition=-1
    private var lastSelectedPosition=-1
    private lateinit var context: Context
    inner class Viewholder(val binding: ViewholderSizeBinding):
        RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        context=parent.context
        val binding=ViewholderSizeBinding.inflate(LayoutInflater.from(context),parent,false)
        return Viewholder(binding)
    }

    override fun getItemCount(): Int=items.size
    override fun onBindViewHolder(holder: Viewholder, position: Int) {
      val item=items[position]


        holder.binding.sizeTxT.text=items[position]

        holder.binding.root.setOnClickListener {
            lastSelectedPosition=selectedPosition
            selectedPosition=position
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
        }
        if(selectedPosition==position){
            holder.binding.ColorLayout.setBackgroundResource(R.drawable.grey_bg_selected)
            holder.binding.sizeTxT.setTextColor(context.resources.getColor(R.color.purple_500))
        }else{
            holder.binding.ColorLayout.setBackgroundResource(R.drawable.grey_bg)
            holder.binding.sizeTxT.setTextColor(context.resources.getColor(R.color.black))
        }
    }


}


//class SizeAdapter (val items:MutableList<String>):
//    RecyclerView.Adapter<ColorAdapter.Viewholder>() {
//        private var selectedPosition=-1
//        private var lastSelectedPosition=-1
//        private lateinit var context: Context
//        inner class Viewholder(val binding: ):
//            RecyclerView.ViewHolder(binding.root)
//
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
//            context=parent.context
//            val binding= ViewholderColorBinding.inflate(LayoutInflater.from(context),parent,false)
//            return Viewholder(binding)
//        }
//        override fun getItemCount(): Int=items.size
//        override fun onBindViewHolder(holder: Viewholder, position:Int) {
//            val item=items[position]
//
//
//            Glide.with(holder.itemView.context)
//                .load(item[position])
//                .into(holder.binding.pic)
//
//            holder.binding.root.setOnClickListener {
//                lastSelectedPosition=selectedPosition
//                selectedPosition=position
//                notifyItemChanged(lastSelectedPosition)
//                notifyItemChanged(selectedPosition)
//            }
//            if(selectedPosition==position){
//
//                holder.binding.colorLayout.setBackgroundResource(R.drawable.grey_bg_selected)
//            }else{
//                holder.binding.colorLayout.setBackgroundResource(R.drawable.grey_bg)
//
//            }
//        }



