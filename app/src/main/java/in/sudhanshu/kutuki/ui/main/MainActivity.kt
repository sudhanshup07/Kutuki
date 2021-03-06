package `in`.sudhanshu.kutuki.ui.main

import `in`.sudhanshu.kutuki.common.MarginItemDecoration
import `in`.sudhanshu.kutuki.databinding.ActivityMainBinding
import `in`.sudhanshu.kutuki.ui.video_playback.VideoActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    companion object{
        const val CATEGORY_NAME_KEY = "CAT_NAME_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerCategories.addItemDecoration(MarginItemDecoration(48))
        binding.recyclerCategories.layoutManager = GridLayoutManager(
            this,
            2,
            GridLayoutManager.HORIZONTAL,
            false
        )
        binding.recyclerCategories.adapter = CategoryAdapter(
            CategoryAdapter.OnClickListener{ category, _->

                Intent(this, VideoActivity::class.java).apply {
                    putExtra(CATEGORY_NAME_KEY, category.name)
                    startActivity(this)
                }
            })

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.categoryResponse.collect {
                    when (it) {
                        is GetCategoryListEvent.Success -> {
                            val list = it.data.videoCategories.map { item ->
                                item.value
                            }

                            (binding.recyclerCategories.adapter as CategoryAdapter).submitList(list)
                        }

                        is GetCategoryListEvent.Failure -> {
                            Toast.makeText(
                                this@MainActivity,
                                it.errorText,
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                        else -> Unit
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loading.collect {
                    if (it) {
                        binding.progressBar.visibility = View.VISIBLE
                    } else {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }
}