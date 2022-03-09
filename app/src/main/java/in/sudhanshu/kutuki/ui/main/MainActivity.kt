package `in`.sudhanshu.kutuki.ui.main

import `in`.sudhanshu.kutuki.common.MarginItemDecoration
import `in`.sudhanshu.kutuki.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

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
            CategoryAdapter.OnClickListener{ categoryItem, _ ->

            })

        lifecycleScope.launch{
            viewModel.categoryResponse.collect {
               when(it){
                   is GetCategoryListEvent.Success -> {
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