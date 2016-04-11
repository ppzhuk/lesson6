package ru.ppzh.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ToDoListDatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = "ToDoListDatabaseHelper";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "todolist.db";

    private static final String SQL_CREATE_TASK_TABLE =
            "CREATE TABLE " + TasksTable.TABLE_NAME
                    + " ("
                    + TasksTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TasksTable.COLUMN_TEXT + " TEXT, "
                    + TasksTable.COLUMN_FAVORITE + " BOOLEAN, "
                    + TasksTable.COLUMN_COMPLETED + " BOOLEAN, "
                    + TasksTable.COLUMN_DATE + " DATETIME"
                    + ")";

    public ToDoListDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TASK_TABLE);

        Log.d(TAG, "Database created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
