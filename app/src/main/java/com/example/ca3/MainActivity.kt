package com.example.ca3

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {


    lateinit var  adap:RecyclerAdapterDemo
    val list = ArrayList<PetDataModel>()

    lateinit var btn1: FloatingActionButton
    lateinit var btn2: FloatingActionButton
    lateinit var btn3: FloatingActionButton
    private lateinit var btn4: FloatingActionButton
    var fabvisibility = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv=findViewById<RecyclerView>(R.id.recyclerView)

        val layoutManager= LinearLayoutManager(applicationContext)
        rv.layoutManager = (layoutManager)
        rv.itemAnimator= DefaultItemAnimator()
        adap = RecyclerAdapterDemo(list) { pet ->
            val intent = Intent(this, PetDetails::class.java)
            intent.putExtra("pet_name", pet.getPetName())
            intent.putExtra("pet_type", pet.getPetType())
            intent.putExtra("pet_image", pet.getPetImage() ?: R.drawable.dog1)
            intent.putExtra("pet_desc", pet.getPetDesc())
            startActivity(intent)
        }

        rv.adapter = adap
        call()

        btn1 = findViewById(R.id.idFABAdd)
        btn2 = findViewById(R.id.idFABProfile)
        btn3 = findViewById(R.id.idFABLogout)
        btn4 = findViewById(R.id.idFABMap)

        fabvisibility = false
        btn1.setOnClickListener {
            if (!fabvisibility) {
                btn2.show()
                btn3.show()
                btn4.show()

                btn2.visibility = View.VISIBLE
                btn3.visibility = View.VISIBLE
                btn4.visibility = View.VISIBLE

                btn1.setImageDrawable(resources.getDrawable(R.drawable.close))
                fabvisibility = true
            }
            else {
                btn2.hide()
                btn3.hide()
                btn4.hide()

                btn2.visibility = View.GONE
                btn3.visibility = View.GONE
                btn4.visibility = View.GONE

                btn1.setImageDrawable(resources.getDrawable(R.drawable.open))
                fabvisibility = false
            }
        }
        btn2.setOnClickListener{
            val intent = Intent(this, ProfilePage::class.java)
            startActivity(intent)
        }

        btn3.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmation")
            builder.setMessage("Are you sure you want to logout?")

            builder.setPositiveButton("Yes") { _, _ ->

                val sharedPref = getSharedPreferences("saved", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.clear()
                editor.apply()

                startActivity(Intent(this, Login::class.java))
                finish()
                Toast.makeText(this@MainActivity,"Logged Out Successfully", Toast.LENGTH_LONG).show()
            }

            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss() // Dismiss the dialog, do not close the app
            }

            val dialog = builder.create()
            dialog.show()
        }

        btn4.setOnClickListener{
            val latitude = 31.309676864032266
            val longitude = 75.54177050215007
            val uri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude")
            val mapIntent = Intent(Intent.ACTION_VIEW, uri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }

    fun call(){
        list.add(PetDataModel(R.drawable.dog1,"Lucy", "Nova Scotia Duck Tolling Retriever","Tollers are intelligent, outgoing, and affectionate, but they are not for everyone. More than many breeds, Tollers like to stay busy and they are a mental and physical commitment. "))
        list.add(PetDataModel(R.drawable.cat1,"Jerry", "Indie Cat"," Indie cats are not a distinct breed and do not have specific breed properties. They are a diverse group of domestic cats that have adapted to their local environments and are often found as strays or in animal shelters. Each \"Indie\" cat is unique, and their properties and behavior can vary widely based on individual factors and experiences."))
        list.add(PetDataModel(R.drawable.dog2,"Scooby", "German Shephard","The German Shepherd's temperament is intelligent, loyal and energetic. Reserved but friendly. Generally enjoy the company of their family and can struggle with separation anxiety. German Shepherds are not naturally aggressive but their protective instinct means they can be hesitant when first meeting strangers."))
        list.add(PetDataModel(R.drawable.hamster2,"Stuart", "Hamster","Hamsters are small to large muroid rodents with compact bodies, small, furry ears, short legs, wide feet, and short stubby tails. Body lengths range from 50 mm to 340 mm, and tail lengths range from 7 to 106 mm. Females of some species are larger than males. Hamsters have long, thick fur"))
        list.add(PetDataModel(R.drawable.cat2,"Tom", "Norwegian Forest Tabby Cat"," Friendly, intelligent, and generally good with people. The Norwegian Forest cat has a lot of energy. They are very interactive cats who enjoy being part of their family environment and love to play games. Fanciers note that these cats produce a variety of high-pitched \"chirping\" vocalisations."))
        list.add(PetDataModel(R.drawable.dog3,"Jimmy", "Staffordshire Bull Terrier","Small Yet Strong: Despite their compact size, Staffordshire Bull Terriers are muscular and powerful dogs, known for their strength. Playful and Energetic: They have a high energy level and love to play, making them great companions for active individuals or families.\n"))
        list.add(PetDataModel(R.drawable.duck,"Donald", "Duck","Just a typical duck"))
        list.add(PetDataModel(R.drawable.hamster1,"Garfield", "Hamster","Hamsters are small to large muroid rodents with compact bodies, small, furry ears, short legs, wide feet, and short stubby tails. Body lengths range from 50 mm to 340 mm, and tail lengths range from 7 to 106 mm. Females of some species are larger than males. Hamsters have long, thick fur"))
    }


}