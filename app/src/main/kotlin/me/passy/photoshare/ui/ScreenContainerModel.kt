package me.passy.photoshare.ui

data class ScreenContainerModel(val fabVisible: Boolean, val menuMode: MenuMode) {
    companion object {
        val DEFAULT = ScreenContainerModel(fabVisible = true, menuMode = MenuMode.HAMBURGER)
    }
}

enum class MenuMode {
    HAMBURGER, UP
}
