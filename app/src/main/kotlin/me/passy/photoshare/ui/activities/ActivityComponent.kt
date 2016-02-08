package me.passy.photoshare.ui.activities

import dagger.Subcomponent
import me.passy.photoshare.ui.ForActivity
import me.passy.photoshare.ui.presenters.PresenterModule

@ForActivity
@Subcomponent(modules = arrayOf(ActivityModule::class, PresenterModule::class))
interface ActivityComponent {
    fun inject(activity: StreamActivity)
    fun inject(activity: PhotoUploadActivity)
    fun inject(activity: BaseActivity)
}
