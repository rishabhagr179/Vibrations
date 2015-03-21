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
public class EventsProvider extends ContentProvider {
    static final int SPONSOR = 100;
    static final int SPONSOR_WITH_ID=101;
    static final int GALLERY_WITH_ID=102;
    static final int GALLERY=103;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private EventsDbHelper mOpenHelper;
    private static final SQLiteQueryBuilder sponsorQueryBuilder;
    private static final SQLiteQueryBuilder galleryQueryBuilder;

    static{
        sponsorQueryBuilder = new SQLiteQueryBuilder();
        sponsorQueryBuilder.setTables(EventsContract.SponsorEntry.TABLE_NAME);
        galleryQueryBuilder = new SQLiteQueryBuilder();
        galleryQueryBuilder.setTables(EventsContract.GalleryEntry.TABLE_NAME);
    }
    private static final String sSponsorIdSelection = EventsContract.SponsorEntry._ID + " = ? ";
    private static final String sGalleryIdSelection = EventsContract.GalleryEntry._ID + " = ? ";
    @Override
    public boolean onCreate() {
        mOpenHelper = new EventsDbHelper(getContext());
        return true;
    }
    public Cursor getSponsorById(Uri uri, String[] projection, String sortOrder){
        long id = EventsContract.SponsorEntry.getIdFromUri(uri);
        String[] selectionArgs = new String[]{Long.toString(id)};
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
    public Cursor getImageById(Uri uri, String[] projection, String sortOrder){
        long id = EventsContract.GalleryEntry.getIdFromUri(uri);
        String[] selectionArgs = new String[]{Long.toString(id)};
        String selection=sGalleryIdSelection;
        return galleryQueryBuilder.query(mOpenHelper.getReadableDatabase(),
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
                        EventsContract.SponsorEntry.TABLE_NAME,
                        new String[]{EventsContract.SponsorEntry._ID, EventsContract.SponsorEntry.COLUMN_NAME,
                                EventsContract.SponsorEntry.COLUMN_TYPE, EventsContract.SponsorEntry.COLUMN_LOGO},
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case GALLERY: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        EventsContract.GalleryEntry.TABLE_NAME,
                        new String[]{EventsContract.GalleryEntry._ID, EventsContract.GalleryEntry.COLUMN_IMAGE},
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
            case GALLERY_WITH_ID:
                retCursor = getImageById(uri,projection, sortOrder);
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
                return EventsContract.SponsorEntry.CONTENT_TYPE;
            case GALLERY:
                return EventsContract.GalleryEntry.CONTENT_TYPE;
            case SPONSOR_WITH_ID:
                return EventsContract.SponsorEntry.CONTENT_ITEM_TYPE;
            case GALLERY_WITH_ID:
                return EventsContract.GalleryEntry.CONTENT_ITEM_TYPE;
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
                long _id = db.insert(EventsContract.SponsorEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = EventsContract.SponsorEntry.buildSponsorUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case GALLERY: {
                long _id = db.insert(EventsContract.GalleryEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = EventsContract.GalleryEntry.buildGalleryUri(_id);
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
                rowsDeleted = db.delete(EventsContract.SponsorEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case GALLERY:
                rowsDeleted = db.delete(EventsContract.GalleryEntry.TABLE_NAME,selection,selectionArgs);
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
                rowsUpdated = db.update(EventsContract.SponsorEntry.TABLE_NAME,values,selection,selectionArgs);
                break;
            case GALLERY:
                rowsUpdated = db.update(EventsContract.GalleryEntry.TABLE_NAME,values,selection,selectionArgs);
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
        final String authority = EventsContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, EventsContract.PATH_SPONSOR,SPONSOR);
        matcher.addURI(authority, EventsContract.PATH_SPONSOR+"/#",SPONSOR_WITH_ID);
        matcher.addURI(authority, EventsContract.PATH_GALLERY+"/#",GALLERY_WITH_ID);
        matcher.addURI(authority, EventsContract.PATH_GALLERY,GALLERY);
        return matcher;
    }
}
