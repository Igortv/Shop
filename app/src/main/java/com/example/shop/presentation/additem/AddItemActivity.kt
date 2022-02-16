package com.example.shop.presentation.additem

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.shop.viewmodelfactories.AddItemViewModelFactory
import com.example.shop.App
import com.example.shop.databinding.ActivityAddItemBinding
import com.example.shop.presentation.arch.BaseAppCompatActivity
import javax.inject.Inject

class AddItemActivity : BaseAppCompatActivity<AddItemViewState, AddItemViewModel>()/*ComponentActivity()*/ {

    lateinit var binding: ActivityAddItemBinding

    @Inject
    lateinit var vmFactory: AddItemViewModelFactory

    override fun createViewModel(): AddItemViewModel {
        val vm = ViewModelProvider(this, vmFactory)
            .get(AddItemViewModel::class.java)
        return vm
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewStateLiveData()

        (applicationContext as App).appComp().inject(this)

//        viewModel.viewState.observe(this, Observer { result ->
//            handleStateChange(result)
//        })

        binding.buttonAdd.setOnClickListener {
            viewModel.createItem(
                binding.editTextAddItemName.text.toString(),
                binding.editTextAddItemDescription.text.toString(),
                binding.editTextAddItemPrice.text.toString(),
                binding.editTextAddItemUrlImage.text.toString()
            )
        }
    }

    override fun viewStateHandler(viewState: AddItemViewState): () -> Unit = when(viewState) {
        AddItemViewState.OK -> {
            {}
        }
        AddItemViewState.Loading -> {
            {}
        }
        is AddItemViewState.Error -> {
            {
                Toast.makeText(this, viewState.message, Toast.LENGTH_LONG).show()
            }
        }
        is AddItemViewState.ItemAdded -> {
            {
                finish()
            }
        }
        is AddItemViewState.EmptyFields -> {
            {
                Toast.makeText(this, "Fields cannot be empty, fill all fields.", Toast.LENGTH_LONG).show()
            }
        }
    }

//    private fun handleStateChange(viewState: AddItemViewState) {
//        when(viewState) {
//            is AddItemViewState.Error -> {
//                Toast.makeText(this, viewState.message, Toast.LENGTH_LONG).show()
//            }
//            is AddItemViewState.ItemAdded -> {
//                finish()
//            }
//            is AddItemViewState.EmptyFields -> {
//                Toast.makeText(this, "Fields cannot be empty, fill all fields.", Toast.LENGTH_LONG).show()
//            }
//        }
//    }
}