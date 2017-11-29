package br.com.jonathanzanella.roomcache

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.row_data.view.*

class DataAdapter (val dataSource: DataSource) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {
    private var dataArray: List<Data> = ArrayList()
    private var disposable: Disposable? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun setData(data: Data) {
            itemView.data_id.text = data.id.toString()
            itemView.data_name.text = data.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_data, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(dataArray[position])
    }

    override fun getItemCount(): Int = dataArray.size

    fun onStart() {
        disposable = dataSource.all()
                .doOnNext { dataArray = it }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { notifyDataSetChanged() }
    }

    fun onStop() {
        disposable?.dispose()
    }
}