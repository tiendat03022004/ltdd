package com.example.btck1.activity

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.btck1.Model.User
import com.example.btck1.R
import com.example.btck1.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri: Uri
    private lateinit var auth: FirebaseAuth
    private lateinit var dialog:Dialog
//    private var firebaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val uid = auth.currentUser?.uid
    val id=uid.toString()


        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        binding.save.setOnClickListener {
            val firstName = binding.name.text.toString()
            val phone = binding.Phone.text.toString()
            val address = binding.address.text.toString()
            Toast.makeText( this@ProfileActivity,uid.toString(), Toast.LENGTH_SHORT).show()
                             showProgressBar()

                            val user = User(firstName, phone, address)
                       if(uid != null){
                           databaseReference.child(id).setValue(user).addOnCompleteListener() {
                               if(it.isSuccessful){
                                   uploadProfilePic()
                               }else{
                                   hideProgressBar()
                                   val tv = it.exception.toString()
                                   Toast.makeText( this@ProfileActivity,tv, Toast.LENGTH_SHORT).show()
                               }
                           }
                       }
                        }
        binding.backBtn.setOnClickListener {
            finish()
        }
                }


    fun uploadProfilePic(){
        imageUri=Uri.parse("android.resource://$packageName/${R.drawable.duy}")
        storageReference=FirebaseStorage.getInstance().getReference("Users/"+auth.currentUser?.uid)
        storageReference.putFile(imageUri).addOnCompleteListener {
            hideProgressBar()
            Toast.makeText( this@ProfileActivity,"Profile successfuly updated", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            hideProgressBar()
            Toast.makeText( this@ProfileActivity,"failed to 2 profile", Toast.LENGTH_SHORT).show()
        }
    }
    fun showProgressBar(){
        dialog= Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.show()
    }
    fun hideProgressBar(){
        dialog.dismiss()
    }
}



//package com.example.btck1.activity
//
//import android.app.Dialog
//import android.net.Uri
//import android.os.Bundle
//import android.view.Window
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.example.btck1.Model.User
//import com.example.btck1.R
//import com.example.btck1.databinding.ActivityProfileBinding
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.storage.FirebaseStorage
//import com.google.firebase.storage.StorageReference
//
//class ProfileActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityProfileBinding
//    private lateinit var storageReference: StorageReference
//    private lateinit var imageUri: Uri
//    private lateinit var auth: FirebaseAuth
//    private lateinit var dialog: Dialog
//    private val firebaseDatabase = FirebaseDatabase.getInstance()
//    private lateinit var databaseReference: DatabaseReference
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityProfileBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        auth = FirebaseAuth.getInstance()
//
//        val uid = auth.currentUser?.uid
//        if (uid == null) {
//            // Nếu không có người dùng nào đăng nhập
//            Toast.makeText(this, "Không có người dùng nào đăng nhập.", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
//        binding.save.setOnClickListener {
//            showProgressBar()
//            val users = auth.currentUser
//            if (users != null) {
//                // Kiểm tra xem thông tin người dùng đã được load chưa
//                users.reload().addOnCompleteListener { reloadTask ->
//                    if (reloadTask.isSuccessful) {
//                        // Thông tin người dùng đã được tải lại thành công
//                        val email = users.email
//                        if (email != null) {
//                            // Tiếp tục với việc xử lý dữ liệu
//                            val firstName = binding.name.text.toString()
//                            val phone = binding.Phone.text.toString()
//                            val address = binding.address.text.toString()
//                            val user = User(email, firstName, phone, address)
//                            if (uid != null) {
//                                databaseReference.child(uid).setValue(user).addOnCompleteListener {
//                                    if (it.isSuccessful) {
//                                        uploadProfilePic()
//                                    } else {
//                                        hideProgressBar()
//                                        Toast.makeText(this@ProfileActivity, "lỗi 1", Toast.LENGTH_SHORT).show()
//                                    }
//                                }
//                            }
//                        }
//                    } else {
//                        hideProgressBar()
//                        Toast.makeText(this@ProfileActivity, "Failed to reload user", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            } else {
//                hideProgressBar()
//                Toast.makeText(this, "Người dùng chưa đăng nhập.", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun uploadProfilePic() {
//        imageUri = Uri.parse("android.resource://com.example.btck1/${R.drawable.th}")
//        storageReference = FirebaseStorage.getInstance().getReference("Users/" + auth.currentUser?.uid)
//        storageReference.putFile(imageUri).addOnCompleteListener {
//            hideProgressBar()
//            Toast.makeText(this@ProfileActivity, "Profile successfully updated", Toast.LENGTH_SHORT).show()
//        }.addOnFailureListener {
//            hideProgressBar()
//            Toast.makeText(this@ProfileActivity, "lỗi 2", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun showProgressBar() {
//        dialog = Dialog(this)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setContentView(R.layout.dialog_wait)
//        dialog.show()
//    }
//
//    private fun hideProgressBar() {
//        dialog.dismiss()
//    }
//}
//
