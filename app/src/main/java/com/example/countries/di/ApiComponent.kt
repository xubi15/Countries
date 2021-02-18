package com.example.countries.di

import com.example.countries.model.CountriesService
import com.example.countries.viewmodel.CountryListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun injectCountries(service: CountriesService)

    fun injectCountryViewModel(countryViewModel: CountryListViewModel)
}