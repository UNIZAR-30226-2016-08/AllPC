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

$query = 'PREPARE sentencia FROM "INSERT INTO PCs VALUES(NULL,?,?,?,?,?,?,?,?,?)"';
$resultado = mysql_query($query);
if(!$resultado)
die("Fallo el comando:".mysql_error());
else{

$modelo = $_GET["modelo"];
$marca = $_GET["marca"];
$ram = $_GET["ram"];
$procesador = $_GET["procesador"];
$so = $_GET["so"];
$almacenamiento = $_GET["almacenamiento"];
$pantalla = $_GET["pantalla"];
$grafica = $_GET["grafica"];
$conexiones = $_GET["conexiones"];

$query ='SET @modelo = "'.$modelo.'"'.', @marca = "'.$marca.'"'.', @ram ='.$ram.', @procesador= "'.$procesador.'"'.', @so= "'.$so.'"'.', @almacenamiento= '.$almacenamiento.', @pantalla= '.$pantalla.', @grafica= "'.$grafica.'"'.', @conexiones= "'.$conexiones.'"';
if(!mysql_query($query))
die("Error en SET: ".mysql_error());
$query = 'EXECUTE sentencia USING @modelo,@marca,@ram,@procesador,@so,@almacenamiento,@pantalla,@grafica,@conexiones';
if(!mysql_query($query))
die("Error en EXECUTE:".mysql_error());
$query = 'DEALLOCATE PREPARE sentencia';
if(!mysql_query($query)){
    die("Error en DEALLOCATE:".mysql_error());
}else {
    print("ok");
}
}

//http://allpcserver.ddns.net/AllPC/insertPC.php?modelo=iee&marca=iee&ram=3&procesador=iee&so=iee&almacenamiento=100&pantalla=15.5&grafica=iee&conexiones=iee
    
mysql_close();
?>
