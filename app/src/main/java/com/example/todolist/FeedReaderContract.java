package com.example.todolist;

public class FeedReaderContract {
    private FeedReaderContract() {}

    public static class FeedEntry {
        public static final String COLUMN_NAME_ID = "id";
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_NOTIFICATION = "notification";
        public static final String COLUMN_NAME_ATTACHMENT = "attachment";
        public static final String COLUMN_NAME_ATTACHMENT_PATH = "attachment_path";
        public static final String COLUMN_NAME_NOTIFICATION_ID = "notification_id";



    }
    static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
                    FeedEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    FeedEntry.COLUMN_NAME_CATEGORY + " TEXT," +
                    FeedEntry.COLUMN_NAME_DATE + " REAL," +
                    FeedEntry.COLUMN_NAME_TIME + " REAL," +
                    FeedEntry.COLUMN_NAME_STATUS + " REAL," +
                    FeedEntry.COLUMN_NAME_NOTIFICATION + " REAL," +
                    FeedEntry.COLUMN_NAME_ATTACHMENT + " REAL," +
                    FeedEntry.COLUMN_NAME_ATTACHMENT_PATH + " TEXT," +
                    FeedEntry.COLUMN_NAME_NOTIFICATION_ID + " REAL)";
    static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
}
