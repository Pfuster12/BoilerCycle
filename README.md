# BoilerCycle

[ ![Download](https://api.bintray.com/packages/pfuster12/maven/boilercycle/images/download.svg) ](https://bintray.com/pfuster12/maven/boilercycle/_latestVersion)

Lightweight android library for quick RecyclerView creation written in Kotlin.

Read the guide in a beautiful gitbook website: https://lightmass.gitbook.io/boilercycle/

## Use BoilerCycle in your App

BoilerCycle is uploaded in JCenter. You can add BoilerCycle in your app easily by adding the dependency into your app module's ```build.gradle``` file dependencies section:

```groovy
implementation 'com.lightmass.lib:boilercycle:1.2.0'
```

## How-To

Using RecyclerView can sometimes be tedious and time-consuming, specially when theres a large number of lists in your app. Creating new classes for adapters and ViewHolders becomes annoying and includes a lot of boilerplate code. 

Creating a RecyclerView list using BoilerCycle instead is easy. It keeps the code in your original class improving code legibility and has a lightning quick set up. It only takes a simple chain function!

Declare a RecyclerView in your layout:

```xml
...

    <android.support.v7.widget.RecyclerView
        android:id="@+id/recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

...
```

The library lets you set a custom item layout. For example let's create a simple image and TextView item:
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="?attr/selectableItemBackground"
    xmlns:tools="http://schemas.android.com/tools"
    android:padding="16dp">

    <ImageView
        android:id="@+id/boilercycler_item_image"
        android:layout_width="42dp"
        android:layout_height="42dp"
        android:src="@mipmap/ic_launcher_round"/>

    <TextView
        android:id="@+id/boilercycler_item_title"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="center_vertical"
        android:paddingStart="16dp"
        tools:text="Item1"
        style="@style/Base.TextAppearance.AppCompat.Medium"/>
</LinearLayout>

```
Now grab the recycler view in code and set its layout manager:

```kotlin
 recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
```

We're ready to use BoilerCycle. First get an instance of the library:
```kotlin
BoilerCycle.getBoiler()
...
```
Next set your custom item layout to use
```kotlin
BoilerCycle.getBoiler()
          // set the item layout,
         .setItemLayout(R.layout.item)
...
```
And finally set the adapter. Boilercycle uses the great Kotlin lambda to provide the adapter with custom onBind and onItem click methods. Pass the context, RecyclerView and your list of data for size count and let all the boilerplate be handled:
```kotlin
BoilerCycle.getBoiler()
                // set the item layout,
                .setItemLayout(R.layout.item)
                // set the adapter with context, RecyclerView and list data,
                .setAdapter(this, recycler_view, listData,
                        // onBind lambda method passed with view holder and position data,
                        onBind = { holder, position ->
                            // set holder views with data,
                            holder.itemView.boilercycler_item_image.setImageDrawable(drawable)
                            holder.itemView.boilercycler_item_title.text = listData[position]
                        },
                        // onClick method passed with view and position data,
                        onClick = { view, position ->
                            Toast.makeText(this, "Clicked on: " + "$position", Toast.LENGTH_SHORT).show()
                        })
```

Everytime data is updated, added or deleted just notify from your RecyclerView adapter. For example:

```kotlin
fun dataUpdate() {
    ...
    // notify the adapter
    recycler_view.adapter.notifyDataSetChanged()
    ...
}
```

## Adding Headers and Footers

In v1.1.0 BoilerCycle now supports adding header and footer items that will show at the top and bottom of your RecyclerView respectively. Adding them is similar to adding an item.

Simply chain the functions useHeader(resId: Int) or useFooter(resId: Int) (Or both) before setting the adapter:

```kotlin
BoilerCycle.getBoiler()
        // set the item layout,
        .setItemLayout(R.layout.item)
        // set the header item
        .useHeader(R.layout.header)
        // set the footer item
        .useFooter(R.layout.footer)
        // set the adapter
        ...
```

Then in the adapter's method onBind, simply check for what position you are binding for (Whether it is the first i.e. the header or the last, i.e. the footer). This can be done with a when statement for example:

```kotlin
BoilerCycle.getBoiler()
        // set the item layout,
        .setItemLayout(R.layout.item)
        // set the header item
        .useHeader(R.layout.header)
        // set the footer item
        .useFooter(R.layout.footer)
        // set the adapter with context, RecyclerView and list data,
        .setAdapter(this, recycler_view, listData,
                // onBind lambda method passed with view holder and position data,
                onBind = { holder, position ->
                    when (position) {
                        // for the header,
                         0 -> // do something
                        // for items, pass position - 1 as header takes the first index,
                        in 1..data.size -> holder.itemView.boilercycler_item_title.text = data[position - 1]
                        // for the footer,
                        data.size + 1 -> // do something
                    }
                    // set holder views with data,
                    holder.itemView.boilercycler_item_image.setImageDrawable(drawable)
                    holder.itemView.boilercycler_item_title.text = listData[position]
                },
                // onClick method passed with view and position data,
                onClick = { view, position ->
                    Toast.makeText(this, "Clicked on: " + "$position", Toast.LENGTH_SHORT).show()
                })
```
