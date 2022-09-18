package com.example.githubusersapi

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.SearchUserResponse
import data.UserResponse
import network.ApiConfig
import network.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel : ViewModel() {
    private val client = ApiConfig.getApiService()

    private val _listUsers = MutableLiveData<Resource<List<UserResponse>>>()
    private val _detailUser = MutableLiveData<Resource<UserResponse>>()

    private val _listFollower = MutableLiveData<Resource<List<UserResponse>>>()
    private val _listFollowing = MutableLiveData<Resource<List<UserResponse>>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getListUsers(): LiveData<Resource<List<UserResponse>>> {
        _isLoading.value = true
        _listUsers.postValue(Resource.Loading())
        client.getListUsers().enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(call: Call<List<UserResponse>>, response: Response<List<UserResponse>>) {
                _isLoading.value = false
                val list = response.body()
                if (response.isSuccessful)
                    _listUsers.postValue(Resource.Success(list))
                else
                    _listUsers.postValue(Resource.Error(null))
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
        return _listUsers
    }

    fun getSearchUsers(query: String): LiveData<Resource<List<UserResponse>>> {
        _isLoading.value = true
        _listUsers.postValue(Resource.Loading())
        client.getSearchUsers(query).enqueue(object : Callback<SearchUserResponse>{
            override fun onResponse(call: Call<SearchUserResponse>, response: Response<SearchUserResponse>) {
                _isLoading.value = false
                val list = response.body()?.items
                if (response.isSuccessful)
                    _listUsers.postValue(Resource.Success(list))
                else
                    _listUsers.postValue(Resource.Error(null))
            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
        return _listUsers
    }

    fun getDetailUser(login: String?): LiveData<Resource<UserResponse>>{
        _isLoading.value = true
        if (login != null){
            client.getDetailUser(login).enqueue(object: Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    _isLoading.value = false
                    val result = response.body()
                    if (response.isSuccessful)
                        _detailUser.postValue(Resource.Success(result))
                    else
                        _detailUser.postValue(Resource.Error(null))

                }
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }
        return _detailUser
    }

    fun getFollowerUser(login: String): LiveData<Resource<List<UserResponse>>>{
        _isLoading.value = true
        _listFollower.postValue(Resource.Loading())
        client.getFollowerUser(login).enqueue(object : Callback <List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                _isLoading.value = false
                val list = response.body()
                if (response.isSuccessful)
                    _listFollower.postValue(Resource.Success(list))
                else
                    _listFollower.postValue(Resource.Error(null))
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
        return _listFollower
    }

    fun getFollowingUser(login: String): LiveData<Resource<List<UserResponse>>>{
        _isLoading.value = true
        _listFollowing.postValue(Resource.Loading())
        client.getFollowingUser(login).enqueue(object : Callback <List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                _isLoading.value = false
                val list = response.body()
                if (response.isSuccessful)
                    _listFollowing.postValue(Resource.Success(list))
                else
                    _listFollowing.postValue(Resource.Error(null))
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
        return _listFollowing
    }

}