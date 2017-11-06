package br.com.jonathanzanella.roomcache

import android.os.AsyncTask
import android.util.Log
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class DatabaseObservable<T>(private val generateData: () -> List<T>, private val mergeData: (cache: List<T>, data: T) -> List<T>) {
    private var bs: BehaviorSubject<List<T>>? = null

    fun cache(): Observable<List<T>> {
        if(bs == null) {
            bs = BehaviorSubject.create()
            AsyncTask.execute { bs?.onNext(generateData()) }
        }
        return bs!!.replay(1).autoConnect()
    }

    fun newData(data: T) {
        AsyncTask.execute { bs?.onNext(mergeData(bs!!.blockingFirst(), data)) }
    }
}