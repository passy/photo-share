package me.passy.photoshare.ui.activities

import android.os.Bundle
import android.view.MenuItem
import com.trello.rxlifecycle.components.RxActivity
import me.passy.photoshare.PhotoShareApplication
import me.passy.photoshare.ui.ForActivity
import me.passy.photoshare.ui.ScreenContainer
import me.passy.photoshare.ui.ScreenContainerModel
import rx.Observable
import javax.inject.Inject

abstract class BaseActivity : RxActivity() {
    @field:[Inject ForActivity]
    lateinit var screenContainer: ScreenContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)

        val mainFrame = screenContainer.bind(this, screenContainerModel)
        layoutInflater.inflate(layout, mainFrame)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                screenContainer.onHomeSelected(this)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    open val component: ActivityComponent = PhotoShareApplication.graph.plus(ActivityModule())
    abstract val layout: Int
    abstract val screenContainerModel: Observable<ScreenContainerModel>
}

