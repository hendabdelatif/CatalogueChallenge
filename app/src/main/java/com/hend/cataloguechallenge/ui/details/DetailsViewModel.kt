package com.hend.cataloguechallenge.ui.details

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.hend.cataloguechallenge.persistance.entities.Product
import com.hend.cataloguechallenge.repository.CatalogueRepository
import com.hend.cataloguechallenge.ui.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * ViewModel to get product details from DB
 */
class DetailsViewModel @ViewModelInject constructor(
    private val catalogueRepository: CatalogueRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel(), LifecycleObserver {

    private val productName = savedStateHandle.get<String>("name") ?: ""

    /** Live Data to get MovieEntity **/
    private val productLiveData: MutableLiveData<Product> = MutableLiveData()
    fun getProduct(): LiveData<Product> = productLiveData

    init {
        viewModelScope.launch {
            catalogueRepository.getCategoryDao().getProductByName(productName)?.let {
                productLiveData.value = it
            }
        }
    }
}
