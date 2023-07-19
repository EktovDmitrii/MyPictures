package com.example.mypictures.di

import com.example.mypictures.data.ApiFactory
import com.example.mypictures.data.ApiService
import com.example.mypictures.data.PhotoRepositoryImpl
import com.example.mypictures.domain.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun provideApiServices(): ApiService {
        return ApiFactory.apiService
    }

    @Singleton
    @Provides
    fun provideApiRepository(impl: ApiService): PhotoRepository {
        return PhotoRepositoryImpl(impl)
    }
}