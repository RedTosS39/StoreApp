package com.example.storeapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyBroadcastReceivers : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        when (intent?.action) {

            ACTION_ADD_ITEM-> {
                val extras = intent.getBundleExtra(KEY_BROADCAST_BUNDLE)
                val name = extras?.getString(KEY_BROADCAST_NAME)
                val enabled = extras?.getBoolean(KEY_BROADCAST_ENABLED)

                val status =  if(enabled == true) {
                    ITEM_ADD
                } else {
                    ITEM_DELETE
                }
                Toast.makeText(context, "$name $status", Toast.LENGTH_SHORT).show()
            }
        }
    }


    companion object {
        private const val STATE = "STATE"
        const val KEY_BROADCAST_BUNDLE = "KEY_BROADCAST_BUNDLE"
        const val ACTION_ADD_ITEM = "ACTION_ADD_ITEM"
        const val KEY_BROADCAST_NAME = "KEY_BROADCAST_NAME"
        const val KEY_BROADCAST_ENABLED = "KEY_BROADCAST_ENABLED"

        private const val ITEM_ADD = "added to bucket"
        private const val ITEM_DELETE="deleted from bucket"
    }
}