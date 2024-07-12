<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý doanh thu cửa hàng</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.2/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container-fluid {
            padding-top: 50px;
        }
        h2 {
            margin-bottom: 20px;
        }
        .table {
            margin-top: 20px;
            background-color: white;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .table thead {
            background-color: #007bff;
            color: white;
        }
        .table th, .table td {
            text-align: center;
            vertical-align: middle;
        }
        .form-control {
            border-radius: 5px;
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div id="page-wrapper">
            <div class="container mt-3">
                <h2>Quản lí danh sách cửa hàng</h2>
                <form action="ManagerRevenueBrand" method="post">
                    <div class="form-group row">
                        <label for="month" class="col-sm-2 col-form-label">Tháng</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="month" name="month" placeholder="Nhập tháng">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="year" class="col-sm-2 col-form-label">Năm</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="year" name="year" placeholder="Nhập năm">
                        </div>
                    </div>
                    <!-- Đường dẫn ẩn cho tháng và năm -->
                 
                    <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                    <br>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Mã cửa hàng</th>
                                <th>Tên Cửa Hàng</th>
                                <th>Mô tả cửa hàng</th>
                                <th>Địa chỉ cửa hàng</th>
                                <th>Tên ngân hàng</th>
                                <th>Số tài khoản</th>
                                <th>Doanh thu</th>
                                <th>Trả tiền</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="brand" items="${listBrands}">
                                <tr>
                                    <td>${brand.brandID}</td>
                                    <td>${brand.brandName}</td>
                                    <td>${brand.brandDescription}</td>
                                    <td>${brand.brandAddess}</td>
                                    <td>${brand.bankName}</td>
                                    <td>${brand.accountNumber}</td>
                                    <td>
                                        <input type="hidden" name="CTVID" value="${brand.CTVID}">
                                        ${revenue[brand.CTVID]} <!-- Hiển thị doanh thu ở đây -->
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${statusMap[brand.CTVID] == 0 || statusMap[brand.CTVID] == -1}">
                                                <!-- Thêm các thuộc tính dữ liệu để truyền vào JavaScript -->
                                                <a href="#" class="btn btn-success tratiendanhsach"
                                                   data-brandid="${brand.brandID}"
                                                   data-brandname="${brand.brandName}"
                                                   data-ctvid="${brand.CTVID}"
                                                   data-revenue="${revenue[brand.CTVID]}"
                                                   data-month="${param.month}"
                                                   data-year="${param.year}">Trả tiền</a>
                                            </c:when>
                                            <c:otherwise>
                                                <td></td>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </form>

                <!-- Hiển thị statusMap để kiểm tra -->
                <c:forEach var="entry" items="${statusMap.entrySet()}">
                    <p>${entry.key}: ${entry.value}</p>
                </c:forEach>
                <c:forEach var="entry" items="${revenue.entrySet()}">
                    <p>${entry.key}: ${entry.value}</p>
                </c:forEach>
            </div>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.2/js/bootstrap.min.js"></script>

    <!-- JavaScript để xử lý Ajax và hiển thị thông báo -->
    <script>
    $(document).ready(function() {
        $('.tratiendanhsach').click(function(e) {
            e.preventDefault(); // Ngăn chặn hành động mặc định của thẻ a

            var brandID = $(this).data('brandid');
            var brandName = $(this).data('brandname');
            var CTVID = $(this).data('ctvid');
            var revenue = $(this).data('revenue');
            var month = $(this).data('month');
            var year = $(this).data('year');

            $.ajax({
                type: 'POST',
                url: 'revenuebrand', // Đường dẫn đến servlet
                data: {
                    brandID: brandID,
                    CTVID: CTVID,
                    brandName: brandName,
                    revenue: revenue,
                    month: month,
                    year: year
                },
                success: function(response) {
                    if (response === 'success') {
                        alert('Đã thanh toán thành công cho ' + brandName + ' vào tháng ' + month + ' năm ' + year);
                        // Cập nhật UI tại đây nếu cần
                    } else {
                        alert('Không thể thực hiện thanh toán. Vui lòng thử lại sau.');
                    }
                },
                error: function() {
                    alert('Đã xảy ra lỗi. Vui lòng thử lại sau.');
                }
            });
        });
    });
    </script>
</body>
</html>
