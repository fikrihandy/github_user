package academy.bangkit.githubuser.ui.follow

import academy.bangkit.githubuser.data.remote.response.GithubUser
import academy.bangkit.githubuser.databinding.FragmentFollowBinding
import academy.bangkit.githubuser.ui.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

class FollowFragment : Fragment() {


    private lateinit var binding: FragmentFollowBinding

    private var position: Int = 0
    lateinit var username: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val followViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        )[FollowViewModel::class.java]

        arguments?.let {
            position = it.getInt(ARG_SECTION_NUMBER)
            username = it.getString(ARG_USERNAME) as String
        }
        if (position == 1) followViewModel.getFollowers(username) else followViewModel.getFollowing(
            username
        )
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollow.addItemDecoration(itemDecoration)

        followViewModel.listFollow.observe(viewLifecycleOwner) { itemProfile ->
            setItemData(itemProfile)
        }

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        followViewModel.isFailed.observe(viewLifecycleOwner) {
            showDialog(it)
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingView.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setItemData(items: List<GithubUser>) {
        val adapter = FollowAdapter()
        adapter.submitList(items)
        binding.rvFollow.adapter = adapter
    }

    private fun showDialog(isFailed: Boolean) {
        if (isFailed) {
            val data = arrayListOf("DetailActivity", username)

            val bundle = Bundle()
            bundle.putStringArrayList("dialog", data)
            val dialogFragment = DialogFragment()
            dialogFragment.arguments = bundle

            val transaction = childFragmentManager.beginTransaction()
            transaction.add(dialogFragment, "my_dialog")
            transaction.commit()
        }
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_USERNAME = "username"
    }
}