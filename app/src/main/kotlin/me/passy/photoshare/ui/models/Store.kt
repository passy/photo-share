package me.passy.photoshare.ui.models

import rx.Observable

interface Store<T> {
    fun observable(): Observable<T>
    fun setState(model: T): Unit
}
