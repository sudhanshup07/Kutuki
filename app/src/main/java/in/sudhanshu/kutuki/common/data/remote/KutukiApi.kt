package `in`.sudhanshu.kutuki.common.data.remote

import `in`.sudhanshu.kutuki.common.data.remote.dto.CategoryResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface KutukiApi {

    @GET("v2/5e2bebd23100007a00267e51")
    suspend fun getCategories(): Response<CategoryResponseDto>

}
