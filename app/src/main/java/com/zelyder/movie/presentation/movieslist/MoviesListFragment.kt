package com.zelyder.movie.presentation.movieslist

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zelyder.movie.*
import com.zelyder.movie.domain.models.ListMovie
import com.zelyder.movie.presentation.core.NavigationClickListener
import com.zelyder.movie.presentation.core.BaseFragment

class MoviesListFragment : BaseFragment(), MovieListItemClickListener {

    private var navigationClickListener: NavigationClickListener? = null
    var itemClickListener: MovieListItemClickListener? = null
    var recyclerView: RecyclerView? = null
    var columnsCount = PORTRAIT_LIST_COLUMNS_COUNT
    private val viewModel: MoviesListViewModel by lazy { viewModelFactoryProvider()
        .viewModelFactory().create(MoviesListViewModel::class.java) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavigationClickListener) {
            navigationClickListener = context
        }
        itemClickListener = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        columnsCount = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> LANDSCAPE_LIST_COLUMNS_COUNT
            Configuration.ORIENTATION_PORTRAIT -> PORTRAIT_LIST_COLUMNS_COUNT
            else -> PORTRAIT_LIST_COLUMNS_COUNT
        }

        recyclerView = view.findViewById<RecyclerView>(R.id.rvMoviesList).apply {
            layoutManager = GridLayoutManager(view.context, columnsCount)
            adapter = MoviesListAdapter(navigationClickListener, itemClickListener)
        }

        viewModel.moviesList.observe(this.viewLifecycleOwner, {
            (recyclerView?.adapter as? MoviesListAdapter)?.apply {
                bindMovies(it)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.updateList()
    }

    override fun onDestroyView() {
        recyclerView = null
        super.onDestroyView()
    }

    override fun onDetach() {
        navigationClickListener = null
        itemClickListener = null
        super.onDetach()
    }

    override fun onClickLike(movieId: Int, isFavorite: Boolean) {
        viewModel.updateMovie(movieId, isFavorite)
    }
}

private const val PORTRAIT_LIST_COLUMNS_COUNT = 2
private const val LANDSCAPE_LIST_COLUMNS_COUNT = 3