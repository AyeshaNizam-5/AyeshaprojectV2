package com.ayeshanizam.ayeshaprojectv2.auth


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract.Constants
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ayeshanizam.ayeshaprojectv2.R
import com.ayeshanizam.ayeshaprojectv2.userdb.UserRoomDatabase
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: MaterialButton
    private lateinit var registerButton : MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Hiding title bar using code
        supportActionBar?.hide()
        // Hiding the status bar
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_login)

        //check if username exists in shared preferences
        val sharedPref = getSharedPreferences(com.ayeshanizam.ayeshaprojectv2.constants.Constants.SHARED_PREFS, Context.MODE_PRIVATE)
        val username = sharedPref.getString(com.ayeshanizam.ayeshaprojectv2.constants.Constants.USERNAME_KEY, "")
        if (!username.isNullOrEmpty()) {
            val intent = Intent(this, com.ayeshanizam.ayeshaprojectv2.MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        usernameEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            validateLogin()
        }
    }

    private fun validateLogin() {
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

        else {
            val userDb = UserRoomDatabase.getDatabase(this)
            val userDao = userDb.userDao()
            val user = userDao.getUser(username);
            if(user != null){
                if(user.password == password){
                    saveUsernameToPreferences(username)
                    val intent = Intent(this, com.ayeshanizam.ayeshaprojectv2.MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUsernameToPreferences(username: String) {
        val sharedPref = getSharedPreferences(com.ayeshanizam.ayeshaprojectv2.constants.Constants.SHARED_PREFS, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(com.ayeshanizam.ayeshaprojectv2.constants.Constants.USERNAME_KEY, username)
            apply()
        }
    }
}
