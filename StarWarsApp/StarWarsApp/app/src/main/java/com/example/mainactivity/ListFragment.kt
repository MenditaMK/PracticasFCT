package com.example.mainactivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mainactivity.databinding.FragmentListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment(), View.OnClickListener, PersonajeAdapter.OnItemClickListener {
    private var param1: String? = null
    private var param2: String? = null
    lateinit var personajeService : PersonajeService
    lateinit var listadoPersonajes : MutableList<Personaje>
    lateinit var adapter : PersonajeAdapter
    lateinit var viewModel : VMMainActivity
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    var numPag : Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(VMMainActivity::class.java)
        personajeService = getRetrofit().create(PersonajeService::class.java)

        binding.btnSiguiente.setOnClickListener(this)
        binding.btnAnterior.setOnClickListener(this)
        listadoPersonajes = mutableListOf<Personaje>()
        initRecyclerView()

        realizarLlamada(numPag)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    /* Interfaz
       Función: Esta función construirá un objeto retrofit base
       Entradas: --
       Salidas: El objeto retrofit
       Precondiciones: --
    */
    private fun getRetrofit() : Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(getLoggingInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://swapi.dev/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    /* Interfaz
       Función: Esta función nos crea un objeto interceptor para añadirselo a un retrofit
       Entradas: --
       Salidas: El objeto interceptor
       Precondiciones: --
     */
    private fun getLoggingInterceptor() : Interceptor{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    /* Interfaz
       Función: Esta función inicializa el recycler view
       Entradas: --
       Salidas: --
       Precondiciones: --
     */
    private fun initRecyclerView(){
        adapter = PersonajeAdapter(listadoPersonajes, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }

    /* Interfaz
       Función: Esta función realizará una llamada asíncrona a una api para obtener un listado o mostrar un mensaje de error
                en caso contrario
       Entradas: El número de la página a solicitar a la api
       Salidas: --
       Precondiciones: El número de la página debe de ser mayor que 0
     */
    private fun realizarLlamada(numPag : Int){
        CoroutineScope(Dispatchers.IO).launch {
            val call = personajeService.getCharacterData(numPag).execute()
            activity?.runOnUiThread{
                if(call.isSuccessful){
                    val result : PersonajeResponse? = call.body()
                    if(result?.next == null) binding.btnSiguiente.visibility = View.GONE
                    if(result?.previous == null) binding.btnAnterior.visibility = View.GONE
                    val listado = result?.results ?: emptyList()
                    listadoPersonajes.clear()
                    listadoPersonajes.addAll(listado)
                    adapter.notifyDataSetChanged()
                    viewModel.listadoPersonajes.value = listado
                }else{
                    Toast.makeText(context,"HA OCURRIDO UN ERROR, SENTIMOS LAS MOLESTIAS", Toast.LENGTH_LONG)
                }
            }

        }
    }

    override fun onItemClick(position: Int) {
        viewModel.position.value = position
    }

    override fun onClick(v: View?) {
        if (v != null) {
            if(v.id == R.id.btnSiguiente){
                if(binding.btnAnterior.visibility == View.GONE) binding.btnAnterior.visibility = View.VISIBLE
                numPag++
                realizarLlamada(numPag)
            } else if(v.id == R.id.btnAnterior){
                if(binding.btnSiguiente.visibility == View.GONE) binding.btnSiguiente.visibility = View.VISIBLE
                numPag--
                realizarLlamada(numPag)
            }
        }
    }
}