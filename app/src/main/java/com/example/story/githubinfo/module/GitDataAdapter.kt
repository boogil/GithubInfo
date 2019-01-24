package com.example.story.githubinfo.module

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.story.githubinfo.R
import com.google.gson.internal.LinkedTreeMap
import com.squareup.picasso.Picasso

class GitDataAdapter(context: Context, mDataset: ArrayList<LinkedTreeMap<String, Any>>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var context: Context
    lateinit var mDataset: List<LinkedTreeMap<String, Any>>

    init {
        this.context = context
        this.mDataset = mDataset
    }
    inner class ViewHolderItem (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView
        var tvDescription: TextView
        var tvCount: TextView
        init {
            tvTitle = itemView.findViewById(R.id.TV_GIT_TITLE)
            tvDescription = itemView.findViewById(R.id.TV_GIT_DESCRIPTION)
            tvCount = itemView.findViewById(R.id.TV_GIT_COUNT   )
        }
    }
    inner class ViewHolderHeader (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivProfile: ImageView
        var tvName: TextView
        init {
            ivProfile = itemView.findViewById(R.id.IV_GIT_USER_PROFILE)
            tvName= itemView.findViewById(R.id.TV_GIT_USER_NAME)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var view: View
        lateinit var viewHolder: RecyclerView.ViewHolder
        when (viewType) {
            B_TYPE_HEADER -> {
                view = LayoutInflater.from(context).inflate(R.layout.item_git_header,  null, false)
                viewHolder = ViewHolderHeader(view)
            }
            B_TYPE_ITEM -> {
                view = LayoutInflater.from(context).inflate(R.layout.item_git_item, null, false)
                viewHolder = ViewHolderItem(view)
            }
        }
        return viewHolder

    }

    override fun getItemCount(): Int {
        return mDataset.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var mData = mDataset.get(position)
        if (position == 0) {
            var viewHolderHeader = holder as ViewHolderHeader
            viewHolderHeader.tvName.text = mData.get("login").toString()
            Picasso.get()
                    .load(mData.get("avatar_url").toString())
                    .into(viewHolderHeader.ivProfile);
        } else {
            var viewHolderItem = holder as ViewHolderItem
            viewHolderItem.tvTitle.text = mData.get("name").toString()
            viewHolderItem.tvDescription.text = mData.get("description").toString()
            viewHolderItem.tvCount.text = mData.get("stargazers_count").toString()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return B_TYPE_HEADER
        } else {
            return B_TYPE_ITEM
        }
    }

    companion object {
        const val ITEM_TYPE_KEY = "itemType"
        const val B_TYPE_HEADER = 1
        const val B_TYPE_ITEM = 2

    }
}