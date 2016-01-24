package me.passy.photoshare.ui


import android.app.Activity
import android.support.v4.widget.DrawerLayout
import android.view.ViewGroup

/**
 * An indirection which allows controlling the root container used for each activity.
 */
public interface ScreenContainer {
    /**
     * The root [android.view.ViewGroup] into which the activity should place its contents.
     */
    fun bind(activity: Activity): ViewGroup

    /**
     * Returns the drawerLayout of this window.
     */
    val drawerLayout: DrawerLayout
}

