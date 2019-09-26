package com.zdog.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zdog.demo.extention.addFragment
import com.zdog.demo.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            addFragment(MainFragment.newInstance())
    }
}
