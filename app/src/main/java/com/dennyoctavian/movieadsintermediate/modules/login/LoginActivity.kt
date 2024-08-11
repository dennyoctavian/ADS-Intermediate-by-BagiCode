package com.dennyoctavian.movieadsintermediate.modules.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dennyoctavian.movieadsintermediate.R
import com.dennyoctavian.movieadsintermediate.utils.UserPreference

class LoginActivity : AppCompatActivity() {
    private lateinit var username: EditText
    private lateinit var loginButton: Button
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        username = findViewById(R.id.etUsername)
        loginButton = findViewById(R.id.loginButton)
        userPreference = UserPreference(this)

        loginButton.setOnClickListener {
            if (username.text.isNullOrEmpty()) {
                username.error = "Please input username"
            } else {
                userPreference.setNameUser(username.text.toString())
                userPreference.setStatusUser(true)
                val result = Intent()
                setResult(Activity.RESULT_OK, result)
                finish()
            }
        }
    }
}