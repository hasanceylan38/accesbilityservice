package com.example.flutter_application_21

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Rational
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import android.view.accessibility.AccessibilityManager
import android.app.PictureInPictureParams


class MainActivity : FlutterActivity() {
    private val CHANNEL = "com.example.flutter_application_21/accessibility"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
            .setMethodCallHandler { call, result ->
                when (call.method) {
                    "startService" -> startAccessibilityService(result)
                    "stopService" -> stopAccessibilityService(result)
                    "enterPipMode" -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            enterPipMode()
                            result.success(null)
                        } else {
                            result.error("UNAVAILABLE", "PiP mode is not supported on this Android version.", null)
                        }
                    }
                    else -> result.notImplemented()
                }
            }
    }

    private fun startAccessibilityService(result: MethodChannel.Result) {
        showAccessibilityServiceDisabledDialog("Service Enabled","etkinleştirin")
        result.success("Accessibility Service Started")
    }

    private fun stopAccessibilityService(result: MethodChannel.Result) {
       showAccessibilityServiceDisabledDialog("Service Disabled","devre dışı bırakın")
    }

    private fun enterPipMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val aspectRatio = Rational(16, 9) 
            val pipBuilder = PictureInPictureParams.Builder()
            pipBuilder.setAspectRatio(aspectRatio)
            enterPictureInPictureMode(pipBuilder.build())
        }
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            enterPipMode()
        }
    }

    private fun showAccessibilityServiceDisabledDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage("Lütfen Erişilebilirlik Hizmetini ayarlardan manuel olarak $message.")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .setOnDismissListener {
                val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                startActivity(intent)
            }
            .show()
    }
}
