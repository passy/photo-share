package me.passy.photoshare.ui.presenters

import com.trello.rxlifecycle.components.ActivityLifecycleProvider

interface Presenter<T, U> {
    fun bind(lifecycle: ActivityLifecycleProvider, view: T, model: U)
}
