package com.example.mainactivity

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    var fragmentListSmallScreen : FragmentContainerView? = null
    lateinit var viewModel : VMMainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(VMMainActivity::class.java)
        fragmentListSmallScreen = findViewById(R.id.fragmentListSmallScreen);

        if(fragmentListSmallScreen != null){ //Significará que estamos ante una pantalla pequeña
            supportFragmentManager.beginTransaction().add( //Añadimos el fragment inicial
                R.id.fragmentListSmallScreen,
                ListFragment(), null
            ).commit()

            viewModel.position.observe(this, Observer {
                supportFragmentManager.beginTransaction().replace(  //Si el usuario selecciona un elemento, cambiaremos de fragment
                        R.id.fragmentListSmallScreen,
                        DetailsFragment(), null
                ).addToBackStack(null).commit()
            })
        }
    }
}