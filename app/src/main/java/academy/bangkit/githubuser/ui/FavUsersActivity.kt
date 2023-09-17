package academy.bangkit.githubuser.ui

import academy.bangkit.githubuser.R
import academy.bangkit.githubuser.data.remote.response.GithubUser
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import academy.bangkit.githubuser.databinding.ActivityFavUsersBinding
import academy.bangkit.githubuser.ui.detail.FavUserAddUpdateViewModel
import academy.bangkit.githubuser.ui.detail.FavUserAddViewModelFactory
import academy.bangkit.githubuser.ui.setting.SettingActivity
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

class FavUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavUsersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: FavUserAddUpdateViewModel = obtainViewModel(this@FavUsersActivity)

        val layoutManager = LinearLayoutManager(this)
        binding.rvItemProfile.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvItemProfile.addItemDecoration(itemDecoration)

        viewModel.getAllFavUsers().observe(this) {
            val favUsers: MutableList<GithubUser> = mutableListOf()
            for (favUser in it) {
                favUsers.add(
                    GithubUser(
                        favUser.avatarUrl
                            ?: "https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png",
                        favUser.username
                    )
                )
            }
            if (it.isEmpty()) {
                binding.rvItemProfile.visibility = View.GONE
                binding.ifEmpty.visibility = View.VISIBLE
            }

            val profileAdapter = ProfileAdapter()
            profileAdapter.submitList(favUsers)
            binding.rvItemProfile.adapter = profileAdapter

        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuSettingInFav -> {
                    startActivity(Intent(this@FavUsersActivity, SettingActivity::class.java))
                    true
                }

                else -> false
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavUserAddUpdateViewModel {
        val factory = FavUserAddViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavUserAddUpdateViewModel::class.java]
    }
}