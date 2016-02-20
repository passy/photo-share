package me.passy.photoshare.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.Bind
import butterknife.ButterKnife
import me.passy.photoshare.PhotoShareApplication
import me.passy.photoshare.R
import me.passy.photoshare.ui.ScreenContainerModel
import me.passy.photoshare.ui.params.PhotoUploadParams
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.onClick
import org.jetbrains.anko.startActivityForResult
import rx.Observable
import java.io.File

class StreamActivity : BaseActivity(), AnkoLogger {
    override val screenContainerModel: Observable<ScreenContainerModel>
        get() = Observable.just(ScreenContainerModel.DEFAULT.copy(fabVisible = true))

    enum class RequestCode(val code: Int) {
        PHOTO(0),
        UPLOAD(1)
    }

    @Bind(R.id.coordinator_layout)
    lateinit var coordinator: CoordinatorLayout

    @Bind(R.id.recycler)
    lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        screenContainer.fab.onClick {
            startActivityForResult<CameraActivity>(RequestCode.PHOTO.code)
        }

        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != RESULT_OK) {
            Snackbar.make(coordinator, "Something went wrong. Please try again.", Snackbar.LENGTH_SHORT)
                .show()
            super.onActivityResult(requestCode, resultCode, data)
        } else {
            // TODO: Decide whether this is a responsibility the presenter should be concerned with.
            when (requestCode) {
                RequestCode.PHOTO.code -> {
                    val photoPath = File(data!!.getStringExtra(CameraActivity.EXTRA_PHOTO_PATH))
                    startActivityForResult<PhotoUploadActivity>(
                            RequestCode.UPLOAD.code, PhotoUploadActivity.EXTRA to PhotoUploadParams(photoPath))
                }
                RequestCode.UPLOAD.code -> {
                    Snackbar.make(coordinator, "Your photo should appear shortly. Thanks.", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override val component: ActivityComponent
        get() = PhotoShareApplication.graph.plus(ActivityModule())

    override val layout = R.layout.content_main
}

class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    @Bind(R.id.photo_title)
    lateinit var title: TextView

    @Bind(R.id.photo_image)
    lateinit var image: ImageView

    init {
        ButterKnife.bind(this, itemView)
    }
}
