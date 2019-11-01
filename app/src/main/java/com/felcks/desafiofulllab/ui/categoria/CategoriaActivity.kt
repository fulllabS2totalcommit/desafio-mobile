package com.felcks.desafiofulllab.ui.categoria

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.felcks.desafiofulllab.api.RestApi
import com.felcks.desafiofulllab.common.repository.CategoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoriaActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {

            try{
                val repo = CategoryRepository(RestApi.api)
                val list = repo.getCategoryList()
                print(list)
            }
            catch (t: Throwable){
            }
            finally {
            }
        }
    }
}