package com.osamaaftab.muzz.di

import com.osamaaftab.muzz.presentation.viewmodel.ChatViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel { ChatViewModel(get(), get()) }
}