package com.hend.cataloguechallenge.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hend.cataloguechallenge.R
import com.hend.cataloguechallenge.persistance.entities.Category
import kotlinx.android.synthetic.main.item_section.view.*

/**
 * Adapter that handles sectioned list of categories with its own products
 */
class SectionedCategoriesAdapter(private val categoriesList: List<Category>) :
    RecyclerView.Adapter<SectionedCategoriesAdapter.SectionViewHolder>() {

    class SectionViewHolder(val context : Context, itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtSectionName: AppCompatTextView? = itemView.txt_section_name
        private val rvProducts: RecyclerView = itemView.rv_products

        fun bind(category: Category) {
            txtSectionName?.text = category.name
            rvProducts.setHasFixedSize(true)
            rvProducts.isNestedScrollingEnabled = false
            rvProducts.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvProducts.adapter = ProductsAdapter(category.products)
        }

        companion object {
            fun create(parent: ViewGroup): SectionViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_section, parent, false)
                return SectionViewHolder(parent.context,view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        return SectionViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        holder.bind(categoriesList[position])
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }
}