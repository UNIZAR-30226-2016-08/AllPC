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

$query = 'PREPARE sentencia FROM "INSERT INTO Administradores VALUES(NULL,?,?,?)"';
$resultado = mysql_query($query);
if(!$resultado)
die("Fallo el comando:".mysql_error());
else{

$nombre = $_GET["nombre"];
$correo = $_GET["correo"];
$pass = $_GET["pass"];

$query ='SET @nombre = "'.$nombre.'"'.', @correo = "'.$correo.'"'.', @pass= "'.$pass.'"';
if(!mysql_query($query))
die("Error en SET: ".mysql_error());
$query = 'EXECUTE sentencia USING @nombre,@correo,@pass';
if(!mysql_query($query))
die("Error en EXECUTE:".mysql_error());
$query = 'DEALLOCATE PREPARE sentencia';
if(!mysql_query($query)){
    die("Error en DEALLOCATE:".mysql_error());
}else {
    print("ok");
}
}

//http://allpcserver.ddns.net/AllPC/insertAdmin.php?nombre=iee&correo=iee&pass=iee
    
mysql_close();
?>
