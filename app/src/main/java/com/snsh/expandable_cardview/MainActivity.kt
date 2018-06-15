package com.snsh.expandable_cardview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import comm.saingaung.senghein.simplerecyclerview.uty.itf.MyExpandableRecyclerClickListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cardView = recycler_cardview
        val itemList = mutableListOf<MyExpandableAdapter.MyItemExpandable>()
        itemList.add(MyExpandableAdapter.MyItemExpandable(MyExpandableAdapter.PARENT, "P-Lang"))
        itemList.add(MyExpandableAdapter.MyItemExpandable(MyExpandableAdapter.CHILD, "Kotlin"))
        itemList.add(MyExpandableAdapter.MyItemExpandable(MyExpandableAdapter.CHILD, "Go"))
        itemList.add(MyExpandableAdapter.MyItemExpandable(MyExpandableAdapter.CHILD, "C++"))
        itemList.add(MyExpandableAdapter.MyItemExpandable(MyExpandableAdapter.CHILD, "Java"))
        itemList.add(MyExpandableAdapter.MyItemExpandable(MyExpandableAdapter.CHILD, "Clojure"))

        val item = MyExpandableAdapter.MyItemExpandable(MyExpandableAdapter.PARENT, "Android")
        item.children = listOf(
                MyExpandableAdapter.MyItemExpandable(MyExpandableAdapter.CHILD, "Jelly Bean"),
                MyExpandableAdapter.MyItemExpandable(MyExpandableAdapter.CHILD, "KitKat"),
                MyExpandableAdapter.MyItemExpandable(MyExpandableAdapter.CHILD, "Lollipop"),
                MyExpandableAdapter.MyItemExpandable(MyExpandableAdapter.CHILD, "Marshmallow"),
                MyExpandableAdapter.MyItemExpandable(MyExpandableAdapter.CHILD, "Nougat"),
                MyExpandableAdapter.MyItemExpandable(MyExpandableAdapter.CHILD, "Oreo"))
        itemList.add(item)

        cardView.layoutManager = LinearLayoutManager(this)
        cardView.adapter = MyExpandableAdapter(itemList, object : MyExpandableRecyclerClickListener {
            override fun onParentClicked(id: Int, text: String) {
                Toast.makeText(this@MainActivity, "id $id, name $text, parent",
                        Toast.LENGTH_LONG).show()
            }

            override fun onChildClicked(id: Int, text: String) {
                Toast.makeText(this@MainActivity, "id $id, name $text, child",
                        Toast.LENGTH_LONG).show()
            }

        })
    }
}
