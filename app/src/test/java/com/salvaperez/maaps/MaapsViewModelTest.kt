package com.salvaperez.maaps

import androidx.lifecycle.Observer
import com.google.android.gms.maps.model.LatLng
import com.salvaperez.maaps.domain.use_case.GetTransportUseCase
import com.salvaperez.maaps.presentation.main.MaapsViewModel
import com.salvaperez.maaps.presentation.utils.Event
import com.salvaperez.maaps.presentation.utils.Resource
import com.salvaperez.maaps.presentation.model_ui.TransportsModelUi
import com.salvaperez.maaps.presentation.model_ui.toModelUi
import com.salvaperez.maaps.utils.*
import io.mockk.*

class MaapsViewModelTest: BaseTest() {

    init {
        var place = listOf(mockedTransport.copy())
        val getTransportUseCase = mockk<GetTransportUseCase>()
        val moveCamera = mockk<Observer<Event<LatLng>>>()
        val onClickInfoWindow = mockk<Observer<Event<TransportsModelUi>>>()
        val transportPlaces = mockk<Observer<Resource<TransportsModelUi>>>()

        fun initViewModelObservers(vModel: MaapsViewModel) {
            vModel.transportPlaces.observeForever(transportPlaces)
            vModel.moveCamera.observeForever(moveCamera)
            vModel.onClickInfoWindow.observeForever(onClickInfoWindow)
        }

        every { moveCamera.onChanged(any()) } just Runs
        every { transportPlaces.onChanged(any())} just Runs
        every { onClickInfoWindow.onChanged(any())} just Runs

        Given("Camera move if recive a Transport Object") {
            val vModel = MaapsViewModel(getTransportUseCase)
            initViewModelObservers(vModel)

            When("place is ready to be displayed") {
                place = listOf(mockedTransport.copy())
                vModel.getTransportList(place)

                Then("Get transport places display it and move the camera ") {
                    verify(exactly = 1) {
                        moveCamera.onChanged(any())
                        transportPlaces.onChanged(checkAnyResourceSuccess())
                        transportPlaces.onChanged(checkResourceLoading())
                    }

                    verify(exactly = 0) {
                        transportPlaces.onChanged(checkResourceError())
                    }
                }
            }

            When("No places found it") {
                vModel.getTransportList(emptyList())

                Then("Show error") {
                    verify(exactly = 1) {
                        transportPlaces.onChanged(checkResourceLoading())
                        transportPlaces.onChanged(checkResourceError())
                    }

                    verify(exactly = 0) {
                        moveCamera.onChanged(any())
                        transportPlaces.onChanged(checkAnyResourceSuccess())
                    }
                }
            }

            When("Go to detail screen") {
                val transportList = place
                vModel.getTransportList(transportList)
                val latLng = LatLng(2354.2, 3523535.453)

                vModel.clickOnInfoWindow(latLng)

                Then("User click in marker detail") {
                    verify(exactly = 1) {
                        onClickInfoWindow.onChanged(any())
                    }
                }
            }
        }
    }
}