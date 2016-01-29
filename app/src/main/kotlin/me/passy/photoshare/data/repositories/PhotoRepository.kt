package me.passy.photoshare.data.repositories

import me.passy.photoshare.data.parse.Photo

interface PhotoRepository {
    fun savePhoto(photo: Photo)
}
