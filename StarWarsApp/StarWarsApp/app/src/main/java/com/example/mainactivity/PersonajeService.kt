package com.example.mainactivity

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface PersonajeService {
    @GET("people/")
    fun getCharacterData(@Query("page") numPag : Int): Call<PersonajeResponse>
}