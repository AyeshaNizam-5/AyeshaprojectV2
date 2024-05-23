package com.ayeshanizam.ayeshaprojectv2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ayeshanizam.ayeshaprojectv2.R
import com.ayeshanizam.ayeshaprojectv2.auth.LoginActivity
import com.ayeshanizam.ayeshaprojectv2.constants.Constants
import com.ayeshanizam.ayeshaprojectv2.databinding.ActivitySettingPageBinding



class SettingPage : AppCompatActivity() {
    lateinit var binding: ActivitySettingPageBinding;
    lateinit var sharedpreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedpreferences = getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE);
        binding.SaveUserNameBtn.setOnClickListener {
            val newUsername = binding.etUserName.text.toString()
            val usernameSize =binding.etUserName.text.toString().length
            if(newUsername != null && usernameSize > 5 ){
                val editor = sharedpreferences.edit();
                editor.putString(Constants.USERNAME_KEY, binding.etUserName.text.toString());
                editor.apply();

                Toast.makeText(this,"user name has been updated", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this,"you need to enter a valid new username", Toast.LENGTH_LONG).show()
            }

        }

        binding.logoutBtn.setOnClickListener {
            val editor = sharedpreferences.edit();
            editor.clear();
            editor.apply();
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.SavePasswordBtn.setOnClickListener {
            val currentPass = binding.etCurrentPassword.text.toString()
            val usernameSize =binding.etUserName.text.toString().length
            val editor = sharedpreferences.edit();
        }


        // this is for accessing the bottom navigation bar and changing pages
        binding.bottomNavigation.setOnItemSelectedListener {
            try {
                when (it.itemId) {
                    R.id.settingsNavbtn -> {
                        val intent = Intent(this, SettingPage::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.homeNavbtn -> {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        true
                    }
                    else -> {
                        true
                    }
                }
            } catch (e: Exception) {
                throw e
            }
        }

    }


}