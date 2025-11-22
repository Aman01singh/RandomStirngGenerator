package com.iav.randomstringgenerator.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iav.randomstringgenerator.data.remote.response.random_string_res.RandomString
import com.iav.randomstringgenerator.network.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: NetworkRepository) : ViewModel() {

    val strings = MutableLiveData<MutableList<RandomString>>(mutableListOf())
    val error = MutableLiveData<String?>()
    val isLoading = MutableLiveData<Boolean>()


    fun generateRandomString() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)

            val result = repository.generateString()

            result.onSuccess { data ->
                val currentList = strings.value ?: mutableListOf()
                currentList.add(data)
                strings.postValue(currentList)
                error.postValue(null)
            }.onFailure {
                error.postValue(it.message ?: "Unknown error")
            }

            isLoading.postValue(false)
        }
    }


    fun deleteItem(position: Int) {
        val list = strings.value ?: return
        list.removeAt(position)
        strings.value = list
    }

    fun clearAll() {
        strings.value = mutableListOf()
    }
}
