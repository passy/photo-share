package me.passy.photoshare.ui.activities

import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.widget.EditText
import android.widget.ImageView
import butterknife.Bind
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.widget.textChanges
import me.passy.photoshare.R
import me.passy.photoshare.ui.MenuMode
import me.passy.photoshare.ui.ScreenContainerModel
import me.passy.photoshare.ui.params.PhotoUploadParams
import me.passy.photoshare.ui.presenters.PhotoUploadPresenter
import me.passy.photoshare.ui.presenters.PhotoUploadPresenterFactory
import me.passy.photoshare.ui.presenters.PresenterHolder
import me.passy.photoshare.ui.views.PhotoUploadView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.imageURI
import rx.Observable
import rx.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

class PhotoUploadActivity : BaseActivity(), PhotoUploadView, AnkoLogger {
    companion object {
        val EXTRA = "EXTRA"
    }

    private var params: PhotoUploadParams = PhotoUploadParams.EMPTY

    private val sendActionClicks: PublishSubject<Unit> = PublishSubject.create()

    @Bind(R.id.thumbnail)
    lateinit var thumbnailView: ImageView

    @Bind(R.id.photo_title)
    lateinit var photoTitleView: EditText

    @field:[Inject Singleton]
    lateinit var presenterHolder: PresenterHolder

    @Inject
    lateinit var presenterFactory: PhotoUploadPresenterFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this) // TODO: Figure out if this is okay.

        params = intent.getParcelableExtra<PhotoUploadParams>(EXTRA) ?: PhotoUploadParams.EMPTY

        if (params == PhotoUploadParams.EMPTY) {
             throw IllegalArgumentException("Failed to specify $EXTRA when starting $localClassName}.")
        }

        presenterHolder.obtain(
                savedInstanceState,
                this,
                params,
                presenterFactory,
                this
        )
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)

        presenterHolder.save<PhotoUploadPresenter>(outState, this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_send, menu)
        // We want a loud crash here if this fails.
        menu!!.findItem(R.id.action_send).clicks().subscribe(sendActionClicks)

        return super.onCreateOptionsMenu(menu)
    }

    override val saveBtnObservable: Observable<Unit>
        get() = sendActionClicks

    override val photoTitleObservable: Observable<CharSequence>
        get() = photoTitleView.textChanges()

    override val layout: Int = R.layout.content_photo_upload

    override val screenContainerModel: Observable<ScreenContainerModel>
        get() = Observable.just(
                ScreenContainerModel.DEFAULT.copy(fabVisible = false, menuMode = MenuMode.UP)
        )

    override fun setThumbnailSource(src: Uri) {
        thumbnailView.imageURI = src
    }

    override fun setFormEnabled(enabled: Boolean) {
        photoTitleView.isEnabled = enabled
    }

}
