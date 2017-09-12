package design.shortnd.shushmekotlin

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Places

class MainActivity : AppCompatActivity(),
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    // Constants
    open val TAG = MainActivity::class.java.name

    // Member variables
    private lateinit var mAdapter: PlaceListAdapter
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up the recycler view
        mRecyclerView = findViewById(R.id.places_list_recycler_view)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = PlaceListAdapter(this)
        mRecyclerView.adapter = mAdapter

        // Build up the LocationServices API client
        // Uses the addApi method to request the LocationServices API
        // Also uses enableAutoManage to automatically when to connect/suspend the client
        val client: GoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, this)
                .build()
    }

    /***
     * Called when the Google API Client is successfully connected
     *
     * @param connectionHint Bundle of data provided to clients by Google Play services
     */
    override fun onConnected(connectionHint: Bundle?) {
        Log.i(TAG, "API Client Connection Successful")
    }

    /***
     * Called when the Google API Client is suspended
     *
     * @param cause cause The reason for the disconnection. Defined by constant CAUSE_*
     */
    override fun onConnectionSuspended(cause: Int) {
        Log.i(TAG, "API Client Connection Suspended!")
    }

    /***
     * Called when the Google API client failed to connect to Google Play Services
     *
     * @param result A ConnectionResult that can be used to resolving the error
     */
    override fun onConnectionFailed(result: ConnectionResult) {
        Log.i(TAG, "API Client Connection Failed")
    }

    /***
     * Button Click event handler to handle clicking the "Add new location" Button
     *
     * @param view
     */
    fun onAddPlaceButtonClicked(view: View) {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, getString(R.string.enable_location_first),
                    Toast.LENGTH_LONG).show()
            return
        }
        Toast.makeText(this, getString(R.string.location_permission_granted)
                , Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()

        // Initialize location permissions checkbox
        val locationPermissions = findViewById<CheckBox>(R.id.location_permission_checkbox)
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationPermissions.isChecked = false
        } else {
            locationPermissions.isChecked = true
            locationPermissions.isEnabled = false
        }
    }

    fun onLocationPermissionClicked(view: View) {
        ActivityCompat.requestPermissions(this@MainActivity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                PackageManager.PERMISSION_GRANTED)
    }
}
