package design.shortnd.shushmekotlin.provider

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


open class PlaceDbHelper(context: Context) : SQLiteOpenHelper(context,
        PlaceContract().DATABASE_NAME, null, PlaceContract().VERSION) {

    companion object {
        val ID: String = "_id"
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {

        // Create a table to hold the places data
        val SQL_CREATE_PLACES_TABLE = "create table" + PlaceContract().PlaceEntry().TABLE_NAME + " (" +
                "$ID integer primary key autoincrement," +
                PlaceContract().PlaceEntry().COLUMN_PLACE_ID + " text not null, " +
                "unique (" + PlaceContract().PlaceEntry().COLUMN_PLACE_ID + ") on conflict replace" +
                "); "
        sqLiteDatabase.execSQL(SQL_CREATE_PLACES_TABLE)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        // For now simply drop the table and create a new one.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PlaceContract().PlaceEntry().TABLE_NAME)
        onCreate(sqLiteDatabase)
    }
}