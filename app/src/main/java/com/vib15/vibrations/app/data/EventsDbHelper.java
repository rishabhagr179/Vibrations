package com.vib15.vibrations.app.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.vib15.vibrations.app.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


/**
 * Created by Agarwal on 06-03-2015.
 */
public class EventsDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Events.db";
    private final String TABLE_NAME_DATE = "Evt_Date";
    private final String COLUMN_DATE_DAY = "day";
    private final String TABLE_NAME_EVENTS = "events";
    private final String COLUMN_ID = "id";
    private final String COLUMN_NAME = "name";
    private final String COLUMN_DETAILS = "details";
    private final String COLUMN_CATEGORY = "category";
    private final String COLUMN_LOCATION = "location";
    private final String COLUMN_DAY = "day";
    private final String COLUMN_START_TIME = "startTime";
    private final String COLUMN_END_TIME = "endTime";
    private final String COLUMN_IMAGEID = "imageid";
    private final String COLUMN_ALARM_STATUS = "alarm";
    public static final String TABLE_NAME_GALLERY = EventsContract.GalleryEntry.TABLE_NAME;
    private static final String COLUMN_ID_GALLERY= EventsContract.GalleryEntry._ID;
    private static final String COLUMN_IMAGE_GALLERY= EventsContract.GalleryEntry.COLUMN_IMAGE;
    private final String TABLE_NAME_NEWS = "latestnews";
    private final String COLUMN_NEWS = "new";
    private final String TABLE_NAME_UPDATE = "upd";
    private final String COLUMN_DESC_UPDATE = "Type";
    private final String COLUMN_NO_UPDATE = "updateNo";
    public static Context mContext;
    private SQLiteDatabase database;
    public EventsDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
        database = getWritableDatabase();
    }
    public void setUpdateNo(int q){
        database.execSQL("Update "+ TABLE_NAME_UPDATE +" Set "+ COLUMN_NO_UPDATE +"="+q+" where "+ COLUMN_DESC_UPDATE +"=\'events\'");
    }
    public int getEventUpdateNo(){
        Cursor c = database.rawQuery("Select "+ COLUMN_NO_UPDATE +" from "+ TABLE_NAME_UPDATE +" Where "+ COLUMN_DESC_UPDATE +"=\'events\'",null);
        c.moveToFirst();
        return c.getInt(c.getColumnIndex(COLUMN_NO_UPDATE));
    }
    public void setNews(String q){
        database.execSQL("Update "+ TABLE_NAME_NEWS +" Set "+COLUMN_NEWS+"=\'"+q+"\'");
    }
    public String getNews(){
        Cursor c = database.rawQuery("Select "+COLUMN_NEWS+" from "+ TABLE_NAME_NEWS,null);
        c.moveToFirst();
        return c.getString(c.getColumnIndex(COLUMN_NEWS));
    }
    public int getNo(){
        SQLiteDatabase database = (new EventsDbHelper(mContext)).getWritableDatabase();
        Cursor c = database.rawQuery("select * from "+ EventsContract.SponsorEntry.TABLE_NAME,null);
        c.moveToFirst();
        int i=1;
        while(c.moveToNext())
            i++;
        database.close();
        return i;
    }
    public int getDay(){
        Cursor c = database.rawQuery("Select "+COLUMN_DATE_DAY+" from "+ TABLE_NAME_DATE,null);
        c.moveToFirst();
        return c.getInt(c.getColumnIndex(COLUMN_DATE_DAY));
    }
    public void setAlarm(int id, int val) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ALARM_STATUS, val);
        database.update(TABLE_NAME_EVENTS, cv, COLUMN_ID + " = " + id, null);
    }

    public Cursor getAllRecords() {
        return database.rawQuery("SELECT * FROM " + TABLE_NAME_EVENTS + " WHERE "
                + COLUMN_DETAILS + " not like \'1\'", null);

    }

    public Cursor getRegisterableRecords() {
        return database.rawQuery("SELECT * FROM " + TABLE_NAME_EVENTS + " WHERE "
                + COLUMN_NAME + " not like \'%(F)%\' AND " + COLUMN_NAME
                + " not like \'%(R2)\' AND " + COLUMN_NAME
                + " not like \'Inauguration\' AND " + COLUMN_NAME
                + " not like \'Band Performance\' AND " + COLUMN_NAME
                + " not like \'%(s)\' AND "+COLUMN_NAME+" not like \'Troogle\' ORDER BY " + COLUMN_CATEGORY, null);

    }
    public Cursor getEventsRecords() {
        return database.rawQuery("SELECT * FROM " + TABLE_NAME_EVENTS + " WHERE "
                + COLUMN_NAME + " not like \'%(F)%\' AND " + COLUMN_NAME
                + " not like \'%(R2)\' AND " + COLUMN_NAME
                + " not like \'Inauguration\' AND " + COLUMN_NAME
                + " not like \'Band Performance\' AND " + COLUMN_NAME
                + " not like \'%(s)\'  ORDER BY " + COLUMN_CATEGORY, null);

    }

    public Cursor getRecordsSortedByTimeOfDay(int day) {
        return database.rawQuery("SELECT * FROM " + TABLE_NAME_EVENTS + " WHERE "
                        + COLUMN_DAY + "=" + day + " AND "+COLUMN_CATEGORY+" != \'Troogle\' ORDER BY " + COLUMN_START_TIME,
                null);
    }
    public Cursor getRecordsSortedByTimeOfDay(int day, String location) {
        return database.rawQuery("SELECT * FROM " + TABLE_NAME_EVENTS + " WHERE "
                        + COLUMN_DAY + "=" + day  + " AND "+COLUMN_CATEGORY+" != \'Troogle\' and " + COLUMN_LOCATION
                        + " like \'" + location + "\' ORDER BY " + COLUMN_START_TIME,
                null);
    }

    public Cursor getRecordsofCategory(String Category) {
        return database.rawQuery("SELECT * FROM " + TABLE_NAME_EVENTS + " WHERE "
                + COLUMN_CATEGORY + " like \'" + Category + "\'" + " AND "
                + COLUMN_DETAILS + " not like \'1\'", null);
    }

    public Cursor getRecordsSortedByCategory() {
        return database.rawQuery("SELECT * FROM " + TABLE_NAME_EVENTS + " WHERE "
                + COLUMN_DETAILS + " not like \'1\' " + " ORDER BY "
                + COLUMN_CATEGORY, null);
    }

    public Cursor getDetailsofEvent(String eventName) {
        return database.rawQuery("SELECT * FROM " + TABLE_NAME_EVENTS + " WHERE "
                + COLUMN_NAME + " like \'" + eventName + "\'" + " AND "
                + COLUMN_DETAILS + " not like \'1\'", null);
    }

    public Cursor getHotEvents(int currentTime, int day) {
        return database.rawQuery("SELECT * FROM " + TABLE_NAME_EVENTS + " WHERE "
                + COLUMN_DAY + " = " + day + " AND " + COLUMN_START_TIME
                + " <= " + currentTime + " AND " + COLUMN_END_TIME + ">"
                + currentTime, null);
    }

    public void updateDetails(String q){
        database.execSQL(q);
    }
    public void insertImage(int id, Bitmap image)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte imageInByte[] = stream.toByteArray();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_ID_GALLERY, id);
        cv.put(COLUMN_IMAGE_GALLERY, imageInByte);
        mContext.getContentResolver().insert(EventsContract.GalleryEntry.CONTENT_URI,cv);
    }

    public Cursor getAllGalleryRecords()
    {
        return (database.rawQuery("SELECT * FROM "+ TABLE_NAME_GALLERY,null));
    }

    public ArrayList<Bitmap> getImages(){

        ArrayList<Bitmap> images = new ArrayList<Bitmap>();
        ByteArrayInputStream imageStream;
        Cursor c=getAllGalleryRecords();
        Bitmap image;
        c.moveToFirst();
        do {

            imageStream = new ByteArrayInputStream(c.getBlob(1));
            image = BitmapFactory.decodeStream(imageStream);
            images.add(image);
        } while (c.moveToNext());
        return images;
    }
    public Bitmap getImageByPosition(int pos){
        // ArrayList<Bitmap> images=new ArrayList<Bitmap>();
        Cursor c= (database.rawQuery("SELECT * FROM "+ TABLE_NAME_GALLERY +" where "+ COLUMN_ID_GALLERY +"="+(pos+1),null));
        c.moveToFirst();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(c.getBlob(1));
        Bitmap image = BitmapFactory.decodeStream(imageStream);
        return image;

    }
    public void close() {
        database.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_NAME_UPDATE +"( type TEXT(10), updateNo NUMBER)");
        db.execSQL("INSERT INTO "+ TABLE_NAME_UPDATE +" VALUES( \'events\',0)");
        db.execSQL("CREATE TABLE "+ TABLE_NAME_NEWS +"("+COLUMN_NEWS+" TEXT)");
        db.execSQL("INSERT INTO "+ TABLE_NAME_NEWS +" VALUES( \'Preparations! Wait till 10th!\')");
        db.execSQL("create table " + TABLE_NAME_GALLERY + "( " + COLUMN_ID_GALLERY
                + " NUMBER PRIMARY KEY, " + COLUMN_IMAGE_GALLERY + " BLOB)");

        final String SQL_CREATE_SPONSOR_TABLE="CREATE TABLE "+ EventsContract.SponsorEntry.TABLE_NAME+" ("
                + EventsContract.SponsorEntry._ID+" INTEGER PRIMARY KEY, "
                + EventsContract.SponsorEntry.COLUMN_NAME+" TEXT NOT NULL, "
                + EventsContract.SponsorEntry.COLUMN_TYPE+" TEXT NOT NULL, "
                + EventsContract.SponsorEntry.COLUMN_LOGO+" INTEGER );";
        db.execSQL(SQL_CREATE_SPONSOR_TABLE);

        db.execSQL("create table " + TABLE_NAME_EVENTS + "( " + COLUMN_ID
                + " NUMBER PRIMARY KEY, " + COLUMN_NAME + " TEXT(30), "
                + COLUMN_DETAILS + " NUMBER, " + COLUMN_CATEGORY
                + " NUMBER, " + COLUMN_LOCATION + " TEXT, " + COLUMN_DAY
                + " TEXT, " + COLUMN_START_TIME + " NUMBER, "
                + COLUMN_END_TIME + " NUMBER, " + COLUMN_IMAGEID
                + " NUMBER, " + COLUMN_ALARM_STATUS + " NUMBER)");

        // DAY 1
        db.execSQL("INSERT INTO events values"
                + "( 1 , \'Mock Parliament\', " + R.string.mockparliament
                + ",\'" + Constants.Category.LEKHANI + "\',\'"
                + Constants.Location.SEMINAR_HALL + "\', 1, 1030, 1130,"
                + R.drawable.parliament + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 2 , \'Word Nerd (P)\', " + R.string.wordnerd + ",\'"
                + Constants.Category.QUESTS + "\',\'"
                + Constants.Location.C11_12_13 + "\', 1, 1100, 1130, "
                + R.drawable.word + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 3 , \'Comic Script\', " + R.string.na + ",\'"
                + Constants.Category.CREATIVE_EVENTS + "\',\'"
                + Constants.Location.C8_9_10 + "\', 1, 1130, 1230, "
                + R.drawable.glueit + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 5 , \'Spin-A-Yarn\', " + R.string.na + ",\'"
                + Constants.Category.LEKHANI + "\',\'"
                + Constants.Location.SEMINAR_HALL + "\', 1, 1230, 1330, "
                + R.drawable.extempore + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 6 , \'Grid Lock\', " + R.string.na + ",\'"
                + Constants.Category.QUESTS + "\',\'"
                + Constants.Location.IQ + "\', 1, 1300, 1400, "
                + R.drawable.scrapper + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 7 , \'Antakshari (P)\', " + R.string.antakshri + ",\'"
                + Constants.Category.QUESTS + "\',\'"
                + Constants.Location.C11_12_13 + "\', 1, 1330, 1400, "
                + R.drawable.antakshari + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 8 , \'Personality Hunt (R1)\', "
                + R.string.personalityhunt + ",\'"
                + Constants.Category.PERSONALITY_HUNT + "\',\'"
                + Constants.Location.C8_9_10 + "\', 1, 1400, 1430, "
                + R.drawable.personality_hunt + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 10 , \'Antakshari (F)\', " + R.string.antakshri
                + ",\'" + Constants.Category.QUESTS + "\',\'"
                + Constants.Location.IQ + "\', 1, 1430, 1500, "
                + R.drawable.antakshari + ", 0)");

        // DAY 2
        db.execSQL("INSERT INTO events values"
                + "( 11 , \'Egg Painting\', " + R.string.eggpainting
                + ",\'" + Constants.Category.CREATIVE_EVENTS + "\',\'"
                + Constants.Location.IQ + "\', 2, 1000, 1100, "
                + R.drawable.egg + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 12 , \'Carta Logy\', " + R.string.na + ",\'"
                + Constants.Category.QUESTS + "\',\'"
                + Constants.Location.SEMINAR_HALL + "\', 2, 1030, 1130, "
                + R.drawable.visionary + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 13 , \'Portray\', " + R.string.portray + ",\'"
                + Constants.Category.CREATIVE_EVENTS + "\',\'"
                + Constants.Location.C8_9_10 + "\', 2, 1030, 1130, "
                + R.drawable.portray + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 14 , \'Incredible India(P)\',"
                + R.string.incredibleindia + ",\'"
                + Constants.Category.QUESTS + "\',\'"
                + Constants.Location.C11_12_13 + "\', 2, 1130, 1200, "
                + R.drawable.incredible_india + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 15 , \'FoosBall\', " + R.string.foosball+ ",\'"
                + Constants.Category.TROOGLE + "\',\'"
                + Constants.Location.IQ + "\', 2, 1200, 1300, "
                + R.drawable.foosball + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 16 , \'Pictionary\', " + R.string.pictionary + ",\'"
                + Constants.Category.LEKHANI + "\',\'"
                + Constants.Location.C8_9_10 + "\', 2, 1200, 1230, "
                + R.drawable.pictionary + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 17 , \'Finding Fanny\', " + R.string.treasurehunt
                + ",\'" + Constants.Category.TROOGLE + "\',\'Whole Campus\', 2, 1230, 1330, "
                + R.drawable.treasurehunt + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 18 , \'Indradhanush\', " + R.string.indradhanush
                + ",\'" + Constants.Category.CREATIVE_EVENTS + "\',\'"
                + Constants.Location.C11_12_13 + "\', 2, 1230, 1330, "
                + R.drawable.rangoli + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 19 , \'Goofie\', " + R.string.goofie
                + ",\'" + Constants.Category.TROOGLE + "\',\'"
                + Constants.Location.SEMINAR_HALL + "\', 2, 1300, 1330, "
                + R.drawable.goofie + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 20 , \'Incredible India(F)\', "
                + R.string.incredibleindia + ",\'"
                + Constants.Category.QUESTS + "\',\'"
                + Constants.Location.SEMINAR_HALL + "\', 2, 1400, 1500, "
                + R.drawable.incredible_india + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 21 , \'Copy Cats\', " + R.string.copycat + ",\'"
                + Constants.Category.CREATIVE_EVENTS + "\',\'"
                + Constants.Location.C8_9_10 + "\', 2, 1400, 1500, "
                + R.drawable.copycat + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 22 , \'Personality Hunt(R2)\', "
                + R.string.personalityhunt + ",\'"
                + Constants.Category.PERSONALITY_HUNT + "\',\'"
                + Constants.Location.C11_12_13 + "\', 2, 1430, 1500, "
                + R.drawable.personality_hunt + ", 0)");

        // DAY 3
        db.execSQL("INSERT INTO events values"
                + "( 23 , \'Splash\', " + R.string.splash + ",\'"
                + Constants.Category.CREATIVE_EVENTS + "\',\'"
                + Constants.Location.C11_12_13 + "\', 3, 1000, 1100, "
                + R.drawable.splash + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 24 , \'Quidditch\', " + R.string.quiddich
                + ",\'" + Constants.Category.TROOGLE + "\',\'"
                + Constants.Location.IQ + "\', 3, 1030, 1130, "
                + R.drawable.qudditch + ", 0)");

        db.execSQL("INSERT INTO events values" + "( 25 , \'ACT-O-PROP\', "
                + R.string.admad + ",\'" + Constants.Category.LEKHANI
                + "\',\'" + Constants.Location.IQ + "\', 3, 1100, 1200, "
                + R.drawable.ad_mad + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 26 , \'Entertainment Quiz (P)\', "
                + R.string.entertainmentquiz + ",\'"
                + Constants.Category.QUESTS + "\',\'"
                + Constants.Location.C11_12_13 + "\', 3, 1130, 1200, "
                + R.drawable.entertainment_quiz + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 29 , \'Nukkad Natak\', " + R.string.nukkadnatak
                + ",\'" + Constants.Category.ABHIVYAKTI + "\',\'"
                + Constants.Location.IQ + "\', 3, 1300, 1400, "
                + R.drawable.nukaad_natak + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 30 , \'Entertainment Quiz (F)\', "
                + R.string.entertainmentquiz + ",\'"
                + Constants.Category.QUESTS + "\',\'"
                + Constants.Location.SEMINAR_HALL + "\', 3, 1400, 1500, "
                + R.drawable.entertainment_quiz + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 33 , \'Stage Play \', " + R.string.stageplay + ",\'"
                + Constants.Category.ABHIVYAKTI + "\' , \'"
                + Constants.Location.STAGE_AREA + "\', 1, 1700, 0, "
                + R.drawable.stage_play + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 34 , \'Footloose \', " + R.string.footloose + ",\'"
                + Constants.Category.TAAL + "\',\'"
                + Constants.Location.STAGE_AREA + "\', 1, 1800, 0, "
                + R.drawable.solo_dance + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 35 , \'Band Performance\', "
                + R.string.bandperformance1 + ",\'"
                + Constants.Category.LEKHANI + "\',\'"
                + Constants.Location.STAGE_AREA + "\', 1, 1900, 0, "
                + R.drawable.band_performance + ", 0)");
        db.execSQL("INSERT INTO events values"
                + "( 47 , \'BIT Idol \'," + R.string.bitidol + ",\'"
                + Constants.Category.TARANG + " \',\'"
                + Constants.Location.STAGE_AREA + "\', 1, 1600, 0, "
                + R.drawable.solo_singing + ", 0)");
        // On Stage Events DAY 2

        db.execSQL("INSERT INTO events values"
                + "( 38 , \' Voice-A-Verse\', " + R.string.voiceaversa
                + ",\'" + Constants.Category.LEKHANI + "\',\'"
                + Constants.Location.STAGE_AREA + "\', 2, 1600, 0, "
                + R.drawable.voice_a_verse + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 39 , \'Symphony \', " + R.string.symphony + ",\'"
                + Constants.Category.TARANG + "\',\'"
                + Constants.Location.STAGE_AREA + "\', 2, 1700, 0, "
                + R.drawable.band_performance + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 40 , \'Two-to-Tango\', " + R.string.twototango + ",\'"
                + Constants.Category.TAAL + "\',\'"
                + Constants.Location.STAGE_AREA + "\', 2, 1800, 0, "
                + R.drawable.two_to_tango + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 41 , \' Band Performance(s)\', "
                + R.string.bandperformance2 + ",\'"
                + Constants.Category.LEKHANI + "\',\'"
                + Constants.Location.STAGE_AREA + "\', 2, 1900, 0, "
                + R.drawable.band_performance + ", 0)");

        // On Stage Events DAY 3

        db.execSQL("INSERT INTO events values"
                + "( 42 , \'Personality Hunt (F)\', "
                + R.string.personalityhunt + ",\'"
                + Constants.Category.PERSONALITY_HUNT + "\',\'"
                + Constants.Location.STAGE_AREA + "\', 3, 1530, 0, "
                + R.drawable.personality_hunt + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 43 , \'Sur Sangam \', " + R.string.sursangam + ",\'"
                + Constants.Category.TARANG + "\',\'"
                + Constants.Location.STAGE_AREA + "\', 3, 1600, 0, "
                + R.drawable.sur + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 44 , \'Ghungroo\', " + R.string.ghunghroo + ",\'"
                + Constants.Category.TAAL + "\',\'"
                + Constants.Location.STAGE_AREA + "\', 3, 1700, 0, "
                + R.drawable.ghunghroo + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 45 , \'Razzmataz\', " + R.string.Razzmatazz + ",\'"
                + Constants.Category.TAAL + "\',\'"
                + Constants.Location.STAGE_AREA + "\', 3, 1800, 0, "
                + R.drawable.group_dance + ", 0)");

        db.execSQL("INSERT INTO events values"
                + "( 46 , \'Talent Walk \', " + R.string.talentwalk + ",\'"
                + Constants.Category.TALENT_WALK + "\',\'"
                + Constants.Location.STAGE_AREA + "\', 3, 1900, 0, "
                + R.drawable.talent_walk + ", 0)");
        db.execSQL("create table " + TABLE_NAME_DATE + "( "+COLUMN_DATE_DAY+" Number)");
        db.execSQL("INSERT INTO "+ TABLE_NAME_DATE +" values(9)");
        db.execSQL("INSERT INTO "+ EventsContract.SponsorEntry.TABLE_NAME+" VALUES( 0,\'Cafe Heart\',\'Beverage Partner\',"+ R.drawable.sponsors1+");");
        db.execSQL("INSERT INTO "+ EventsContract.SponsorEntry.TABLE_NAME+" VALUES( 1,\'Endeavour\',\'Technical Education Partner\',"+R.drawable.sponsors2+");");
        db.execSQL("INSERT INTO "+ EventsContract.SponsorEntry.TABLE_NAME+" VALUES( 2,\'Lailas\',\'Fashion Partner\',"+R.drawable.sponsors3+");");
        db.execSQL("INSERT INTO "+ EventsContract.SponsorEntry.TABLE_NAME+" VALUES( 3,\'Ind it\',\'Food Partner\',"+R.drawable.sponsors4+");");
        db.execSQL("INSERT INTO "+ EventsContract.SponsorEntry.TABLE_NAME+" VALUES( 4,\'Pink Square\',\'Mall Partner\',"+R.drawable.sponsors5+");");
        db.execSQL("INSERT INTO "+ EventsContract.SponsorEntry.TABLE_NAME+" VALUES( 5,\'Tatoo Baba\',\'Tatoo Partner\',"+R.drawable.sponsors6+");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_NEWS);
        db.execSQL("DROP TABLE IF EXISTS " + EventsContract.SponsorEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DATE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DATE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_GALLERY);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_UPDATE);
        onCreate(db);
    }
}
