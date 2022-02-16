package com.example.shop.presentation.shoplist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shop.App
import com.example.shop.R
import com.example.shop.viewmodelfactories.ShopListViewModelFactory
import com.example.shop.databinding.ActivityShopListBinding
import com.example.shop.presentation.additem.AddItemActivity
import com.example.shop.presentation.arch.BaseAppCompatActivity
import com.example.shop.presentation.item.edit.EditItemActivity
import com.example.shop.presentation.item.view.ViewItemActivity
import com.example.shop.presentation.login.LoginActivity
import javax.inject.Inject

class ShopListActivity : BaseAppCompatActivity<ShopListViewState, ShopListViewModel>(), OnListItemClickListener/*ComponentActivity()*/ {

    private lateinit var binding: ActivityShopListBinding

    @Inject
    lateinit var vmFactory: ShopListViewModelFactory

    private lateinit var loginButtonMenu: MenuItem

    override fun createViewModel(): ShopListViewModel {
        val vm = ViewModelProvider(this, vmFactory)
            .get(ShopListViewModel::class.java)
        return vm
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShopListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (applicationContext as App).appComp().inject(this)
        initViewStateLiveData()

        binding.buttonAddItem.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)
        }

        binding.shopListSwipeRefreshLayout.setOnRefreshListener {
            viewModel.getItems()
            binding.shopListSwipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()

        if (viewModel.isUserLogged()) {
            binding.buttonAddItem.visibility = View.VISIBLE
        } else {
            binding.buttonAddItem.visibility = View.INVISIBLE
        }
        viewModel.getItems()
        invalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.mymenu, menu)
        loginButtonMenu = menu.findItem(R.id.loginStatus)
        loginButtonMenu.title = if (viewModel.isUserLogged()) "Log out" else "Log in"

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.loginStatus -> {
                if (viewModel.isUserLogged()) {
                    viewModel.setUserLoggedValue(isLogged = false)
                    binding.buttonAddItem.visibility = View.INVISIBLE
                    loginButtonMenu.title = "Log in"
                } else {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun viewStateHandler(viewState: ShopListViewState): () -> Unit = when(viewState) {
        ShopListViewState.Loading -> {
            {}
        }
        is ShopListViewState.ItemsLoaded -> {
            {
                val adapter = ItemAdapter(viewState.items, this)
                binding.itemRecyclerView.adapter = adapter
                val linearLayoutManager = LinearLayoutManager(this)
                binding.itemRecyclerView.layoutManager = linearLayoutManager
            }
        }
        is ShopListViewState.Error -> {
            {
                Toast.makeText(this, viewState.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onItemClicked(itemId: String) {
        if (viewModel.isUserLogged()) {
            val intent = Intent(this, EditItemActivity::class.java)
            intent.putExtra(EditItemActivity.ITEM_ID_EXTRA, itemId)
            startActivity(intent)
        } else {
            val intent = Intent(this, ViewItemActivity::class.java)
            intent.putExtra(ViewItemActivity.ITEM_ID_EXTRA, itemId)
            startActivity(intent)
        }
    }
}