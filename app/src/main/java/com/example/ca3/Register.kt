package com.example.ca3

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

class Register : AppCompatActivity() {

    val myFile = "saved"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val signup: Button = findViewById(R.id.sign)
        val enterName: EditText = findViewById(R.id.name)
        val enterUser: EditText = findViewById(R.id.user)
        val enterPass: EditText = findViewById(R.id.pass)
        val enterCNF: EditText = findViewById(R.id.cnfpass)
        val enterPhone: EditText = findViewById(R.id.phone)
        val enterMail: EditText = findViewById(R.id.email)
        signup.setOnClickListener{
            val name = enterName.text.toString()
            val user = enterUser.text.toString()
            val pass = enterPass.text.toString()
            val cnf = enterCNF.text.toString()
            val phone = enterPhone.text.toString()
            val mail = enterMail.text.toString()

            if(name.isNotEmpty() && user.isNotEmpty() && pass.isNotEmpty() && cnf.isNotEmpty() && phone.isNotEmpty() && mail.isNotEmpty()) {
                if(pass.length>=6) {
                    if (pass == cnf) {
                        val db = DBHelper(this, null)
                        // creating variables for values
                        // in name and age edit texts
                        // calling method to add
                        // name to our database
                        db.addName(name, user, pass, phone, mail)

                        val sharedPreferences = getSharedPreferences("saved", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putBoolean("isLoggedIn", true)
                        editor.putString("id", name)
                        editor.putString("password", pass)
                        editor.apply()

                        val vg: ViewGroup? = findViewById(R.id.blue_toast)
                        val inflater = layoutInflater
                        val layout: View = inflater.inflate(R.layout.custom_blue_toast, vg)
                        val tvv = layout.findViewById<TextView>(R.id.tview2)
                        val toast = Toast(applicationContext)
                        toast.setGravity(Gravity.BOTTOM, 0, 50)
                        toast.setView(layout)
                        toast.duration = Toast.LENGTH_LONG
                        tvv.text = "Successfully Registered"
                        toast.show()

                        val i = Intent(this, MainActivity::class.java)
                        startActivity(i)
                        // at last, clearing edit texts
                        enterName.text.clear()
                        enterUser.text.clear()
                        enterPass.text.clear()
                        enterCNF.text.clear()
                        enterPhone.text.clear()
                        enterMail.text.clear()
                    } else {

                        val vg: ViewGroup? = findViewById(R.id.red_toast)
                        val inflater = layoutInflater
                        val layout: View = inflater.inflate(R.layout.custom_red_toast, vg)
                        val tvv = layout.findViewById<TextView>(R.id.tview)
                        val toast = Toast(applicationContext)
                        toast.setGravity(Gravity.BOTTOM, 0, 50)
                        toast.setView(layout)
                        toast.duration = Toast.LENGTH_LONG

                        tvv.text = "Password doesn't match"
                        toast.show()
                    }
                }
                else{
                    val vg: ViewGroup? = findViewById(R.id.red_toast)
                    val inflater = layoutInflater
                    val layout: View = inflater.inflate(R.layout.custom_red_toast, vg)
                    val tvv = layout.findViewById<TextView>(R.id.tview)
                    val toast = Toast(applicationContext)
                    toast.setGravity(Gravity.BOTTOM, 0, 50)
                    toast.setView(layout)
                    toast.duration = Toast.LENGTH_LONG

                    tvv.text = "Minimum password length 6"
                    toast.show()
                }
            }

            else{val vg: ViewGroup? = findViewById(R.id.yellow_toast)
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



    }
}