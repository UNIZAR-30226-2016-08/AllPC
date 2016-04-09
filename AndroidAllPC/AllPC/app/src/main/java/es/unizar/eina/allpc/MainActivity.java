package es.unizar.eina.allpc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private static final int ACTIVITY_LOGIN=0;

    private static final int MENU_LOGIN = Menu.FIRST;
    private static final int MENU_COMPARADOR = Menu.FIRST + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("prueba");

        //DBConnection conexionBD = new DBConnection();
        //conexionBD.conectar();

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
