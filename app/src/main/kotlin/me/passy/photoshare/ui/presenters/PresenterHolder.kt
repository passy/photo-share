package me.passy.photoshare.ui.presenters

object PresenterRegistry {
    private val presenterMap: MutableMap<Class<Any>, Presenter<Any, Any>> = mutableMapOf()

    fun <T> putPresenter(c: PresenterHolder<T>, p: T)
        where T : Presenter<Any, Any> {
        presenterMap.put(c.javaClass, p)
    }

    @Suppress("UNCHECKED_CAST", "CAST_NEVER_SUCCEEDS")
    fun <T> getPresenter(c: PresenterHolder<T>): T
        where T : Presenter<Any, Any> {
        synchronized(this) {
            return presenterMap[c.javaClass as Class<Any>] as T
        }
    }

    @Suppress("CAST_NEVER_SUCCEEDS")
    fun remove(c: PresenterHolder<*>) {
        synchronized(this) {
            presenterMap.remove(c.javaClass as Class<Any>)
        }
    }
}

// Phantom type to make registration type-safe-ish.
interface PresenterHolder<T : Presenter<*, *>> {
}
