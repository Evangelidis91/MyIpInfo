package com.evangelidis.myipinfo.di

import com.evangelidis.myipinfo.viewModel.ViewModelIpStatus
import com.evangelidis.myipinfo.models.api.IpService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: IpService)

    fun inject(viewModel: ViewModelIpStatus)
}