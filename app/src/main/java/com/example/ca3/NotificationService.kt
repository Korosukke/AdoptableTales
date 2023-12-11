package com.example.ca3

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.IBinder
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationService : Service() {

    private val handler = Handler()
    private val intervalMillis: Long = 5 * 1000 // 5 hours
    private lateinit var notificationTask: Runnable
    private val petList: List<NotificationModel> = getPetList()

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My Notification Channel"
            val descriptionText = "Channel description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("channel_id", name, importance).apply {
                description = descriptionText
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        notificationTask = Runnable {
            sendNotification()
            handler.postDelayed(notificationTask, intervalMillis)
        }
        handler.post(notificationTask)
    }


    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(notificationTask)
    }

    private fun sendNotification() {
        val pet = getSpecificPet()

        if (pet != null) {
            val builder = NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.person)
                .setContentTitle("Check out ${pet.name}!")
                .setContentText("Adopt this lovely ${pet.type} today.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            // Create an explicit intent to open your app's main activity
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("pet_id", pet.id)

            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            builder.setContentIntent(pendingIntent)

            // Call setAutoCancel on the Notification object, not on the builder
            val notification = builder.build()
            notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL

            val notificationManager = NotificationManagerCompat.from(this)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Check if the app has the required permission to post notifications
                if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    // The app has the permission to post notifications
                    notificationManager.notify(1, notification)
                } else {
                    // Request the notification permission from the user
                    // You should implement the logic to request permissions here
                    // For example, you can show a dialog or request permissions using ActivityCompat.requestPermissions
                }
            } else {
                // For versions below Android 6.0, no permission check is needed
                notificationManager.notify(1, notification)
            }

        }
    }


    private fun getSpecificPet(): NotificationModel? {
        if (petList.isNotEmpty()) {
            val randomIndex = (0 until petList.size).random()
            return petList[randomIndex]
        }
        return null
    }

    private fun getPetList(): List<NotificationModel> {
        // Implement the logic to fetch a list of pet data from your data source (e.g., SQLite database).
        // Replace this with your database retrieval logic.
        return listOf(
                    NotificationModel(1,R.drawable.dog1,"Lucy", "Nova Scotia Duck Tolling Retriever","Tollers are intelligent, outgoing, and affectionate, but they are not for everyone. More than many breeds, Tollers like to stay busy and they are a mental and physical commitment. "),
                    NotificationModel(2,R.drawable.cat1,"Jerry", "Indie"," Indie cats are not a distinct breed and do not have specific breed properties. They are a diverse group of domestic cats that have adapted to their local environments and are often found as strays or in animal shelters. Each \"Indie\" cat is unique, and their properties and behavior can vary widely based on individual factors and experiences."),
                    NotificationModel(3,R.drawable.dog2,"Tommy", "German Shephard","The German Shepherd's temperament is intelligent, loyal and energetic. Reserved but friendly. Generally enjoy the company of their family and can struggle with separation anxiety. German Shepherds are not naturally aggressive but their protective instinct means they can be hesitant when first meeting strangers."),
                    NotificationModel(4,R.drawable.hamster2,"Stuart", "Hamster","Hamsters are small to large muroid rodents with compact bodies, small, furry ears, short legs, wide feet, and short stubby tails. Body lengths range from 50 mm to 340 mm, and tail lengths range from 7 to 106 mm. Females of some species are larger than males. Hamsters have long, thick fur"),
                    NotificationModel(5,R.drawable.cat2,"Michael", "Norwegian Forest Tabby"," Friendly, intelligent, and generally good with people. The Norwegian Forest cat has a lot of energy. They are very interactive cats who enjoy being part of their family environment and love to play games. Fanciers note that these cats produce a variety of high-pitched \"chirping\" vocalisations."),
                    NotificationModel(6,R.drawable.dog3,"Jimmy", "Staffordshire Bull Terrier","Small Yet Strong: Despite their compact size, Staffordshire Bull Terriers are muscular and powerful dogs, known for their strength. Playful and Energetic: They have a high energy level and love to play, making them great companions for active individuals or families.\n"),
                    NotificationModel(7,R.drawable.duck,"Donald", "Duck","Just a typical duck"),
                    NotificationModel(8,R.drawable.hamster1,"Garfield", "Hamster","Hamsters are small to large muroid rodents with compact bodies, small, furry ears, short legs, wide feet, and short stubby tails. Body lengths range from 50 mm to 340 mm, and tail lengths range from 7 to 106 mm. Females of some species are larger than males. Hamsters have long, thick fur")
            // Add more pet data entries as needed
        )
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
