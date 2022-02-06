package com.example.shop.domain.usecase

import com.example.shop.domain.common.Resource
import com.example.shop.domain.repository.ShopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteItemUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {
    operator fun invoke(itemId: String): Flow<Unit> = flow {
        val result = shopRepository.deleteItem(itemId)
        if (result is Resource.Error) {
            error(result.message)
        }
    }
}