//package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;
//
//import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DBHandler.COL_ACCNO;
//import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DBHandler.COL_AMOUNT;
//import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DBHandler.COL_EXPENSETYPE;
//import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DBHandler.COl_DATE;
//import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DBHandler.TABLE_TRANSACTION;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
//import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
//import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;
//
//public class PersistentTransactionDAO implements TransactionDAO {
//    private DBHandler dbHandler;
//    private SQLiteDatabase db;
//
//    public PersistentTransactionDAO(Context context) {
//        this.dbHandler = new DBHandler(context);
//    }
//
//    @Override
//    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
//        db = dbHandler.getWritableDatabase();
//        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//
//        ContentValues values = new ContentValues();
//        values.put(COl_DATE,dateFormat.format(date));
//        values.put(COL_ACCNO,accountNo);
//        values.put(COL_EXPENSETYPE, String.valueOf(expenseType));
//        values.put(COL_AMOUNT,amount);
//
//        db.insert(TABLE_TRANSACTION,null,values);
//        db.close();
//
//    }
//
//    @Override
//    public List<Transaction> getAllTransactionLogs() throws ParseException {
//        db = dbHandler.getReadableDatabase();
//        Cursor cursorTransac = db.rawQuery("SELECT * FROM "+TABLE_TRANSACTION,null);
//
//        ArrayList<Transaction> transactionList = new ArrayList<>();
//
//        if(cursorTransac.moveToFirst()){
//            do {
//                String date = cursorTransac.getString(cursorTransac.getColumnIndex(COl_DATE));
//                String accNo = cursorTransac.getString(cursorTransac.getColumnIndex(COL_ACCNO));
//                String expenseType = cursorTransac.getString(cursorTransac.getColumnIndex(COL_EXPENSETYPE));
//                Double amount = cursorTransac.getDouble(cursorTransac.getColumnIndex(COL_AMOUNT));
//
//                Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(date);
//                ExpenseType expenseType1 = ExpenseType.valueOf(expenseType);
//
//                Transaction transaction = new Transaction(date1,accNo,expenseType1,amount);
//                transactionList.add(transaction);
//            }while (cursorTransac.moveToNext());
//
//        }
//        cursorTransac.close();
//        return transactionList;
//    }
//
//    @Override
//    public List<Transaction> getPaginatedTransactionLogs(int limit) throws ParseException {
//        db = dbHandler.getReadableDatabase();
//        Cursor cursorTransac = db.rawQuery("SELECT * FROM "+TABLE_TRANSACTION,null);
//        int size = cursorTransac.getCount();
//        ArrayList<Transaction> transactionList = new ArrayList<>();
//
//        if(cursorTransac.moveToFirst()){
//            do {
//                String date = cursorTransac.getString(cursorTransac.getColumnIndex(COl_DATE));
//                String accNo = cursorTransac.getString(cursorTransac.getColumnIndex(COL_ACCNO));
//                String expenseType = cursorTransac.getString(cursorTransac.getColumnIndex(COL_EXPENSETYPE));
//                Double amount = cursorTransac.getDouble(cursorTransac.getColumnIndex(COL_AMOUNT));
//
//                Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(date);
//                ExpenseType expenseType1 = ExpenseType.valueOf(expenseType);
//
//                Transaction transaction = new Transaction(date1,accNo,expenseType1,amount);
//                transactionList.add(transaction);
//            }while (cursorTransac.moveToNext());
//
//        }
//        cursorTransac.close();
//        if(size<=limit){
//            return transactionList;
//        }
//        else {
//            return transactionList.subList(size-limit,size);
//        }
//
//    }
//}
/*
 * Copyright 2015 Department of Computer Science and Engineering, University of Moratuwa.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *                  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DBHandler.COL_ACCNO;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DBHandler.COL_AMOUNT;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DBHandler.COL_EXPENSETYPE;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DBHandler.COl_DATE;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DBHandler.TABLE_TRANSACTION;


/**
 * This is an In-Memory implementation of TransactionDAO interface. This is not a persistent storage. All the
 * transaction logs are stored in a LinkedList in memory.
 */
public class PersistentTransactionDAO implements TransactionDAO {
    private final DBHandler helper;
    private SQLiteDatabase db;

    public PersistentTransactionDAO(Context context) {
        helper = new DBHandler(context);
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {

        db = helper.getWritableDatabase();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        ContentValues values = new ContentValues();
        values.put(COl_DATE, df.format(date));
        values.put(COL_ACCNO, accountNo);
        values.put(COL_EXPENSETYPE, String.valueOf(expenseType));
        values.put(COL_AMOUNT, amount);

        // insert row
        db.insert(TABLE_TRANSACTION, null, values);
        db.close();
    }

    @Override
    public List<Transaction> getAllTransactionLogs() throws ParseException {
        List<Transaction> transactions = new ArrayList<Transaction>();

        db = helper.getReadableDatabase();

        String[] projection = {
                COl_DATE,
                COL_ACCNO,
                COL_EXPENSETYPE,
                COL_AMOUNT
        };

        Cursor cursor = db.query(
                TABLE_TRANSACTION,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        while(cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndex(COl_DATE));
            Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(date);
            String accountNumber = cursor.getString(cursor.getColumnIndex(COL_ACCNO));
            String type = cursor.getString(cursor.getColumnIndex(COL_EXPENSETYPE));
            ExpenseType expenseType = ExpenseType.valueOf(type);
            double amount = cursor.getDouble(cursor.getColumnIndex(COL_AMOUNT));
            Transaction transaction = new Transaction(date1,accountNumber,expenseType,amount);

            transactions.add(transaction);
        }
        cursor.close();
        return transactions;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) throws ParseException {

        List<Transaction> transactions = new ArrayList<Transaction>();

        db = helper.getReadableDatabase();

        String[] projection = {
                COl_DATE,
                COL_ACCNO,
                COL_EXPENSETYPE,
                COL_AMOUNT
        };

        Cursor cursor = db.query(
                TABLE_TRANSACTION,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        int size = cursor.getCount();

        while(cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndex(COl_DATE));
            Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(date);
            String accountNumber = cursor.getString(cursor.getColumnIndex(COL_ACCNO));
            String type = cursor.getString(cursor.getColumnIndex(COL_EXPENSETYPE));
            ExpenseType expenseType = ExpenseType.valueOf(type);
            double amount = cursor.getDouble(cursor.getColumnIndex(COL_AMOUNT));
            Transaction transaction = new Transaction(date1,accountNumber,expenseType,amount);

            transactions.add(transaction);
        }

        if (size <= limit) {
            return transactions;
        }
        // return the last <code>limit</code> number of transaction logs
        return transactions.subList(size - limit, size);


    }

}

