package com.zi.sorttabview;

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.google.android.material.tabs.TabLayout
import kotlin.properties.Delegates


/**
 * desc   : 自定义排序控件
 * date   : 2021/12/28 16:49
 * version: 1
 * author : 魏滋珑
 */
class SortTabView : LinearLayoutCompat {

    private lateinit var mContext: Context
    private var textSize: Float by Delegates.notNull()
    private var tabLayout: TabLayout by Delegates.notNull()
    var onTabClickListener: OnTabClickListener? = null
    private lateinit var listData: MutableList<Item>

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context = context, attrs = attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        init(context = context, attrs = attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        tabLayout.layoutParams.height = measuredHeight
    }

    private fun init(context: Context, attrs: AttributeSet) {
        this.mContext = context
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.SortTabView)
        textSize = typeArray.getDimension(R.styleable.SortTabView_mTextSize, 12f)
        val textColor = typeArray.getInt(R.styleable.SortTabView_mTextColor, 0)
        val textSelectColor = typeArray.getInt(R.styleable.SortTabView_mTextSelectColor, 0)
        val background = typeArray.getColor(R.styleable.SortTabView_mBackground, 0)
        LayoutInflater.from(context).inflate(R.layout.view_sort, this, true)
        tabLayout = findViewById(R.id.tab_layout)
        tabLayout.setBackgroundColor(background)
        tabLayout.setTabTextColors(textColor, textSelectColor)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (::listData.isInitialized) {
                    listData[tab.position].status = 1
                    listData.forEach {
                        val view = tabLayout.getTabAt(it.position)?.customView
                        if (it.position != tab.position) {
                            it.status = 0
                            view?.run {
                                val upIv: ImageView = findViewById(R.id.up_iv)
                                val downIv: ImageView = findViewById(R.id.down_iv)
                                upIv.setImageResource(if (it.status == 1) R.mipmap.up_blue else R.mipmap.up_gray)
                                downIv.setImageResource(if (it.status == 2) R.mipmap.down_blue else R.mipmap.down_gray)
                            }
                        } else {
                            view?.run {
                                val upIv: ImageView = findViewById(R.id.up_iv)
                                val downIv: ImageView = findViewById(R.id.down_iv)
                                upIv.setImageResource(if (it.status == 1) R.mipmap.up_blue else R.mipmap.up_gray)
                                downIv.setImageResource(if (it.status == 2) R.mipmap.down_blue else R.mipmap.down_gray)
                            }
                        }
                    }
                    onTabClickListener?.onClick(listData[tab.position])
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                if (::listData.isInitialized) {
                    val view = tabLayout.getTabAt(tab.position)?.customView
                    view?.run {
                        val item = listData[tab.position]
                        when (item.status) {
                            0 -> {
                                item.status = 1
                            }
                            1 -> {
                                item.status = 2
                            }
                            2 -> {
                                item.status = 0
                            }
                        }
                        val upIv: ImageView = findViewById(R.id.up_iv)
                        val downIv: ImageView = findViewById(R.id.down_iv)
                        upIv.setImageResource(if (item.status == 1) R.mipmap.up_blue else R.mipmap.up_gray)
                        downIv.setImageResource(if (item.status == 2) R.mipmap.down_blue else R.mipmap.down_gray)
                    }
                    onTabClickListener?.onClick(listData[tab.position])
                }
            }
        })
        typeArray.recycle()
    }

    fun setData(list: MutableList<Item>) {
        list.forEachIndexed { index, it ->
            it.position = index
            tabLayout.addTab(tabLayout.newTab().setCustomView(tabIcon(it)))
        }
        listData = list
    }

    private fun tabIcon(item: Item): View? {
        val newTab: View = LayoutInflater.from(mContext).inflate(R.layout.item_view_sort, null)
        val tv: TextView = newTab.findViewById(R.id.name_tv)
        tv.text = item.name
        tv.textSize = textSize
        val upIv: ImageView = newTab.findViewById(R.id.up_iv)
        val downIv: ImageView = newTab.findViewById(R.id.down_iv)
        upIv.setImageResource(if (item.status == 1) R.mipmap.up_blue else R.mipmap.up_gray)
        downIv.setImageResource(if (item.status == 2) R.mipmap.down_blue else R.mipmap.down_gray)
        /*  newTab.setOnClickListener {

              when (item.status) {
                  0 -> {
                      item.status = 1
                  }
                  1 -> {
                      item.status = 2
                  }
                  2 -> {
                      item.status = 0
                  }
              }
              listData?.forEach {
                  if (it.position != item.position) {
                      it.status = 0
                      var view = tabLayout.getTabAt(it.position)?.customView
                      view?.run {
                          val upIv: ImageView = findViewById(R.id.up_iv)
                          val downIv: ImageView = findViewById(R.id.down_iv)
                          upIv.setImageResource(if (it.status == 1) R.mipmap.up_blue else R.mipmap.up_gray)
                          downIv.setImageResource(if (it.status == 2) R.mipmap.down_blue else R.mipmap.down_gray)
                      }
                  }
              }
              upIv.setImageResource(if (item.status == 1) R.mipmap.up_blue else R.mipmap.up_gray)
              downIv.setImageResource(if (item.status == 2) R.mipmap.down_blue else R.mipmap.down_gray)
              onTabClickListener?.onClick(item)
          }*/
        return newTab
    }

    interface OnTabClickListener {
        fun onClick(item: Item)
    }

    /**
     * status 0未选中 1向上 2向下
     */
    data class Item(var name: String) {
        var status: Int = 0
        internal var position: Int = 0

        constructor(name: String, status: Int) : this(name) {
            if (status > 2 || status < 0)
                this.status = 0
        }
    }

}