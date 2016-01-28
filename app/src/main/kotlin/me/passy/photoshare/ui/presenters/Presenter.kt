package me.passy.photoshare.ui.presenters

interface Presenter<T, U> {
    fun bind(view: T, model: U)
}