package `in`.sudhanshu.kutuki.ui.video_playback

import `in`.sudhanshu.kutuki.R
import `in`.sudhanshu.kutuki.common.domain.model.Video
import `in`.sudhanshu.kutuki.databinding.RowVideoBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class VideoPlaylistAdapter(private val onClickListener: OnClickListener):
    ListAdapter<Video, VideoPlaylistAdapter.CategoryItemViewHolder>(DiffCallback) {

    class CategoryItemViewHolder(
        private var binding: RowVideoBinding

    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Video, onClickListener: OnClickListener, position: Int) {

            if(item.isPlaying){
                binding.container.background = ContextCompat.getDrawable(itemView.context,R.drawable.background_playing_video)
            }else{
                binding.container.background = null
            }
            Picasso.get()
                .load(item.thumbnailURL)
                .placeholder(R.drawable.ic_broken_image)
                .resize(800,450)
                .centerCrop()
                .into(binding.imageView)

           binding.root.setOnClickListener {
                onClickListener.onClick(item, it, position)
           }
            binding.executePendingBindings()

        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem.title == newItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        return CategoryItemViewHolder(
            RowVideoBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickListener, position)
    }

    class OnClickListener(val clickListener: (item: Video, view: View, position: Int) -> Unit) {
        fun onClick(item: Video, view: View, position: Int) = clickListener(item, view, position)
    }
}