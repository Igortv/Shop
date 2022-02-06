package com.example.shop.domain.usecase

import com.example.shop.domain.common.Resource
import com.example.shop.domain.model.Item
import com.example.shop.domain.repository.ShopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateItemUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {
    operator fun invoke(item: Item): Flow<Unit> = flow {
        val result = shopRepository.updateItem(item)
        if (result is Resource.Error) {
            error(result.message)
        }
    }
}