package com.example.calllogs;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
import android.provider.CallLog;
import android.provider.CallLog.Calls;;

public class Calllogs extends BroadcastReceiver{

	SQLiteDatabase dbb;
	String number,calltype,duration,time,date,logtypecode;
	Handler hand=new Handler();
	Cursor cur;
	LocalDb db;
	ContentValues values;
	int logType;
	 int cursorch=0;
	
	@Override
	public void onReceive(final Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		db=new LocalDb(context);
		
		String state=intent.getStringExtra(TelephonyManager.EXTRA_STATE);
		//Log.i("a","a");
		if(intent.getAction().equals("android.intent.action.PHONE_STATE"))
		{
			if(state.equals(TelephonyManager.EXTRA_STATE_IDLE))
			{
				hand.postDelayed(new Runnable()
				{
					
					@Override
					public void run() 
					{
						
						String[] fields={android.provider.CallLog.Calls.NUMBER,android.provider.CallLog.Calls.DATE,android.provider.CallLog.Calls.TYPE,android.provider.CallLog.Calls.DURATION};
					    
					    db.open();
					
					    cur=context.getContentResolver().query(CallLog.Calls.CONTENT_URI,fields, null, null, null);
					   
					    if(cur.getCount()>0 && cursorch==0)
					    {
					    	cursorch=1;
					    	cur.moveToLast();
					    	int i=cur.getPosition();
					    	int j=cur.getCount();
					    	Log.i("i", ""+i);
					    	Log.i("j", ""+j);
					    	
					    		String number=cur.getString(cur.getColumnIndex(CallLog.Calls.NUMBER));
					    		int type=cur.getInt(cur.getColumnIndex(CallLog.Calls.TYPE));
					    		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
					    		dateformat.setTimeZone(TimeZone.getTimeZone("GMT"));
					    		time=dateformat.getDateTimeInstance().format(new Date(0)); 
					    		Calendar c=Calendar.getInstance();
					    		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					    		String date=df.format(c.getTime());
					    		long duration=cur.getLong(cur.getColumnIndex(CallLog.Calls.DURATION));
					    		
					    		Log.i("ddd"," "+ duration);
					    		Log.i("number", " " + number);
			    				Log.i("type"," "+ type);
			    				Log.i("date&time"," "+ time);
					    		
			    				switch(type)
			    				{
			    				case CallLog.Calls.OUTGOING_TYPE:
			    				calltype="Out going";
			    				break;
			    				case CallLog.Calls.INCOMING_TYPE:
			    				calltype="In Coming";
			    				break;
			    				case CallLog.Calls.MISSED_TYPE:
			    				calltype="Missed call";
			    				break;
			    				}
			    				ContentValues values=new ContentValues();
			    				values.put("cnumber",number);
			    				values.put("ctype",calltype);
			    				values.put("cdateandtime",date);
			    				values.put("cduration",duration);
			    				db.insert_values("CallLogs1",values);
			    				db.close();
				    }
					    else
					    {
					    	cur=context.getContentResolver().query(CallLog.Calls.CONTENT_URI,fields, null, null, null);
					    	
					    Log.i("NOT","NOT"+cur.getCount());
					    }
					    
	
					}},4000);
				
				
				
			}
		}
		
		
		
	}

}
