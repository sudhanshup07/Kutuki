package `in`.sudhanshu.kutuki.ui.video_playback

import `in`.sudhanshu.kutuki.R
import `in`.sudhanshu.kutuki.common.MarginItemDecoration
import `in`.sudhanshu.kutuki.common.domain.model.Video
import `in`.sudhanshu.kutuki.databinding.ActivityVideoBinding
import `in`.sudhanshu.kutuki.ui.main.MainActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VideoActivity : AppCompatActivity(), Player.Listener{

    private var isFull: Boolean = true
    private lateinit var binding: ActivityVideoBinding
    private val viewModel: VideoViewModel by viewModels()

    private lateinit var exoPlayer: ExoPlayer
    private var categoryName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()
        categoryName = intent.getStringExtra(MainActivity.CATEGORY_NAME_KEY) ?: ""

        setUpExoplayer()
        setUpRecyclerView()
        observeVideoResponse()
    }

    private fun setUpRecyclerView() {
        binding.recyclerVideo.addItemDecoration(MarginItemDecoration(20))
        binding.recyclerVideo.layoutManager = GridLayoutManager(
            this,
            1,
            GridLayoutManager.HORIZONTAL,
            false
        )
        binding.recyclerVideo.adapter = VideoPlaylistAdapter(
            VideoPlaylistAdapter.OnClickListener{ _, _, position ->

                if(exoPlayer.isPlaying){
                    exoPlayer.seekToDefaultPosition(position)
                }
            })
    }

   private fun setUpExoplayer() {
        exoPlayer = ExoPlayer.Builder(this).build()
        binding.idExoPlayerVIew.player = exoPlayer

        exoPlayer.addListener(this)
        binding.idExoPlayerVIew.setControllerVisibilityListener {
            //can be used later
        }

       binding.idExoPlayerVIew.findViewById<ImageView>(R.id.exo_full).setOnClickListener {
           if(isFull) {

               (it as ImageView).setImageResource(R.drawable.ic_fullscreen)
               binding.idExoPlayerVIew.layoutParams.width = 1000
               binding.recyclerVideo.visibility = View.VISIBLE

           }else{
               (it as ImageView).setImageResource(R.drawable.ic_fullscreen_exit)
               binding.idExoPlayerVIew.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
               binding.recyclerVideo.visibility = View.GONE
           }
           isFull = !isFull
       }
    }

    private fun observeVideoResponse() {
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.videoResponse.collect {

                    when(it){

                        is GetVideoListEvent.Success -> {

                            val list = it.data.videos.map { item ->
                                item.value
                            }.filter { video ->
                                video.categories.split(",").contains(categoryName)
                            }

                            (binding.recyclerVideo.adapter as VideoPlaylistAdapter).submitList(list)

                            playVideo(list)
                        }

                        else -> Unit
                    }
                }
            }
        }
    }

    private fun playVideo(list: List<Video>) {

        list.forEach {
            val mediaItem: MediaItem = MediaItem.Builder()
                .setUri(it.videoURL)
                .setMediaId(it.title)
                .build()
            exoPlayer.addMediaItem(mediaItem)
        }

        exoPlayer.prepare()
        exoPlayer.play()
    }

    private fun hideSystemBars() {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    override fun onPause() {
        super.onPause()
        if(exoPlayer.isPlaying) {
            exoPlayer.pause()
        }
    }

    override fun onStop() {
        super.onStop()
        if(exoPlayer.isPlaying) {
            exoPlayer.stop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
    }
}