package com.mazroid.chesstest.repo

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.mazroid.chesstest.model.ChessModel
import com.mazroid.chesstest.network.ServiceInstance
import com.mazroid.chesstest.util.SingletonHolderUtil
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainRepo(val context: Context) {

    private val bag: CompositeDisposable = CompositeDisposable()
    var gson = Gson()

    companion object :
        SingletonHolderUtil<MainRepo, Context>(::MainRepo) {
        val TAG = "MainRepo"
    }


    init {
        try {
        } catch (e: Exception) {
            Log.d(TAG, ":Error ")
        }
    }

    fun getAll(): MutableLiveData<ChessModel> {
        val data = MutableLiveData<ChessModel>()
        bag.add(
            ServiceInstance.getInstance(context).service?.getAll()
            !!.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    Log.e(TAG, "API-Success: ${it}")
                    data.postValue(it!!)

                }, { t ->
                    Log.e(TAG, "API-Error3: ${t.localizedMessage}")
                    data.postValue(null)

                })
        )
        return data
    }


}
