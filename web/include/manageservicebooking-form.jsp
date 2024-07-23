    

<%-- 
    Document   : manageservicebooking-form
    Created on : May 29, 2024, 1:37:38 PM
    Author     : ACER
--%>
<script>
    function canModifyBooking(bookingDate, slot) {
        const now = new Date();
        const bookingDateTime = new Date(bookingDate);
        const slotTimes = {
            "Slot 1 (7:00 - 9:00)": [7, 0],
            "Slot 2 (9:00 - 11:00)": [9, 0],
            "Slot 3 (13:00 - 15:00)": [13, 0],
            "Slot 4 (15:00 - 18:00)": [15, 0]
        };

        if (slotTimes[slot]) {
            bookingDateTime.setHours(slotTimes[slot][0], slotTimes[slot][1], 0, 0);
        }

        const twelveHoursInMilliseconds = 12 * 60 * 60 * 1000;
        if ((bookingDateTime - now) < twelveHoursInMilliseconds) {
            alert('Bạn chỉ có thể hủy hoặc chỉnh sửa lịch hẹn trước 12 tiếng so với thời gian đặt.');
            return false;
        }
        return true;
    }
</script>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="nav nav-pills nav-justified">

    <a class="nav-item nav-link active" href="ListBookingCustomerIDServlet">Lịch sủ dịch vụ</a>
    <a class="nav-item nav-link  " href="ListCustomerIDRefundServlet">Danh sách dịch vụ hoàn trả tiền </a>
    
</nav>
<div class="container">
    <h2 class="my-4" style="font-size: 24px; color: #333; margin-top: 20px; margin-bottom: 20px; text-align: center; text-transform: uppercase; letter-spacing: 2px; font-weight: bold;">Lịch sử dịch vụ</h2>
    <div class="table-responsive">
        <table class="table table-bordered table-striped table-hover">
            <thead class="thead-dark">
                <tr>
<!--                    <th scope="col">Customer ID</th>-->
                    <!--<th scope="col">Service ID</th>-->
                    <th scope="col">Dịch vụ</th>
                    <th scope="col">Tên</th>
                    <th scope="col">SĐT</th>
                    <th scope="col">Email</th>
                    <th scope="col">Địa Chỉ</th>
                    <th scope="col">Sex</th>
                    <th scope="col">Ngày Đặt Lịch</th>
                    <th scope="col">Slot</th>
                    <th scope="col">Trạng Thái</th>
                    <th scope="col">Ghi Chú</th>
                    <th scope="col">Giá</th>
                    <th scope="col">Thanh Toán</th>
                    <th scope="col">Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="booking" items="${listB}">
                    <tr>
                        <!--<td>${booking.customerID}</td>-->
                        <!--<td>${booking.serviceID}</td>-->
                        <td>${booking.serviceName}</td>
                        <td>${booking.name}</td>
                        <td>${booking.phoneNumber}</td>
                        <td>${booking.email}</td>
                        <td>${booking.address}</td>
                        <td>${booking.sex}</td>
                        <td>${booking.bookingDate}</td>
                        <td>${booking.slot}</td>
                        <td>
                            <c:choose>
                                <c:when test="${booking.bookingStatus == 0}">Lịch đã bị hủy</c:when>
                                <c:when test="${booking.bookingStatus == 1}">Đang xử lý ...</c:when>
                                <c:when test="${booking.bookingStatus == 2}">Đã Đặt</c:when>
                                <c:when test="${booking.bookingStatus == 3}">Đã Hoàn Thành</c:when>
                                <c:otherwise>Other Status</c:otherwise>
                            </c:choose>
                        </td>
                        <td>${booking.note}</td>
                        <td>${booking.price}.VNĐ</td>
                        <td>
                            <c:choose>
                                <c:when test="${booking.billStatus == 1}">Đang xử lý...</c:when>
                                <c:when test="${booking.billStatus == 2}">Chưa Thanh Toán</c:when>
                                <c:when test="${booking.billStatus == 3}">Đã Thanh Toán</c:when>
                            </c:choose>
                        </td>
                        <td>
                            
<!--                                <form action="DeleteBookingServlet" method="post" onsubmit="return confirm('Bạn chắc chắn muốn hủy dịch vụ này?');">
                                    <input type="hidden" name="bookingID" value="${booking.bookingID}">
                                    <input type="hidden" name="billID" value="${booking.billID}">
                                    <button type="submit" class="btn btn-danger btn-sm">Hủy</button>
                                </form>-->
                            
                            <c:if test="${booking.bookingStatus == 1 || booking.bookingStatus == 2 && booking.billStatus == 3}">
                                <form action="CustomerRefund" method="get" onsubmit="return confirm('Bạn chắc chắn muốn hủy dịch vụ này? Nếu bạn hủy thì số tiền hoàn lại bạn sẽ mất 20%. Số tiền sẽ được hoàn lại trong vòng 48 giờ tới');">
                                    <input type="hidden" name="bookingID" value="${booking.bookingID}">
                                    <input type="hidden" name="customerID" value="${booking.customerID}">
                                    <input type="hidden" name="name" value="${booking.name}">
                                    <input type="hidden" name="bookingID" value="${booking.customerID}">
                                    <input type="hidden" name="servicePrice" value="${booking.price}">
                                    <input type="hidden" name="billID" value="${booking.billID}">
                                    <input type="hidden" name="serviceName" value="${booking.serviceName}">
                                    <input type="hidden" name="t" value="${booking.email}">
                                    
                                    <button type="submit" class="btn btn-danger btn-sm">Hủy</button>
                                </form>
                            </c:if>
                            
                           <c:if test="${booking.bookingStatus == 3}">
                                <form action="FeedBackServlet" method="get">
                                    
                                    <input type="hidden" name="CustomerID" value="${booking.customerID}">
                                    <input type="hidden" name="ServiceID" value="${booking.serviceID}">
                                    <input type="hidden" id="bookingDate" name="BookingDate" value="${booking.bookingDate}">
                                    <input type="hidden" name="name" value="${booking.name}">
                                    <button type="submit" class="btn btn-danger btn-sm">Feedback</button>
                                </form>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${booking.bookingStatus == 2 && booking.billStatus == 3}">
                                <form action="UpdateBookingServlet" method="get" onsubmit="return canModifyBooking('${booking.bookingDate}', '${booking.slot}');">
                                    <input type="hidden" name="bookingID" value="${booking.bookingID}">
                                    <button type="submit" class="btn btn-success btn-sm">Chỉnh sửa</button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
