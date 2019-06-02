package br.com.roquebuarque.fallingwords.data

import com.google.gson.annotations.SerializedName

data class WordResponse(
    @SerializedName("text_eng") val eng: String,
    @SerializedName("text_spa") val spa: String
)
