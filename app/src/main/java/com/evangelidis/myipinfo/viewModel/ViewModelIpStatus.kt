package com.evangelidis.myipinfo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.evangelidis.myipinfo.di.DaggerApiComponent
import com.evangelidis.myipinfo.models.IpDataResponse
import com.evangelidis.myipinfo.models.api.IpService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ViewModelIpStatus : ViewModel() {

    @Inject
    lateinit var ipService: IpService

    private val disposable = CompositeDisposable()

    val ipInfo = MutableLiveData<IpDataResponse>()
    val loadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getIpInfo(ip: String) {
        fetchIpDetails(ip)
    }

    private fun fetchIpDetails(ip: String) {
        loading.value = true
        disposable.add(
            ipService.getIpInfo(ip)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<IpDataResponse>() {
                    override fun onSuccess(response: IpDataResponse) {
                        ipInfo.value = response
                        loadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        loadError.value = true
                        loading.value = false
                    }
                })
        )
    }
}
