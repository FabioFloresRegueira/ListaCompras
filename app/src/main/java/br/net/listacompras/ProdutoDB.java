package br.net.listacompras;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProdutoDB extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "produtos";
    public static final String COLUMN_NAME_ID = "_id";
    public static final String COLUMN_NAME_NOME = "nome";
    public static final String COLUMN_NAME_QTD = "quantidade";
    public static final String[] COLUMNS = {COLUMN_NAME_ID, COLUMN_NAME_NOME, COLUMN_NAME_QTD};
    private static final String TAG = "ProdutoDB";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "listacompras.db";
    private static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME_NOME + " TEXT, " + COLUMN_NAME_QTD + " INTEGER) ";
    private static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public ProdutoDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP);
        db.execSQL(SQL_CREATE);
    }

    public Cursor getDataCursor() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,                     // The table to query
                COLUMNS,                        // The columns to return
                null,                           // The columns for the WHERE clause
                null,                           // The values for the WHERE clause
                null,                           // don't group the rows
                null,                           // don't filter by row groups
                COLUMN_NAME_ID + " DESC"        // The sort order
        );
        return cursor;
    }

    public long inserir(String nome, int quantidade) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME_NOME, nome);
        contentValues.put(COLUMN_NAME_QTD, quantidade);

        return getWritableDatabase().insert(TABLE_NAME, null, contentValues);
    }

    public void deletar(long id) {
        getWritableDatabase().delete(TABLE_NAME, COLUMN_NAME_ID + " = " + id, null);
    }
}
