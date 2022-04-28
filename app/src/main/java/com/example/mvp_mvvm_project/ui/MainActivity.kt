package com.example.mvp_mvvm_project.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mvp_mvvm_project.R
import com.example.mvp_mvvm_project.databinding.ActivityMainBinding
import com.example.mvp_mvvm_project.ui.login.LoginFragment

class MainActivity : AppCompatActivity(), NavigationActivity {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            setHomePage()
        }
    }

    override fun navigationTo(fragment: Fragment, withTransaction: Boolean) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.push_up_out,
                R.anim.push_up_in,
                R.anim.slide_out
            )
            .replace(R.id.container_layout, fragment)

        if (withTransaction) {
            transaction.addToBackStack("Transaction")
        }

        transaction.commit()

    }

    private fun setHomePage(){
        navigationTo(LoginFragment.newInstance())
    }
}