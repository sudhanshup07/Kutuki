package `in`.sudhanshu.kutuki.ui.main

import `in`.sudhanshu.kutuki.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch{
            viewModel.categoryResponse.collect {
               when(it){
                   is GetCategoryListEvent.Success -> {
                        it.data.videoCategories.forEach {list ->
                            Log.e("KYA_AAYA",list.value.name )
                        }
                   }
                   else -> Unit
               }
            }
        }
    }
}