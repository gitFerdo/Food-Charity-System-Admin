package com.example.food_charity_system_admin.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.food_charity_system_admin.R
import com.example.food_charity_system_admin.databinding.ItemCategaryLayoutBinding
import com.example.food_charity_system_admin.model.CategoryModel
import android.content.Context
import com.bumptech.glide.Glide

class CategoryAdapter(var context: Context, private val list : ArrayList<CategoryModel>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){
    inner class CategoryViewHolder(view : View) : ViewHolder(view) {
        var binding = ItemCategaryLayoutBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_categary_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.textView2.text = list[position].cat
        Glide.with(context).load(list[position].img).into((holder.binding.itemCategiryView))
    }
}