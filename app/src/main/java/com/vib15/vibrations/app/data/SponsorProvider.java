package com.vib15.vibrations.app.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by Agarwal on 06-03-2015.
 */
public class SponsorProvider extends ContentProvider {
    static final int SPONSOR = 100;
    static final int SPONSOR_WITH_ID=101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private EventsDbHelper mOpenHelper;
    private static final SQLiteQueryBuilder sponsorQueryBuilder;

    static{
        sponsorQueryBuilder = new SQLiteQueryBuilder();
        sponsorQueryBuilder.setTables(SponsorContract.SponsorEntry.TABLE_NAME);
    }
    private static final String sSponsorIdSelection = SponsorContract.SponsorEntry._ID + " = ? ";
    @Override
    public boolean onCreate() {
        mOpenHelper = new EventsDbHelper(getContext());
        return true;
    }
    public Cursor getSponsorById(Uri uri, String[] projection, String sortOrder){
        long startDate = SponsorContract.SponsorEntry.getIdFromUri(uri);
        String[] selectionArgs = new String[]{Long.toString(startDate)};
        String selection=sSponsorIdSelection;
        return sponsorQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case SPONSOR: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SponsorContract.SponsorEntry.TABLE_NAME,
                        new String[]{SponsorContract.SponsorEntry._ID,SponsorContract.SponsorEntry.COLUMN_NAME,
                                SponsorContract.SponsorEntry.COLUMN_TYPE,SponsorContract.SponsorEntry.COLUMN_LOGO},
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case SPONSOR_WITH_ID:
                retCursor = getSponsorById(uri, projection, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case SPONSOR:
                return SponsorContract.SponsorEntry.CONTENT_TYPE;
            case SPONSOR_WITH_ID:
                return SponsorContract.SponsorEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case SPONSOR: {
                long _id = db.insert(SponsorContract.SponsorEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = SponsorContract.SponsorEntry.buildSponsorUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        if(null==selection) selection="1";
        switch(match){
            case SPONSOR:
                rowsDeleted = db.delete(SponsorContract.SponsorEntry.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(rowsDeleted!=0)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;
        switch(match){
            case SPONSOR:
                rowsUpdated = db.update(SponsorContract.SponsorEntry.TABLE_NAME,values,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(rowsUpdated!=0)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = SponsorContract.CONTENT_AUTHORITY;
        matcher.addURI(authority,SponsorContract.PATH_SPONSOR,SPONSOR);
        matcher.addURI(authority,SponsorContract.PATH_SPONSOR+"/#",SPONSOR_WITH_ID);
        return matcher;
    }
}
