package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "expensemngdb.sqlite";

    public static final String TABLE_NAME = "accounts";
    public static final String TABLE_TRANSACTION = "transact";

    public static final String COL_ACCNO = "accountNo";
    public static final String COL_BANKNAME = "bank";
    public static final String COL_ACCHOLDERNAME = "accountHolder";
    public static final String COL_BALANCE = "balance";

    public static final String COL_ID = "ID";
    public static final String COl_DATE = "date";
    public static final String COL_EXPENSETYPE = "expenseType";
    public static final String COL_AMOUNT = "amount";


    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ACCNO + " INTEGER PRIMARY KEY, "
                + COL_BANKNAME + " TEXT, "
                + COL_ACCHOLDERNAME + " TEXT, "
                + COL_BALANCE + " REAL)";

        String query2 = "CREATE TABLE " + TABLE_TRANSACTION + " ("
                + COL_ID + " INTEGER PRIMARY KEY, "
                + COL_ACCNO + " INTEGER, "
                + COL_EXPENSETYPE + " TEXT, "
                + COL_AMOUNT + " REAL," +
                "FOREIGN KEY ("+COL_ACCNO+") REFERENCES " + TABLE_NAME + "(" + COL_ACCNO + ")"
                +")";

        db.execSQL(query1);
        db.execSQL(query2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TRANSACTION);
        onCreate(db);
    }
}
