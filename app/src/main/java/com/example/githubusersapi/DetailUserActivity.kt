package com.example.githubusersapi

import android.content.Intent.EXTRA_USER
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubusersapi.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import data.UserResponse
import network.Resource

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailUserBinding
    private lateinit var viewModel : MainViewModel

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val login = intent.getStringExtra(EXTRA_USER)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getDetailUser(login).observe(this) {
            when (it) {
                is Resource.Success -> {
                    showLoading(false)
                    val data = it.data!!
                    showData(data)
                }
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(this@DetailUserActivity, it.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    showLoading(true)
                }

            }
        }

        viewPager(login)
    }

    private fun showData(data: UserResponse) {
        if (data.name.isNullOrEmpty()){
            supportActionBar?.title = data.login
        }else{
            supportActionBar?.title = data.name
        }
        Glide.with(this@DetailUserActivity)
            .load(data.avatarUrl)
            .into(binding.ivAvatar)
        binding.tvName.text = data.name
        binding.tvUsername.text = data.login
        "Repository: ${data.publicRepos}".also { binding.tvRepos.text = it }
        "Follower: ${data.followers}".also { binding.tvFollower.text = it }
        "Following: ${data.following}".also { binding.tvFollowing.text = it }
        if (data.location.isNullOrEmpty()){
            binding.tvLocation.visibility = View.GONE
        }else{
            binding.tvLocation.visibility = View.VISIBLE
            binding.tvLocation.text = data.location
        }

    }

    private fun viewPager(login: String?) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, login.toString())
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }



    private fun showLoading(isLoading: Boolean) {
        binding.pbDetailuser.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}