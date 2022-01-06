package com.mazroid.chesstest.model


import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import com.google.gson.annotations.SerializedName

@Parcelize
//@Entity(tableName = "%s")
data class ChessModel(
    @ColumnInfo(name = "trns")
    @SerializedName("trns")
    var trns: List<Trn>
) : Parcelable

@Parcelize
//    @Entity(tableName = "%s")
data class Trn(
    @ColumnInfo(name = "img")
    @SerializedName("img")
    var img: String,
    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String,
    @ColumnInfo(name = "slug")
    @SerializedName("slug")
    var slug: String,
    @ColumnInfo(name = "status")
    @SerializedName("status")
    var status: Int
) : Parcelable
