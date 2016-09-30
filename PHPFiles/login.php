<?php
	$con = mysqli_connect("localhost", "root", "", "zihaddb");

	if (mysqli_connect_errno($con)) {
		echo "Failed to connect to MySQL : ".mysqli_connect_errno();
	}

	$username = $_GET["username"];
	$password = $_GET["password"];
	$result = mysqli_query($con, "SELECT id,name FROM account WHERE username='$username' and password='$password'");
	$row = mysqli_fetch_array($result);
	if ($row) {
		$arr = array('success'=>true, 'id'=>$row[0], 'name'=>$row[1]);
		echo json_encode($arr);
	} else {
		$arr = array('success'=>false);
		echo json_encode($arr);
	}

	mysqli_close($con);
?>