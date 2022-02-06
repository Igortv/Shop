package com.example.shop.domain.usecase

import com.example.shop.domain.common.Resource
import com.example.shop.domain.model.Item
import com.example.shop.domain.repository.ShopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetItemByIdUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {
    operator fun invoke(itemId: String): Flow<Item> = flow {
        val result = shopRepository.getItemById(itemId)
        when (result) {
            is Resource.Success -> {
                emit(result.data)
            }
            is Resource.Error -> {
                error(result.message)
            }
        }
    }
}