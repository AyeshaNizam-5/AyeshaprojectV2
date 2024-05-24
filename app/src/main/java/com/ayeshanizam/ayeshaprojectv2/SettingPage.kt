package com.ayeshanizam.ayeshaprojectv2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ayeshanizam.ayeshaprojectv2.R
import com.ayeshanizam.ayeshaprojectv2.auth.LoginActivity
import com.ayeshanizam.ayeshaprojectv2.constants.Constants
import com.ayeshanizam.ayeshaprojectv2.databinding.ActivitySettingPageBinding
import com.ayeshanizam.ayeshaprojectv2.userdb.UserRoomDatabase


class SettingPage : AppCompatActivity() {
    lateinit var binding: ActivitySettingPageBinding;
    lateinit var sharedpreferences: SharedPreferences
    lateinit var userdb : UserRoomDatabase
    lateinit var bottomFragment : BottomFragment
    lateinit var fm: FragmentManager
    lateinit var ft: FragmentTransaction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Hiding title bar using code
        supportActionBar?.hide()
        // Hiding the status bar
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
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
            if (binding.etCurrentPassword.text.toString() == "" || binding.etNewPassword.text.toString() == "") {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (user.toString().isNullOrEmpty()) {
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (user?.password != binding.etCurrentPassword.text.toString()) {
                Toast.makeText(this, "Current password is incorrect", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            user?.password = binding.etNewPassword.text.toString()
            userdb.userDao().updateUser(user);
            Toast.makeText(this, "Password updated", Toast.LENGTH_SHORT).show()
            binding.etCurrentPassword.text.clear()
            binding.etNewPassword.text.clear()
        }


    }



}