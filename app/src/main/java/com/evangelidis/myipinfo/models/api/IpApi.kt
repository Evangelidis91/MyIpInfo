package com.evangelidis.myipinfo.models.api

import com.evangelidis.myipinfo.models.IpDataResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface IpApi {

    @GET("{IP}/json")
    fun getIpInfo(
        @Path("IP") ip: String
    ): Single<IpDataResponse>
}