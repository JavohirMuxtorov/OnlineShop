package com.example.savdo.screen.makeorder

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.savdo.MapsActivity
import com.example.savdo.R
import com.example.savdo.model.AddressModel
import com.example.savdo.model.ProductModel
import com.example.savdo.utils.Constants
import com.example.savdo.utils.LocaleManager
import kotlinx.android.synthetic.main.activity_make_order.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MakeOrderActivity : AppCompatActivity() {
    lateinit var items: List<ProductModel>
    var address:AddressModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_order)
        items = intent.getSerializableExtra(Constants.EXTRA_DATA) as List<ProductModel>

        tvTotalAmount.setText(items.sumByDouble { it.cartCount.toDouble()*(it.price.replace(" ","").toDouble()) }.toString())

        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }
        edAddres.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
        cardViewBack.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe
    fun onEvent(addresModel: AddressModel){
        this.address = addresModel
        edAddres.setText("${addresModel.latitude}, ${addresModel.longitude}")
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }
}