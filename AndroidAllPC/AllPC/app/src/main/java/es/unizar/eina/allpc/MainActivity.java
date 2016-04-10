package es.unizar.eina.allpc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    /* LISTA DE PC */
    //----------------------------------------------------------------------------------------
    private String lenguajeProgramacion[]=new String[]{"PC1","PC2","PC3","PC4","PC5","PC6",
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

        //DBConnection conexionBD = new DBConnection();
        //conexionBD.conectar();

        /* LISTA DE PC */
        //----------------------------------------------------------------------------------------
        LenguajeListAdapter adapter=new LenguajeListAdapter(this,lenguajeProgramacion,imgid);
        lista=(ListView)findViewById(R.id.mi_lista);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Slecteditem= lenguajeProgramacion[+position];
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
            }
        });
        //----------------------------------------------------------------------------------------

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);

        menu.add(Menu.NONE, MENU_LOGIN, Menu.NONE, "Login");
        menu.add(Menu.NONE, MENU_COMPARADOR, Menu.NONE, "Comparador");

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
        }
        return super.onOptionsItemSelected(item);
    }


    private void login(){
        Intent i = new Intent(this, LoginActivity.class);
        startActivityForResult(i, ACTIVITY_LOGIN);
    }
}
