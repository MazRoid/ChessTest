package com.mazroid.chesstest.network

import android.content.Context
import android.util.Log
import androidx.annotation.Keep
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.mazroid.chesstest.util.AppConstant
import com.mazroid.chesstest.util.SingletonHolderUtil
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.io.IOException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


@Keep
class RetrofitInstance constructor(val context: Context?) {

    companion object : SingletonHolderUtil<RetrofitInstance, Context>(::RetrofitInstance) {

    }

    private var retrofit: Retrofit? = null
    val cacheSize = (40 * 1024 * 1024).toLong() //40mb
    val OKHTTP_CACHE_DIR_NAME: String = "http.cache.directory"

    fun provideCache(cacheFile: File): Cache = Cache(cacheFile, cacheSize)

    fun provideHttpCacheFile(context: Context): File {
        Log.e(
            "RAJIV_CACHE",
            "cache-Dir: ${context.cacheDir.toString() + File.separator + OKHTTP_CACHE_DIR_NAME}"
        )
        val directory =
            File(context.cacheDir.toString() + File.separator + OKHTTP_CACHE_DIR_NAME)
        if (!directory.exists())
            directory.mkdirs()
        return directory

    }

    /**
     * Create an instance of Retrofit object
     */

    val retrofitInstance: Retrofit
        get() {
            if (retrofit == null) {
                val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BODY

                }

//                val okHttpClient = OkHttpClient.Builder()
                val okHttpClient = unSafeOkHttpClient()
                okHttpClient.addInterceptor(object : Interceptor {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val original = chain.request()
                        val request = original.newBuilder()
                            .header("Accept", "application/json")
                            .header("Content-Type", "application/json")
                            .method(original.method(), original.body())
                            .build()
                        return chain.proceed(request)
                    }
                })
                okHttpClient.readTimeout(12, TimeUnit.SECONDS)
                okHttpClient.connectTimeout(12, TimeUnit.SECONDS)


                okHttpClient.addInterceptor(interceptor)

                val gson = GsonBuilder()
                    .setLenient()
                    .create()

                retrofit = Retrofit.Builder()
                    .baseUrl(AppConstant.base_url)
                    .client(okHttpClient.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit!!
        }

    private fun unSafeOkHttpClient(): OkHttpClient.Builder {
        val okHttpClient = OkHttpClient.Builder()
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts: Array<TrustManager> = arrayOf(object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            if (trustAllCerts.isNotEmpty() && trustAllCerts.first() is X509TrustManager) {
                okHttpClient.sslSocketFactory(
                    sslSocketFactory,
                    trustAllCerts.first() as X509TrustManager
                )
                okHttpClient.hostnameVerifier { _, _ -> true }
            }

            return okHttpClient
        } catch (e: Exception) {
            return okHttpClient
        }
    }

}
