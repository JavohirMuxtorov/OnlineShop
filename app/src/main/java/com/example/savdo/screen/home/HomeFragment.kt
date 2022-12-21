package com.example.savdo.screen.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.savdo.R
import com.example.savdo.api.Api
import com.example.savdo.model.BaseResponse
import com.example.savdo.model.CategoryModel
import com.example.savdo.model.OffersModel
import com.example.savdo.screen.MainViewModel
import com.example.savdo.utils.Constants
import com.example.savdo.view.CategoryAdapter
import com.example.savdo.view.CategoryAdapterCallback
import com.example.savdo.view.ProductAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe.setOnRefreshListener {
            loadData()
        }
        recyclerProduct.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)
        recyclerCategories.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL,false)
        viewModel.error.observe(requireActivity(), Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })
        viewModel.offersData.observe(requireActivity(), Observer {

            carouselView.setImageListener { position, imageView ->
                Glide.with(imageView).load(Constants.HOST_IMAAGE+it[position].image).into(imageView)
            }
            carouselView.pageCount = it.count()
        })

        viewModel.progress.observe(requireActivity(), Observer {
            swipe.isRefreshing = it
        })
        viewModel.categoryData.observe(requireActivity(), Observer {
            recyclerCategories.adapter = CategoryAdapter(it, object : CategoryAdapterCallback{
                override fun onClick(item: CategoryModel) {
                    viewModel.getProductsByCategory(item.id)
                }
            })
        })
        viewModel.productData.observe(requireActivity(), Observer {
            recyclerProduct.adapter = ProductAdapter(it)
        })
        loadData()
    }
    fun loadData(){
        viewModel.getOffers()
        viewModel.getAllDBCategory()
        viewModel.getAllDBProducts()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}