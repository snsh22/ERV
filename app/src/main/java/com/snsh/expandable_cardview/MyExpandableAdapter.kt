package com.snsh.expandable_cardview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import comm.saingaung.senghein.simplerecyclerview.uty.itf.MyExpandableRecyclerClickListener
import kotlinx.android.synthetic.main.holder_expandable_parent.view.*

/**
 * Created by L on 2017-09-30.
 */

class MyExpandableAdapter(private var myItemExpandable: MutableList<MyItemExpandable>, private var myExpandableRecyclerClickListener: MyExpandableRecyclerClickListener?)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val PARENT = 0
        const val CHILD = 1
        const val OPEN = 0.0F
        const val CLOSE = 180.0F
    }

    data class MyItemExpandable(val type: Int = 0,
                                var text: String = "Default",
                                var id: String = "0",
                                var children: List<MyItemExpandable>? = null)

    inner class ItemHolder(v: View?, myExpandableRecyclerClickListener: MyExpandableRecyclerClickListener?, viewType: Int) : RecyclerView.ViewHolder(v) {
        var itemText = v?.itemText
        val ivItemToggle = v?.ivItemToggle

        init {
            itemView.setOnClickListener {
                when (viewType) {
                    PARENT -> myExpandableRecyclerClickListener?.onParentClicked(myItemExpandable[adapterPosition].id.toInt(), myItemExpandable[adapterPosition].text)
                    CHILD -> myExpandableRecyclerClickListener?.onChildClicked(myItemExpandable[adapterPosition].id.toInt(), myItemExpandable[adapterPosition].text)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        var view: View? = null
        when (viewType) {
            PARENT -> view = inflater.inflate(R.layout.holder_expandable_parent, parent, false)
            CHILD -> view = inflater.inflate(R.layout.holder_expandable_child, parent, false)
        }
        return ItemHolder(view, myExpandableRecyclerClickListener, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemHolder = holder as? ItemHolder
        val item = myItemExpandable[position]

        itemHolder?.let {
            it.ivItemToggle?.let {
                it.setImageResource(R.drawable.my_expandable_toggle)
                it.rotation = if (item.children == null) OPEN else CLOSE

                it.setOnClickListener { view ->
                    val start = myItemExpandable.indexOf(item) + 1
                    if (item.children == null) {
                        var count = 0
                        var nextHeader = myItemExpandable.indexOf(myItemExpandable.find {
                            (count++ >= start) && (it.type == item.type)
                        })

                        if (nextHeader == -1) nextHeader = myItemExpandable.size
                        item.children = myItemExpandable.slice(start until nextHeader)

                        val end = item.children!!.size
                        if (end > 0) myItemExpandable.removeAll(item.children!!)

                        view.animate().rotation(CLOSE).start()
                        notifyItemRangeRemoved(start, end)
                    } else {
                        item.children?.let {
                            myItemExpandable.addAll(start, it)
                            view.animate().rotation(OPEN).start()
                            notifyItemRangeInserted(start, it.size)
                            item.children = null
                        }
                    }
                }
            }

            it.itemText!!.text = item.text
        }
    }

    override fun getItemCount(): Int = myItemExpandable.size

    override fun getItemViewType(position: Int): Int = myItemExpandable[position].type
}