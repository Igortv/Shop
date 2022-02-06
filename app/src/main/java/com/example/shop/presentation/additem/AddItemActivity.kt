package com.example.shop.presentation.additem

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.shop.viewmodelfactories.AddItemViewModelFactory
import com.example.shop.App
import com.example.shop.databinding.ActivityAddItemBinding
import javax.inject.Inject

class AddItemActivity : AppCompatActivity()/*ComponentActivity()*/ {

    lateinit var binding: ActivityAddItemBinding

    @Inject
    lateinit var vmFactory: AddItemViewModelFactory

    private lateinit var viewModel: AddItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (applicationContext as App).appComponent.inject(this)

        viewModel = ViewModelProvider(this, vmFactory)
            .get(AddItemViewModel::class.java)

        viewModel.viewState.observe(this, Observer { result ->
            handleStateChange(result)
        })

        binding.buttonAdd.setOnClickListener {
            viewModel.createItem(
                binding.editTextAddItemName.text.toString(),
                binding.editTextAddItemDescription.text.toString(),
                binding.editTextAddItemPrice.text.toString(),
                binding.editTextAddItemUrlImage.text.toString()
            )
        }
    }

    private fun handleStateChange(viewState: AddItemViewState) {
        when(viewState) {
            is AddItemViewState.Error -> {
                Toast.makeText(this, viewState.message, Toast.LENGTH_LONG).show()
            }
            is AddItemViewState.ItemAdded -> {
                finish()
            }
            is AddItemViewState.EmptyFields -> {
                Toast.makeText(this, "Fields cannot be empty, fill all fields.", Toast.LENGTH_LONG).show()
            }
        }
    }
}