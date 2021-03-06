package es.unizar.eina.allpc;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private static final int ACTIVITY_COMPARADOR=3;
    private static final int ACTIVITY_VIEW=4;

    private static final int MENU_LOGIN = Menu.FIRST;
    private static final int MENU_COMPARADOR = Menu.FIRST + 1;
    private static final int MENU_CREAR_PC = Menu.FIRST + 2;
    private static final int MENU_CERRAR_SESION = Menu.FIRST + 3;
    private static final int MENU_ORDEN_ASC = Menu.FIRST + 4;
    private static final int MENU_ORDEN_DESC = Menu.FIRST + 5;

    private static final int SHOW_ID = Menu.FIRST + 6;
    private static final int DELETE_ID = Menu.FIRST + 7;
    private static final int EDIT_ID = Menu.FIRST + 8;
    private static final int ADD_ID = Menu.FIRST + 9;


    private boolean mLoginAdmin;
    private ListView mLista;
    private ConexionBD mBd;

    private long mid1 = 0;
    private long mid2 = 0;

    private int orden = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Iniciar en modo usuario standart
        mLoginAdmin = false;


        mLista = (ListView)findViewById(R.id.mi_lista);
        fillData(orden);

        mBd = new ConexionBD();
        registerForContextMenu(mLista);
    }

    /**
     * Metodo fillData
     *
     * Recupera de la base de datos todos los PC y los mete en una lista
     *
     */
    private void fillData(int orden){
        Cursor mCursorPCs = mBd.getPCs(orden);
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
            menu.add(Menu.NONE, MENU_CREAR_PC, Menu.NONE, R.string.menu_crearPC);
            menu.add(Menu.NONE, MENU_CERRAR_SESION, Menu.NONE, R.string.menu_logout);
        }
        else{
            menu.add(Menu.NONE, MENU_LOGIN, Menu.NONE, R.string.menu_login);
            menu.add(Menu.NONE, MENU_COMPARADOR, Menu.NONE, R.string.menu_comparador);
        }
        menu.add(Menu.NONE, MENU_ORDEN_ASC, Menu.NONE, R.string.menu_order_asc);
        menu.add(Menu.NONE, MENU_ORDEN_DESC, Menu.NONE, R.string.menu_order_desc);
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
            menu.add(Menu.NONE, MENU_CREAR_PC, Menu.NONE, R.string.menu_crearPC);
            menu.add(Menu.NONE, MENU_CERRAR_SESION, Menu.NONE, R.string.menu_logout);
        }
        else{
            menu.add(Menu.NONE, MENU_LOGIN, Menu.NONE, R.string.menu_login);
            menu.add(Menu.NONE, MENU_COMPARADOR, Menu.NONE, R.string.menu_comparador);
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
                verComparador(mid1, mid2);
                return true;
            case MENU_CREAR_PC:
                crearPC();
                return true;
            case MENU_CERRAR_SESION:
                cerrarSesion();
                return true;
            case MENU_ORDEN_ASC:
                orden=0;
                fillData(orden);
                return true;
            case MENU_ORDEN_DESC:
                orden=1;
                fillData(orden);
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
        menu.add(Menu.NONE, SHOW_ID, Menu.NONE, R.string.menu_ver);
        if(mLoginAdmin) {
            menu.add(Menu.NONE, DELETE_ID, Menu.NONE, R.string.menu_borrar);
            menu.add(Menu.NONE, EDIT_ID, Menu.NONE, R.string.menu_editar);
        }
        else{
            menu.add(Menu.NONE, ADD_ID, Menu.NONE, R.string.menu_compare);
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
        AdapterView.AdapterContextMenuInfo info;
        switch(item.getItemId()) {
            case DELETE_ID:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                mBd.delete(info.id, "PCs");
                fillData(orden);
                return true;

            case EDIT_ID:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                editPc(info.position, info.id);
                return true;

            case SHOW_ID:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                verPC(info.id);
                return true;

            case ADD_ID:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                anyadirComparador(info.id);
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
     * Metodo editPc
     *
     * Recibe el id del PC a editar y crea un activity PCedit
     *
     * @param position
     * @param id
     */
    protected void editPc(int position, long id) {
        Intent i = new Intent(this, PCedit.class);
        i.putExtra("_id", id);
        startActivityForResult(i, ACTIVITY_EDIT);
    }

    /**
     * Metodo verPC
     *
     * Recibe el id del PC que se desea visualizar
     *
     * @param id
     */
    private void verPC(long id){
        Intent i = new Intent(this, PcView.class);
        i.putExtra("_id", id);
        startActivityForResult(i, ACTIVITY_VIEW);
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
     * Metodo verComparador
     *
     * Crea un activity PcComparator
     */
    private void verComparador(long id1, long id2) {
        if(mid1==0 || mid2==0){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Aviso");
            alertDialog.setMessage("Debes seleccionar al menos 2 PC");
            alertDialog.setButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.setIcon(R.drawable.logo_fondo);
            alertDialog.show();
        }
        else {
            Intent i = new Intent(this, PcComparator.class);
            i.putExtra("id1", id1);
            i.putExtra("id2", id2);
            startActivityForResult(i, ACTIVITY_COMPARADOR);
        }
    }

    /**
     * Metodo anyadirComparador
     *
     * Recibe un ID y modifica la variable midx correspondiente
     * para mostrar el PC en el comparador.
     *
     * @param id
     */
    private void anyadirComparador(long id){
        if(mid1==0){
            mid1 = id;
        }
        else{
            mid2 = mid1;
            mid1 = id;
        }
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
            fillData(orden);
        }
    }
}
