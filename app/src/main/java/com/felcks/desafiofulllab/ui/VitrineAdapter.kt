package com.felcks.desafiofulllab.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.felcks.desafiofulllab.App
import com.felcks.desafiofulllab.R
import com.felcks.desafiofulllab.common.domain.Product
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_sell_product.view.*

class VitrineAdapter(private var listProduct: List<Product>):
    RecyclerView.Adapter<VitrineAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = App.instance.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var filteredListProduct = mutableListOf<Product>().apply {
        addAll(listProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = mLayoutInflater.inflate(R.layout.item_sell_product, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = filteredListProduct.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = filteredListProduct[position]

        holder.itemView.tv_titulo.text = item.name
//        holder.itemView.tv_autor.text = item.escritor
//        Picasso.with(App.instance).load(item).into(holder.itemView.iv_foto)

        holder.itemView.iv_foto.setOnClickListener {

//            val mBuilder: AlertDialog.Builder = AlertDialog.Builder(activity)
//            val mView = mLayoutInflater.inflate(R.layout.custom_dialog_zoom, null)
//
//            val photoView = mView.findViewById<PhotoView>(R.id.imageView)
//            photoView.setImageDrawable(holder.itemView.iv_foto.drawable)
//            mBuilder.setView(mView)
//            val mDialog = mBuilder.create()
//            mDialog.show()
        }
    }

    fun filtro(text: String){

//        listaLivroFiltrada.removeAll { true }
//
//        for(item in listaLivro){
//
//            if(text == ""){
//                listaLivroFiltrada.add(item)
//            }
//            else if(item.nome.contains(text, ignoreCase = true)){
//                listaLivroFiltrada.add(item)
//            }
//        }
//
//        notifyDataSetChanged()
    }

    fun updateAllItens(listProducts: List<Product>){

        this.listProduct = listProducts
        this.filteredListProduct.clear()
        this.filteredListProduct.addAll(this.listProduct)

        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view)
}