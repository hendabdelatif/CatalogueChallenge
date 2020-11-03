package com.hend.cataloguechallenge.ui.details

import androidx.lifecycle.ViewModelProvider
import com.hend.cataloguechallenge.databinding.ActivityDetailsBinding
import com.hend.cataloguechallenge.ui.base.BaseActivity
import com.hend.cataloguechallenge.utils.BASE_URL
import com.hend.cataloguechallenge.utils.loadImageUrl
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activity to show product details from DB
 */
@AndroidEntryPoint
class DetailsActivity : BaseActivity(){

    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var binding: ActivityDetailsBinding

    override fun init() {
        detailsViewModel = ViewModelProvider(this).get(DetailsViewModel ::class.java)
    }

    override fun initViewBinding() {
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun observeViewModel() {
        detailsViewModel.getProduct().observe(this,  {
            binding.txtName.text = it.name
            binding.txtPrice.text = binding.txtPrice.text.toString().plus(" ").plus(it.salePrice.amount).plus(" ").plus(it.salePrice.currency)
            binding.imgPoster.loadImageUrl(BASE_URL.plus(it.url))
        })
    }
}