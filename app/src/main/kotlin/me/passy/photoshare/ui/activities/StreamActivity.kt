package me.passy.photoshare.ui.activities

import android.app.Activity
import android.os.Bundle
import me.passy.photoshare.R
import org.jetbrains.anko.AnkoLogger


public class StreamActivity : Activity(), AnkoLogger {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }
}

