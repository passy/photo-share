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
import com.trello.rxlifecycle.components.RxActivity
import me.passy.photoshare.R
import rx.Observable
import javax.inject.Inject

class ScreenContainerImpl @Inject constructor() : ScreenContainer {
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

    override fun bind(activity: RxActivity, screenContainerModel: Observable<ScreenContainerModel>): ViewGroup {
        activity.setContentView(R.layout.base_layout)
        ButterKnife.bind(this, activity)

        screenContainerModel
                .distinctUntilChanged()
                .compose(activity.bindToLifecycle<ScreenContainerModel>())
                .subscribe { model ->
                    if (model.fabVisible) {
                        fab.show()
                    } else {
                        fab.hide()
                    }

                    setupToolbar(toolbar, activity, model.menuMode)
                    setupDrawerLayout(drawerLayout, activity, model.menuMode)
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

    private fun setupToolbar(toolbar: Toolbar, activity: Activity, menuMode: MenuMode) {
        activity.setActionBar(toolbar)

        val upDrawable = when (menuMode) {
            MenuMode.UP -> 0
            MenuMode.HAMBURGER -> R.drawable.ic_menu_white_24dp
        }

        with(activity.actionBar) {
            setHomeAsUpIndicator(upDrawable)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupDrawerLayout(drawerLayout: DrawerLayout, activity: Activity, menuMode: MenuMode) {
        when (menuMode) {
            MenuMode.HAMBURGER -> {
                drawerLayout.isEnabled = true
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }

            MenuMode.UP -> {
                drawerLayout.isEnabled = false
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
    }
}
