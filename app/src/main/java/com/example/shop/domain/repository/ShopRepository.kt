package com.example.shop.domain.repository

import com.example.shop.domain.common.Resource
import com.example.shop.domain.model.Item

interface ShopRepository {
    suspend fun getList(): Resource<List<Item>>
    suspend fun createItem(item: Item): Resource<Unit>
    suspend fun getItemById(itemId: String): Resource<Item>
    suspend fun updateItem(item: Item): Resource<Unit>
    suspend fun deleteItem(itemId: String): Resource<Unit>
}