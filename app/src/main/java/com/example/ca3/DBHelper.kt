package com.example.ca3

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COl + " TEXT," +
                USER_COL + " TEXT," +
                PASS_COL + " TEXT," +
                PHN_COL + " TEXT," +
                MAIl_COL + " TEXT" + ")")
        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
    }
    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }// This method is for adding data in our database
    fun addName(name : String, user : String, password : String, phone : String, email : String){
        // below we are creating
        // a content values variable
        val values = ContentValues()
        // we are inserting our values // in the form of key-value pair
        values.put(NAME_COl, name)
        values.put(USER_COL, user)
        values.put(PASS_COL, password)
        values.put(PHN_COL, phone)
        values.put(MAIl_COL, email)
        // here we are creating a // writable variable of // our database as we want to
        // insert value in our database
        val db = this.writableDatabase
        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)
        // at last we are
        // closing our database
        db.close()
    }
    // below method is to get
    // all data from our database
    fun getName(): Cursor? {
        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase
        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " +
                TABLE_NAME ,null)
        //return db.rawQuery("SELECT * FROM " +
        //TABLE_NAME+"WHERE AGE>=50",null)
    }
    fun getUserInfo(username: String): Cursor? {
        val db = this.readableDatabase
        val columns = arrayOf(NAME_COl, USER_COL, PASS_COL, MAIl_COL, PHN_COL)
        val selection = "$USER_COL = ?"
        val selectionArgs = arrayOf(username)

        return db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null)
    }

    fun checkUserCredentials(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE $USER_COL = ? AND $PASS_COL = ?", arrayOf(username, password))
        val isValid = cursor.count > 0
        cursor.close()
        db.close()
        return isValid
    }



    companion object{
        // here we have defined variables for our database
        // below is variable for database name
        private val DATABASE_NAME = "CYCLER"
        // below is the variable for database version
        private val DATABASE_VERSION = 1
        // below is the variable for table name
        val TABLE_NAME = "my_table1"
        // below is the variable for id column
        val ID_COL = "id"
        // below is the variable for name column
        val NAME_COl = "name"
        // below is the variable for age column
        val USER_COL = "username"
        val PASS_COL = "password"
        val MAIl_COL = "email"
        val PHN_COL = "phone"
    }
}