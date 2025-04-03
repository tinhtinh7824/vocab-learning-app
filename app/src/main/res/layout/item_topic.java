<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:padding="16dp"
    android:background="?attr/selectableItemBackground"
    android:clickable="true"
    android:focusable="true">

    <!-- Icon của chủ đề -->
    <ImageView
        android:id="@+id/iconTopic"
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:layout_gravity="center_vertical"
        android:layout_margin="20dp"
        android:src="@drawable/house" />

    <!-- Tên chủ đề -->
    <TextView
        android:id="@+id/textTopic"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="18sp"
        android:textColor="#000000"
        android:layout_gravity="center_vertical"
        android:layout_marginStart="16dp"
        android:text="Topic Name" />
    <EditText
        android:id="@+id/btnEditTopic"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="16sp"
        android:hint="Nhập tên chủ đề"
        android:src="@drawable/ic_edit"
        android:visibility="gone"/> <!-- Ẩn đi ban đầu -->

</LinearLayout>
