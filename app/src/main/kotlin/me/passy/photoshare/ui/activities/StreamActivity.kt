package me.passy.photoshare.ui.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import me.passy.photoshare.PhotoShareApplication
import me.passy.photoshare.R
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.onClick


public class StreamActivity : BaseActivity(), AnkoLogger {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _screenContainer.fab.onClick {
            Snackbar.make(_screenContainer.coordinatorLayout, "Hello, World!", Snackbar.LENGTH_SHORT).show()
        }

    }

    override val component: ActivityComponent
        get() = PhotoShareApplication.graph.plus(ActivityModule())

    override val layout = R.layout.content_main
}

