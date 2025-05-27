package com.example.wheel_vet.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ciudad(
    val idciudad: Int,
    val nombreciudad: String
): Parcelable

