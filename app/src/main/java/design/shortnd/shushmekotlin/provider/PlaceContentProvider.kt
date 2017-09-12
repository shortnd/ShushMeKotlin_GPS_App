package design.shortnd.shushmekotlin.provider

import android.annotation.SuppressLint
import android.content.ContentProvider
import android.content.ContentUris.withAppendedId
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

@SuppressLint("Registered")
@Suppress("VARIABLE_WITH_REDUNDANT_INITIALIZER")
open class PlaceContentProvider : ContentProvider() {

    // Define final integer constants for the directory of places and a single item.
    // It's convention to use 100, 200, 300, etc for directories,
    // and related ints (101, 102, ..) for items in that directory.
    private val PLACES = 100
    private val PLACES_WITH_ID = 101

    // Declare a static variable for the Uri matcher that you construct
    private val sUriMatcher: UriMatcher = buildUriMatcher()

    // Define a static builderUriMatcher method that associates URI's with their int match
    private fun buildUriMatcher(): UriMatcher {
        // Initialize a UriMatcher
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        // Add URI matches
        uriMatcher.addURI(PlaceContract().AUTHORITY, PlaceContract().PATH_PLACES, PLACES)
        uriMatcher.addURI(PlaceContract().AUTHORITY, PlaceContract().PATH_PLACES + "/#",
                PLACES_WITH_ID)
        return uriMatcher
    }

    // Member variable for a PlaceDbHelper that's initialized in the onCreate() method
    private lateinit var mPlaceDbHelper: PlaceDbHelper

    override fun onCreate(): Boolean {
        val content = context
        mPlaceDbHelper = PlaceDbHelper(content)
        return true
    }

    /***
     * Handles requests to insert a single new row of data
     *
     * @param uri
     * @param values
     * @return
     */
    override fun insert(uri: Uri, values: ContentValues): Uri {
        val db = mPlaceDbHelper.writableDatabase

        // Write URI matching code to identify the match for the places directory
        val match: Int = sUriMatcher.match(uri)
        val returnUri: Uri
        when (match) {
            PLACES -> {
                // Insert new values into the database
                val id = db.insert(PlaceContract().PlaceEntry().TABLE_NAME, null, values)
                if (id > 0) {
                    returnUri = withAppendedId(PlaceContract().PlaceEntry().CONTENT_URI, id)
                } else {
                    throw android.database.SQLException("Failed to insert row into " + uri)
                }
            }
            else -> {
                // Default case throws an UnsupportedException
                throw UnsupportedOperationException("Unknown uri: " + uri)
            }
        }

        // Notify the resolver if the uri has been changed, and return the newly inserted URI
        context.contentResolver.notifyChange(uri, null)

        // Return constructed uri (this points to the newly inserted row of data)
        return returnUri
    }

    /***
     * Handles requests for data by URI
     *
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */

    override fun query(uri: Uri?, projection: Array<out String?>, selection: String?,
                       selectionArgs: Array<out String?>, sortOrder: String?): Cursor {

        // Get access to underlying database (read-only for query)
        val db = mPlaceDbHelper.readableDatabase

        // Write URI match code and set a variable to return a Cursor
        val match = sUriMatcher.match(uri)
        val retCursor: Cursor
        when (match) {
            PLACES -> {
                // Query for the places directory
                retCursor = db.query(PlaceContract().PlaceEntry().TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder)
            }
            else -> {
                throw UnsupportedOperationException("Unknown uri: " + uri)
            }
        }

        // Set a notification URI on the Cursor and return that Cursor
        retCursor.setNotificationUri(context.contentResolver, uri)

        // Return the desired Cursor
        return retCursor
    }

    /***
     * Deletes a single row of data
     *
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return number of rows affected
     */
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String?>): Int {
        // Get access to the database and write URI matching code to recognize a single item
        val db = mPlaceDbHelper.writableDatabase
        val match = sUriMatcher.match(uri)
        // Keep track of the number of deleted places
        var placesDeleted = 0 // Starts at 0
        when (match) {
        // Handle the single item case, recognized by the ID included in the URI path
            PLACES_WITH_ID -> {
                // Get the place ID from the URI path
                val id = uri.pathSegments[1]
                // Use selections/selectionArgs to filter for this ID
                placesDeleted = db.delete(PlaceContract().PlaceEntry().TABLE_NAME, "_id=?", arrayOf(id))
            }
            else -> {
                throw UnsupportedOperationException("Unknown uri: " + uri)
            }
        }
        // Notify the resolver of a change and return the number of items deleted
        if (placesDeleted != 0) {
            // A place (or more) was deleted, set notification
            context.contentResolver.notifyChange(uri, null)
        }
        // Return the number of places deleted
        return placesDeleted
    }

    /***
     * Updates a single row of data
     *
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return number of rows affected
     */
    override fun update(uri: Uri, values: ContentValues, selection: String, selectionArgs: Array<String?>): Int {
        // Get access to underlying database
        val db = mPlaceDbHelper.writableDatabase
        val match = sUriMatcher.match(uri)
        // Keep track of the number of updates places
        var placesUpdated = 0

        when (match) {
            PLACES_WITH_ID -> {
                // Get the place ID from the URI path
                val id = uri.pathSegments[1]
                // Use selections/selectionArgs to filter for this ID
                placesUpdated = db.update(PlaceContract().PlaceEntry().TABLE_NAME, values, "_id=?", arrayOf(id))
            }
            else -> {
                throw UnsupportedOperationException("Unknown uri: " + uri)
            }
        }

        // Notify the resolver of a change and return the number of items updated
        if (placesUpdated != 0) {
            // A place (or more) was updated, set notification
            context.contentResolver.notifyChange(uri, null)
        }
        // Return the number of places deleted
        return placesUpdated
    }

    override fun getType(uri: Uri): String {
        throw UnsupportedOperationException("Not yet implemented")
    }
}