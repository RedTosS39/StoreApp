package com.example.storeapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyBroadcastReceivers : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        when (intent?.action) {
            Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                val state = intent.getBooleanExtra(STATE, false)
                Toast.makeText(context,
                    "ACTION_AIRPLANE_MODE_CHANGED_TO: $state",
                    Toast.LENGTH_SHORT).show()
            }
            Intent.ACTION_BATTERY_LOW -> {
                Toast.makeText(context, "ACTION_BATTERY_LOW", Toast.LENGTH_SHORT).show()
            }

            ACTION_ADD_ITEM -> {
                val state = intent.getBooleanExtra(EXTRA_ADD, false)
                Toast.makeText(context, "$state", Toast.LENGTH_SHORT).show()
                val name = intent.getStringExtra(EXTRA_NAME) ?: "Name"
                when(state) {
                    true -> {Toast.makeText(context, "$name $ADD_TO_BUCKET", Toast.LENGTH_SHORT).show()}
                    false -> {Toast.makeText(context, "$name $DELETE_TO_BUCKET", Toast.LENGTH_SHORT).show()}
                }

            }
        }
    }


    companion object {
        private const val STATE = "STATE"

        private const val ADD_TO_BUCKET = "added to bucket"
        private const val DELETE_TO_BUCKET = "deleted from bucket"
        const val ACTION_ADD_ITEM = "ITEM"
        const val EXTRA_ADD = "ITEM"
        const val EXTRA_NAME = "ITEM"
    }
}