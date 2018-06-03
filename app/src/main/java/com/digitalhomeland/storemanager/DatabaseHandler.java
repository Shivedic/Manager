package com.digitalhomeland.storemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.digitalhomeland.storemanager.models.Application;
import com.digitalhomeland.storemanager.models.Attendance;
import com.digitalhomeland.storemanager.models.EmpRoster;
import com.digitalhomeland.storemanager.models.Employee;
import com.digitalhomeland.storemanager.models.Notif;
import com.digitalhomeland.storemanager.models.StRoster;
import com.digitalhomeland.storemanager.models.Store;
import com.digitalhomeland.storemanager.models.TaskInst;
import com.digitalhomeland.storemanager.models.Taskd;
import com.digitalhomeland.storemanager.models.TaskdInst;
import com.digitalhomeland.storemanager.models.Tasko;
import com.digitalhomeland.storemanager.models.Taskw;
import com.digitalhomeland.storemanager.models.TaskwInst;
import com.digitalhomeland.storemanager.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prthma on 13-05-2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 39;

    // Database Name
    private static final String DATABASE_NAME = "employeeDB";

    // table name
    private static final String TABLE_NOTIFICATION = "notificationsTable";
    private static final String TABLE_ATTENDANCE = "attendanceTable";
    private static final String TABLE_APPLICATIONS = "applicationsTable";
    private static final String TABLE_USER = "userTable";
    private static final String TABLE_STORE = "storeTable";
    private static final String TABLE_EMPLOYEE = "employeeTable";
    private static final String TABLE_TASKD = "taskdTable";
    private static final String TABLE_TASKDINST = "taskdInstTable";
    private static final String TABLE_TASKW = "taskwTable";
    private static final String TABLE_TASKWINST = "taskwInstTable";
    private static final String TABLE_TASKO = "taskoTable";
    private static final String TABLE_STROSTER = "stRosterTable";
    private static final String TABLE_EMPROSTER = "empRosterTable";


    // Notifications Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_STUDENT = "student";
    private static final String KEY_TITLE = "title";
    private static final String KEY_SUBJECT = "subject";
    private static final String KEY_DATE = "date";
    private static  final String KEY_NEID = "employeeId";
    private static  final String KEY_NSEQID = "seqId";

    //Attendance Table Columns
    private static final String KEY_VALUE = "value";
    private static final String KEY_DATEINST = "date";
    private static  final String KEY_AEMPID = "employeeId";


    //Application Table column
    private static final String KEY_AID = "id";
    private static final String KEY_ACCEPTANCE = "accepted";
    private static final String KEY_ATITLE = "title";
    private static final String KEY_ASUBJECT = "subject";
    private static final String KEY_ADATE = "date";
    private static  final String KEY_AEID = "employeeId";
    private static final String KEY_ASEQID = "seqId";

    //User Table column
    private static final String KEY_UFIRSTNAME = "firstname";
    private static final String KEY_UMIDDLENAME = "middlename";
    private static final String KEY_ULASTNAME = "lastname";
    private static final String KEY_UPHONE = "phone";
    private static final String KEY_UEMAIL = "email";
    private static final String KEY_UAADHARID = "aadharid";
    private static final String KEY_UEID = "employeeid";
    private static final String KEY_USTOREID = "storeid";
    private static final String KEY_UTEAMNAME = "teamName";
    private static final String KEY_UDESIGNATION = "designation";


    //Store Table column
    private static final String KEY_SID = "id";
    private static final String KEY_SNAME = "name";
    private static final String KEY_SSTOREID = "storeid";
    private static final String KEY_SCITY = "city";
    private static final String KEY_SSTATE = "state";
    private static final String KEY_SLAT = "latitude";
    private static final String KEY_SLONG = "longitude";
    private static final String KEY_SADDRESS = "address";
    private static final String KEY_SEMPCOUNT = "empCount";
    private static final String KEY_SKEYSTATE = "keyState";
    private static final String KEY_SSELLERID = "sellerId";
    private static final String KEY_SCLOSINGDAY = "closingDay";
    private static final String KEY_SROSTERGENDAY = "rosterGenDay";

    //User Table column
    private static final String KEY_EFIRSTNAME = "firstname";
    private static final String KEY_EMIDDLENAME = "middlename";
    private static final String KEY_ELASTNAME = "lastname";
    private static final String KEY_EPHONE = "phone";
    private static final String KEY_EEMAIL = "email";
    private static final String KEY_EAADHARID = "aadharid";
    private static final String KEY_EEID = "employeeid";
    private static final String KEY_EISMANAGER = "ismanager";
    private static final String KEY_ETEAMNAME = "teamname";
    private static final String KEY_EDESIGNATION = "designation";
    private static final String KEY_EMANAGERID = "managerid";

    // Taskd Table Columns names
    private static final String KEY_DID = "id";
    private static final String KEY_DTITLE = "title";
    private static final String KEY_DSUBJECT = "subject";
    private static final String KEY_DEMPID = "empId";
    private static  final String KEY_DHOURS = "hours";
    private static  final String KEY_DMINS = "minutes";
    private static  final String KEY_DSEQID = "seqId";

    // TaskdInst Table Columns names
    private static final String KEY_TDIID = "id";
    private static final String KEY_TDITITLE = "title";
    private static final String KEY_TDISUBJECT = "subject";
    private static final String KEY_TDIEMPID = "empId";
    private static  final String KEY_TDIHOURS = "hours";
    private static  final String KEY_TDIMINS = "minutes";
    private static  final String KEY_TDISEQID = "seqId";
    private static  final String KEY_TDIACCEPTEDAT = "acceptedAt";
    private static  final String KEY_TDICOMPLETEDAT = "completedAt";
    private static  final String KEY_TDIDATE = "date";

    // Taskw Table Columns names
    private static final String KEY_WID = "id";
    private static final String KEY_WTITLE = "title";
    private static final String KEY_WSUBJECT = "subject";
    private static final String KEY_WEMPID = "empId";
    private static  final String KEY_WHOURS = "hours";
    private static  final String KEY_WMINS = "minutes";
    private static  final String KEY_WDAYOFWEEK = "dayOfWeek";
    private static  final String KEY_WSEQID = "seqId";

    // Taskw Table Columns names
    private static final String KEY_WIID = "id";
    private static final String KEY_WITITLE = "title";
    private static final String KEY_WISUBJECT = "subject";
    private static final String KEY_WIEMPID = "empId";
    private static  final String KEY_WIHOURS = "hours";
    private static  final String KEY_WIMINS = "minutes";
    private static  final String KEY_WIDAYOFWEEK = "dayOfWeek";
    private static  final String KEY_WIDATE = "date";
    private static  final String KEY_WIACCEPTEDAT = "acceptedAt";

    // Tasko Table Columns names
    private static final String KEY_OID = "id";
    private static final String KEY_OTITLE = "title";
    private static final String KEY_OSUBJECT = "subject";
    private static final String KEY_OEMPID = "empId";
    private static  final String KEY_OHOURS = "hours";
    private static  final String KEY_OMINS = "minutes";
    private static  final String KEY_ODATETOSET = "dateToSet";
    private static  final String KEY_OACCEPTEDAT = "acceptedAt";
    private static  final String KEY_OSEQID = "seqId";

    // StStatus Table Columns names
    private static final String KEY_STRID = "id";
    private static final String KEY_STRDOFW = "dayOfW";
    private static final String KEY_STRDATE = "date";
    private static final String KEY_STRSTATUS = "storeStatus";
    private static  final String KEY_STREVENTS = "events";
    private static  final String KEY_STREVENTSTIT = "eventTitle";
    private static  final String KEY_STREVENTSUB = "eventSubject";
    private static  final String KEY_STRSEQID = "seqId";
    private static  final String KEY_STRPUSHED = "pushed";

    // empStatus Table Columns names
    private static final String KEY_EMRID = "id";
    private static final String KEY_EMRDOFW = "dayOfW";
    private static final String KEY_EMRDATE = "date";
    private static final String KEY_EMREMPID = "empId";
    private static final String KEY_EMRSTATUS = "empStatus";
    private static final String KEY_EMRSHIFT = "shift";
    private static  final String KEY_EMRPUSHED = "pushed";
    private static final String KEY_EMRCHECKIN = "checkIn";
    private static final String KEY_EMRCHECKOUT = "checkOut";

    String CREATE_NOTIFICATION_TABLE = "CREATE TABLE " + TABLE_NOTIFICATION + "("
            + KEY_ID + " TEXT," + KEY_STUDENT + " TEXT,"
            + KEY_TITLE + " TEXT," + KEY_SUBJECT + " TEXT," + KEY_NEID + " TEXT," + KEY_DATE + " TEXT," + KEY_NSEQID + " TEXT" + ")";
    String CREATE_ATTENDANCE_TABLE = "CREATE TABLE " + TABLE_ATTENDANCE + "("
            + KEY_VALUE + " TEXT," + KEY_DATEINST  + " TEXT," + KEY_AEMPID + " TEXT" + ")";
    String CREATE_APPLICATIONS_TABLE = "CREATE TABLE " + TABLE_APPLICATIONS + "("
            + KEY_AID + " TEXT," + KEY_ACCEPTANCE + " TEXT,"
            + KEY_ATITLE + " TEXT," + KEY_ASUBJECT + " TEXT," + KEY_AEID + " TEXT," + KEY_ADATE + " TEXT," + KEY_ASEQID + " TEXT" + ")";
    String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + KEY_UFIRSTNAME + " TEXT," + KEY_UMIDDLENAME + " TEXT,"
            + KEY_ULASTNAME + " TEXT," + KEY_UPHONE + " TEXT," + KEY_UEMAIL + " TEXT," + KEY_UAADHARID + " TEXT," + KEY_UEID + " TEXT," + KEY_UTEAMNAME + " TEXT," + KEY_UDESIGNATION + " TEXT," + KEY_USTOREID + " TEXT" + ")";
    String CREATE_STORE_TABLE = "CREATE TABLE " + TABLE_STORE + "("
            + KEY_SID + " TEXT," + KEY_SNAME + " TEXT," + KEY_SSTOREID + " TEXT," + KEY_SEMPCOUNT + " TEXT," + KEY_SCITY + " TEXT," + KEY_SSTATE + " TEXT," + KEY_SLAT + " TEXT," + KEY_SLONG + " TEXT," + KEY_SADDRESS + " TEXT," + KEY_SKEYSTATE + " TEXT," + KEY_SSELLERID + " TEXT," + KEY_SCLOSINGDAY + " TEXT," + KEY_SROSTERGENDAY + " TEXT " + ")";
    String CREATE_EMPLOYEE_TABLE = "CREATE TABLE " + TABLE_EMPLOYEE + "("
            + KEY_EFIRSTNAME + " TEXT," + KEY_EMIDDLENAME + " TEXT,"
            + KEY_ELASTNAME + " TEXT," + KEY_EPHONE + " TEXT," + KEY_EEMAIL + " TEXT," + KEY_EAADHARID + " TEXT," + KEY_EEID + " TEXT," + KEY_EISMANAGER + " TEXT," + KEY_ETEAMNAME + " TEXT," + KEY_EDESIGNATION + " TEXT," + KEY_EMANAGERID + " TEXT" + ")";
    String CREATE_TASKD_TABLE = "CREATE TABLE " + TABLE_TASKD + "("
            + KEY_DID + " TEXT,"
            + KEY_DTITLE + " TEXT," + KEY_DSUBJECT + " TEXT," + KEY_DEMPID + " TEXT," + KEY_DHOURS + " TEXT," + KEY_DMINS + " TEXT," + KEY_DSEQID + " TEXT" + ")";
    String CREATE_TASKDINST_TABLE = "CREATE TABLE " + TABLE_TASKDINST + "("
            + KEY_TDIID + " TEXT,"
            + KEY_TDITITLE + " TEXT," + KEY_TDISUBJECT + " TEXT," + KEY_TDIEMPID + " TEXT," + KEY_TDIHOURS + " TEXT," + KEY_TDIMINS + " TEXT," + KEY_TDIACCEPTEDAT + " TEXT," + KEY_TDICOMPLETEDAT + " TEXT," + KEY_TDIDATE + " TEXT," + KEY_TDISEQID + " TEXT" + ")";
    String CREATE_TASKW_TABLE = "CREATE TABLE " + TABLE_TASKW + "("
            + KEY_WID + " TEXT,"
            + KEY_WTITLE + " TEXT," + KEY_WSUBJECT + " TEXT," + KEY_WEMPID + " TEXT," + KEY_WHOURS + " TEXT," + KEY_WMINS + " TEXT," + KEY_WDAYOFWEEK + " TEXT," + KEY_WSEQID + " TEXT" + ")";
    String CREATE_TASKWINST_TABLE = "CREATE TABLE " + TABLE_TASKWINST + "("
            + KEY_WIID + " TEXT,"
            + KEY_WITITLE + " TEXT," + KEY_WISUBJECT + " TEXT," + KEY_WIEMPID + " TEXT," + KEY_WIHOURS + " TEXT," + KEY_WIMINS + " TEXT," + KEY_WIDAYOFWEEK + " TEXT," + KEY_WIDATE + " TEXT," + KEY_WIACCEPTEDAT + " TEXT" + ")";
    String CREATE_TASKO_TABLE = "CREATE TABLE " + TABLE_TASKO + "("
            + KEY_OID + " TEXT,"
            + KEY_OTITLE + " TEXT," + KEY_OSUBJECT + " TEXT," + KEY_OEMPID + " TEXT," + KEY_OHOURS + " TEXT," + KEY_OMINS + " TEXT," + KEY_ODATETOSET + " TEXT," + KEY_OACCEPTEDAT + " TEXT," + KEY_OSEQID + " TEXT" + ")";
    String CREATE_STROSTER_TABLE = "CREATE TABLE " + TABLE_STROSTER + "("
            + KEY_STRID + " TEXT,"
            + KEY_STRDATE + " TEXT," + KEY_STRDOFW + " TEXT," + KEY_STRSTATUS + " TEXT," + KEY_STREVENTS + " TEXT," + KEY_STREVENTSTIT + " TEXT," + KEY_STREVENTSUB + " TEXT," + KEY_STRSEQID + " TEXT," + KEY_STRPUSHED + " TEXT" + ")";
    String CREATE_EMPROSTER_TABLE = "CREATE TABLE " + TABLE_EMPROSTER + "("
            + KEY_EMRID + " TEXT,"
            + KEY_EMRDATE + " TEXT," + KEY_EMREMPID + " TEXT," + KEY_EMRDOFW + " TEXT," + KEY_EMRSTATUS + " TEXT," + KEY_EMRSHIFT + " TEXT," + KEY_EMRPUSHED + " TEXT," + KEY_EMRCHECKIN + " TEXT," + KEY_EMRCHECKOUT + " TEXT " + ")";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        getWritableDatabase();
    }

    public DatabaseHandler(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
       try {
           db.execSQL(CREATE_NOTIFICATION_TABLE);
           Log.d("myTag", "created notif table");
           db.execSQL(CREATE_ATTENDANCE_TABLE);
           Log.d("myTag", "created attendance table");
           Log.d("myTag", "created resource table");
           db.execSQL(CREATE_APPLICATIONS_TABLE);
           db.execSQL(CREATE_USER_TABLE);
           db.execSQL(CREATE_STORE_TABLE);
           db.execSQL(CREATE_EMPLOYEE_TABLE);
           db.execSQL(CREATE_TASKD_TABLE);
           db.execSQL(CREATE_TASKDINST_TABLE);
           db.execSQL(CREATE_TASKW_TABLE);
           db.execSQL(CREATE_TASKWINST_TABLE);
           db.execSQL(CREATE_TASKO_TABLE);
           db.execSQL(CREATE_STROSTER_TABLE);
           db.execSQL(CREATE_EMPROSTER_TABLE);
       }catch(SQLException se){Log.d("myTag", "error in create", se);}
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDANCE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPLICATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKDINST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKW);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKWINST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STROSTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPROSTER);
        // Create tables again
        onCreate(db);
    }

    // Upgrading database
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new notifications
    void addNotif(Notif notif) {
        try{
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, notif.getId()); // Contact Name
        values.put(KEY_TITLE, notif.getTitle()); // Contact Name
        values.put(KEY_SUBJECT, notif.getSubject()); // Contact Phone
        values.put(KEY_DATE, notif.getDate()); // Contact Name
        values.put(KEY_NEID, notif.getEmployeeId());
        values.put(KEY_NSEQID, notif.getSeqId());

        // Inserting Row

        long rowinsert =  db.insertOrThrow(TABLE_NOTIFICATION, null, values);
        Log.d("myTag" ,"rowinsert"+ rowinsert);
        db.close(); // Closing database connection
    }catch(Exception e){ Log.d("myTag", "err" + e.toString() + "\n" +  e.getMessage() + "\n" + e.getCause() + "\n" + e.getStackTrace() );
            Log.d("myTag", "err lol ", e);
    e.printStackTrace();
    }
    }

    // Adding new notifications
    void addAttendance(Attendance attendance) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_VALUE, attendance.getValue()); // Contact Phone
            values.put(KEY_DATEINST, attendance.getDate()); // Contact Name
            values.put(KEY_AEMPID , attendance.getEmployeeId());
            // Inserting Row

            long rowinsert =  db.insertOrThrow(TABLE_ATTENDANCE, null, values);
            Log.d("myTag" ,"rowinsert"+ rowinsert);
            db.close(); // Closing database connection
        }catch(Exception e){ Log.d("myTag", "err" + e.toString() + "\n" +  e.getMessage() + "\n" + e.getCause() + "\n" + e.getStackTrace() );
            Log.d("myTag", "err lol ", e);
            e.printStackTrace();
        }
    }

    // Adding new applications
    void addApplications(Application application) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();
