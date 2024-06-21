//
//package com.example.btck1.activity
//
//import android.os.Bundle
//import android.text.Editable
//import android.text.TextWatcher
//import android.util.Log
//import android.view.View
//import android.widget.EditText
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.Observer
//import androidx.recyclerview.widget.GridLayoutManager
//import com.example.btck1.Adapter.PopularAdapter
//import com.example.btck1.Model.ItemsModel
//import com.example.btck1.R
//import com.example.btck1.ViewModel.MainViewModel
//import com.example.btck1.databinding.ActivityFindBinding
//import com.google.firebase.database.*
//
//class FindActivity : AppCompatActivity() {
//    private val firebaseDatabase = FirebaseDatabase.getInstance()
//    private val viewModel= MainViewModel()
//
//    private val _popular = MutableLiveData<MutableList<ItemsModel>>()
//    val populars: LiveData<MutableList<ItemsModel>> = _popular
//
//
//
//
//    private lateinit var binding: ActivityFindBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityFindBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        val searchEditText = findViewById<EditText>(R.id.Search)
//        val searchText = searchEditText.text.toString()
//
//        initPopular(searchText)
//
//    }
//
//    private fun initPopular(keyword:String) {
//
//    //    binding.textView.text = find
//
//
//        var find = binding.Search.text.toString()
//        binding.progressBarPopular.visibility = View.VISIBLE
//        viewModel.populars.observe(this, Observer {
//            binding.ViewPopular.layoutManager = GridLayoutManager(this, 2)
//            binding.ViewPopular.adapter = PopularAdapter(it)
//            binding.progressBarPopular.visibility = View.GONE
//        })
//        viewModel.initfind(keyword)
//
//
//    }
//}
package com.example.btck1.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.btck1.Adapter.PopularAdapter
import com.example.btck1.R
import com.example.btck1.ViewModel.MainViewModel
import com.example.btck1.databinding.ActivityFindBinding

class FindActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFindBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindBinding.inflate(layoutInflater)// đoips chíu xml
        setContentView(binding.root)
        binding.BackBtn.setOnClickListener {
            finish()//out
        }
        // Khởi tạo ViewModel
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // Lấy tham chiếu đến EditText để tìm kiếm
        val searchEditText = findViewById<EditText>(R.id.Search)

        // Thiết lập TextWatcher cho searchEditText để theo dõi thay đổi văn bản
        //cho phép tìm kiếm ngay khi người dùng nhập văn bản vào ô tìm kiếm
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Gọi hàm initPopular với từ khóa mới khi có sự thay đổi văn bản

                initPopular(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Không có thao tác cần thực hiện khi văn bản thay đổi
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Không có thao tác cần thực hiện khi văn bản thay đổi
            }
        })

        // Khởi tạo danh sách các mục phổ biến ban đầu với từ khóa trống
        initPopular("")
    }

    private fun initPopular(keyword: String) {
        binding.progressBarPopular.visibility = View.VISIBLE
// Quan sát LiveData để lấy danh sách các mục phổ biến từ ViewModel
        viewModel.populars.observe(this, Observer { items ->
            // Thiết lập RecyclerView và Adapter để hiển thị danh sách các mục phổ biến
            binding.ViewPopular.layoutManager = GridLayoutManager(this, 2)
            binding.ViewPopular.adapter = PopularAdapter(items)
            binding.progressBarPopular.visibility = View.GONE
        })

        // Gọi hàm khởi tạo danh sách các mục phổ biến trong ViewModel
        viewModel.initfind(keyword)
    }
}

