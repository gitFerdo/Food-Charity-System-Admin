package com.example.food_charity_system_admin.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.food_charity_system_admin.R
import com.example.food_charity_system_admin.activity.DonationActivity
import com.example.food_charity_system_admin.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.userButton.setOnClickListener {
            startActivity(Intent(requireContext(), DonationActivity::class.java))
        //findNavController().navigate(R.id.action_homeFragment_to_userFragment)
        }

        binding.paymentButton.setOnClickListener {
            startActivity(Intent(requireContext(), DonationActivity::class.java))
        //findNavController().navigate(R.id.action_homeFragment_to_paymentFragment)
        }

        binding.donationButton.setOnClickListener {
            startActivity(Intent(requireContext(), DonationActivity::class.java))
            //findNavController().navigate(R.id.action_homeFragment_to_donationFragment)
        }

        binding.charityButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_charityFragment)
        }

        binding.categoryButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_categoryFragment)
        }

        binding.sliderButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_sliderFragment)
        }

        return binding.root
    }

}