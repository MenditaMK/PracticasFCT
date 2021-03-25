package com.example.mainactivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class VMMainActivity(application: Application) : AndroidViewModel(application) {
    val position : MutableLiveData<Int> by lazy{
        MutableLiveData<Int>()
    }
    val listadoPersonajes : MutableLiveData<List<Personaje>> by lazy{
        MutableLiveData<List<Personaje>>()
    }
}

