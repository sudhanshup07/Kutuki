package `in`.sudhanshu.kutuki.ui.main

import `in`.sudhanshu.kutuki.R
import `in`.sudhanshu.kutuki.common.domain.model.Category
import `in`.sudhanshu.kutuki.databinding.RowCategoryBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CategoryAdapter(private val onClickListener: OnClickListener):
    ListAdapter<Category, CategoryAdapter.CategoryItemViewHolder>(DiffCallback) {

    class CategoryItemViewHolder(
        private var binding: RowCategoryBinding

    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Category, onClickListener: OnClickListener) {
            binding.categoryItem = item

            Picasso.get()
                .load(item.image)
                .placeholder(R.drawable.ic_broken_image)
                .resize(500,500)
                .centerCrop()
                .into(binding.imageView)
            binding.root.setOnClickListener {
                onClickListener.onClick(item, it)
            }
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.name == newItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        return CategoryItemViewHolder(
            RowCategoryBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        val item = getItem(position)

//        val imageView = holder.itemView.findViewById<CircleImageView>(R.id.imageView)
//        Picasso.get()
//            .load(item.image)
//            .placeholder(R.drawable.ic_broken_image)
//            .resize(500,500)
//            .centerCrop()
//            .into(imageView)

//        holder.itemView.setOnClickListener {
//            onClickListener.onClick(item, it)
//        }

        holder.bind(item, onClickListener)
    }

    class OnClickListener(val clickListener: (item: Category, view: View) -> Unit) {
        fun onClick(item: Category, view: View) = clickListener(item, view)
    }
}