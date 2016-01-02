
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;



public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String Studants_TABLE_NAME = "students";
    public static final String Courses_TABLE_NAME = "courses";
    public static final String Departments_TABLE_NAME = "departments";
    public static final String Assigments_TABLE_NAME = "assignments";
    public static final String Studant_Course_TABLE_NAME = "student_course";
    public static final String STUDENTS_COLUMN_ID = "st_id";
    public static final String STUDENTS_COLUMN_NAME = "fname";
    public static final String STUDENTS_COLUMN_LASTNAME = "lastname";
    public static final String STUDENTS_COLUMN_PASSWORD = "password";
    public static final String STUDENTS_COLUMN_SPECIALIST = "specialist";
    public static final String STUDENTS_COLUMN_EMAIL = "email";
    public static final String COURSES_COLUMN_ID = "co_id";
    public static final String COURSES_COLUMN_NAME = "name";
    public static final String COURSES_COLUMN_DEPARTMENT_ID = "department_id";
    public static final String COURSES_COLUMN_HOURS = "hours";
    public static final String DEPARTMENTS_COLUMN_ID = "department_id";
    public static final String  DEPARTMENTS_COLUMN_NAME = "name";
    public static final String ASSIGMENTS_COLUMN_ID = "ass_id";
    public static final String   ASSIGMENTS_COLUMN_TITLE = "title";
    public static final String   ASSIGMENTS_COLUMN_DATE = "due_date";
    public static final String   ASSIGMENTS_COLUMN_TIME = "due_time";
    public static final String   ASSIGMENTS_COLUMN_COURSE_ID = "course_id";
    public static final String   ASSIGMENTS_COLUMN_DESCRIPTION = "description";


    public static final String Student_Course_COLUMN_ID = "st_co_id";
    public static final String   Student_Course_COLUMN_ST_ID = "st_id";
    public static final String   Student_Course_COLUMN_CO_ID = "co_id";
    public static final String   Student_Course_COLUMN_SEMESTER = "semester";
    public static final String   Student_Course_COLUMN_YEAR = "year";


    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table students " +
                        "(st_id integer primary key, fname text,lastname text,password text, specialist text,email text)"
        );

        db.execSQL(
                "create table departments " +
                        "(department_id integer primary key, name text)"
        );

        db.execSQL(
                "create table courses " +
                        "(co_id integer primary key, name text,department_id integer, FOREIGN KEY(" +COURSES_COLUMN_DEPARTMENT_ID+ ") REFERENCES "+Departments_TABLE_NAME+" ("+DEPARTMENTS_COLUMN_ID+"), hours integer)"
        );

        db.execSQL(
                "create table assignments " +
                        "(ass_id integer primary key, title text,due_date date,due_time time,description text, course_id integer,FOREIGN KEY(" +ASSIGMENTS_COLUMN_COURSE_ID+") REFERENCES "+Courses_TABLE_NAME+" ("+COURSES_COLUMN_ID+") )"
        );

        db.execSQL(
                "create table student_course " +
                        "(id integer primary key, st_id integer, FOREIGN KEY(" + Student_Course_COLUMN_ST_ID + ") REFERENCES " + Studants_TABLE_NAME + " (" + STUDENTS_COLUMN_ID + "),course_id integer,FOREIGN KEY(" + Student_Course_COLUMN_CO_ID + ") REFERENCES " + Courses_TABLE_NAME + " (" + COURSES_COLUMN_DEPARTMENT_ID + "),year int,semester text )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS students");
        db.execSQL("DROP TABLE IF EXISTS departments");
        db.execSQL("DROP TABLE IF EXISTS assignments");
        db.execSQL("DROP TABLE IF EXISTS courses");
        db.execSQL("DROP TABLE IF EXISTS student_course");
        onCreate(db);
    }

    public boolean insertStudent (int id, String fname, String lname, String password,String specialist, String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("st_id", id);
        contentValues.put("fname", fname);
        contentValues.put("lastname", lname);
        contentValues.put("password", password);
        contentValues.put("email", email);
        db.insert("students", null, contentValues);
        return true;
    }

    public boolean insertCourse (int id, String name, int dep_id,int hour)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("co_id", id);
        contentValues.put("name", name);
        contentValues.put("department_id", dep_id);
        contentValues.put("hours", hour);
        db.insert("courses", null, contentValues);
        return true;
    }

    public boolean insertDepartment (int id, String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("department_id", id);
        contentValues.put("name", name);

        db.insert("departments", null, contentValues);
        return true;
    }

    public boolean insertAssignment (int id, String title, int co_id, String desc,Date due_date, Time due_time)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ass_id", id);
        contentValues.put("title", title);
        contentValues.put("due_date", due_date.getDate());
        contentValues.put("due_time", due_time.getTime());
        contentValues.put("course_id", co_id);
        contentValues.put(" description", desc);

        db.insert("assignments", null, contentValues);
        return true;
    }

    public boolean insertStudent_Course (int id, int st_id, int co_id,int year,String semester)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("st_co_id", id);
        contentValues.put("st_id", st_id);
        contentValues.put("co_id", co_id);
        contentValues.put("year",year);
        contentValues.put("semester",semester);

        db.insert("student_course", null, contentValues);
        return true;
    }


    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from student_course where st_id="+id+"", null );
        return res;
    }

    /*public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        //int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
       // return numRows;
    }
*/
    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllStudent_Course()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
           // array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}
