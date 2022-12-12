//package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;
//
//import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DBHandler.COL_ACCHOLDERNAME;
//import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DBHandler.COL_ACCNO;
//import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DBHandler.COL_COL_BALANCE;
//import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DBHandler.COL_BANKNAME;
//import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DBHandler.COL_EXPENSETYPE;
//import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DBHandler.TABLE_NAME;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
//import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
//import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
//import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
//
//public class PersistentAccountDAO implements AccountDAO {
//
//    private final DBHandler dbHandler;
//    private SQLiteDatabase db;
//
//    public PersistentAccountDAO(Context context) {
//        dbHandler = new DBHandler(context);
//    }
//
//
//    @Override
//    public List<String> getAccountNumbersList() {
//        db = dbHandler.getReadableDatabase();
//        Cursor cursorAccNumb = db.rawQuery("SELECT "+COL_ACCNO+" FROM "+TABLE_NAME,null);
//
//        ArrayList<String> accNumbersList = new ArrayList<>();
//
//        if(cursorAccNumb.moveToFirst()){
//            do {
//                accNumbersList.add(cursorAccNumb.getString(cursorAccNumb.getColumnIndex(COL_ACCNO)));
//
//            } while(cursorAccNumb.moveToNext());
//        }
//
//        cursorAccNumb.close();
//        return accNumbersList;
//
//    }
//
//    @Override
//    public List<Account> getAccountsList() {
//        db = dbHandler.getReadableDatabase();
//        Cursor cursorAccounts = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
//
//        ArrayList<Account> accountList = new ArrayList<>();
//
//        if(cursorAccounts.moveToFirst()){
//            do {
//                accountList.add(new Account(cursorAccounts.getString(cursorAccounts.getColumnIndex(COL_ACCNO)),
//                        cursorAccounts.getString(cursorAccounts.getColumnIndex(COL_BANKNAME)),
//                        cursorAccounts.getString(cursorAccounts.getColumnIndex(COL_ACCHOLDERNAME)),
//                        cursorAccounts.getDouble(cursorAccounts.getColumnIndex(COL_COL_BALANCE))
//                        ));
//
//            } while(cursorAccounts.moveToNext());
//        }
//
//        cursorAccounts.close();
//        return accountList;
//    }
//
//    @Override
//    public Account getAccount(String accountNo) throws InvalidAccountException {
//        db = dbHandler.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE " + COL_ACCNO + " = "+accountNo,null);
//        if(cursor.moveToFirst()){
//            return new Account(cursor.getString(cursor.getColumnIndex(COL_ACCNO)),
//                    cursor.getString(cursor.getColumnIndex(COL_BANKNAME)),
//                    cursor.getString(cursor.getColumnIndex(COL_ACCHOLDERNAME)),
//                    cursor.getDouble(cursor.getColumnIndex(COL_COL_BALANCE))
//            );
//        }
//        String msg = "Account " + accountNo + " is invalid.";
//        throw new InvalidAccountException(msg);
//    }
//
//    @Override
//    public void addAccount(Account account) {
//        db = dbHandler.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//
//        values.put(COL_ACCNO,account.getAccountNo());
//        values.put(COL_BANKNAME,account.getBankName());
//        values.put(COL_ACCHOLDERNAME,account.getAccountHolderName());
//        values.put(COL_COL_BALANCE,account.getCOL_BALANCE());
//
//        db.insert(TABLE_NAME,null,values);
//        db.close();
//
//    }
//
//    @Override
//    public void removeAccount(String accountNo) throws InvalidAccountException {
//        db = dbHandler.getWritableDatabase();
//        db.delete(TABLE_NAME,"accountNo=?",new String[]{accountNo});
//        db.close();
//    }
//
//    @Override
//    public void updateCOL_BALANCE(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
//        db = dbHandler.getReadableDatabase();
//        Account account ;
//        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE " + COL_ACCNO + " = "+accountNo,null);
//        if(cursor.moveToFirst()){
//            account = new Account(cursor.getString(cursor.getColumnIndex(COL_ACCNO)),
//                    cursor.getString(cursor.getColumnIndex(COL_BANKNAME)),
//                    cursor.getString(cursor.getColumnIndex(COL_ACCHOLDERNAME)),
//                    cursor.getDouble(cursor.getColumnIndex(COL_COL_BALANCE))
//            );
//        }
//        else{
//            String msg = "Account " + accountNo + " is invalid.";
//            throw new InvalidAccountException(msg);
//        }
//        switch (expenseType) {
//            case EXPENSE:
//                account.setCOL_BALANCE(account.getCOL_BALANCE() - amount);
//                break;
//            case INCOME:
//                account.setCOL_BALANCE(account.getCOL_BALANCE() + amount);
//                break;
//        }
//        addAccount(account);
//
//    }
//}
package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DBHandler.COL_ACCNO;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DBHandler.COL_BALANCE;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DBHandler.COL_BANKNAME;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DBHandler.COL_ACCHOLDERNAME;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DBHandler.TABLE_NAME;

