package `in`.sudhanshu.kutuki.common.data.repository

import `in`.sudhanshu.kutuki.common.data.remote.KutukiApi
import `in`.sudhanshu.kutuki.common.domain.model.CategoryResponse
import `in`.sudhanshu.kutuki.common.domain.repository.MainRepository
import `in`.sudhanshu.kutuki.common.domain.repository.Resource

class MainRepositoryImpl(
    private val api: KutukiApi
): MainRepository {


    override suspend fun getCategories(): Resource<CategoryResponse> {
        return try{
            val response = api.getCategories()
            val result = response.body()?.toCategoryList()

            if(response.isSuccessful){
                Resource.Success(result)
            }else{
                Resource.Error(response.message())
            }
        }catch (e: Exception){
            Resource.Error(e.message ?: "An error occurred.")
        }

    }
}