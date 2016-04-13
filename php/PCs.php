<?php
//MySQL clásico
require_once 'mysql-login.php';
//Conectando
$con = mysql_connect($hostname, $username, $password);
//Manejo de errores
if (!$con)
die("Falló la conexión a MySQL: " . mysql_error());
//Seleccionar base de datos
mysql_select_db($database)
or die("Seleccion de base de datos fallida " . mysql_error());

$tabla = $_GET["tabla"];

if ($tabla == "Administradores"){
    $query = "SELECT * FROM $tabla";
    $resultado = mysql_query($query);
    if(!$resultado)
        die("Fallo el comando:".mysql_error());
    else{
        //print("<table>");
        //while($rows = mysql_fetch_array($resultado,MYSQL_ASSOC)){
          //  print("<tr>");
//            print("<td>".$rows["_id"]."</td>");
//            print("<td>".$rows["nombre"]."</td>");
  //          print("<td>".$rows["correo"]."</td>");
    //        print("<td>".$rows["password"]."</td>");
      //      print("</tr>");
        //}
        //print("</table>");
        print(json_encode(mysql_fetch_array($resultado,MYSQL_ASSOC)));
    }
    mysql_free_result($resultado);
    
}elseif($tabla == "PCs"){
    $query = "SELECT * FROM $tabla";
    $resultado = mysql_query($query);
    if(!$resultado)
        die("Fallo el comando:".mysql_error());
    else{
        print("<table>");
        while($rows = mysql_fetch_array($resultado,MYSQL_ASSOC)){
            print("<tr>");
            print("<td>".$rows["_id"]."</td>");
            print("<td>".$rows["Modelo"]."</td>");
            print("<td>".$rows["Marca"]."</td>");
            print("<td>".$rows["RAM"]."</td>");
            print("<td>".$rows["Procesador"]."</td>");
            print("<td>".$rows["SO"]."</td>");
            print("<td>".$rows["Almacenamiento"]."</td>");
            print("<td>".$rows["Pantalla"]."</td>");
            print("<td>".$rows["Grafica"]."</td>");
            print("<td>".$rows["Conexiones"]."</td>");
            print("</tr>");
        }
        print("</table>");
    }

    mysql_free_result($resultado);
}
mysql_close();
?>
