package com.brk.besinkitabi.service

import com.brk.besinkitabi.model.Besin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BesinAPIServis {

    private val retrofit = Retrofit.Builder().baseUrl("https://raw.githubusercontent.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BesinAPI::class.java)

   suspend fun getData() : List<Besin> {
       return  retrofit.getBesin()
   }
}