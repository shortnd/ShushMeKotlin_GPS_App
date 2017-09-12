package design.shortnd.shushmekotlin.provider

import android.net.Uri
import android.provider.BaseColumns



open class PlaceContract {

    // The name of the database to be used
    val DATABASE_NAME = "shushmekotlin.db"
    // The version of the database that must be updated whenever you update
    // the schema
    val VERSION = 1

    // The authority, which is how your code knows which Content Provider to access
    val AUTHORITY = "design.shortnd.android.shushmekotlin"

    // The base content URI = "content://" + <authority>
    val BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY)

    // Define the possible paths for accessing data in this contract
    // This is the path for the "places" directory
    val PATH_PLACES = "places"

    open inner class PlaceEntry: BaseColumns {

        // TaskEntry content URI = base content URI + path
        val CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PLACES).build()

        open val TABLE_NAME = "places"
        val COLUMN_PLACE_ID = "placeID"
    }
}
