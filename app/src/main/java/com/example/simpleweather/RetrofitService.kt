package com.example.simpleweather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("data/2.5/forecast?&appid=3bbea22f826e4eef49dc445bd1114a75")
    fun getWeather(
        @Query("lat") lat : String,
        @Query("lon") lon : String
    ) : Call<Weather>
}