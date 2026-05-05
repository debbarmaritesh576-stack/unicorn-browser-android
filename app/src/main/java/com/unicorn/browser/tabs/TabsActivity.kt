package com.unicorn.browser.tabs

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unicorn.browser.R

class TabsActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: TabsAdapter
    private lateinit var btnAdd: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabs)

        recycler = findViewById(R.id.tabsRecycler)
        btnAdd = findViewById(R.id.btnAddTab)

        adapter = TabsAdapter(
            TabsManager.getTabs().toMutableList(),
            onClick = { position ->
                TabsManager.setCurrent(position)
                setResultOk()
                finish()
            },
            onClose = { position ->
                TabsManager.closeTab(position)
                adapter.updateData(TabsManager.getTabs())
                if (TabsManager.size() == 0) {
                    TabsManager.addTab("https://www.google.com")
                    adapter.updateData(TabsManager.getTabs())
                }
            }
        )

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        btnAdd.setOnClickListener {
            TabsManager.addTab("https://www.google.com")
            setResultOk()
            finish()
        }
    }

    private fun setResultOk() {
        setResult(Activity.RESULT_OK, Intent())
    }
}