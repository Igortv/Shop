package com.example.shop.data.repository

import com.example.shop.data.remote.ShopApi
import com.example.shop.data.remote.dto.ItemDto
import com.example.shop.data.remote.dto.toItem
import com.example.shop.domain.common.Resource
import com.example.shop.domain.model.Item
import com.example.shop.domain.repository.ShopRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val api: ShopApi
) : ShopRepository {
    override suspend fun getList(): Resource<List<Item>> {
        val response = api.getAllItems()
        try {
            //val response = call.execute()
            if (response.isSuccessful) {
                val data = response.body()!!.toList().map { it.second.toItem() }
                return Resource.Success(data)
            } else {
                val responseCode = response.code()
                return Resource.Error("Response code: $responseCode")
            }
        } catch (e: HttpException) {
            return Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        } catch (e: IOException) {
            return Resource.Error("Couldn't reach server. Check your internet connection.")
        }
    }

    override suspend fun createItem(item: Item): Resource<Unit> {
        val requestEntity = ItemDto.from(item)
        val response = api.createItem(requestEntity.id, requestEntity)
        try {
            //val response = call.response
            if (response.isSuccessful) {
                return Resource.Success(Unit)
            } else {
                return Resource.Error("Response code: " + response.code())
            }
        } catch (e: HttpException) {
            return Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        } catch (e: IOException) {
            return Resource.Error("Couldn't reach server. Check your internet connection.")
        }
    }

    override suspend fun getItemById(itemId: String): Resource<Item> {
        val response = api.getItem(itemId)
        try {
            //val response = call.execute()
            if (response.isSuccessful) {
                val data = response.body()!!.toItem()
                return Resource.Success(data)
            } else {
                return Resource.Error("Response code: " + response.code())
            }
        } catch (e: HttpException) {
            return Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        } catch (e: IOException) {
            return Resource.Error("Couldn't reach server. Check your internet connection.")
        }
    }

    override suspend fun updateItem(item: Item): Resource<Unit> {
        val requestEntity = ItemDto.from(item)
        val response = api.updateItem(item.id, requestEntity)
        try {
            //val response = call.execute()
            if (response.isSuccessful) {
                return Resource.Success(Unit)
            } else {
                return Resource.Error("Response code: " + response.code())
            }
        } catch (e: HttpException) {
            return Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        } catch (e: IOException) {
            return Resource.Error("Couldn't reach server. Check your internet connection.")
        }
    }

    override suspend fun deleteItem(itemId: String): Resource<Unit> {
        val response = api.deleteItem(itemId)
        try {
            //val response = call.execute()
            if (response.isSuccessful) {
                return Resource.Success(Unit)
            } else {
                return Resource.Error("Response code: " + response.code())
            }
        } catch (e: HttpException) {
            return Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        } catch (e: IOException) {
            return Resource.Error("Couldn't reach server. Check your internet connection.")
        }
    }
}