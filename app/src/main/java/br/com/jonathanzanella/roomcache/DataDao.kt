package br.com.jonathanzanella.roomcache

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface DataDao {
    @Query("SELECT * FROM Data")
    fun all(): List<Data>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(data: Data): Long
}