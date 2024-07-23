<!DOCTYPE html>
<html lang="en">
    <%@page contentType="text/html" pageEncoding="UTF-8" %>
<head>
    <meta charset="UTF-8">
    <title>Update Category</title>
    
</head>
<body>

<nav class="nav nav-pills nav-justified">
    <!--<a class="nav-item nav-link active" href="#">Thông tin chung</a>-->
</nav>
    <div class="container mt-5">
    <form class="needs-validation mt-3" novalidate action="UpdateCategory" method="post">
        <div class="row">
            <div class="col-12" style="background-color:rgb(255 72 128);border-radius: 10px;    width: 30%;
">
                <h2>Cập nhật danh mục</h2>
            </div>
            <div class="col-md-6 offset-3 mb-3">
                
                <div class="input-group">
                    <input type="hidden" class="form-control" id="categoryID" name="categoryID" value="${category.categoryID}" required>
                
                 </div>`
            </div>
                  <div class="col-md-6 offset-3 mb-3 mt-3">
                <label for="serviceName">Tên danh mục</label>
                <div class="input-group">
                    <input pattern="^.{1,50}$" type="text" class="form-control" id="categoryName" placeholder="Tên danh mục" name="categoryName"
                           aria-describedby="inputGroupPrepend" value="${category.categoryName}" required>
                    <div class="invalid-feedback">
                        Tối đa 1-50 ký tự.
                    </div>`
                </div>
            </div>
                    <div class="col-md-6 offset-3 mb-3">
                <label for="categoryImg">Ảnh</label>
                <div class="input-group">
                    <input type="text" class="form-control" id="categorytImg" placeholder="Ảnh" name="categoryImg"
                           aria-describedby="inputGroupPrepend" value="${category.img}" required>
                    <div class="invalid-feedback">
                        Vui lòng điền vào.
                    </div>
                </div>
            </div>
                    <div class="row">
                <div class="col-3 offset-3">
                    <button class="btn btn-primary" type="submit">Cập nhật</button>
                </div>
                <div class="col-6">${thongbao}</div>
            </div>
        </div>
    </form>
</div>
  
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

<!-- Custom JS for form validation -->
<script>
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            // Fetch all the forms we want to apply custom Bootstrap validation styles to
            var forms = document.getElementsByClassName('needs-validation');
            // Loop over them and prevent submission
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
