package academy.bangkit.githubuser.ui

import academy.bangkit.githubuser.data.remote.response.GithubSearchResponse
import academy.bangkit.githubuser.data.remote.response.GithubUser
import academy.bangkit.githubuser.data.remote.retrofit.ApiConfig
import android.util.Log
import androidx.lifecycle.*
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _listUser = MutableLiveData<List<GithubUser>>()
    val listUser: LiveData<List<GithubUser>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFailed = MutableLiveData<Boolean>()
    val isFailed: LiveData<Boolean> = _isFailed

    init {
        findUser()
    }

    private fun findUser() {
        _isLoading.value = true
        _isFailed.value = false
        val client = ApiConfig.getApiService().getUsersList(username)
        client.enqueue(object : Callback<GithubSearchResponse> {
            override fun onResponse(
                call: Call<GithubSearchResponse>,
                response: Response<GithubSearchResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubSearchResponse>, t: Throwable) {
                _isLoading.value = false
                _isFailed.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        var username: String = "abdullah"
        private const val TAG = "MainViewModel"
    }
}