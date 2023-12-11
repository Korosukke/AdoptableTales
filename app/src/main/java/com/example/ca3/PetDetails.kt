package com.example.ca3

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class PetDetails : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_details)

        val petImageView = findViewById<ImageView>(R.id.petImageView)
        val petNameTextView = findViewById<TextView>(R.id.petNameTextView)
        val petTypeTextView = findViewById<TextView>(R.id.petTypeTextView)
        val petDescTextView = findViewById<TextView>(R.id.petTypeTextView1)
        val knowme = findViewById<TextView>(R.id.know)

        // Get pet details from the intent
        val petName = intent.getStringExtra("pet_name")
        val petType = intent.getStringExtra("pet_type")
        val petImageResId = intent.getIntExtra("pet_image",  R.drawable.dog1)
        val petDesc = intent.getStringExtra("pet_desc")

        // Set the pet details in the UI
        petNameTextView.text = petName
        petTypeTextView.text = petType
        petImageView.setImageResource(petImageResId)
        petDescTextView.text = petDesc

        knowme.setOnClickListener {
            val petType = petTypeTextView.text.toString()

            val searchQuery = "https://www.google.com/search?q=${petType}"
            val uri = Uri.parse(searchQuery)

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }


    }
}
