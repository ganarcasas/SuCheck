package com.su.checker

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var statusTextView: TextView
    private lateinit var checkButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusTextView = findViewById(R.id.statusTextView)
        checkButton = findViewById(R.id.checkButton)

        checkButton.setOnClickListener {
            checkRootStatus()
        }
    }

    private fun checkRootStatus() {
        val isRooted = isDeviceRooted()
        statusTextView.text = getString(R.string.checking)
        
        if (isRooted) {
            statusTextView.text = getString(R.string.rooted)
            statusTextView.setTextColor(resources.getColor(android.R.color.holo_green_light))
        } else {
            statusTextView.text = getString(R.string.not_rooted)
            statusTextView.setTextColor(resources.getColor(android.R.color.holo_red_light))
        }
    }

    private fun isDeviceRooted(): Boolean {
        val superUserFile = File("/system/xbin/su")
        return superUserFile.exists() || canExecuteCommand("which su") || canExecuteCommand("whereis su")
    }

    private fun canExecuteCommand(command: String): Boolean {
        return try {
            val process = Runtime.getRuntime().exec(command)
            val reader = process.inputStream.bufferedReader()
            val output = reader.readText()
            output.isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }
}
