package com.midominio.Ejercicio3.view.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.midominio.Ejercicio3.databinding.ActivityMainBinding
import com.midominio.Ejercicio3.model.ProductApi
import com.midominio.Ejercicio3.model.Tienda
import com.midominio.Ejercicio3.view.adapter.Adaptador
//import com.midominio.ejercicio_3.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), Adaptador.OnItemListener {

    private val BASE_URL= "https://www.serverbpw.com/"
    private val LOG_TAG = "LOGS"

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val  productApi: ProductApi = retrofit.create(ProductApi::class.java)

        val call: Call<List<Tienda>> = productApi.getProduct("cm/2022-2/products.php")

        call.enqueue(object: Callback<List<Tienda>>{
            override fun onResponse(call: Call<List<Tienda>>, response: Response<List<Tienda>>
            ) {
                Log.d(LOG_TAG,"Respuesta del Servidor: ${response.toString()}")
                Log.d(LOG_TAG, "Datos: ${response.body().toString()}")

                binding.pbConexion.visibility = View.INVISIBLE

                val adaptador=Adaptador(this@MainActivity, response.body()!!, this@MainActivity )

                val recycleView = binding.rvMenu

                recycleView.layoutManager = LinearLayoutManager(this@MainActivity)

                recycleView.adapter = adaptador

            }

            override fun onFailure(call: Call<List<Tienda>>, t: Throwable) {
                Log.d(LOG_TAG,"ERROR")
                Toast.makeText(this@MainActivity, "No hay conexión", Toast.LENGTH_SHORT).show()
                binding.pbConexion.visibility = View.INVISIBLE

            }

        })

    }

    override fun onItemClick(product: Tienda) {
        val param = Bundle()

        param.putString("id",product.id)

        val intent =Intent(this, DetalleTienda::class.java)

        intent.putExtras(param)

        startActivity(intent)
    }
}