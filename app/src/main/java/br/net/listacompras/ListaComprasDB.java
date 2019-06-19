package br.net.listacompras;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListaComprasDB extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "produtos";
    public static final String COLUMN_NAME_ID = "_id";
    public static final String COLUMN_NAME_NOME = "nome";
    public static final String COLUMN_NAME_QTD = "quantidade";
    public static final String COLUMN_NAME_PRECO = "preco";
    public static final String COLUMN_NAME_COMPRADO = "comprado";
    public static final String[] COLUMNS = {COLUMN_NAME_ID, COLUMN_NAME_NOME, COLUMN_NAME_QTD, COLUMN_NAME_PRECO, COLUMN_NAME_COMPRADO};
    private static final String TAG = "ListaComprasDB";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "listacompras.db";
    private static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME_NOME + " TEXT, " + COLUMN_NAME_QTD + " INTEGER, " + COLUMN_NAME_PRECO + " REAL, " + COLUMN_NAME_COMPRADO + " TEXT) ";
    private static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public ListaComprasDB(Context context) {
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

    public List<Produto> getProdutos() {
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
        List<Produto> produtos = new ArrayList<>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            long id = cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_ID));
            String nome = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NOME));
            int quantidade = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_QTD));
            double preco = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_PRECO));
            boolean comprado;
            if (Objects.equals(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_COMPRADO)),"sim")) {
                comprado = true;
            } else {
                comprado = false;
            }
            Produto produto = new Produto(id, nome, quantidade, preco, comprado);
            produtos.add(produto);
            cursor.moveToNext();
        }
        return produtos;
    }

    public long setProduto(Produto produto) {
        String nome = produto.getNome();
        int quantidade = produto.getQuantidade();
        double preco = produto.getPreco();
        String comprado;
        if (produto.isComprado()) {
            comprado = "sim";
        } else {
            comprado = "nao";
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_NOME, nome);
        contentValues.put(COLUMN_NAME_QTD, quantidade);
        contentValues.put(COLUMN_NAME_PRECO, preco);
        contentValues.put(COLUMN_NAME_COMPRADO, comprado);
        return getWritableDatabase().insert(TABLE_NAME, null, contentValues);
    }

    public void removerProduto(long id) {
        getWritableDatabase().delete(TABLE_NAME, COLUMN_NAME_ID + " = " + id, null);
    }

    public void removerTodos() {
        getWritableDatabase().delete(TABLE_NAME, COLUMN_NAME_ID + " > 0 ", null);
    }

    public void atualizarProduto(long id, Produto produto) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_NOME, produto.getNome());
        contentValues.put(COLUMN_NAME_QTD, produto.getQuantidade());
        contentValues.put(COLUMN_NAME_PRECO, produto.getPreco());
        String comprado;
        if (produto.isComprado()) {
            comprado = "sim";
        } else {
            comprado = "nao";
        }
        contentValues.put(COLUMN_NAME_COMPRADO, comprado);
        getWritableDatabase().update(TABLE_NAME, contentValues, COLUMN_NAME_ID + " = " + id, null);
    }

    public void comprarProduto(long id, Produto produto) {
        ContentValues contentValues = new ContentValues();
        String comprado;
        if (produto.isComprado()) {
            comprado = "sim";
        } else {
            comprado = "nao";
        }
        contentValues.put(COLUMN_NAME_COMPRADO, comprado);
        getWritableDatabase().update(TABLE_NAME, contentValues, COLUMN_NAME_ID + " = " + id, null);
    }
}
