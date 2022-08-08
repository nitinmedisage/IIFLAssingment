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
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.elink.iiflassingment.R
import com.elink.iiflassingment.interfaces.WatchListOneClickListener
import com.elink.iiflassingment.model.ChildWatchListEntity
import com.elink.iiflassingment.utilies.Utility
/**
 *  Watch list activity for to check data list coming from json file and set adapter
 *
 */

class WathListOneAdapter(
        private val context: Context,
        private var list: MutableList<ChildWatchListEntity>,
        val OnItemClickListener: WatchListOneClickListener,
        var gridSwitch: String
) : RecyclerView.Adapter<WathListOneAdapter.MyViewHolder>() {

    var updateLastTradePrice = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view :View
        view= inflater.inflate(R.layout.row_grid_watch_list_one, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = list.get(position)

        holder.textViewShortName?.text = user.shortName
        holder.textViewExchType?.text = user.exch+" / "+user.exchType

        if (updateLastTradePrice > 0){
            holder.textViewLastTradePrice?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_arrow_upward_24, 0, 0, 0);
            holder.textViewLastTradePrice?.setTextColor(ContextCompat.getColor(context,R.color.dark_green))
        } else {
            holder.textViewLastTradePrice?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_arrow_downward_24, 0, 0, 0);
            holder.textViewLastTradePrice?.setTextColor(ContextCompat.getColor(context,R.color.dark_red))
        }
        holder.textViewLastTradePrice?.text = user.lastTradePrice.toString()
        holder.textViewPClose?.text =user.pClose.toString()
        holder.textViewVolume.text = Utility.getFormatedNumber(user.volume.toString())
        holder.root?.setOnClickListener {
            OnItemClickListener.onWatchListItemClickListener(user)
            }
        }

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view){
        val textViewShortName = view.findViewById<TextView>(R.id.textViewShortName)
        val textViewExchType = view.findViewById<TextView>(R.id.textViewEachType)
        val textViewLastTradePrice = view.findViewById<TextView>(R.id.textViewLastTradePrice)
        val textViewVolume = view.findViewById<TextView>(R.id.textViewVolume)
        val textViewPClose = view.findViewById<TextView>(R.id.textViewPClose)
        val root = view.findViewById<CardView>(R.id.root)
    }

    /**
     *  this method use for set and update last tread price  with random number increment and decrement
     *  @param updateRandomNumber
     */
    fun updateLastTradePrice(updateRandomNumber: Int) {
        updateLastTradePrice = updateRandomNumber
        notifyDataSetChanged()
    }
    /**
     *  this method use for set recycle view orientation with list and grid
     *  @param userList
     *  @param switch
     */
    fun setAdapterData(userList: MutableList<ChildWatchListEntity>, switch: String) {
        list = userList
        gridSwitch = switch
        notifyDataSetChanged()
    }
}