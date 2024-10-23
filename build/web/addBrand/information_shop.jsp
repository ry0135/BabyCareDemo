<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">


    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"/>


    <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@600;700&family=Montserrat:wght@200;400;600&display=swap" rel="stylesheet"> 

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- Icon Font Stylesheet -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">  
</head>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f5f5f5;
    }
    .info-definition {
        position: absolute;
        background-color: white;
        width: 70%;
        margin-top: 100px;
        margin-left: 15%;  /* Giảm khoảng cách bên trái để cân bằng */
        margin-right:  25%;  /* Giảm khoảng cách bên trái để cân bằng */
        margin-bottom: 50px; /* Tạo thêm khoảng cách phía dưới nếu cần */
        padding: 20px; /* Tạo thêm không gian bên trong phần tử */
        height: 1000px;
        box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1); /* Thêm đổ bóng để tạo độ nổi */

    }
    .shop-form .form-group {
        margin-bottom: 20px;
    }

    .shop-form label {
        font-weight: bold;
        display: block;
        margin-bottom: 5px;
    }

    .shop-form .required {
        color: #ff4d4f;
    }

    .shop-form input[type="text"],
    .shop-form input[type="email"] {
        width: 100%;
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 4px;
    }

    .shop-form input[disabled] {
        background-color: #f5f5f5;
    }

    .error {
        color: #ff4d4f;
        font-size: 12px;
    }

    .edit-link {
        color: #1890ff;
        text-decoration: none;
        font-size: 14px;
    }

    .edit-link:hover {
        text-decoration: underline;
    }

    .form-actions {
        display: flex;
        justify-content: flex-end;
    }

    .btn {
        padding: 10px 20px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 14px;
    }

    .save-btn {
        background-color: #f5f5f5;
        margin-right: 10px;
    }

    .next-btn {
        background-color: #ff4880;
        color: white;
    }

    .next-btn:hover {
        background-color: #d9363e;
    }

    .timeline {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin: 20px 0;
        padding: 0;
        list-style-type: none;
    }

    .timeline li {
        flex: 1;
        position: relative;
        text-align: center;
    }

    .timeline li:not(:last-child)::after {
        content: '';
        position: absolute;
        top: 50%;
        left: 49%;
        height: 4px;
        width: 100%;
        background-color: #ccc;
        z-index: 1;
        transform: translateY(-50%);
    }
    .timeline li.active:not(:last-child)::after {
        background-color: #4CAF50;
    }

    .timeline .icon {
        background-color: #4CAF50;
        color: white;
        border-radius: 50%;
        padding: 10px;
        display: inline-block;
        margin-bottom: 30px;
    }

    .timeline .icon.active {
        background-color: #ff4880;
    }

    .timeline .icon.inactive {
        background-color: #ccc;
    }

    .timeline .step-title {
        font-weight: bold;
    }

    .timeline .step-date {
        color: #666;
    }

    .timeline .step {
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    #newAddressInput {
        display: none;
    }
    /* Định dạng thông báo thành công */
    #success-message {
        position: absolute;
        top: 100px;
        right: 600px;
        background-color: #28a745;
        color: white;
        padding: 15px;
        border-radius: 5px;
        box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);

    }
</style>
<c:if test="${hasPending}">
    <h3 class="text-danger">Chúng tôi đã tiếp nhận thông tin của bạn. Chúng tôi sẽ thông báo qua email của bạn trong vòng 7 ngày.</h3>
