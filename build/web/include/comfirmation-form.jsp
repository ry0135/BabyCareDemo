<%-- 
Document   : confirmation-form
Created on : May 28, 2024, 4:23:41 PM
Author     : ACER
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Lấy thông tin từ session
    String name = (String) session.getAttribute("name");
    String address = (String) session.getAttribute("address");
    String phone = (String) session.getAttribute("phone");
    String sex = (String) session.getAttribute("sex");
    String slot = (String) session.getAttribute("slot");
    String bookingDate = (String) session.getAttribute("bookingDate");
    String note = (String) session.getAttribute("note");
    double servicePrice = (double) session.getAttribute("servicePrice");
    Integer bookingStatus = (Integer) session.getAttribute("bookingStatus");
    Integer bookingID = (Integer) session.getAttribute("bookingID");
    Integer billID = (Integer) session.getAttribute("billID");
    String email = (String) session.getAttribute("email");
    String serviceName = (String) session.getAttribute("serviceName");
    // Map mã trạng thái đặt hàng sang thông báo dễ đọc
   
%>

<!DOCTYPE html>
<style>
    .container{
        margin: 20px;
     
    }
    
     .card {
            margin-top: 30px;
        }
        .card-header h2 {
            font-weight: bold;
             text-align: center;
        }
        .btn-back-home {
            margin-top: 10px;
            display: block;
            width: 150px;
            text-align: center;
</style>
    <body>
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class="card">
                        <div class="card-header">
                            <h2>Đặt lịch thành công</h2>
                        </div>
                        <div class="card-body">
                            <h5 class="card-title">Cảm ơn bạn đã đặt dịch vụ này, <%= name%>!</h5>
                            <p class="card-text">Thông tin thanh toán:</p>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item"><strong>Tên:</strong> <%= name%></li>
                                <li class="list-group-item"><strong>Địa chỉ:</strong> <%= address%></li>
                                <li class="list-group-item"><strong>Phone:</strong> <%= phone%></li>
                                <li class="list-group-item"><strong>Giới tính:</strong> <%= sex%></li>
                                <li class="list-group-item"><strong>Tên dịch vụ:</strong> <%=serviceName%></li>
                                <li class="list-group-item"><strong>Ngày đặt:</strong> <%= bookingDate%></li>
                                <li class="list-group-item"><strong>Khoảng thời gian:</strong> <%= slot%></li>
                                <li class="list-group-item"><strong>Trạng thái:</strong> Đang xử lý</li> 
                                <li class="list-group-item"><strong>Giá:</strong> <%= servicePrice%> VND</li>
                                <li class="list-group-item"><strong>Ghi chú:</strong> <%= note%></li>
                                <li class="list-group-item"><strong>Mã dịch vụ:</strong> <%= bookingID%></li>
                                <li class="list-group-item"><strong>Email:</strong> <%= email%></li>
                            </ul>
                                                      <div style=" margin-top: 10px">
                                                          <a href="index.jsp" class="btn btn-primary btn-back-home">Về Trang Chủ</a>
                                  
                        </div>
                           

                        </div>
                    </div>
                </div>
            </div>
        </div>