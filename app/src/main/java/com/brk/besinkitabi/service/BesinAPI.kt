package com.brk.besinkitabi.service

import com.brk.besinkitabi.model.Besin
import retrofit2.http.GET

interface BesinAPI {
   // https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json
    //https://raw.githubusercontent.com/atilsamancioglu/

    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    suspend fun getBesin() : List<Besin>
}