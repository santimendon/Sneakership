package com.smendon.sneakersapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.smendon.sneakersapp.ui.sneakerdetails.SneakerViewActivity
import com.smendon.sneakersapp.ui.theme.SneakersAppTheme
import dagger.hilt.android.AndroidEntryPoint
import io.branch.referral.Branch

const val BRANCH_DEEPLINK_ID = "branch_deeplink_id"
const val ANDROID_DEEPLINK_PATH = "android_deeplink_path"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val TAG = "BRANCHSDK_LOG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            SneakersAppTheme {
                SneakersMainScreen()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Branch.sessionBuilder(this).withCallback { branchUniversalObject, linkProperties, error ->
            if (error != null) {
                Log.e(TAG, "branch init failed. Caused by -" + error.message)
            } else {
                Log.i(TAG, "branch init complete!")
                if (branchUniversalObject != null) {
                    Log.i(TAG, "title " + branchUniversalObject.title)
                    Log.i(TAG, "CanonicalIdentifier " + branchUniversalObject.canonicalIdentifier)
                    Log.i(TAG, "metadata " + branchUniversalObject.contentMetadata.convertToJson())
                }
                if (branchUniversalObject?.contentMetadata?.customMetadata?.containsKey(ANDROID_DEEPLINK_PATH) == true) {
                    val productId = branchUniversalObject.contentMetadata.customMetadata.get(ANDROID_DEEPLINK_PATH)
                    Log.i(TAG, "SneakerId= $productId")
                    productId?.let {
                        val intent = Intent(this@MainActivity, SneakerViewActivity::class.java)
                        intent.putExtra(
                            BRANCH_DEEPLINK_ID,
                            productId
                        )
                        startActivity(intent)
                    }
                }
                if (linkProperties != null) {
                    Log.i(TAG, "Channel " + linkProperties.channel)
                    Log.i(TAG, "control params " + linkProperties.controlParams)
                }
            }
        }.withData(this.intent.data).init()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Branch.sessionBuilder(this).withCallback { referringParams, error ->
            if (error != null) {
                Log.e(TAG, error.message)
            } else if (referringParams != null) {
                Log.i(TAG, referringParams.toString())
            }
        }.reInit()
    }
}