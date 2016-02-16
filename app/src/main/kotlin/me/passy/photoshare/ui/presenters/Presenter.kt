package me.passy.photoshare.ui.presenters

import com.trello.rxlifecycle.components.ActivityLifecycleProvider

interface Presenter<T> {
    fun bind(view: T, lifecycleProvider: ActivityLifecycleProvider)
}
