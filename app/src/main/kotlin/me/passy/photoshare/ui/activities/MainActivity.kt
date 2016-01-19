package me.passy.photoshare.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.parse.ParseUser
import com.parse.ui.ParseLoginBuilder
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.warn


class MainActivity : Activity(), AnkoLogger {

    val LOGIN_CODE = 0

    private fun redirectOnLogin() {
        val user = ParseUser.getCurrentUser()
        if (user != null && user.isAuthenticated) {
            warn { "Authenticated. "}
            startActivity<StreamActivity>()
            finish()
        } else {
            warn { "Not authenticated. Get off." }
            startActivityForResult(ParseLoginBuilder(this).build(), LOGIN_CODE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        redirectOnLogin()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == LOGIN_CODE) {
            redirectOnLogin()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
