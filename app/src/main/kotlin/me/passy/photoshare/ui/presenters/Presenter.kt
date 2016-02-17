package me.passy.photoshare.ui.presenters

import android.os.Bundle
import com.trello.rxlifecycle.components.ActivityLifecycleProvider

interface Presenter<T> {
    fun bind(view: T, lifecycleProvider: ActivityLifecycleProvider)
    // TODO: Actually call this ...
    fun save(state: Bundle): Unit
}
