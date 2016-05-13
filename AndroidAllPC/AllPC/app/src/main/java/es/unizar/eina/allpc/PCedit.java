package es.unizar.eina.allpc;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class PCedit extends AppCompatActivity {

    private EditText mIdText;
    private EditText mModeloText;
    private EditText mMarcaText;
    private Spinner mRamText;
    private EditText mProcesadorText;
    private EditText mSOText;
    private Spinner mHDDText;
    private Spinner mPantallaText;
    private EditText mGraficaText;
    private EditText mConexionesText;

    private Long mRowId;

    private ConexionBD mBd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBd = new ConexionBD();



        setContentView(R.layout.activity_pcedit);
        setTitle(R.string.app_name);

        mIdText = (EditText) findViewById(R.id.id);
        mModeloText = (EditText) findViewById(R.id.modelo);
        mMarcaText = (EditText) findViewById(R.id.marca);
        mRamText = (Spinner) findViewById(R.id.ram);
        mProcesadorText = (EditText) findViewById(R.id.procesador);
        mSOText = (EditText) findViewById(R.id.so);
        mHDDText = (Spinner) findViewById(R.id.hdd);
        mPantallaText = (Spinner) findViewById(R.id.pantalla);
        mGraficaText = (EditText) findViewById(R.id.grafica);
        mConexionesText = (EditText) findViewById(R.id.conexiones);

        Button confirmButton = (Button) findViewById(R.id.confirm);

        mRowId = (savedInstanceState == null) ? null :
                (Long) savedInstanceState.getSerializable(DbAdapter.KEY_PC_ROWID);
        if (mRowId == null) {
            Bundle extras = getIntent().getExtras();
            mRowId = (extras != null) ? extras.getLong(DbAdapter.KEY_PC_ROWID)
                    : null;
        }

        //RELLENAR EL SPINNER RAM---------------------------------------------------
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter.add("1GB");
        adapter.add("2GB");
        adapter.add("4GB");
        adapter.add("8GB");
        adapter.add("16GB");

        mRamText = (Spinner) findViewById(R.id.ram);
        mRamText.setAdapter(adapter);
        //-----------------------------------------------

        //RELLENAR EL SPINNER HDD--------------------------------------------------------
        ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter<CharSequence>(
                this, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter2.add("128GB");
        adapter2.add("256GB");
        adapter2.add("512GB");
        adapter2.add("1TB");
        adapter2.add("2TB");

        mHDDText = (Spinner) findViewById(R.id.hdd);
        mHDDText.setAdapter(adapter2);
        //-----------------------------------------------

        //RELLENAR EL SPINNER PANTALLA---------------------------------------------------------
        ArrayAdapter<CharSequence> adapter3 = new ArrayAdapter<CharSequence>(
                this, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter3.add("10 pulgadas");
        adapter3.add("12 pulgadas");
        adapter3.add("15.7 pulgadas");
        adapter3.add("17 pulgadas");

        mPantallaText = (Spinner) findViewById(R.id.pantalla);
        mPantallaText.setAdapter(adapter3);
        //-----------------------------------------------

        populateFields();
        confirmButton.setOnClickListener(new View.OnClickListener() {

                                             public void onClick(View view) {
                                                 Intent mIntent = new Intent();
                                                 setResult(RESULT_OK, mIntent);
                                                 finish();
                                             }

                                         }
        );
    }


    /**
     * Metodo populateFields
     *
     * Si el Id es distinto de null, recibe de la base de datos
     * los datos del PC con el id recibido
     */
    private void populateFields() {
        if (mRowId != null) {
            Cursor pc = mBd.getPC(mRowId);
            startManagingCursor(pc);
            pc.moveToFirst();

            // Rellenar campos del formulario
            mIdText.setText(pc.getString(
                    pc.getColumnIndexOrThrow("_id")));
            mModeloText.setText(pc.getString(
                    pc.getColumnIndexOrThrow("modelo")));
            mMarcaText.setText(pc.getString(
                    pc.getColumnIndexOrThrow("marca")));
            mRamText.setSelection(spinnerPositionRAM(
                    pc.getString(pc.getColumnIndex("ram"))));
            mProcesadorText.setText(pc.getString(
                    pc.getColumnIndexOrThrow("procesador")));
            mSOText.setText(pc.getString(
                    pc.getColumnIndexOrThrow("so")));
            mHDDText.setSelection(spinnerPositionHDD(
                    pc.getString(pc.getColumnIndex("almacenamiento"))));
            mPantallaText.setSelection(spinnerPositionPantalla(
                    pc.getString(pc.getColumnIndex("pantalla"))));
            mGraficaText.setText(pc.getString(
                    pc.getColumnIndexOrThrow("grafica")));
            mConexionesText.setText(pc.getString(
                    pc.getColumnIndexOrThrow("conexiones")));
        }
    }


    /**
     * Metodo spinnerPositionRAM
     *
     * Recibe el valor de la RAM de un PC y devuelve la posicion del spinner donde
     * esta ese valor
     *
     * @param valor
     * @return
     */
    private int spinnerPositionRAM (String valor){
        switch (valor){
            case "1":
                return 0;
            case "2":
                return 1;
            case "4":
                return 2;
            case "8":
                return 3;
            case "16":
                return 4;
        }
        return 1;
    }

    /**
     * Metodo spinnerPositionHDD
     *
     * Recibe el valor de la HDD de un PC y devuelve la posicion del spinner donde
     * esta ese valor
     *
     * @param valor
     * @return
     */
    private int spinnerPositionHDD (String valor){
        switch (valor){
            case "128":
                return 0;
            case "256":
                return 1;
            case "512":
                return 2;
            case "1024":
                return 3;
            case "2048":
                return 4;
        }
        return 1;
    }


    /**
     * Metodo spinnerPositionPantalla
     *
     * Recibe el valor de la Pantalla de un PC y devuelve la posicion del spinner donde
     * esta ese valor
     *
     * @param valor
     * @return
     */
    private int spinnerPositionPantalla (String valor){
        switch (valor){
            case "10":
                return 0;
            case "12":
                return 1;
            case "15.7":
                return 2;
            case "17":
                return 3;
        }
        return 1;
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
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

    private void saveState() {
        String modelo = mModeloText.getText().toString();
        String marca = mMarcaText.getText().toString();
        int ram = 0;
        switch (mRamText.getSelectedItemPosition()){
            case 0:
                ram = 1;
                break;
            case 1:
                ram = 2;
                break;
            case 2:
                ram = 4;
                break;
            case 3:
                ram = 8;
                break;
            case 4:
                ram = 16;
                break;
        }
        String procesador = mProcesadorText.getText().toString();
        String so = mSOText.getText().toString();
        int hdd = 0;
        switch (mHDDText.getSelectedItemPosition()){
            case 0:
                hdd = 128;
                break;
            case 1:
                hdd = 256;
                break;
            case 2:
                hdd = 512;
                break;
            case 3:
                hdd = 1024;
                break;
            case 4:
                hdd = 2048;
                break;
        }
        double pantalla = 0;
        switch (mPantallaText.getSelectedItemPosition()){
            case 0:
                pantalla = 10;
                break;
            case 1:
                pantalla = 12;
                break;
            case 2:
                pantalla = 15.7;
                break;
            case 3:
                pantalla = 17;
                break;
        }
        String grafica = mGraficaText.getText().toString();
        String conexiones = mConexionesText.getText().toString();

        if (modelo.equals(null) || modelo.equals("")){
            modelo = "NO MODEL";
        }

        if (mRowId == null) {
            mBd.insertPC(modelo, marca, ram, procesador, so, hdd, pantalla, grafica, conexiones);
        }
        else {
            mBd.updatePC(mRowId, modelo, marca, ram, procesador, so, hdd, pantalla, grafica, conexiones);
        }
    }
}
