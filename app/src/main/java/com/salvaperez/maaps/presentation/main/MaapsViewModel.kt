package com.salvaperez.maaps.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.salvaperez.maaps.domain.model.TransportsModel
import com.salvaperez.maaps.domain.use_case.GetTransportUseCase
import com.salvaperez.maaps.presentation.utils.Event
import com.salvaperez.maaps.presentation.utils.Resource
import com.salvaperez.maaps.presentation.model_ui.TransportsModelUi
import com.salvaperez.maaps.presentation.model_ui.toModelUi
import kotlinx.coroutines.launch

class MaapsViewModel(private val getTransportUseCase: GetTransportUseCase): ViewModel() {

    private var transportListCache: List<TransportsModelUi> = emptyList()

    private val _moveCamera: MutableLiveData<Event<LatLng>> by lazy { MutableLiveData<Event<LatLng>>() }
    val moveCamera : LiveData<Event<LatLng>> get() = _moveCamera

    private val _onClickInfoWindow: MutableLiveData<Event<TransportsModelUi>> by lazy { MutableLiveData<Event<TransportsModelUi>>() }
    val onClickInfoWindow : LiveData<Event<TransportsModelUi>> get() = _onClickInfoWindow

    private val _transportPlaces: MutableLiveData<Resource<TransportsModelUi>> by lazy { MutableLiveData<Resource<TransportsModelUi>>() }
    val transportPlaces : LiveData<Resource<TransportsModelUi>> get() = _transportPlaces


    fun onInit(){
        showHideLoading(true)

        viewModelScope.launch {
            getTransportUseCase(
                onGetErrorTransport = {showHideLoading(false)},
                onGetTransportSuccess = {getTransportList(it)}
            )
        }
    }

    fun getTransportList(transportList: List<TransportsModel>?){
        if(!transportList.isNullOrEmpty()) {
            this.transportListCache = transportList.map { it.toModelUi() }
            _moveCamera.value = Event(transportList.first().toModelUi().latLong)

            for(transport in transportList){
                _transportPlaces.value = Resource.success(transport.toModelUi())
            }
            showHideLoading(false)
        }else{
            _transportPlaces.value = Resource.error()
            showHideLoading(false)
        }
    }

    private fun showHideLoading(isLoading: Boolean){
        _transportPlaces.value = Resource.loading(isLoading)
    }

    fun clickOnInfoWindow(latLng: LatLng){
        for (place in transportListCache) {
            if (latLng == place.latLong) {
                _onClickInfoWindow.value = Event((place))
            }
        }
    }
}