package com.thi.poly.thi.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.thi.poly.thi.Constant;
import com.thi.poly.thi.model.Phone;

import java.util.ArrayList;
import java.util.List;

public class DAO implements Constant {
    private final DBHelper databaseHelper;

    public DAO(Context context) {
        this.databaseHelper = new DBHelper(context);
    }

    public long insert(Phone phone) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TB_COLUMN_ID, phone.getID());
        contentValues.put(TB_COLUMN_NAME, phone.getName());

        long result = db.insert(TABLE, null, contentValues);
        if (Constant.isDEBUG) Log.e("insert", "insert ID : " + phone.getID());
        db.close();
        return result;
    }

    public long delete(String ID) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long result = db.delete(TABLE, TB_COLUMN_ID + " = ?",
                new String[]{String.valueOf(ID)});
        db.close();
        return result;

    }

    public long update(Phone phone) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TB_COLUMN_NAME, phone.getName());

        return db.update(TABLE, contentValues, TB_COLUMN_ID + " = ?",
                new String[]{String.valueOf(phone.getID())});
    }

    public List<Phone> getAll() {

        List<Phone> phones = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE;

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String bookID = cursor.getString(cursor.getColumnIndex(TB_COLUMN_ID));
                String bookName = cursor.getString(cursor.getColumnIndex(TB_COLUMN_NAME));

                Phone phone = new Phone();
                phone.setID(bookID);
                phone.setName(bookName);

                phones.add(phone);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return phones;

    }

    public Phone getByID(String bookID) {

        Phone phone = null;

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query(TABLE, new String[]{TB_COLUMN_ID, TB_COLUMN_NAME},
                TB_COLUMN_ID + "=?", new String[]{bookID},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {

            String ID = cursor.getString(cursor.getColumnIndex(TB_COLUMN_ID));
            String bookName = cursor.getString(cursor.getColumnIndex(TB_COLUMN_NAME));



            phone = new Phone();
        }
        cursor.close();
        return phone;

    }
}
