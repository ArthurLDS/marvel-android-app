package br.com.cwi.marvelapp.di

import br.com.cwi.marvelapp.BuildConfig
import br.com.cwi.marvelapp.data.source.remote.MarvelApi
import br.com.cwi.marvelapp.util.HashGenerate
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { createService(get()) }
    single { createRetrofit(get(), BuildConfig.BASE_URL) }
    single { createOkHttpClient() }
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor { chain -> createParametersDefault(chain) }
        .addInterceptor(httpLoggingInterceptor).build()

}

fun createRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(
            MoshiConverterFactory.create(
                getMoshi()
            )
        )
        .build()
}

private fun getMoshi(): Moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .build()

fun createParametersDefault(chain: Interceptor.Chain): Response {
    val timeStamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
    var request = chain.request()
    val builder = request.url.newBuilder()

    builder.addQueryParameter("apikey", BuildConfig.API_PUBLIC)
        .addQueryParameter(
            "hash",
            HashGenerate.generate(timeStamp, BuildConfig.API_PRIVATE, BuildConfig.API_PUBLIC)
        )
        .addQueryParameter("ts", timeStamp.toString())

    request = request.newBuilder().url(builder.build()).build()
    return chain.proceed(request)
}

fun createService(retrofit: Retrofit): MarvelApi {
    return retrofit.create(MarvelApi::class.java)
}