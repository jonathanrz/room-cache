package br.com.jonathanzanella.roomcache

import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private val database = Room.databaseBuilder(this, MyDatabase::class.java, DB_NAME).build()
    private val dataSource = DataRepository(database.dataDao())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
