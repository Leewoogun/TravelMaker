package com.gumibom.travelmaker.di


import com.gumibom.travelmaker.data.api.google.GoogleLocationSearchService
import com.gumibom.travelmaker.data.api.kakao.KakaoLocationSearchService
import com.gumibom.travelmaker.data.api.login.LoginService
import com.gumibom.travelmaker.data.api.meeting.MeetingService
import com.gumibom.travelmaker.data.api.naver.NaverLocationSearchService

import com.gumibom.travelmaker.data.api.signup.SignupService
import com.gumibom.travelmaker.util.ApplicationClass
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NaverRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class GoogleRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class KakaoRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MainRetrofit

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    @NaverRetrofit
    fun provideNaverRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(ApplicationClass.NAVER_LOCATION_SEARCH_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()

    @Provides
    @Singleton
    @MainRetrofit
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(ApplicationClass.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()

    @Provides
    @Singleton
    @GoogleRetrofit
    fun provideGoogleRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(ApplicationClass.GOOGLE_GEOCODE_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()

    @Provides
    @Singleton
    @KakaoRetrofit
    fun provideKakaoRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(ApplicationClass.KAKAO_LOCATION_SEARCH_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()

    @Provides
    @Singleton
    fun provideNaverLocationSearchService(@NaverRetrofit retrofit: Retrofit) : NaverLocationSearchService {
        return retrofit.create(NaverLocationSearchService::class.java)
    }

    @Provides
    @Singleton
    fun provideGoogleLocationSearchService(@GoogleRetrofit retrofit: Retrofit) : GoogleLocationSearchService{
        return retrofit.create(GoogleLocationSearchService::class.java)
    }

    @Provides
    @Singleton
    fun provideKakaoService(@KakaoRetrofit retrofit : Retrofit) : KakaoLocationSearchService {
        return retrofit.create(KakaoLocationSearchService::class.java)
    }

    @Provides
    @Singleton
    fun provideSignupService(@MainRetrofit retrofit : Retrofit) : SignupService {
        return retrofit.create(SignupService::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginService(@MainRetrofit retrofit : Retrofit) : LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Provides
    @Singleton
    fun provideMeetingService(@MainRetrofit retrofit : Retrofit) : MeetingService {
        return retrofit.create(MeetingService::class.java)
    }
}