package com.evangelidis.myipinfo.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.evangelidis.myipinfo.R
import com.evangelidis.myipinfo.databinding.ActivityMainBinding
import com.evangelidis.myipinfo.extensions.gone
import com.evangelidis.myipinfo.extensions.show
import com.evangelidis.myipinfo.utils.Constants.AFRICA
import com.evangelidis.myipinfo.utils.Constants.AFRICA_CODE
import com.evangelidis.myipinfo.utils.Constants.ANTARCTICA
import com.evangelidis.myipinfo.utils.Constants.ANTARCTICA_CODE
import com.evangelidis.myipinfo.utils.Constants.ASIA
import com.evangelidis.myipinfo.utils.Constants.ASIA_CODE
import com.evangelidis.myipinfo.utils.Constants.EUROPE
import com.evangelidis.myipinfo.utils.Constants.EUROPE_CODE
import com.evangelidis.myipinfo.utils.Constants.NORTH_AMERICA
import com.evangelidis.myipinfo.utils.Constants.NORTH_AMERICA_CODE
import com.evangelidis.myipinfo.utils.Constants.OCEANIA
import com.evangelidis.myipinfo.utils.Constants.OCEANIA_CODE
import com.evangelidis.myipinfo.utils.Constants.SOUTH_AMERICA
import com.evangelidis.myipinfo.utils.Constants.SOUTH_AMERICA_CODE
import com.evangelidis.myipinfo.utils.GetPublicIP
import com.evangelidis.myipinfo.utils.InternetStatus
import com.evangelidis.myipinfo.viewModel.ViewModelIpStatus
import com.evangelidis.tantintoast.TanTinToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModelIpStatus

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        viewModel = ViewModelProviders.of(this).get(ViewModelIpStatus::class.java)

        if (InternetStatus.getInstance(this).isOnline) {
            getIp()
        } else {
            TanTinToast.Warning(this).text(getString(R.string.error_text)).typeface(R.font.montserrat_medium).show()
            binding.ipInfoGroup.gone()
        }

        binding.icnRefresh.setOnClickListener {
            if (InternetStatus.getInstance(this).isOnline) {
                getIp()
            }
        }
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this@MainActivity, R.style.AlertDialogTheme)
        builder.apply {
            setIcon(R.drawable.icn_ip)
            setTitle(R.string.app_name)
            setMessage(R.string.close_popup_window_title)
            setCancelable(false)
            setPositiveButton(R.string.close_the_app_text) { _, _ -> finish() }
            setNegativeButton(R.string.cancel) { dialog, _ -> dialog.cancel() }
            create().show()
        }
    }

    private fun getIp() {
        binding.loader.show()
        val task = GetPublicIP()

        GlobalScope.launch(context = Dispatchers.Main) {
            task.execute()
            val publicIp = task.get().toString()
            viewModel.getIpInfo(publicIp)
            observeViewModel()
        }
    }

    private fun observeViewModel() {
        viewModel.ipInfo.observe(this, { data ->
            data?.let {
                with(binding) {
                    map.addMarker(it.latitude, it.longitude)
                    ip.text = it.ip
                    asn.text = it.asn
                    organization.text = it.org
                    country.text = it.country
                    city.text = it.city
                    latitude.text = it.latitude.toString()
                    longitude.text = it.longitude.toString()
                    timezone.text = it.timezone
                    setContinent(it.continentCode)
                }
            }
        })

        viewModel.loadError.observe(this, { isError ->
            isError?.let {
                binding.ipInfoGroup.visibility = if (it) View.GONE else View.VISIBLE
                binding.listError.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    TanTinToast.Warning(this).text("An error occurred while loading Data.").typeface(R.font.montserrat_medium).show()
                }
            }
        })

        viewModel.loading.observe(this, { isLoading ->
            isLoading?.let {
                with(binding) {
                    loader.visibility = if (it) View.VISIBLE else View.GONE
                    icnRefresh.isClickable = !it
                    ipInfoGroup.visibility = if (it) View.GONE else View.VISIBLE
                }
            }
        })
    }

    private fun setContinent(continentCode: String) {
        binding.continent.text = when (continentCode) {
            AFRICA_CODE -> AFRICA
            ANTARCTICA_CODE -> ANTARCTICA
            ASIA_CODE -> ASIA
            EUROPE_CODE -> EUROPE
            NORTH_AMERICA_CODE -> NORTH_AMERICA
            OCEANIA_CODE -> OCEANIA
            SOUTH_AMERICA_CODE -> SOUTH_AMERICA
            else -> EUROPE
        }
    }
}
