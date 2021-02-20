package com.salvaperez.maaps.presentation.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.salvaperez.maaps.R
import com.salvaperez.maaps.presentation.extensions.showToast
import com.salvaperez.maaps.presentation.utils.Resource
import com.salvaperez.maaps.presentation.model_ui.TransportsModelUi
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity(){

    private val vModel: DetailViewModel by currentScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        loadObservers()
        vModel.onInit(intent.getParcelableExtra(PLACE))
    }

    private fun loadObservers(){
        vModel.placeData.observe(this, Observer { place ->
            when(place.status){
                Resource.Status.SUCCESS -> showPlace(place.data as TransportsModelUi)
                Resource.Status.ERROR -> showError()
            }
        })
    }

    private fun showPlace(place: TransportsModelUi) {
        txTitleTransportPlace.text = place.name
    }

    private fun showError() {
        showToast(getString(R.string.error_retrieve_transports))
        finish()
    }

    companion object{
        const val PLACE = "detailActivity.place"
    }
}