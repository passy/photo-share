package me.passy.photoshare.ui.activities

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import butterknife.Bind
import me.passy.photoshare.PhotoShareApplication
import me.passy.photoshare.R
import me.passy.photoshare.ui.MenuMode
import me.passy.photoshare.ui.ScreenContainerModel
import me.passy.photoshare.ui.models.PhotoUploadModel
import me.passy.photoshare.ui.params.PhotoUploadParams
import me.passy.photoshare.ui.presenters.PhotoUploadPresenterImpl
import me.passy.photoshare.ui.views.PhotoUploadView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.imageURI
import rx.Observable

public class PhotoUploadActivity : BaseActivity(), PhotoUploadView, AnkoLogger {
    companion object {
        val EXTRA = "EXTRA"
    }

    private var params: PhotoUploadParams = PhotoUploadParams.EMPTY

    @Bind(R.id.thumbnail)
    lateinit var thumbnailView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        params = intent.getParcelableExtra<PhotoUploadParams>(EXTRA) ?: PhotoUploadParams.EMPTY

        if (params == PhotoUploadParams.EMPTY) {
            throw IllegalArgumentException("Failed to specify $EXTRA when starting $localClassName}.")
        }

        // TODO: No, not like that, silly!
        val presenter = PhotoUploadPresenterImpl()
        presenter.bind(
                this, PhotoUploadModel(photoPath = Observable.just(Uri.fromFile(params.photoPath))))
    }

    override val component: ActivityComponent
        get() = PhotoShareApplication.graph.plus(ActivityModule())

    override val layout: Int = R.layout.content_photo_upload

    override val screenContainerModel: Observable<ScreenContainerModel>
        get() = Observable.just(
                ScreenContainerModel.DEFAULT.copy(fabVisible = false, menuMode = MenuMode.UP)
        )

    override fun setThumbnailSource(src: Uri) {
        thumbnailView.imageURI = src
    }

}
