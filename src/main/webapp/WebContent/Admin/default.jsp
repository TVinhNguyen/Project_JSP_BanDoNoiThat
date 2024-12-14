<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="/WebContent/Admin/css/style.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f5f6fa;
            color: #333;
        }

        .dashboard-container {
            display: flex;
            height: 100vh;
            overflow: hidden;
        }
        .main-content {
            flex: 1;
            background: #fff;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            margin: 20px;
            padding: 20px;
            overflow-y: auto;
        }

        h2 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 20px;
        }

        .summary-cards {
            display: flex;
            justify-content: space-between;
            gap: 20px;
            margin-bottom: 30px;
        }

        .card {
            flex: 1;
            background: #3498db;
            color: white;
            text-align: center;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 12px rgba(0, 0, 0, 0.2);
        }

        .card h3 {
            margin: 0 0 10px;
            font-size: 1.2rem;
        }

        .card p {
            font-size: 1.5rem;
            font-weight: bold;
        }

        .chart-container {
            background: #ecf0f1;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .chart-container h3 {
            text-align: center;
            margin-bottom: 20px;
            color: #34495e;
        }

        canvas {
            display: block;
            max-width: 100%;
        }
    </style>
</head>
<body>
<div class="dashboard-container">
        <%@ include file="dashboard.jsp" %>

    <div class="main-content" id="mainContent">
        <h2>Welcome, Admin</h2>

        <div class="summary-cards">
            <div class="card">
                <h3>Total Products</h3>
                <p><%= request.getAttribute("totalProducts") %></p>
            </div>
            <div class="card">
                <h3>Total Orders</h3>
                <p><%= request.getAttribute("totalOrders") %></p>
            </div>
        </div>

        <div class="chart-container">
            <h3>Orders in <%= request.getAttribute("month") %>/<%= request.getAttribute("year") %></h3>
            <canvas id="ordersChart"></canvas>
        </div>
    </div>
</div>

<script>
    // Dữ liệu được gửi từ server dưới dạng JSON
    const orderDates = JSON.parse('<%= request.getAttribute("orderDatesJson") %>');
    const orderCounts = JSON.parse('<%= request.getAttribute("orderCountsJson") %>');

    // Hàm chuyển đổi ngày từ kiểu ISO sang định dạng dd/MM/yyyy
    const formatDate = (dateString) => {
        const date = new Date(dateString);
        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0'); // Tháng trong JavaScript bắt đầu từ 0
        const year = date.getFullYear();
        return `${day}/${month}/${year}`;
    };

    // Chuyển đổi tất cả các ngày
    const formattedDates = orderDates.map(formatDate);

    // Hiển thị biểu đồ với dữ liệu đã chỉnh sửa
    const ctx = document.getElementById('ordersChart').getContext('2d');
    const ordersChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: formattedDates,
            datasets: [{
                label: 'Số lượng đơn hàng',
                data: orderCounts,
                backgroundColor: 'rgba(54, 162, 235, 0.6)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { display: false }
            },
            scales: {
                x: {
                    title: { display: true, text: 'Ngày' }
                },
                y: {
                    title: { display: true, text: 'Số lượng đơn hàng' },
                    beginAtZero: true
                }
            }
        }
    });
</script>

</body>
</html>
