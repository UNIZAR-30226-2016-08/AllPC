package es.unizar.eina.allpc;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by mario on 13/04/16.
 */
public class ConexionBD {

    public static String[][] getPCs(String url) {

        class GetPCsJSON extends AsyncTask<String, Void, String[][]> {

            @Override
            protected String[][] doInBackground(String... params) {

                HttpURLConnection con = null;
                String[][] pcs = null;

                try {
                    URL url = new URL(params[0]);
                    con = (HttpURLConnection) url.openConnection();

                    // Obtener el estado del recurso
                    int statusCode = con.getResponseCode();

                    if(statusCode==200) {

                        InputStream in = new BufferedInputStream(con.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"), 8);
                        StringBuilder sb = new StringBuilder();

                        String line = null;
                        while ((line = reader.readLine()) != null)
                        {
                            sb.append(line + "\n");
                        }
                        String pcsCod = sb.toString();
                        pcs = obtenPcs(pcsCod);
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }finally {
                    if (con != null) con.disconnect();
                }
                return pcs;
            }

            protected String[][] obtenPcs(String jsonCodificado){
                //Obtenemos el Objeto a partir del String
                JSONArray pcsCod = null;
                String[][] pcs = null;
                try {
                    pcsCod = (JSONArray) new JSONTokener(jsonCodificado).nextValue();
                    //Obtenemos un array de JSON
                    pcs = new String [pcsCod.length()][10];
                    for (int i = 0; i < pcsCod.length(); i++) {
                        //Como cada elemento del array está en JSON,
                        //obtenemos el objeto JSON correspondiente
                        JSONObject d = pcsCod.getJSONObject(i);
                        //Obtenemos los datos del susodicho
                        pcs[i][0] = d.getString("_id");
                        pcs[i][1] = d.getString("Modelo");
                        pcs[i][2] = d.getString("Marca");
                        pcs[i][3] = d.getString("RAM");
                        pcs[i][4] = d.getString("Procesador");
                        pcs[i][5] = d.getString("SO");
                        pcs[i][6] = d.getString("Almacenamiento");
                        pcs[i][7] = d.getString("Pantalla");
                        pcs[i][8] = d.getString("Grafica");
                        pcs[i][9] = d.getString("Conexiones");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return pcs;
            }
        }
        GetPCsJSON g = new GetPCsJSON();
        g.execute(url);
        String[][] pcs = new String[1][1];
        pcs[0][0] = "iee";
        try {
            pcs = g.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < pcs.length; i++){
            for (int j = 0; j < pcs[i].length; j++){
                System.out.println(pcs[i][j]);
            }
        }
        return pcs;
    }
}