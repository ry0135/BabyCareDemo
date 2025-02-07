<%-- 
    Document   : customer-payment-form
    Created on : Jun 6, 2024, 11:14:27 AM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="nav nav-pills nav-justified">
    
    <!--<a class="nav-item nav-link " href="ListBookingEmploye">Danh Sách khách hàng đặt lịch</a>-->
    <a class="nav-item nav-link active" href="ListCustomerPayment">Danh Sách khách hàng đặt lịch</a>
     <a class="nav-item nav-link" href="ListConfirmedServices">Danh Sách lịch hoàn thành</a>
   
</nav>

<div class="container">
    <h2 class="my-4"style=" font-size: 24px; /* Adjust the font size */
        color: #333; /* Set the text color */
        margin-top: 20px; /* Add some top margin for spacing */
        margin-bottom: 20px; /* Add some bottom margin for spacing */
        text-align: center; /* Center-align the text */
        text-transform: uppercase; /* Convert text to uppercase */
        letter-spacing: 2px; /* Add some letter spacing */
        font-weight: bold; /* Make the text bold */">Danh Sách khách hàng đặt lịch</h2>


    <table class="table table-bordered table-striped">
        <thead class="thead-dark">
            <tr>

                <th>Customer ID</th>
                <th>Service ID</th>
                <th>Tên</th>
                <th>SĐT</th>
                <th>Địa Chỉ</th>
                <th>Sex</th>
                <th>Ngày Đặt Lịch</th>
                <th>Slot</th>
                <th>Giá</th>
                <th>Thanh Toán</th>
                <th></th>

                
            </tr>
        </thead>
        <tbody>
            <c:forEach var="booking" items="${listE}">
                <tr>

                    <td>${booking.customerID}</td>
                    <td>${booking.serviceID}</td>
                    <td>${booking.name}</td>
                    <td>${booking.phoneNumber}</td>
                    <td>${booking.address}</td>
                    <td>${booking.sex}</td>
                    <td>${booking.bookingDate}</td>
                    <td>${booking.slot}</td>


                    <td>${booking.price}.VNĐ</td>
                    <td>
                        <c:choose>
                            <c:when test="${booking.billStatus == 1}">Đang xủ lý...</c:when>
                            <c:when test="${booking.billStatus == 2}">Chưa Thanh Toán</c:when>
                            <c:when test="${booking.billStatus == 3}">Đã Thanh Toán</c:when>

                        </c:choose>
                    </td>
                    <td>
                        
                        <c:if test="${booking.bookingStatus == 2}">
                            <form action="StatusBookingServlet2" method="post" onsubmit="return confirm('Bạn chắc chắn muốn hoàn thành lịch của khách hàng này ?');">
                                <input type="hidden" name="bookingID" value="${booking.bookingID}">
                                <input type="hidden" name="bookingStatus" value="3">
                                <button type="submit" class="btn btn-success">Hoàn thành</button>
                            </form>
                        </c:if>
                    </td>


                    

                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
