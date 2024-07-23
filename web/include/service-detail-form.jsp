
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
        .stars-outer {
            display: inline-block;
            position: relative;
            font-family: 'Font Awesome 5 Free';
            font-weight: 900;
        }
        .stars-outer::before {
            content: '\f005 \f005 \f005 \f005 \f005'; /* Unicode character for stars */
            font-size: 1rem;
            color: #ccc; /* Color of empty stars */
        }
        .stars-inner {
            position: absolute;
            top: 0;
            left: 0;
            white-space: nowrap;
            overflow: hidden;
            width: 0;
            color: #f8ce0b; /* Color of filled stars */
        }
        .stars-inner::before {
            content: '\f005 \f005 \f005 \f005 \f005'; /* Unicode character for stars */
            font-size: 1rem;
        }
                .fa-star {
                    color: #f5b301;
                }

                .fa-star.half::before {
                    content: '\f089'; /*    Unicode for half star in Font Awesome */
                    color: #f5b301;
                    position: absolute;
                    margin-left: -1em;
                    top: 1px;
                }
    </style>
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
                    <div class="comment-rating">
<!--                            <p style="color: red; text-decoration: underline">${getAverageRating}</p>-->
                            <c:forEach var="i" begin="1" end="5">
                                <i class="fa fa-star" style="position: relative; color: ${i <= getAverageRating? '#f5b301' : (i == Math.ceil(getAverageRating) && getAverageRating % 1 != 0 ? 'transparent' : '#ccc')};">
                                    <c:if test="${i == Math.ceil(getAverageRating) && getAverageRating % 1 != 0}">
                                        <i class="fa fa-star half"></i>
                                    </c:if>
                                </i>
                            </c:forEach>
                        </div>
                        <div style="border-left: 1px solid rgba(0, 0, 0, .14); margin-left: 20px;">
                            <p style="margin-left: 20px;">
                                <span style="color: red;">${EvaluateRating}</span> 
                                <span style="color: black;">đánh giá</span>
                            </p>
                        </div>
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
                                <div class="comment-rating">
                                    <c:forEach var="i" begin="1" end="5">
                                        <i class="fa fa-star" style="color: <c:choose>
                                               <c:when test="${i <= feedback.satisfactionLevel}">#f5b301</c:when>
                                               <c:otherwise>#ccc</c:otherwise>
                                           </c:choose>;"></i>
                                    </c:forEach>
                                </div>
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
        
        
    