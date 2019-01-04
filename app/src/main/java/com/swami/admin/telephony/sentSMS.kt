package com.swami.admin.telephony

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sent_sms.*

class sentSMS : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sent_sms)
        tvDel.setText("Message Sent To: $mno")
    }
}
