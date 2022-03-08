package `in`.sudhanshu.kutuki.ui.main

import `in`.sudhanshu.kutuki.common.domain.model.Category
import `in`.sudhanshu.kutuki.common.domain.repository.MainRepository
import `in`.sudhanshu.kutuki.common.domain.repository.Resource
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel  @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

    private val _categoryResponse = MutableStateFlow<GetCategoryListEvent>(GetCategoryListEvent.Empty)
    val categoryResponse: StateFlow<GetCategoryListEvent> = _categoryResponse

    init {
      getCategories()
    }

    private fun getCategories(){
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = repository.getCategories()) {
                is Resource.Success -> {
                    Log.e("SUCCESS", "YES")
                    _categoryResponse.value = GetCategoryListEvent.Success(
                        response.data!!.response
                    )
                }

                is Resource.Error -> {
                    Log.e("ERROR", response.message!!)
                }

                else -> Unit
            }
        }
    }
}