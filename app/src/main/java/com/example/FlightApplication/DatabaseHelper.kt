package com.example.FlightApplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "travel.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "profile"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_IMAGE = "image"
        private const val COLUMN_FAVORITE_PLACE = "favorite_place"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE IF NOT EXISTS $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, 
                $COLUMN_USERNAME TEXT, 
                $COLUMN_IMAGE TEXT, 
                $COLUMN_FAVORITE_PLACE TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
        Log.d("DB", "create table")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    private fun isTableExists(db: SQLiteDatabase?, tableName: String): Boolean {
        val cursor = db?.rawQuery(
            "SELECT name FROM sqlite_master WHERE type='table' AND name=?",
            arrayOf(tableName)
        )
        val exists = cursor?.count!! > 0
        cursor?.close()
        return exists
    }

    fun isProfileExist(): Boolean {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_NAME", null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        return count > 0
    }

    fun insertProfile(name: String, profileImage: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply{
            put(COLUMN_USERNAME, name)
            put(COLUMN_IMAGE, profileImage)
            put(COLUMN_FAVORITE_PLACE, "Seoul")
        }
        db.insert(TABLE_NAME, null, contentValues)
    }

    fun updateProfile(name: String, profileImage: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply{
            put(COLUMN_USERNAME, name)
            put(COLUMN_IMAGE, profileImage)
        }
        db.update(TABLE_NAME, contentValues, "$COLUMN_ID=?", arrayOf("1"))
    }

    fun insertOrUpdateProfile(name: String, profileImage: String) {
        if (isProfileExist()) {
            updateProfile(name, profileImage)
        }
        else {
            insertProfile(name, profileImage)
        }
    }

    fun loadProfile():List<String> {
        val datalist = mutableListOf<String>()
        val db = this.readableDatabase
        try {
            if (isProfileExist()) {
                val cursor:Cursor = db.rawQuery("SELECT username, image, favorite_place from $TABLE_NAME where $COLUMN_ID=1", null)
                if (cursor.moveToFirst()) {
                    // 각 열의 데이터를 가져와서 리스트에 추가
                    datalist.add(cursor.getString(cursor.getColumnIndexOrThrow("username")))
                    datalist.add(cursor.getString(cursor.getColumnIndexOrThrow("image")))
                    datalist.add(cursor.getString(cursor.getColumnIndexOrThrow("favorite_place")))
                }
                cursor.close()

            }
        }
        catch (e : Exception) {
            e.printStackTrace()
        }
        finally {
            datalist.add("")
            datalist.add("")
            datalist.add("")
        }
        return datalist
    }
}