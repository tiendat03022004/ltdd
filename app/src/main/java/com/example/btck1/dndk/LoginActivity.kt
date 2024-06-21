package com.example.btck1.dndk

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.btck1.databinding.ActivityLoginBinding
import com.example.btck1.R
import com.example.btck1.activity.IntroActivity
import com.example.btck1.activity.MainActivity
import com.example.btck1.databinding.ActivityDkBinding
import com.example.btck1.dndk.DkActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

public class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googlelogin: GoogleSignInClient
//    private lateinit var  facebookAuthProvider:FacebookAuthProvider


    //dangnhap=gg
    private fun signIn() {
        val signInClient = googlelogin.signInIntent
        launcher.launch(signInClient)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                manageResults(task);
            }
        }

    private fun manageResults(task: Task<GoogleSignInAccount>) {
       // Lấy đối tượng GoogleSignInAccount từ kết quả
        val account: GoogleSignInAccount? = task.result
        // Xử lý thông tin của tài khoản Google ở đây
        if (account != null) {
            // Đăng nhập vào Firebase bằng credential của Google
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            // Lấy instance của FirebaseAuth
            firebaseAuth.signInWithCredential(credential).addOnCompleteListener {

                firebaseAuth = FirebaseAuth.getInstance()// truy cập các phương thức thuộc Auth
                val currentUser = firebaseAuth.currentUser?.uid
                if(currentUser != null){
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Success!!.", Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(this, IntroActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "that bai", Toast.LENGTH_SHORT).show()
                }
            }

        }}
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //dangnhap=gg
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googlelogin = GoogleSignIn.getClient(this, gso)


        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.gg.setOnClickListener {
            signIn()
        }

        binding.signupText.setOnClickListener {
            val intent = Intent(this, DkActivity::class.java)
            startActivity(intent)

        }
        binding.loginbutton.setOnClickListener {
            val email = binding.emailnumber.text.toString()
            val pass = binding.password.text.toString()


            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    firebaseAuth = FirebaseAuth.getInstance()
                    val currentUser = firebaseAuth.currentUser?.uid
                    if(currentUser != null){
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Success!!.", Toast.LENGTH_SHORT).show()
                    } else {
//                        Toast.makeText(this, "that bai", Toast.LENGTH_SHORT).show()
                    }
                }
                }
            } else {
//                Toast.makeText(this, "Các trường trống không được phép!!", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "mật khẩu vượt quá kí tự!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}


