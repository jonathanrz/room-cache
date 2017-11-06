package br.com.jonathanzanella.roomcache

import android.arch.persistence.room.Room
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val database = Room.databaseBuilder(this, MyDatabase::class.java, DB_NAME).build()
    private val dataSource = DataRepository(database.dataDao())
    private val dataAdapter = DataAdapter(dataSource)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = dataAdapter

        save_btn.setOnClickListener {
            object : AsyncTask<Void, Void, Void?>() {
                override fun doInBackground(vararg p0: Void?): Void? {
                    dataSource.save(Data(name = new_data_name.text.toString()))
                    return null
                }

                override fun onPostExecute(result: Void?) {
                    super.onPostExecute(result)
                    new_data_name.setText("")
                }
            }.execute()
        }
    }

    override fun onStart() {
        super.onStart()
        dataAdapter.onStart()
    }

    override fun onStop() {
        dataAdapter.onStop()
        super.onStop()
    }
}