/**
 * This is an In-Memory implementation of the AccountDAO interface. This is not a persistent storage. A HashMap is
 * used to store the account details temporarily in the memory.
 */
public class PersistentAccountDAO implements AccountDAO {
    private final DBHandler helper;
    private SQLiteDatabase db;


    public PersistentAccountDAO(Context context) {
        helper = new DBHandler(context);
    }

    @Override
    public List<String> getAccountNumbersList() {
        db = helper.getReadableDatabase();

        String[] projection = {
                COL_ACCNO
        };

        Cursor cursor = db.query(
                TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        List<String> accountNumbers = new ArrayList<String>();

        while(cursor.moveToNext()) {
            String accountNumber = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_ACCNO));
            accountNumbers.add(accountNumber);
        }
        cursor.close();
        return accountNumbers;
    }

    @Override
    public List<Account> getAccountsList() {
        List<Account> accounts = new ArrayList<Account>();

        db = helper.getReadableDatabase();

        String[] projection = {
                COL_ACCNO,
                COL_BANKNAME,
                COL_ACCHOLDERNAME,
                COL_BALANCE
        };

        Cursor cursor = db.query(
                TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        while(cursor.moveToNext()) {
            String accountNumber = cursor.getString(cursor.getColumnIndex(COL_ACCNO));
            String bankName = cursor.getString(cursor.getColumnIndex(COL_BANKNAME));
            String accountHolderName = cursor.getString(cursor.getColumnIndex(COL_ACCHOLDERNAME));
            double balance = cursor.getDouble(cursor.getColumnIndex(COL_BALANCE));
            Account account = new Account(accountNumber,bankName,accountHolderName,balance);

            accounts.add(account);
        }
        cursor.close();
        return accounts;

    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {

        db = helper.getReadableDatabase();
        String[] projection = {
                COL_ACCNO,
                COL_BANKNAME,
                COL_ACCHOLDERNAME,
                COL_BALANCE
        };

        String selection = COL_ACCNO + " = ?";
        String[] selectionArgs = { accountNo };

        Cursor c = db.query(
                TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        if (c == null){
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }
        else {
            c.moveToFirst();

            Account account = new Account(accountNo, c.getString(c.getColumnIndex(COL_BANKNAME)),
                    c.getString(c.getColumnIndex(COL_ACCHOLDERNAME)), c.getDouble(c.getColumnIndex(COL_BALANCE)));
            return account;
        }
    }

    @Override
    public void addAccount(Account account) {

        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ACCNO, account.getAccountNo());
        values.put(COL_BANKNAME, account.getBankName());
        values.put(COL_ACCHOLDERNAME, account.getAccountHolderName());
        values.put(COL_BALANCE,account.getBalance());

        // insert row
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {

        db = helper.getWritableDatabase();
        db.delete(TABLE_NAME, COL_ACCNO + " = ?",
                new String[] { accountNo });
        db.close();
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

        db = helper.getWritableDatabase();
        String[] projection = {
                COL_BALANCE
        };

        String selection = COL_ACCNO + " = ?";
        String[] selectionArgs = { accountNo };

        Cursor cursor = db.query(
                TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        double balance;
        if(cursor.moveToFirst())
            balance = cursor.getDouble(0);
        else{
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }

        ContentValues values = new ContentValues();
        switch (expenseType) {
            case EXPENSE:
                values.put(COL_BALANCE, balance - amount);
                break;
            case INCOME:
                values.put(COL_BALANCE, balance + amount);
                break;
        }

        // updating row
        db.update(TABLE_NAME, values, COL_ACCNO + " = ?",
                new String[] { accountNo });

        cursor.close();
        db.close();
    }
}
