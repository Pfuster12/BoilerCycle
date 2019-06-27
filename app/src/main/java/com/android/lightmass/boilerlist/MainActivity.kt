package com.android.lightmass.boilerlist

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.lightmass.boilercycle.BoilerCycle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var data: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // populate the fake data,
        data = mutableListOf("This", "is", "a", "good", "example")

        // set the layout manager,
        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        /**
         * Simple adapter example
         */
/*        BoilerCycle.getBoiler()
                .setSimpleAdapter(this, recycler_view, data as MutableList,
                        onBind = {textView, position ->
                            textView.text = (data as MutableList)[position]
                        },
                        onClick = { view, position ->
                            Toast.makeText(this, "Clicked on: " + "$position", Toast.LENGTH_SHORT).show()
                        })*/


        /**
         * No header or footer example
         */
       /* BoilerCycle.getBoiler()
                .setItemLayout(R.layout.item)
                .setAdapter(this, recycler_view, data,
                        onBind = { holder, position ->
                            //holder.itemView.boilercycler_item_image.setImageDrawable(drawable)
                            holder.itemView.boilercycler_item_title.text = data[position]
                        },
                        onClick = { view, position ->
                            Toast.makeText(this, "Clicked on: " + "$position", Toast.LENGTH_SHORT).show()
                        })*/

        /**
         * RecyclerView with only a header.
         */
        BoilerCycle.getBoiler()
                .setItemLayout(R.layout.item)
                .useHeader(R.layout.header)
                .setAdapter(this, recycler_view, data,
                        onBind = {holder, position ->
                            when (position) {
                                in 1..data.size -> holder.itemView.findViewById<TextView>(R.id.boilercycler_item_title).text = data[position - 1]
                            }
                        },
                        onClick = {view, position ->
                            Toast.makeText(this, "Clicked on: " + "$position", Toast.LENGTH_SHORT).show()
                        })

        /**
         * RecyclerView with only a footer.
         */
        /*BoilerCycle.getBoiler()
                .setItemLayout(R.layout.item)
                .useFooter(R.layout.footer)
                .setAdapter(this, recycler_view, data,
                        onBind = {holder, position ->
                            when (position) {
                                in 1..data.size -> holder.itemView.findViewById<TextView>(R.id.boilercycler_item_title).text = data[position - 1]
                            }
                        },
                        onClick = {view, position ->
                            Toast.makeText(this, "Clicked on: " + "$position", Toast.LENGTH_SHORT).show()
                        })*/


        // set the button
        setAddMoreButton()
    }

    /**
     * Set the button to add more items to the list.
     */
    private fun setAddMoreButton() {
        button.setOnClickListener {
            data.addAll(listOf("This", "is", "even", "better"))
            recycler_view.adapter?.notifyDataSetChanged()
        }
    }
}
