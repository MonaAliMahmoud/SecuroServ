package com.securoserv.ui.maps.zonesmap


import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.securoserv.R
import com.securoserv.data.model.Zones
import com.securoserv.databinding.FragmentMapBinding
import com.securoserv.ui.base.ViewState
import com.securoserv.ui.base.di.DIBaseFragment
import com.securoserv.ui.maps.camerasmap.CamerasMapFragment

class ZonesMapFragment :
    DIBaseFragment<FragmentMapBinding, ZonesMapRepository, ZonesMapViewModel>(),
    OnMapReadyCallback {

    private var param1: String? = null
    private var param2: String? = null

    private var mMap: GoogleMap? = null

    var zones = ArrayList<Zones>()

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String) =
            ZonesMapFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_map

    override fun initView() {
        zones = viewModel.getAllZones("")

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?

        mapFragment?.getMapAsync(this)
    }

    override fun render(state: ViewState) {
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        var latLng = LatLng(0.0, 0.0)
        for (i in 0 until zones.size) {
            val latLong = zones[i].zoneName!!.split(",")

            latLng = LatLng(latLong[0].toDouble(), latLong[1].toDouble())
            mMap?.addMarker(
                MarkerOptions().position(latLng)
                    .title(zones[i].zoneId)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            )
        }

        mMap?.animateCamera(CameraUpdateFactory.zoomTo(10f))
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))

        mMap?.setOnInfoWindowClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.add(R.id.content_frame, CamerasMapFragment())!!
                .commit()
        }
    }

    override fun bindViewModel() {}
}