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


$id = $_GET["_id"];
$tabla = $_GET["tabla"];

$resultado = mysql_query("DELETE FROM $tabla WHERE _id=$id");
if(!$resultado)
die("Fallo el comando:".mysql_error());
else
echo mysql_affected_rows()."Filas afectadas";



//http://allpcserver.ddns.net/AllPC/deletePC.php?_id=3&tabla=PCs
    
mysql_close();
?>
