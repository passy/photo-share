package me.passy.photoshare.ui.activities

import me.passy.photoshare.R
import me.passy.photoshare.ui.ScreenContainerModel
import org.jetbrains.anko.AnkoLogger
import rx.Observable

class CoordinatorActivity : BaseActivity(), AnkoLogger {
    override val layout: Int
        get() = R.layout.coordinator_layout

    override val screenContainerModel: Observable<ScreenContainerModel>
        get() = Observable.just(ScreenContainerModel.DEFAULT)
}
