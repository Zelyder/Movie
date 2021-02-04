package com.zelyder.movie.presentation.moviedetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zelyder.movie.R
import com.zelyder.movie.domain.models.Actor

class ActorsListAdapter : RecyclerView.Adapter<ActorsViewHolder>(){

    var actors = listOf<Actor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsViewHolder {
        return ActorsViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_actor_list, parent, false))
    }

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) {
        holder.bind(actors[position])
    }

    override fun getItemCount(): Int = actors.size

    fun bindActors(newActors: List<Actor>) {
        actors = newActors
        notifyDataSetChanged()
    }
}

class ActorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val ivActor : ImageView = itemView.findViewById(R.id.ivActor)
    private val tvActor : TextView = itemView.findViewById(R.id.tvActor)

    fun bind(actor: Actor){
        Picasso.get().load(actor.picture)
            .placeholder(R.drawable.ic_person_circle)
            .into(ivActor)
        tvActor.text = actor.name
    }
}