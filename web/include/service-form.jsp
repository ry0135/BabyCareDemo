<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
 /* Basic Reset */
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

/* Category Container */
.category {
    padding: 20px;
    max-width: 800px;
    margin: 0 auto;
    display: flex;
    align-items: center;
    gap: 20px;
}

/* Service List */
.service-list {
    flex: 1;
    display: flex;
}

.service-list a {
    
    font-size: 20px;
    text-decoration: none;
    color: #007bff;
    font-weight: bold;
}

.service-list ul {
    display: flex;
    list-style-type: none;
    margin-top: 10px;
    padding-left: 0;
}

.service-list li { 
    margin: 10px;
}

.service-list li a {
   
    font-size: 16px;
    text-decoration: none;
    color: #555;
}

.service-list li a:hover {
    text-decoration: underline;
    color: #007bff;
}

/* Search Form */
.search-form {
    display: flex;
    align-items: center;
}

.search-form input[type="search"] {
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 16px;
    flex: 1;
}

.search-form button {
    padding: 10px 20px;
    border: none;
    background-color: #007bff;
    color: white;
    border-radius: 4px;
    margin-left: 10px;
    cursor: pointer;
}

.search-form button:hover {
    background-color: #0056b3;
}

</style>
<!-- Services Start -->
<div class="header-carousel-item">
    <img src="images/h/h3.jpg" class="img-fluid w-100" style="height: 600px" alt="Image">
    <div class="carousel-caption">
        <div class="carousel-caption-content p-3">
            <h5 class="text-white text-uppercase fw-bold mb-4" style="letter-spacing: 3px;">Trung Tâm Dịch Vụ</h5>
            <h1 class="display-1 text-capitalize text-white mb-4">Giải Pháp Tốt Nhất Cho Bạn</h1>
            <p class="mb-5 fs-5">Chúng tôi luôn sẵn sàng mang lại những dịch vụ tốt nhất cho bạn.</p>
        </div>
    </div>
</div>

<div class="container-fluid py-5">
    <div class="container">
        <div class="border-start border-5 border-primary ps-5 mb-5" style="max-width: 600px;">
            <h6 class="text-primary text-uppercase">DỊCH VỤ</h6>
            <h1 class="display-5 text-uppercase mb-0">ĐẶT DỊCH VỤ TRỰC TUYẾN CHO BÉ CỦA BẠN</h1>
        </div>
        <div class="category">
        <div class="service-list">
            <a href="listService">Service:</a>
            <ul>
                <c:forEach items="${listC}" var="t">
                    <li><a href="ServiceTypeServlet?tid=${t.type}">${t.typeName}</a></li>
                </c:forEach>
            </ul>
        </div>
        <div class="search">
            <form class="search-form" action="searchService" method="post">
                <input type="search" name="txt" placeholder="Search Service...">
                <button type="submit">Search</button>
            </form>
        </div>
    </div>
        
    </div>
    <div class="row g-5">
        <c:forEach items="${ListS}" var="S">
            <div class="col-md-6">
                <div class="service-item bg-light d-flex p-4">
                    <div class="row">
                        <div class="col-5">
                            <img src="${S.listImg}" style="width: 200px; height: 180px;">
                        </div>
                        <div class="col-lg-7">
                            <h4 class="sub-title pe-3 mb-0">${S.serviceName}</h4>
                            <h5 style="color: red">${S.getServicePrice()}đ</h5>
                            <a class="text-primary text-uppercase" href="getservicedetail?serviceID=${S.serviceID}">Xem thêm<i class="bi bi-chevron-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    
</div>
</div>
<!-- Services End -->

<div class="row mt-5">
    <div class="col-12">
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <c:forEach var="i" begin="1" end="${numberPage}">
                    <li class="page-item <c:if test="${i == currentPage}">active</c:if>">
                        <a class="page-link" href="listService?page=${i}">${i}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </div>
</div>
<div class="container-fluid about bg-light py-5">
    <div class="container py-5">
        <div class="row g-5 align-items-center">
            <div class="col-lg-5 wow fadeInLeft" data-wow-delay="0.2s">
                <div class="about-img pb-5 ps-5">
                    <img src="images/y/y5.jpg" class="img-fluid rounded w-100" style="object-fit: cover;" alt="Image">
                </div>
            </div>
            <div class="col-lg-7 wow fadeInRight" data-wow-delay="0.4s">
                <div class="section-title text-start mb-5">
                    <h4 class="sub-title pe-3 mb-0">About Us</h4>
                    <h1 class="display-3 mb-4">Chúng tôi luôn sẵn sàng mang lại những dịch vụ tốt nhất cho bạn.</h1>
                    <p class="mb-4">Babycare là trang thông tin dành cho Bố và Bé, chuyên cung cấp các dịch vụ tiện ích cho em bé. Nhằm giúp cho độc giả có những quyết định tốt trong việc chăm sóc sức khỏe, xây dựng đời sống gia đình và nuôi dạy trẻ tốt hơn</p>
                    <div class="mb-4">
                        <p class="text-secondary"><i class="fa fa-check text-primary me-2"></i> Dịch vụ chuyên nghiệp và nhanh chóng</p>
                        <p class="text-secondary"><i class="fa fa-check text-primary me-2"></i> Cung cấp đồ trang trí, nội thất và phụ kiện theo yêu cầu</p>
                        <p class="text-secondary"><i class="fa fa-check text-primary me-2"></i> Chi Phí hợp lý</p>
                    </div>
                    <a href="#" class="btn btn-primary rounded-pill text-white py-3 px-5">Discover More</a>
                </div>
            </div>
        </div>
    </div>
</div>
