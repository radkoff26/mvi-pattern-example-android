package com.github.radkoff26.mvi.extensions

import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.areAllPermissionsGranted(permissions: List<String>): Boolean =
    permissions.all {
        checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED
    }

fun FragmentActivity.requestAllPermissions(
    permissions: List<String>,
    onSuccess: () -> Unit = {},
    onFailToGrant: () -> Unit = {}
) {
    registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        if (!areAllPermissionsGranted(permissions)) {
            onFailToGrant.invoke()
        } else {
            onSuccess.invoke()
        }
    }.launch(permissions.toTypedArray())
}

fun FragmentActivity.getAllNonGrantedPermissions(permissions: List<String>): List<String> =
    permissions.filter {
        checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED
    }
