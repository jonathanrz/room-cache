package br.com.jonathanzanella.roomcache

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Data(@PrimaryKey(autoGenerate = true) var id: Long = 0, var name: String = "")