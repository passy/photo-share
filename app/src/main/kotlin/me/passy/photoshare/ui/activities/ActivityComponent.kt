package me.passy.photoshare.ui.activities

import dagger.Subcomponent
import me.passy.photoshare.ui.ForActivity

@ForActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
public interface ActivityComponent {
    fun inject(activity: StreamActivity)
    fun inject(activity: BaseActivity)
}
