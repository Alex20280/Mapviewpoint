package com.example.mapviewpoint.utils

import android.Manifest
import android.content.Context
import pub.devrel.easypermissions.EasyPermissions

object PermissionsHelper {

    const val REQUEST_CODE_LOCATION_PERMISSION = 0

    fun hasLocationPermission(context: Context) =
        EasyPermissions.hasPermissions(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
}