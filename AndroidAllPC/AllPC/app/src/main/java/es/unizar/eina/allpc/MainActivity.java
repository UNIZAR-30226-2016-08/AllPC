package es.unizar.eina.allpc;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends AppCompatActivity {

    private static final int ACTIVITY_LOGIN=0;
    private static final int ACTIVITY_CREATE=1;
    private static final int ACTIVITY_EDIT=2;

    private static final int MENU_LOGIN = Menu.FIRST;
    private static final int MENU_COMPARADOR = Menu.FIRST + 1;
    private static final int MENU_CREAR_PC = Menu.FIRST + 2;
    private static final int MENU_CERRAR_SESION = Menu.FIRST + 3;

    private static final int SHOW_ID = Menu.FIRST + 4;
    private static final int DELETE_ID = Menu.FIRST + 5;
    private static final int EDIT_ID = Menu.FIRST + 6;


    private boolean mLoginAdmin;
    private ListView mLista;
    private ConexionBD mBd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Iniciar en modo usuario standart
        mLoginAdmin = false;


        mLista = (ListView)findViewById(R.id.mi_lista);
        fillData();

        mBd = new ConexionBD();
        registerForContextMenu(mLista);
    }

    /**
     * Metodo fillData
     *
     * Recupera de la base de datos todos los PC y los mete en una lista
     *
     */
    private void fillData(){
        Cursor mCursorPCs = mBd.getPCs();
        startManagingCursor(mCursorPCs);

        String[] from = new String[] { "modelo",
                "marca"};
        int[] to = new int[] { R.id.texto_principal, R.id.texto_secundario};

        SimpleCursorAdapter pc =
                new SimpleCursorAdapter(this, R.layout.fila_lista, mCursorPCs, from, to);

        mLista.setAdapter(pc);
    }

    /**
     * Metodo onPrepareOptionsMenu
     *
     * Actualiza el menu si se cierra sesion o si se hace login
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if(mLoginAdmin){
            menu.add(Menu.NONE, MENU_CREAR_PC, Menu.NONE, "Crear PC");
            menu.add(Menu.NONE, MENU_CERRAR_SESION, Menu.NONE, "Cerrar Sesion");
        }
        else{
            menu.add(Menu.NONE, MENU_LOGIN, Menu.NONE, "Login");
            menu.add(Menu.NONE, MENU_COMPARADOR, Menu.NONE, "Comparador");
        }
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Metodo onCreateOptionsMenu
     *
     * Crea un menu al inicio del activity
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);

        if(mLoginAdmin){
            menu.add(Menu.NONE, MENU_CREAR_PC, Menu.NONE, "Crear PC");
            menu.add(Menu.NONE, MENU_CERRAR_SESION, Menu.NONE, "Cerrar Sesion");
        }
        else{
            menu.add(Menu.NONE, MENU_LOGIN, Menu.NONE, "Login");
            menu.add(Menu.NONE, MENU_COMPARADOR, Menu.NONE, "Comparador");
        }

        return result;
    }

    /**
     * Metodo onOptionsItemSelected
     *
     * Recibe la opcion del menu pulsada y ejecuta el codigo correspondiente
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_LOGIN:
                login();
                return true;
            case MENU_COMPARADOR:
                return true;
            case MENU_CREAR_PC:
                crearPC();
                return true;
            case MENU_CERRAR_SESION:
                cerrarSesion();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodo onCreateContextMenu
     *
     * Crea un menu contextual cuando se mantiene el dedo pulsado
     * sobre un PC
     *
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, SHOW_ID, Menu.NONE, "Ver PC");
        if(mLoginAdmin) {
            menu.add(Menu.NONE, DELETE_ID, Menu.NONE, "Borrar PC");
            menu.add(Menu.NONE, EDIT_ID, Menu.NONE, "Editar PC");
        }
    }


    /**
     * Metodo onContextItemSelected
     *
     * Recibe la opcion pulsada en el menu contextual y ejecuta el codigo
     * correspondiente
     *
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //AdapterView.AdapterContextMenuInfo uNote = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo(); //p6
        switch(item.getItemId()) {
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                mBd.delete(info.id, "PCs");
                fillData();
                return true;

            case EDIT_ID:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                editNote(info.position, info.id);
                return true;

            case SHOW_ID:
                return true;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * Metodo crearPC
     *
     * Crea un activity PCEdit
     */
    private void crearPC(){
        Intent i = new Intent(this, PCedit.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }

    /**
     * Metodo editNote
     *
     * Recibe el id del PC a editar y crea un activity PCedit
     *
     * @param position
     * @param id
     */
    protected void editNote(int position, long id) {
        Intent i = new Intent(this, PCedit.class);
        i.putExtra("_id", id);
        startActivityForResult(i, ACTIVITY_EDIT);
    }

    /**
     * Metodo cerrarSesion
     *
     * Cambia la variable mLoginAdmin a false para bloquear las opciones
     * de administrador
     */
    private void cerrarSesion(){
        mLoginAdmin=false;
    }

    /**
     * Metodo login
     *
     * Crea un activity LoginActivity
     */
    private void login(){
        Intent i = new Intent(this, LoginActivity.class);
        startActivityForResult(i, ACTIVITY_LOGIN);
    }

    /**
     * Metodo onActivityResult
     *
     * Recibe un booleano que indica si el usuario se ha registrado como
     * administrador o no
     *
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(intent.hasExtra("LOGIN_ADMIN")){
            Bundle extras = intent.getExtras();
            mLoginAdmin = extras.getBoolean("LOGIN_ADMIN");
        }
        else{
            fillData();
        }
    }
}
