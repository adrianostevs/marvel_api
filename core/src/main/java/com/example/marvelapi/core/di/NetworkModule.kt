package com.example.marvelapi.core.di

import com.example.marvelapi.core.BuildConfig
import com.example.marvelapi.core.data.remote.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    val hostname = "gateway.marvel.com"

    @Singleton
    @Provides
    fun provideClient(): OkHttpClient = OkHttpClient.Builder()
        .certificatePinner(
            CertificatePinner.Builder()
                .add(hostname, "sha256/Butq4SxnDn7zUeVFTPkJ0Vnftekkd4EsStOYzDvtzOg=")
                .add(hostname, "sha256/980Ionqp3wkYtN9SZVgMzuWQzJta1nfxNPwTem1X0uc=")
                .build()
        )
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .connectTimeout(15, TimeUnit.SECONDS).readTimeout(15, TimeUnit.SECONDS).build()

    @Singleton
    @Provides
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder().baseUrl("https://gateway.marvel.com:443/v1/public/")
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
        return retrofit.create(ApiService::class.java)
    }

}