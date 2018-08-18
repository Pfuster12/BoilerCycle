package com.android.lightmass.boilercycle

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

// constants
private const val BOILERCYCLE_ITEM = 100
private const val BOILERCYCLE_HEADER = 101
private const val BOILERCYCLE_FOOTER = 102

/**
 * BoilerCycle is a one-class lightweight Android Library that handles creating the
 * tedious RecyclerView + ViewHolder pattern set up easily in a few function calls.
 * Class handling the init of variables and main library functionality.
 */
class BoilerCycle private constructor() {

    /**
     * Global vars
     */
    // holds the layout id to inflate in the view holder
    private var mResId = -1
    // holds the header layout id to inflate in the view holder
    private var mHeaderResId = -1
    // holds the footer layout id to inflate in the view holder
    private var mFooterResId = -1
    // bool check if user is using a header
    private var isUsingHeader = false
    // bool check if user is using a header
    private var isUsingFooter = false
    // check if user is using simple item
    private var isUsingSimpleItem = false
    // adapter variable
    private var adapter: RecyclerView.Adapter<ViewHolder>? = null

    companion object {
        private var instance: BoilerCycle? = null
        fun getBoiler() =
                instance ?: BoilerCycle()
    }

    /**
     * Functions
     */

    /**
     * Grabs the view holder layout
     */
    fun setItemLayout(resId: Int): BoilerCycle {
        mResId = resId
        // return this for chaining
        return this
    }

    /**
     * Grabs the header layout
     */
    fun useHeader(resId: Int): BoilerCycle {
        mHeaderResId = resId
        // set the bool to true
        isUsingHeader = true
        // return this for chaining
        return this
    }

    /**
     * Grabs the header layout
     */
    fun useFooter(resId: Int): BoilerCycle {
        mFooterResId = resId
        // set the bool to true
        isUsingFooter = true
        // return this for chaining
        return this
    }

    /**
     * Sets the adapter by creating a view holder using the res Id provided.
     * The on bind method is passed through a lambda in the function constructor.
     * Finally the view holder on click is passed through as a lambda, to go into the
     * view holder constructor.
     */
    fun setAdapter(context: Context, recyclerView: RecyclerView, data: List<Any>,
                   onBind: (holder: ViewHolder, position: Int) -> Unit, onClick: (view: View, position: Int) -> Unit): RecyclerView? {
        if (!isUsingSimpleItem) {
            // init the adapter,
            adapter = object : RecyclerView.Adapter<ViewHolder>() {
                // creates viewholder
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                    return when (viewType) {
                        BOILERCYCLE_HEADER -> {
                            ViewHolder(LayoutInflater.from(context).inflate(mHeaderResId, parent, false), onClick)
                        }
                        BOILERCYCLE_ITEM -> {
                            ViewHolder(LayoutInflater.from(context).inflate(mResId, parent, false), onClick)
                        }
                        BOILERCYCLE_FOOTER -> {
                            ViewHolder(LayoutInflater.from(context).inflate(mFooterResId, parent, false), onClick)
                        }
                        else -> ViewHolder(LayoutInflater.from(context).inflate(mResId, parent, false), onClick)
                    }
                }

                // gets the item count from the data list
                override fun getItemCount() = data.size

                // on binds through lambda function
                override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                    onBind(holder, position)
                }

                // get whether item is a header, footer or item
                override fun getItemId(position: Int): Long {
                    when {
                        // only header
                        isUsingHeader && !isUsingFooter -> {
                            return when (position) {
                                0 -> BOILERCYCLE_HEADER.toLong()
                                else -> BOILERCYCLE_ITEM.toLong()
                            }
                        }
                        // only footer
                        isUsingFooter && !isUsingHeader -> {
                            return when (position) {
                                data.size -> BOILERCYCLE_FOOTER.toLong()
                                else -> BOILERCYCLE_ITEM.toLong()
                            }
                        }
                        // using both
                        isUsingHeader && isUsingFooter -> {
                            return when (position) {
                                0 -> BOILERCYCLE_HEADER.toLong()
                                data.size + 1 -> BOILERCYCLE_FOOTER.toLong()
                                else -> BOILERCYCLE_ITEM.toLong()
                            }
                        }
                        // else return item type
                        else -> return BOILERCYCLE_ITEM.toLong()
                    }
                }
            }
            // and sets the adapter,
            recyclerView.adapter = adapter
        }
        // returns the recycler view attached to the adapter
        return recyclerView
    }

    /**
     * Sets the simple list item adapter by creating a view holder using the res Id provided.
     * The on bind method is passed through a lambda in the function constructor.
     * Finally the view holder on click is passed through as a lambda, to go into the
     * view holder constructor.
     */
    fun setSimpleAdapter(context: Context, recyclerView: RecyclerView, data: List<Any>,
                         onBind: (textView: TextView, position: Int) -> Unit, onClick: (view: View, position: Int) -> Unit): RecyclerView? {
        // set the simple layout,
        mResId = R.layout.simple_boilercycle_item
        // init the adapter,
        adapter = object : RecyclerView.Adapter<ViewHolder>() {
            // creates viewholder
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                    ViewHolder(LayoutInflater.from(context).inflate(mResId, parent, false), onClick)

            // gets the item count from the data list
            override fun getItemCount() = data.size

            // on binds through lambda function
            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                // pass the textview in the simple layout
                onBind(holder.itemView.findViewById(R.id.simple_boilercycler_item_title), position)
            }
        }
        // and sets the adapter,
        recyclerView.adapter = adapter

        // returns the recycler view attached to the adapter
        return recyclerView
    }

    /**
     * View holder class, sets the item view on click to the lambda passed
     */
    class ViewHolder(itemView: View, onClick: (view: View, position: Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
        // in init
        init {
            // set the item view click listener to the lambda
            itemView.setOnClickListener { onClick(itemView, adapterPosition) }
        }
    }
}