package me.passy.photoshare.ui.presenters

interface Presenter<T> {
    fun bind(view: T)
}
