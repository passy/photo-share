package me.passy.photoshare.ui.activities

import android.app.Activity
import android.os.Bundle
import butterknife.ButterKnife
import me.passy.photoshare.ui.ForActivity
import me.passy.photoshare.ui.ScreenContainer
import javax.inject.Inject

abstract public class BaseActivity : Activity() {
    @field:[Inject ForActivity]
    lateinit var screenContainer: ScreenContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)

        val mainFrame = screenContainer.bind(this)
        layoutInflater.inflate(layout, mainFrame)

        ButterKnife.bind(this)
    }

    abstract val component: ActivityComponent
    abstract val layout: Int
}
