package com.example.registro

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.fragment.app.FragmentTransaction
import com.example.registro.R.id.Linefra

import com.google.android.material.bottomnavigation.BottomNavigationView


import kotlin.collections.ArrayList

class presesentacion : AppCompatActivity() {

    lateinit var DeliversFragment: DeliversFragment
    lateinit var HomeFragment: HomeFragment
    lateinit var ProfileFragment:ProfileFragment
    lateinit var SettingsFragment:SettingsFragment
    lateinit var ShoppFragment:ShoppFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presesentacion)

        val bottomNavigation : BottomNavigationView = findViewById(R.id.bottom_navigation)

        HomeFragment= HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(Linefra,HomeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        bottomNavigation.setOnNavigationItemSelectedListener { item ->

            when (item.itemId){

                R.id.delivers->{
                    DeliversFragment= DeliversFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(Linefra, DeliversFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.home->{
                    HomeFragment= HomeFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(Linefra, HomeFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.profile->{
                    ProfileFragment= ProfileFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(Linefra, ProfileFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.shopp->{
                    ShoppFragment= ShoppFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(Linefra, ShoppFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.settings->{
                    SettingsFragment= SettingsFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(Linefra, SettingsFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }
            true
        }


    }


}
