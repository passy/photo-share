package me.passy.photoshare.ui

import android.app.Activity
import android.support.v4.widget.DrawerLayout
import android.view.ViewGroup
import android.widget.Toolbar
import butterknife.Bind
import butterknife.ButterKnife
import me.passy.photoshare.R
import javax.inject.Inject

public class ScreenContainerImpl @Inject constructor() : ScreenContainer {
    @Bind(R.id.drawer_layout)
    lateinit override var drawerLayout: DrawerLayout

    @Bind(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @Bind(R.id.activity_content)
    lateinit var container: ViewGroup


    override fun bind(activity: Activity): ViewGroup {
        activity.setContentView(R.layout.activity_main)
        ButterKnife.bind(this, activity)

        setupDrawerLayout(drawerLayout, activity)
        setupToolbar(toolbar, activity)

        return container
    }

    private fun setupToolbar(toolbar: Toolbar, activity: Activity) {
        activity.setActionBar(toolbar)

        with(activity.actionBar) {
            setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupDrawerLayout(drawerLayout: DrawerLayout, activity: Activity) {
    }
}
