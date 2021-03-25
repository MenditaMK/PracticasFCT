package com.example.mainactivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mainactivity.Personaje
import com.example.mainactivity.R
import com.example.mainactivity.VMMainActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModel : VMMainActivity
    private lateinit var name : TextView
    private lateinit var height : TextView
    private lateinit var mass : TextView
    private lateinit var hairColor : TextView
    private lateinit var skinColor : TextView
    private lateinit var eyeColor : TextView
    private lateinit var birthYear : TextView
    private lateinit var gender : TextView

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
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name = view.findViewById(R.id.name)
        height = view.findViewById(R.id.height)
        mass = view.findViewById(R.id.mass)
        hairColor = view.findViewById(R.id.hair_color)
        skinColor = view.findViewById(R.id.skin_color)
        eyeColor = view.findViewById(R.id.eye_color)
        birthYear = view.findViewById(R.id.birth_year)
        gender = view.findViewById(R.id.gender)
        viewModel = ViewModelProvider(requireActivity()).get(VMMainActivity::class.java)

        viewModel.position.observe(requireActivity(), Observer {
            var listado : List<Personaje>? = viewModel.listadoPersonajes.value
            var position : Int? = viewModel.position.value
            name.text = position?.let { it1 -> listado?.get(it1)?.name.toString() }
            height.text = position?.let { it1 -> listado?.get(it1)?.height.toString() }
            mass.text = position?.let { it1 -> listado?.get(it1)?.mass.toString() }
            hairColor.text = position?.let { it1 -> listado?.get(it1)?.hairColor.toString() }
            skinColor.text = position?.let { it1 -> listado?.get(it1)?.skinColor.toString() }
            eyeColor.text = position?.let { it1 -> listado?.get(it1)?.eyeColor.toString() }
            birthYear.text = position?.let { it1 -> listado?.get(it1)?.birthYear.toString() }
            gender.text = position?.let { it1 -> listado?.get(it1)?.gender.toString() }
        })
    }
}