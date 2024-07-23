<!DOCTYPE html>
<html lang="en">
<head>
    <%@page contentType="text/html" pageEncoding="UTF-8"%>
    <title>Customer Testimonial</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            width: 60%;
            margin: auto;
            padding: 20px;
            background-color: #f9f9f9;
        }
        h1, p {
            text-align: center;
        }
        .form-group {
            margin-bottom: 1.5em;
        }
        .form-group label {
            display: block;
            margin-bottom: 0.5em;
            font-weight: bold;
        }
        .form-group input[type="text"],
        .form-group input[type="date"],
        .form-group textarea {
            width: 100%;
            padding: 0.8em;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .form-group textarea {
            height: 120px;
            resize: vertical;
        }
        .satisfaction-level {
            display: flex;
            justify-content: space-between;
            width: 80%;
            margin: auto;
        }
        .satisfaction-level input[type="radio"] {
            display: none;
        }
        .satisfaction-level label {
            width: 60px;
            height: 60px;
            background-color: lightgray;
            border-radius: 50%;
            text-align: center;
            line-height: 60px;
            cursor: pointer;
            transition: background-color 0.3s;
            display: inline-block;
            background-size: cover;
        }
        .satisfaction-level input[type="radio"]:checked + label {
            background-color: #4CAF50;
        }
        .satisfaction-level label img {
            width: 100%;
            height: 100%;
            border-radius: 50%;
        }
        .form-group button {
            display: block;
            width: 100%;
            padding: 1em;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 1em;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .form-group button:hover {
            background-color: #45a049;
        }
        .fa-star.half::before {
            content: '\f089'; /* Unicode for half star in Font Awesome */
            color: #f5b301;
            position: absolute;
            margin-left: -1em;
            top: 1px;
        }

        /* CSS for star rating */
        .rating {
            display: flex;
            flex-direction: row-reverse;
            justify-content: center;
        }

        .rating input {
            display: none;
        }

        .rating label {
            font-size: 2em;
            color: #ccc;
            cursor: pointer;
            transition: color 0.2s;
        }

        .rating input:checked ~ label {
            color: #f5b301;
        }

        .rating label:hover,
        .rating label:hover ~ label {
            color: #f5b301;
        }

        .comment-rating {
            display: flex;
            justify-content: flex-start;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <h1>Đánh giá dịch vụ</h1>
    <p>We would love to hear about your experience with our service!</p>
  
    <form id="ratingForm" action="FeedBackServlet" method="post" onsubmit="return validateForm()">
        <div class="form-group">
            <label for="customerID"></label>
            <input type="hidden" id="customerID" name="CustomerID" value="<%= request.getAttribute("CustomerID") %>" required readonly>
        </div>
        <div class="form-group">
            <label for="customerName"></label>
            <input type="hidden" id="customerName" name="name" value="<%= request.getAttribute("name") %>" required>
        </div>
        <div class="form-group">
            <label for="serviceID"></label>
            <input type="hidden" id="serviceID" name="ServiceID" value="<%= request.getAttribute("ServiceID") %>" required>
        </div>
        <div class="form-group">
            <label for="testimonial">Đánh giá của bạn</label>
            <textarea id="testimonial" name="testimonial" placeholder="Share your experience with us..." ></textarea>
        </div>

        <!-- Star Rating -->
        <div class="form-group">
            <label>Rating:</label>
            <div class="rating">
                <input type="radio" id="star5" name="rating" value="5" /><label for="star5" title="5 stars">★</label>
                <input type="radio" id="star4" name="rating" value="4" /><label for="star4" title="4 stars">★</label>
                <input type="radio" id="star3" name="rating" value="3" /><label for="star3" title="3 stars">★</label>
                <input type="radio" id="star2" name="rating" value="2" /><label for="star2" title="2 stars">★</label>
                <input type="radio" id="star1" name="rating" value="1" /><label for="star1" title="1 star">★</label>
            </div>
        </div>

        <div class="form-group">
            <button type="submit">Submit</button>
        </div>
    </form>

    <script>
        function validateForm() {
            const ratings = document.getElementsByName('rating');
            let ratingSelected = false;

            for (const rating of ratings) {
                if (rating.checked) {
                    ratingSelected = true;
                    break;
                }
            }

            if (!ratingSelected) {
                alert('Vui lòng đánh giá sao cho sản phẩm.');
                return false; // Ngăn biểu mẫu gửi đi
            }

            return true; // Cho phép biểu mẫu gửi đi
        }
    </script>
</body>
</html>
