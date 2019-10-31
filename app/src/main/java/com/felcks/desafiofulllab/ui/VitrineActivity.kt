package com.felcks.desafiofulllab.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.felcks.desafiofulllab.api.RestApi
import com.felcks.desafiofulllab.R
import com.felcks.desafiofulllab.repository.SearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VitrineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vitrine)

        CoroutineScope(Dispatchers.IO).launch {

            val repo = SearchRepository(RestApi.api)
            val list = repo.getProductList(null, 0, 10)
            print(list)
        }
    }
}
