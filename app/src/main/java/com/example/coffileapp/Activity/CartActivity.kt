package com.example.coffileapp.Activity

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffileapp.Adapter.CartAdapter
import com.example.coffileapp.Helper.ChangeNumberItemsListener
import com.example.coffileapp.Helper.ManagmentCart
import com.example.coffileapp.R
import com.example.coffileapp.databinding.ActivityCartBinding
import com.example.coffileapp.databinding.ViewholderCartBinding

class CartActivity : AppCompatActivity() {

    lateinit var binding: ActivityCartBinding
    lateinit var managementCart: ManagmentCart
    private var tax: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementCart= ManagmentCart(this)

        calculateCart()
        setVariable()
        initCartList()

    }

    private fun initCartList() {
        binding.apply {
            listView.layoutManager=
                LinearLayoutManager(this@CartActivity, LinearLayoutManager.VERTICAL, false)
            listView.adapter= CartAdapter(
                managementCart.getListCart(),
                this@CartActivity,
                object : ChangeNumberItemsListener{
                    override fun onChanged() {
                        calculateCart()
                    }
                }
            )
        }
    }

    private fun setVariable() {
        binding.backBtn.setOnClickListener { finish() }
    }


    private fun calculateCart() {
        val percentTax=0.02
        val delivery=15
        tax=((managementCart.getTotalFee()*percentTax)*100)/100.0
        val total=((managementCart.getTotalFee()+tax+delivery)*100)/100
        val itemtotal= (managementCart.getTotalFee()*100)/100
        binding.apply {
            totalFeeTxt.text="$$itemtotal"
            totalTaxTxt.text="$$tax"
            deliveryTxt.text="$$delivery"
            totalTxt.text="$$total"
        }
    }
}