package me.passy.photoshare.ui.presenters

import android.os.Bundle
import com.trello.rxlifecycle.ActivityEvent
import com.trello.rxlifecycle.components.ActivityLifecycleProvider
import me.passy.photoshare.ui.params.Params
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

class PresenterHolder @Inject @Singleton constructor() {
    private val registry: ConcurrentHashMap<String, Presenter<*>> = ConcurrentHashMap()

    @Suppress("UNCHECKED_CAST")
    fun <V, P : Params, T : Presenter<V>> obtain(
            savedState: Bundle?,
            activity: ActivityLifecycleProvider,
            params: P,
            factory: PresenterFactory<P, T>,
            view: V) : T {
        val slug = activity.javaClass.name
        val presenter = registry.getOrPut(slug, {
            var presenter: T? = null
            if (savedState != null) {
                presenter = factory.fromState(savedState)
            }

            if (presenter == null) {
                factory.create(params)
            }

            presenter!!.apply { bind(view, activity) }
        }) as T

        activity
                .lifecycle()
                .filter { e -> e == ActivityEvent.DESTROY }
                .compose(activity.bindToLifecycle<ActivityEvent>())
                .subscribe { registry.remove(slug) }

        return presenter
    }
}
