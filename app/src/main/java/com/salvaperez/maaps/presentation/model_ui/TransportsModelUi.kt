package com.salvaperez.maaps.presentation.model_ui

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransportsModelUi(
    val id: String,
    val name: String,
    val latLong: LatLng,
    val icon: Float
): Parcelable