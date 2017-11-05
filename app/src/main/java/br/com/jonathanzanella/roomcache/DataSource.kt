package br.com.jonathanzanella.roomcache

import android.accounts.Account

interface DataSource {
    fun all(): List<Data>
    fun save(data: Data): Boolean
}

class DataRepository(val dao: DataDao) : DataSource {
    override fun all(): List<Data> = dao.all()
    override fun save(data: Data): Boolean = dao.save(data) != 0L
}