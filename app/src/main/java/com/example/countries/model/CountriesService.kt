package com.example.countries.model

import com.example.countries.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class CountriesService {

    @Inject
    lateinit var api: CountriesApi

    init {
        DaggerApiComponent.create().injectCountries(this)
    }

    // single is an observable that only emits 1 value and closes
    fun getCountries() : Single<List<Country>> {
        return api.getCountries()
    }
}