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
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Switch;

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

    private EditText mBodyText;
    private Long mRowId;

    private DbAdapter mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //p3
        mDbHelper = new DbAdapter(this);
        mDbHelper.open();




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


    private void populateFields() {
        if (mRowId != null) {
            //Cursor pc = mDbHelper.fetchPC(mRowId);
            ConexionBD bd = new ConexionBD();
            Cursor pc = bd.getPC(mRowId);
            startManagingCursor(pc);
            /*mIdText.setText(pc.getString(
                    pc.getColumnIndexOrThrow(DbAdapter.KEY_PC_ROWID)));
            mModeloText.setText(pc.getString(
                    pc.getColumnIndexOrThrow(DbAdapter.KEY_PC_MODELO)));
            mMarcaText.setText(pc.getString(
                    pc.getColumnIndexOrThrow(DbAdapter.KEY_PC_MARCA)));
            mRamText.setSelection(spinnerPositionRAM(
                    pc.getString(pc.getColumnIndex(DbAdapter.KEY_PC_RAM))));
            mProcesadorText.setText(pc.getString(
                    pc.getColumnIndexOrThrow(DbAdapter.KEY_PC_PROCESADOR)));
            mSOText.setText(pc.getString(
                    pc.getColumnIndexOrThrow(DbAdapter.KEY_PC_SO)));
            mHDDText.setSelection(1);
            mPantallaText.setSelection(1);
            mGraficaText.setText(pc.getString(
                    pc.getColumnIndexOrThrow(DbAdapter.KEY_PC_GRAFICA)));
            mConexionesText.setText(pc.getString(
                    pc.getColumnIndexOrThrow(DbAdapter.KEY_PC_CONEXIONES)));*/
            System.out.println("------------------------------");
            pc.moveToFirst();
            /*String[] a = pc.getColumnNames();
            System.out.println(a[0] + " " + a[1]);
            System.out.println(pc.getColumnIndex(a[0]));
            System.out.println(pc.getString(pc.getColumnIndex(a[2])));
            System.out.println("------------------------------");*/
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
            mHDDText.setSelection(1);
            mPantallaText.setSelection(1);
            mGraficaText.setText(pc.getString(
                    pc.getColumnIndexOrThrow("grafica")));
            mConexionesText.setText(pc.getString(
                    pc.getColumnIndexOrThrow("conexiones")));
        }
    }

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putSerializable(DbAdapter.KEY_PC_ROWID, mRowId);
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
            //long id = mDbHelper.createPC(modelo, marca, ram, procesador, so, hdd, pantalla, grafica, conexiones);
            ConexionBD bd= new ConexionBD();
            bd.insertPC(modelo, marca, ram, procesador, so, hdd, pantalla, grafica, conexiones);
            /*if (id > 0) {
                mRowId = id;
            }*/
        }
        else {
            //mDbHelper.updatePC(mRowId, modelo, marca, ram, procesador, so, hdd, pantalla, grafica, conexiones);
            ConexionBD bd = new ConexionBD();
            System.out.println("ACTUALIZAR PC");
            bd.updatePC(mRowId, modelo, marca, ram, procesador, so, hdd, pantalla, grafica, conexiones);
            System.out.println("ACTUALIZAR PC 2");
        }
    }
}
