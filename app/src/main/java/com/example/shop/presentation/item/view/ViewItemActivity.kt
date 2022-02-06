package com.example.shop.presentation.item.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.shop.App
import com.example.shop.R
import com.example.shop.viewmodelfactories.ViewItemViewModelFactory
import com.example.shop.databinding.ActivityItemViewBinding
import com.example.shop.domain.model.Item
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ViewItemActivity : AppCompatActivity()/*ComponentActivity()*/ {
    companion object {
        const val ITEM_ID_EXTRA = "item_id"
    }
    lateinit var binding: ActivityItemViewBinding
    lateinit var item: Item

    @Inject
    lateinit var vmFactory: ViewItemViewModelFactory

    private lateinit var viewModel: ViewItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (applicationContext as App).appComponent.inject(this)

        viewModel = ViewModelProvider(this, vmFactory)
            .get(ViewItemViewModel::class.java)

        viewModel.viewState.observe(this, Observer { result ->
            handleStateChange(result)
        })

        val itemId = intent.extras?.getString(ITEM_ID_EXTRA)
        if (itemId != null) {
            viewModel.getItem(itemId)
        }
    }

    private fun handleStateChange(viewState: ViewItemViewState) {
        when(viewState) {
            is ViewItemViewState.Loading -> {

            }
            is ViewItemViewState.Error -> {
                Toast.makeText(this, viewState.message, Toast.LENGTH_LONG).show()
            }
            is ViewItemViewState.ItemLoaded -> {
                item = viewState.item

                binding.textViewItemName.text = item.name
                binding.textViewItemDescription.text = item.description
                binding.textViewItemPrice.text = item.price
                Picasso.get()
                    .load(item.imageUrl)
                    .placeholder(R.drawable.placeholder)
                    //.error(R.drawable.loading_image_error)
                    .into(binding.imageViewItemImage);
            }
        }
    }
}