package me.passy.photoshare.ui.activities

import android.app.Activity
import android.os.Bundle
import me.passy.photoshare.ui.ForActivity
import me.passy.photoshare.ui.ScreenContainer
import javax.inject.Inject

abstract public class BaseActivity : Activity() {
    @field:[Inject ForActivity]
    lateinit var mainframe: ScreenContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component.inject(this)
    }

    abstract val component: ActivityComponent
}
