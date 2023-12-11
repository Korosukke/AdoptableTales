package com.example.ca3

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ProfilePage : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page) // Set the profile layout

        val sharedPreferences = getSharedPreferences("saved", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("id", "")

        if (username != null) {
            // Retrieve user information from the database
            val dbHelper = DBHelper(this, null)
            val cursor = dbHelper.getUserInfo(username)

            if (cursor != null && cursor.moveToFirst()) {
                // Assuming you have TextViews in your profile layout to display the user info
                val nameTextView = findViewById<TextView>(R.id.textView4)
                val usernameTextView = findViewById<TextView>(R.id.textView5)
                    val emailTextView = findViewById<TextView>(R.id.textView6)
                val phoneTextView = findViewById<TextView>(R.id.textView7)

                // Extract user information from the cursor
                val name = cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl))
                val email = cursor.getString(cursor.getColumnIndex(DBHelper.MAIl_COL))
                val phone = cursor.getString(cursor.getColumnIndex(DBHelper.PHN_COL))

                // Display the user information in the TextViews
                nameTextView.text = "Name: $name"
                usernameTextView.text = "Username: $username"
                emailTextView.text = "Email: $email"
                phoneTextView.text = "Phone: $phone"
            } else {
                // Handle the case where user information is not found
                // You can display an error message or take appropriate action.
            }

            // Close the cursor to release resources
            cursor?.close()
        } else {
            // Handle the case where the username is null (not found in SharedPreferences)
            // You can display an error message or take appropriate action.
        }
    }
}
