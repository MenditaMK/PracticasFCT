package com.example.mainactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PersonajeAdapter(val listado : List<Personaje>, val listener : OnItemClickListener) : RecyclerView.Adapter<PersonajeAdapter.PersonajeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonajeHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PersonajeHolder(layoutInflater.inflate(R.layout.item_personaje, parent, false))
    }

    override fun getItemCount(): Int = listado.size

    override fun onBindViewHolder(holder: PersonajeHolder, position: Int) {
        holder.render(listado[position])
    }

    inner class PersonajeHolder(val view: View):RecyclerView.ViewHolder(view), View.OnClickListener{
        fun render(personaje:Personaje){
            var txtNombre = view.findViewById<TextView>(R.id.nombrePersonaje)
            txtNombre.text = personaje.name
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position : Int = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position : Int)
    }
}