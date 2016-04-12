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
    public static final String KEY_PC_MODELO = "modelo";
    public static final String KEY_PC_MARCA = "marca";
    public static final String KEY_PC_RAM = "ram";
    public static final String KEY_PC_PROCESADOR = "procesador";
    public static final String KEY_PC_SO = "so";
    public static final String KEY_PC_HDD = "hdd";
    public static final String KEY_PC_PANTALLA = "pantalla";
    public static final String KEY_PC_GRAFICA = "grafica";
    public static final String KEY_PC_CONEXIONES = "conexiones";

    private static final String TAG = "DbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    /**
     * Database creation sql statement
     */
    private static final String DATABASE_ADMIN_CREATE =
            "create table administradores (_id integer primary key autoincrement, "
                    + "correo text not null, nombre text not null, password text not null);";

    private static final String DATABASE_PC_CREATE =
            "create table pc (_id integer primary key autoincrement, "
                    + "modelo text not null, marca text not null, ram integer not null, " +
                    "procesador text not null, so text, hdd integer not null, " +
                    "pantalla integer not null, grafica text, conexiones text not null);";

    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE_ADMIN = "administradores";
    private static final String DATABASE_TABLE_PC = "pc";
    private static final int DATABASE_VERSION = 3;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_ADMIN_CREATE);
            db.execSQL(DATABASE_PC_CREATE);

            //Usuario por defecto
            db.execSQL("insert into administradores values (null, 'admin@admin.com', 'admin', 'admin');");

            //PC por defecto
            db.execSQL("insert into pc values (null, 'K55Vm', 'Asus', 8, 'i7 3610QM', '8', 500, 15," +
                    "'GT630M', 'Muchas');");
            db.execSQL("insert into pc values (null, 'P2', 'HP', 4, 'i3 2222Y', 'W10', 1000, 15," +
                    "'GT670M', 'Muchas');");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS administradores");
            db.execSQL("DROP TABLE IF EXISTS pc");
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


    /* ADMINISTRADORES */

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
            return (correo.equals(c.getString(0)) && password.equals(c.getString(1)));
        }
        else {
            return false;
        }
    }

    /* PC */
    public long createPC(String modelo, String marca, int ram, String procesador, String so,
                         int hdd, int pantalla, String grafica, String conexiones){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PC_MODELO, modelo);
        initialValues.put(KEY_PC_MARCA, marca);
        initialValues.put(KEY_PC_RAM, ram);
        initialValues.put(KEY_PC_PROCESADOR, procesador);
        initialValues.put(KEY_PC_SO, so);
        initialValues.put(KEY_PC_HDD, hdd);
        initialValues.put(KEY_PC_PANTALLA, pantalla);
        initialValues.put(KEY_PC_GRAFICA, grafica);
        initialValues.put(KEY_PC_CONEXIONES, conexiones);


        return  mDb.insert(DATABASE_TABLE_PC, null, initialValues);
    }

    public boolean deletePC(long rowId) {
        return mDb.delete(DATABASE_TABLE_PC, KEY_PC_ROWID + "=" + rowId, null) > 0;
    }

    public boolean updatePC(long rowId, String modelo, String marca, int ram, String procesador,
                            String so, int hdd, int pantalla, String grafica, String conexiones){
        ContentValues args = new ContentValues();
        args.put(KEY_PC_ROWID, rowId);
        args.put(KEY_PC_MODELO, modelo);
        args.put(KEY_PC_MARCA, marca);
        args.put(KEY_PC_RAM, ram);
        args.put(KEY_PC_PROCESADOR, procesador);
        args.put(KEY_PC_SO, so);
        args.put(KEY_PC_HDD, hdd);
        args.put(KEY_PC_PANTALLA, pantalla);
        args.put(KEY_PC_GRAFICA, grafica);
        args.put(KEY_PC_CONEXIONES, conexiones);

        return mDb.update(DATABASE_TABLE_PC, args, KEY_PC_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor fetchAllPC() {
        return mDb.query(DATABASE_TABLE_PC, new String[]{KEY_PC_ROWID, KEY_PC_MODELO,
                KEY_PC_MARCA, KEY_PC_RAM, KEY_PC_PROCESADOR, KEY_PC_SO, KEY_PC_HDD,
                KEY_PC_PANTALLA, KEY_PC_GRAFICA, KEY_PC_CONEXIONES}, null, null, null,
                null, null);
    }

    public Cursor fetchPC(long rowId) throws SQLException {

        Cursor mCursor =

                mDb.query(true, DATABASE_TABLE_PC, new String[] {KEY_PC_ROWID, KEY_PC_MODELO,
                KEY_PC_MARCA, KEY_PC_RAM, KEY_PC_PROCESADOR, KEY_PC_SO, KEY_PC_HDD,
                                KEY_PC_PANTALLA, KEY_PC_GRAFICA, KEY_PC_CONEXIONES},
                        KEY_PC_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

}