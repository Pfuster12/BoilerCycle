# BoilerCycle
Lightweight android library for quick RecyclerView creation.

Using RecyclerView can sometimes be tedious and time-consuming, specially when theres a large number of lists in your app. Creating new classes for adapters and ViewHolders becomes annoying and difficult to follow, including a lot of boilerplate code.

## How-To
Making a list with RecyclerView using BoilerCycle is easy. It keeps the code in your original class improving code legibility and has a quick set up. It only takes a simple chain function.

Declare a RecyclerView in your layout:

```xml
...

    <android.support.v7.widget.RecyclerView
        android:id="@+id/recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

...
```

The library lets you set your custom item layout to use. Create your item's layout:
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

```Kotlin
 recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
```

We're ready to use BoilerCycle. First get an instance of the library:
```Kotlin
BoilerCycle.getBoiler()
...
```
Next set your custom item layout to use
```Kotlin
BoilerCycle.getBoiler()
         .setItemLayout(R.layout.item)
...
```
And finally set the adapter. Boilercycle uses the great Kotlin lambda use to provide the adapter with custom on bind and on item click methods. Pass the data for size count and let all the boilerplate be handled:
```Kotlin
BoilerCycle.getBoiler()
        .setItemLayout(R.layout.item)
        .setSimpleAdapter(this, recycler_view, data,
                onBind = {textView, position ->
                    textView.text = data[position]
                },
                onClick = { view, position ->
                    Toast.makeText(this, "Clicked on: " + "$position", Toast.LENGTH_SHORT).show()
                }))
```
