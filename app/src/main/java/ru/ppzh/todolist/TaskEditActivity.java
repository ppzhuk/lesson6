package ru.ppzh.todolist;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TaskEditActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private long taskId;
    private Task task;

    private EditText taskET;
    private CheckBox favoriteCB;
    private CheckBox completedCB;
    private Button saveBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_edit);
        taskId = getIntent().getLongExtra(MainActivity.TASK_ID_EXTRA, -1);

        taskET = (EditText) findViewById(R.id.task_text_et);
        favoriteCB = (CheckBox) findViewById(R.id.favorite_cb);
        completedCB = (CheckBox) findViewById(R.id.completed_cb);
        saveBTN = (Button) findViewById(R.id.save_btn);

        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTask();
                finish();
            }
        });

        getSupportLoaderManager().initLoader(1, null, this);
    }

    private void updateTask() {
        Uri uri = ContentUris.withAppendedId(MainActivity.TASKS_URI, taskId);
        ContentValues values = new ContentValues();
        SimpleDateFormat format =
                new SimpleDateFormat(MainActivity.DATE_FORMAT, Locale.getDefault());

        values.put(TasksTable.COLUMN_TEXT,
                taskET.getText().toString());
        values.put(TasksTable.COLUMN_DATE,
                format.format(new Date()));
        values.put(TasksTable.COLUMN_COMPLETED,
                Boolean.toString(completedCB.isChecked()));
        values.put(TasksTable.COLUMN_FAVORITE,
                Boolean.toString(favoriteCB.isChecked()));

        getContentResolver().update(uri, values, null, null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                this,
                MainActivity.TASKS_URI,
                null,
                "_ID = " + Long.toString(taskId),
                null, null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        TaskCursor taskCursor = new TaskCursor(data);
        taskCursor.moveToFirst();
        task = taskCursor.getTask();

        taskET.setText(task.getText());
        favoriteCB.setChecked(task.isFavorite());
        completedCB.setChecked(task.isCompleted());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
