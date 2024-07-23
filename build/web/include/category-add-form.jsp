
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="nav nav-pills nav-justified">
    <a class="nav-item nav-link active" href="category-add.jsp">Thêm danh mục mới</a>
    <a class="nav-item nav-link" href="CategoryList">Danh sách danh mục</a>
</nav>
<div class="container"> 
    <div class="row">
        <form class="needs-validation mt-3" novalidate action="AddCategory" method="post">
            <div class="row">
                <div class="col-12" style="background-color:#7ab730;border-radius: 10px;">
                    <h2>Thêm danh mục mới</h2>
                </div>
                <%-- Input Service Name --%>
                <div class="col-md-6 offset-3 mb-3 mt-3">
                    <label for="categoryName">Tên danh mục</label>
                    <div class="input-group">
                        <input pattern="^.{1,50}$" type="text" class="form-control" id="categoryName" placeholder="Tên danh mục" name="Name" aria-describedby="inputGroupPrepend" required>
                        <div class="invalid-feedback">
                            Tối đa từ 1-50 ký tự
                        </div>
                    </div>
                </div>
                 <%-- Input Service Image URL --%>
                <div class="col-md-6 offset-3 mb-3">
                    <label for="categoryImg">Đường dẫn ảnh</label>
                    <div class="input-group">
                        <input type="text" class="form-control" id="categoryImg" placeholder="URL" name="categoryImg" aria-describedby="inputGroupPrepend" required>
                        <div class="invalid-feedback">
                            Vui lòng điền vào
                        </div>
                    </div>
                </div>
                  <%-- Submit Button and Message --%>
                <div class="row">
                    <div class="col-3 offset-3">
                        <button class="btn btn-primary" type="submit">Thêm</button>
                    </div>
                    <div class="col-6">${thongbao}</div>
                </div>
            </div>
        </form>
    </div>
</div>
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