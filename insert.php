<?php
    require('dbcon.php');

    $response = array('success' => true);

    $id = $_REQUEST['USERID'];
    $password = $_REQUEST['USERPassword'];
    $company = $_REQUEST['USERCompany'];
    $phone = $_REQUEST['USERphone'];

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

    if (empty($company)) {
        $response['success'] = false;
        $response['message'] = 'USERCompany is not set.';
        echo json_encode($response);
        return;
    }

    if (empty($phone)) {
        $response['success'] = false;
        $response['message'] = 'USERphone is not set.';
        echo json_encode($response);
        return;
    }

    try {
        $stmt = $con->prepare('INSERT INTO Chemist VALUES(:id, :password, :company, :phone)');
        $stmt->bindParam(':id', $id);
        $stmt->bindParam(':password', $password);
        $stmt->bindParam(':company', $company);
        $stmt->bindParam(':phone', $phone);

        if (!$stmt->execute()) {
            $response['message'] = 'Failed execution.';
            $response['success'] = false;
        }
    } catch(PDOException $e) {
        die("Database error: " . $e->getMessage());
    }

    echo json_encode($response);
?>