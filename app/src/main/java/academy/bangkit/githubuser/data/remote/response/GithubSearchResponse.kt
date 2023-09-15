package academy.bangkit.githubuser.data.remote.response

import com.google.gson.annotations.SerializedName

data class GithubSearchResponse(
    @field:SerializedName("items")
    val items: List<GithubUser>
)