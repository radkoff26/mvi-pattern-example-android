package com.github.radkoff26.mvi

import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.radkoff26.mvi.extensions.areAllPermissionsGranted
import com.github.radkoff26.mvi.extensions.getAllNonGrantedPermissions
import com.github.radkoff26.mvi.extensions.requestAllPermissions
import com.github.radkoff26.mvi.extensions.toastMessage
import com.github.radkoff26.mvi.service.LocationWatchService
import com.github.radkoff26.mvi.ui.ChangeNameFragment
import com.github.radkoff26.mvi.ui.InfoFragment

class MainActivity : AppCompatActivity() {
    private var wasStopped = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissionsAndProceedIfPossible()
        startService(Intent(this, LocationWatchService::class.java))
    }

    override fun onStart() {
        super.onStart()
        if (wasStopped && !areAllPermissionsGranted(PERMISSIONS)) {
            val nonGranted = getAllNonGrantedPermissions(PERMISSIONS)
            requestAllPermissions(
                nonGranted,
                onFailToGrant = {
                    toastMessage("Permissions were not granted!")
                    finish()
                }
            )
        }
    }

    override fun onStop() {
        super.onStop()
        wasStopped = true
    }

    private fun requestPermissionsAndProceedIfPossible() {
        if (areAllPermissionsGranted(PERMISSIONS)) {
            startActivityFragments()
        } else {
            val nonGranted = getAllNonGrantedPermissions(PERMISSIONS)
            requestAllPermissions(
                nonGranted,
                onSuccess = {
                    startActivityFragments()
                },
                onFailToGrant = {
                    toastMessage("Permissions were not granted!")
                    finish()
                }
            )
        }
    }

    private fun startActivityFragments() {
        openFirstFragment()
        openSecondFragment()
    }

    private fun openFirstFragment() {
        openFragment(
            ChangeNameFragment(),
            R.id.firstContainer
        )
    }

    private fun openSecondFragment() {
        openFragment(
            InfoFragment(),
            R.id.secondContainer
        )
    }

    private fun openFragment(fragment: Fragment, @IdRes layoutId: Int) {
        supportFragmentManager.beginTransaction().apply {
            add(layoutId, fragment)
            commit()
        }
    }

    companion object {
        private val PERMISSIONS = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    }
}