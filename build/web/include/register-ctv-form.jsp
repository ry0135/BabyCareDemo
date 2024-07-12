<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký cửa hàng</title>
    <link rel="stylesheet" href="style.css">
    <style>
        /* CSS cho tiêu đề */
        h1.display-5 {
            font-weight: bold;
            color: #2c3e50;
            text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
        }

        /* CSS cho thông báo lỗi */
        .text-danger {
            font-weight: bold;
            color: #e74c3c !important;
        }

        /* CSS cho nhãn và input */
        label {
            font-weight: bold;
            color: #34495e;
        }

        input[type="text"], input[type="file"], textarea {
            border: 1px solid #ced4da;
            border-radius: 5px;
            padding: 10px;
            width: 100%;
        }

        input[type="text"]:focus, input[type="file"]:focus, textarea:focus {
            border-color: #2980b9;
            box-shadow: 0 0 5px rgba(41, 128, 185, 0.5);
        }

        button[type="submit"]:hover {
            background-color: #2980b9;
        }

        .col-md-6 {
            padding: 10px;
        }

        .offset-3 {
            margin-left: 25%;
        }

        /* CSS cho thông báo */
        .invalid-feedback {
            color: #e74c3c;
            font-size: 0.9em;
        }

        .terms {
            border: 1px solid #ced4da;
            border-radius: 5px;
            padding: 10px;
            max-height: 150px;
            overflow-y: scroll;
            background-color: #f8f9fa;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="container-fluid pt-5">
        <div class="container">
            <div class="border-start border-5 border-primary ps-5 mb-5" style="max-width: 600px;">
                <h1 class="display-5 text-uppercase mb-0">Đăng kí cửa hàng</h1>
            </div>
            <div class="row g-5">
                <div class="col-12">
                    <c:if test="${hasPending}">
                        <h3 class="text-danger">Chúng tôi đã tiếp nhận thông tin của bạn. Chúng tôi sẽ thông báo qua email của bạn trong vòng 7 ngày.</h3>
                    </c:if>
                    <c:if test="${!hasPending}">
                        <h1>${message}</h1>
                        <form class="needs-validation" novalidate action="addBrand" method="post" enctype="multipart/form-data">
                            <div class="row">
                                <!-- Brand Name -->
                                <div class="col-md-6 offset-3 mb-3">
                                    <label for="brandName">Tên cửa hàng<span class="text-danger">*</span></label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="brandName" placeholder="Tên cửa hàng"
                                               name="brandName" aria-describedby="inputGroupPrepend" pattern="^.{1,100}$" required>
                                        <div class="invalid-feedback">
                                            Tên cửa hàng chứa từ 1 đến 100 kí tự
                                        </div>
                                    </div>
                                </div>
                                <!-- Brand Description -->
                                <div class="col-md-6 offset-3 mb-3">
                                    <label for="brandDescription">Mô tả cửa hàng</label>
                                    <div class="input-group">
                                        <textarea class="form-control" id="brandDescription" placeholder="Mô tả cửa hàng"
                                                  name="brandDescription" aria-describedby="inputGroupPrepend" maxlength="200"></textarea>
                                        <div class="invalid-feedback">
                                            Mô tả cửa hàng không quá 200 kí tự
                                        </div>
                                    </div>
                                </div>
                                <!-- Brand Logo -->
                                <div class="col-md-6 offset-3 mb-3">
                                    <div class="input-field col s6">
                                        <label for="brandLogo">Logo cửa hàng <span class="text-danger">*</span></label>
                                        <div class="input-group">
                                            <input id="avatar" type="file" class="form-control" name="logo" value="" required>
                                        </div>
                                    </div>
                                </div>
                                <!-- Brand Address -->
                                <div class="col-md-6 offset-3 ol mb-3">
                                    <label for="address">Địa chỉ cửa hàng<span class="text-danger">*</span></label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="address" placeholder="Địa chỉ cửa hàng"
                                               name="brandAddress" aria-describedby="inputGroupPrepend" pattern="^.{1,100}$" required>
                                        <div class="invalid-feedback">
                                            Địa chỉ có độ dài tối đa 100 kí tự
                                        </div>
                                    </div>
                                </div>
                                <!-- Bank Name -->
                                <div class="col-md-6 offset-3 ol mb-3">
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
                                <div class="col-md-6 offset-3 ol mb-3">
                                    <label for="accountNumber">Số tài khoản<span class="text-danger">*</span></label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="accountNumber" placeholder="Số tài khoản"
                                               name="accountNumber" aria-describedby="inputGroupPrepend" pattern="^.{1,20}$" required>
                                        <div class="invalid-feedback">
                                            Số tài khoản có độ dài tối đa 20 kí tự
                                        </div>
                                    </div>
                                </div>
                                <!-- Terms and Conditions -->
                                <div class="col-md-6 offset-3 mb-3">
                                    <label for="terms">Điều khoản đăng ký bán hàng<span class="text-danger">*</span></label>
                                    <div class="terms" id="terms">
                                        <p><strong>Điều khoản 1:</strong> Người bán hàng phải tuân thủ tất cả các quy định pháp luật liên quan đến hoạt động kinh doanh và bán hàng trực tuyến.</p>
                                        <p><strong>Điều khoản 2:</strong> Người bán cam kết các sản phẩm được rao bán là hàng chính hãng, đảm bảo chất lượng và có nguồn gốc xuất xứ rõ ràng.</p>
                                        <p><strong>Điều khoản 3:</strong> Người bán phải cung cấp thông tin chi tiết về chính sách đổi trả và bảo hành cho khách hàng, đảm bảo quyền lợi của người tiêu dùng.</p>
                                        <p><strong>Điều khoản 4:</strong> Người bán cam kết giá cả các sản phẩm niêm yết trên website là chính xác và không thay đổi trong thời gian diễn ra khuyến mãi (nếu có).</p>
                                        <p><strong>Điều khoản 5:</strong> Người bán phải đảm bảo việc xử lý đơn hàng và giao hàng đúng thời gian cam kết, đồng thời thông báo kịp thời cho khách hàng trong trường hợp có sự cố.</p>
                                        <p><strong>Điều khoản 6:</strong> Người bán cam kết bảo mật thông tin cá nhân của khách hàng và không tiết lộ cho bên thứ ba mà không có sự đồng ý của khách hàng.</p>
                                        <p><strong>Điều khoản 7:</strong> Người bán chịu trách nhiệm bồi thường cho khách hàng trong trường hợp cung cấp sản phẩm kém chất lượng hoặc không đúng mô tả.</p>
                                        <p><strong>Điều khoản 8:</strong> Người bán có quyền chấm dứt hợp đồng bán hàng trong trường hợp khách hàng vi phạm các điều khoản hoặc không tuân thủ quy định của website.</p>
                                        <p><strong>Điều khoản 9:</strong> Người bán cam kết không vi phạm quyền sở hữu trí tuệ của bên thứ ba, bao gồm nhưng không giới hạn ở việc sử dụng hình ảnh, thương hiệu, và mô tả sản phẩm.</p>
                                        <p><strong>Điều khoản 10:</strong> Mọi tranh chấp phát sinh từ hợp đồng bán hàng sẽ được giải quyết thông qua thương lượng hòa giải trước khi đưa ra cơ quan pháp luật có thẩm quyền.</p>
                                    </div>
                                    <div class="form-check mt-2">
                                        <input type="checkbox" class="form-check-input" id="agreeTerms" required>
                                        <label class="form-check-label" for="agreeTerms">Tôi đã đọc và đồng ý với các điều khoản đăng ký bán hàng</label>
                                        <div class="invalid-feedback">
                                            Bạn phải đồng ý với các điều khoản trước khi đăng ký
                                        </div>
                                    </div>
                                </div>

                                <!-- Submit Button -->
                                <div class="row">
                                    <div class="col-3 offset-3">
                                        <button class="btn btn-primary" type="submit">Đăng ký</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </c:if>
                    <h3 class="text-danger">${thongbao}</h3>
                </div>
            </div>
        </div>
    </div>

    <script>
        (function () {
            'use strict';
