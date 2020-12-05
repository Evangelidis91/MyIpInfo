package com.evangelidis.myipinfo.models.api

import com.evangelidis.myipinfo.di.DaggerApiComponent
import com.evangelidis.myipinfo.models.IpDataResponse
import io.reactivex.Single
import javax.inject.Inject

class IpService {

    @Inject
    lateinit var api: IpApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getIpInfo(ip: String): Single<IpDataResponse> {
        return api.getIpInfo(ip)
    }
}
