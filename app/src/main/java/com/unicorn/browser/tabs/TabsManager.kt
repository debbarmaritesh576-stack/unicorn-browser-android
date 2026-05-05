package com.unicorn.browser.tabs

object TabsManager {

    private val tabs = mutableListOf<Tab>()
    private var currentIndex = -1
    private var nextId = 0

    fun initIfEmpty(home: String) {
        if (tabs.isEmpty()) {
            addTab(home)
        }
    }

    fun addTab(url: String): Tab {
        val tab = Tab(id = nextId++, url = url)
        tabs.add(tab)
        currentIndex = tabs.lastIndex
        return tab
    }

    fun closeTab(index: Int) {
        if (index in tabs.indices) {
            tabs.removeAt(index)
            if (tabs.isEmpty()) {
                currentIndex = -1
            } else {
                currentIndex = (index - 1).coerceAtLeast(0)
            }
        }
    }

    fun setCurrent(index: Int) {
        if (index in tabs.indices) {
            currentIndex = index
        }
    }

    fun updateCurrentUrl(url: String) {
        if (currentIndex in tabs.indices) {
            tabs[currentIndex].url = url
        }
    }

    fun getCurrent(): Tab? =
        if (currentIndex in tabs.indices) tabs[currentIndex] else null

    fun getTabs(): List<Tab> = tabs

    fun getCurrentIndex(): Int = currentIndex

    fun size(): Int = tabs.size
}