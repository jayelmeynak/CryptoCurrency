package com.example.cryptocurrency.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WhitePaper(
    @SerializedName("link")
    val link: String,
    @SerializedName("thumbnail")
    val thumbnail: String,

)