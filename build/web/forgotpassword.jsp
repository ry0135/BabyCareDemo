<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Quên mật khẩu</title>
    <style>
        body {
            font-family: 'Nunito', sans-serif;
            font-size: 16px;
            color: #384047;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
        }

        .container-fluid {
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .logo img {
            max-height: 60px;
        }

        .navbar-brand {
            font-size: 36px;
            font-weight: bold;
        }

        .text-primary {
            color: #ff4880;
        }

        .text-secondary {
            color: #4d65f9;
        }

        h1 {
            text-align: center;
            font-size: 32px;
            margin: 20px 0;
            color: #333;
        }

        h3 {
            text-align: center;
            font-size: 20px;
            color: #C23628;
            margin-bottom: 20px;
        }

        form {
            background: #fff;
            padding: 20px;
            max-width: 400px;
            margin: 0 auto;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: #555;
        }

        input[type="email"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 16px;
            color: #333;
        }

        button {
            display: block;
            width: 100%;
            padding: 12px;
            font-size: 18px;
            color: #fff;
            background-color: #28a745;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #218838;
        }

        .modal-content {
            border: none;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .modal-body {
            padding: 20px;
        }
    </style>
</head>
<body>
    <div class="container-fluid"> 
        <div class="row"> 
            <div class="logo col-md-4">
                <img class="img-fluid" src="images/logo.png" alt="Logo">
                <h1 class="navbar-brand text-primary">
                    Baby<span class="text-secondary">Care</span>
                </h1>
            </div>
        </div>
    </div>

    <h1>Quên mật khẩu</h1>

    <div id="forgotPasswordModal" class="modal fade show" role="dialog" style="display: block;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <form action="forgotPassword" method="post">
                        <div class="form-group">
                            <label for="email">Email:</label>
                            <input type="email" class="form-control" id="email" name="email" placeholder="Nhập email của bạn" required>
                        </div>
                        <h3>${thongbao}</h3>
                        <button type="submit">Gửi</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function(){
            $('#forgotPasswordModal').modal('show');
        });
    </script>
</body>
</html>
