package com.hend.cataloguechallenge.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hend.cataloguechallenge.R
import com.hend.cataloguechallenge.persistance.entities.Product
import com.hend.cataloguechallenge.ui.details.DetailsActivity
import com.hend.cataloguechallenge.utils.BASE_URL
import com.hend.cataloguechallenge.utils.listen
import com.hend.cataloguechallenge.utils.loadImageUrl

/**
 * Adapter that handles list of products inside one section
 */
class ProductsAdapter(private val productsList: List<Product>) : RecyclerView.Adapter<ProductsAdapter.ItemViewHolder>()  {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtName: AppCompatTextView? = itemView.findViewById(R.id.txt_name)
        private val imgProduct: AppCompatImageView? = itemView.findViewById(R.id.img_product)

        fun bind(product: Product) {
            product.let {
                txtName?.text = it.name
                imgProduct?.loadImageUrl(BASE_URL.plus(it.url))
            }
        }

        companion object {
            fun create(parent: ViewGroup): ItemViewHolder{
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_product, parent, false)
                return ItemViewHolder(
                    view
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.create(parent).listen { position ->
            ContextCompat.startActivity(
                parent.context,
                Intent(parent.context, DetailsActivity::class.java)
                    .putExtra("name", productsList[position].name), null
            )
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(productsList[position])
    }

    override fun getItemCount(): Int {
        return productsList.size
    }
}