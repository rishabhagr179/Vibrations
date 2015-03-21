package com.vib15.vibrations.app.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Agarwal on 06-03-2015.
 */
public class EventsContract {
    public static final String CONTENT_AUTHORITY = "com.vib15.vibrations.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_SPONSOR = SponsorEntry.TABLE_NAME;
    public static final String PATH_GALLERY = GalleryEntry.TABLE_NAME;

    public static final class SponsorEntry implements BaseColumns {
        public static final String TABLE_NAME = "sponsor";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LOGO="logo";
        public static final String COLUMN_TYPE="type";
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SPONSOR).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SPONSOR;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE+ "/" + CONTENT_AUTHORITY + "/" + PATH_SPONSOR;
        public static Uri buildSponsorUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static long getIdFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }
    }
    public static final class GalleryEntry implements BaseColumns {
        public static final String TABLE_NAME = "images";
        public static final String COLUMN_IMAGE="image";
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_GALLERY).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GALLERY;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE+ "/" + CONTENT_AUTHORITY + "/" + PATH_GALLERY;
        public static Uri buildGalleryUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static long getIdFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }
    }

}

