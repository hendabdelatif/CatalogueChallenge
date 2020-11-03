package com.hend.cataloguechallenge.ui.catalogue

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hend.cataloguechallenge.databinding.ActivityCatalogueBinding
import com.hend.cataloguechallenge.network.response.Resource
import com.hend.cataloguechallenge.ui.adapters.SectionedCategoriesAdapter
import com.hend.cataloguechallenge.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Activity that contains sectioned list of categories with their own products
 */
@AndroidEntryPoint
class CatalogueActivity : BaseActivity() {

    private lateinit var viewModel: CatalogueViewModel
    private lateinit var binding: ActivityCatalogueBinding

    override fun init() {
        viewModel = ViewModelProvider(this).get(CatalogueViewModel::class.java)
    }

    override fun initViewBinding() {
        binding = ActivityCatalogueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvCategories.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    @ExperimentalCoroutinesApi
    override fun observeViewModel() {
        viewModel.categoriesList.observe(this, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    showLoading()
                }
                Resource.Status.SUCCESS -> {
                    hideLoading()

                    with(binding.rvCategories) {
                        adapter = it.data?.let { categoriesList ->
                            SectionedCategoriesAdapter(categoriesList)
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    hideLoading()
                    showError(it.message!!)
                }
            }
        })
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showError(msg: String) {
        binding.errorMessage.visibility = View.VISIBLE
        binding.errorMessage.text = msg
    }
}