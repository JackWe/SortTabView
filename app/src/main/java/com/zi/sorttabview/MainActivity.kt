package com.zi.sorttabview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sortTabView = findViewById<SortTabView>(R.id.sort_view)
        val list = mutableListOf<SortTabView.Item>()
        for (i in 1..5) {
            list.add(SortTabView.Item("Sort$i"))
        }
        sortTabView.setData(list)
        sortTabView.onTabClickListener = object : SortTabView.OnTabClickListener {
            override fun onClick(item: SortTabView.Item) {
                Log.e("wei==", item.toString())
            }
        }
    }
}