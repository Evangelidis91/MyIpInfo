package com.evangelidis.myipinfo.models

import com.google.gson.annotations.SerializedName

data class InfoModel(
    @SerializedName("title") val title: String,
    @SerializedName("info")val info: String
)
