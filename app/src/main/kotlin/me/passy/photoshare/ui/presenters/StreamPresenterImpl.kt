package me.passy.photoshare.ui.presenters

import android.os.Bundle
import com.trello.rxlifecycle.components.ActivityLifecycleProvider
import me.passy.photoshare.ui.params.NoParams
import me.passy.photoshare.ui.views.StreamView
import javax.inject.Inject
import javax.inject.Singleton

class StreamPresenterImpl : StreamPresenter {
    override fun bind(view: StreamView, lifecycleProvider: ActivityLifecycleProvider) {
    }

    override fun save(state: Bundle) {
    }
}

class StreamPresenterFactoryImpl @Inject @Singleton constructor():
        StreamPresenterFactory {
    override fun create(params: NoParams): StreamPresenter = StreamPresenterImpl()
    override fun fromState(bundle: Bundle): StreamPresenter? = null
}
