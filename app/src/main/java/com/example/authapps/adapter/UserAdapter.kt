package com.example.authapps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.authapps.databinding.ItemUserBinding
import com.example.authapps.model.UserData

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val userList = mutableListOf<UserData>()

    fun addUsers(users: List<UserData>?) {
        users?.let {
            userList.addAll(it)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = userList.size

    class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserData) {
            binding.tvFirstName.text = user.firstName
            binding.tvLastName.text = user.lastName
            binding.tvEmail.text = user.email
            Glide.with(binding.root.context)
                .load(user.avatar)
                .into(binding.imavatar)
        }
    }
}
