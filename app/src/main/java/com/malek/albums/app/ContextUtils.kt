package com.malek.albums.app

import android.app.Activity
import android.content.res.Configuration
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.malek.albums.AlbumsApplication
import com.malek.albums.BR
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.malek.albums.app.albumList.AlbumItemViewModel
import com.squareup.picasso.Picasso


val Activity.injector
    get() = (application as AlbumsApplication).appComponent


class AutoBindViewHolder(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(autoBindViewModel: AutoBindViewModel) {
        binding.setVariable(BR.model, autoBindViewModel)
    }
}

abstract class AutoBindViewModel {
    abstract val layout: Int
    abstract fun getIdentifier(): Int
    abstract fun areContentsTheSame(other: AutoBindViewModel): Boolean
}


class
AutoBindAdapter : RecyclerView.Adapter<AutoBindViewHolder>() {
    private val autoBindViewModels: MutableList<AutoBindViewModel> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutoBindViewHolder {
        val itemBinding =
            DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                viewType,
                parent,
                false
            )
        return AutoBindViewHolder(itemBinding)
    }

    override fun getItemViewType(position: Int) = autoBindViewModels[position].layout

    override fun getItemCount() = autoBindViewModels.size

    override fun onBindViewHolder(holder: AutoBindViewHolder, position: Int) {
        holder.bind(autoBindViewModels[position])
    }

    fun replaceData(newItems: List<AutoBindViewModel>) {
        val oldList = mutableListOf<AutoBindViewModel>()
        oldList.addAll(autoBindViewModels)
        autoBindViewModels.clear()
        autoBindViewModels.addAll(newItems)
        val diff = DiffUtil.calculateDiff(
            AutoBindViewModelDiffCallBack(
                oldList = oldList,
                newList = autoBindViewModels
            )
        )
        diff.dispatchUpdatesTo(this)
    }
}

@BindingAdapter("bindList", "orientationAware", "lastScrollPosition", requireAll = false)
fun bindList(
    recyclerView: RecyclerView,
    items: List<AutoBindViewModel>?,
    orientationAware: Boolean?,
    lastScrollPosition: Int?
) {

    if (recyclerView.adapter == null) {
        recyclerView.adapter = AutoBindAdapter()
    }
    if (recyclerView.layoutManager == null) {
        orientationAware?.let {
            if (it) {
                val currentOrientation = recyclerView.context.resources.configuration.orientation
                recyclerView.layoutManager =
                    if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                        LinearLayoutManager(recyclerView.context, RecyclerView.VERTICAL, false)
                    } else {
                        GridLayoutManager(recyclerView.context, 2)
                    }
            } else {
                recyclerView.setDefaultLayoutManager()

            }
        } ?: kotlin.run {
            recyclerView.setDefaultLayoutManager()
        }
    }
    lastScrollPosition?.let {
        if (it > -1) {
            recyclerView.scrollToPosition(it)
        }
    }
    items?.let {
        val adapter = recyclerView.adapter
        if (adapter is AutoBindAdapter) {
            adapter.replaceData(it)
        }
    }

}


@BindingAdapter("imageUrl")
fun bindUrlImage(imageView: ImageView, url: String?) {
    Picasso.get().load(url).into(imageView)
}

fun RecyclerView.setDefaultLayoutManager() {
    this.layoutManager = LinearLayoutManager(
        this.context,
        RecyclerView.VERTICAL,
        false
    )
}

class AutoBindViewModelDiffCallBack(
    private val oldList: List<AutoBindViewModel>,
    private val newList: List<AutoBindViewModel>
) :
    DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].getIdentifier() == newList[newItemPosition].getIdentifier()
    }

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        newList[newItemPosition].areContentsTheSame(oldList[oldItemPosition])

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return newList[newItemPosition]
    }
}
