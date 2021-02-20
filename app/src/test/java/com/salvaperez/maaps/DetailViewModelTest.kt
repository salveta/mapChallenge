package com.salvaperez.maaps

import androidx.lifecycle.Observer
import com.salvaperez.maaps.presentation.detail.DetailViewModel
import com.salvaperez.maaps.presentation.utils.Resource
import com.salvaperez.maaps.presentation.model_ui.TransportsModelUi
import com.salvaperez.maaps.presentation.model_ui.toModelUi
import com.salvaperez.maaps.utils.*
import io.mockk.*

class DetailViewModelTest: BaseTest() {

    init {
        val transportPlaces = mockk<Observer<Resource<TransportsModelUi>>>()

        fun initViewModelObservers(vModel: DetailViewModel) {
            vModel.placeData.observeForever(transportPlaces)
        }

        every { transportPlaces.onChanged(any())} just Runs

        Given("A received objet from marker") {
            val vModel = DetailViewModel()
            initViewModelObservers(vModel)

            When("the onInit method is shoot") {
                vModel.onInit(mockedTransport.copy().toModelUi())

                Then("Display data in detail screen") {
                    verify(exactly = 1) {
                        transportPlaces.onChanged(checkAnyResourceSuccess())
                    }

                    verify(exactly = 0) {
                        transportPlaces.onChanged(checkResourceError())
                    }
                }
            }

            When("the onInit method is shoot without data") {
                vModel.onInit(null)

                Then("show error") {
                    verify(exactly = 1) {
                        transportPlaces.onChanged(checkResourceError())
                    }

                    verify(exactly = 0) {
                        transportPlaces.onChanged(checkAnyResourceSuccess())
                    }
                }
            }
        }
    }
}