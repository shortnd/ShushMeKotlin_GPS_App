package design.shortnd.shushmekotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class MainActivity : AppCompatActivity() {

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

    }
}
