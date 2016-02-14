package me.passy.photoshare.ui.presenters

import rx.Observable

interface Presenter<T, U> {
    fun bind(view: T, model: Observable<U>)
}