</c:if>
<c:if test="${!hasPending}">
    <div class="info-definition">

        <ul class="timeline">
            <li>
                <div class="step">
                    <span class="icon active">
                        <i class="fas fa-receipt"></i>
                    </span>
                    <span class="step-title">Thông tin shop</span>
                </div>
            </li>
            <li>
                <div class="step">
                    <span class="icon inactive">
                        <i class="fas fa-dollar-sign"></i>
                    </span>
                    <span class="step-title">Thông tin định danh</span>
                </div>
            </li>

        </ul>
        <form class="shop-form needs-validation"  novalidate action="addBrand" method="post" enctype="multipart/form-data">
            
            <div class="form-group">
                <label for="shop-name">Tên Shop <span class="required">*</span></label>
                <input type="text" class="form-control" id="brandName" placeholder="Tên cửa hàng"
                       name="brandName" aria-describedby="inputGroupPrepend" pattern="^.{1,100}$" value="${brand.brandName}"  required>
                <div class="invalid-feedback">
                    Tên cửa hàng chứa từ 1 đến 100 kí tự
                </div>
                <p class="text-danger">${thongbao1}</p>
            </div>

            <div class="form-group">
                <label>Địa chỉ lấy hàng <span class="required">*</span></label>
                <p id="address-display " name="brandAddress">
                    <br>
                    <c:choose>
                        <c:when test="${not empty brand.brandAddess}">
                            ${brand.brandAddess}
                        </c:when>
                        <c:otherwise>
                            ${sessionScope.user.address}
                        </c:otherwise>
                    </c:choose>
                </p>
                ${sessionScope.thongbao}

                <div class="Address">
                    <label for="addressType">Chọn địa chỉ:</label>
                    <select name="addressType" id="addressType" onchange="toggleNewAddressInput()">
                        <option value="default">Chọn địa chỉ mặc định</option>
                        <option value="new">Nhập địa chỉ mới</option>
                    </select>

                    <div id="newAddressInput">
                        <label for="newAddress">Địa chỉ mới:</label>
                        <input type="text" id="newAddress" name="newAddress">
                    </div>
                </div>    
            </div>

            <div class="form-group">
                <label for="email">Email <span class="required">*</span></label>  
                <input type="email" id="email" value="${sessionScope.user.email}" disabled>
            </div>

            <div class="form-group">
                <label for="phone">Số điện thoại <span class="required">*</span></label>
                <input type="text" id="phone" value="${sessionScope.user.phone}" disabled>
            </div>
            <div class="form-group">
                <div class="form-group">
                    <label for="bankName" >Tên Ngân Hàng <span class="text-danger">*</span></label>
                    <div class="input-group">

                        <select class="form-control" name="bankName" required>
                            <option value="">Chọn ngân hàng</option>
                            <option value="Vietcombank" ${brand.bankName == 'Vietcombank' ? 'selected' : ''}>Ngân hàng Thương mại Cổ phần Ngoại thương Việt Nam (Vietcombank)</option>
                            <option value="Vietinbank" ${brand.bankName == 'Vietinbank' ? 'selected' : ''}>Ngân hàng Thương mại Cổ phần Công Thương Việt Nam (Vietinbank)</option>
                            <option value="BIDV" ${brand.bankName == 'BIDV' ? 'selected' : ''}>Ngân hàng Thương mại Cổ phần Đầu tư và Phát triển Việt Nam (BIDV)</option>
                            <option value="ACB" ${brand.bankName == 'ACB' ? 'selected' : ''}>Ngân hàng Thương mại Cổ phần Á Châu (ACB)</option>
                            <option value="Techcombank" ${brand.bankName == 'Techcombank' ? 'selected' : ''}>Ngân hàng Thương mại Cổ phần Kỹ Thương Việt Nam (Techcombank)</option>
                            <option value="MB" ${brand.bankName == 'MB' ? 'selected' : ''}>Ngân hàng Thương mại Cổ phần Quân đội (MB)</option>
                            <option value="SHB" ${brand.bankName == 'SHB' ? 'selected' : ''}>Ngân hàng Thương mại Cổ phần Sài Gòn - Hà Nội (SHB)</option>
                            <option value="DongABank" ${brand.bankName == 'DongABank' ? 'selected' : ''}>Ngân hàng Thương mại Cổ phần Đông Á (DongABank)</option>
                            <option value="TPBank" ${brand.bankName == 'TPBank' ? 'selected' : ''}>Ngân hàng Thương mại Cổ phần Tiên Phong (TPBank)</option>
                            <option value="VietABank" ${brand.bankName == 'VietABank' ? 'selected' : ''}>Ngân hàng Thương mại Cổ phần Bản Việt (VietABank)</option>
                            <option value="SCB" ${brand.bankName == 'SCB' ? 'selected' : ''}>Ngân hàng Thương mại Cổ phần Sài Gòn (SCB)</option>
                            <option value="HDBank" ${brand.bankName == 'HDBank' ? 'selected' : ''}>Ngân hàng Thương mại Cổ phần Phát triển Thành phố Hồ Chí Minh (HDBank)</option>
                            <option value="BacABank" ${brand.bankName == 'BacABank' ? 'selected' : ''}>Ngân hàng Thương mại Cổ phần Bắc Á (BacABank)</option>
                            <option value="OCB" ${brand.bankName == 'OCB' ? 'selected' : ''}>Ngân hàng Thương mại Cổ phần Phương Đông (OCB)</option>
                            <option value="Sacombank" ${brand.bankName == 'Sacombank' ? 'selected' : ''}>Ngân hàng Thương mại Cổ phần Sài Gòn Thương Tín (Sacombank)</option>
                        </select>

                        <div class="invalid-feedback">
                            Vui lòng chọn ngân hàng
                        </div>
                    </div>
                </div>
            </div>


            <!-- Account Number -->
            <div class="form-group">
                <label for="accountNumber">Số tài khoản<span class="text-danger">*</span></label>
                <div class="input-group">
                    <input type="text" class="form-control" id="accountNumber" placeholder="Số tài khoản"
                           name="accountNumber" aria-describedby="inputGroupPrepend" pattern="^.{1,20}$" value="${brand.accountNumber}"  required>
                    <div class="invalid-feedback">
                        Số tài khoản có độ dài tối đa 20 kí tự
                    </div>
                </div>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn save-btn">Lưu</button>
                <a href="addBrandIndentifi" class="btn next-btn">Tiếp theo</a>
            </div>
        </form>     

    </div>
