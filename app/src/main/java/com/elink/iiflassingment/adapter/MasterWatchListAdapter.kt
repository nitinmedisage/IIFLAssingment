package com.elink.assingmentmedisage.adapter

/**
 * Created by Nitin Sabale
 * @since 27-07-2022. 18:33
 */
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.elink.iiflassingment.R
import com.elink.iiflassingment.interfaces.OnItemClickListener
import com.elink.iiflassingment.model.WatchListEntity


class MasterWatchListAdapter(private val context: Context, private var list: MutableList<WatchListEntity>, val OnItemClickListener: OnItemClickListener) : RecyclerView.Adapter<MasterWatchListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.activity_main_adapter,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = list.get(position)
        holder.title?.text = user.MwatchName
        holder.root?.setOnClickListener {
            OnItemClickListener.onItemClickListener(user)
            }
        }

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view){
        val title = view.findViewById<TextView>(R.id.txt_title)
        val root = view.findViewById<CardView>(R.id.root)
    }

    fun setAdapterData(userList: MutableList<WatchListEntity>) {
        list = userList
        notifyDataSetChanged()
    }
}