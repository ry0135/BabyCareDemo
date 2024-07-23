<%-- 
    Document   : managebookingEmploye-form
    Created on : May 30, 2024, 9:52:08 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<nav class="nav nav-pills nav-justified">

    <a class="nav-item nav-link " href="ListBookingCustomerIDServlet">Lịch sử dịch vụ</a>
    <a class="nav-item nav-link active " href="ListCustomerIDRefundServlet">Danh Sách Dịch Vụ Hoàn Tiền </a>
    
</nav>

<div class="container">
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

                <!--<th>Customer ID</th>-->

                <th>Tên</th>
                <th>Dịch Vụ</th>
                <th>Tên Ngân Hàng</th>
                <th>Tên Tài Khoản</th>
                <th>Số Tài khoản</th>
                <th>Gía</th>
                <th>Ngày</th>
                <th>Trạng Thái</th>
                <th>Lý Do</th>
              

            </tr>
        </thead>
        <tbody>
            <c:forEach var="b" items="${listB}">
                <tr>

                    <!--<td>${b.customerID}</td>-->

                    <td>${b.name}</td>
                    <td>${b.serviceName}</td>
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
                                Đã hoan tien
                            </c:when>
                            
                        </c:choose>
                    </td>
                    <td>${b.note}</td>
                    
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>