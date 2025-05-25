package com.example.wheel_vet.model

import com.google.gson.annotations.SerializedName

data class Ciudad(
    @SerializedName("idciudad") val idciudad: Int,
    @SerializedName("nombreciudad") val nombreciudad: String
)
