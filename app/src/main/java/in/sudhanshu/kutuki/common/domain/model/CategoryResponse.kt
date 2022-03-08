package `in`.sudhanshu.kutuki.common.domain.model

data class CategoryResponse(
    val code: Int,
    val response: Response
)

data class Response(
    val videoCategories: Map<String, Category>
)


