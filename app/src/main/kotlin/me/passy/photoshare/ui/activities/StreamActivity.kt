package me.passy.photoshare.ui.activities

import android.os.Bundle
import me.passy.photoshare.PhotoShareApplication
import org.jetbrains.anko.AnkoLogger


public class StreamActivity : BaseActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override val component: ActivityComponent
        get() = PhotoShareApplication.graph.plus(ActivityModule())
}

