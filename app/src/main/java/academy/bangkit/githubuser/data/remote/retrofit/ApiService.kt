package academy.bangkit.githubuser.data.remote.retrofit

import academy.bangkit.githubuser.data.remote.response.GithubUserDetail
import academy.bangkit.githubuser.data.remote.response.GithubSearchResponse
import academy.bangkit.githubuser.data.remote.response.GithubUser
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUsersList(@Query("q") users: String): Call<GithubSearchResponse>

    @GET("users/{username}")
    fun getUserDetail(@Path("username") username: String): Call<GithubUserDetail>

    @GET("users/{username}/followers")
    fun getUserFollowers(@Path("username") username: String): Call<List<GithubUser>>

    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String): Call<List<GithubUser>>
}