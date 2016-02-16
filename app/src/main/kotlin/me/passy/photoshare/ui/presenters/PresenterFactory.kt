package me.passy.photoshare.ui.presenters

import android.os.Bundle
import me.passy.photoshare.ui.params.Params

interface PresenterFactory<in P : Params, T> {
    fun create(params: P): T
    fun fromState(bundle: Bundle): T?
}
