package me.passy.photoshare

import dagger.Component
import me.passy.photoshare.ui.activities.ActivityComponent
import me.passy.photoshare.ui.activities.ActivityModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidModule::class, ApplicationModule::class))
interface ApplicationComponent {
    fun inject(application: PhotoShareApplication)
    fun plus(activityModule: ActivityModule): ActivityComponent
}

