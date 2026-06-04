<?php
header('Content-Type: application/json; charset=utf-8');
header('Access-Control-Allow-Origin: *');

$host = '10.170.210.122';
$port = '5432';
$dbname = 'SistemaGestor';
$user = 'postgres';
$pass = 'pass';

try {
    $conn = new PDO("pgsql:host=$host;port=$port;dbname=$dbname", $user, $pass, [
        PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION
    ]);

    $mapa = [
        'cola'     => 'ACTIVA',
        'curso'    => 'EN_CURSO',
        'resuelta' => 'RESUELTA'
    ];

    $resultado = [];

    foreach ($mapa as $key => $estado) {
        $stmt = $conn->prepare("SELECT COUNT(*) as total FROM incidencia WHERE estado = ?");
        $stmt->execute([$estado]);
        $total = (int) $stmt->fetchColumn();

        $resultado[$key] = [
            'total' => $total
        ];
    }

    echo json_encode($resultado, JSON_UNESCAPED_UNICODE);

} catch (PDOException $e) {
    http_response_code(500);
    echo json_encode(['error' => $e->getMessage()]);
}
