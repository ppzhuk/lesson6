package ru.ppzh.todolist;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    public static final Uri TASKS_URI = Uri.withAppendedPath(ToDoListContentProvider.CONTENT_URI, "tasks");

    private final ContentObserver observer = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Log.d(TAG, "ContentObserver: onChange");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Cursor cursor = managedQuery(TASKS_URI,
                null, null, null, null);

        cursor.registerContentObserver(observer);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(observer);
    }

    public void addData(View view) {
        ContentValues values = new ContentValues();
        values.put(TasksTable.COLUMN_TEXT,
                "some task");
        values.put(TasksTable.COLUMN_DATE,
                new Date().toString());
        values.put(TasksTable.COLUMN_COMPLETED,
                "False");
        values.put(TasksTable.COLUMN_FAVORITE,
                "False");
        getContentResolver().insert(
                TASKS_URI,
                values);
    }
}