Log.d("myTag", "adding appli db : " + application.getSubject() + " : " + application.getDate() + " : " + application.getTitle());
            ContentValues values = new ContentValues();
            values.put(KEY_AID, application.getId()); // Contact Name
            values.put(KEY_ACCEPTANCE, application.getAcceptStatus()); // Contact Phone
            values.put(KEY_ATITLE, application.getTitle()); // Contact Name
            values.put(KEY_ASUBJECT, application.getSubject()); // Contact Phone
            values.put(KEY_ADATE, application.getDate()); // Contact Name
            values.put(KEY_AEID, application.getEmployeeId());

            // Inserting Row

            long rowinsert =  db.insertOrThrow(TABLE_APPLICATIONS, null, values);
            Log.d("myTag" ,"rowinsert"+ rowinsert);
            db.close(); // Closing database connection
        }catch(Exception e){ Log.d("myTag", "err" + e.toString() + "\n" +  e.getMessage() + "\n" + e.getCause() + "\n" + e.getStackTrace() );
            Log.d("myTag", "err lol ", e);
            e.printStackTrace();
        }
    }

    // Adding new user
    void addUsers(User user) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            Log.d("myTag", "adding user db : " + user.getFirstName() + " : " + user.getMiddleName() + " : " + user.getLastName() + user.getPhone() + " : " + user.getEmail() + " : " + user.getAadharId());
            ContentValues values = new ContentValues();
            values.put(KEY_UFIRSTNAME, user.getFirstName()); // Contact Name
            values.put(KEY_UMIDDLENAME, user.getMiddleName()); // Contact Phone
            values.put(KEY_ULASTNAME, user.getLastName()); // Contact Name
            values.put(KEY_UPHONE, user.getPhone()); // Contact Phone
            values.put(KEY_UEMAIL, user.getEmail()); // Contact Name
            values.put(KEY_UAADHARID, user.getAadharId());
            values.put(KEY_UEID, user.getEmployeeId());
            values.put(KEY_USTOREID, user.getStoreId());
            values.put(KEY_UDESIGNATION, "");
            values.put(KEY_UTEAMNAME, "");
            // Inserting Row

            long rowinsert =  db.insertOrThrow(TABLE_USER, null, values);
            Log.d("myTag" ,"rowinsert"+ rowinsert);
            db.close(); // Closing database connection
        }catch(Exception e){ Log.d("myTag", "err" + e.toString() + "\n" +  e.getMessage() + "\n" + e.getCause() + "\n" + e.getStackTrace() );
            Log.d("myTag", "err lol ", e);
            e.printStackTrace();
        }
    }

    void addEmployees(Employee employee) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            Log.d("myTag", "adding user db : " + employee.getFirstName() + " : " + employee.getMiddleName() + " : " + employee.getLastName() + employee.getPhone() + " : " + employee.getEmail() + " : " + employee.getAadharId());
            ContentValues values = new ContentValues();
            values.put(KEY_EFIRSTNAME, employee.getFirstName()); // Contact Name
            values.put(KEY_EMIDDLENAME, employee.getMiddleName()); // Contact Phone
            values.put(KEY_ELASTNAME, employee.getLastName()); // Contact Name
            values.put(KEY_EPHONE, employee.getPhone()); // Contact Phone
            values.put(KEY_EEMAIL, employee.getEmail()); // Contact Name
            values.put(KEY_EAADHARID, employee.getAadharId());
            values.put(KEY_EEID, employee.getEmployeeId());
            values.put(KEY_EISMANAGER, employee.getIsManager()); // Contact Name
            values.put(KEY_ETEAMNAME, employee.getTeamName());
            values.put(KEY_EDESIGNATION, employee.getDesignation());
            values.put(KEY_EMANAGERID, employee.getManagerId());
            // Inserting Row

            long rowinsert =  db.insertOrThrow(TABLE_EMPLOYEE, null, values);
            Log.d("myTag" ,"rowinsert"+ rowinsert);
            db.close(); // Closing database connection
        }catch(Exception e){ Log.d("myTag", "err" + e.toString() + "\n" +  e.getMessage() + "\n" + e.getCause() + "\n" + e.getStackTrace() );
            Log.d("myTag", "err lol ", e);
            e.printStackTrace();
        }
    }

    // Adding new user
    void addStores(Store store) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            //Log.d("myTag", "adding user db : " + school.getName() + " : " + school.getSchoolId() + " : " + school.getCity());
            ContentValues values = new ContentValues();
            values.put(KEY_SID, store.getId()); // Contact Name
            values.put(KEY_SNAME, store.getName()); // Contact Name
            values.put(KEY_SSTOREID, store.getStoreId()); // Contact Phone
            values.put(KEY_SCITY, store.getCity()); // Contact Name
            values.put(KEY_SSTATE, store.getState()); // Contact Name
            values.put(KEY_SLAT, store.getLat()); // Contact Name
            values.put(KEY_SLONG , store.getLongi()); // Contact Phone
            values.put(KEY_SADDRESS , store.getAddress()); // Contact Name
            values.put(KEY_SEMPCOUNT , store.getEmpCount()); // Contact Name
            values.put(KEY_SKEYSTATE , store.getKeyState());
            values.put(KEY_SCLOSINGDAY , store.getClosingDay());
            values.put(KEY_SROSTERGENDAY , store.getRosterGenDay());
            // Inserting Row

            long rowinsert =  db.insertOrThrow(TABLE_STORE, null, values);
            Log.d("myTag" ,"rowinsert"+ rowinsert);
            db.close(); // Closing database connection
        }catch(Exception e){ Log.d("myTag", "err" + e.toString() + "\n" +  e.getMessage() + "\n" + e.getCause() + "\n" + e.getStackTrace() );
            Log.d("myTag", "err lol ", e);
            e.printStackTrace();
        }
    }

    public void updateStoreClosing(String id, String closingDay){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("closingDay", closingDay);
        String where = KEY_SSTOREID + " = ? ";
        String[] whereArgs = {id};
        db.update(TABLE_STORE, cv, where, whereArgs);
    }

    public void updateTeam(String id, String designation, String teamName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("designation", designation);
        cv.put("teamName", teamName);
        String where = KEY_UEID + " = ? ";
        String[] whereArgs = {id};
        db.update(TABLE_USER, cv, where, whereArgs);
    }

    // Adding new user
    void generateSTRoster(String date, StRoster str) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            //Log.d("myTag", "adding user db : " + school.getName() + " : " + school.getSchoolId() + " : " + school.getCity());
            ContentValues values = new ContentValues();
            values.put(KEY_STRID, ""); // Contact Name
            values.put(KEY_STRDOFW, str.getDayOfw()); // Contact Phone
            values.put(KEY_STRDATE, date); // Contact Name
            values.put(KEY_STRSTATUS, str.getStoreStatus()); // Contact Name
            values.put(KEY_STREVENTS , str.getEvents()); // Contact Phone
            values.put(KEY_STREVENTSTIT , str.getEventTitle()); // Contact Name
            values.put(KEY_STREVENTSUB , str.getEventSub()); // Contact Name
            values.put(KEY_STRSEQID , "");
            values.put(KEY_STRPUSHED , str.getPushed()); // Contact Name
            // Inserting Row

            long rowinsert =  db.insertOrThrow(TABLE_STROSTER, null, values);
            Log.d("myTag" ,"rowinsert"+ rowinsert);
            db.close(); // Closing database connection
        }catch(Exception e){ Log.d("myTag", "err" + e.toString() + "\n" +  e.getMessage() + "\n" + e.getCause() + "\n" + e.getStackTrace() );
            Log.d("myTag", "err lol ", e);
            e.printStackTrace();
        }
    }

    // Adding new user
    void generateEmpRoster(String date, EmpRoster str) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            //Log.d("myTag", "adding user db : " + school.getName() + " : " + school.getSchoolId() + " : " + school.getCity());
            ContentValues values = new ContentValues();
            values.put(KEY_EMRID, ""); // Contact Name
            values.put(KEY_EMRDOFW, str.getDayOfw()); // Contact Phone
            values.put(KEY_EMREMPID, str.getEmpId()); // Contact Phone
            values.put(KEY_EMRDATE, date); // Contact Name
            values.put(KEY_EMRSTATUS, str.getEmpStatus()); // Contact Name
            values.put(KEY_EMRSHIFT , str.getShift()); // Contact Phone
            values.put(KEY_EMRPUSHED , str.getPushed()); // Contact Phone
            // Inserting Row

            long rowinsert =  db.insertOrThrow(TABLE_EMPROSTER, null, values);
            Log.d("myTag" ,"rowinsert"+ rowinsert);
            db.close(); // Closing database connection
        }catch(Exception e){ Log.d("myTag", "err" + e.toString() + "\n" +  e.getMessage() + "\n" + e.getCause() + "\n" + e.getStackTrace() );
            Log.d("myTag", "err lol ", e);
            e.printStackTrace();
        }
    }

    // Getting contacts Count
    public int getStRosterCount() {
        String countQuery = "SELECT  * FROM " + TABLE_STROSTER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int counter = cursor.getCount();

        cursor.close();
        Log.d("myTag", "count resource : " + cursor.getCount() + " : " + cursor.getColumnCount());
        // return count
        return counter;
    }

    // Getting contacts Count
    public int getEmpRosterCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EMPROSTER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int counter = cursor.getCount();

        cursor.close();
        Log.d("myTag", "count resource : " + cursor.getCount() + " : " + cursor.getColumnCount());
        // return count
        return counter;
    }

    // Adding new notifications
    void addTaskd(Taskd tsk) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_DID, ""); // Contact Name
            values.put(KEY_DEMPID, tsk.getEmpId()); // Contact Phone
            values.put(KEY_DTITLE, tsk.getTitle()); // Contact Name
            values.put(KEY_DSUBJECT, tsk.getSubject()); // Contact Phone
            values.put(KEY_DHOURS, tsk.getHours()); // Contact Name
            values.put(KEY_DMINS, tsk.getMinutes());
            values.put(KEY_DSEQID, "");

            // Inserting Row

            long rowinsert =  db.insertOrThrow(TABLE_TASKD, null, values);
            Log.d("myTag" ,"rowinsert"+ rowinsert);
            db.close(); // Closing database connection
        }catch(Exception e){ Log.d("myTag", "err" + e.toString() + "\n" +  e.getMessage() + "\n" + e.getCause() + "\n" + e.getStackTrace() );
            Log.d("myTag", "err lol ", e);
            e.printStackTrace();
        }
    }

    // Adding new notifications
    void addTaskdInst(TaskdInst tsk) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_TDIID, tsk.get_id()); // Contact Name
            values.put(KEY_TDIEMPID, tsk.getEmpId()); // Contact Phone
            values.put(KEY_TDITITLE, tsk.getTitle()); // Contact Name
            values.put(KEY_TDISUBJECT, tsk.getSubject()); // Contact Phone
            values.put(KEY_TDIHOURS, tsk.getHours()); // Contact Name
            values.put(KEY_TDIMINS, tsk.getMinutes());
            values.put(KEY_TDIACCEPTEDAT, tsk.getAcceptedAt()); // Contact Name
            values.put(KEY_TDIMINS, tsk.getCompletedAt());
            values.put(KEY_TDIDATE, tsk.getDate());
            values.put(KEY_TDISEQID, "");

            // Inserting Row

            long rowinsert =  db.insertOrThrow(TABLE_TASKDINST, null, values);
            Log.d("myTag" ,"rowinsert"+ rowinsert);
            db.close(); // Closing database connection
        }catch(Exception e){ Log.d("myTag", "err" + e.toString() + "\n" +  e.getMessage() + "\n" + e.getCause() + "\n" + e.getStackTrace() );
            Log.d("myTag", "err lol ", e);
            e.printStackTrace();
        }
    }

    public ArrayList<TaskInst> getTaskdByDate(String date){
        SQLiteDatabase db = this.getReadableDatabase();
        TaskdInst tsk = null;
        ArrayList<TaskInst> tList = new ArrayList<>();
        String where = KEY_TDIDATE + "= ?";
        String[] whereArgs = {date};
        Cursor cursor = db.query(TABLE_TASKDINST,new String[]{KEY_TDITITLE, KEY_TDISUBJECT, KEY_TDIEMPID, KEY_TDIHOURS, KEY_TDIMINS,KEY_TDIDATE,KEY_TDIACCEPTEDAT,KEY_TDIID} ,where, whereArgs, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                tsk = new TaskdInst(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),"",cursor.getString(7));
                TaskInst tmp = new TaskInst(tsk);
                tList.add(tmp);
            } while (cursor.moveToNext());
        }
        db.close();
        if(tList.size() != 0) {
            return tList;
        }
        return null;
    }

    public TaskdInst getTaskdInstById(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        TaskdInst tsk = null;
        String where = KEY_TDIID + "= ?";
        String[] whereArgs = {id};
        Cursor cursor = db.query(TABLE_TASKDINST,new String[]{KEY_TDITITLE, KEY_TDISUBJECT, KEY_TDIEMPID, KEY_TDIHOURS, KEY_TDIMINS,KEY_TDIDATE,KEY_TDIACCEPTEDAT,"",KEY_TDIID} ,where, whereArgs, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                tsk = new TaskdInst(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),"",cursor.getString(7));
            } while (cursor.moveToNext());
        }
        db.close();
        if(tsk != null) {
            return tsk;
        }
        return null;
    }

    public void updateTaskdInstState(String id, String acceptedAt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("acceptedAt", acceptedAt);
        String where = KEY_TDIID + " = ? ";
        String[] whereArgs = {id};
        db.update(TABLE_TASKDINST, cv, where, whereArgs);
    }



    // Adding new notifications
    void addTaskw(Taskw tsk) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_WID, ""); // Contact Name
            values.put(KEY_WEMPID, tsk.getEmpId()); // Contact Phone
            values.put(KEY_WTITLE, tsk.getTitle()); // Contact Name
            values.put(KEY_WSUBJECT, tsk.getSubject()); // Contact Phone
            values.put(KEY_WHOURS, tsk.getHours()); // Contact Name
            values.put(KEY_WMINS, tsk.getMinutes());
            values.put(KEY_WDAYOFWEEK, tsk.getDayOfWeek());
            values.put(KEY_WSEQID, "");

            // Inserting Row
            long rowinsert =  db.insertOrThrow(TABLE_TASKW, null, values);
            Log.d("myTag" ,"rowinsert"+ rowinsert);
            db.close(); // Closing database connection
        }catch(Exception e){ Log.d("myTag", "err" + e.toString() + "\n" +  e.getMessage() + "\n" + e.getCause() + "\n" + e.getStackTrace() );
            Log.d("myTag", "err lol ", e);
            e.printStackTrace();
        }
    }

    // Adding new notifications
    void addTaskwInst(TaskwInst tsk) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_WIID, tsk.get_id()); // Contact Name
            values.put(KEY_WIEMPID, tsk.getEmpId()); // Contact Phone
            values.put(KEY_WITITLE, tsk.getTitle()); // Contact Name
            values.put(KEY_WISUBJECT, tsk.getSubject()); // Contact Phone
            values.put(KEY_WIHOURS, tsk.getHours()); // Contact Name
            values.put(KEY_WIMINS, tsk.getMinutes());
            values.put(KEY_WIDAYOFWEEK, tsk.getDayOfWeek());
            values.put(KEY_WIDATE, tsk.getDate());
            values.put(KEY_WIACCEPTEDAT, tsk.getAcceptedAt());

            // Inserting Row
            long rowinsert =  db.insertOrThrow(TABLE_TASKWINST, null, values);
            Log.d("myTag" ,"rowinsert"+ rowinsert);
            db.close(); // Closing database connection
        }catch(Exception e){ Log.d("myTag", "err" + e.toString() + "\n" +  e.getMessage() + "\n" + e.getCause() + "\n" + e.getStackTrace() );
            Log.d("myTag", "err lol ", e);
            e.printStackTrace();
        }
    }

    public ArrayList<TaskInst> getTaskwByDate(String date){
        SQLiteDatabase db = this.getReadableDatabase();
        TaskwInst tsk = null;
        ArrayList<TaskInst> tList = new ArrayList<>();
        String where = KEY_WIDATE + "= ?";
        String[] whereArgs = {date};
        Cursor cursor = db.query(TABLE_TASKWINST,new String[]{ KEY_WITITLE, KEY_WISUBJECT, KEY_WIEMPID, KEY_WIHOURS, KEY_WIMINS,KEY_WIDATE,KEY_WIACCEPTEDAT,KEY_WIID} ,where, whereArgs, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                tsk = new TaskwInst(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),"",cursor.getString(7));
                TaskInst tmp = new TaskInst(tsk);
                tList.add(tmp);
            } while (cursor.moveToNext());
        }
        db.close();
        if(tList.size() != 0) {
            return tList;
        }
        return null;
    }

    public TaskwInst getTaskwInstById(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        TaskwInst tsk = null;
        String where = KEY_WIID + "= ?";
        String[] whereArgs = {id};
        Cursor cursor = db.query(TABLE_TASKWINST,new String[]{ KEY_WITITLE, KEY_WISUBJECT, KEY_WIEMPID, KEY_WIHOURS, KEY_WIMINS,KEY_WIDATE,KEY_WIACCEPTEDAT,KEY_WIID} ,where, whereArgs, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                tsk = new TaskwInst(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),"",cursor.getString(7));
            } while (cursor.moveToNext());
        }
        db.close();
        if(tsk != null) {
            return tsk;
        }
        return null;
    }

    public ArrayList<Notif> getAllNotifb(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Notif> notifL = new ArrayList<>();
        Notif notif = null;
        String where = KEY_NEID + "= ?";
        String[] whereArgs = {"-"};
        Cursor cursor = db.query(TABLE_NOTIFICATION,new String[]{ KEY_ID, KEY_TITLE, KEY_SUBJECT, KEY_DATE, KEY_NEID,KEY_NSEQID} ,where, whereArgs, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                notif = new Notif(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),Integer.parseInt(cursor.getString(5)));
                notifL.add(notif);
            } while (cursor.moveToNext());
        }
        db.close();
        if(notifL.size() != 0) {
            return notifL;
        }
        return null;
    }

    public ArrayList<Notif> getAllNotife(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Notif> notifL = new ArrayList<>();
        Notif notif = null;
        String where = KEY_NEID + "!= ?";
        String[] whereArgs = {"-"};
        Cursor cursor = db.query(TABLE_NOTIFICATION,new String[]{ KEY_ID, KEY_TITLE, KEY_SUBJECT, KEY_DATE, KEY_NEID,KEY_NSEQID} ,where, whereArgs, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                notif = new Notif(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),Integer.parseInt(cursor.getString(5)));
                notifL.add(notif);
            } while (cursor.moveToNext());
        }
        db.close();
        if(notifL.size() != 0) {
            return notifL;
        }
        return null;
    }


    public void updateTaskwInstState(String id, String acceptedAt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("acceptedAt", acceptedAt);
        String where = KEY_WIID + " = ? ";
        String[] whereArgs = {id};
        db.update(TABLE_TASKWINST, cv, where, whereArgs);
    }


    // Adding new notifications
    void addTasko(Tasko tsk) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_OID, ""); // Contact Name
            values.put(KEY_OEMPID, tsk.getEmpId()); // Contact Phone
            values.put(KEY_OTITLE, tsk.getTitle()); // Contact Name
            values.put(KEY_OSUBJECT, tsk.getSubject()); // Contact Phone
            values.put(KEY_OHOURS, tsk.getHours()); // Contact Name
            values.put(KEY_OMINS, tsk.getMinutes());
            values.put(KEY_ODATETOSET, tsk.getDateToSet());
            values.put(KEY_OACCEPTEDAT, tsk.getAcceptedAt());
            values.put(KEY_OSEQID, "");

            // Inserting Row

            long rowinsert =  db.insertOrThrow(TABLE_TASKO, null, values);
            Log.d("myTag" ,"rowinsert"+ rowinsert);
            db.close(); // Closing database connection
        }catch(Exception e){ Log.d("myTag", "err" + e.toString() + "\n" +  e.getMessage() + "\n" + e.getCause() + "\n" + e.getStackTrace() );
            Log.d("myTag", "err lol ", e);
            e.printStackTrace();
        }
    }

    public void updateTasko(Tasko tsk){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", tsk.getId());
        String where = KEY_ODATETOSET + " = ? AND " + KEY_OHOURS + " = ? AND " + KEY_OMINS + " = ?";
        String[] whereArgs = {tsk.getDateToSet(), tsk.getHours(), tsk.getMinutes()};
        db.update(TABLE_TASKO, cv, where, whereArgs);
    }

    public void updateTaskoInstState(String id, String acceptedAt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("acceptedAt", acceptedAt);
        String where = KEY_OID + " = ? ";
        String[] whereArgs = {id};
        db.update(TABLE_TASKO, cv, where, whereArgs);
    }


    public ArrayList<TaskInst> getTaskoByDate(String date){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<TaskInst> tList = new ArrayList<>();
        Tasko tsk = null;
        String where = KEY_ODATETOSET + "= ?";
        String[] whereArgs = {date};
        Cursor cursor = db.query(TABLE_TASKO,new String[]{KEY_OID, KEY_OTITLE, KEY_OSUBJECT, KEY_OEMPID, KEY_OHOURS, KEY_OMINS,KEY_ODATETOSET} ,where, whereArgs, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                tsk = new Tasko(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
                TaskInst tmp = new TaskInst(tsk);
                tList.add(tmp);
            } while (cursor.moveToNext());
        }
        db.close();
        if(tList.size() != 0) {
            return tList;
        }
        return null;
    }

    public Tasko getTaskoById(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Tasko tsk = null;
        String where = KEY_OID + "= ?";
        String[] whereArgs = {id};
        Cursor cursor = db.query(TABLE_TASKO,new String[]{KEY_OID, KEY_OTITLE, KEY_OSUBJECT, KEY_OEMPID, KEY_OHOURS, KEY_OMINS,KEY_ODATETOSET} ,where, whereArgs, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                tsk = new Tasko(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
            } while (cursor.moveToNext());
        }
        db.close();
        if(tsk != null) {
            return tsk;
        }
        return null;
    }


    /*
    // Getting notifications Count later for multiple students
    public int getNotifCount(String student) {
        //String countQuery = "SELECT  * FROM  " + TABLE_NOTIFICATION + "WHERE ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTIFICATION, new String[] { KEY_ID, KEY_TITLE
                        }, KEY_STUDENT + "=?",
                new String[] { String.valueOf(student) }, null, null, null, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
*/

    // Getting contacts Count
    public int getNotifCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NOTIFICATION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int counter = cursor.getCount();

        cursor.close();
        Log.d("myTag", "count : " + cursor.getCount() + " : " + cursor.getColumnCount());
        // return count
        return counter;
    }

    public int getAttendanceCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ATTENDANCE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int counter = cursor.getCount();

        cursor.close();
        Log.d("myTag", "count attenadnce : " + cursor.getCount() + " : " + cursor.getColumnCount());
        // return count
        return counter;
    }

    // Getting contacts Count
    public int getApplicationCount() {
        String countQuery = "SELECT  * FROM " + TABLE_APPLICATIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int counter = cursor.getCount();

        cursor.close();
        Log.d("myTag", "count resource : " + cursor.getCount() + " : " + cursor.getColumnCount());
        // return count
        return counter;
    }

    // Getting contacts Count
    public int getEmployeeCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EMPLOYEE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int counter = cursor.getCount();

        cursor.close();
        Log.d("myTag", "count resource : " + cursor.getCount() + " : " + cursor.getColumnCount());
        // return count
        return counter;
    }

    // Getting contacts Count
    public int getTaskdCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TASKD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int counter = cursor.getCount();

        cursor.close();
        Log.d("myTag", "count : " + cursor.getCount() + " : " + cursor.getColumnCount());
        // return count
        return counter;
    }

    // Getting contacts Count
    public int getTaskwCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TASKW;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int counter = cursor.getCount();
        cursor.close();
        Log.d("myTag", "count : " + cursor.getCount() + " : " + cursor.getColumnCount());
        // return count
        return counter;
    }

    // Getting contacts Count
    public int getTaskoCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TASKO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int counter = cursor.getCount();

        cursor.close();
        Log.d("myTag", "count : " + cursor.getCount() + " : " + cursor.getColumnCount());
        // return count
        return counter;
    }

    public User getUser(){
        User userObj = null;
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User("", cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(9),cursor.getString(7), cursor.getString(8));
                userObj = user;
                Log.d("myTag",cursor.getString(0) + " : " + cursor.getString(1) + " : "  + cursor.getString(2) + " : " + cursor.getString(3) );
            } while (cursor.moveToNext());
        }
        return userObj;
    }

    public ArrayList<Employee> getAllEmployees(){
        ArrayList<Employee> empList = new ArrayList<Employee>();
        String selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Employee emp = new Employee("", cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10));
                empList.add(emp);
                Log.d("myTag",cursor.getString(0) + " : " + cursor.getString(1) + " : "  + cursor.getString(2) + " : " + cursor.getString(3) + " : " + cursor.getString(4) + " : " + cursor.getString(5) + " : "  + cursor.getString(6) + " : " + cursor.getString(7) + " " + cursor.getString(8) + " : " + cursor.getString(9) + " : "  + cursor.getString(10));
            } while (cursor.moveToNext());
        }
        return empList;
    }

    public void getAllStRoster(){
        ArrayList<Employee> empList = new ArrayList<Employee>();
        String selectQuery = "SELECT  * FROM " + TABLE_STROSTER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Log.d("myTag",cursor.getString(0) + " : " + cursor.getString(1) + " : "  + cursor.getString(2) + " : " + cursor.getString(3) + " : " +  cursor.getString(4) + " : " + cursor.getString(5) + " : "  + cursor.getString(6) + " : " +  cursor.getString(7) + " : " +  cursor.getString(8) );
            } while (cursor.moveToNext());
        }
    }

    public void getAllEmpRoster(){
        ArrayList<Employee> empList = new ArrayList<Employee>();
        String selectQuery = "SELECT  * FROM " + TABLE_EMPROSTER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Log.d("myTag",cursor.getString(0) + " : " + cursor.getString(1) + " : "  + cursor.getString(2) + " : " + cursor.getString(3) + " : " +  cursor.getString(4) + " : " + cursor.getString(5) + " : " + cursor.getString(6) + " : " + cursor.getString(7) + " : " + cursor.getString(8));
            } while (cursor.moveToNext());
        }
    }

    public  Store getStore(){
        Store storeObj = null;
        String selectQuery = "SELECT  * FROM " + TABLE_STORE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Store store = new Store(cursor.getString(0), cursor.getString(1),cursor.getString(2), cursor.getString(4), cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8), cursor.getString(3), cursor.getString(9),cursor.getString(10),cursor.getString(11), cursor.getString(12));
                storeObj = store;
                Log.d("myTag","getting store : " + cursor.getString(0) + " : " + cursor.getString(1) + " : "  + cursor.getString(2) + " : "+ cursor.getString(3) + " : " + cursor.getString(4) + " : "+ cursor.getString(5) + " : " + cursor.getString(6) + " : " + cursor.getString(7) + " : "  + cursor.getString(8) + " : "+ cursor.getString(9) + " : " + cursor.getString(10) + " : "+ cursor.getString(11) + " : " + cursor.getString(12));
            } while (cursor.moveToNext());
        }
        return storeObj;
    }

    // Getting All Contacts
    public ArrayList<Notif> getAllNotifications(int limit , int offset) {
        ArrayList<Notif> notifList = new ArrayList<Notif>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTIFICATION + " ORDER BY (" + KEY_NSEQID + ") DESC";
                //+ " LIMIT " + limit;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Notif notification = new Notif();
               // Log.d("myTag","db lol notif : " + cursor.getString(0) + " : " + cursor.getString(1) + " : "  + cursor.getString(2) + " : " + cursor.getString(3) + " : " + cursor.getString(4) + " : " + cursor.getString(5));
                notification.setTitle(cursor.getString(2));
                notification.setSubject(cursor.getString(3));
                notification.setDate(cursor.getString(5).substring(3,15));
                // Adding contact to list
                notifList.add(notification);
            } while (cursor.moveToNext());
        }

        // return contact list
        return notifList;
    }

    // Getting All Attendance
    public ArrayList<Attendance> getAllAttendance(int limit, int offset) {
        ArrayList<Attendance> attendanceList = new ArrayList<Attendance>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ATTENDANCE + " LIMIT "+ limit;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Attendance attendance = new Attendance();
                Log.d("myTag",cursor.getString(0) + " : " + cursor.getString(2));
                attendance.setDate(cursor.getString(2));
                attendance.setValue(cursor.getString(0));
                // Adding contact to list
                attendanceList.add(attendance);
            } while (cursor.moveToNext());
        }

        // return contact list
        return attendanceList;
    }

    // Getting All Attendance
    public ArrayList<Application> getAllApplications(int limit, int offset) {
        ArrayList<Application> applicationList = new ArrayList<Application>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_APPLICATIONS ;
                //+ " LIMIT " + limit;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Application application = new Application(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(5),cursor.getString(4),"");
                Log.d("myTag","getting appli : " + cursor.getString(0) + " : " + cursor.getString(1) + " : " + cursor.getString(2) + " : " + cursor.getString(3) + " : " + cursor.getString(4) + " : " + cursor.getString(5) + " : " + cursor.getString(6));
                applicationList.add(application);
            } while (cursor.moveToNext());
        }
        // return contact list
        return applicationList;
    }

    // Getting All Attendance
    public ArrayList<Taskd> getAllTaskd(int limit, int offset) {
        ArrayList<Taskd> taskdList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TASKD ;
        //+ " LIMIT " + limit;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Taskd tsk = new Taskd(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
                Log.d("myTag","getting taskd : " + cursor.getString(0) + " : " + cursor.getString(1) + " : " + cursor.getString(2) + " : " + cursor.getString(3) + " : " + cursor.getString(4));
                taskdList.add(tsk);
            } while (cursor.moveToNext());
        }
        // return contact list
        return taskdList;
    }

    // Getting All Attendance
    public ArrayList<Taskw> getAllTaskw(String day) {
        try {
            ArrayList<Taskw> taskwList = new ArrayList<>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_TASKW;
            //+ " LIMIT " + limit;
            Log.d("myTag", "getting day : " + day);
            SQLiteDatabase db = this.getReadableDatabase();
            String where = KEY_WDAYOFWEEK + "= ?";
            String[] whereArgs = {day};
            Cursor cursor = db.query(TABLE_TASKW, new String[]{KEY_WTITLE, KEY_WSUBJECT, KEY_WEMPID, KEY_WHOURS, KEY_WMINS, KEY_WDAYOFWEEK}, where, whereArgs, null, null, null, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Taskw tsk = new Taskw(cursor.getString(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                    Log.d("myTag", "getting appli : " + cursor.getString(0) + " : " + cursor.getString(1) + " : " + cursor.getString(2) + " : " + cursor.getString(3) + " : " + cursor.getString(4) + " : " + cursor.getString(5));
                    taskwList.add(tsk);
                } while (cursor.moveToNext());
            }
            // return contact list
            return taskwList;
        }catch(SQLException e ) {
            Log.d("myTag", "error : ", e);
        }
        return null;
    }

    // Getting All Attendance
    public ArrayList<Tasko> getAllTasko(String date) {
        ArrayList<Tasko> taskoList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TASKO ;
        //+ " LIMIT " + limit;
        SQLiteDatabase db = this.getReadableDatabase();
        String where = KEY_ODATETOSET + "= ?";
        String[] whereArgs = {date};
        Cursor cursor = db.query(TABLE_TASKO,new String[]{KEY_OID,KEY_OTITLE, KEY_OSUBJECT, KEY_OEMPID, KEY_OHOURS, KEY_OMINS, KEY_ODATETOSET} ,where, whereArgs, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Tasko tsk = new Tasko(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
                Log.d("myTag","getting appli : " + cursor.getString(0) + " : " + cursor.getString(1) + " : " + cursor.getString(2) + " : " + cursor.getString(3) + " : " + cursor.getString(4));
                taskoList.add(tsk);
            } while (cursor.moveToNext());
        }
        // return contact list
        return taskoList;
    }

    public void viewAllTasko() {
        ArrayList<Application> applicationList = new ArrayList<Application>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TASKO ;
        //+ " LIMIT " + limit;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Log.d("myTag","getting appli : " + cursor.getString(0) + " : " + cursor.getString(1) + " : " + cursor.getString(2) + " : " + cursor.getString(3) + " : " + cursor.getString(4) + " : " + cursor.getString(5) + " : " + cursor.getString(6) + " : " + cursor.getString(7) + " : " + cursor.getString(8));
            } while (cursor.moveToNext());
        }
        // return contact list
    }

    public void viewAllTaskw() {
        ArrayList<Application> applicationList = new ArrayList<Application>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TASKW ;
        //+ " LIMIT " + limit;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Log.d("myTag","getting appli : " + cursor.getString(0) + " : " + cursor.getString(1) + " : " + cursor.getString(2) + " : " + cursor.getString(3) + " : " + cursor.getString(4) + " : " + cursor.getString(5) + " : " + cursor.getString(6) + " : " + cursor.getString(7));
            } while (cursor.moveToNext());
        }
        // return contact list
    }


    public Application updateApplicationAcceptStatus(String title, String subject, String date, String studentid, String acceptStatus, String appid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("accepted", acceptStatus);
        //cv.put("id",appid);
        //String where = KEY_ATITLE + "= ?," + KEY_ASUBJECT +  "= ?," + KEY_ADATE + "= ?," + KEY_ASTUDENTID + "= ?";
        //String[] whereArgs = {title, subject, date, studentid};
        //db.update(TABLE_APPLICATIONS, cv, where, whereArgs);
        String where = KEY_AID + "= ?";
        String[] whereArgs = {appid};
        db.update(TABLE_APPLICATIONS, cv, where, whereArgs);
        db.close();
        Application application = new Application(appid,acceptStatus,title,subject,date,studentid);
        return application;
    }

    public Boolean updateActivationKeyStatus(String state, String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ketstate", state);
        String where = KEY_SSTOREID + "= ?";
        String[] whereArgs = {id};
        db.update(TABLE_STORE, cv, where, whereArgs);
        db.close();
        return true;
    }

    public Boolean removeEmp(String empId){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = KEY_EEID + "= ?";
        String[] whereArgs = {empId};
        db.delete(TABLE_EMPLOYEE, where, whereArgs);
        db.close();
        return true;
    }

    /*
    public void removeAllResources(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESOURCE,null,null);
        db.close();
    }
    */

    public void dropAllUserTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        //noinspection TryFinallyCanBeTryWithResources not available with API < 19
        try {
            List<String> tables = new ArrayList<>(cursor.getCount());

            while (cursor.moveToNext()) {
                tables.add(cursor.getString(0));
            }

            for (String table : tables) {
                if (table.startsWith("sqlite_")) {
                    continue;
                }
                db.execSQL("DROP TABLE IF EXISTS " + table);
                Log.d("myTag", "Dropped table " + table);
            }
        } finally {
            cursor.close();
        }
    }

    public Application getApplicationById(String applicationId){
        SQLiteDatabase db = this.getReadableDatabase();
        Application application = null;
        String where = KEY_AID + "= ?";
        String[] whereArgs = {applicationId};
        Cursor cursor = db.query(TABLE_APPLICATIONS,new String[]{KEY_AID, KEY_ACCEPTANCE, KEY_ATITLE, KEY_ASUBJECT, KEY_ADATE, KEY_AEID, KEY_ASEQID} ,where, whereArgs, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                application =  new Application(cursor.getString(0), cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4), cursor.getString(5), cursor.getString(6));
            } while (cursor.moveToNext());
        }
        db.close();
        if(application != null) {
            return application;
        }
        return null;
    }

    public Boolean getApprovedAppli(String date, String empId){
        SQLiteDatabase db = this.getReadableDatabase();
        Application application = null;
        String where = KEY_ADATE + "= ? AND " + KEY_AEID + "= ? AND " + KEY_ACCEPTANCE + "= ? ";
        String[] whereArgs = {date, empId, "true"};
        Cursor cursor = db.query(TABLE_APPLICATIONS,new String[]{KEY_AID, KEY_ACCEPTANCE, KEY_ATITLE, KEY_ASUBJECT, KEY_ADATE, KEY_AEID, KEY_ASEQID} ,where, whereArgs, null, null, null, null);
        if (cursor.getCount() != 0) {
            return true;
        }
        db.close();
        return false;
    }

    public Boolean containsAppli(String date, String empId){
        SQLiteDatabase db = this.getReadableDatabase();
        String where = KEY_ADATE + "= ? AND " + KEY_AEID + " =? ";
        Log.d("myTag", "searching for date : " + date);
        String[] whereArgs = {date, empId};
        Cursor cursor = db.query(TABLE_APPLICATIONS,new String[]{KEY_ADATE} ,where, whereArgs, null, null, null, null);
        Log.d("myTag", "result : " + cursor.getCount());
        if(cursor.getCount() == 0) return true;
        db.close();
        return false;
    }

    public Employee getEmployeeById(String employeeId){
        SQLiteDatabase db = this.getReadableDatabase();
        Employee emp = null;
        String where = KEY_EEID + "= ?";
        String[] whereArgs = {employeeId};
        Cursor cursor = db.query(TABLE_EMPLOYEE,new String[]{KEY_EFIRSTNAME,KEY_EMIDDLENAME,KEY_ELASTNAME,KEY_EPHONE, KEY_EEMAIL,KEY_EAADHARID, KEY_EEID} ,where, whereArgs, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Log.d("myTag", "getting employee : " + cursor.getString(0)+" : "+ cursor.getString(1)+" : "+ cursor.getString(2) +" : "+cursor.getString(3)+" : "+ cursor.getString(4)+" : "+ cursor.getString(5) +" : "+ cursor.getString(6));
                emp = new Employee("",cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10));
            } while (cursor.moveToNext());
        }
        db.close();
        if(emp != null) {
            return emp;
        }
        return null;
    }

    public StRoster getSTRosterByDate(String date){
        SQLiteDatabase db = this.getReadableDatabase();
        StRoster str = null;
        String where = KEY_STRDATE + "= ?";
        Log.d("myTag", "searching for date : " + date);
        String[] whereArgs = {date};
        Cursor cursor = db.query(TABLE_STROSTER,new String[]{KEY_STRID,KEY_STRDOFW,KEY_STRDATE,KEY_STRSTATUS, KEY_STREVENTS,KEY_STREVENTSTIT, KEY_STREVENTSUB, KEY_STRPUSHED} ,where, whereArgs, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Log.d("myTag", "setting store : " + cursor.getString(0)+" : "+ cursor.getString(1)+" : "+ cursor.getString(2) +" : "+cursor.getString(3)+" : "+ cursor.getString(4)+" : "+ cursor.getString(5) +" : "+ cursor.getString(6));
                str = new StRoster(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),cursor.getString(7));
            } while (cursor.moveToNext());
        }
        db.close();
        if(str != null) {
            Log.d("myTag", "returning : " + str.getInString());
            return str;

        }
        return null;
    }

    public ArrayList<StRoster> getPushableSTR(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<StRoster> srList =  new ArrayList<>();
        StRoster str = null;
        String where = KEY_STRPUSHED + "= ?";
        //Log.d("myTag", "searching for date : " + date);
        String[] whereArgs = {""};
        Cursor cursor = db.query(TABLE_STROSTER,new String[]{KEY_STRID,KEY_STRDOFW,KEY_STRDATE,KEY_STRSTATUS, KEY_STREVENTS,KEY_STREVENTSTIT, KEY_STREVENTSUB, KEY_STRPUSHED} ,where, whereArgs, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Log.d("myTag", "setting store : " + cursor.getString(0)+" : "+ cursor.getString(1)+" : "+ cursor.getString(2) +" : "+cursor.getString(3)+" : "+ cursor.getString(4)+" : "+ cursor.getString(5) +" : "+ cursor.getString(6));
                str = new StRoster(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
                srList.add(str);
            } while (cursor.moveToNext());
        }
        db.close();
        if(srList.size() != 0) {
            Log.d("myTag", "return ing : " + str.getInString());
            return srList;
        }
        return null;
    }


    public ArrayList<EmpRoster> getPushableER(){
        SQLiteDatabase db = this.getReadableDatabase();
        EmpRoster str = null;
        ArrayList<EmpRoster> rosterL = new ArrayList<>();
        String where = KEY_EMRPUSHED + "= ?";
        String[] whereArgs = {""};
        Cursor cursor = db.query(TABLE_EMPROSTER,new String[]{KEY_EMRID,KEY_EMRDOFW,KEY_EMRDATE, KEY_EMREMPID,KEY_EMRSTATUS, KEY_EMRSHIFT, KEY_EMRPUSHED} ,where, whereArgs, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                str = new EmpRoster(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getString(5),cursor.getString(6));
                rosterL.add(str);
            } while (cursor.moveToNext());
        }
        db.close();
        if(str != null) {
            return rosterL;
        }
        return null;
    }

    public EmpRoster getEmpRosterByDate(String date, String empId){
        SQLiteDatabase db = this.getReadableDatabase();
        EmpRoster str = null;
        String where = KEY_STRDATE + "= ? AND " + KEY_EMREMPID + "= ?";
        String[] whereArgs = {date, empId};
        Cursor cursor = db.query(TABLE_EMPROSTER,new String[]{KEY_EMRID,KEY_EMRDOFW,KEY_EMRDATE, KEY_EMREMPID,KEY_EMRSTATUS, KEY_EMRSHIFT, KEY_EMRPUSHED, KEY_EMRCHECKIN, KEY_EMRCHECKOUT} ,where, whereArgs, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Log.d("myTag", "returning : " + cursor.getString(0) + " : " +  cursor.getString(1) + " : " + cursor.getString(2)+ " : " + cursor.getString(3)+ " : " + cursor.getString(4)+ " : " +cursor.getString(5)+ " : " +cursor.getString(6)+ " : " +cursor.getString(7)+ " : " +cursor.getString(8));
                str = new EmpRoster(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8));
            } while (cursor.moveToNext());
        }
        db.close();
        if(str != null) {
            return str;
        }
        return null;
    }

    public String getMonthlyAttendance(String date,String month, String empId){
        SQLiteDatabase db = this.getReadableDatabase();
        Integer days = 0;
        Integer onDuty = 0;
        Integer present = 0;
        Integer plannedL = 0;
        Integer offD = 0;
        Integer weeklyO = 0;
        String where = KEY_STRDATE + "= ? AND " + KEY_EMREMPID + "= ?";
        String[] whereArgs = {month, empId};
        Cursor cursor = db.query(TABLE_EMPROSTER,new String[]{KEY_EMRID,KEY_EMRCHECKIN, KEY_EMRCHECKOUT, KEY_EMRSTATUS} ,where, whereArgs, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                if(Integer.parseInt(KEY_STRDATE.substring(0,2)) < Integer.parseInt(date)) {
                    days+=1;
                    if (cursor.getString(3).equals("On Duty")) {
                        onDuty++;
                        if (!cursor.getString(1).equals("") && !cursor.getString(2).equals("")) {
                                present++;
                        }
                    } else if(cursor.getString(3).equals("planned Leave")) {
                        plannedL++;
                    }
                    else if (cursor.getString(3).equals("Off Duty")){
                        offD++;
                    }
                    else if (cursor.getString(3).equals("Weekly Off")){
                        weeklyO++;
                    }
                }
            } while (cursor.moveToNext());
        }
        db.close();
        String ret = "Days:" + onDuty + ", Present:" + present + ", PL:" + plannedL + ", UPL:" + (onDuty - present);
        return ret;
    }


    public void updateApplicationAcceptStatus(String applicationId, String acceptStatus){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("accepted", acceptStatus);
        String where = KEY_AID + "= ?";
        String[] whereArgs = {applicationId};
        db.update(TABLE_APPLICATIONS, cv, where, whereArgs);
    }

    public void updateStRoster(StRoster st){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("storeStatus", st.getStoreStatus());
        cv.put("events", st.getEvents());
        cv.put("eventTitle", st.getEventTitle());
        cv.put("eventSubject", st.getEventSub());
        String where = KEY_STRDATE + "= ?";
        String[] whereArgs = {st.getDate()};
        db.update(TABLE_STROSTER, cv, where, whereArgs);
    }

    public void updateStRosterPushed(StRoster st){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("pushed", "true");
        String where = KEY_STRDATE + "= ?";
        String[] whereArgs = {st.getDate()};
        db.update(TABLE_STROSTER, cv, where, whereArgs);
    }

    public void updateEmpRoster(EmpRoster st){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("empStatus", st.getEmpStatus());
        cv.put("shift", st.getShift());
        String where = KEY_EMRDATE + "= ? AND " + KEY_EMREMPID + "= ?";
        String[] whereArgs = {st.getDate(), st.getEmpId()};
        db.update(TABLE_EMPROSTER, cv, where, whereArgs);
    }

    public void updateEMRPunch(String empId, String date, String checkIn, String checkOut){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("checkIn", checkIn);
        cv.put("checkOut", checkOut);
        String where = KEY_EMRDATE + "= ? AND " + KEY_EMREMPID + "= ?";
        String[] whereArgs = {date, empId};
        db.update(TABLE_EMPROSTER, cv, where, whereArgs);
    }

    public void updateEmpRosterPushed(EmpRoster st){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("pushed", "true");
        String where = KEY_EMRDATE + "= ? AND " + KEY_EMREMPID + "= ?";
        String[] whereArgs = {st.getDate(), st.getEmpId()};
        db.update(TABLE_EMPROSTER, cv, where, whereArgs);
    }

    public void updateCheckIn(String empId,String date, String checkIn){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("checkIn", checkIn);
            String where = KEY_EMREMPID + "= ? AND " + KEY_EMRDATE + "= ?";
            String[] whereArgs = {empId, date};
            Integer ret = db.update(TABLE_EMPROSTER, cv, where, whereArgs);
            Log.d("myTag", "update val : " + ret);
        }
        catch(SQLException e){
            Log.d("myTag", "error", e);
        }
    }

    public void updateCheckOut(String emrId,String date, String checkOut){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("checkOut", checkOut);
            String where = KEY_EMREMPID + "= ? AND " + KEY_EMRDATE + "= ?";
            String[] whereArgs = {emrId, date};
            db.update(TABLE_EMPROSTER, cv, where, whereArgs);
            db.close();}
        catch(SQLException e){
            Log.d("myTag", "error", e);
        }
    }


    public void deleteRoster(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_EMPROSTER);
        db.execSQL("delete from "+ TABLE_STROSTER);
    }

    public void deleteEmp(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_EMPLOYEE);
    }

    public void deleteStore(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_STORE);
    }

    public void deleteAppli(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_APPLICATIONS);
    }
}
