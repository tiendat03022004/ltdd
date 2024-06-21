package com.example.btck1.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.btck1.Model.BrandModel
import com.example.btck1.Model.ItemsModel
import com.example.btck1.Model.SliderModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainViewModel:ViewModel() {
    // Khởi tạo một thể hiện của cơ sở dữ liệu Firebase
// getInstance() trả về thể hiện duy nhất của FirebaseDatabase để sử dụng trong toàn bộ ứng dụng
// Tham chiếu này được sử dụng để truy cập và tương tác với cơ sở dữ liệu Firebas
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    //LiveData cho danh sách banner
    private val banner = MutableLiveData<List<SliderModel>>()
    val banners: LiveData<List<SliderModel>> = banner

    private val _brand = MutableLiveData<MutableList<BrandModel>>()
    val brands: LiveData<MutableList<BrandModel>> = _brand

    private val _popular = MutableLiveData<MutableList<ItemsModel>>()
    val populars: LiveData<MutableList<ItemsModel>> = _popular



    fun loadBanners() {
        val Ref = firebaseDatabase.getReference("Banner")
        Ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<SliderModel>()
                // Duyệt qua mỗi nút con (child node) trong "Category"
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(SliderModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                banner.value = lists
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    fun loadBrand() {
        val Ref = firebaseDatabase.getReference("Category")
        Ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<BrandModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(BrandModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                _brand.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun loadPupolar() {
        val Ref = firebaseDatabase.getReference("Items")
        Ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ItemsModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(ItemsModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                _popular.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    fun initfind(keyword:String) {
        val ref = firebaseDatabase.getReference("Items")
        ref.orderByChild("title").startAt(keyword).endAt(keyword+ "\uF8FF")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val lists = mutableListOf<ItemsModel>()
                    for (childSnapshot in snapshot.children) {
                        val item = childSnapshot.getValue(ItemsModel::class.java)
                        if (item != null) {
                            lists.add(item)
                        }
                    }
                    _popular.value = lists
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("TAG", "initfind:onCancelled", error.toException())
                }
            })
    }
}
