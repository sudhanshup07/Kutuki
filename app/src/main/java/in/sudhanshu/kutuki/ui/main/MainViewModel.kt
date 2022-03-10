package `in`.sudhanshu.kutuki.ui.main

import `in`.sudhanshu.kutuki.common.domain.repository.MainRepository
import `in`.sudhanshu.kutuki.common.domain.repository.Resource
import `in`.sudhanshu.kutuki.ui.video_playback.GetVideoListEvent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel  @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

    private val _categoryResponse = MutableStateFlow<GetCategoryListEvent>(GetCategoryListEvent.Empty)
    val categoryResponse: StateFlow<GetCategoryListEvent> = _categoryResponse

    private val _loading =  MutableSharedFlow<Boolean>(0)
    val loading: SharedFlow<Boolean> = _loading

    init {
        getCategories()
    }

    private fun getCategories(){
        viewModelScope.launch(Dispatchers.IO) {

            _loading.emit(true)

            when (val response = repository.getCategories()) {

                is Resource.Success -> {
                    _categoryResponse.value = GetCategoryListEvent.Success(
                        response.data!!.response
                    )
                    _loading.emit(false)
                }

                is Resource.Error -> {
                    _loading.emit(false)
                }

                else -> Unit
            }
        }
    }


}