<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
 
.product-item {
    position: relative;
    background: #fff;
    border-radius: 10px;
    overflow: hidden;
    transition: transform 0.3s;
}

.product-item:hover {
    transform: scale(1.05);
}

.product-item img {
    height: 200px;
    object-fit: cover;
}

.product-item h6 {
    font-size: 1rem;
    margin-bottom: 0.5rem;
}

.product-item h5 {
    font-size: 1.25rem;
    color: #007bff;
}

/* CSS cho thông báo hết hàng */
.out-of-stock {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5); /* Nền bán trong suốt */
    color: white;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 20px;
    font-weight: bold;
    text-transform: uppercase;
    opacity: 1;
    z-index: 1; /* Đảm bảo thông báo nằm trên cùng */
}
</style>
<div class="container mt-5">
    <!-- Hero Section -->
    <div class="container-fluid py-5 hero-header wow fadeIn" data-wow-delay="0.1s">
        <div class="container py-5">
            <div class="row g-5">
                <div class="col-lg-7 col-md-12">
                    <h1 class="mb-3 text-primary">We Care Your Baby.</h1>
                    <h1 class="mb-5 display-1 text-white">The Best Play Area For Your Kids</h1>
                    <a href="" class="btn btn-primary px-4 py-3 px-md-5 me-4 btn-border-radius">Get Started</a>
                    <a href="" class="btn btn-primary px-4 py-3 px-md-5 btn-border-radius">Learn More</a>
                </div>
            </div>
        </div>
    </div>
    <!-- Preferentials Section -->
    <div class="container-fluid pt-5 mb-5">
        <div class="container">
            <div class="border-start border-5 border-primary ps-5 mb-5" style="max-width: 600px;">
                <h6 class="text-primary text-uppercase">Mã Giảm Giá</h6>
                <h1 class="display-5 text-uppercase mb-0">Siêu Khuyến Mãi</h1>
            </div>
            <div class="row">
                <c:forEach var="preferential" items="${preferentialList}">
                    <div style="height: 350px;" class="pb-5 mb-5 mt-5 col-md-4">
                        <div class="product-item position-relative bg-light d-flex flex-column text-center">
                            <div class="product-img-container position-relative">
                                <img class="img-fluid" src="img/discount/${preferential.preferentiaImg}" alt="Image">
                                <c:if test="${preferential.quantity == 0}">
                                    <div class="out-of-stock">Hết hạn sử dụng</div>
                                </c:if>
                            </div>
                            <h6 class="text-uppercase">${preferential.preferentialName}</h6>
                            <h6 class="text-uppercase">Code : <span style="color: #ff4880;">${preferential.preferential}</span></h6>
                            <h5 class="text-primary mb-0">Số lượng: ${preferential.quantity}</h5>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>