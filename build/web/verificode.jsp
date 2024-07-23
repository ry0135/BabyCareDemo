<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Verification Code</title>
    
    <style>
        body {
            background-color: #f4f4f4;
            font-family: Arial, sans-serif;
        }
        
        .verificode-form {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.3);
            max-width: 400px;
            width: 100%;
            text-align: center;
        }
        
        .verificode-form span {
            display: block;
            margin-bottom: 20px;
            color: #555;
        }
        
        .input-group {
            margin-bottom: 20px;
        }
        
        .form-control {
            width: calc(100% - 40px);
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        
        .btn-u {
            background-color: #5cb85c;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }
        
        .btn-u:hover {
            background-color: #4cae4c;
        }
        
        .text-danger {
            color: #d9534f;
            margin-top: 20px;
        }
        
        #countdown {
            margin-top: 20px;
            font-size: 16px;
            color: #777;
        }
    </style>
</head>
<body>

<div class="verificode-form" id="verificodeForm">
    <span>Hệ thống đã gửi mã kích hoạt đến Email của bạn.</span>
    <span>Xin vui lòng kiểm tra Email để lấy mã kích hoạt tài khoản của bạn.</span>
    <form action="VerificodeServlet" method="post" class="Log-reg-block sky-form">
        <div class="input-group">
            <input type="text" name="authcode" class="form-control" placeholder="Nhập mã kích hoạt" required>
        </div>
        <input type="submit" value="Kích hoạt" class="btn-u btn-u-sea-shop">
    </form>

    <form action="ResendCodeServlet" method="post" class="Log-reg-block sky-form">
        <input type="hidden" name="userID" value="<%= request.getSession().getAttribute("userID") %>">
        <input type="submit" value="Gửi lại mã" class="btn-u btn-u-sea-shop">
    </form>

    <form action="cancelRegistration" method="get" class="Log-reg-block sky-form">  
        <input type="submit" value="Trở về trang đăng kí" class="btn-u btn-u-sea-shop">      
    </form>

    <h3 class="text-danger">${thongbao}</h3>

    <!-- Countdown Timer -->
    <div id="countdown"></div>
</div>

<script>
    // Countdown timer
    var countdownTime = 90; // 90 seconds
    var countdownElement = document.getElementById('countdown');
    
    function startCountdown() {
        var interval = setInterval(function() {
            countdownElement.innerHTML = "Thời gian còn lại: " + countdownTime + " giây";
            countdownTime--;
            
            if (countdownTime < 0) {
                clearInterval(interval);
                // Time's up, redirect to registration page
                window.location.href = 'cancelRegistration'; // Servlet to cancel registration
            }
        }, 1000);
    }
    
    window.onload = startCountdown;
</script>

</body>
</html>
