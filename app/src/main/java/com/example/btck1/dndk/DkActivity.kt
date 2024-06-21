package com.example.btck1.dndk

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.btck1.R
import com.example.btck1.databinding.ActivityDkBinding
import com.example.btck1.dndk.LoginActivity
import com.google.firebase.auth.FirebaseAuth


class DkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDkBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.signupText.setOnClickListener {
            val intent=Intent(this,LoginActivity::class.java)// tương tác tới các trang
            startActivity(intent)
        }
        binding.Dkbutton.setOnClickListener {
            //tham chiếu và chuyển đổi thành 1 chuỗi
            val email = binding.emailnumber.text.toString()
            val pass = binding.password.text.toString()
            val confirmPass = binding.agpassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {
                    Toast.makeText(this,"Thành công!!", Toast.LENGTH_SHORT).show()
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this,LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Không được phép để trống", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


