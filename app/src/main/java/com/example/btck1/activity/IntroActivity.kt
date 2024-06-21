package com.example.btck1.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.btck1.databinding.ActivityIntroBinding
import com.example.btck1.dndk.LoginActivity

class IntroActivity : AppCompatActivity() {
    private lateinit var binding:ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottom.setOnClickListener {
            startActivity(Intent(this@IntroActivity, LoginActivity::class.java))
        }

    }
}