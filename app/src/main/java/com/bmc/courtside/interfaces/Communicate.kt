package com.bmc.courtside.interfaces

import android.view.View

interface Communicate {
    fun sendData()
}

interface ClickListener {
    fun onItemClick(v: View, position: Int)
}