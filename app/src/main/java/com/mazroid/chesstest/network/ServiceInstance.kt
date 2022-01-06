package com.mazroid.chesstest.network

import android.content.Context
import androidx.annotation.Keep
import com.mazroid.chesstest.util.SingletonHolderUtil

class ServiceInstance constructor(val context: Context?){


    companion object : SingletonHolderUtil<ServiceInstance, Context>(::ServiceInstance)

    var serviceInstance: NetworkServices? = null


    val service: NetworkServices?
        get() {
            if (serviceInstance == null) {
                return RetrofitInstance(context).retrofitInstance.create(NetworkServices::class.java)
            }
            return serviceInstance
        }

}
