package com.example.githubusersapi


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersapi.databinding.ActivityListUserBinding

import network.Resource



class ListUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListUserBinding
    private lateinit var userAdapter: ListUserAdapter
    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_user)
        binding = ActivityListUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getListUsers().observe(this){
            when (it) {
                is Resource.Success -> {
                    showLoading(false)
                    userAdapter.setAllData(it.data!!)
                }
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(this@ListUserActivity, it.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    showLoading(true)
                }
            }
        }
        showRecyclerList()
    }

    private fun showRecyclerList() {
        userAdapter = ListUserAdapter()
        binding.rvListUser.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@ListUserActivity, LinearLayoutManager.VERTICAL, false)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchUser(query)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    private fun searchUser(query: String?) {
        viewModel.getSearchUsers(query!!).observe(this) {
            when (it) {
                is Resource.Success -> {
                    showLoading(false)
                    userAdapter.setAllData(it.data!!)
                }
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(this@ListUserActivity, it.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    showLoading(true)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu1 -> {
                Toast.makeText(this@ListUserActivity, getString(R.string.owner), Toast.LENGTH_SHORT).show()
                true
            }
            else -> true
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}