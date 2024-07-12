<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm Sản Phẩm</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.2/css/bootstrap.min.css">
    <style>
       body {
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            height: 100vh;
            
            justify-content: center;
            align-items: center;
        }
     
        h2 {
            margin-bottom: 30px;
        }
        .invalid-feedback {
            display: none;
        }
        .input-group {
            margin-bottom: 20px;
        }
        .form-control {
            border-radius: 0.25rem;
        }
        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
            transition: background-color 0.3s ease, border-color 0.3s ease;
        }
        .btn-primary:hover {
            background-color: #0056b3;
            border-color: #0056b3;
        }
    </style>

</head>
<body>
    <nav class="nav nav-pills nav-justified">
        <a class="nav-item nav-link" href="product-list-manager">Danh sách sản phẩm</a>
        <a class="nav-item nav-link active" href="#">Thêm sản phẩm mới</a>
    </nav>
    <div class="container">
        <form class="needs-validation" novalidate action="addproduct" method="post" enctype="multipart/form-data">
            <div class="col-12">
                <h2>Thêm sản phẩm mới</h2>
            </div>
            <div class="row">
                <div class="input-field col s6">
                    <div class="input-group">
                        <input pattern="^.{1,100}$" type="text" class="form-control" id="foodName" placeholder="Tên sản phẩm" name="productName" aria-describedby="inputGroupPrepend" required>
                        <div class="invalid-feedback">
                            Tối đa từ 1-100 ký tự
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s6">
                    <div class="input-group">
                        <input type="text" pattern="^\d{1,8}(.\d{1,2})?$" class="form-control" id="foodPrice" placeholder="Giá sản phẩm" name="productPrice" aria-describedby="inputGroupPrepend" required>
                        <div class="invalid-feedback">
                            Giá tiền chỉ bao gồm chữ số
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s6">
                  <div class="input-group">
                        <select class="form-select" id="productType" name="productType">
                            <option value="Tất cả các mặt hàng dành cho trẻ em"${product.productType.equals("Tất cả các mặt hàng dành cho trẻ em") ? "selected" : ""}>Tất cả các mặt hàng dành cho trẻ em</option>
                            <option value="Quần áo trẻ em"${product.productType.equals("Quần áo trẻ em") ? "selected" : ""}>Quần áo trẻ em</option>
                            <option value="Đồ chơi trẻ em" ${product.productType.equals("Đồ chơi trẻ em") ? "selected" : ""}>Đồ chơi trẻ em</option>
                            <option value="Tã em bé" ${product.productType.equals("Tã em bé") ? "selected" : ""}>Tã em bé</option>
                            <option value="Sữa" ${product.productType.equals("Sữa") ? "selected" : ""}>Sữa</option>
                            <option value="Xe đẩy trẻ em" ${product.productType.equals("Xe đẩy trẻ em") ? "selected" : ""}>Xe đẩy trẻ em</option>
                            <option value="Nội thất trẻ em" ${product.productType.equals("Nội thất trẻ em") ? "selected" : ""}>Nội thất trẻ em</option>
                            <option value="Đồ dùng tắm cho bé" ${product.productType.equals("Đồ dùng tắm cho bé") ? "selected" : ""}>Đồ dùng tắm cho bé</option>
                            <option value="Sách trẻ em" ${product.productType.equals("Sách trẻ em") ? "selected" : ""}>Sách trẻ em</option>
                            <option value="Đồ dùng ăn uống cho bé" ${product.productType.equals("Đồ dùng ăn uống cho bé") ? "selected" : ""}>Đồ dùng ăn uống cho bé</option>
                            <option value="Chăm sóc sức khỏe cho bé" ${product.productType.equals("Chăm sóc sức khỏe cho bé") ? "selected" : ""}>Chăm sóc sức khỏe cho bé</option>                           
                        </select>
                </div>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s6">
                    <div class="input-group">
                        <input type="text" pattern="^\d{1,8}(.\d{1,2})?$" class="form-control" id="productAmount" placeholder="Số lượng sản phẩm" name="productAmount" aria-describedby="inputGroupPrepend" required>
                        <div class="invalid-feedback">
                             Trường này chỉ bao gồm chữ số
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s6">
                    <div class="input-group">
                        <input type="text" pattern="^.{1,30}$" class="form-control" id="productOrigin" placeholder="Xuất xứ sản phẩm" name="productOrigin" aria-describedby="inputGroupPrepend" required>
                        <div class="invalid-feedback">
                            Trường này là bắt buộc, độ dài tối đa 30 ký tự
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s6">
                    <div class="input-group">
                        <input type="text" class="form-control" id="productDescription" placeholder="Mô tả sản phẩm" name="productDescription" aria-describedby="inputGroupPrepend">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s6">
                    <label for="productImg">Đường dẫn ảnh</label>
                    <div class="input-group">
                        <input id="avatar" type="file" class="form-control" name="productImg" value="" required>
                    </div>
                </div>
            </div>
            <div class="row mt-3">
                <div class="col-3">
                    <button class="btn btn-primary" type="submit">Thêm</button>
                </div>
                <div class="col-6">${thongbao}</div>
            </div>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
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
</body>
</html>
