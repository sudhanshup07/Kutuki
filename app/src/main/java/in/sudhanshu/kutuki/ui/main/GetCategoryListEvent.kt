package `in`.sudhanshu.kutuki.ui.main

import `in`.sudhanshu.kutuki.common.domain.model.Response

sealed class GetCategoryListEvent{

    class  Success(val data: Response): GetCategoryListEvent()
    object Loading: GetCategoryListEvent()
    class Failure(val errorText: String): GetCategoryListEvent()
    object Empty: GetCategoryListEvent()
}
