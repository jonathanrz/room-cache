package br.com.jonathanzanella.roomcache

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class DatabaseObservable<T>(private val generateData: () -> List<T>, private val mergeData: (cache: List<T>, data: T) -> List<T>) {
    private var allData: List<T> = emptyList()
    private var bs: BehaviorSubject<List<T>>? = null

    fun cache(): Observable<List<T>> {
        if(bs == null) {
            bs = BehaviorSubject.create()
            publishUpdate(Observable.fromCallable { generateData() })
        }
        return bs!!.replay(1).autoConnect()
    }

    fun newData(data: T) {
        publishUpdate(Observable.fromCallable { mergeData(allData, data) })
    }

    private fun publishUpdate(observable: Observable<List<T>>) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .doOnNext { allData = it }
                .subscribe { bs?.onNext(it) }
    }
}