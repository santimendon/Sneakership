package com.smendon.sneakersapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.branch.referral.Branch

@HiltAndroidApp
class SneakersApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Branch logging for debugging
        Branch.enableTestMode()

        // Branch object initialization
        Branch.getAutoInstance(this)
        //Will set identity with adid later
        Branch.getInstance().setIdentity("123")
    }
}