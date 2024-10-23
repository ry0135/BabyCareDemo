
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">
<style>
     .container-header {
        display: flex;
        justify-content: space-around;
        background-color: white;
        position: fixed;
        top: 0;
        width: 100%;
        z-index: 1000; /* Đảm bảo header luôn hiển thị trên cùng */
        padding: 10px 20px;
    }
</style>

<div class="container-header" >
    <div class="info-shop d-flex" style="display: flex; align-items: center">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/index.jsp" class="navbar-brand" style="text-decoration: none;">
                <h1 class=" display-6" style="    font-family: 'Fredoka', sans-serif; color: #ff4880; text-decoration: none;">
                    Baby<span class="" style="color: #42a5f5;">Care</span>
                </h1>
            </a>
        </div>
        <div class="title">
            <span>Đăng ký trở thành Người bán BabyCare  </span>
        </div>
    </div>
    <div class="user-info " style="display: flex; align-items: center">
        <img class="img-avatar text-white ms-lg-5" style="width: 70px; height: 83%; border-radius: 50%; margin-top: 5px;" src="${pageContext.request.contextPath}/img/${sessionScope.user.avatar}" alt="">
        <a class="nav-link text-white px-5 ms-lg-3" style="background-color: #ff4880">
            ${sessionScope.user.firstname} ${sessionScope.user.lastname}
        </a>
    </div>
</div>

