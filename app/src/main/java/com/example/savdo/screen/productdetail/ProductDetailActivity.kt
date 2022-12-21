package com.example.savdo.screen.productdetail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.savdo.R
import com.example.savdo.model.ProductModel
import com.example.savdo.utils.Constants
import com.example.savdo.utils.LocaleManager
import com.example.savdo.utils.PrefUtils
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {
    lateinit var item: ProductModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        item = intent.getSerializableExtra(Constants.EXTRA_DATA) as ProductModel

        cardViewBack.setOnClickListener {
            finish()
        }
        cardViewFavorite.setOnClickListener {
            PrefUtils.setFavorites(item)

            if (PrefUtils.checkFavorites(item)){
                imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
            }else{
                imgFavorite.setImageResource(R.drawable.favorite)
            }
        }
        tvTittle.text = item.name
        tvName.text = item.name
        tvPrice.text = item.price

        if (PrefUtils.getCartCount(item)>0){
            add2Cart.visibility = View.GONE
        }else{
            add2Cart.visibility = View.VISIBLE
        }


        if (PrefUtils.checkFavorites(item)){
            imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        }else{
            imgFavorite.setImageResource(R.drawable.favorite)
        }

        Glide.with(this).load(Constants.HOST_IMAAGE+item.image).into(productImg)

        add2Cart.setOnClickListener {
            PrefUtils.setCart(item)
            Toast.makeText(this, "Product add to cart", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }
}