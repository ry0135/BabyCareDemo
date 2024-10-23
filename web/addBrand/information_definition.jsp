<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">




    <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@600;700&family=Montserrat:wght@200;400;600&display=swap" rel="stylesheet"> 

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- Icon Font Stylesheet -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">  
</head>
<style>
    html, body {
        padding: 0;
        margin: 0;
        height: 100%;
        width: 100%;
    }
    body {
        font-family: Arial, sans-serif;
        background-color: #f5f5f5;
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

    .complete-btn {
        background-color: #ff4880;
        color: white;
    }


    .complete-btn:hover {
        background-color: #d9363e;
    }
    .back-btn{
        background-color: #ccc;
        color: white;
    }
    .back-btn:hover {
        background-color: #ccc;
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
        background-color: #ff4880;
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
        background-color: #ff4880;
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
    .container-header {
        display: flex;
        justify-content: space-around;
        background-color: white;
        position: fixed;
        top: 0;
        width: 100%;
        z-index: 1000; /* Đảm bảo header luôn hiển thị trên cùng */
        padding: 10px 20px;
    }
    .container {
        background-color: #f6f6f6;
        min-height: 100vh;
        margin-top: 100px;
        padding: 0; /* Xóa padding */
        width: 100vw; /* Sử dụng toàn bộ chiều rộng màn hình */
        position: relative;
    }

    .info-shop{
        display: flex;
        align-items: center;
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
    .alert {
        display: flex;
        align-items: center;
        padding: 15px;
        background-color: #e7f3fe;
        border: 1px solid #b3d7ff;
        border-radius: 5px;
        color: #31708f;
        font-size: 16px;
    }

    .alert-info {
        background-color: #e7f3fe;
        border-color: #b3d7ff;
    }

    .alert i {
        margin-right: 10px;
        color: #31708f; /* Màu cho biểu tượng thông tin */
    }
    .upload-container {
        display: flex;
        margin-bottom: 20px;
    }

    .upload-label {
        margin-bottom: 5px;
        font-size: 16px;
    }

    .text-danger {
        color: red;
    }

    .upload-area {
        border: 2px dashed #FF5722; /* Màu viền của ô upload */
        border-radius: 8px;
        width: 200px; /* Chiều rộng của ô upload */
        height: 150px; /* Chiều cao của ô upload */
        display: flex;
        justify-content: center;
        align-items: center;
        position: relative;
    }

    .file-input {
        display: none; /* Ẩn input file */
    }

    .upload-preview {
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .add-icon {
        font-size: 40px; /* Kích thước biểu tượng */
        color: #FF5722; /* Màu sắc của biểu tượng */
    }
  

  
    .upload-preview {
    width: 100%; /* Sử dụng toàn bộ chiều rộng của ô upload */
    height: 100%; /* Sử dụng toàn bộ chiều cao của ô upload */
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative; /* Để có thể căn giữa biểu tượng + */
    overflow: hidden; /* Ẩn phần hình ảnh tràn ra ngoài */
}

.img-preview {
    width: 100%;  /* Đặt chiều rộng của ảnh là 100% */
    height: auto; /* Chiều cao tự động để duy trì tỉ lệ khung hình */
    max-height: 100%; /* Giới hạn chiều cao của ảnh để nằm trong khung */
    border-radius: 4px; /* Để tạo các góc tròn cho ảnh */
    position: absolute; /* Để có thể căn giữa ảnh */
    top: 50%; /* Căn giữa theo chiều dọc */
    left: 50%; /* Căn giữa theo chiều ngang */
    transform: translate(-50%, -50%); /* Điều chỉnh để căn giữa chính xác */
}


</style>

<%@include file="header_brand.jsp" %>


<div class="container" style="margin: 0 0; max-width: 2000px">
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

            <div class="alert alert-info" role="alert">
                <i class="fas fa-info-circle"></i>
                Vui lòng cung cấp Thông Tin Định Danh của Chủ Shop (nếu là cá nhân), hoặc Người Đại Diện Pháp Lý trên giấy đăng ký kinh doanh.
            </div>

            <form class="shop-form needs-validation" novalidate action="${pageContext.request.contextPath}/addBrandIndentifi" method="post" enctype="multipart/form-data">
                <!-- Số CCCD -->
                <div class="form-group row">
                    <label class="col-lg-3" for="CCCDNumber">* Số CCCD/CMND <span class="text-danger">*</span></label>
                    <input class="col-lg-3" type="text" id="CCCDNumber" name="CCCDNumber" placeholder="Nhập vào" maxlength="12" pattern="\d{12}" value="${brand.identifiNumber}" required style="width: 320px;">
                    <div class="invalid-feedback">
                        Vui lòng nhập đúng 12 số cho Số CCCD/CMND.
                    </div>
                </div>

                <!-- Họ và Tên -->
                <div class="form-group row">
                    <label class="col-lg-3" for="name">* Họ và Tên</label>
                    <input class="col-lg-3" type="text" id="name" name="name" placeholder="Nhập vào" maxlength="100"  required style="width: 320px;">
                    <div class="invalid-feedback">
                        Vui lòng nhập Họ và Tên.
                    </div>
                </div>

                <!-- Hình chụp của thẻ CMND/CCCD -->
                <div class="upload-container row">
                    <label for="fileUpload" class="upload-label col-lg-3">* Hình chụp của thẻ CMND/CCCD <span class="text-danger">*</span></label>
                    <div class="upload-area col-lg-3" onclick="triggerFileUpload()">
                        <!-- Input file -->
                        <input type="file" id="fileUpload" name="img_CCCD" class="file-input" accept="image/*" required onchange="previewImage(this, document.getElementById('imgPreview1'))">
                        <!-- Icon dấu cộng -->
                        <div class="upload-preview">
                            <img id="imgPreview1" class="img-preview" style="width: 100%; height: auto; display: none;" alt="">
                            <span class="add-icon" id="addIcon1">+</span>
                        </div>
                    </div>

                    <div class="col-lg-2">
                        <img id="imgPreview1" class="img-avatar text-white" style="width: 100px; height: auto;" src="cancuoc/mattruoc.png" alt="">
                    </div>
                    <div class="invalid-feedback">
                        Vui lòng tải lên hình chụp của thẻ CMND/CCCD.
                    </div>
                </div>

                <!-- Ảnh đang cầm CMND/CCCD của bạn -->
                <div class="upload-container row">
                    <label for="fileUpload2" class="upload-label col-lg-3">* Ảnh đang cầm CMND/CCCD của bạn<span class="text-danger">*</span></label>
                    <div class="upload-area col-lg-3" onclick="triggerFileUpload2()">
                        <!-- Input file -->
                        <input type="file" id="fileUpload2" name="img_CCCD_Face" class="file-input" accept="image/*" required onchange="previewImage(this, document.getElementById('imgPreview2'))">
                        <!-- Icon dấu cộng -->
                        <div class="upload-preview">
                            <img id="imgPreview2" class="img-preview" style="width: 100%; height: auto; display: none;" alt="">
                            <span class="add-icon" id="addIcon2">+</span>
                        </div>
                    </div>
                    <div class="col-lg-2">
                        <img id="imgPreview2" class="text-white" style="width: 100px; height: auto;" src="cancuoc/voimat.png" alt="">
                    </div>
                    <div class="invalid-feedback">
                        Vui lòng tải lên ảnh đang cầm CMND/CCCD của bạn.
                    </div>
                </div>
                

                <!-- Checkbox xác nhận -->
                <div class="form-group">
                    <label class="checkbox">
                        <input type="checkbox" name="confirm" required> Tôi xác nhận tất cả dữ liệu đã cung cấp là chính xác và trung thực.
                    </label>
                    <div class="invalid-feedback">
                        Vui lòng xác nhận rằng bạn đã cung cấp dữ liệu chính xác.
                    </div>
                </div>
                <p>
                    ${thongbao}
                </p>
              

                <!-- Buttons -->
                <div class="form-actions" style="justify-content: space-between">
                    <a href="${pageContext.request.contextPath}/addBrand" class="btn back-btn" style="text-decoration: none">quay lại</a>  
                    <button type="submit" class="btn complete-btn">Hoàn tất</button>
                </div>
            </form>
        </div>
    </div>



</c:if>


<script>
    function triggerFileUpload() {
        document.getElementById('fileUpload').click();
    }

    function triggerFileUpload2() {
        document.getElementById('fileUpload2').click();
    }



    function previewImage(input, previewElement) {
        const file = input.files[0];
        const reader = new FileReader();

        reader.onload = function (e) {
            previewElement.src = e.target.result; // Đặt src của img là dữ liệu hình ảnh đã tải lên
            previewElement.style.display = 'block'; // Hiển thị hình ảnh
            const addIcon = previewElement.parentElement.querySelector('.add-icon');
            if (addIcon) {
                addIcon.style.display = 'none'; // Ẩn biểu tượng cộng
            }
        };

        if (file) {
            reader.readAsDataURL(file); // Đọc file và gọi hàm onload
        }
    }


</script>
<!--<script>
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
</script>-->
