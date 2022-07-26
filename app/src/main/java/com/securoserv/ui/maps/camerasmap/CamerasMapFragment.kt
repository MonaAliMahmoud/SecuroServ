package com.securoserv.ui.maps.camerasmap

import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.securoserv.R
import com.securoserv.data.model.Cameras
import com.securoserv.databinding.FragmentMapBinding
import com.securoserv.ui.base.ViewState
import com.securoserv.ui.base.di.DIBaseFragment
import com.securoserv.ui.home.HomeFragment

class CamerasMapFragment :
    DIBaseFragment<FragmentMapBinding, CamerasMapRepository, CamerasMapViewModel>(),
    OnMapReadyCallback {

    private var param1: String? = null
    private var param2: String? = null

    private var mMap: GoogleMap? = null

    var cameras = ArrayList<Cameras>()

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String) =
            CamerasMapFragment().apply {
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

        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?

        supportMapFragment?.getMapAsync(this)
    }

    override fun getLayoutId(): Int = R.layout.fragment_map

    override fun initView() {

        cameras = viewModel.getZoneCameras("")

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?

        mapFragment?.getMapAsync(this)
    }

    override fun render(state: ViewState) {
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        var latLng = LatLng(0.0, 0.0)
        for (i in 0 until cameras.size) {
            val latLong = cameras[i].cameraName!!.split(",")

            latLng = LatLng(latLong[0].toDouble(), latLong[1].toDouble())
            mMap?.addMarker(
                MarkerOptions().position(latLng)
                    .title(cameras[i].cameraId)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            )
        }

        mMap?.animateCamera(CameraUpdateFactory.zoomTo(10f))
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))

        mMap?.setOnInfoWindowClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.content_frame, HomeFragment())!!
                .commit()
        }
    }

    override fun bindViewModel() {}
}