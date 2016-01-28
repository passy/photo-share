package me.passy.photoshare.ui.activities

import android.content.Intent
import android.os.Bundle
import me.passy.photoshare.PhotoShareApplication
import me.passy.photoshare.R
import me.passy.photoshare.ui.ScreenContainerModel
import me.passy.photoshare.ui.params.PhotoUploadParams
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import rx.Observable
import java.io.File

public class StreamActivity : BaseActivity(), AnkoLogger {
    override val screenContainerModel: Observable<ScreenContainerModel>
        get() = Observable.just(ScreenContainerModel.DEFAULT.copy(fabVisible = true))

    private val REQUEST_PHOTO: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        screenContainer.fab.onClick {
            startActivityForResult<CameraActivity>(REQUEST_PHOTO)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // TODO: Tell presenter.
        if (requestCode == 0 && resultCode == RESULT_OK) {
            val photoPath = File(data!!.getStringExtra(CameraActivity.EXTRA_PHOTO_PATH))
            startActivity<PhotoUploadActivity>(
                    PhotoUploadActivity.EXTRA to PhotoUploadParams(photoPath))
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override val component: ActivityComponent
        get() = PhotoShareApplication.graph.plus(ActivityModule())

    override val layout = R.layout.content_main
}

