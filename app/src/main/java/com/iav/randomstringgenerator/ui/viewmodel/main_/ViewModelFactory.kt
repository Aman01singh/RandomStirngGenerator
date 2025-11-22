package com.iav.randomstringgenerator.ui.viewmodel.main_

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iav.randomstringgenerator.network.NetworkRepository
import com.iav.randomstringgenerator.ui.viewmodel.HomeViewModel

class HomeViewModelFactory(private val repository: NetworkRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }
}
