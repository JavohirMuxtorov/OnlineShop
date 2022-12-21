package com.example.savdo.screen.cart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.savdo.R
import com.example.savdo.model.ProductModel
import com.example.savdo.screen.MainViewModel
import com.example.savdo.screen.makeorder.MakeOrderActivity
import com.example.savdo.utils.Constants
import com.example.savdo.utils.PrefUtils
import com.example.savdo.view.CartAdapter
import kotlinx.android.synthetic.main.fragment_cart.*
import java.io.Serializable

class CartFragment : Fragment() {
lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.error.observe(this, Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })
        viewModel.progress.observe(this, Observer {
            swipeC.isRefreshing = it
        })
        viewModel.productData.observe(this, Observer {
            recycler.adapter = CartAdapter(it)
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler.layoutManager = LinearLayoutManager(requireActivity())
        swipeC.setOnRefreshListener {
            loadData()
        }
        makeOrder.setOnClickListener {
            val intent = Intent(requireActivity(), MakeOrderActivity::class.java)
            intent.putExtra(Constants.EXTRA_DATA, (viewModel.productData.value ?: emptyList<ProductModel>()) as Serializable)
            startActivity(intent)
        }
        loadData()
    }
    fun loadData(){
        viewModel.getProductsByIds(PrefUtils.getCartList().map { it.product_id })
    }

    companion object {
        @JvmStatic
        fun newInstance() = CartFragment()
    }
}