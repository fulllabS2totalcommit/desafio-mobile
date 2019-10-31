package com.felcks.desafiofulllab.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.felcks.desafiofulllab.App
import com.felcks.desafiofulllab.R
import com.felcks.desafiofulllab.common.domain.Product
import com.felcks.desafiofulllab.common.viewmodel.Response
import com.felcks.desafiofulllab.common.viewmodel.Status
import com.felcks.desafiofulllab.databinding.ActivityVitrineBinding
import kotlinx.android.synthetic.main.activity_vitrine.*
import org.koin.android.ext.android.inject

class VitrineActivity : AppCompatActivity() {

    private val viewModel: VitrineViewModel by inject()
    private var adapter: VitrineAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityVitrineBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_vitrine
        )
        binding.viewModel = viewModel
        binding.executePendingBindings()

        ll_erro.findViewById<AppCompatImageView>(R.id.iv_refresh).setOnClickListener {
            viewModel.loadProductList(null, 0, 10)
        }

        this.iniciaAdapter(listOf())

        viewModel.listProducts.observe(this, Observer<Response> {
                response -> processResponse(response)
        })
    }

    private fun processResponse(response: Response){
        when(response.status){
            Status.SUCCESS -> {
               iniciaAdapter((response.data as List<*>).filterIsInstance<Product>())
            }
            else -> {}
        }
    }

    private fun iniciaAdapter(listProducts: List<Product>){

        if(this.adapter == null){
            val layoutManager = LinearLayoutManager(App.instance)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            rv_list.layoutManager = layoutManager
            rv_list.setItemViewCacheSize(listProducts.size)

            this.adapter = VitrineAdapter(
                listProducts
            )
            rv_list.adapter = adapter
        }
        else{
            this.adapter?.updateAllItens(listProducts)
        }
    }
}
