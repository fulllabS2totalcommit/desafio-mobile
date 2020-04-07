package com.felcks.desafiofulllab.ui.categoria

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felcks.desafiofulllab.common.domain.Category
import com.felcks.desafiofulllab.common.repository.CategoryRepository
import com.felcks.desafiofulllab.common.viewmodel.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoriaViewModel(private val categoryRepository: CategoryRepository) : ViewModel(){

    val listCategory = MutableLiveData<Response>()
    var loading = ObservableField<Boolean>(false)
    var isError = ObservableField<Boolean>(false)

    private var listCategoryDomain: List<Category> = listOf()

    init {

        listCategory.postValue(Response.loading())
        loadCategories()
    }

    fun loadCategories(){

        CoroutineScope(Dispatchers.IO).launch {

            try{
                loading.set(true)
                isError.set(false)

                val list = categoryRepository.getCategoryList()

                if(list.isEmpty())
                    listCategory.postValue(Response.empty())
                else {

                    listCategoryDomain = list
                    listCategory.postValue(Response.success(
                        list.map {
                            CategoriaDTO(it.name, null, TipoCategoriaDTO.Categoria)
                        }
                    ))
                }
            }
            catch (t: Throwable){
                isError.set(true)
                listCategory.postValue(Response.error(t))
            }
            finally {
                loading.set(false)
            }
        }
    }

    fun selectItem(pos: Int, obj: Any){

        val categoriaDTO = obj as? CategoriaDTO
            ?: throw Throwable("Função não implementada para esse objeto.")

        if(categoriaDTO.tipo == TipoCategoriaDTO.Subcategoria)
            throw Throwable("Pesquisa na subcategoria - função não implementada.")


        val subCategorias = listCategoryDomain[pos].listSubCategories.map {
            CategoriaDTO(it.name,
                null,
                TipoCategoriaDTO.Subcategoria)
        }

        listCategory.value = Response.success(subCategorias)
    }

    fun showHomeCategory(){

        val categorias = this.listCategoryDomain.map {
            CategoriaDTO(it.name,
                null,
                TipoCategoriaDTO.Categoria)
        }

        listCategory.value = Response.success(categorias)

    }

    fun getCurrentType(): TipoCategoriaDTO?{
        return (this.listCategory.value?.data as? List<*>)?.filterIsInstance<CategoriaDTO>()?.first()?.tipo
    }
}