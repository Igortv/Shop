package com.example.shop.data.remote.dto

import com.example.shop.domain.model.Item
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ItemDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("description")
    @Expose
    val description: String,
    @SerializedName("price")
    @Expose
    val price: String,
    @SerializedName("imageUrl")
    @Expose
    val imageUrl: String
) {
    companion object {
        fun from(item: Item): ItemDto {
            return ItemDto(
                id = item.id,
                name = item.name,
                description = item.description,
                price = item.price,
                imageUrl = item.imageUrl
            )
        }
    }
}

fun ItemDto.toItem() : Item {
    return Item (
        id = id,
        name = name,
        description = description,
        price = price,
        imageUrl = imageUrl
    )
}
