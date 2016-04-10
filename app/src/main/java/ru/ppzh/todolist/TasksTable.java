package ru.ppzh.todolist;

import android.provider.BaseColumns;

interface TasksTable extends BaseColumns {
    String TABLE_NAME = "tasks";

    String COLUMN_TEXT = "text";
    String COLUMN_DATE = "date";
    String COLUMN_FAVORITE = "favorite";
    String COLUMN_COMPLETED = "completed";
}
