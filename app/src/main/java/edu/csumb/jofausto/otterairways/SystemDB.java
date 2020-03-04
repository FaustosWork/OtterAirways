package edu.csumb.jofausto.otterairways;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;

public class SystemDB {

    public static final String DB_NAME = "airlineticketreservationsystem.db";
    public static final int DB_VERSION = 1;

    public static final String ACCOUNT_TABLE = "accounts";
    public static final String FLIGHT_TABLE = "flights";
    public static final String RESERVATION_TABLE = "reservations";
    public static final String LOG_TABLE = "logtable";

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public static final String FLIGHT_NO = "flightno";
    public static final String DEPARTURE = "departure";
    public static final String ARRIVAL = "arrival";
    public static final String TICKETS_AVAILABLE = "ticketsavailable";
    public static final String TIME = "time";
    public static final String PRICE = "price";
    public static final String RESERVATION_NUMBER = "reservationnumber";

    public static final String OWNER = "owner";
    public static final String TICKETS_RESERVED = "ticketsreserved";
    public static final String RESERVED_FLIGHT_NO = "reservedflightno";
    public static final String AMOUNT_OWED = "amountowed";

    public static final String LOG = "log";


    public static final String CREATE_ACCOUNT_TABLE =
            "CREATE TABLE " + ACCOUNT_TABLE + " (" +
                    USERNAME + " TEXT    NOT NULL UNIQUE, " +
                    PASSWORD + " TEXT    NOT NULL);";

    public static final String CREATE_FLIGHT_TABLE =
            "CREATE TABLE " + FLIGHT_TABLE + " (" +
                    FLIGHT_NO + " TEXT    NOT NULL UNIQUE, " +
                    DEPARTURE + " TEXT    NOT NULL, " +
                    ARRIVAL + " TEXT    NOT NULL, " +
                    TIME + " TEXT    NOT NULL, " +
                    TICKETS_AVAILABLE + " INTEGER    NOT NULL, " +
                    PRICE + " TEXT    NOT NULL);";

    public static final String CREATE_RESERVATION_TABLE =
            "CREATE TABLE " + RESERVATION_TABLE + " (" +
                    RESERVATION_NUMBER + " INTEGER    PRIMARY KEY, " +
                    OWNER + " TEXT    NOT NULL, " +
                    TICKETS_RESERVED + " INTEGER    NOT NULL, " +
                    RESERVED_FLIGHT_NO + " TEXT    NOT NULL, " +
                    AMOUNT_OWED + " TEXT NOT NULL );";

    public static final String CREATE_LOG_TABLE =
            "CREATE TABLE " + LOG_TABLE + " (" +
                    LOG + " TEXT NOT NULL);";

    public static final String DROP_ACCOUNT_TABLE =
            "DROP TABLE IF EXISTS " + ACCOUNT_TABLE;

    public static final String DROP_FLIGHT_TABLE =
            "DROP TABLE IF EXISTS " + FLIGHT_TABLE;

    public static final String DROP_RESERVATION_TABLE =
            "DROP TABLE IF EXISTS " + RESERVATION_TABLE;

