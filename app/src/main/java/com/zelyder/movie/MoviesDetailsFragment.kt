package com.zelyder.movie

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class MoviesDetailsFragment : Fragment() {

    var backBtnClickListener: OnBackBtnClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBackBtnClickListener) {
            backBtnClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnBack: View = view.findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            backBtnClickListener?.onClickBack()
        }
    }

    override fun onDetach() {
        super.onDetach()
        backBtnClickListener = null
    }


}