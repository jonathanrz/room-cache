package br.com.jonathanzanella.roomcache

import android.os.AsyncTask
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class DatabaseObservable<T>(private val generateData: () -> List<T>, private val mergeData: (cache: List<T>, data: T) -> List<T>) {
    private var allData: List<T> = emptyList()
    private var bs: BehaviorSubject<List<T>>? = null

    fun cache(): Observable<List<T>> {
        if(bs == null) {
            bs = BehaviorSubject.create()
            AsyncTask.execute {
                allData = generateData()
                bs?.onNext(allData)
            }
        }
        return bs!!.replay(1).autoConnect()
    }

    fun newData(data: T) {
        AsyncTask.execute {
            allData = mergeData(allData, data)
            bs?.onNext(mergeData(allData, data))
        }
    }
}