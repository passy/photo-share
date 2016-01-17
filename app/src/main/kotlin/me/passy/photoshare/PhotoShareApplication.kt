package me.passy.photoshare

import android.support.multidex.MultiDexApplication
import com.parse.Parse
import com.parse.ParseTwitterUtils

public class PhotoShareApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

       Parse.enableLocalDatastore(this);
        Parse.initialize(this, BuildConfig.PARSE_APPLICATION_ID, BuildConfig.PARSE_CLIENT_KEY);
        ParseTwitterUtils.initialize(BuildConfig.PARSE_APPLICATION_ID, BuildConfig.PARSE_CLIENT_KEY)
    }
}
