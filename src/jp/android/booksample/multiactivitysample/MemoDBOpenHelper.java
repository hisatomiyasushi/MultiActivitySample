package jp.android.booksample.multiactivitysample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoDBOpenHelper extends SQLiteOpenHelper {

    /**
     * ここで扱うデータベースの名称です。
     */
    private static final String DATABASE_NAME = "MEMO_DATA";

    /**
     * データベースにテーブルを作成するSQL文です
     */
    private static final String SQL_CREATE_TABLE = String
            .format("CREATE TABLE %1$s ( %2$s INTEGER PRIMARY KEY AUTOINCREMENT, %3$s TEXT NOT NULL, %4$s TEXT);",
                    "memo_data", "_id", "title", "body");

    /**
     * データベースのバージョンを示す値です。この値がデータベースのバージョン確認に用いられます。
     */
    private static final int DATABASE_VERSION = 1;

    public MemoDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * データベースを作成する処理です。 コンストラクタが呼び出された時点で、使用するデータベースが存在しない場合呼び出されます。
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    /**
     * データベースを更新する処理です。 コンストラクタが呼び出された時点で、使用するデータベースのバージョンが古かった場合に呼び出されます。
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // ここでは特に何も行いません
    }

}
