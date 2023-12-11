package com.example.ca3

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class Login : AppCompatActivity() {

    lateinit var signbtn: TextView
    val myFile = "saved"
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton: Button = findViewById(R.id.login)
        val enterUser: EditText = findViewById(R.id.user)
        val enterPass: EditText = findViewById(R.id.pass)

        loginButton.setOnClickListener {
            val username = enterUser.text.toString()
            val password = enterPass.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val db = DBHelper(this, null)
                val isUserValid = db.checkUserCredentials(username, password)

                if (isUserValid) {
                    val sharedPreferences = getSharedPreferences("saved", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.putString("id", username)
                    editor.putString("password", password)
                    editor.apply()

                    // Successful login, navigate to another activity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    // Clear the input fields
                    enterUser.text.clear()
                    enterPass.text.clear()
                } else {
                    val vg: ViewGroup? = findViewById(R.id.red_toast)
                    val inflater = layoutInflater
                    val layout: View = inflater.inflate(R.layout.custom_red_toast, vg)
                    val tvv = layout.findViewById<TextView>(R.id.tview)
                    val toast = Toast(applicationContext)
                    toast.setGravity(Gravity.BOTTOM, 0, 50)
                    toast.setView(layout)
                    toast.duration = Toast.LENGTH_LONG

                    tvv.text = "Invalid ID or Password"
                    toast.show()
                }
            } else {
                val vg: ViewGroup? = findViewById(R.id.yellow_toast)
                val inflater = layoutInflater
                val layout: View = inflater.inflate(R.layout.custom_yellow_toast, vg)
                val tvv = layout.findViewById<TextView>(R.id.tview1)
                val toast = Toast(applicationContext)
                toast.setGravity(Gravity.BOTTOM, 0, 50)
                toast.setView(layout)
                toast.duration = Toast.LENGTH_LONG

                tvv.text = "Please fill all the fields"
                toast.show()
            }
        }

        signbtn = findViewById(R.id.signup)
        signbtn.setOnClickListener{
            intent = Intent(this,Register::class.java)
            startActivity(intent)
        }
    }
}