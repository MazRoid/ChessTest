package com.mazroid.chesstest.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mazroid.chesstest.model.ChessModel
import com.mazroid.chesstest.repo.MainRepo


class MainViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "MainViewModel"
    }

    val repo = MainRepo.getInstance(application)

    fun getAll(): MutableLiveData<ChessModel> {

        return repo.getAll()
    }


}
