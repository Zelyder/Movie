package com.zelyder.movie.movieslist

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zelyder.movie.BaseFragment
import com.zelyder.movie.domain.MoviesDataSourceImpl
import com.zelyder.movie.NavigationClickListener
import com.zelyder.movie.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesListFragment : BaseFragment() {

    var navigationClickListener: NavigationClickListener? = null
    var recyclerView: RecyclerView? = null
    var columnsCount = PORTRAIT_LIST_COLUMNS_COUNT
    val coroutineScope = CoroutineScope(Dispatchers.Main)

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

        columnsCount = when (resources.configuration.orientation){
            Configuration.ORIENTATION_LANDSCAPE -> LANDSCAPE_LIST_COLUMNS_COUNT
            Configuration.ORIENTATION_PORTRAIT -> PORTRAIT_LIST_COLUMNS_COUNT
            else -> PORTRAIT_LIST_COLUMNS_COUNT
        }

        recyclerView = view.findViewById<RecyclerView>(R.id.rvMoviesList).apply {
            layoutManager = GridLayoutManager(view.context, columnsCount)
            adapter = MoviesListAdapter(navigationClickListener)
        }

    }

    private suspend fun setupList() = withContext(Dispatchers.Main){
        (recyclerView?.adapter as? MoviesListAdapter)?.apply {
            bindMovies(dataProvider?.dataSource()?.getMoviesAsync() ?: listOf())
        }
    }

    override fun onStart() {
        super.onStart()
        coroutineScope.launch {
            setupList()
        }
    }

    override fun onDetach() {
        super.onDetach()
        navigationClickListener = null
    }
}

private const val PORTRAIT_LIST_COLUMNS_COUNT = 2
private const val LANDSCAPE_LIST_COLUMNS_COUNT = 3
private const val ADAPTER_DECORATION_SPACE = 8f