package com.example.mainactivity

import com.google.gson.annotations.SerializedName

data class PersonajeResponse(
    @SerializedName("count")
    var count : Int,
    @SerializedName("next")
    var next : String?,
    var previous : String?,
    @SerializedName("results")
    var results : List<Personaje>
)

data class Personaje(
    @SerializedName("name")
    var name : String,
    @SerializedName("height")
    var height : String,
    @SerializedName("mass")
    var mass : String,  //Debe de ser String porque hay algunos datos que vienen como "unknown" y salta un error
    @SerializedName("hair_color")
    var hairColor : String,
    @SerializedName("skin_color")
    var skinColor : String,
    @SerializedName("eye_color")
    var eyeColor : String,
    @SerializedName("birth_year")
    var birthYear : String,
    @SerializedName("gender")
    var gender : String
)