package com.example.shop.data.remote

import com.example.shop.data.remote.dto.ItemDto
import retrofit2.Response
import retrofit2.http.*


interface ShopApi {
    @GET("/items/.json")
    suspend fun getAllItems(): Response<Map<String, ItemDto>>

    @GET("/items/{id}.json")
    suspend fun getItem(@Path("id") itemId: String?): Response<ItemDto?>

    @PUT("/items/{id}.json")
    suspend fun createItem(@Path("id") id: String?, @Body item: ItemDto?): Response<Unit>

    @PUT("/items/{id}.json")
    suspend fun updateItem(@Path("id") id: String?, @Body item: ItemDto?): Response<Unit>

    @DELETE("/items/{id}.json")
    suspend fun deleteItem(@Path("id") itemId: String?): Response<Unit>
}