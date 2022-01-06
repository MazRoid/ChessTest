package com.mazroid.chesstest.ui.adapter

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mazroid.chesstest.R
import com.mazroid.chesstest.databinding.PotItemBinding
import com.mazroid.chesstest.model.Trn
import com.mazroid.chesstest.util.AppConstant
import org.jetbrains.annotations.NotNull
import java.util.*

class MainAdapter(
    val context: Activity,
    var list: List<Trn>
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    val TAG = "MainAdapter"

    init {}

    override fun onCreateViewHolder(@NotNull parent: ViewGroup, viewType: Int): ViewHolder {

        val itemBinding = PotItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(itemBinding)

    }

    @UseExperimental(ExperimentalStdlibApi::class)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list!!.get(position)
        holder.bind(data)
    }


    inner class ViewHolder(val itemBinding: PotItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(data: Trn) {

            itemBinding.tvName.text = "${data.name} "
            itemBinding.tvYear.text = "${AppConstant.getYear(data.slug)} "

            itemBinding.imageView.visibility = View.VISIBLE
            Glide.with(context)
                .load(data.img)
                .apply(
                    RequestOptions()
                        .centerCrop().error(R.drawable.ic_launcher_background)
                ).into(itemBinding.imageView)
        }
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

}
