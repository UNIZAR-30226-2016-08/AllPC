package es.unizar.eina.allpc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmailView;
    private EditText mPasswordView;

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

    private void login(){
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        System.out.println("LOGIN------> "  + email + " - " + password);

        comprobarDatos(email, password);
    }

    private void comprobarDatos(String email, String password){
        //Añadir comprobacion con la base de datos

        loginCorrecto();
    }

    private void loginCorrecto() {
        boolean loginAdmin = true;
        Bundle bundle = new Bundle();
        bundle.putBoolean("LOGIN_ADMIN", loginAdmin);
        Intent mIntent = new Intent();
        mIntent.putExtras(bundle);
        setResult(RESULT_OK, mIntent);
        finish();
    }

    //Capturar pulsacion del boton atras
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
