package academy.bangkit.githubuser.ui

import academy.bangkit.githubuser.data.remote.response.GithubUser
import academy.bangkit.githubuser.databinding.UserSearchBinding
import academy.bangkit.githubuser.ui.detail.DetailActivity
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ProfileAdapter : ListAdapter<GithubUser, ProfileAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = UserSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            Toast.makeText(
                holder.itemView.context, "Kamu memilih " + item.username, Toast.LENGTH_LONG
            ).show()
            val intentToDetail =
                Intent(holder.itemView.context as Activity, DetailActivity::class.java)
                    .putExtra(DetailActivity.USERNAME, item.username)
            holder.itemView.context.startActivity(intentToDetail)
        }
    }

    class MyViewHolder(private val binding: UserSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GithubUser) {
            binding.tvItem.text = user.username
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