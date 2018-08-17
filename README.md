# BoilerCycle
Lightweight android library for quick RecyclerView creation written in Kotlin.

Using RecyclerView can sometimes be tedious and time-consuming, specially when theres a large number of lists in your app. Creating new classes for adapters and ViewHolders becomes annoying and includes a lot of boilerplate code. 

Instead creating a RecyclerView list using BoilerCycle is easy. It keeps the code in your original class improving code legibility and has a quick set up. It only takes a simple chain function.

## How-To
Declare a RecyclerView in your layout:

```xml
...

    <android.support.v7.widget.RecyclerView
        android:id="@+id/recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

...
```

The library lets you set a custom item layout. For example:
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

## Using a simple list item
The BoilerCycle library contains a simple item layout that can be used as default if the list is very basic, or for testing. It contains a single Textview with padding:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="?attr/selectableItemBackground"
    xmlns:tools="http://schemas.android.com/tools">

    <TextView
        android:id="@+id/simple_boilercycler_item_title"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="16dp"
        tools:text="Item1"
        style="@style/Base.TextAppearance.AppCompat.Body1"/>

</LinearLayout>

```

The simple layout can be used by calling the setSimpleAdapter() method directly on the instance, no need to set a layout:

```kotlin
BoilerCycle.getBoiler()
                // set the adapter with context, RecyclerView and list data,
                .setSimpleAdapter(this, recycler_view, data,
                        onBind = {textView, position ->
                            // set the simple item TextView
                            textView.text = data[position]
                        },
                        onClick = { view, position ->
                            Toast.makeText(this, "Clicked on: " + "$position", Toast.LENGTH_SHORT).show()
                        })
```

Enjoy making lists again!
