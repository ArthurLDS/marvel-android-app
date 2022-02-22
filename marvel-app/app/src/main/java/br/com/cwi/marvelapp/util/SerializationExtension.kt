package br.com.cwi.marvelapp.util

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.Type

object SerializationExtension {

    val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    inline fun <reified T> String.jsonToListObject(): List<T>? {
        val type: Type = Types.newParameterizedType(List::class.java, T::class.java)
        return moshi.adapter<List<T>>(type).fromJson(this)
    }

    inline fun <reified T> String.jsonToObject(): T? {
        return moshi.adapter<T>(T::class.java).fromJson(this)
    }

    inline fun <reified T> T.objectToJson(): String {
        return moshi.adapter<T>(T::class.java).toJson(this)
    }
}
