package `in`.sudhanshu.kutuki.ui.video_playback

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
class VideoViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

    private val _videoResponse = MutableStateFlow<GetVideoListEvent>(GetVideoListEvent.Empty)
    val videoResponse: StateFlow<GetVideoListEvent> = _videoResponse

    init {
        getVideos()
    }

    private fun getVideos(){
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = repository.getVideos()) {
                is Resource.Success -> {

                    Log.e("SUCCESS", "YES")
                    _videoResponse.value = GetVideoListEvent.Success(
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