package `in`.sudhanshu.kutuki.di

import `in`.sudhanshu.kutuki.common.data.remote.KutukiApi
import `in`.sudhanshu.kutuki.common.data.repository.MainRepositoryImpl
import `in`.sudhanshu.kutuki.common.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMainRepository(api: KutukiApi): MainRepository {
        return MainRepositoryImpl(api)
    }
}