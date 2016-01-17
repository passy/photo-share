package me.passy.photoshare

import android.support.multidex.MultiDexApplication
import com.parse.ParseTwitterUtils

public class PhotoShareApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        ParseTwitterUtils.initialize(BuildConfig.PARSE_APPLICATION_ID, BuildConfig.PARSE_CLIENT_KEY)
    }
}
