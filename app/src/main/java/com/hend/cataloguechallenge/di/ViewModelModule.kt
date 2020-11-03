package com.hend.cataloguechallenge.di

import androidx.lifecycle.SavedStateHandle
import com.hend.cataloguechallenge.network.api.categories.CategoriesListService
import com.hend.cataloguechallenge.persistance.dao.CategoryDao
import com.hend.cataloguechallenge.repository.CatalogueRepository
import com.hend.cataloguechallenge.ui.catalogue.CatalogueViewModel
import com.hend.cataloguechallenge.ui.details.DetailsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Module that provides instances of ViewModels
 */
@ExperimentalCoroutinesApi
@Module
@InstallIn(ActivityRetainedComponent::class)
object ViewModelModule {

    @Provides
    @ActivityRetainedScoped
    fun provideCatalogueViewModel(catalogueRepository: CatalogueRepository) : CatalogueViewModel {
        return CatalogueViewModel(catalogueRepository)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideDetailsViewModel(catalogueRepository: CatalogueRepository) : DetailsViewModel {
        return DetailsViewModel(catalogueRepository, SavedStateHandle())
    }

}
