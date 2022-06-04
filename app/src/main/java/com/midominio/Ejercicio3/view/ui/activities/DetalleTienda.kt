package com.midominio.Ejercicio3.view.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.midominio.Ejercicio3.model.ProductApi
import com.midominio.Ejercicio3.model.DetalleTienda
import com.midominio.Ejercicio3.databinding.ActivityProductoDetallesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetalleTienda : AppCompatActivity() {

    private lateinit var binding: ActivityProductoDetallesBinding

    private val BASE_URL= "https://www.serverbpw.com/"
    private val LOG_TAG = "LOGS"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductoDetallesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        val id = bundle?.getString("id", "0")
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val  productApi: ProductApi = retrofit.create(ProductApi::class.java)

        val call: Call<DetalleTienda> = productApi.getProductDetail(id)

        call.enqueue(object : Callback<DetalleTienda>{
            override fun onResponse(call: Call<DetalleTienda>, response: Response<DetalleTienda>
            ) {

                with(binding){
                    pbConexion.visibility = View.INVISIBLE
                    Toast.makeText(this@DetalleTienda, "Conexión Establecida", Toast.LENGTH_SHORT).show()

                    tvTitulo.text = response.body()?.name

                    Glide.with(this@DetalleTienda)
                        .load(response.body()?.imag_url)
                        .into(ivImage)

                    tvLongDesc.text = response.body()?.desc
                }

            }

            override fun onFailure(call: Call<DetalleTienda>, t: Throwable) {
                Log.d(LOG_TAG,"ERROR")
                Toast.makeText(this@DetalleTienda, "No hay conexión Producto", Toast.LENGTH_SHORT).show()
                binding.pbConexion.visibility = View.INVISIBLE
            }

        })

    }
}