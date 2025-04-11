package nl.frankkie.poketcghelper.android

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.projection.MediaProjectionManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import android.provider.Settings


lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>
lateinit var screenCaptureLauncher: ActivityResultLauncher<Intent>
lateinit var systemAlertWindowLauncher: ActivityResultLauncher<Intent>

class MyRemoteClientActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissionLauncher = registerForActivityResult(RequestMultiplePermissions()) { permissions ->
            val allPermissionsGranted = permissions.entries.all { it.value }
            if (allPermissionsGranted) {
                startScreenRecordingService()
            } else {
                Toast.makeText(this, "Permissions required", Toast.LENGTH_LONG).show()

                val hasPermissionFGS_MP = ContextCompat.checkSelfPermission(this, Manifest.permission.FOREGROUND_SERVICE_MEDIA_PROJECTION)
                val hasPermissionNotifications = ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                val hasPermissionSystemAlertWindow = ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW)
                val hasPermissionRecordAudio = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)

                if (hasPermissionSystemAlertWindow != PackageManager.PERMISSION_GRANTED) {
                    //request permission
                    val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
                    systemAlertWindowLauncher.launch(intent)
                } else {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName"))
                    startActivity(intent)
                }
            }
        }

        screenCaptureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // Do something with the media projection
                val intent = Intent(this, MyMediaProjectionService::class.java)
                intent.putExtra("resultCode", result.resultCode)
                intent.putExtra("result", result.data)
                startForegroundService(intent)
            } else {
                Toast.makeText(this, "Screen capture permission denied", Toast.LENGTH_LONG).show()
            }
        }

        systemAlertWindowLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.v(TAG, "Back from system alert window permission request")
            startScreenRecordingService()
        }


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
                Button(onClick = {
                    startScreenRecordingService()
                }) {
                    Text("Start Screen Recording")
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = """
                    This is the Remote Control Client.
                    You can use this to automatically control the Pokemon TCG Pocket app.
                    
                    It uses ADB to control the game, that's why a PC is required.
                    
                    You could also use this without a PC, 
                    but then you have to manually press the buttons,
                    and swipe the cards. The overlay will tell you what to do.
                    
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
                    - Press the 'Start Screen Recording' button.
                    - This app will see the contents of the Pokemon TCG Pocket app.
                    - You can now control the game from your PC.
                    
                    At some point you will have to approve screen-recording;
                    Also known as 'Media Projection' or 'Casting'.
                    
                    This helper will than use OCR (optical character recognition)
                    to read the text on the screen, to figure out which cards you have.
                    Of which actions to take.
                    
                    ---
                    
                    Using Overlay is optional.
                    
                    The Overlay will need some permissions.
                    'Display over other apps', or 'Draw over other apps'.
                    
                    When not connect to ADB, you can do the actions manually.
                    The overlay will tell you what to do.
                                                                     
                    ---                        
                    
                """.trimIndent()
                )

            }
        }
    }

    fun startOverlay() {
        //check permission
        val hasPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW)
        if (hasPermission != PackageManager.PERMISSION_GRANTED) {
            //request permission
            val intent = Intent(
                android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                android.net.Uri.parse("package:$packageName")
            )
            startActivity(intent)
        } else {
            //start overlay service
            val serviceIntent = Intent(this, MyOverlayService::class.java)
            startService(serviceIntent)
        }
    }

    fun startScreenRecordingService() {
        //check permission
        //val hasPermissionFGS_MP = ContextCompat.checkSelfPermission(this, Manifest.permission.FOREGROUND_SERVICE_MEDIA_PROJECTION)
        val hasPermissionNotifications = ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
        val hasPermissionSystemAlertWindow = ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW)
        val hasPermissionRecordAudio = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
        if (hasPermissionNotifications != PackageManager.PERMISSION_GRANTED
            //|| hasPermissionSystemAlertWindow != PackageManager.PERMISSION_GRANTED
            || hasPermissionRecordAudio != PackageManager.PERMISSION_GRANTED
        ) {
            Log.v(TAG, "Permissions not granted by the user.")

            if (Build.VERSION.SDK_INT >= 34) {
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.POST_NOTIFICATIONS,
                        Manifest.permission.SYSTEM_ALERT_WINDOW,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.FOREGROUND_SERVICE_MEDIA_PROJECTION,
                    )
                )
            } else {
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.POST_NOTIFICATIONS,
                        Manifest.permission.SYSTEM_ALERT_WINDOW,
                        Manifest.permission.RECORD_AUDIO,
                    )
                )
            }

            return
        } else {
            //permission checked
            Log.v(TAG, "startScreenRecordingService: permission granted")
            //
            val mpm = getSystemService(MediaProjectionManager::class.java)
            val mpmIntent = mpm.createScreenCaptureIntent()
            screenCaptureLauncher.launch(mpmIntent)
        }
    }

    fun stopScreenRecordingService() {
        val intent = Intent(this, MyMediaProjectionService::class.java)
        stopService(intent)
    }


    companion object {
        const val TAG = "MyRemoteClientActivity"
    }
}
