package com.example.shop.domain.usecase

import com.example.shop.domain.common.Resource
import com.example.shop.domain.model.Item
import com.example.shop.domain.repository.ShopRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetListUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {
    suspend operator fun invoke(): Flow<List<Item>> {
        return flow {
            val result = shopRepository.getList()
            when (result) {
                is Resource.Success -> {
                    emit(result.data)
                }
                is Resource.Error -> {
                    error(result.message)
                }
            }
        }/*.flowOn(Dispatchers.IO)*/
    }
}