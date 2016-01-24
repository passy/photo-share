package me.passy.photoshare.ui.activities

import me.passy.photoshare.PhotoShareApplication
import me.passy.photoshare.R
import org.jetbrains.anko.AnkoLogger

public class PhotoUploadActivity : BaseActivity(), AnkoLogger {
    override val component: ActivityComponent
        get() = PhotoShareApplication.graph.plus(ActivityModule())

    override val layout: Int = R.layout.content_photo_upload

}
