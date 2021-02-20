package com.salvaperez.maaps.presentation.model_ui

import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.salvaperez.maaps.domain.model.TransportsModel

fun TransportsModel.toModelUi(): TransportsModelUi {
    return TransportsModelUi(
        id = id,
        name = name,
        latLong = convertLatLong(longitud, latitud),
        icon = getIcon(companyZoneId)
    )
}

private fun convertLatLong(long: Double, lat: Double ): LatLng {
    return LatLng(lat, long)
}

private fun getIcon(id: Int): Float {
    return when (id) {
        582 -> { BitmapDescriptorFactory.HUE_ORANGE }
        378 -> { BitmapDescriptorFactory.HUE_BLUE }
        402 -> { BitmapDescriptorFactory.HUE_GREEN}
        545 -> { BitmapDescriptorFactory.HUE_MAGENTA }
        467 -> { BitmapDescriptorFactory.HUE_AZURE }
        473 -> { BitmapDescriptorFactory.HUE_ROSE }
        412 -> { BitmapDescriptorFactory.HUE_VIOLET }
        else -> { BitmapDescriptorFactory.HUE_ORANGE }
    }
}

