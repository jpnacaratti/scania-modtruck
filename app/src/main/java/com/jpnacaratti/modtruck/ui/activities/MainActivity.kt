package com.jpnacaratti.modtruck.ui.activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jpnacaratti.modtruck.bluetooth.BluetoothReceiver
import com.jpnacaratti.modtruck.bluetooth.BluetoothService
import com.jpnacaratti.modtruck.ui.navigation.BottomBar
import com.jpnacaratti.modtruck.ui.navigation.BottomNavGraph
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.jpnacaratti.modtruck.ui.viewmodels.HomeScreenViewModel
import com.jpnacaratti.modtruck.ui.viewmodels.TruckViewModel
import com.jpnacaratti.modtruck.ui.viewmodels.UserViewModel
import com.jpnacaratti.modtruck.utils.GoogleFontProvider
import com.jpnacaratti.modtruck.utils.UserPreferences

class MainActivity : ComponentActivity() {

    private lateinit var bluetoothReceiver: BluetoothReceiver
    private lateinit var bluetoothService: BluetoothService
    private val truckViewModel by viewModels<TruckViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerBluetoothReceiver()

        GoogleFontProvider.initialize()

        enableEdgeToEdge()

        window.attributes.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES

        val screenViewModel by viewModels<HomeScreenViewModel>()
        val userViewModel by viewModels<UserViewModel>()

        val preferences = UserPreferences(this)
        truckViewModel.setPreferences(preferences)
        truckViewModel.updateHasConnectedBefore(preferences.getHasConnectedBefore())
        truckViewModel.loadSmartBoxFromPreferences()

        setContent {
            ModTruck(
                truckViewModel = truckViewModel,
                userViewModel = userViewModel,
                homeScreenViewModel = screenViewModel
            )
        }

        if (!hasBluetoothPermissions(this)) {
            requestBluetoothPermissions(this)
        } else {
            bluetoothService = BluetoothService(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bluetoothService.stopService()
    }

    private fun registerBluetoothReceiver() {
        bluetoothReceiver = BluetoothReceiver(truckViewModel)

        val intentFilter = IntentFilter().apply {
            addAction(BluetoothReceiver.TRUCK_CONNECTED)
            addAction(BluetoothReceiver.TRUCK_INFO_RECEIVED)
            addAction(BluetoothReceiver.SMARTBOX_INFO_RECEIVED)
        }

        registerReceiver(bluetoothReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED)
    }

    private fun hasBluetoothPermissions(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.BLUETOOTH
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_ADMIN
                ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_SCAN
                ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestBluetoothPermissions(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            REQUEST_BLUETOOTH_PERMISSIONS_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_BLUETOOTH_PERMISSIONS_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                bluetoothService = BluetoothService(this)
            } else {
                Toast.makeText(this, "Permissões de Bluetooth são necessárias", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    companion object {
        private const val REQUEST_BLUETOOTH_PERMISSIONS_CODE = 101
    }
}

@Composable
fun ModTruck(
    truckViewModel: TruckViewModel,
    userViewModel: UserViewModel,
    homeScreenViewModel: HomeScreenViewModel
) {
    val navController = rememberNavController()

    ModTruckTheme {
        Scaffold(
            bottomBar = {
                BottomBar(navController = navController)
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier.padding(
                    PaddingValues(
                        start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                        top = 0.dp,
                        end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                        bottom = 0.dp
                    )
                )
            ) {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent
                )

                BottomNavGraph(
                    navController = navController,
                    truckViewModel = truckViewModel,
                    userViewModel = userViewModel,
                    homeScreenViewModel = homeScreenViewModel
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ModTruck(
        truckViewModel = TruckViewModel(),
        userViewModel = UserViewModel(),
        homeScreenViewModel = HomeScreenViewModel()
    )
}