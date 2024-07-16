<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="include/header-admin.jsp" %>

<div class="container-fluid pt-5">
    <div id="page-wrapper">
        <div class="container">
            <div class="border-start border-5 border-primary ps-5 mb-5" style="max-width: 600px;">
                <h6 class="text-primary text-uppercase">Thống kê</h6>
                <h1 class="display-5 text-uppercase mb-0">Thống kê bán hàng của hệ thống</h1>
            </div>
            <div class="col-md-12 statistical d-flex justify-content-around mb-5">
                <div class="d-flex register m-1">
                    <div style="background-color: orange;" class="icon"><i class="fa-solid fa-person-circle-check"></i></div>
                    <div class="detail">
                        <div>${numberOfOrderToConfirm}</div>
                        <div>Tổng đơn hàng đợi xét duyệt</div>
                    </div>
                </div>
                <div class="d-flex user m-1">
                    <div style="background-color: rgba(0, 0, 255, 0.726);" class="icon"><i class="fa-solid fa-user"></i></div>
                    <div class="detail">
                        <div>${numberOfUsers}</div>
                        <div>Số tài khoản khách hàng đã đăng kí</div>
                    </div>
                </div>
                <div class="d-flex user m-1">
                    <div style="background-color: rgba(0, 0, 255, 0.726);" class="icon"><i class="fa-solid fa-user"></i></div>
                    <div class="detail">
                        <div>${numberOfCTV}</div>
                        <div>Số tài khoản cộng tác viên đã đăng kí</div>
                    </div>
                </div>
                <div class="d-flex pet-sold m-1">
                    <div style="background-color: #7AB730;" class="icon"><i class="fa-solid fa-paw"></i></div>
                    <div class="detail">
                        <div>${numberOfProductLeft}</div>
                        <div>Tổng số sản phẩm đang bán</div>
                    </div>
                </div>
                <div class="d-flex money m-1">
                    <div style="background-color: rgb(204, 0, 204);" class="icon"><i class="fa-solid fa-coins"></i></div>
                    <div class="detail">
                        <div>${orderRevenue}</div>
                        <div>Doanh thu bán hàng trong năm nay</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container mt-3">
<!--            <div class="col-md-12 statistical d-flex justify-content-around mb-5" style="margin-top: 10px">
                <form action="Sratistic" method="post" class="d-flex flex-column align-items-center">
                    <div class="d-flex money m-1 align-items-center">
                        <div style="background-color: rgb(204, 0, 204);" class="icon"><i class="fa-solid fa-coins"></i></div>
                        <div class="detail">
                            <p>${monthlyRevenue}</p>
                            <div class="form-group mb-3">
                               <label for="month"></label>
                                <select id="month" name="month" class="form-select" >
                                    <option style="font-size: 14px" value="1">Tháng 1</option>
                                    <option value="2">Tháng 2</option>
                                    <option value="3">Tháng 3</option>
                                    <option value="4">Tháng 4</option>
                                    <option value="5">Tháng 5</option>
                                    <option value="6">Tháng 6</option>
                                    <option value="7">Tháng 7</option>
                                    <option value="8">Tháng 8</option>
                                    <option value="9">Tháng 9</option>
                                    <option value="10">Tháng 10</option>
                                    <option value="11">Tháng 11</option>
                                    <option value="12">Tháng 12</option>
                                </select>
                            </div>
                            <div class="form-group mb-3">
                                <label for="year"></label>
                                <select id="year" name="year" class="form-select" >
                                    <option style="font-size: 14px" value="2020">2020</option>
                                    <option value="2021">2021</option>
                                    <option value="2022">2022</option>
                                    <option value="2023">2023</option>
                                    <option value="2024">2024</option>
                                    <option value="2025">2025</option>
                                    <option value="2026">2026</option>
                                    <option value="2027">2027</option>
                                    <option value="2028">2028</option>
                                    <option value="2029">2029</option>
                                    <option value="2030">2030</option>
                                    <option value="2031">2031</option>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-primary mt-3">Xem thống kê</button>
                        </div>
                    </div>
                </form>
            </div>-->
                            <form action="Sratistic" method="post">
                                
                                <label for="yearRevenue">Doanh thu theo năm</label>
                              
                                        <span>${yearRevenue}</span>
                                <select id="yearRevenue" name="yearRevenue" class="form-select">
                                    <c:forEach var="year" begin="2020" end="2031">
                                        <option value="${year}" <c:if test="${year == yearRevenue}">selected</c:if>>${year}</option>
                                    </c:forEach>
                                    </select>
                                  <button type="submit" class="btn btn-primary mt-3">Xem thống kê</button>
                            </form>                
                            <div class="container mt-3">
                                <div class="col-md-12">
                                    <canvas id="revenueChartYear" width="800" height="400"></canvas>
                                </div>
                            </div>
                            <div class="container mt-3">
                                 <label for="yearRevenue">Doanh thu theo từng năm</label>
                            <div class="col-md-12">
                                <canvas id="revenueChart" width="800" height="400"></canvas>
                            </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    
