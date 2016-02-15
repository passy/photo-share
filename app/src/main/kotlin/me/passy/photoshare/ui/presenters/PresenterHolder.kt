package me.passy.photoshare.ui.presenters

import com.trello.rxlifecycle.ActivityEvent
import com.trello.rxlifecycle.components.ActivityLifecycleProvider
import me.passy.photoshare.ui.params.Params
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

class PresenterHolder @Inject @Singleton constructor() {
    private val registry: ConcurrentHashMap<String, Presenter<*>> = ConcurrentHashMap()

    @Suppress("UNCHECKED_CAST")
    fun <V, P : Params, T : Presenter<V>> obtain(activity: ActivityLifecycleProvider,
                                     params: P,
                                     factory: PresenterFactory<P, T>,
                                     view: V) : T {
        val slug = activity.javaClass.name
        val presenter = registry.getOrPut(slug, {
            factory.create(params).apply {
                bind(view)
            }
        }) as T

        activity
                .lifecycle()
                .filter { e -> e == ActivityEvent.DESTROY }
                .compose(activity.bindToLifecycle<ActivityEvent>())
                .subscribe { registry.remove(slug) }

        return presenter
    }
}
