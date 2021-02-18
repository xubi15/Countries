package com.example.countries.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countries.R
import com.example.countries.view.adapters.CountryListAdapter
import com.example.countries.viewmodel.CountryListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModelCountry: CountryListViewModel
    private val countriesAdapter = CountryListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModelCountry = ViewModelProviders.of(this).get(CountryListViewModel::class.java)
        viewModelCountry.refresh()

        countriesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModelCountry.refresh()
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModelCountry.countries.observe(this, Observer { countries ->
            countries?.let {
                countriesList.visibility = View.VISIBLE
                countriesAdapter.updateCountries(it)
            }
        })

        viewModelCountry.countryLoadError.observe(this, Observer { isError ->
            isError?.let {
                list_error.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModelCountry.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    list_error.visibility = View.GONE
                    countriesList.visibility = View.GONE
                }
            }
        })
    }

}