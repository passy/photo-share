package me.passy.photoshare.ui

import android.app.Activity
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.NavUtils
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.view.ViewGroup
import android.widget.Toolbar
import butterknife.Bind
import butterknife.ButterKnife
import me.passy.photoshare.R
import rx.Observable
import javax.inject.Inject

public class ScreenContainerImpl @Inject constructor() : ScreenContainer {
    @Bind(R.id.drawer_layout)
    lateinit override var drawerLayout: DrawerLayout

    @Bind(R.id.fab)
    lateinit override var fab: FloatingActionButton

    @Bind(R.id.coordinator_layout)
    lateinit override var coordinatorLayout: CoordinatorLayout

    @Bind(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @Bind(R.id.activity_content)
    lateinit var container: ViewGroup

    override fun bind(activity: Activity, screenContainerModel: Observable<ScreenContainerModel>): ViewGroup {
        activity.setContentView(R.layout.base_layout)
        ButterKnife.setDebug(true)
        ButterKnife.bind(this, activity)

        setupDrawerLayout(drawerLayout, activity)
        setupToolbar(toolbar, activity)

        screenContainerModel.subscribe { model ->
            if (model.fabVisible) {
                fab.show()
            } else {
                fab.hide()
            }

            when (model.drawerMode) {
                DrawerMode.DRAWER_HIDDEN ->
                    drawerLayout.isEnabled = false

                DrawerMode.DRAWER_VISIBLE ->
                    drawerLayout.isEnabled = true
            }
        }

        return container
    }

    override fun onHomeSelected(activity: Activity) {
        if (drawerLayout.isEnabled) {
            drawerLayout.openDrawer(GravityCompat.START)
        } else {
            NavUtils.navigateUpFromSameTask(activity)
        }
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
