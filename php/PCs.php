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

$query = "SELECT * FROM $tabla";
$resultado = mysql_query($query);
if(!$resultado)
    die("Fallo el comando:".mysql_error());
else{
    $return = array();
    while($rows = mysql_fetch_array($resultado,MYSQL_ASSOC)){
        array_push($return,$rows);
    }
    print(json_encode($return));
}

mysql_free_result($resultado);
    
mysql_close();
?>
