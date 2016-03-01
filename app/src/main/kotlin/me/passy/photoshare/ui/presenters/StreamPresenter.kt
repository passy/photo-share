package me.passy.photoshare.ui.presenters

import me.passy.photoshare.ui.params.NoParams
import me.passy.photoshare.ui.views.StreamView

interface StreamPresenter : Presenter<StreamView>
interface StreamPresenterFactory : PresenterFactory<NoParams, StreamPresenter>
