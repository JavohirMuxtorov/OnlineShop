package com.example.savdo.api

import com.example.savdo.model.BaseResponse
import com.example.savdo.model.CategoryModel
import com.example.savdo.model.OffersModel
import com.example.savdo.model.ProductModel
import com.example.savdo.model.request.GetProductsByIdsRequest
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {
    @GET("get_offers")
    fun getOffers(): Observable<BaseResponse<List<OffersModel>>>

    @GET("get_categories")
    fun getCategories(): Observable<BaseResponse<List<CategoryModel>>>

    @GET("get_top_products")
    fun getTopProducts(): Observable<BaseResponse<List<ProductModel>>>

    @GET("get_products/{category_id}")
    fun getCategoryProducts(@Path("category_id") categoryId: Int): Observable<BaseResponse<List<ProductModel>>>

    @POST("get_products_by_ids")
    fun getProductsByIds(@Body request: GetProductsByIdsRequest): Observable<BaseResponse<List<ProductModel>>>
}