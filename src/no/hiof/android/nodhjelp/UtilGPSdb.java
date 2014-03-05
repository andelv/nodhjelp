package no.hiof.android.nodhjelp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UtilGPSdb {
	private Context context;
	private DBhelper dbhelper;
	public final String DBname = "GPSdb";
	public final int DBversion = 3;
	public SQLiteDatabase db;
	
	public final String TABLENAME = "location";
	public final String COLUMN_LOCATION_ID = "locationId";
	public final String COLUMN_LAT = "lat";
	public final String COLUMN_LON = "lon";
	public final String COLUMN_HEIGHT =  "height";
	public final String COLUMN_TIME =  "time";
	
	public final String CREATEDB = "create table location(locationId integer " +
			"primary key autoincrement, lat integer not NULL, lon integer not NULL" +
			", height integer not NULL, time text not NULL);";
	
	public UtilGPSdb(Context context){
		this.context = context;
		dbhelper = new DBhelper(context);
	}
	
	public class DBhelper extends SQLiteOpenHelper{
		public DBhelper(Context context){
			super(context, DBname, null, DBversion);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db){
			db.execSQL(CREATEDB);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public void  insertRows(float lat, float lon, float height, String time){
		open();
		ContentValues values = new ContentValues();
		
		values.put(COLUMN_LAT, lat);
		values.put(COLUMN_LON, lon);
		values.put(COLUMN_HEIGHT, height);
		values.put(COLUMN_TIME, time);
		
		db.insert(TABLENAME, null, values);
		close();
	}
	
	public Cursor getAllRows(){
		Cursor cursor = db.query(TABLENAME, new String[]{COLUMN_LOCATION_ID, 
				COLUMN_LAT, COLUMN_LON, COLUMN_HEIGHT, COLUMN_TIME}, 
				null, null, null, null, COLUMN_TIME);
		return cursor;
	}
	
	public void open() throws SQLException{
		db = dbhelper.getWritableDatabase();
	}
	
	public void close(){
		dbhelper.close();
	}
}
