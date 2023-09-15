package academy.bangkit.githubuser.data.remote.response

import com.google.gson.annotations.SerializedName

data class GithubUser(
    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("login")
    val username: String
)