package com.hend.cataloguechallenge.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Abstract class with commonly used abstract methods through the activities
 */
abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun init()
    protected abstract fun initViewBinding()
    protected abstract fun observeViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        observeViewModel()
        initViewBinding()
    }
}