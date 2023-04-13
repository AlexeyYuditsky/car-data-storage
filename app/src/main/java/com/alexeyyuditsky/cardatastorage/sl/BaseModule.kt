package com.alexeyyuditsky.cardatastorage.sl

import androidx.lifecycle.ViewModel

interface BaseModule<T : ViewModel> {
    fun getViewModel(): T
}
