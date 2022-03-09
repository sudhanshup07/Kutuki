package `in`.sudhanshu.kutuki.ui.video_playback

import `in`.sudhanshu.kutuki.R
import `in`.sudhanshu.kutuki.databinding.ActivityVideoBinding
import `in`.sudhanshu.kutuki.ui.main.MainViewModel
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player

class VideoActivity : AppCompatActivity(), Player.Listener{

    private var isFull: Boolean = true
    private lateinit var binding: ActivityVideoBinding
    private val viewModel: MainViewModel by viewModels()

    private val url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    private lateinit var exoPlayer: ExoPlayer
    private lateinit var bottomModalSheet: BottomModalSheet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()

        bottomModalSheet = BottomModalSheet()

        val id = intent.getStringExtra("catId") ?: ""

        exoPlayer = ExoPlayer.Builder(this).build()
        binding.idExoPlayerVIew.player = exoPlayer

        val mediaItem: MediaItem = MediaItem.fromUri(url)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()

        exoPlayer.addListener(this)
        binding.idExoPlayerVIew.setControllerVisibilityListener {
            //can be used later
        }

        binding.idExoPlayerVIew.findViewById<ImageButton>(R.id.exo_full).setOnClickListener {
            if(isFull) {
                binding.idExoPlayerVIew.layoutParams.height = 500
                binding.idExoPlayerVIew.layoutParams.width = 1000
            }else{
                binding.idExoPlayerVIew.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                binding.idExoPlayerVIew.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            }
            isFull = !isFull
        }
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)

        //use later
    }

    private fun hideSystemBars() {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
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