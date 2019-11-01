package com.felcks.desafiofulllab.ui.categoria

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.felcks.desafiofulllab.App
import com.felcks.desafiofulllab.R
import com.felcks.desafiofulllab.common.viewmodel.Response
import com.felcks.desafiofulllab.common.viewmodel.Status
import com.felcks.desafiofulllab.databinding.ActivityCategoriaBinding
import kotlinx.android.synthetic.main.activity_categoria.*
import org.koin.android.ext.android.inject

class CategoriaActivity: AppCompatActivity(){

    private val viewModel: CategoriaViewModel by inject()
    private var adapter: CategoriaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityCategoriaBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_categoria
        )
        binding.viewModel = viewModel
        binding.executePendingBindings()

        ll_erro.findViewById<AppCompatImageView>(R.id.iv_refresh).setOnClickListener {
            viewModel.loadCategories()
        }

        this.iniciaAdapter(listOf())

        viewModel.listCategory.observe(this, Observer<Response> {
                response -> processResponse(response)
        })
    }

    private fun processResponse(response: Response){
        when(response.status){
            Status.SUCCESS -> {
                iniciaAdapter((response.data as List<*>).filterIsInstance<CategoriaDTO>())
            }
            else -> {}
        }
    }

    private fun iniciaAdapter(listCategory: List<CategoriaDTO>){

        if(this.adapter == null){
            val layoutManager = LinearLayoutManager(App.instance)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            rv_list.layoutManager = layoutManager
            rv_list.setItemViewCacheSize(listCategory.size)

            this.adapter = CategoriaAdapter(listCategory, categoriaClickListener)
            rv_list.adapter = adapter
        }
        else{
            this.adapter?.updateAllItens(listCategory)
        }
    }

    private fun updateToolbarTitle(){

        val tipoCategoria = viewModel.getCurrentType()
        supportActionBar?.title = tipoCategoria?.name ?: "Categoria"
    }

    private val categoriaClickListener = object : TwoParametersClickListener {
        override fun onClick(pos: Int, obj: Any) {

            try{
                viewModel.selectItem(pos, obj)
                updateToolbarTitle()
            }
            catch (e: Throwable){
                Toast.makeText(this@CategoriaActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val actionBar : ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {

        val tipoCategoria = viewModel.getCurrentType()
        if(tipoCategoria == TipoCategoriaDTO.Categoria)
            super.onBackPressed()
        else{
            try{
                viewModel.showHomeCategory()
                updateToolbarTitle()
            }
            catch (e: Throwable){
                Toast.makeText(this@CategoriaActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}