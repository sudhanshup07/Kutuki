package `in`.sudhanshu.kutuki.ui.main

import `in`.sudhanshu.kutuki.common.domain.repository.MainRepository
import `in`.sudhanshu.kutuki.common.domain.repository.Resource
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel  @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

    private val _btStatus = MutableSharedFlow<Int>(0)
    val btStatus: SharedFlow<Int> = _btStatus

    init {
      getCategories()
    }

    private fun getCategories(){
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = repository.getCategories()) {
                is Resource.Success -> {
                    _btStatus.emit(response.data!!.code)
                }

                else -> Unit
            }
        }
    }
}