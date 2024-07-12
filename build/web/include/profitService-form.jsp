<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Báo cáo doanh thu dịch vụ</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f0f2f5;
            margin: 0;
            padding: 0;
            color: #333;
        }
        .container {
            max-width: 1200px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            border-radius: 8px;
        }
        h1 {
            color: #4CAF50;
            text-align: center;
            margin-bottom: 20px;
            font-size: 40px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 12px;
            text-align: center;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .pagination {
            display: flex;
            justify-content: center;
            margin: 20px 0;
        }
        .pagination .page-item {
            margin: 0 5px;
        }
        .pagination .page-link {
            color: #4CAF50;
            border: 1px solid #ddd;
            padding: 8px 16px;
            text-decoration: none;
            border-radius: 4px;
            transition: background-color 0.3s;
        }
        .pagination .page-link:hover, .pagination .page-item.active .page-link {
            background-color: #4CAF50;
            color: white;
            border: 1px solid #4CAF50;
        }
        .chart-container {
            width: 100%;
            max-width: 800px;
            margin: 0 auto 20px;
        }
    </style>
</head>
<body>
    <div class="container-fluid pt-5">
     <div id="page-wrapper">
        <h1>Báo cáo doanh thu dịch vụ</h1>
        <h3 style="color: red">Tổng doanh thu: ${totalRevenue} đ</h3>

        <h3>Danh sách dịch vụ đã đặt</h3>
        <table>
            <thead>
                <tr>
                    <th>Booking ID</th>
                    <th>Customer ID</th>
                    <th>Tên</th>
                    <th>Giá</th>
                    <th>Dịch Vụ</th>
                    <th>Bill ID</th>
                    <th>Trạng Thái</th>
                    <th>Ngày Thanh Toán</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="service" items="${listB}">
                    <tr>
                        <td>${service.bookingID}</td>
                        <td>${service.customerID}</td>
                        <td>${service.name}</td>
                        <td>${service.price}</td>
                        <td>${service.serviceName}</td>
                        <td>${service.billID}</td>
                        <td>Thanh Toán</td>
                        <td>${service.billDate}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="pagination">
            <c:forEach var="i" begin="1" end="${numberPage}">
                <div class="page-item <c:if test="${i == currentPage}">active</c:if>">
                    <a class="page-link" href="ProfitServiceServlet?page=${i}">${i}</a>
                </div>
            </c:forEach>
        </div>

        <h3 >Số lượng dịch vụ đã đặt</h3>
        <div class="chart-container">
            <canvas id="serviceCountChart"></canvas>
        </div>
        </div>
    <!--</div>-->

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
   <script>
    $(document).ready(function() {
        var serviceCountJson = '${serviceCountJson}';
        var serviceCount = JSON.parse(serviceCountJson);

        var labels = [];
        var data = [];
        for (var serviceName in serviceCount) {
            labels.push(serviceName);
            data.push(serviceCount[serviceName]);
        }

        var ctx = document.getElementById('serviceCountChart').getContext('2d');
        var serviceCountChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Số lượng dịch vụ đã đặt',
                    data: data,
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            precision: 0 // Force integers on y-axis
                        }
                    }
                }
            }
        });
    });
</script>

</body>
</html>