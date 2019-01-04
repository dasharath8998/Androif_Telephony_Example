package com.swami.admin.telephony

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_delivered_sms.*

class deliveredSMS : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivered_sms)
        tvSent.setText("Message Delivered To: $mno")
    }
}
