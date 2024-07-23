<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Báo cáo doanh thu dịch vụ</title>
    <style>
        /* Include the CSS styles here */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f0f2f5;
            margin: 0;
            padding: 0;
            color: #333;
        }

        .container {
            max-width: 1200px;
            margin-right: 20px;
            
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }

        h1 {
            color: #4CAF50;
            text-align: center;
            margin-top: 100px;
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

        .pagination .page-link:hover,
        .pagination .page-item.active .page-link {
            background-color: #4CAF50;
            color: white;
            border: 1px solid #4CAF50;
        }

        .chart-container {
            width: 100%;
            max-width: 800px;
            margin: 0 auto 20px;
        }

        /* Form Styling */
        form {
            width: 200px;
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-bottom: 20px;
        }

        form label {
            font-size: 18px;
           
        }

        form select {
            width: 70px;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            margin-bottom: 20px;
            font-size: 16px;
        }

        form button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 4px 2px;
            cursor: pointer;
            border-radius: 4px;
            transition: background-color 0.3s;
        }

        form button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Báo cáo doanh thu dịch vụ</h1>
        <div>
            <form method="get" action="ProfitServiceYearServlet">
                <label for="year">Chọn năm:</label>
                <select id="yearRevenue" name="yearRevenue" class="form-select">
                    <c:forEach var="year" begin="2020" end="2031">
                        <option value="${year}" <c:if test="${year == selectedYear}">selected</c:if>>${year}</option>
                    </c:forEach>
                </select>
                <button type="submit" class="btn btn-primary text-white w-100 py-3 px-5">Xem</button>
            </form>
        </div>
        <h3 style="color: red">Tổng doanh thu: ${totalRevenue} đ</h3>

        <table>
            <thead>
                <tr>
                    <th>Năm</th>
                    <th>Tháng</th>
                    <th>Doanh thu</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="revenue" items="${listB}">
                    <tr>
                        <td>${revenue.year}</td>
                        <td>${revenue.month}</td>
                        <td>${revenue.getFormattedTotalRevenue()}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <h3>Doanh thu theo từng tháng</h3>
        <div class="chart-container">
            <canvas id="monthlyRevenueChart"></canvas>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script>
        $(document).ready(function() {
            // Lấy dữ liệu JSON từ JSP
            var monthlyRevenueJson = '${monthlyRevenueJson}';
            console.log('Monthly Revenue JSON:', monthlyRevenueJson); // Thêm dòng này để kiểm tra dữ liệu JSON
            
            // Chuyển đổi JSON thành đối tượng JavaScript
            var monthlyRevenue = JSON.parse(monthlyRevenueJson);

            // Tạo các mảng cho các nhãn và dữ liệu
            var labels = [];
            var data = [];
            
            // Khởi tạo dữ liệu cho các tháng từ 1 đến 12
            for (var month = 1; month <= 12; month++) {
                var found = false;
                for (var i = 0; i < monthlyRevenue.length; i++) {
                    if (monthlyRevenue[i].month === month) {
                        labels.push(month + '/' + monthlyRevenue[i].year);
                        data.push(monthlyRevenue[i].totalRevenue);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    labels.push('Tháng ' + month);
                    data.push(0);
                }
            }

            // Vẽ biểu đồ
            var ctx = document.getElementById('monthlyRevenueChart').getContext('2d');
            var monthlyRevenueChart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'Doanh thu hàng tháng',
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
