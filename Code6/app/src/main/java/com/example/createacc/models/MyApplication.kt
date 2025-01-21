package com.example.createacc.models

import android.app.Application
import com.example.createacc.CredentialsManager

class MyApplication : Application() {
    val credentialsManager: CredentialsManager by lazy {
        CredentialsManager(this)
    }
}
