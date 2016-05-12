package es.unizar.eina.allpc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends AppCompatActivity {

    private EditText mEmailView;
    private EditText mPasswordView;


    /**
     * Metodo onCreate
     *
     * Pagina con 2 campos y un boton de login
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    /**
     * Metodo login
     *
     * Recibe los datos introducidos en el formulario de login
     */
    private void login(){
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        comprobarDatos(email, password);
    }

    /**
     * Metodo comprobarDatos
     *
     * Comprueba con la base de datos el email y pass introducidas
     *
     * @param email
     * @param password
     */
    private void comprobarDatos(String email, String password){
        ConexionBD bd = new ConexionBD();
        String[][] admins = bd.getAdmins();
        boolean login = false;
        for(int i=0; i<admins.length; i++){

            if((email.equals(admins[i][2])) && (password.equals(admins[i][3]))){
                login=true;
                loginCorrecto();
            }
        }
        if(!login){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Error...");
            alertDialog.setMessage("Login Incorrecto");
            alertDialog.setButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.setIcon(R.drawable.default_pc);
            alertDialog.show();
        }
    }

    /**
     * Metodo loginCorrecto
     *
     * Devuelve true al MainActivity para indicar que el login
     * ha sido correcto
     */
    private void loginCorrecto() {
        boolean loginAdmin = true;
        Bundle bundle = new Bundle();
        bundle.putBoolean("LOGIN_ADMIN", loginAdmin);
        Intent mIntent = new Intent();
        mIntent.putExtras(bundle);
        setResult(RESULT_OK, mIntent);
        finish();
    }

    /**
     * Metodo onKeyDown
     *
     * Captura la pulsacion del boton atras
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent mIntent = new Intent();
            setResult(RESULT_OK, mIntent);
            finish();
        }
        return true;
    }
}
