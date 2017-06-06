package examples.com.examples.DB;

//import java.util.ArrayList;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
//import android.widget.ArrayAdapter;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class DataBaseAdapter {
	
	static final String DATABASE_NAME = "Dnd.rajesh.db";
    
    static final int DATABASE_VERSION = 1;

    public static final int NAME_COLUMN = 1;
    
    Cursor cursor;
    int count;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table "+"CONTACTS"+
                                 "( " +"ID"+" integer primary key autoincrement,"+ ""
                                 		+ "NAME  text);";
   /* static final String DATABASE_CREATE1 = "create table "+"BLACK_LIST_LOGS"+
            "( " +"ID"+" integer primary key autoincrement,"+ ""
            		+ "NAME  text);";
 */
    static final String DATABASE_CREATE_1="create table BLACK_LIST (ID integer primary key autoincrement,NAME text,NUMBER text,TIME text);";
    // Variable to hold the database instance
    public  SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelper dbHelper;
    
    public  DataBaseAdapter(Context _context) 
    {
            context = _context;
            dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
            System.out.println("db intialised");
    }
    // Method to openthe Database  
    public  DataBaseAdapter open() throws SQLException 
    {
            db = dbHelper.getWritableDatabase();
            return this;
    }
    
 // Method to close the Database  
    public void close() 
    {
            db.close();
    }
    // method returns an Instance of the Database 
    public  SQLiteDatabase getDatabaseInstance()
    {
            return db;
    }
 // method to insert a record in Table
    
    /*public void getContactCount(){
   Log.i("", "getContactCount");
     
    	cursor= db.rawQuery("select * from CONTACTS", null);	
		if (cursor.moveToFirst()) {
			MyPhoneStateListener.contacts_list_count=true;
			 Log.i("", "getContactCount contacts exists");					
				
		}
		cursor.close();
		db.close();
    }*/
    public void getAllContacts(){
    	//DataBaseView d= new DataBaseView();
    	DataBaseView.al.clear();
    	Log.i("", "getAllContacts");
    	
    	cursor= db.rawQuery("select * from CONTACTS", null);	
		if (cursor.moveToFirst()) {
			//MyPhoneStateListener.contacts_list_count=true;
									
			 do {
				 //System.out.println("entered if loop");
				 //String id =cursor.getString(cursor.getColumnIndex("ID"));
				 String name =cursor.getString(cursor.getColumnIndex("NAME"));	
				 //String number =cursor.getString(cursor.getColumnIndex("NUMBER"));	
				 Log.i("", "getAllContacts "+name);
				 DataBaseView.al.add(name);					
			} 
			 while (cursor.moveToNext());	 			
		}
		cursor.close();
		db.close();
    }
    public String getNumber(String name, String time){
    	//DataBaseView d= new DataBaseView();
    	//DataBaseView.al.clear();
    	String num=null;
    	Log.i("", "getNumber "+name+ "   "+time);
    	
    	cursor= db.rawQuery("select NUMBER from BLACK_LIST where NAME='"+name+"' and TIME='"+time+"'", null);	
    	//cursor= db.rawQuery("select * from BLACK_LIST where TIME ='"+time+"'", null);	
    	
		if (cursor.moveToFirst()) {
			//MyPhoneStateListener.contacts_list_count=true;
									
			 do {
				 //System.out.println("entered if loop");
				 //String id =cursor.getString(cursor.getColumnIndex("ID"));
				  num =cursor.getString(cursor.getColumnIndex("NUMBER"));	
				 //String number =cursor.getString(cursor.getColumnIndex("NUMBER"));	
				 Log.i("", "getNumber number "+num);
				 //DataBaseView.al.add(name);					
			} 
			 while (cursor.moveToNext());	 			
		}
		cursor.close();
		db.close();
		return num;
    }
    
    public void getAllBlocklogs(){
    	//DataBaseView d= new DataBaseView();
    	BlackListLogs.cardArrayAdapter.clear();
    	Log.i("", "getAllBlocklogs");
    	try {
    		cursor= db.rawQuery("select * from BLACK_LIST", null);	
        	
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("", "getAllBlocklogs eeee"+e); 
		}
    	
		if (cursor.moveToFirst()) {
			//MyPhoneStateListener.contacts_list_count=true;
			//Log.i("", "getAllBlocklogs");
			cursor.moveToLast();
			 do {
				// Log.i("", "getAllBlocklogs");
				//System.out.println("entered if loop");
				//String id =cursor.getString(cursor.getColumnIndex("ID"));
				 String name =cursor.getString(cursor.getColumnIndex("NAME"));	
				 String number =cursor.getString(cursor.getColumnIndex("NUMBER"));	
				 String time =cursor.getString(cursor.getColumnIndex("TIME"));	
				 try {
					 Log.i("", "getAllBlocklogs name "+name+"   number "+number+"     time"+time);
						
				} catch (Exception e) {
					// TODO: handle exception
					Log.i("", "getAllBlocklogs eeeeeeeeeee");
					 
				}
				  Card card = new Card(name, number,time);
				  //Log.i("", "getAllBlocklogs card");
					 
				 BlackListLogs.cardArrayAdapter.add(card);	
				  //BlackListLogs.bl.add(name+"\n"+number+"\n"+time);
				  //Log.i("", "getAllBlocklogs cardArrayAdapter");
				 
			} 
			 while (cursor.moveToPrevious());	 
			 
		}
		cursor.close();
		db.close();
    }
   
	public void insertEntry(String Name)
    {   	
     //Cursor Cursor=null;
    	
    	
    	//StringBuilder sb = new StringBuilder(Pattern.length);

    	
    	 //System.out.println(":  "+sb);
    	       // String str=sb.toString();
    	         ContentValues newValues = new ContentValues();
                // Assign values for each column.
                newValues.put("NAME", Name);
                                                 
                // Insert the row into your table
                db.insert("CONTACTS", null, newValues);
               
				//String name =Cursor.getString(Cursor.getCount());
                Log.i("info", "db insertentry method,DataBaseAdapter: Name inserted");
                Toast.makeText(context, " Info Saved.  ", Toast.LENGTH_LONG).show();
    	 
    }
	public void insert_Blaclist_Entry(String Name, String number)
    {   	
		
		 Calendar calender=Calendar.getInstance();
         SimpleDateFormat date= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        // date.format(Date.)
         String time =date.format(calender.getTime());
        Log.i("", "insert_Blaclist_Entry  is "+Name+"  "+time+"number  "+number);
     //Cursor Cursor=null;
    	
    	
    	//StringBuilder sb = new StringBuilder(Pattern.length);

    	
    	 //System.out.println(":  "+sb);
    	       // String str=sb.toString();
    	         ContentValues newValues = new ContentValues();
                // Assign values for each column.
                newValues.put("NAME", Name);
                newValues.put("NUMBER", number);
                newValues.put("TIME", time);
                                                 
                // Insert the row into your table
                db.insert("BLACK_LIST", null, newValues);
               
				//String name =Cursor.getString(Cursor.getCount());
                Log.i("info", "db insertentry method,DataBaseAdapter: insert_Blaclist_Entry Name inserted");
               // Toast.makeText(context, " Info Saved.  ", Toast.LENGTH_LONG).show();
    	 
    }
    
    // method to delete a Record of UserName
     public void deleteEntry(String Name)
     {
    	 
    	
            db.execSQL("DELETE FROM CONTACTS WHERE NAME='"+Name+"'");
            Log.i("info", "db deleteentry method,DataBaseAdapter: deleted name: "+Name);
            //Toast.makeText(context,newname+ " Deleted Successfully", Toast.LENGTH_LONG).show();
         
        
     }
     public void deleteBlackEntry(String Name,String time)
     {
    	 
    	
            db.execSQL("DELETE FROM BLACK_LIST WHERE NAME='"+Name+"' and TIME='"+time+"'");
            Log.i("info", "db deleteentry method,DataBaseAdapter: deleted name: "+Name);
            //Toast.makeText(context,newname+ " Deleted Successfully", Toast.LENGTH_LONG).show();
         
        
     }
     public void verifynumber(String phonenumber){
    	 //this method makes sure that no duplicate numbers exist if exists it deletes and will be added in insertEntry method
    	 cursor= db.rawQuery("select * from DETAILS where NUMBER='"+phonenumber+"'", null);
 		
 		if (cursor.moveToFirst()) {
 			//name exits in database
 			 db.execSQL("DELETE FROM DETAILS WHERE NUMBER='"+phonenumber+"'"); 			
 			 System.out.println("DataBaseAdapter,contact existed so deleted from db and will be added : "+phonenumber);
			Toast.makeText(context,"Pattern already exists on this number,changes overridden", Toast.LENGTH_SHORT).show();
     }
 		else {
 			Log.i("info", "db verify method,DataBaseAdapter: the name does not exist in db ");
 		     
			
		}
 		cursor.close();
 		
     }
     public void verifyname(String name){
    	 //this method makes sure that no duplicate numbers exist if exists it deletes and will be added in insertEntry method
    	 cursor= db.rawQuery("select * from CONTACTS where NAME='"+name+"'", null);
 		
 		if (cursor.moveToFirst()) {
 			//name exits in database
 			 db.execSQL("DELETE FROM DETAILS WHERE NAME='"+name+"'"); 			
 			 System.out.println("DataBaseAdapter,contact existed so deleted from db and will be added : "+name);
			Toast.makeText(context,"Pattern already exists on this number,changes overridden", Toast.LENGTH_SHORT).show();
     }
 		else {
 			Log.i("info", "db verify method,DataBaseAdapter: the name does not exist in db ");
 		     
			
		}
 		cursor.close();
 		
     }
     public Boolean CheckName(String name){
    	 //String name="All contacts";
    	 cursor= db.rawQuery("select * from CONTACTS where NAME='"+name+"'", null);
    	 if (cursor.moveToFirst()) {
    		 Log.i("info", "CheckName name exists  ");
    	    	
    		 cursor.close();
    		 return true;  		 
    	 }
    	 else{
    		 cursor.close();
    		 return false;
    	 }
    	
    		
     }
     public String GetPattern (String name){
    	 String pattern=null;
    	 cursor= db.rawQuery("select PATTERN from DETAILS where NAME='"+name+"'", null);
    	 if (cursor.moveToFirst()) {
    		  pattern =cursor.getString(cursor.getColumnIndex("PATTERN"));
    	 }
    	 cursor.close();
    	 Log.i("info", "DataBaseAdapter.java: pattern is  "+pattern);
    	 return pattern;
    	 
    	
     }
     public Boolean checkpackage(String name){
    	 cursor= db.rawQuery("select PATTERN from DETAILS where PACKAGE='"+name+"'", null);
    	 if (cursor.moveToFirst()) {
    		 cursor.close();
    		 return true;  		 
    	 }
    	 else{
    		 cursor.close();
    		 return false;
    	 }
    	 
     }
     public String GetPatternPackage (String name){
    	 String pattern=null;
    	 cursor= db.rawQuery("select PATTERN from DETAILS where PACKAGE='"+name+"'", null);
    	 if (cursor.moveToFirst()) {
    		  pattern =cursor.getString(cursor.getColumnIndex("PATTERN"));
    	 }
    	 cursor.close();
    	 return pattern;
    	 
    	
     }
     public void deleteall(){
    	 String TABLE_NAME="CONTACTS";
    	 db.execSQL("delete from "+ TABLE_NAME);
    	 Log.i("tag", "deleted all");
    	 
     }
    
     
    
    	 
    	 
     }

     

