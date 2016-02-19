package me.passy.photoshare.ui.presenters

import android.os.Bundle
import com.trello.rxlifecycle.ActivityEvent
import com.trello.rxlifecycle.components.ActivityLifecycleProvider
import com.trello.rxlifecycle.components.RxActivity
import me.passy.photoshare.ui.params.Params
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

class PresenterHolder @Inject constructor() : AnkoLogger {
    private val registry: ConcurrentHashMap<String, Presenter<*>> = ConcurrentHashMap()

    init {
        info("Init PresenterHolder")
    }

    @Suppress("UNCHECKED_CAST")
    fun <V, P : Params, T : Presenter<V>> obtain(
            savedState: Bundle?,
            activity: RxActivity,
            params: P,
            factory: PresenterFactory<P, T>,
            view: V) : T {
        val slug = activity.javaClass.name
        val presenter = registry.getOrPut(slug, {
            var presenter: T? = null
            if (savedState != null) {
                presenter = factory.fromState(savedState)
            }

            presenter ?: factory.create(params)
        }) as T

        activity
                .lifecycle()
                .filter { e -> e == ActivityEvent.DESTROY }
                .filter { !activity.isChangingConfigurations }
                .subscribe {
                    info { "Removing $slug from registry." }
                    registry.remove(slug)
                }

        return presenter.apply {
            bind(view, activity)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Presenter<*>>save(state: Bundle?,
                               activity: ActivityLifecycleProvider) {
        if (state != null) {
            val presenter = registry[activity.javaClass.name] as T?
            presenter?.save(state)
        }
    }
}
