<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <div class="container">
        <h1>Lịch sử giao dịch</h1>
        <table class="table">
            <thead>
                <tr>
                    <th>Ngày</th>
                    <th>Đơn Hàng</th>
                    <th>Tiền</th>

                 
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${completedOrders}" var="completedOrders">
                    <tr>
                        <td><c:out value="${completedOrders.date}"/></td>
                        <td><c:out value="${completedOrders.idOrder}"/></td>
                                            <td>+<c:out value="${completedOrders.totalAmount}"/>vnd</td>

                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
 