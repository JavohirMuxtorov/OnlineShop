package com.example.savdo.screen

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.savdo.api.Api
import com.example.savdo.api.NetworkManager
import com.example.savdo.api.repository.ShopRepository
import com.example.savdo.db.AppDatabase
import com.example.savdo.model.BaseResponse
import com.example.savdo.model.CategoryModel
import com.example.savdo.model.OffersModel
import com.example.savdo.model.ProductModel
import com.example.savdo.utils.Constants
import com.example.savdo.view.CategoryAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    val repository = ShopRepository()
    val error = MutableLiveData<String>()
    val progress = MutableLiveData<Boolean>()
    val offersData = MutableLiveData<List<OffersModel>>()
    val categoryData = MutableLiveData<List<CategoryModel>>()
    val productData = MutableLiveData<List<ProductModel>>()

    fun getOffers() {
        repository.getOffers(error, progress, offersData)
    }

    fun getCategories() {
        repository.getCategories(error, categoryData)
    }

    fun getTopProducts() {
        repository.getTopProducts(error, productData)
    }

    fun getProductsByCategory(id: Int) {
        repository.getProductsByCategory(id, error, productData)
    }

    fun getProductsByIds(ids: List<Int>) {
        repository.getProductsByIds(ids, error, progress, productData)
    }

    fun InsertAllProducts2DB(items: List<ProductModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getDatabase().getProductDao().deleteAll()
            AppDatabase.getDatabase().getProductDao().insertAll(items)
        }
    }
    fun getAllDBProducts(){
        CoroutineScope(Dispatchers.Main).launch {
            productData.value = withContext(Dispatchers.IO){AppDatabase.getDatabase().getProductDao().getAllProducts()}
        }
    }
    fun InsertAllCategory2DB(items: List<CategoryModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getDatabase().getCategoryDao().deleteAll()
            AppDatabase.getDatabase().getCategoryDao().insertAll(items)
        }
    }
    fun getAllDBCategory(){
        CoroutineScope(Dispatchers.Main).launch {
            categoryData.value = withContext(Dispatchers.IO){AppDatabase.getDatabase().getCategoryDao().getAllCategories()}
        }
    }
}
