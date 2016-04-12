package es.unizar.eina.allpc;

/**
 * Created by Adrian on 12/04/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Simple notes database access helper class. Defines the basic CRUD operations
 * for the notepad example, and gives the ability to list all notes as well as
 * retrieve or modify a specific note.
 *
 * This has been improved from the first version of this tutorial through the
 * addition of better error handling and also using returning a Cursor instead
 * of using a collection of inner classes (which is less scalable and not
 * recommended).
 */
public class DbAdapter {

    public static final String KEY_ADMIN_ROWID = "_id";
    public static final String KEY_ADMIN_CORREO = "correo";
    public static final String KEY_ADMIN_NOMBRE = "nombre";
    public static final String KEY_ADMIN_PASS = "password";

    public static final String KEY_PC_ROWID = "_id";
    public static final String KEY_PC_NOMBRE = "nombre";

    private static final String TAG = "DbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    /**
     * Database creation sql statement
     */
    private static final String DATABASE_ADMIN_CREATE =
            "create table administradores (_id integer primary key autoincrement, "
                    + "correo text not null, nombre text not null, password text not null);";


    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE_ADMIN = "administradores";
    private static final String DATABASE_TABLE_PC = "pc";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_ADMIN_CREATE);
            //db.execSQL(DATABASE_NOTES_CREATE);

            //Usuario por defecto
            db.execSQL("insert into administradores values (null, 'admin@admin.com', 'admin', 'admin');");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS administradores");
            //db.execSQL("DROP TABLE IF EXISTS categories");
            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     *
     * @param ctx the Context within which to work
     */
    public DbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     *
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public DbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }


    public long createAdmin(String correo, String nombre, String password){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ADMIN_CORREO, correo);
        initialValues.put(KEY_ADMIN_NOMBRE, nombre);
        initialValues.put(KEY_ADMIN_PASS, password);

        return  mDb.insert(DATABASE_TABLE_ADMIN, null, initialValues);
    }

    public boolean deleteAdmin(long rowId){
        return  mDb.delete(DATABASE_TABLE_ADMIN, KEY_ADMIN_ROWID + "=" + rowId, null) > 0;
    }

    public boolean login(String correo, String password){
        Cursor c =
                mDb.query(true, DATABASE_TABLE_ADMIN, new String[] {KEY_ADMIN_CORREO,
                                KEY_ADMIN_NOMBRE}, KEY_ADMIN_CORREO + "= '" + correo + "'", null,
                        null, null, null, null);

        if(c.moveToFirst()){
            System.out.println("USER: " + c.getString(0) + " PASS: " + c.getString(1));
            return (correo.equals(c.getString(0)) && password.equals(c.getString(1)));
        }
        else {
            return false;
        }
    }
}