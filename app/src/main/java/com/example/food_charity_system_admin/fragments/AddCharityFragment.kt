package com.example.food_charity_system_admin.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.Visibility
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.food_charity_system_admin.R
import com.example.food_charity_system_admin.adapter.AddCharityImageAdapter
import com.example.food_charity_system_admin.databinding.FragmentAddCharityBinding
import com.example.food_charity_system_admin.model.AddCharityModel
import com.example.food_charity_system_admin.model.CategoryModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class AddCharityFragment : Fragment() {

    private lateinit var binding: FragmentAddCharityBinding
    private lateinit var list: ArrayList<Uri>
    private lateinit var listImages: ArrayList<String>
    private lateinit var adapter: AddCharityImageAdapter
    private var coverImage: Uri ? = null
    private lateinit var dialog : Dialog
    private var coverImgUrl: String? = ""
    private lateinit var categoryList: ArrayList<String>

    private var launchGalleryAvtivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            coverImage = it.data!!.data
            binding.btnSelectImg.setImageURI(coverImage)
            //binding.btnSelectImg.visibility = android.opengl.Visibility
        }
    }

    private var launchCharityAvtivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val imageUrl = it.data!!.data
            list.add(imageUrl!!)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddCharityBinding.inflate(layoutInflater)

        list = ArrayList()
        listImages = ArrayList()

        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.progress_layout)

        binding.btnSelectImg.setOnClickListener {
            val intent = Intent("android.intent.action.GET_CONTENT")
            intent.type = "image/*"
            launchGalleryAvtivity.launch(intent)
        }

        binding.btnSelectCharity.setOnClickListener {
            val intent = Intent("android.intent.action.GET_CONTENT")
            intent.type = "image/*"
            launchGalleryAvtivity.launch(intent)
        }

        setCharityCategory()

        adapter = AddCharityImageAdapter(list)
        binding.charityImgView.adapter = adapter

        binding.btnAddCharity.setOnClickListener{
            validateData()
        }
        return binding.root
    }

    private fun validateData() {
        if (binding.edCharityName.text.toString().isEmpty()) {
            binding.edCharityName.requestFocus()
            binding.edCharityName.error = "Empty"

        } else if (binding.edCharityPrice.text.toString().isEmpty()) {
            binding.edCharityPrice.requestFocus()
            binding.edCharityPrice.error = "Empty"

        } else if (coverImage == null){
            Toast.makeText(requireContext(), "Please select cover image.", Toast.LENGTH_SHORT).show()

        }else if (listImages.size < 1){
            Toast.makeText(requireContext(), "Please select charity images.", Toast.LENGTH_SHORT).show()

        } else {
            uploadImage()
        }
    }

    private fun setCharityCategory() {
        categoryList = ArrayList()
        Firebase.firestore.collection("categories").get().addOnSuccessListener {
            categoryList.clear()
            for (doc in it.documents) {
                val data = doc.toObject(CategoryModel::class.java)
                categoryList.add(data!!.cat!!)
            }
            categoryList.add(0, "Select Category")

            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item_layout, categoryList)
            binding.CharityCategoryDropdoen.adapter = arrayAdapter

        }
    }

    private fun uploadImage() {
        dialog.show()

        val filName = UUID.randomUUID().toString()+".jpg"

        val refStorage = FirebaseStorage.getInstance().reference.child("charities/$filName")
        refStorage.putFile(coverImage!!)
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener{image ->
                    coverImgUrl = image.toString()

                    uploadCharityImage()
                }
            }

            .addOnFailureListener{
                dialog.dismiss()
                Toast.makeText(requireContext(), "Something went wrong with storage.", Toast.LENGTH_SHORT).show()
            }
    }

    private var i = 0
    private fun uploadCharityImage() {
        dialog.show()

        val filName = UUID.randomUUID().toString()+".jpg"

        val refStorage = FirebaseStorage.getInstance().reference.child("category/$filName")
        refStorage.putFile(list[i])
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener{image ->
                    listImages.add(image!!.toString())

                    if (list.size == listImages.size) {
                        storeData()
                    } else {
                        i *= 1
                        uploadCharityImage()
                    }

                }
            }

            .addOnFailureListener{
                dialog.dismiss()
                Toast.makeText(requireContext(), "Something went wrong with storage.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun storeData() {
        val db = Firebase.firestore.collection("charities")
        val key = db.id

        val data = AddCharityModel(
            binding.edCharityName.text.toString(),
            binding.edCharityDes.text.toString(),
            coverImgUrl.toString(),
            categoryList[binding.CharityCategoryDropdoen.selectedItemPosition],
            key,
            binding.edCharityPrice.text.toString(),
            listImages
        )

        db.document(key).set(data).addOnSuccessListener {
            dialog.dismiss()
            Toast.makeText(requireContext(), "Charity Added", Toast.LENGTH_SHORT).show()
            binding.btnAddCharity.text = null

        }.addOnFailureListener {
            dialog.dismiss()
            Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }
}

private fun View.setImageURI(coverImage: Uri?) {
    TODO("Not yet implemented")
}
