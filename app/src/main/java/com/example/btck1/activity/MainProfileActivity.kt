package com.example.btck1.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.btck1.Model.User
import com.example.btck1.R
import com.example.btck1.databinding.ActivityMainProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MainProfileActivity : AppCompatActivity() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private lateinit var binding: ActivityMainProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val uid = auth.currentUser?.uid

        if (uid != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid)

            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                // Bỏ qua cảnh báo từ Android Lint liên quan đến việc sử dụng một `MutableLiveData`
                // mà không kiểm tra xem nó có bằng `null` trước khi sử dụng.
                @SuppressLint("NullSafeMutableLiveData")

                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    if (user != null) {
                        _user.value = user
                    } else {
                        Toast.makeText(this@MainProfileActivity, "User data is null", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@MainProfileActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "User ID is null", Toast.LENGTH_SHORT).show()
        }
        // Lắng nghe sự thay đổi trong LiveData _user để cập nhật giao diện người dùng
        user.observe(this, Observer { user ->
            binding.name.text = user.firstName
            binding.Phone.text = user.phone
            binding.address.text = user.address
        })

        binding.Update.setOnClickListener{
            startActivity(Intent(this@MainProfileActivity,ProfileActivity::class.java))
        }
        binding.BackBtn.setOnClickListener {
          finish()
        }
    }

    // Uncomment and use these functions as needed
    /*
    fun uploadProfilePic() {
        imageUri = Uri.parse("android.resource://$packageName/${R.drawable.th}")
        storageReference = FirebaseStorage.getInstance().getReference("Users/" + auth.currentUser?.uid)
        storageReference.putFile(imageUri).addOnCompleteListener {
            hideProgressBar()
            Toast.makeText(this, "Profile successfully updated", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            hideProgressBar()
            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
        }
    }

    fun showProgressBar() {
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.show()
    }

    fun hideProgressBar() {
        dialog.dismiss()
    }
    */
}
