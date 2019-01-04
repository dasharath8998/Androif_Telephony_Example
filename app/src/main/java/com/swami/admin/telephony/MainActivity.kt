package com.swami.admin.telephony

import android.Manifest
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.telephony.SmsManager
import kotlinx.android.synthetic.main.activity_main.*

var mno:String? = null

class MainActivity : AppCompatActivity() {

    var sms_permission: Boolean = false
    var call_permission: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var sms_status = ContextCompat.checkSelfPermission(this@MainActivity,Manifest.permission.SEND_SMS)
        var call_status = ContextCompat.checkSelfPermission(this@MainActivity,Manifest.permission.CALL_PHONE)

        if(sms_status == PackageManager.PERMISSION_GRANTED){
            sms_permission = true;
        }

        if(call_status == PackageManager.PERMISSION_GRANTED){
            call_permission = true
        }

        if(sms_status != PackageManager.PERMISSION_GRANTED || call_status != PackageManager.PERMISSION_GRANTED){
            requestPermission()
        }else if(sms_status != PackageManager.PERMISSION_GRANTED){
            requestSMS()
        }else if(call_status != PackageManager.PERMISSION_GRANTED){
            requestCall()
        }

        btnSMS.setOnClickListener {

            if(sms_permission) {
                mno = etNumber.text.toString()
                var numbers = etNumber.text.toString().split(",")
                for(number in numbers) {
                    var sIntent = Intent(this@MainActivity, sentSMS::class.java)
                    var dIntent = Intent(this@MainActivity, deliveredSMS::class.java)

                    var psIntent = PendingIntent.getActivity(this@MainActivity, 0, sIntent, 0)
                    var pdIntent = PendingIntent.getActivity(this@MainActivity, 0, dIntent, 0)
                    var sManager = SmsManager.getDefault()
                    sManager.sendTextMessage(etNumber.text.toString(), null, etMessage.text.toString(), psIntent, pdIntent)
                }
                }else{
                requestSMS()
            }
        }

        btnCall.setOnClickListener {
            if(call_permission) {
                var i = Intent()
                i.action = Intent.ACTION_CALL
                i.data = Uri.parse("tel: ${etNumber.text}")
                startActivity(i)
            }else{
                requestCall()
            }
        }

        btnAttach.setOnClickListener {
            var aDialog = AlertDialog.Builder(this@MainActivity)
            aDialog.setTitle("Message")
            aDialog.setMessage("Plese Select From Below Source")
            aDialog.setPositiveButton("Camera",object:DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    var i = Intent("android.media.action.IMAGE_CAPTURE")
                    startActivityForResult(i,1)
                }
            })
            aDialog.setNegativeButton("File",object:DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    var i = Intent()
                    i.action = Intent.ACTION_GET_CONTENT
                    i.type = "*/*"
                    startActivityForResult(i,2)
                }
            })
            aDialog.setCancelable(true)
            aDialog.show()

        }

    }

    fun requestPermission(){
        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.SEND_SMS,Manifest.permission.CALL_PHONE),1)
    }
    fun requestSMS(){
        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.SEND_SMS),2)
    }
    fun requestCall(){
        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.CALL_PHONE),3)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1 -> {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    sms_permission = true
                }
                if(grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    call_permission = true
                }
            }
            2 -> {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    sms_permission = true
                }
            }
            3 -> {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    call_permission = true
                }
            }
        }
    }
}
