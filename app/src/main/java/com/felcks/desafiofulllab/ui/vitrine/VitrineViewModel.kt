package com.felcks.desafiofulllab.ui.vitrine

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felcks.desafiofulllab.common.repository.SearchRepository
import com.felcks.desafiofulllab.common.viewmodel.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VitrineViewModel(private val searchRepository: SearchRepository): ViewModel() {

    val listProducts = MutableLiveData<Response>()
    var loading = ObservableField<Boolean>(false)
    var isError = ObservableField<Boolean>(false)

    init {

        listProducts.postValue(Response.loading())
        loadProductList(null, 0, 10)
    }

    fun loadProductList(query: String?, offSet: Int, size: Int){

        CoroutineScope(Dispatchers.IO).launch {

            try{
                loading.set(true)
                isError.set(false)

                val list = searchRepository.getProductList(query, offSet, size)

                if(list.isEmpty())
                    listProducts.postValue(Response.empty())
                else
                    listProducts.postValue(Response.success(list))
            }
            catch (t: Throwable){
                isError.set(true)
                listProducts.postValue(Response.error(t))
            }
            finally {
                loading.set(false)
            }
        }
    }
}