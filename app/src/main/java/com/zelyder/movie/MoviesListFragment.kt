package com.zelyder.movie

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class MoviesListFragment : Fragment() {

    var navigationClickListener: NavigationClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavigationClickListener) {
            navigationClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item: View = view.findViewById(R.id.avengersItem)
        item.setOnClickListener {
            navigationClickListener?.navigateToDetails()
        }
    }

    override fun onDetach() {
        super.onDetach()
        navigationClickListener = null
    }


}