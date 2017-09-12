package design.shortnd.shushmekotlin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

open class PlaceListAdapter(mainActivity: MainActivity) : RecyclerView.Adapter<PlaceListAdapter.PlaceViewHolder>() {
    private lateinit var mContext: Context

    /**
     * Constructor using the context and the db cursor
     *
     * @param context the calling context/activity
     */
    fun PlaceListAdapter(context: Context) { this.mContext = context }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item
     *
     * @param parent The ViewGroup into which the new View will be added
     * @param viewType the view type of the new View
     * @return A new PlaceViewHolder that holds a View with the item_place_card layout
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        // Get the RecyclerView item layout
        var inflator = LayoutInflater.from(mContext)
        var view = inflator.inflate(R.layout.item_place_card, parent, false)
        return PlaceViewHolder(view)
    }

    /**
     * Binds the data from a particular postion in the cursor to the corresponding view holder
     *
     * @param holder The PlaceHolder instance corresponding to the required postion
     * @param postion The current position that needs to be loaded with data
     */
    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {

    }

    /**
     * Returns the number of items in the cursor
     *
     * @return Number of items in the cursor, or 0 if null
     */
    override fun getItemCount(): Int { return 0 }

    /**
     * PlaceViewHolder class for the recycler view item
     */

    inner class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var nameTextView: TextView = itemView.findViewById(R.id.name_text_view)
        private var addressTextView: TextView = itemView.findViewById(R.id.address_text_view)

    }

//    internal inner class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        var nameTextView: TextView
//        var addressTextView: TextView
//
//        init {
//            nameTextView = itemView.findViewById(R.id.name_text_view) as TextView
//            addressTextView = itemView.findViewById(R.id.address_text_view) as TextView
//        }
//
//    }

}