    public static final String DROP_LOG_TABLE =
            "DROP TABLE IF EXISTS " + LOG_TABLE;


    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name,
                        CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_ACCOUNT_TABLE);
            db.execSQL(CREATE_FLIGHT_TABLE);
            db.execSQL(CREATE_RESERVATION_TABLE);
            db.execSQL(CREATE_LOG_TABLE);

            db.execSQL("INSERT INTO accounts VALUES ('alice5', 'csumb100')");
            db.execSQL("INSERT INTO accounts VALUES ('brian77', '123ABC')");
            db.execSQL("INSERT INTO accounts VALUES ('chris21', 'CHRIS21')");

            db.execSQL("INSERT INTO flights VALUES ('Otter101', 'Monterey', 'Los Angeles', '10:00(AM)', 10, '150.00')");
            db.execSQL("INSERT INTO flights VALUES ('Otter102', 'Los Angeles', 'Monterey', '1:00(PM)', 10, '150.00')");
            db.execSQL("INSERT INTO flights VALUES ('Otter201', 'Monterey', 'Seattle', '11:00(AM)', 5, '200.50')");
            db.execSQL("INSERT INTO flights VALUES ('Otter205', 'Monterey', 'Seattle', '3:00(PM)', 15, '150.00')");
            db.execSQL("INSERT INTO flights VALUES ('Otter202', 'Seattle', 'Monterey', '2:00(PM)', 5, '200.50')");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {

            Log.d("Task list", "Upgrading db from version "
                    + oldVersion + " to " + newVersion);

            db.execSQL(SystemDB.DROP_ACCOUNT_TABLE);
            db.execSQL(SystemDB.DROP_FLIGHT_TABLE);
            db.execSQL(SystemDB.DROP_RESERVATION_TABLE);
            db.execSQL(SystemDB.DROP_LOG_TABLE);
            onCreate(db);
        }

    }

    private SQLiteDatabase db;
    public DBHelper dbHelper;

    public SystemDB(Context context) {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);

    }

    public boolean addAccount(String username, String password) {
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, username);
        cv.put(PASSWORD, password);
        if(hasAccount(username)) {
            return false;
        }
        long result = db.insert(ACCOUNT_TABLE, null, cv);
        if(result == -1) {
            return false;
        }
        return true;
    }

    public boolean hasAccount(String username) {
        db = dbHelper.getWritableDatabase();
        String selectString = "SELECT * FROM " + ACCOUNT_TABLE + " WHERE " + USERNAME + " =?";

        Cursor cursor = db.rawQuery(selectString, new String[] {username});

        boolean hasObject = false;
        if(cursor.moveToFirst()){
            hasObject = true;
        }

        return hasObject;
    }

    public Cursor getFlights(String departure, String arrival, int numOfTickets) {
        String whereClause = DEPARTURE + " =? AND " + ARRIVAL + " =? AND " + TICKETS_AVAILABLE + " >=" + numOfTickets ;  // WHERE departure = ? AND arrival = ?

        String[] whereArgs = new String[] {departure, arrival}; // {Monterey, Seattle }

        // SELECT FROM flights
        Cursor cursor = dbHelper.getReadableDatabase().query(FLIGHT_TABLE, null, whereClause, whereArgs, null, null, null);
        return cursor;
    }

    public Cursor verifyLogin(String username, String password) {
        String whereClause = USERNAME + " =? AND " + PASSWORD + " =?";
        String[] whereArgs = new String[] {username, password};

        Cursor cursor = dbHelper.getReadableDatabase().query(ACCOUNT_TABLE, null, whereClause, whereArgs, null, null, null);
        return cursor;
    }

    public Cursor getFlight(String flightNo) {
        String whereClause = FLIGHT_NO + " =? ";
        String[] whereArgs = new String[] {flightNo};

        Cursor cursor = dbHelper.getReadableDatabase().query(FLIGHT_TABLE, null, whereClause, whereArgs, null, null, null);
        return cursor;
    }

    public String bookFlight(String flightNo, int numOfTickets, String account) {
        db = dbHelper.getWritableDatabase();
        Cursor cursor = getFlight(flightNo);
        String ticketsAvailable = "";
        cursor.moveToFirst();
        ticketsAvailable += cursor.getString(4);
        Double priceOwed = Double.parseDouble(cursor.getString(5));
        int tickets = Integer.parseInt(ticketsAvailable);

        priceOwed *= numOfTickets;
        tickets -= numOfTickets;
        ContentValues cv = new ContentValues();
        cv.put(TICKETS_AVAILABLE, tickets);

        String whereClause = FLIGHT_NO + " = ? ";
        String[] whereArgs = new String[] {flightNo};
        db.update(FLIGHT_TABLE, cv, whereClause, whereArgs);


        ContentValues reservationCV = new ContentValues();
        reservationCV.put(OWNER, account);
        reservationCV.put(TICKETS_RESERVED, numOfTickets);
        reservationCV.put(RESERVED_FLIGHT_NO, flightNo);
        reservationCV.putNull(RESERVATION_NUMBER);
        reservationCV.put(AMOUNT_OWED, priceOwed.toString());
        db.insert(RESERVATION_TABLE, null,reservationCV);

        Cursor test = db.query(RESERVATION_TABLE, null, null, null, null, null, null);
        test.moveToFirst();
        Integer num = test.getCount();
        String text = test.getString(0);

        return num.toString();
    }

    public Cursor getReservation(String reservationNumber) {
        int resNum = Integer.parseInt(reservationNumber);
        String whereClause = RESERVATION_NUMBER + " = " + resNum;

        Cursor cursor = dbHelper.getReadableDatabase().query(RESERVATION_TABLE, null, whereClause, null, null, null, null);
        return cursor;
    }

    public String getAmountOwed(int numOfTickets, String flightNo) {
        db = dbHelper.getWritableDatabase();
        Cursor cursor = getFlight(flightNo);
        cursor.moveToFirst();
        Double priceOwed = Double.parseDouble(cursor.getString(5));
        priceOwed *= numOfTickets;

        return  priceOwed.toString();
    }

    public void deleteReservation(String reservationNumber) {
        db = dbHelper.getWritableDatabase();
        Integer resNum = Integer.parseInt(reservationNumber);
        String whereClause = RESERVATION_NUMBER + " = " + resNum;
        Cursor cursor = dbHelper.getReadableDatabase().query(RESERVATION_TABLE, null, whereClause, null, null, null, null);
        cursor.moveToFirst();

        Integer ticketsReserved = Integer.parseInt(cursor.getString(2));
        String flightNo = cursor.getString(3);

        Cursor flight = getFlight(flightNo);
        flight.moveToFirst();

        Integer newTotal = Integer.parseInt(flight.getString(4));
        newTotal += ticketsReserved;

        ContentValues update = new ContentValues();
        update.put(TICKETS_AVAILABLE, newTotal);
        String where = FLIGHT_NO + " = ? ";
        String[] whereArgs = new String[] {flightNo};
        db.update(FLIGHT_TABLE, update, where, whereArgs);

        String[] where2 = new String[] {resNum.toString()};

        db.delete(RESERVATION_TABLE, RESERVATION_NUMBER + " = " ,where2);
    }

    public Cursor getReservationsByOwner(String username) {
        db = dbHelper.getWritableDatabase();
        String whereClause = OWNER + " = ?";

        String[] whereArgs = new String[] {username};
        Cursor cursor = dbHelper.getReadableDatabase().query(RESERVATION_TABLE, null, whereClause, whereArgs, null, null, null);

        return cursor;
    }

    public void logNewAccount(String username) {
        String log = "New Account- \n";
        log += username + " ";
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        String rightNow = dateFormat.format(date).toString();
        log += rightNow;
        log += "\n";

        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LOG, log);
        db.insert(LOG_TABLE, null, cv);
    }

    public void logNewReservation(String reservationNumber) {
        db = dbHelper.getWritableDatabase();
        String log = "Reserve Seat- \n";



        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        String rightNow = dateFormat.format(date).toString();

        Cursor reservation = getReservation(reservationNumber);
        reservation.moveToFirst();

        Cursor flight = getFlight(reservation.getString(3));
        flight.moveToFirst();

        log += "Customer: " + reservation.getString(1) + "\n";
        log += "Flight No: " + reservation.getString(3) + "\n";
        log += "Departure: " + flight.getString(1) + "\n";
        log += "Arrival: " + flight.getString(2) + "\n";
        log += "Time: " + flight.getString(3) + "\n";
        log += "Number of Tickets: " + reservation.getString(2) + "\n";
        log += "Reservation Number: " + reservation.getString(0) + "\n";
        log += "Total Due: " + reservation.getString(4) + "\n";
        log += "Time Created: " + rightNow + "\n";
        log += "\n";

        ContentValues cv = new ContentValues();
        cv.put(LOG, log);
        db.insert(LOG_TABLE, null, cv);
    }

    public void logDeletion() {
        db = dbHelper.getWritableDatabase();
        db.execSQL("INSERT INTO logtable VALUES('Delete Reservation-\nCustomer: CS28G0\nReservationNumber: 2\nFlightNo: Otter102\nDeparture: Los Angeles\nArrival: Monterey\nTime: 1:00(PM)\nNumber Of Tickets: 6\nTime Created 12/13/2019 11:15:36')");

    }

    public Cursor getLog() {
        Cursor cursor = dbHelper.getReadableDatabase().query(LOG_TABLE, null, null, null, null, null, null);
        return cursor;
    }

    public void addFlight(String flightNo, String departure, String arrival, String time, int ticketsAvailable, String price) {
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FLIGHT_NO, flightNo);
        cv.put(DEPARTURE, departure);
        cv.put(ARRIVAL, arrival);
        cv.put(TIME, time);
        cv.put(TICKETS_AVAILABLE, ticketsAvailable);
        cv.put(PRICE, price);

        db.insert(FLIGHT_TABLE, null, cv);

    }


}