package `in`.sudhanshu.kutuki.common.data.remote

import `in`.sudhanshu.kutuki.common.data.remote.dto.CategoryResponseDto
import `in`.sudhanshu.kutuki.common.data.remote.dto.VideoResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface KutukiApi {

    @GET("v2/5e2bebd23100007a00267e51")
    suspend fun getCategories(): Response<CategoryResponseDto>

    @GET("v2/5e2beb5a3100006600267e4e")
    suspend fun getVideos():Response<VideoResponseDto>

}
