package com.felcks.desafiofulllab.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.felcks.desafiofulllab.R
import com.felcks.desafiofulllab.common.domain.Product
import com.felcks.desafiofulllab.common.viewmodel.Response
import com.felcks.desafiofulllab.common.viewmodel.Status
import org.koin.android.ext.android.inject

class VitrineActivity : AppCompatActivity() {

    private val viewModel: VitrineViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vitrine)

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
        print(listProducts)
    }
}
