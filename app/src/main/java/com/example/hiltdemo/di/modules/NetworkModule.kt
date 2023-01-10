package com.example.hiltdemo.di.modules

import com.example.hiltdemo.network.ApiService
import com.example.hiltdemo.utils.AppConstants
import com.example.hiltdemo.utils.RetrofitNew
import com.example.hiltdemo.utils.RetrofitS
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    @RetrofitNew
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.DEFAULT_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpBuilder.addInterceptor(loggingInterceptor)
        okHttpBuilder.readTimeout(2, TimeUnit.MINUTES)
        okHttpBuilder.connectTimeout(2, TimeUnit.MINUTES)
        okHttpBuilder.writeTimeout(2, TimeUnit.MINUTES)
        return okHttpBuilder.build()
    }


    @Singleton
    @Provides

    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @Singleton
    @RetrofitS
    fun provideNewRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.DEFAULT_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(@RetrofitNew retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}