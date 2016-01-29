package me.passy.photoshare.data.parse

import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser

/*
 * An extension of ParseObject that makes
 * it more convenient to access information
 * about a given Photo 
 */
@ParseClassName("Photo")
class Photo : ParseObject() {
    var image: ParseFile
        get() = getParseFile("image")
        set(file) = put("image", file)

    var user: ParseUser
        get() = getParseUser("user")
        set(user) = put("user", user)
}
