package com.example.food_charity_system_admin.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food_charity_system_admin.databinding.ImgItemBinding

class AddCharityImageAdapter(val list: ArrayList<Uri>)
    : RecyclerView.Adapter<AddCharityImageAdapter.AddCharityImageViewHolder>() {
    inner class AddCharityImageViewHolder(val binding : ImgItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddCharityImageViewHolder {
        val binding = ImgItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddCharityImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AddCharityImageViewHolder, position: Int) {
        holder.binding.imageView2.setImageURI(list[position])
    }
}