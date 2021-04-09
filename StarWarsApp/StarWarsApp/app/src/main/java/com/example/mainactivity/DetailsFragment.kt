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
import com.example.mainactivity.databinding.FragmentDetailsBinding

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
    private var _binding : FragmentDetailsBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentDetailsBinding.inflate(inflater, container,false)
        val view = binding.root
        return view
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
        viewModel = ViewModelProvider(requireActivity()).get(VMMainActivity::class.java)

        viewModel.position.observe(requireActivity(), Observer {
            var listado : List<Personaje>? = viewModel.listadoPersonajes.value
            var position : Int? = viewModel.position.value
            binding.name.text = position?.let { it1 -> listado?.get(it1)?.name.toString() }
            binding.height.text = position?.let { it1 -> listado?.get(it1)?.height.toString() }
            binding.mass.text = position?.let { it1 -> listado?.get(it1)?.mass.toString() }
            binding.hairColor.text = position?.let { it1 -> listado?.get(it1)?.hairColor.toString() }
            binding.skinColor.text = position?.let { it1 -> listado?.get(it1)?.skinColor.toString() }
            binding.eyeColor.text = position?.let { it1 -> listado?.get(it1)?.eyeColor.toString() }
            binding.birthYear.text = position?.let { it1 -> listado?.get(it1)?.birthYear.toString() }
            binding.gender.text = position?.let { it1 -> listado?.get(it1)?.gender.toString() }
        })
    }
}