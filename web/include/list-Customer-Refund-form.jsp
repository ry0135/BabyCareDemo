<%-- 
    Document   : managebookingEmploye-form
    Created on : May 30, 2024, 9:52:08 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            color: black;
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

<div class="container">
    <div id="page-wrapper">
    <h2 class="my-4"style=" font-size: 24px; /* Adjust the font size */
        color: #333; /* Set the text color */
        margin-top: 20px; /* Add some top margin for spacing */
        margin-bottom: 20px; /* Add some bottom margin for spacing */
        text-align: center; /* Center-align the text */
        text-transform: uppercase; /* Convert text to uppercase */
        letter-spacing: 2px; /* Add some letter spacing */
        font-weight: bold; /* Make the text bold */">Hoàn Trả Tiền</h2>
      

    <table class="table table-bordered table-striped">
        <thead class="thead-dark">
            <tr>
<!--                <th>h</th>-->
                <th>Customer ID</th>

                <th>Tên</th>
<!--                <th>Dịch Vụ</th>-->
                <th>Tên Ngân Hàng</th>
                <th>Tên Tài Khoản</th>
                <th>Số Tài khoản</th>
                <th>Gía</th>
                <th>Ngày</th>
                <th>Trạng Thái</th>
                <th>Lý Do</th>
                <th>Action</th>

            </tr>
        </thead>
        <tbody>
            <c:forEach var="b" items="${listB}">
                <tr>
<!--                    <td>${b.refundID}</td>-->
                    <td>${b.customerID}</td>

                    <td>${b.name}</td>
<!--                 <td>${b.serviceName}</td>-->
                    <td>${b.bankName}</td>
                    <td>${b.accountName}</td>
                    <td>${b.accountNumber}</td>
                    <td>${b.refundAmount}</td>
                    <td>${b.refundDate}</td>
                    
                    <td>
                        <c:choose>
                           
                            <c:when test="${b.refundStatus == 1}">
                                Đang xử lý ...
                            </c:when>
                            <c:when test="${b.refundStatus == 2}">
                                Đã hoàn tiền
                            </c:when>
                            
                        </c:choose>
                    </td>
                    <td>${b.note}</td>
                    <td>
                            <c:if test="${b.refundStatus == 1}">
                                <form action="StatusCustomerRefundServlet" method="post">
                                    <input type="hidden" name="refundID" value="${b.refundID}">
                                    <button type="submit" class="btn btn-success btn-sm">Hoàn Thành</button>
                                </form>
                            </c:if>
                        </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
  </div>
</div>
</body>
</html>