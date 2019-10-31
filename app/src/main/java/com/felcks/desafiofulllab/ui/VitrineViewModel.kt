package com.felcks.desafiofulllab.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felcks.desafiofulllab.common.repository.SearchRepository
import com.felcks.desafiofulllab.common.viewmodel.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VitrineViewModel(private val searchRepository: SearchRepository): ViewModel() {

    val listProducts = MutableLiveData<Response>()

    init {

        listProducts.postValue(Response.loading())
        CoroutineScope(Dispatchers.IO).launch {

            try{
                val list = searchRepository.getProductList(null, 0, 10)

                if(list.isEmpty())
                    listProducts.postValue(Response.empty())
                else
                    listProducts.postValue(Response.success(list))
            }
            catch (t: Throwable){
                listProducts.postValue(Response.error(t))
            }
        }
    }
}