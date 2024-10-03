<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">

    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@600;700&family=Montserrat:wght@200;400;600&display=swap" rel="stylesheet"> 

    <!-- Icon Font Stylesheet -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="lib/animate/animate.min.css" rel="stylesheet">
    <link href="lib/lightbox/css/lightbox.min.css" rel="stylesheet">
    <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

    <!-- Customized Bootstrap Stylesheet -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="css/style.css" rel="stylesheet">

</head>
<style>
    .container-header {
        display: flex;
        justify-content: space-between;
        background-color: red;
        position: fixed;
        top: 0;
        width: 100%;
        z-index: 1000; /* Đảm bảo header luôn hiển thị trên cùng */
        padding: 10px 20px;
    }
    .container {
        background-color: #ccc;
        height: 100%;
        margin-top: 120px; /* Đảm bảo nội dung không bị header che khuất */
    }

    .info-shop{
        display: flex;
        align-items: center;
    }

    .info-definition{
        background-color: white;
        width: 50%;
        margin-top: 50px;
        margin-left: 25%;
        height: 100%;
    }
</style>
<div class="container-header" >
    <div class="info-shop d-flex">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/index.jsp" class="navbar-brand"><h1 class="text-primary display-6">Baby<span class="text-secondary">Care</span></h1></a>
        </div>
        <div class="title">
            <span>Đăng ký trở thành Người bán BabyCare  </span>
        </div>
    </div>
    <div class="user-info">
        <img class="img-avatar text-white ms-lg-5" style="width: 50px; height: 83%; border-radius: 50%; margin-top: 5px;" src="img/${sessionScope.user.avatar}" alt="">
        <a href="#" class="nav-link bg-primary text-white px-5 ms-lg-3">
            ${sessionScope.user.firstname} ${sessionScope.user.lastname}
        </a>
    </div>
</div>

<div class="container">
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
                <li>
                    <div class="step">
                        <span class="icon inactive">
                            <i class="fas fa-box-open"></i>
                        </span>
                        <span class="step-title">Đã Nhận Được Hàng</span>
                    </div>
                </li>
                <li>
                    <div class="step">
                        <span class="icon inactive"><i class="fas fa-star"></i></span>
                        <span class="step-title">Đánh Giá</span>
                    </div>
                </li>
            </ul>

            <h2>Vui lòng cung cấp Thông Tin Định Danh của Chủ Shop</h2>

            <form class="identification-form" action="#" method="post" enctype="multipart/form-data">
                <!-- Hình Thức Định Danh -->
                <div class="form-group">
                    <label>* Hình Thức Định Danh</label>
                    <div class="radio-group">
                        <label><input type="radio" name="identification" value="cccd" checked> Căn Cước Công Dân (CCCD)</label>
                        <label><input type="radio" name="identification" value="cmnd"> Chứng Minh Nhân Dân (CMND)</label>
                        <label><input type="radio" name="identification" value="passport"> Hộ Chiếu</label>
                    </div>
                </div>

                <!-- Số CCCD -->
                <div class="form-group">
                    <label for="cccd">* Số Căn Cước Công Dân (CCCD)</label>
                    <input type="text" id="cccd" name="cccd" placeholder="Nhập vào" maxlength="12" required>
                </div>

                <!-- Họ và Tên -->
                <div class="form-group">
                    <label for="name">* Họ và Tên</label>
                    <input type="text" id="name" name="name" placeholder="Nhập vào" maxlength="100" required>
                </div>

                <!-- Hình chụp của thẻ CMND/CCCD -->
                <div class="form-group">
                    <label>* Hình chụp của thẻ CMND/CCCD/nội chiếu</label>
                    <div class="upload-group">
                        <input type="file" id="idCardImage" name="idCardImage" accept="image/*" required>
                    </div>
                </div>

                <!-- Ảnh đang cầm CMND/CCCD -->
                <div class="form-group">
                    <label>* Ảnh đang cầm CMND/CCCD/Hộ chiếu của bạn</label>
                    <div class="upload-group">
                        <input type="file" id="selfieImage" name="selfieImage" accept="image/*" required>
                    </div>
                </div>

                <!-- Checkbox xác nhận -->
                <div class="form-group">
                    <label class="checkbox">
                        <input type="checkbox" name="confirm" required> Tôi xác nhận tất cả dữ liệu đã cung cấp là chính xác và trung thực.
                    </label>
                </div>

                <!-- Buttons -->
                <div class="form-actions">
                    <button type="submit" class="btn save-btn">Lưu</button>
                    <button type="button" class="btn complete-btn">Hoàn tất</button>
                </div>
            </form>
        </div>
    </div>
</c:if>
<h3 class="text-danger">${thongbao}</h3>
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

