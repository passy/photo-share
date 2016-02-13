package me.passy.photoshare

import android.support.multidex.MultiDexApplication
import com.parse.Parse
import com.parse.ParseACL
import com.parse.ParseObject
import com.parse.ParseTwitterUtils
import me.passy.photoshare.data.parse.Photo

class PhotoShareApplication : MultiDexApplication() {
    companion object {
        // allow access to it from java code
        @JvmStatic lateinit var graph: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        Parse.enableLocalDatastore(this)
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG)
        Parse.initialize(this, BuildConfig.PARSE_APPLICATION_ID, BuildConfig.PARSE_CLIENT_KEY)
        initParseModels()

        /*
         * For more information on app security and Parse ACL:
         * https://www.parse.com/docs/android_guide#security-recommendations
         */
        val defaultACL = ParseACL()
        defaultACL.publicReadAccess = true
        defaultACL.publicWriteAccess = false

       /*
        * Default ACL is public read access, and user read/write access
        */
        ParseACL.setDefaultACL(defaultACL, true)

        ParseTwitterUtils.initialize(BuildConfig.TWITTER_CONSUMER_KEY, BuildConfig.TWITTER_CONSUMER_SECRET)

        graph = DaggerApplicationComponent.builder().androidModule(AndroidModule(this)).build()
        graph.inject(this)
    }

    private fun initParseModels() {
        ParseObject.registerSubclass(Photo::class.java)
    }
}
