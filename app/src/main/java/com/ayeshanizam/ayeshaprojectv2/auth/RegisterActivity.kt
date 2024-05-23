package com.ayeshanizam.ayeshaprojectv2.auth

import com.ayeshanizam.ayeshaprojectv2.R

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ayeshanizam.ayeshaprojectv2.userdb.UserEntity
import com.ayeshanizam.ayeshaprojectv2.userdb.UserRoomDatabase
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope

class RegisterActivity : AppCompatActivity() {

    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var registerButton: MaterialButton
    private lateinit var loginButton: MaterialButton
    private lateinit var userdb : UserRoomDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //check if username exists in shared preferences
        val sharedPref = getSharedPreferences(com.ayeshanizam.ayeshaprojectv2.constants.Constants.SHARED_PREFS, Context.MODE_PRIVATE)
        val username = sharedPref.getString(com.ayeshanizam.ayeshaprojectv2.constants.Constants.USERNAME_KEY, "")
        if (!username.isNullOrEmpty()) {
            val intent = Intent(this, com.ayeshanizam.ayeshaprojectv2.MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        usernameEditText = findViewById(R.id.registerUsername)
        passwordEditText = findViewById(R.id.registerPassword)
        registerButton = findViewById(R.id.registerButton)
        loginButton = findViewById(R.id.loginButton)
        userdb = UserRoomDatabase.getDatabase(this)

        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        registerButton.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val username = usernameEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        if (TextUtils.isEmpty(username)) {
            usernameEditText.error = "Username is required"
            return
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.error = "Password is required"
            return
        }

        if( userdb.userDao().getUser(username)?.userName == username){
            usernameEditText.error = "Username already exists"
            return
        }
        saveUserCredentials(username, password)
        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
        // Navigate to login screen or perform any action after successful registration
    }

    private fun saveUserCredentials(username: String, password: String) {
        val userDao = userdb.userDao()
        val user = UserEntity(username, password)
        userDao.addUser(user)
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent);
    }

}
