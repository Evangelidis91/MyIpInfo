package com.evangelidis.myipinfo.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.appcompat.app.AppCompatActivity
import com.evangelidis.myipinfo.utils.Constants.GOOGLE_MAP_ZOOM_LEVEL
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class MapView : FrameLayout {
    private var mapSubject: Subject<GoogleMap>? = null
    private var map: GoogleMap? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val mapFragment = SupportMapFragment.newInstance()
        if (!isInEditMode) {
            val fragmentTransaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.apply {
                add(id, mapFragment)
                commit()
            }
            mapSubject = BehaviorSubject.create()
            mapSubject?.subscribe {
                map = it
            }
            Observable.create(
                ObservableOnSubscribe { e: ObservableEmitter<GoogleMap> ->
                    mapFragment.getMapAsync { value: GoogleMap ->
                        e.onNext(value)
                    }
                } as ObservableOnSubscribe<GoogleMap>).subscribe(mapSubject as BehaviorSubject<GoogleMap>)
        }
    }

    fun addMarker(lat: Double, lng: Double) {
        val position = LatLng(lat, lng)
        map?.let {
            it.apply {
                addMarker(MarkerOptions().position(position))
                moveCamera(CameraUpdateFactory.newLatLng(position))
                animateCamera(CameraUpdateFactory.newLatLngZoom(position, GOOGLE_MAP_ZOOM_LEVEL))
            }
        }
    }
}
