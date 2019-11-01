package com.felcks.desafiofulllab.ui.vitrine

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
import java.text.NumberFormat
import java.util.*

class VitrineAdapter(
    private val activity: Activity,
    private var listProduct: MutableList<Product>):
    RecyclerView.Adapter<VitrineAdapter.MyViewHolder>() {

    private val mLayoutInflater: LayoutInflater = App.instance.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val numberFormat = NumberFormat.getInstance(Locale.GERMANY).apply {
        minimumFractionDigits = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = mLayoutInflater.inflate(R.layout.item_sell_product, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = listProduct.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = listProduct[position]

        holder.itemView.tv_titulo.text = item.name
        holder.itemView.tv_preco.text = "de R$ ${numberFormat.format(item.listPrice)}"
        holder.itemView.tv_preco_lista.text = "POR R$ ${numberFormat.format(item.bestInstallment.total)}"
        holder.itemView.tv_parcelamento.text = "${item.bestInstallment.count}x de ${numberFormat.format(item.bestInstallment.value)}"
        Picasso.with(App.instance).load(item.imagem).into(holder.itemView.iv_foto)

        holder.itemView.iv_foto.setOnClickListener {

            val mBuilder: AlertDialog.Builder = AlertDialog.Builder(activity)
            val mView = mLayoutInflater.inflate(R.layout.custom_dialog_zoom, null)

            val photoView = mView.findViewById<PhotoView>(R.id.imageView)
            photoView.setImageDrawable(holder.itemView.iv_foto.drawable)
            mBuilder.setView(mView)
            val mDialog = mBuilder.create()
            mDialog.show()
        }
    }

    fun updateAllItens(listProducts: List<Product>){

        this.listProduct = listProducts as MutableList<Product>
        notifyDataSetChanged()
    }

    fun addItens(list: List<Product>){

        val initPosition = this.listProduct.size
        this.listProduct.addAll(list)
        notifyItemChanged(initPosition, this.listProduct.size)
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view)
}