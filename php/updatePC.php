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
$modelo = $_GET["modelo"];
$marca = $_GET["marca"];
$ram = $_GET["ram"];
$procesador = $_GET["procesador"];
$so = $_GET["so"];
$almacenamiento = $_GET["almacenamiento"];
$pantalla = $_GET["pantalla"];
$grafica = $_GET["grafica"];
$conexiones = $_GET["conexiones"];

$resultado = mysql_query("UPDATE PCs SET modelo = \"$modelo\", marca = \"$marca\", ram = $ram, procesador = \"$procesador\", so = \"$so\", almacenamiento = $almacenamiento, pantalla = $pantalla, grafica = \"$grafica\", conexiones = \"$conexiones\" WHERE _id=$id");
if(!$resultado)
die("Fallo el comando:".mysql_error());
else
echo mysql_affected_rows()."Filas afectadas";



//http://allpcserver.ddns.net/AllPC/updatePC.php?_id=3&modelo=iee&marca=iee&ram=3&procesador=iee&so=iee&almacenamiento=100&pantalla=15.5&grafica=iee&conexiones=iee
    
mysql_close();
?>
