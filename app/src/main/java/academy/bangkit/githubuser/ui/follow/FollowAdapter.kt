package academy.bangkit.githubuser.ui.follow

import academy.bangkit.githubuser.data.remote.response.GithubUser
import academy.bangkit.githubuser.databinding.UserFollowBinding
import academy.bangkit.githubuser.ui.detail.DetailActivity
import academy.bangkit.githubuser.ui.extension.loadImageWithGlide
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class FollowAdapter :
    ListAdapter<GithubUser, FollowAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = UserFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            val intentToDetail =
                Intent(holder.itemView.context as Activity, DetailActivity::class.java)
                    .putExtra(DetailActivity.USERNAME, item.username)
            holder.itemView.context.startActivity(intentToDetail)
        }
    }

    class MyViewHolder(private val binding: UserFollowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GithubUser) {
            binding.tvItemFollow.text = user.username
            binding.profileImage.loadImageWithGlide(user.avatarUrl)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GithubUser>() {
            override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
                return oldItem == newItem
            }
        }
    }
}