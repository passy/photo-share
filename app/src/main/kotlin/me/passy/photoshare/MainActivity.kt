package me.passy.photoshare

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.widget.Toolbar
import com.parse.ui.ParseLoginBuilder

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            val builder = ParseLoginBuilder(this);
            startActivityForResult(builder.build(), 0);
        }
    }
}
