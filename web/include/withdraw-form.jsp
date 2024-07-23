<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lịch sử rút tiền</title>
        <link rel="stylesheet" href="styles.css">
    </head>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        .container {
            padding: 20px;
        }

        .tabs {
            display: flex;
            margin-bottom: 10px;
        }

        .tab-button {
            padding: 10px 20px;
            border: none;
            background-color: #f0f0f0;
            cursor: pointer;
        }

        .tab-button.active {
            background-color: #00aaff;
            color: white;
        }

        .tab-content {
            display: none;
        }

        .tab-content.active {
            display: block;
        }

        .withdraw-button {
            background-color: #28a745;
            color: white;
            padding: 10px 20px;
            border: none;
            cursor: pointer;
            float: right;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        th {
            background-color: #f2f2f2;
        }

        .form-container {
            margin-top: 20px;
        }

        .form-container input[type="text"], .form-container input[type="submit"] {
            padding: 10px;
            margin: 5px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .form-container input[type="submit"] {
            background-color: #28a745;
            color: white;
            cursor: pointer;
        }
    </style>
    <body>
        <div class="container">        

            <div id="withdraw" class="tab-content active">

                <h3 style="color: green; font-weight: bold;">Số tiền giao dịch tối thiểu là 100.000 VND</h3>

                <div class="form-container">
                    <form id="withdrawForm" action="withdraw" method="post">
                        <input type="hidden" name="brandID" value="${brandID}" />
                        <input type="hidden" name="revenue" value="${revenue}" />

                        <label for="amount">Số tiền cần rút:</label>
                        <input type="text" id="amount" name="amount" placeholder="Nhập số tiền cần rút">
                        <input type="submit" value="Gửi yêu cầu">
                    </form>
                    <h3 style="color: red; font-weight: bold;">${message}</h3>
                    
                

                    <c:if test="${not empty thongbao}">
                         <h3 style="color: green; font-weight: bold;">${thongbao}</h3>
                    </c:if>

                </div>
                <table>
                    <thead>
                        <tr>
                            <th>Ngày yêu cầu</th>                      
                            <th>Số tiền</th>
<!--                            <th>Brand</th>-->
             
                            <th>Trạng thái</th>
                        </tr>
                    </thead>
                    <tbody id="myTable">

                        <c:forEach var="listWithDraw" items="${listWithDraw}">
                            <tr>           
                                <td>${listWithDraw.requestDate}</td>
                                <td class="amount">${listWithDraw.amount}</td>
                                <!--<td >${listWithDraw.brandID}</td>-->

                                <td>
                                    <c:choose>
                                        <c:when test="${listWithDraw.status == '0'}">
                                            <span style="color: red;">Đang xử lý</span>
                                        </c:when>  
                                        <c:when test="${listWithDraw.status == '1'}">
                                            <span style="color: green;">Đã xử lý</span>
                                        </c:when>  
                                    </c:choose>
                                </td>
                            </tr>

                        </c:forEach>

                    </tbody>
                </table>
            </div>
        </div>

        <script>
    document.addEventListener('DOMContentLoaded', function() {
        // Chọn tất cả các phần tử với class 'amount'
        var amountElements = document.querySelectorAll('.amount');
        
        // Tạo một đối tượng Intl.NumberFormat để định dạng số tiền
        var formatter = new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND'
        });
        
        // Duyệt qua tất cả các phần tử và định dạng nội dung của chúng
        amountElements.forEach(function(element) {
            var amount = parseFloat(element.textContent);
            if (!isNaN(amount)) {
                element.textContent = formatter.format(amount);
            }
        });
    });
</script>
    </body>
</html>
