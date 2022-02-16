package com.example.shop.presentation.item.edit

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.shop.App
import com.example.shop.viewmodelfactories.EditItemViewModelFactory
import com.example.shop.R
import com.example.shop.databinding.ActivityEditItemBinding
import com.example.shop.domain.model.Item
import com.example.shop.presentation.arch.BaseAppCompatActivity
import com.squareup.picasso.Picasso
import javax.inject.Inject

class EditItemActivity : BaseAppCompatActivity<EditItemViewState, EditItemViewModel>()/*ComponentActivity()*/ {
    companion object {
        const val ITEM_ID_EXTRA = "item_id"
    }
    lateinit var binding: ActivityEditItemBinding
    lateinit var item: Item

    @Inject
    lateinit var vmFactory: EditItemViewModelFactory

    override fun createViewModel(): EditItemViewModel {
        val vm = ViewModelProvider(this, vmFactory)
            .get(EditItemViewModel::class.java)
        return vm
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (applicationContext as App).appComp().inject(this)

        initViewStateLiveData()

        binding.buttonSave.setOnClickListener {
            viewModel.updateItem(
                item.id,
                binding.editTextEditItemName.text.toString(),
                binding.editTextEditItemDescription.text.toString(),
                binding.editTextEditItemPrice.text.toString(),
                binding.editTextEditItemUrlImage.text.toString()
            )
        }
        binding.buttonDelete.setOnClickListener {
            viewModel.deleteItem(item.id)
        }

        val itemId = intent.extras?.getString(ITEM_ID_EXTRA)
        if (itemId != null) {
            viewModel.getItem(itemId)
        }
    }

    override fun viewStateHandler(viewState: EditItemViewState): () -> Unit = when(viewState) {
        EditItemViewState.Loading -> {
            {}
        }
        is EditItemViewState.Error -> {
            {
                Toast.makeText(this, viewState.message, Toast.LENGTH_LONG).show()
            }
        }
        EditItemViewState.EmptyFields -> {
            {}
        }
        is EditItemViewState.ItemLoaded -> {
            {
                item = viewState.item

                binding.editTextEditItemName.setText(viewState.item.name)
                binding.editTextEditItemDescription.setText(viewState.item.description)
                binding.editTextEditItemPrice.setText(viewState.item.price)
                binding.editTextEditItemUrlImage.setText(viewState.item.imageUrl)
                Picasso.get()
                    .load(item.imageUrl)
                    .placeholder(R.drawable.placeholder)
                    //.error(R.drawable.loading_image_error)
                    .into(binding.imageView);
            }
        }
        is EditItemViewState.ItemUpdated -> {
            {
                Toast.makeText(this, "Item updated", Toast.LENGTH_LONG).show()
                finish()
            }
        }
        is EditItemViewState.ItemDeleted -> {
            {
                Toast.makeText(this, "Item deleted", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}