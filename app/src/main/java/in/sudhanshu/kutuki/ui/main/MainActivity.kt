package `in`.sudhanshu.kutuki.ui.main

import `in`.sudhanshu.kutuki.common.MarginItemDecoration
import `in`.sudhanshu.kutuki.common.domain.model.Category
import `in`.sudhanshu.kutuki.common.domain.model.CategoryResponse
import `in`.sudhanshu.kutuki.databinding.ActivityMainBinding
import `in`.sudhanshu.kutuki.ui.video_playback.VideoActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    lateinit var idList: List<String>

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
            CategoryAdapter.OnClickListener{ _, _, position ->

                Intent(this, VideoActivity::class.java).apply {
                    putExtra("catId", idList[position])
                    startActivity(this)
                }
            })

        lifecycleScope.launch{
            viewModel.categoryResponse.collect {
               when(it){
                   is GetCategoryListEvent.Success -> {

                       lifecycleScope.launch(Dispatchers.IO) {
                           idList = it.data.videoCategories.map { item ->
                               item.key
                           }
                       }
                        val list = it.data.videoCategories.map { item ->
                            item.value
                        }

                       (binding.recyclerCategories.adapter as CategoryAdapter).submitList(list)
                   }
                   else -> Unit
               }
            }
        }
    }
}