<script>
    // Lấy dữ liệu từ request attribute
    var revenue2020 = ${revenue2020};
    var revenue2021 = ${revenue2021};
    var revenue2022 = ${revenue2022};
    var revenue2023 = ${revenue2023};
    var revenue2024 = ${revenue2024};
    var revenue2025 = ${revenue2025};
    var revenue2026 = ${revenue2026};
    var revenue2027 = ${revenue2027};
    var revenue2028 = ${revenue2028};
    var revenue2029 = ${revenue2029};
    var revenue2030 = ${revenue2030};

    // Biểu đồ doanh thu theo từng năm
    var ctx = document.getElementById('revenueChart').getContext('2d');
    var revenueChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['2020', '2021', '2022', '2023', '2024', '2025', '2026', '2027', '2028', '2029', '2030'],
            datasets: [{
                label: 'Doanh thu theo từng năm',
                data: [revenue2020, revenue2021, revenue2022, revenue2023, revenue2024, revenue2025, revenue2026, revenue2027, revenue2028, revenue2029, revenue2030],
                backgroundColor: 'rgba(54, 162, 235, 0.5)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true,
                        callback: function(value) {
                            return value.toLocaleString('en-US', { style: 'currency', currency: 'USD' });
                        }
                    }
                }]
            },
            tooltips: {
                callbacks: {
                    label: function(tooltipItem) {
                        return tooltipItem.yLabel.toLocaleString('en-US', { style: 'currency', currency: 'USD' });
                    }
                }
            }
        }
    });
</script>

<script>
    // Lấy dữ liệu từ request attribute
    var m1 = ${m1};
    var m2 = ${m2};
    var m3 = ${m3};
    var m4 = ${m4};
    var m5 = ${m5};
    var m6 = ${m6};
    var m7 = ${m7};
    var m8 = ${m8};
    var m9 = ${m9};
    var m10 = ${m10};
    var m11 = ${m11};
    var m12 = ${m12};


    // Biểu đồ doanh thu theo từng năm
    var ctx = document.getElementById('revenueChartYear').getContext('2d');
    var revenueChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
            datasets: [{
                label: 'Doanh thu theo từng năm',
                data: [m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11,m12],
                backgroundColor: 'rgba(54, 162, 235, 0.5)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true,
                        callback: function(value) {
                            return value.toLocaleString('en-US', { style: 'currency', currency: 'USD' });
                        }
                    }
                }]
            },
            tooltips: {
                callbacks: {
                    label: function(tooltipItem) {
                        return tooltipItem.yLabel.toLocaleString('en-US', { style: 'currency', currency: 'USD' });
                    }
                }
            }
        }
    });
</script>
            <div class="container mt-3" style="background-color: #f3f3f3">
                <h2>Danh sách sản phẩm bán chạy nhất</h2>
                <p>Đây là danh sách các mặt hàng sản phẩm bán chạy nhất</p>
                <br>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Thứ hạng</th>
                            <th>ID sản phẩm</th>
                            <th>Tên sản phẩm</th>
                            <th>Số lượng đã bán</th>
                        </tr>
                    </thead>
                    <tbody id="myTable">
                        <c:forEach var="product" items="${listProduct}">
                            <tr>
                                <td>${listProduct.indexOf(product) + 1}</td>
                                <td>${product.productId}</td>
                                <td>${product.productName}</td>
                                <td>${product.productAmount}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>



<style>
    .statistical .icon {
        color: white;
        align-self: center;
        padding: 30px 20px;
        font-size: xx-large;
        border-radius: 10% 0 0 10%;
    }

    .statistical .detail {
        background-color: rgba(128, 128, 128, 0.116);
        border-radius: 0 10% 10% 0;
        width: 150px;
        font-size: smaller;
        text-align: center;
    }

    .statistical .detail :first-child {
        font-size: 40px;
        width: 100%;
        padding-top: 5%;
    }

    .statistical .money .detail {
        width: 250px !important;
    }

    .form-select {
        width: 100%;
        margin-top: 10px;
    }

    .btn {
        display: block;
        width: 100%;
    }

    .form-group {
        margin-bottom: 15px;
    }

    .form-group label {
        font-weight: bold;
    }

    .form-group select {
        width: 100%;
    }

    .btn-primary {
        background-color: #007bff;
        border-color: #007bff;
    }
    .form-select option{
        font-size: 14px;
    }

    .btn-primary:hover {
        background-color: #0056b3;
        border-color: #0056b3;
    }
    .small-select {
    width: 100px; /* Adjust the width as needed */
    font-size: 12px; /* Adjust the font size as needed */
    padding: 5px; /* Adjust the padding as needed */
}
</style>
