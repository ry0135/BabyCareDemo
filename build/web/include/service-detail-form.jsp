
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<div class="container-fluid about bg-light py-5">
    <div class="container py-5">
        <div class="row g-5 align-items-center">
            <div class="col-lg-5 wow fadeInLeft" data-wow-delay="0.2s">
                <div class="about-img pb-5 ps-5">
                    <img src="${service.listImg}" class="img-fluid rounded w-100" style="object-fit: cover;" alt="Image">

                    <div class="about-experience">15 years experience</div>
                </div>
            </div>
            <div class="col-lg-7 wow fadeInRight" data-wow-delay="0.4s">
                <div class="section-title text-start mb-5">

                    <h1 class="display-3 mb-4" style=" font-size: 50px">${service.serviceName}</h1>
                    <p class="mb-4 text-primary auto-line-break  ">${service.description}</p>
                    <div class="mb-4">
                       
                        <h4 class="sub-title pe-3 mb-0">${service.servicePrice}đ</h4>
                    </div>


                </div>
                <form action="bookingservlet" method="get">
                    <c:if test="${sessionScope.user != null && sessionScope.user.role  == 3 || sessionScope.user.role  == 4}">
                        <div class="buttons">
                            <div>
                                <input type="hidden" name="serviceIMG" value="${service.listImg}">
                                <input type="hidden" name="serviceID" value="${service.serviceID}">
                                <input type="hidden" name="servicePrice" value="${service.servicePrice}">
                                <input type="hidden" name="serviceName" value="${service.serviceName}">
                                <input class="btn btn-primary" type="submit" value="Đặt Ngay">
                            </div>
                            <h3>${message}</h3>
                        </div>
                    </c:if>

                    <c:if test="${sessionScope.user == null}">
                        <a href="login.jsp" class="btn btn-primary">Đặt lịch ngay</a>
                    </c:if>
                </form>
            </div>
        </div>
        <div class="mt-5">
            <h1 class="display-3 mb-4">Đánh giá</h1>
            <div class="row">
                <c:forEach items="${feedbackList}" var="feedback">
                    <div class="col-md-6 mb-4">
                        <div class="service-item bg-light d-flex p-4">
                            <div>
                                <h5 class="text-uppercase mb-3">Khách hàng: ${feedback.name}</h5>
                                <p>${feedback.testimonial}</p>
                                <small class="text-secondary">Date: ${feedback.experienceDate}</small><br>
                                <small class="text-secondary">Mức độ hài lòng:
    <div class="satisfaction">
        <div class="face">
            <img class="satisfaction-img" src="${feedback.satisfactionLevel == 3 ? 'https://cdn.pixabay.com/photo/2016/09/01/08/25/smiley-1635459_1280.png' : feedback.satisfactionLevel == 2 ? 'https://e7.pngegg.com/pngimages/647/183/png-clipart-smiley-emoticon-smiley-miscellaneous-smiley-thumbnail.png' : 'https://cdn.pixabay.com/photo/2016/09/01/08/25/smiley-1635454_960_720.png'}" alt="Satisfaction Level">
        </div>
    </div>
</small>

<style>
    .satisfaction-img {
        width: 24px; /* Adjust the width as needed */
        height: 24px; /* Adjust the height as needed */
    }
</style>



                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

                 <script>
            document.addEventListener("DOMContentLoaded", function () {
                const paragraphs = document.querySelectorAll('.auto-line-break');
                paragraphs.forEach(p => {
                    p.innerHTML = p.innerHTML.replace(/\./g, '.<br>');
                });
            });
        </script>