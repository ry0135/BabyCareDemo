
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
    body {
        background-color: #f8f9fa;
    }

    .container-fluid {
        padding-top: 50px;
    }

    .container {
        background: white;
        padding: 30px;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        max-width: 2000px;
        margin: auto;
        margin-top: 50px;
    }

    h2 {
        margin-bottom: 20px;
    }

    p {
        margin-bottom: 20px;
    }

    .form-control {
        border-radius: 5px;
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

    .table th,
    .table td {
        text-align: center;
        vertical-align: middle;
    }

    #pagination {
        margin-top: 20px;
    }

    .paging-button {
        display: inline-block;
        padding: 5px 10px;
        border: 1px solid #007bff;
        background-color: white;
        color: #007bff;
        cursor: pointer;
        text-decoration: none;
        border-radius: 5px;
        margin: 0 2px;
    }

    .paging-button.active {
        background-color: #007bff;
        color: white;
    }
</style>

<div class="container-fluid pt-5 ">

    <div id="page-wrapper">
        
        <div class="container mt-3">
            <h2>Quản lí danh sách hoàn tiền sản phẩm</h2>
            <p>Admin có thể xem được danh sách hoàn tiền sản phẩm</p>
            <input class="form-control" id="myInput" type="text" placeholder="Tìm kiếm">
            <br>
            <table class="table table-bordered" id="data-table">
                <thead>
                    <tr>
                <th>Mã đơn hoàn tiền</th>
                <th>Mã khách hàng</th>
                <th>Tên Khách hàng</th>
                <th>Mã đơn hàng</th>
                <th>Tên Ngân Hàng</th>
                <th>Tên Tài Khoản</th>
                <th>Số Tài khoản</th>
                <th>Giá</th>
                <th>Ngày</th>
                <th>Trạng Thái</th>
                <th>Lý Do</th>
                <th>Action</th>

            </tr>

                </thead>
                <tbody id="myTable">

                <c:forEach var="b" items="${listB}">



                     <tr>
                    <td>${b.refundID}</td>
                    <td>${b.userID}</td>
                    <td>${b.fullName}</td>
                    <td>${b.billID}</td>
                    <td>${b.bankName}</td>
                    <td>${b.name}</td>
                    <td>${b.accountNumber}</td>
                    <td>${b.refundAmount}</td>
                    <td>${b.refundDate}</td>
                    <td>
                        
                        <c:choose>
                           
                            <c:when test="${b.refundStatus == 0}">
                                Đang xử lý ...
                            </c:when>
                            <c:when test="${b.refundStatus == 1}">
                                Đã hoàn tiền
                            </c:when>
                            
                        </c:choose>
                    </td>
                    <td>${b.note}</td>
                    <td>
                            <c:if test="${b.refundStatus == 0}">
                                <form action="ApproveOrderRefund" method="get">
                                    <input type="hidden" name="refundID" value="${b.refundID}">
                                    <input type="hidden" name="billID" value="${b.billID}">
                                    <button type="submit" class="btn btn-success btn-sm">Hoàn tiền</button>
                                </form>
                            </c:if>
                        </td>
                </tr>


                </c:forEach>

                </tbody>
            </table>

            <div id="pagination" class="text-center"></div>
        </div>
    </div>
</div>
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