</c:if>
<!--<div id="success-message" class="alert alert-success" style="display: none;">
    Thông tin đã được lưu thành công!
</div>-->
<script>
    document.getElementById("addressType").addEventListener("change", function () {
        var newAddressInput = document.getElementById("newAddressInput");
        if (this.value === "new") {
            newAddressInput.style.display = "block";
        } else {
            newAddressInput.style.display = "none";
        }
    });
</script>


<script>
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            var forms = document.getElementsByClassName('needs-validation');
            var validation = Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();
</script>



<!--
<script>
    // Bật kiểm tra tính hợp lệ và chỉ hiển thị thông báo nếu hợp lệ
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            var forms = document.getElementsByClassName('needs-validation');
            Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    if (form.checkValidity() === false) {
                        // Nếu form không hợp lệ, ngăn gửi form
                        event.preventDefault();
                        event.stopPropagation();
                    } else {
                        // Nếu form hợp lệ, tiếp tục gửi form và hiển thị thông báo
                        event.preventDefault();  // Ngăn gửi form ngay lập tức (có thể bỏ dòng này)
                        window.scrollTo({ top: 0, behavior: 'smooth' });
                        
                        // Hiển thị thông báo thành công
                        var successMessage = document.getElementById('success-message');
                        successMessage.style.display = 'block';

                        // Sau 3 giây, ẩn thông báo
                        setTimeout(function () {
                            successMessage.style.display = 'none';
                        }, 3000);
                    }
                    form.classList.add('was-validated'); // Thêm lớp để kiểm tra
                }, false);
            });
        }, false);
    })();
</script>-->
<!--<script>
   document.querySelector('.shop-form').addEventListener('submit', function (event) {
    event.preventDefault();  // Ngăn gửi form (có thể bỏ dòng này nếu muốn gửi form ngay lập tức)
    window.scrollTo({ top: 0, behavior: 'smooth' });
    // Hiển thị thông báo thành công bằng cách hiển thị div
    var successMessage = document.getElementById('success-message');
    successMessage.style.display = 'block';

    // Sau 3 giây, ẩn thông báo
    setTimeout(function () {
        successMessage.style.display = 'none';
    }, 3000);
});
</script>-->