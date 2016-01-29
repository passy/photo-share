package me.passy.photoshare.ui


import android.app.Activity
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.DrawerLayout
import android.view.ViewGroup
import rx.Observable

/**
 * An indirection which allows controlling the root container used for each activity.
 */
public interface ScreenContainer {
    /**
     * The root [ViewGroup] into which the activity should place its contents.
     */
    fun bind(activity: Activity, screenContainerModel: Observable<ScreenContainerModel>): ViewGroup

    /**
     * Run when "home" in the action bar is selected.
     */
    fun onHomeSelected(activity: Activity)

    /**
     * Returns the [DrawerLayout] of this window.
     */
    val drawerLayout: DrawerLayout

    /**
     * Returns the [CoordinatorLayout] of this window.
     */
    val coordinatorLayout: CoordinatorLayout

    /**
     * Returns the FAB of this window.
     */
    val fab: FloatingActionButton
}

