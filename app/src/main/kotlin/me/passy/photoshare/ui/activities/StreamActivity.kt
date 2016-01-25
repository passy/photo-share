package me.passy.photoshare.ui.activities

import android.os.Bundle
import me.passy.photoshare.PhotoShareApplication
import me.passy.photoshare.R
import me.passy.photoshare.ui.ScreenContainerModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.onClick
import org.jetbrains.anko.startActivity
import rx.Observable

public class StreamActivity : BaseActivity(), AnkoLogger {
    override val screenContainerModel: Observable<ScreenContainerModel>
        get() = Observable.just(ScreenContainerModel(fabVisible = true))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        screenContainer.fab.onClick {
            startActivity<PhotoUploadActivity>()
        }

    }

    override val component: ActivityComponent
        get() = PhotoShareApplication.graph.plus(ActivityModule())

    override val layout = R.layout.content_main
}

