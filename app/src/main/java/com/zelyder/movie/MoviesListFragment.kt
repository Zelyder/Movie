package com.zelyder.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class MoviesListFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item: View = view.findViewById(R.id.avengersItem)
        item.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.main_container, MoviesDetailsFragment())
                addToBackStack("MoviesDetails")
                commit()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }
}