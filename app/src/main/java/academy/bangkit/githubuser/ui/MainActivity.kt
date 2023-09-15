package academy.bangkit.githubuser.ui

import academy.bangkit.githubuser.databinding.ActivityMainBinding
import academy.bangkit.githubuser.data.remote.response.GithubUser
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        binding.rvItemProfile.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvItemProfile.addItemDecoration(itemDecoration)

        mainViewModel.listUser.observe(this) { itemProfile -> setItemData(itemProfile) }
        mainViewModel.isLoading.observe(this) { showLoading(it) }
        mainViewModel.isFailed.observe(this) { showDialog(it) }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchBar.text = searchView.text
                searchView.hide()
                Toast.makeText(
                    this@MainActivity, "Showing ${searchView.text}", Toast.LENGTH_LONG
                ).show()

                MainViewModel.username = searchView.text.toString()

                val intent = Intent(this@MainActivity, MainActivity::class.java)
                startActivity(intent)
                false
            }
        }
    }

    private fun setItemData(users: List<GithubUser>) {
        val profileAdapter = ProfileAdapter()
        profileAdapter.submitList(users)
        binding.rvItemProfile.adapter = profileAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingView.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showDialog(isFailed: Boolean) {
        if (isFailed) {
            val data = arrayListOf("MainActivity", MainViewModel.username)
            val bundle = Bundle()
            bundle.putStringArrayList("dialog", data)
            val fragment = DialogFragment()
            fragment.arguments = bundle
            fragment.show(supportFragmentManager, "my_dialog")
        }
    }
}