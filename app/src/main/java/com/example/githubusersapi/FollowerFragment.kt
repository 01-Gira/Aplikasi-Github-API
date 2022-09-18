package com.example.githubusersapi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersapi.databinding.FragmentFollowersBinding
import network.Resource


class FollowerFragment : Fragment(){
    private lateinit var binding : FragmentFollowersBinding
    private lateinit var followerAdapter: ListUserAdapter
    private val viewModel: MainViewModel by activityViewModels()

    private var login : String? = null

    companion object {
        fun getInstance(login : String):Fragment{
            return FollowerFragment().apply {
                arguments = Bundle().apply {
                    putString("USER", login)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        login = arguments?.getString("USER")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showRecylerList()
        showViewModel()
    }

    private fun showViewModel() {
        viewModel.getFollowerUser(login.toString()).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    isLoading(false)
                    followerAdapter.setAllData(it.data!!)
                }
                is Resource.Error -> {
                    isLoading(false)
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    isLoading(true)
                }
            }
        }
    }

    private fun showRecylerList() {
        followerAdapter = ListUserAdapter()
        binding.rvFollower.apply {
            adapter = followerAdapter
            layoutManager =LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun isLoading(isLoading: Boolean) {
        binding.pbFollowers.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}

