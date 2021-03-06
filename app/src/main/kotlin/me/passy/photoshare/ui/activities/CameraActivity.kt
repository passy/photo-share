package me.passy.photoshare.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.example.android.camera2basic.Camera2BasicFragment
import me.passy.photoshare.R
import org.jetbrains.anko.*
import rx.Subscription

public class CameraActivity : Activity(), AnkoLogger {
    private val FRAG_CAMERA = "CAMERA_FRAGMENT"
    private var photoSubscription: Subscription? = null

    companion object {
        val EXTRA_PHOTO_PATH = "PHOTO_PATH"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        super.onCreate(savedInstanceState)
        CameraActivityUI().setContentView(this)

        val fragment = insertFragment(savedInstanceState)
        photoSubscription = fragment.pictureObservable.subscribe({ file ->
            val intent = Intent()
            intent.putExtra(EXTRA_PHOTO_PATH, file.absolutePath)
            setResult(RESULT_OK, intent)
            finish()
        }, { err ->
            error("Error while capturing: ", err)
            setResult(RESULT_CANCELED)
            finish()
        })
    }

    private fun insertFragment(savedInstanceState: Bundle?): Camera2BasicFragment {
        var fragment = savedInstanceState?.let { bundle ->
            fragmentManager.getFragment(bundle, FRAG_CAMERA) as Camera2BasicFragment
        }

        if (fragment == null) {
            fragment = Camera2BasicFragment.newInstance()
            fragmentManager.beginTransaction()
                    .replace(R.id.camera_holder, fragment, FRAG_CAMERA)
                    .commit()
        }

        return fragment!!
    }

    override fun onDestroy() {
        super.onDestroy()

        photoSubscription?.unsubscribe()
    }
}

class CameraActivityUI : AnkoComponent<CameraActivity> {
    override fun createView(ui: AnkoContext<CameraActivity>): View = with(ui) {
        frameLayout {
            backgroundColor = 0x000.opaque
            id = R.id.camera_holder
        }
    }
}
