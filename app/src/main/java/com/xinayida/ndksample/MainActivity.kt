package com.xinayida.ndksample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp


class MainActivity : AppCompatActivity() {

    var hour: Int = 0
    var minute: Int = 0
    var second: Int = 0
    var text by mutableStateOf("")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = text, style = TextStyle(color = Color.Red, fontSize = 22.sp))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        text = "The calculation took " + measureTicks() + " ticks"
//        text = callbackFromJNI()
//        startTicks()
    }

    override fun onPause() {
        super.onPause()
//        stopTicks()
    }

    /*
     * A function calling from JNI to update current timer
     */
    @Keep
    private fun updateTimer() {
        ++second
        if (second >= 60) {
            ++minute
            second -= 60
            if (minute >= 60) {
                ++hour
                minute -= 60
            }
        }
        runOnUiThread {
            val ticks = "$hour:$minute:$second"
            text = ticks
        }
    }

    /**
     * A native method that is implemented by the 'ndksample' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String
    external fun callbackFromJNI(): String
    external fun startTicks()
    external fun stopTicks()
    external fun measureTicks(): Long

    companion object {
        // Used to load the 'ndksample' library on application startup.
        init {
            System.loadLibrary("ndksample")
        }
    }
}