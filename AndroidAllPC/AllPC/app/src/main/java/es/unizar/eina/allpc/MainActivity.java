package es.unizar.eina.allpc;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int ACTIVITY_LOGIN=0;

    private static final int MENU_LOGIN = Menu.FIRST;
    private static final int MENU_COMPARADOR = Menu.FIRST + 1;
    private static final int MENU_CREAR_PC = Menu.FIRST + 2;
    private static final int MENU_CERRAR_SESION = Menu.FIRST + 3;

    private static final int SHOW_ID = Menu.FIRST + 4;
    private static final int DELETE_ID = Menu.FIRST + 5;
    private static final int EDIT_ID = Menu.FIRST + 6;


    private boolean loginAdmin;
    /* LISTA DE PC */
    //----------------------------------------------------------------------------------------
    private String listaPC[]=new String[]{"PC1","PC2","PC3","PC4","PC5","PC6",
            "PC7","PC8","PC9"};

    private Integer[] imgid={
            R.drawable.default_pc,
            R.drawable.default_pc,
            R.drawable.default_pc,
            R.drawable.default_pc,
            R.drawable.default_pc,
            R.drawable.default_pc,
            R.drawable.default_pc,
            R.drawable.default_pc,
            R.drawable.default_pc
    };

    private ListView lista;
    //----------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("prueba");
        loginAdmin = false;
        DBConnection conexionBD = new DBConnection();
        conexionBD.conectar();

        /* LISTA DE PC */
        //----------------------------------------------------------------------------------------
        PCListAdapter adapter=new PCListAdapter(this, listaPC,imgid);
        lista=(ListView)findViewById(R.id.mi_lista);
        lista.setAdapter(adapter);

        /* ACCION AL PULSAR UN ELEMENTO
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Slecteditem= listaPC[+position];
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
            }
        });*/
        registerForContextMenu(lista);
        //----------------------------------------------------------------------------------------




    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if(loginAdmin){
            menu.add(Menu.NONE, MENU_CREAR_PC, Menu.NONE, "Crear PC");
            menu.add(Menu.NONE, MENU_CERRAR_SESION, Menu.NONE, "Cerrar Sesion");
        }
        else{
            menu.add(Menu.NONE, MENU_LOGIN, Menu.NONE, "Login");
            menu.add(Menu.NONE, MENU_COMPARADOR, Menu.NONE, "Comparador");
        }

        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);

        if(loginAdmin){
            menu.add(Menu.NONE, MENU_CREAR_PC, Menu.NONE, "Crear PC");
            menu.add(Menu.NONE, MENU_CERRAR_SESION, Menu.NONE, "Cerrar Sesion");
        }
        else{
            menu.add(Menu.NONE, MENU_LOGIN, Menu.NONE, "Login");
            menu.add(Menu.NONE, MENU_COMPARADOR, Menu.NONE, "Comparador");
        }

        return result;
    }

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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, SHOW_ID, Menu.NONE, "Ver PC");
        if(loginAdmin) {
            menu.add(Menu.NONE, DELETE_ID, Menu.NONE, "Borrar PC");
            menu.add(Menu.NONE, EDIT_ID, Menu.NONE, "Editar PC");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo uNote = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo(); //p6
        switch(item.getItemId()) {
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                /*CODIGO PARA BORRAR*/
                System.out.println("BORRAR PC " + info.position);
                return true;

            case EDIT_ID:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                /*CODIGO PARA EDITAR*/
                System.out.println("EDITAR PC " + info.position);
                return true;
        }
        return super.onContextItemSelected(item);
    }


    private void crearPC(){
    }

    private void cerrarSesion(){
        System.out.println("CERRAR SESION");
        loginAdmin=false;
    }

    private void login(){
        Intent i = new Intent(this, LoginActivity.class);
        startActivityForResult(i, ACTIVITY_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(intent.hasExtra("LOGIN_ADMIN")){
            Bundle extras = intent.getExtras();
            loginAdmin = extras.getBoolean("LOGIN_ADMIN");
            if(loginAdmin){
                System.out.println("LOGEADO COMO ADMIN");
            }
            else{
                System.out.println("NO LOGEADO COMO ADMIN");
            }
        }
        else{
            System.out.println("NADAA");
        }

    }
}
