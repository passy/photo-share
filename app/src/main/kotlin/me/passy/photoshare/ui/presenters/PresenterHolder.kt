package me.passy.photoshare.ui.presenters

import com.trello.rxlifecycle.ActivityEvent
import com.trello.rxlifecycle.components.ActivityLifecycleProvider
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

class PresenterHolder @Inject @Singleton constructor() {
    private val registry: ConcurrentHashMap<String, Presenter<*, *>> = ConcurrentHashMap()

    @Suppress("UNCHECKED_CAST")
    fun <T : Presenter<*, *>> obtain(activity: ActivityLifecycleProvider,
                      provider: Provider<T>) : T {
        val slug = activity.javaClass.name
        val presenter = registry.getOrPut(slug, { provider.get() })

        activity
                .lifecycle()
                .filter { e -> e == ActivityEvent.DESTROY }
                .compose(activity.bindToLifecycle<ActivityEvent>())
                .subscribe { registry.remove(slug) }

        return presenter as T
    }
}
