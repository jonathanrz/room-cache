package br.com.jonathanzanella.roomcache

import io.reactivex.Observable

interface DataSource {
    fun all(): Observable<List<Data>>
    fun save(data: Data): Boolean
}

class DataRepository(private val dao: DataDao) : DataSource {
    private val generateData: () -> List<Data> = {
        dao.all()
    }
    private val mergeData: (List<Data>, Data) -> List<Data> = { cache, newData ->
        val index = cache.indexOfFirst { it.id == newData.id }
        when(index) {
            -1 -> cache + newData
            0 ->  (arrayOf(newData) as List<Data>) + cache.subList(1, cache.size)
            else -> cache.subList(0, index - 1) + newData + cache.subList(index + 1, cache.size)
        }
    }

    private val dataObservable = DatabaseObservable(generateData, mergeData)

    override fun all(): Observable<List<Data>> = dataObservable.cache()
    override fun save(data: Data): Boolean {
        val id = dao.save(data)
        return if(id != 0L) {
            data.id = id
            dataObservable.newData(data)
            true
        } else {
            false
        }
    }
}