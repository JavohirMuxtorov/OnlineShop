package com.example.savdo.screen.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.savdo.R
import com.example.savdo.screen.MainViewModel
import com.example.savdo.utils.PrefUtils
import com.example.savdo.view.ProductAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment() {
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.productData.observe(this, Observer {
            recyclerProductByIds.adapter = ProductAdapter(it)
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })
        viewModel.progress.observe(this, Observer {
            swipe.isRefreshing = it
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerProductByIds.layoutManager = LinearLayoutManager(requireActivity())
        swipe.setOnRefreshListener {
            loadData()
        }
        loadData()
    }
    fun loadData(){
        viewModel.getProductsByIds(PrefUtils.getFavoriteList())
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoriteFragment()
    }
}