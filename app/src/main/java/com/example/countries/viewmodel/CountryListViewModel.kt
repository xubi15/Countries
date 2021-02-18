package com.example.countries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countries.di.DaggerApiComponent
import com.example.countries.model.CountriesService
import com.example.countries.model.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class CountryListViewModel : ViewModel() {

    @Inject
    lateinit var countriesService: CountriesService

    init {
        DaggerApiComponent.create().injectCountryViewModel(this)
    }

    // sense this viewModel which is using RxJava to get the info from service.
    // when this viewModel is closed we need close/clear this connection
    private val disposable = CompositeDisposable()

    val countries = MutableLiveData<List<Country>>()
    val countryLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {

        /*val mockData:List<Country> = listOf(Country("CountryA"),
            Country("CountryB"),
            Country("CountryC"),
            Country("CountryD"),
            Country("CountryE")
        )*/

        loading.value = true
        disposable.add(
            countriesService.getCountries()
                .subscribeOn(Schedulers.newThread()) // do the processing on a separate thread
                .observeOn(AndroidSchedulers.mainThread()) // get the result on main thread
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(value: List<Country>?) {
                        countries.value = value
                        countryLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable?) {
                        countryLoadError.value = true
                        loading.value = false
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear() //disconnecting service
    }
}