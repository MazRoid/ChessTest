package com.mazroid.chesstest.network

import com.mazroid.chesstest.model.ChessModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkServices {

    @GET("config.json")
    fun getAll(): Single<ChessModel>
}