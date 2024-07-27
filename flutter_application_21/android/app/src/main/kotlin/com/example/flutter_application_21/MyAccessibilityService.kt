package com.example.flutter_application_21

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import io.flutter.plugin.common.MethodChannel
import io.flutter.embedding.engine.FlutterEngine


class MyAccessibilityService : AccessibilityService() {
    private lateinit var methodChannel: MethodChannel

    override fun onServiceConnected() {
        super.onServiceConnected()
        methodChannel = MethodChannel(FlutterEngine(this).dartExecutor.binaryMessenger, "com.example.flutter_application_21/accessibility")
        Log.d("MouseTrackerService", "Accessibility Service Connected")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) {
            Log.d("MouseTrackerService", "Received null AccessibilityEvent")
            return
        }
    
        val packageName = event.packageName?.toString() ?: "Unknown Package"
        val className = event.className?.toString() ?: "Unknown Class"
        val text = event.text?.toString() ?: "No Text"
    
        Log.d("MouseTrackerService", "Event Type: ${event.eventType}, Package: $packageName, Class: $className, Text: $text")
    
        when (event.eventType) {
            AccessibilityEvent.TYPE_VIEW_CLICKED -> {
                Log.d("MouseTrackerService", "View clicked in package: $packageName, class: $className, text: $text")
            }
           // AccessibilityEvent.TYPE_VIEW_FOCUSED -> {
             //   Log.d("MouseTrackerService", "View focused in package: $packageName, class: $className")
           // }
          //  AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> {
            //    Log.d("MouseTrackerService", "Text changed in package: $packageName, class: $className, new text: $text")
           // }
            AccessibilityEvent.TYPE_VIEW_SCROLLED -> {               
                Log.d("MouseTrackerService", "View scrolled in package: $packageName, class: $className")               
            }
            else -> {
               
            }
        }
    }

    override fun onInterrupt() {
        Log.d("MouseTrackerService", "Accessibility Service Interrupted")
    }
}
