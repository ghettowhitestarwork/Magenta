package com.ghettowhitestar.magentatest.di

import android.app.Service
import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.ghettowhitestar.magentatest.api.PicsumApi
import com.ghettowhitestar.magentatest.db.AppDatabase
import com.ghettowhitestar.magentatest.db.LikedPhotoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//Основной DI модуль приложения
@Module
@InstallIn(ApplicationComponent::class)
object AppModule  {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit =
        Retrofit.Builder()
            .baseUrl(PicsumApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    fun checkNetwork(@ApplicationContext appContext: Context):ConnectivityManager{
        var connectivity : ConnectivityManager? = null
        connectivity = appContext.getSystemService(Service.CONNECTIVITY_SERVICE)as ConnectivityManager
        return connectivity
    }

    @Provides
    @Singleton
    fun providePicsumApi(retrofit: Retrofit) : PicsumApi =
        retrofit.create(PicsumApi::class.java)

    @Provides
    @Singleton
    fun provideLikedPhotoDao(appDatabase: AppDatabase): LikedPhotoDao {
        return appDatabase.likedPhotoDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "picsum"
        ).build()
    }

}