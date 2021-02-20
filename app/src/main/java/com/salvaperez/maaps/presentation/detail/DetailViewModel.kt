package com.salvaperez.maaps.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.salvaperez.maaps.presentation.utils.Resource
import com.salvaperez.maaps.presentation.model_ui.TransportsModelUi

class DetailViewModel: ViewModel(){

    val placeData = MutableLiveData<Resource<TransportsModelUi>>()

    fun onInit(place: TransportsModelUi?){
        place?.let {
            placeData.value = Resource.success(place)
        }?: run {
            placeData.value = Resource.error()
        }
    }
}