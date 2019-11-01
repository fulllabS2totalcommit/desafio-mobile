package com.felcks.desafiofulllab.di

import com.felcks.desafiofulllab.api.RestApi
import com.felcks.desafiofulllab.common.repository.CategoryRepository
import com.felcks.desafiofulllab.common.repository.SearchRepository
import com.felcks.desafiofulllab.ui.categoria.CategoriaViewModel
import com.felcks.desafiofulllab.ui.vitrine.VitrineViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object DependencyModules {

    val appModule = module{

        single { RestApi.api }

        single { SearchRepository(get()) }
        viewModel { VitrineViewModel(get()) }

        single { CategoryRepository(get()) }
        viewModel { CategoriaViewModel(get()) }
    }
}