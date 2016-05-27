package es.unizar.eina.allpc;

import android.database.MatrixCursor;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by mario on 13/04/16.
 */
public class ConexionBD {

    private static final String URLGETPCS = "http://allpc.ddns.net/allpc/PCs.php?tabla=PCs";
    private static final String URLGETPCSDESC = "http://allpc.ddns.net/allpc/PCsDesc.php?tabla=PCs";
    private static final String URLGETADMINS = "http://allpc.ddns.net/allpc/Admins.php?tabla=Administradores";
    private static final String URLINSERTPC = "http://allpc.ddns.net/allpc/insertPC.php";
    private static final String URLINSERTADMIN = "http://allpc.ddns.net/allpc/insertAdmin.php";
    private static final String URLUPDATEPC = "http://allpc.ddns.net/allpc/updatePC.php";
    private static final String URLDELETE = "http://allpc.ddns.net/allpc/deletePC.php";
    private static final String DevolverPC = "http://allpc.ddns.net/allpc/devolverPC.php?tabla=PCs&id=";
    private static String DevolverAdmin = "http://allpc.ddns.net/allpc/devolverAdmin.php?tabla=Administradores&id=";

    /**
     * Metodo getPCs
     *
     * Metodo que devuelve un MatrixCursor con todos los PCs almacenados en la BD externa
     *
     * @param orden
     * @return
     */
    public static MatrixCursor getPCs(int orden) {
        class GetPCsJSON extends AsyncTask<String, Void, MatrixCursor> {
            @Override
            protected MatrixCursor doInBackground(String... params) {
                HttpURLConnection con = null;
                String[][] pcs = null;
                MatrixCursor mc = new MatrixCursor(new String[] {
                        "_id", "modelo", "marca", "ram", "procesador", "so",
                        "almacenamiento", "pantalla", "grafica", "conexiones"
                });

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
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        String pcsCod = sb.toString();
                        mc = obtenPcs(pcsCod);
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }finally {
                    if (con != null) con.disconnect();
                }
                return mc;
            }

            protected MatrixCursor obtenPcs(String jsonCodificado){
                //Obtenemos el Objeto a partir del String
                JSONArray pcsCod = null;
                String[][] pcs = null;

                MatrixCursor mc = new MatrixCursor(new String[] {
                        "_id", "modelo", "marca", "ram", "procesador", "so",
                        "almacenamiento", "pantalla", "grafica", "conexiones"
                });

                try {
                    pcsCod = new JSONArray(jsonCodificado);
                    //Obtenemos un array de JSON
                    pcs = new String [pcsCod.length()][10];



                    for (int i = 0; i < pcsCod.length(); i++) {
                        //Como cada elemento del array está en JSON,
                        //obtenemos el objeto JSON correspondiente
                        JSONObject d = pcsCod.getJSONObject(i);
                        mc.addRow(new Object[] {
                                d.getString("_id"), d.getString("modelo"),
                                d.getString("marca"), d.getString("ram"),
                                d.getString("procesador"), d.getString("so"),
                                d.getString("almacenamiento"), d.getString("pantalla"),
                                d.getString("grafica"), d.getString("conexiones")
                        });
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                return mc;
            }
        }

        GetPCsJSON g = new GetPCsJSON();

        if(orden==0){
            g.execute(URLGETPCS);
        }
        else {
            g.execute(URLGETPCSDESC);
        }

        MatrixCursor mc = new MatrixCursor(new String[] {
                "_id", "modelo", "marca", "ram", "procesador", "so",
                "almacenamiento", "pantalla", "grafica", "conexiones"
        });
        try {
            mc = g.get();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }

        return mc;
    }


    /**
     * Metodo getPC
     *
     * Metodo que devuelve un MatrixCursor con la informacion de un PC
     *
     * @param id
     * @return
     */
    public static MatrixCursor getPC(long id) {
        class GetPCsJSON extends AsyncTask<String, Void, MatrixCursor> {
            @Override
            protected MatrixCursor doInBackground(String... params) {
                HttpURLConnection con = null;
                MatrixCursor mc = new MatrixCursor(new String[] {
                        "_id", "modelo", "marca", "ram", "procesador", "so",
                        "almacenamiento", "pantalla", "grafica", "conexiones"
                });
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
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        String pcsCod = sb.toString();
                        mc = obtenPcs(pcsCod);
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }finally {
                    if (con != null) con.disconnect();
                }

                return mc;
            }

            protected MatrixCursor obtenPcs(String jsonCodificado){
                //Obtenemos el Objeto a partir del String
                JSONArray pcsCod = null;

                MatrixCursor mc = new MatrixCursor(new String[] {
                        "_id", "modelo", "marca", "ram", "procesador", "so",
                        "almacenamiento", "pantalla", "grafica", "conexiones"
                });

                try {
                    pcsCod = new JSONArray(jsonCodificado);
                    //Obtenemos un array de JSON
                    JSONObject d = pcsCod.getJSONObject(0);
                    mc.addRow(new Object[] {
                            d.getString("_id"), d.getString("modelo"),
                            d.getString("marca"), d.getString("ram"),
                            d.getString("procesador"), d.getString("so"),
                            d.getString("almacenamiento"), d.getString("pantalla"),
                            d.getString("grafica"), d.getString("conexiones")
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return mc;
            }
        }
        GetPCsJSON g = new GetPCsJSON();
        String consulta = DevolverPC + id;
        g.execute(consulta);
        MatrixCursor mc = new MatrixCursor(new String[] {
                "_id", "modelo", "marca", "ram", "procesador", "so",
                "almacenamiento", "pantalla", "grafica", "conexiones"
        });
        try {
            mc = g.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return mc;
    }


    /**
     * Metodo getAdmins
     *
     * Devuelve una matriz de strings con todos los administradores
     *
     * @return
     */
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
                        while ((line = reader.readLine()) != null) {
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
                        admins[i][1] = d.getString("nombre");
                        admins[i][2] = d.getString("correo");
                        admins[i][3] = d.getString("pass");
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

    /**
     * Metodo insertPC
     *
     * Recibe todos los datos de un PC y los inserta en la base de datos
     *
     * @param modelo
     * @param marca
     * @param ram
     * @param procesador
     * @param so
     * @param almacenamiento
     * @param pantalla
     * @param grafica
     * @param conexiones
     * @return
     */
    public static boolean insertPC(String modelo, String marca, int ram,
                                   String procesador, String so, int almacenamiento,
                                   double pantalla, String grafica, String conexiones) {

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
        try {
            String url = null;
            url = URLINSERTPC + "?modelo="+ URLEncoder.encode(modelo, "UTF-8")+"&marca="
                    +URLEncoder.encode(marca, "UTF-8")+"&ram="
                    +URLEncoder.encode(String.valueOf(ram), "UTF-8")+"&procesador="
                    +URLEncoder.encode(procesador, "UTF-8")+"&so="+URLEncoder.encode(so, "UTF-8")
                    +"&almacenamiento="+URLEncoder.encode(String.valueOf(almacenamiento), "UTF-8")
                    +"&pantalla="+URLEncoder.encode(String.valueOf(pantalla), "UTF-8")+"&grafica="
                    +URLEncoder.encode(grafica, "UTF-8")+"&conexiones="
                    +URLEncoder.encode(conexiones, "UTF-8");
            g.execute(url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        boolean result = false;
        try {
            if (g.get() != null && g.get().compareTo("ok")==0){
                result = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * Metodo updatePC
     *
     * Recibe todos los campos de un PC y actualiza el PC correspondiente en la
     * base de datos
     *
     * @param _id
     * @param modelo
     * @param marca
     * @param ram
     * @param procesador
     * @param so
     * @param almacenamiento
     * @param pantalla
     * @param grafica
     * @param conexiones
     * @return
     */
    public static boolean updatePC(long _id, String modelo, String marca, int ram,
                                   String procesador, String so, int almacenamiento,
                                   double pantalla, String grafica, String conexiones) {

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
        try {
            String url = null;
            url = URLUPDATEPC + "?_id=" + URLEncoder.encode(String.valueOf(_id), "UTF-8")
                    +"&modelo="+ URLEncoder.encode(modelo, "UTF-8")+"&marca="
                    +URLEncoder.encode(marca, "UTF-8")+"&ram="
                    +URLEncoder.encode(String.valueOf(ram), "UTF-8")+"&procesador="
                    +URLEncoder.encode(procesador, "UTF-8")+"&so="+URLEncoder.encode(so, "UTF-8")
                    +"&almacenamiento="+URLEncoder.encode(String.valueOf(almacenamiento), "UTF-8")
                    +"&pantalla="+URLEncoder.encode(String.valueOf(pantalla), "UTF-8")+"&grafica="
                    +URLEncoder.encode(grafica, "UTF-8")+"&conexiones="
                    +URLEncoder.encode(conexiones, "UTF-8");

            g.execute(url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        boolean result = false;
        try {
            if (g.get() != null && g.get().compareTo("ok")==0){
                result = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * Metodo delete
     *
     * Recibe un String con el nombre de la tabla y el id del registro a borrar
     * Sirve para administradores y para PCs
     *
     * @param id
     * @param tabla
     * @return
     */
    public static boolean delete(long id, String tabla) {

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