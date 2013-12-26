
package jp.android.booksample.multiactivitysample;

import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ListActivity {

	// リモートからpushするテスト
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        MemoDBOpenHelper helper = new MemoDBOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        // データベースクエリの発行
        Cursor c = db.query("memo_data", null, null, null, null, null, null);

        // 表示する値の用意
        String[] from = new String[] { "title", "body" };
        int[] to = new int[] { android.R.id.text1, android.R.id.text2 };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2, c, from, to, 0);
        setListAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result;
        MemoDBOpenHelper helper;
        SQLiteDatabase db;
        switch (item.getItemId()) {
            case R.id.operate_additem:
                // データベースを開く
                helper = new MemoDBOpenHelper(this);
                db = helper.getWritableDatabase();
                
                // データを追加する
                ContentValues values = new ContentValues();
                values.put("title", "行の追加");
                values.put("body", "サンプル");
                db.insert("memo_data", null, values);
                db.close();
                
                reloadCursor();
                result = true;
                break;
            case R.id.operate_deleteitem:
                SimpleCursorAdapter adapter = (SimpleCursorAdapter) getListAdapter();
                if (adapter.getCount() > 0) {
                    // リスト上最後の項目のIDを取得する
                    long id = adapter.getItemId(adapter.getCount() - 1);
                    
                    // データの削除処理
                    helper = new MemoDBOpenHelper(this);
                    db = helper.getWritableDatabase();
                    
                    db.delete("memo_data", "_id = ?", new String[] { Long.toString(id) });
                   reloadCursor();
                   
                }
                result = true;
                break;

            default:
                result = super.onOptionsItemSelected(item);
                break;
        }
        return result;
    }
    
    
    /**
     * カーソルを再読込します
     */
    private void reloadCursor() {
        MemoDBOpenHelper helper = new MemoDBOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        // データベースクエリの発行
        Cursor c = db.query("memo_data", null, null, null, null, null, null);

        // カーソルの変更
        SimpleCursorAdapter adapter = (SimpleCursorAdapter) getListAdapter();
        adapter.swapCursor(c);
    }

}
