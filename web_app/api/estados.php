<?php
header('Content-Type: application/json; charset=utf-8');
header('Access-Control-Allow-Origin: *');

$host = 'localhost';
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

        $stmt2 = $conn->prepare(
            "SELECT i.id_incidencia, i.descripcion, i.prioridad, i.estado,
                    i.fecha_creacion, COALESCE(p.codigo_puesto, 'Desconocido') as puesto
             FROM incidencia i
             LEFT JOIN puesto p ON i.id_puesto = p.id_puesto
             WHERE i.estado = ?
             ORDER BY i.fecha_creacion DESC"
        );
        $stmt2->execute([$estado]);
        $items = $stmt2->fetchAll(PDO::FETCH_ASSOC);

        $resultado[$key] = [
            'total' => $total,
            'items' => $items
        ];
    }

    echo json_encode($resultado, JSON_UNESCAPED_UNICODE);

} catch (PDOException $e) {
    http_response_code(500);
    echo json_encode(['error' => $e->getMessage()]);
}
