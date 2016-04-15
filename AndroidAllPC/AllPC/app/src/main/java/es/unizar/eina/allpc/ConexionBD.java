package es.unizar.eina.allpc;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by mario on 13/04/16.
 */
public class ConexionBD {

    private static final String URLGETPCS = "http://allpcserver.ddns.net/AllPC/PCs.php?tabla=PCs";
    private static final String URLGETADMINS = "http://allpcserver.ddns.net/AllPC/PCs.php?tabla=Administradores";
    private static final String URLINSERTPC = "http://allpcserver.ddns.net/AllPC/insertPC.php";
    private static final String URLINSERTADMIN = "http://allpcserver.ddns.net/AllPC/insertAdmin.php";
    private static final String URLUPDATEPC = "http://allpcserver.ddns.net/AllPC/updatePC.php";
    private static final String URLDELETE = "http://allpcserver.ddns.net/AllPC/deletePC.php";

    public static String[][] getPCs() {

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
                        BufferedReader reader = new BufferedReader
                                (new InputStreamReader(in, "UTF-8"), 8);
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
                    pcsCod = new JSONArray(jsonCodificado);
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
        g.execute(URLGETPCS);
        String[][] pcs = new String[1][1];
        pcs[0][0] = "ERROR!!!!";
        try {
            pcs = g.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return pcs;
    }

    public static String[][] getAdmins() {

        class GetAdminsJSON extends AsyncTask<String, Void, String[][]> {

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
                        BufferedReader reader = new BufferedReader
                                (new InputStreamReader(in, "UTF-8"), 8);
                        StringBuilder sb = new StringBuilder();

                        String line = null;
                        while ((line = reader.readLine()) != null)
                        {
                            sb.append(line + "\n");
                        }
                        String adminsCod = sb.toString();
                        pcs = obtenAdmins(adminsCod);
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }finally {
                    if (con != null) con.disconnect();
                }
                return pcs;
            }

            protected String[][] obtenAdmins(String jsonCodificado){
                //Obtenemos el Objeto a partir del String
                JSONArray adminsCod = null;
                String[][] admins = null;
                try {
                    adminsCod = new JSONArray(jsonCodificado);
                    //Obtenemos un array de JSON
                    admins = new String [adminsCod.length()][4];
                    for (int i = 0; i < adminsCod.length(); i++) {
                        //Como cada elemento del array está en JSON,
                        //obtenemos el objeto JSON correspondiente
                        JSONObject d = adminsCod.getJSONObject(i);
                        //Obtenemos los datos del susodicho
                        admins[i][0] = d.getString("_id");
                        admins[i][1] = d.getString("correo");
                        admins[i][2] = d.getString("nombre");
                        admins[i][3] = d.getString("password");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return admins;
            }
        }
        GetAdminsJSON g = new GetAdminsJSON();
        g.execute(URLGETADMINS);
        String[][] admins = new String[1][1];
        admins[0][0] = "ERROR!!!!";
        try {
            admins = g.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return admins;
    }

    public static boolean insertPC(String modelo, String marca,String ram,
                                   String procesador, String so, String almacenamiento,
                                   String pantalla, String grafica, String conexiones) {

        class InsertPCsJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                HttpURLConnection con = null;
                String result = "error";
                try {
                    URL url = new URL(params[0]);
                    con = (HttpURLConnection) url.openConnection();

                    // Obtener el estado del recurso
                    int statusCode = con.getResponseCode();

                    if(statusCode==200) {
                        result = "ok";
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }finally {
                    if (con != null) con.disconnect();
                }
                return result;
            }
        }
        InsertPCsJSON g = new InsertPCsJSON();
        String url = URLINSERTPC + "?modelo="+modelo+"&marca="+marca+"&ram="+ram+"&procesador="+procesador+"&so="+so+"&almacenamiento="+almacenamiento+"&pantalla="+pantalla+"&grafica="+grafica+"&conexiones="+conexiones;
        g.execute(url);
        boolean result = false;
        try {
            if (g.get().compareTo("ok")==0){
                result = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean insertAdmin(String nombre, String correo,String pass) {

        class InsertAdminsJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                HttpURLConnection con = null;
                String result = "error";
                try {
                    URL url = new URL(params[0]);
                    con = (HttpURLConnection) url.openConnection();

                    // Obtener el estado del recurso
                    int statusCode = con.getResponseCode();

                    if(statusCode==200) {
                        result = "ok";
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }finally {
                    if (con != null) con.disconnect();
                }
                return result;
            }
        }
        InsertAdminsJSON g = new InsertAdminsJSON();
        String url = URLINSERTADMIN + "?nombre="+nombre+"&correo="+correo+"&pass="+pass;
        g.execute(url);
        boolean result = false;
        try {
            if (g.get().compareTo("ok")==0){
                result = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean updatePC(String _id, String modelo, String marca,String ram,
                                   String procesador, String so, String almacenamiento,
                                   String pantalla, String grafica, String conexiones) {

        class UpdatePCsJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                HttpURLConnection con = null;
                String result = "error";
                try {
                    URL url = new URL(params[0]);
                    con = (HttpURLConnection) url.openConnection();

                    // Obtener el estado del recurso
                    int statusCode = con.getResponseCode();

                    if(statusCode==200) {
                        result = "ok";
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }finally {
                    if (con != null) con.disconnect();
                }
                return result;
            }
        }
        UpdatePCsJSON g = new UpdatePCsJSON();
        String url = URLUPDATEPC+"?_id="+_id+"&modelo="+modelo+"&marca="+marca+"&ram="+ram+"&procesador="+procesador+"&so="+so+"&almacenamiento="+almacenamiento+"&pantalla="+pantalla+"&grafica="+grafica+"&conexiones="+conexiones;
        g.execute(url);
        boolean result = false;
        try {
            if (g.get().compareTo("ok")==0){
                result = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean delete(String id, String tabla) {

        class DeleteJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                HttpURLConnection con = null;
                String result = "error";
                try {
                    URL url = new URL(params[0]);
                    con = (HttpURLConnection) url.openConnection();

                    // Obtener el estado del recurso
                    int statusCode = con.getResponseCode();

                    if(statusCode==200) {
                        result = "ok";
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }finally {
                    if (con != null) con.disconnect();
                }
                return result;
            }
        }
        DeleteJSON g = new DeleteJSON();
        String url = URLDELETE + "?id="+id+"&tabla="+tabla;
        g.execute(url);
        boolean result = false;
        try {
            if (g.get().compareTo("ok")==0){
                result = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }
}