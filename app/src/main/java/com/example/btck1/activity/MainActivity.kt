package com.example.btck1.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.btck1.Adapter.BrandAdapter
import com.example.btck1.Adapter.PopularAdapter
import com.example.btck1.Model.SliderModel
import com.example.btck1.Adapter.SliderAdapter
import com.example.btck1.ViewModel.MainViewModel
import com.example.btck1.databinding.ActivityMainBinding
import com.example.btck1.maps.MapsActivity

class MainActivity : BaseActivity() {

    private val viewModel=MainViewModel()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBanner()
        initBrand()
        initPopular()
        initBottomMenu()
        findproduct()
        locationShop()
        profile()

    }
    private fun profile(){
        binding.profile.setOnClickListener { startActivity(Intent(this@MainActivity,MainProfileActivity::class.java)) }
    }
    private fun locationShop(){
        binding.addressshop.setOnClickListener { startActivity(Intent(this@MainActivity,MapsActivity::class.java)) }
    }

    private fun findproduct() {
        binding.find.setOnClickListener { startActivity(Intent(this@MainActivity,FindActivity::class.java)) }
    }


    private fun initBottomMenu() {
        binding.cartBtn.setOnClickListener { startActivity(Intent(this@MainActivity,CartActivity::class.java)) }
    }

    private fun initBanner() {
        binding.progressBarBanner.visibility= View.VISIBLE
        viewModel.banners.observe(this, Observer {items->
            banners(items)
            binding.progressBarBanner.visibility=View.GONE
        })
        viewModel.loadBanners()
    }

    private fun banners(images:List<SliderModel>){
        binding.ViewpageSlider.adapter= SliderAdapter(images,binding.ViewpageSlider)
        binding.ViewpageSlider.clipToPadding=false
        binding.ViewpageSlider.clipChildren=false
        binding.ViewpageSlider.offscreenPageLimit=3
        binding.ViewpageSlider.getChildAt(0).overScrollMode=RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }
        binding.ViewpageSlider.setPageTransformer(compositePageTransformer)

        if(images.size>1){
            binding.Dotlndicator.visibility=View.VISIBLE
            binding.Dotlndicator.attachTo(binding.ViewpageSlider)
        }
    }

    private fun initBrand(){
        binding.progressBarBrand.visibility=View.VISIBLE
        viewModel.brands.observe(this, Observer {
            binding.ViewBrand.layoutManager=LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
            binding.ViewBrand.adapter=BrandAdapter(it)
            binding.progressBarBrand.visibility=View.GONE
        })
        viewModel.loadBrand()
    }


    private fun initPopular(){
        binding.progressBarPopular.visibility=View.VISIBLE
        viewModel.populars.observe(this, Observer {
            binding.ViewPopular.layoutManager=GridLayoutManager(this@MainActivity,2)
            binding.ViewPopular.adapter=PopularAdapter(it)
            binding.progressBarPopular.visibility=View.GONE
        })
        viewModel.loadPupolar()
    }
}

