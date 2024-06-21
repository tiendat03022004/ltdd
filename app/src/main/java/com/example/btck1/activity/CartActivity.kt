package com.example.btck1.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.btck1.Adapter.CartAdapter
import com.example.btck1.R
import com.example.btck1.databinding.ActivityCartBinding
import com.example.project1762.Helper.ChangeNumberItemsListener
import com.example.project1762.Helper.ManagmentCart

class CartActivity : BaseActivity() {
    private lateinit var binding:ActivityCartBinding
    private lateinit var managmentCart: ManagmentCart
    private var tax:Double=0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart= ManagmentCart(this)

        setVariable()
        initCartList()
        calculateCart()
    }

    private fun initCartList() {
        binding.viewCart.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.viewCart.adapter=CartAdapter(managmentCart.getListCart(),this,object :ChangeNumberItemsListener{
            override fun onChanged() {
                calculateCart()
            }

        })
        with(binding){
            emptyTxt.visibility=if(managmentCart.getListCart().isEmpty()) View.VISIBLE else View.GONE
            scrollView2.visibility=if(managmentCart.getListCart().isEmpty()) View.GONE else View.VISIBLE
        }
    }
    private fun calculateCart(){
        val percentTax=0.1//phan tram thue
        val delivery=10.0//phi van chuyen
        tax=Math.round((managmentCart.getTotalFee()*percentTax)*100)/100.0// thue

        val itemTotal=Math.round(managmentCart.getTotalFee()*100)/100 //tong san pham
        val total= tax+ itemTotal+ delivery//tong tien

        with(binding){
            totalFeeTxt.text="$$itemTotal"
            taxTxt.text="$$tax"
            deliveryTxt.text="$$delivery"
            totalTxt.text="$$total"
        }
    }

    private fun setVariable() {
        binding.BackBtn.setOnClickListener { finish() }
    }
}