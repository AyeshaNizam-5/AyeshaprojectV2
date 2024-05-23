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
import com.ayeshanizam.ayeshaprojectv2.userdb.UserRoomDatabase


class SettingPage : AppCompatActivity() {
    lateinit var binding: ActivitySettingPageBinding;
    lateinit var sharedpreferences: SharedPreferences
    lateinit var userdb : UserRoomDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userdb = UserRoomDatabase.getDatabase(this)
        sharedpreferences = getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE)

        binding.logoutBtn.setOnClickListener {
            val editor = sharedpreferences.edit();
            editor.clear();
            editor.apply();
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.SavePasswordBtn.setOnClickListener {
            val username = sharedpreferences.getString(Constants.USERNAME_KEY, "")
            val user = userdb.userDao().getUser(username.toString())
            if(binding.etCurrentPassword.text.toString() == "" || binding.etNewPassword.text.toString() == ""){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(user.toString().isNullOrEmpty()){
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(user?.password != binding.etCurrentPassword.text.toString()){
                Toast.makeText(this, "Current password is incorrect", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            user?.password = binding.etNewPassword.text.toString()
            userdb.userDao().updateUser(user);
            Toast.makeText(this, "Password updated", Toast.LENGTH_SHORT).show()
            binding.etCurrentPassword.text.clear()
            binding.etNewPassword.text.clear()
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
                    R.id.favouritesNavbtn -> {
                        val intent = Intent(this, ExistingSongsActivity::class.java)
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