package com.smendon.sneakersapp.ui.sneakerdetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.smendon.sneakersapp.ui.main.BRANCH_DEEPLINK_ID
import com.smendon.sneakersapp.ui.theme.SneakersAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SneakerViewActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val productId = intent.getStringExtra(BRANCH_DEEPLINK_ID)?.toInt()

        setContent {
            SneakersAppTheme {
                if (productId != null) {
                    SneakerDetail(productId, sneakerDetailViewModel = hiltViewModel())
                }
            }
        }
    }
}