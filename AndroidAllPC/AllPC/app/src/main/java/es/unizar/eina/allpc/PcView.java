package es.unizar.eina.allpc;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TextView;

/**
 * Created by Adrian on 20/05/2016.
 */
public class PcView extends AppCompatActivity{
    private long mId1;
    private TextView mModelo1Text;
    private TextView mMarca1Text;
    private TextView mRam1Text;
    private TextView mProcesador1Text;
    private TextView mSO1Text;
    private TextView mHDD1Text;
    private TextView mPantalla1Text;
    private TextView mGrafica1Text;
    private TextView mConexiones1Text;

    private ConexionBD mBd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBd = new ConexionBD();


        setContentView(R.layout.activity_pc_view);
        setTitle(R.string.app_name);

        mModelo1Text = (TextView) findViewById(R.id.modelo1);
        mMarca1Text = (TextView) findViewById(R.id.marca1);
        mRam1Text = (TextView) findViewById(R.id.ram1);
        mProcesador1Text = (TextView) findViewById(R.id.procesador1);
        mSO1Text = (TextView) findViewById(R.id.so1);
        mHDD1Text = (TextView) findViewById(R.id.hdd1);
        mPantalla1Text = (TextView) findViewById(R.id.pantalla1);
        mGrafica1Text = (TextView) findViewById(R.id.grafica1);
        mConexiones1Text = (TextView) findViewById(R.id.conexiones1);

        mId1 = (savedInstanceState == null) ? -1 :
                (Long) savedInstanceState.get("_id");
        if (mId1 == -1) {
            Bundle extras = getIntent().getExtras();
            mId1 = (extras != null) ? extras.getLong("_id")
                    : null;
        }

        populateFields();
    }


    /**
     * Metodo populateFields
     *
     * Si el Id es distinto de null, recibe de la base de datos
     * los datos del PC con el id recibido
     */
    private void populateFields() {
        Cursor pc = mBd.getPC(mId1);
        startManagingCursor(pc);
        pc.moveToFirst();

        // Rellenar campos del formulario
        mModelo1Text.setText(pc.getString(
                pc.getColumnIndexOrThrow("modelo")));
        mMarca1Text.setText(pc.getString(
                pc.getColumnIndexOrThrow("marca")));
        mRam1Text.setText(pc.getString(
                pc.getColumnIndexOrThrow("ram")));
        mProcesador1Text.setText(pc.getString(
                pc.getColumnIndexOrThrow("procesador")));
        mSO1Text.setText(pc.getString(
                pc.getColumnIndexOrThrow("so")));
        mHDD1Text.setText(pc.getString(
                pc.getColumnIndexOrThrow("almacenamiento")));
        mPantalla1Text.setText(pc.getString(
                pc.getColumnIndexOrThrow("pantalla")));
        mGrafica1Text.setText(pc.getString(
                pc.getColumnIndexOrThrow("grafica")));
        mConexiones1Text.setText(pc.getString(
                pc.getColumnIndexOrThrow("conexiones")));
    }


    @Override
    protected void onPause() {
        super.onPause();
        Intent mIntent = new Intent();
        setResult(RESULT_OK, mIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
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
