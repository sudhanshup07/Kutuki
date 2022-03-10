package `in`.sudhanshu.kutuki.common.domain.repository

import `in`.sudhanshu.kutuki.common.domain.model.CategoryResponse
import `in`.sudhanshu.kutuki.common.domain.model.VideoResponse


sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null): Resource<T>(data)
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
}

interface MainRepository {
    suspend fun getCategories():Resource<CategoryResponse>

    suspend fun getVideos():Resource<VideoResponse>
}