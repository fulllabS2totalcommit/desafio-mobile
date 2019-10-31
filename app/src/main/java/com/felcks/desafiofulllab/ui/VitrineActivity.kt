package com.felcks.desafiofulllab.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafio_lemobs_mobile_gabarito.api.RestApi
import com.felcks.desafiofulllab.R
import com.felcks.desafiofulllab.api_model.SearchRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VitrineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vitrine)

        CoroutineScope(Dispatchers.IO).launch {

            val response = RestApi.api.postResgataListagemProduto(
                SearchRequest(
                    null,
                    0,
                    1)
            )

            print(response)
        }
    }
}
