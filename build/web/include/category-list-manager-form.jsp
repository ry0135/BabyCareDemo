<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script><style>
    #pagination {
        margin-top: 10px;
    }

    .paging-button {
        display: inline-block;
        padding: 5px 10px;
        border: 1px solid #ccc;
        background-color: #f7f7f7;
        cursor: pointer;
        text-decoration: none;
    }

    .paging-button.active {
        background-color: #ccc;
    }
 
</style>
    <c:if test="${sessionScope.user==null}">
        <% response.sendRedirect("login.jsp");%>
        </c:if>
   <nav class="nav nav-pills nav-justified">
    <a class="nav-item nav-link " href="category-add.jsp">Thêm danh mục mới</a>
    <a class="nav-item nav-link active" href="CategoryList">Danh sách danh mục</a>
</nav>
 <div class="container mt-3">
    <h2>Quản lí danh sách danh mục</h2>
    <p>Nhân viên có thể xem được danh sách danh mục ở đây</p>
    <input class="form-control" id="myInput" type="text" placeholder="Tìm kiếm">
    <br>
    <table class="table table-bordered" id="data-table">
        <thead>
        <tr>
            <th>CategoryID</th>
            <th>Tên danh mục</th>
            <th>Ảnh</th>
            <th></th>
        </tr>   
        </thead>
        <tbody id="myTable">
       <c:forEach var="category" items="${listCategory}">
            <tr>
                <td>${category.categoryID}</td>
                <td>${category.categoryName}</td>
                <td style="width: 50%"><img style="width: 20%" src="${category.img}" alt="${category.categoryID}"></td>
                <td> <a class="btn btn-success" href="UpdateCategory?categoryID=${category.categoryID}">Cập nhật</a> </td>
<!--               <td><a class="btn btn-success" href="DeleteCategory?categoryID=${category.categoryID}">xóa</a></td>-->
              


             </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div id="pagination" class="text-center"></div>
<script>
    $(document).ready(function () {
        $("#myInput").on("keyup", function () {
            var value = $(this).val().toLowerCase();
            $("#myTable tr").filter(function () {
                $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
            });
        });
    });
</script>
<script>
    var currentPage = 1;
    var recordsPerPage = 10;
    var table = document.getElementById("data-table");
    var rows = table.getElementsByTagName("tr");
    var totalPage = Math.ceil(rows.length / recordsPerPage);

    function showTable(page) {
        var start = (page - 1) * recordsPerPage;
        var end = start + recordsPerPage;

        for (var i = 0; i < rows.length; i++) {
            if (i >= start && i < end) {
                rows[i].style.display = "table-row";
            } else {
                rows[i].style.display = "none";
            }
        }
    }

    function createPagination() {
        var pagination = document.getElementById("pagination");
        pagination.innerHTML = "";

        for (var i = 1; i <= totalPage; i++) {
            var button = document.createElement("a");
            button.href = "#";
            button.innerHTML = i;

            if (i === currentPage) {
                button.classList.add("paging-button", "active");
            } else {
                button.classList.add("paging-button");
            }

            button.addEventListener("click", function () {
                currentPage = parseInt(this.innerHTML);
                showTable(currentPage);

                var currentButton = document.querySelector(".paging-button.active");
                currentButton.classList.remove("active");

                this.classList.add("active");
            });

            pagination.appendChild(button);
        }
    }

    showTable(currentPage);
    createPagination();

</script>
