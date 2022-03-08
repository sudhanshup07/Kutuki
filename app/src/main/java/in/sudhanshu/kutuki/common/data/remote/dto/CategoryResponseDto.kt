package `in`.sudhanshu.kutuki.common.data.remote.dto

import `in`.sudhanshu.kutuki.common.domain.model.Category
import `in`.sudhanshu.kutuki.common.domain.model.CategoryResponse
import `in`.sudhanshu.kutuki.common.domain.model.Response

data class CategoryResponseDto(
    val code: Int,
    val response: ResponseDto
){
    fun toCategoryList(): CategoryResponse{
        return CategoryResponse(
            code = code,
            response = response.toResponse()
        )
    }
}

data class ResponseDto(
    val videoCategories: Map<String, Category>
){
    fun toResponse(): Response{
        return Response(
            videoCategories = videoCategories
        )
    }
}