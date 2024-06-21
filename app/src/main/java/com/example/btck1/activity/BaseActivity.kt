package com.example.btck1.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.btck1.R

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Đặt cờ FLAG_LAYOUT_NO_LIMITS cho cửa sổ hiện tại
        // Cờ này cho phép giao diện người dùng mở rộng ra toàn bộ màn hình
        // bao gồm cả các khu vực bị chiếm bởi thanh trạng thái và thanh điều hướng
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}