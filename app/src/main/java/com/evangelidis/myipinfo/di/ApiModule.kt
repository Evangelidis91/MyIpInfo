package com.evangelidis.myipinfo.di

import com.evangelidis.myipinfo.models.api.IpApi
import com.evangelidis.myipinfo.models.api.IpService
import com.evangelidis.myipinfo.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    @Provides
    fun provideIpApi(): IpApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(IpApi::class.java)
    }

    @Provides
    fun provideIpService(): IpService {
        return IpService()
    }
}
