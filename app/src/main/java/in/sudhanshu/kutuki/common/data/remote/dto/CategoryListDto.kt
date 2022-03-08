package `in`.sudhanshu.kutuki.common.data.remote.dto

import `in`.sudhanshu.kutuki.common.domain.model.CategoryList

data class CategoryListDto(
    val code: Int
){
    fun toCategoryList(): CategoryList{
        return CategoryList(
            code = code
        )
    }
}