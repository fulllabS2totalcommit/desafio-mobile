package com.felcks.desafiofulllab.ui.vitrine

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.felcks.desafiofulllab.App
import com.felcks.desafiofulllab.R
import com.felcks.desafiofulllab.common.domain.Product
import com.felcks.desafiofulllab.common.viewmodel.Response
import com.felcks.desafiofulllab.common.viewmodel.Status
import com.felcks.desafiofulllab.databinding.ActivityVitrineBinding
import com.felcks.desafiofulllab.ui.categoria.CategoriaActivity
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
            val layoutManager = GridLayoutManager(App.instance, 2)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            rv_list.layoutManager = layoutManager
            rv_list.setItemViewCacheSize(listProducts.size)

            this.adapter = VitrineAdapter(this, listProducts)
            rv_list.adapter = adapter
        }
        else{
            this.adapter?.updateAllItens(listProducts)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.action_search)?.actionView as SearchView).apply {

            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false)

            val queryTextListener = object : SearchView.OnQueryTextListener {

                override fun onQueryTextChange(newText: String): Boolean {
                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    viewModel.loadProductList(query, 0, 10)
                    return true
                }
            }

            this.setOnQueryTextListener(queryTextListener)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.action_category ->{
                val intent = Intent(this, CategoriaActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
