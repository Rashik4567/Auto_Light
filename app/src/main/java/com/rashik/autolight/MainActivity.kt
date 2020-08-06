package com.rashik.autolight

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toolbar


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)

    private val TAG: String? = "MyActivity"
    private var bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val toolBar = findViewById<Toolbar>(R.id.toolbar)
//        toolBar.title = "Auto Light"
        enableBluetooth()
    }

    private val autoBroadcastReceiver = object : BroadcastReceiver() {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onReceive(context: Context, intent: Intent) {
            val action: String? = intent.action
            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {

                when (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)) {
                    BluetoothAdapter.STATE_ON -> Log.d(TAG, "onReceive: State on")
                    BluetoothAdapter.STATE_OFF -> Log.d(TAG, "onReceive: Steate off")
                    BluetoothAdapter.STATE_TURNING_ON -> Log.d(TAG, "onReceive: State turning on")
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public override fun onDestroy() {
        Log.d(TAG, "onDestroy: Called")
        super.onDestroy()
        unregisterReceiver(autoBroadcastReceiver)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun enableBluetooth() {
        if (!bluetoothAdapter.isEnabled()) {
            val enableautoIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivity(enableautoIntent)

            val autoIntent = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
            registerReceiver(autoBroadcastReceiver, autoIntent)
        }
    }

}