package com.example.simpleweather

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpleweather.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private var cancellationTokenSource : CancellationTokenSource? = null

    private var lon : String = ""
    private var lat : String = ""

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        requestPermission()

        val url4 = "api.openweathermap.org/data/2.5/forecast?lat=37.4856933&lon=127.1191588&appid=3bbea22f826e4eef49dc445bd1114a75"
        val url5 = "https://api.openweathermap.org/data/2.5/forecast?lat=37.4856933&lon=127.1191588&appid=3bbea22f826e4eef49dc445bd1114a75"
        val url6 = "https://api.openweathermap.org/data/2.5/onecall?lat=37.4856933&lon=127.1191588&exclude=daily&appid=3bbea22f826e4eef49dc445bd1114a75"

    }

    private fun getWeatherApi() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitService = retrofit.create(RetrofitService::class.java)


        retrofitService.getWeather(lat, lon).enqueue(object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                if(response.isSuccessful) {
                    val weather = response.body()
                    val mainList = weather!!.list

                    Log.d("testt weather","${weather}")
                    Log.d("testt weatherList", "${weather?.city?.country}")
                    Log.d("testt mainList","${mainList}")
                    Log.d("testt size","${mainList?.size}")

                    val sunrise = weather.city.sunrise
                    val sunset = weather.city.sunset
                    val sunriseTime = Date(sunrise * 1000L)
                    val sunsetTime = Date(sunset * 1000L)
                    Log.d("testt sunriseTime", "${sunriseTime}")
                    Log.d("testt sunsetTime", "${sunsetTime}")

                    binding.recyclerView.adapter = WeatherAdapter(mainList, LayoutInflater.from(this@MainActivity))
                    binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)

                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                Log.d("testt","${t.message}")
            }

        })
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            REQUEST_ACCESS_LOCATION_PERMISSIONS
        )
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 1000) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.d("testt", "승낙")

                cancellationTokenSource = CancellationTokenSource()

                fusedLocationProviderClient.getCurrentLocation(
                    LocationRequest.PRIORITY_HIGH_ACCURACY,
                    cancellationTokenSource!!.token
                ).addOnSuccessListener { location ->
                    //binding.textView.text = "${location.latitude} / ${location.longitude}"
                    lat = location.latitude.toString()
                    lon = location.longitude.toString()
                    Log.d("testt", "$lat / $lon")

                    getWeatherApi()
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        cancellationTokenSource?.cancel()
    }

    companion object {
        private const val REQUEST_ACCESS_LOCATION_PERMISSIONS = 1000
    }
}






































