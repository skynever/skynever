<?php
    require('dbcon.php');

    $response = array('success' => true);

    $id = $_REQUEST['USERID'];
    $password = $_REQUEST['USERPassword'];

    if (empty($id)) {
        $response['success'] = false;
        $response['message'] = 'USERID is not set.';
        echo json_encode($response);
        return;
    }

    if (empty($password)) {
        $response['success'] = false;
        $response['message'] = 'USERPassword is not set.';
        echo json_encode($response);
        return;
    }

    $stmt = $con->prepare('SELECT * FROM Chemist WHERE USERID=:id LIMIT 1');
    $stmt->bindParam(':id', $id);
    $stmt->execute();

    $user = $stmt->fetch();

    if ($user) {
        if ($user['USERPassword'] === $password) {
            $response['result'] = $user;

        } else {
            $response['success'] = false;
            $response['message'] = 'Password is incorrect.';
            $response['result'] = 'INCORRECT_PASSWORD';
        }
    } else {
        $response['success'] = false;
        $response['message'] = 'User not found.';
        $response['result'] = 'USER_NOT_FOUND';
    }

    echo json_encode($response);
?>
