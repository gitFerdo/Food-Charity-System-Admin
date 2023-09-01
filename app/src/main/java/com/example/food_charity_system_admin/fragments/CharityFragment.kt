package com.example.food_charity_system_admin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.food_charity_system_admin.R
import com.example.food_charity_system_admin.databinding.FragmentCharityBinding

class CharityFragment : Fragment() {

    private lateinit var binding: FragmentCharityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCharityBinding.inflate(layoutInflater)

        binding.floatingActionButton.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_charityFragment_to_addCharityFragment)
        }
        return binding.root
    }
}