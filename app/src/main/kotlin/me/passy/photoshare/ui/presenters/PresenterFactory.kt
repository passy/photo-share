package me.passy.photoshare.ui.presenters

import me.passy.photoshare.ui.params.Params

interface PresenterFactory<in P : Params, T> {
    fun create(params: P): T
}
