package ru.ppzh.todolist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String TASK_ID_EXTRA =
            "ru.ppzh.dotolist.task_id";
    public static final String DATE_FORMAT = "EEE, d MMM yyyy HH:mm";
    public static final String SORT_ORDER = TasksTable.COLUMN_COMPLETED + ", "
            + TasksTable.COLUMN_FAVORITE + " DESC, "
            + TasksTable.COLUMN_DATE + " DESC";

    public static final Uri TASKS_URI = Uri.withAppendedPath(ToDoListContentProvider.CONTENT_URI, "tasks");

    TaskCursorAdapter adapter;

    ListView list;
    EditText taskInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        adapter = new TaskCursorAdapter(this, null);

        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);

        taskInput = (EditText) findViewById(R.id.task_text_input);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), TaskEditActivity.class);
                i.putExtra(TASK_ID_EXTRA, id);
                startActivity(i);
            }
        });

        getSupportLoaderManager().initLoader(0, null, this);
    }

    public void addData(View view) {
        ContentValues values = new ContentValues();
        SimpleDateFormat format =
                new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        values.put(
                TasksTable.COLUMN_TEXT,
                taskInput.getText().toString()
        );
        values.put(TasksTable.COLUMN_DATE,
                format.format(new Date()));
        values.put(TasksTable.COLUMN_COMPLETED,
                "False");
        values.put(TasksTable.COLUMN_FAVORITE,
                "False");
        getContentResolver().insert(
                TASKS_URI,
                values);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, TASKS_URI, null, null, null, SORT_ORDER);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(new TaskCursor(data));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
