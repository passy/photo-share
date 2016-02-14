package me.passy.photoshare.ui.presenters

import com.trello.rxlifecycle.ActivityEvent
import com.trello.rxlifecycle.components.ActivityLifecycleProvider
import me.passy.photoshare.ui.models.Store
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

class PresenterHolder @Inject @Singleton constructor() {
    private val registry: ConcurrentHashMap<String, Presenter<*, *>> = ConcurrentHashMap()

    @Suppress("UNCHECKED_CAST")
    fun <V, M, T : Presenter<V, M>> obtain(activity: ActivityLifecycleProvider,
                                           provider: Provider<T>,
                                           view: V,
                                           store: Store<M>) : T {
        val slug = activity.javaClass.name
        val presenter = registry.getOrPut(slug, {
            val p = provider.get()
            p.bind(view, store.observable())
            return p
        }) as T

        activity
                .lifecycle()
                .filter { e -> e == ActivityEvent.DESTROY }
                .compose(activity.bindToLifecycle<ActivityEvent>())
                .subscribe { registry.remove(slug) }

        return presenter
    }
}
