package academy.bangkit.githubuser.ui.detail

import academy.bangkit.githubuser.R
import academy.bangkit.githubuser.ui.follow.SectionsPagerAdapter
import academy.bangkit.githubuser.data.remote.response.GithubUserDetail
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import academy.bangkit.githubuser.databinding.ActivityDetailBinding
import academy.bangkit.githubuser.ui.DialogFragment
import academy.bangkit.githubuser.ui.extension.loadImageWithGlide
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val username = intent.getStringExtra(USERNAME) as String
        binding.username.text = username

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username
        binding.viewPager.adapter = sectionsPagerAdapter

        supportActionBar?.elevation = 0f

        val detailViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]

        detailViewModel.getDetailUser(username)

        val observer = Observer<GithubUserDetail> { userDetail ->
            setItemData(userDetail)
        }

        detailViewModel.userDetail.observe(this, observer)
        detailViewModel.isLoading.observe(this) { showLoading(it) }
        detailViewModel.isFailed.observe(this) { showDialog(it, username) }

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingView.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setItemData(detail: GithubUserDetail) {
        binding.avatar.loadImageWithGlide(detail.avatarUrl)
        binding.name.text = detail.name
        binding.followers.text = resources.getString(R.string.data_followers, detail.followers)
        binding.following.text = resources.getString(R.string.data_following, detail.following)
    }

    private fun showDialog(isFailed: Boolean, username: String) {
        if (isFailed) {
            val data = arrayListOf("DetailActivity", username)
            val bundle = Bundle()
            bundle.putStringArrayList("dialog", data)
            val fragment = DialogFragment()
            fragment.arguments = bundle
            fragment.show(supportFragmentManager, "my_dialog")
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
        const val USERNAME = "extra_name"
    }
}