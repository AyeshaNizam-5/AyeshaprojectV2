package com.ayeshanizam.ayeshaprojectv2

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ayeshanizam.ayeshaprojectv2.databinding.FragmentBottomBinding



class BottomFragment : Fragment() {
    private lateinit var binding: FragmentBottomBinding
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBottomBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavigation.setOnItemSelectedListener {
            try {
                when (it.itemId) {
                    R.id.homeNavbtn -> {
                        startActivity(Intent(activity, MainActivity::class.java))
                        true
                    }

                    R.id.favouritesNavbtn -> {
                        startActivity(Intent(activity, ExistingSongsActivity::class.java))
                        true
                    }
                    R.id.settingsNavbtn -> {
                        startActivity(Intent(activity, SettingPage::class.java))
                        true
                    }
                    else -> {
                        false
                    }
                }
            } catch (e: Exception) {
                throw e
            }
        }
    }

}