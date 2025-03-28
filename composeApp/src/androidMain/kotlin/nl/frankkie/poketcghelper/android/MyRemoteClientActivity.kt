package nl.frankkie.poketcghelper.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.jvm.java

class MyRemoteClientActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Remote Control Client")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    startOverlay()
                }) {
                    Text("Start Overlay")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = """
                    This is the Remote Control Client.
                    You can use this to automatically control the Pokemon TCG Pocket app.
                    
                    It uses ADB to control the game, that's why a PC is required.
                    
                    You could also use this without a PC, 
                    but then you have to manually press the buttons,
                    and swipe the cards.
                    
                    Remote control can:
                    - Automatically scan cards in the game. 
                    To synchronize your collection with the Helper-app.
                           
                    - Automatically do the daily tasks.
                    (Like opening the daily booster pack, or claiming the daily reward)
                    
                    ---
                    
                    How to:
                    - Connect your phone to your PC with a USB cable.
                    - Enable USB debugging on your phone.
                    - Start the Remote Control Host on your PC.
                    - Start the Remote Control Client on your phone.
                    - Press the 'Start Overlay' button.
                    - The overlay will show up on top of the Pokemon TCG Pocket app.
                    - You can now control the game from your PC.
                    
                    The Overlay will need some permissions.
                    'Display over other apps', or 'Draw over other apps'.
                                       
                    At some point you will have to approve screen-recording;
                    Also known as 'Media Projection' or 'Casting'.
                    
                    This helper will than use OCR (optical character recognition)
                    to read the text on the screen, to figure out which cards you have.
                    Of which actions to take.
                    
                    ---                        
                    
                """.trimIndent()
                )

            }
        }
    }

    fun startOverlay() {
        val serviceIntent = Intent(this, MyOverlayService::class.java)
        startService(serviceIntent)
    }
}
