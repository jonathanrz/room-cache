package br.com.jonathanzanella.roomcache

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

const val DB_NAME = "data.db"
const val DB_VERSION = 1

@Database(entities = arrayOf(Data::class), version = DB_VERSION)
abstract class MyDatabase : RoomDatabase() {
    abstract fun dataDao(): DataDao
}
