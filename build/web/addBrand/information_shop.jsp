<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${hasPending}">
    <h3 class="text-danger">Chúng tôi đã tiếp nhận thông tin của bạn. Chúng tôi sẽ thông báo qua email của bạn trong vòng 7 ngày.</h3>
</c:if>
<c:if test="${!hasPending}">
    <div class="container">
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
                    <span class="step-title">Đã Xác Nhận Thông Tin Thanh Toán</span>
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
        <form class="shop-form" class="needs-validation" novalidate action="addBrand" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="shop-name">Tên Shop <span class="required">*</span></label>
                <input type="text" class="form-control" id="brandName" placeholder="Tên cửa hàng"
                       name="brandName" aria-describedby="inputGroupPrepend" pattern="^.{1,100}$" value="${brand.brandName}"  required>
                <div class="invalid-feedback">
                    Tên cửa hàng chứa từ 1 đến 100 kí tự
                </div>
            </div>

            <div class="form-group">
                <label>Địa chỉ lấy hàng <span class="required">*</span></label>
                <p id="address-display">
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
                <div class="input-field col s6">
                    <label for="bankName">Tên Ngân Hàng <span class="text-danger">*</span></label>
                    <div class="input-group">
                        <select class="form-control" name="bankName" required>
                            <option value="">Chọn ngân hàng</option>
                            <option value="Vietcombank">Ngân hàng Thương mại Cổ phần Ngoại thương Việt Nam (Vietcombank)</option>
                            <option value="Vietinbank">Ngân hàng Thương mại Cổ phần Công Thương Việt Nam (Vietinbank)</option>
                            <option value="BIDV">Ngân hàng Thương mại Cổ phần Đầu tư và Phát triển Việt Nam (BIDV)</option>
                            <option value="ACB">Ngân hàng Thương mại Cổ phần Á Châu (ACB)</option>
                            <option value="Techcombank">Ngân hàng Thương mại Cổ phần Kỹ Thương Việt Nam (Techcombank)</option>
                            <option value="MB">Ngân hàng Thương mại Cổ phần Quân đội (MB)</option>
                            <option value="SHB">Ngân hàng Thương mại Cổ phần Sài Gòn - Hà Nội (SHB)</option>
                            <option value="DongABank">Ngân hàng Thương mại Cổ phần Đông Á (DongABank)</option>
                            <option value="TPBank">Ngân hàng Thương mại Cổ phần Tiên Phong (TPBank)</option>
                            <option value="VietABank">Ngân hàng Thương mại Cổ phần Bản Việt (VietABank)</option>
                            <option value="SCB">Ngân hàng Thương mại Cổ phần Sài Gòn (SCB)</option>
                            <option value="HDBank">Ngân hàng Thương mại Cổ phần Phát triển Thành phố Hồ Chí Minh (HDBank)</option>
                            <option value="BacABank">Ngân hàng Thương mại Cổ phần Bắc Á (BacABank)</option>
                            <option value="OCB">Ngân hàng Thương mại Cổ phần Phương Đông (OCB)</option>
                            <option value="Sacombank">Ngân hàng Thương mại Cổ phần Sài Gòn Thương Tín (Sacombank)</option>
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
                           name="accountNumber" aria-describedby="inputGroupPrepend" pattern="^.{1,20}$" required>
                    <div class="invalid-feedback">
                        Số tài khoản có độ dài tối đa 20 kí tự
                    </div>
                </div>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn save-btn">Lưu</button>
                <a href="${pageContext.request.contextPath}/addBrand/information_definition.jsp" class="btn next-btn">Tiếp theo</a>

            </div>
        </form>     
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






<style>
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

    .next-btn {
        background-color: #ff4d4f;
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

    .timeline li::after {
        content: '';
        position: absolute;
        top: 50%;
        right: -50%;
        height: 4px;
        width: 100%;
        background-color: #ccc;
        z-index: -1;
    }

    .timeline li.active::after {
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
        background-color: #4CAF50;
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
</style>
