package com.evangelidis.myipinfo.utils

import android.os.AsyncTask
import com.evangelidis.myipinfo.utils.Constants.IP_BASE_URL
import java.io.IOException
import java.net.URL
import java.util.*

class GetPublicIP : AsyncTask<String?, String?, String>() {
    override fun doInBackground(vararg p0: String?): String {
        var publicIP = ""
        try {
            val s = Scanner(URL(IP_BASE_URL).openStream(), "UTF-8").useDelimiter("\\A")
            publicIP = s.next()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return publicIP
    }
}
