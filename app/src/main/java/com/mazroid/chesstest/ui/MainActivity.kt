package com.mazroid.chesstest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mazroid.chesstest.databinding.ActivityMainBinding
import com.mazroid.chesstest.model.Trn
import com.mazroid.chesstest.ui.adapter.MainAdapter
import com.mazroid.chesstest.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    var adapterList: MutableList<Trn> = mutableListOf()
    var adapter: MainAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialize()

    }

    private fun initialize() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val lin = LinearLayoutManager(this)
        binding.rvChess.layoutManager = lin
        viewModel.getAll().observe(this, Observer {
            Log.d("Maz == ", "$it")
            setAdapter(it.trns.toMutableList())
        })
    }

    private fun setAdapter(list: MutableList<Trn>) {
        adapterList.clear()
        adapterList.addAll(list)
        if (adapter == null) {
            adapter = MainAdapter(this, adapterList)
            binding.rvChess.adapter = adapter

        }
        binding.rvChess.adapter?.notifyDataSetChanged()

    }
}