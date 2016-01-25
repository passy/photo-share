package me.passy.photoshare.ui.activities

import me.passy.photoshare.PhotoShareApplication
import me.passy.photoshare.R
import me.passy.photoshare.ui.ScreenContainerModel
import org.jetbrains.anko.AnkoLogger
import rx.Observable

public class PhotoUploadActivity : BaseActivity(), AnkoLogger {
    override val component: ActivityComponent
        get() = PhotoShareApplication.graph.plus(ActivityModule())

    override val layout: Int = R.layout.content_photo_upload

    override val screenContainerModel: Observable<ScreenContainerModel>
        get() = Observable.just(ScreenContainerModel(fabVisible = false))
}
