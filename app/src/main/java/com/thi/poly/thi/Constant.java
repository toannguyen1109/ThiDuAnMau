package com.thi.poly.thi;

public interface Constant {
    boolean isDEBUG = true;

    String TABLE = "Thi";
    String TB_COLUMN_ID = "ID";
    String TB_COLUMN_NAME = "Name";



    String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE + "(" +
            "" + TB_COLUMN_ID + " VARCHAR PRIMARY KEY," +
            "" + TB_COLUMN_NAME + " VARCHAR" +

            ")";
}
