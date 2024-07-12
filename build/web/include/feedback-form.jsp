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
    </style>
</head>
<body>
    <h1>Đánh giá dịch vụ</h1>
    <p>We would love to hear about your experience with our service!</p>
    <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="alert alert-danger" role="alert">
            <%= request.getAttribute("errorMessage") %>
        </div>
    <% } %>
    <form action="FeedBackServlet" method="post">
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
<!--            <h3><%= request.getAttribute("ServiceID") %></h3>-->
        </div>
        <div class="form-group">
            <label for="testimonial">Đánh giá của bạn</label>
            <textarea id="testimonial" name="testimonial" placeholder="Share your experience with us..." required></textarea>
        </div>
<!--        <div class="form-group">
            <label for="serviceID">Ngày/tháng/năm</label>
            <input type="hidden" id="experienceDate" name="experienceDate" value="<%= request.getAttribute("BookingDate") %>" required>
            <h3><%= request.getAttribute("experienceDate") %></h3>
        </div>
         <div class="form-group">
            <label for="experienceDate">Ngày/tháng/năm</label>
            <input type="date" id="experienceDate" name="experienceDate" value="<%= request.getAttribute("BookingDate") %>" required readonly>
        </div>-->
        <div class="form-group">
    <label for="satisfactionLevel">Mức độ hài lòng</label>
    <div class="satisfaction-level">
        <input type="radio" id="satisfaction1" name="satisfactionLevel" value="1" required>
        <label for="satisfaction1" class="satisfaction-label">
            <img src="https://cdn.pixabay.com/photo/2016/09/01/08/25/smiley-1635454_960_720.png" alt="Sad">
        </label>
        <input type="radio" id="satisfaction2" name="satisfactionLevel" value="2" required>
        <label for="satisfaction2" class="satisfaction-label">
            <img src="https://e7.pngegg.com/pngimages/647/183/png-clipart-smiley-emoticon-smiley-miscellaneous-smiley-thumbnail.png" alt="Neutral">
        </label>
        <input type="radio" id="satisfaction3" name="satisfactionLevel" value="3" required>
        <label for="satisfaction3" class="satisfaction-label">
            <img src="https://cdn.pixabay.com/photo/2016/09/01/08/25/smiley-1635459_1280.png" alt="Happy">
        </label>
    </div>
</div>

<style>
    .satisfaction-level {
        display: flex;
        align-items: center;
    }
    .satisfaction-label {
        cursor: pointer;
        padding: 5px;
        transition: background-color 0.3s, border 0.3s;
    }
    .satisfaction-label img {
        width: 24px; /* Adjust the size as needed */
        height: 24px; /* Adjust the size as needed */
    }
    .satisfaction-level input[type="radio"] {
        display: none;
    }
    .satisfaction-level input[type="radio"]:checked + .satisfaction-label {
        background-color: #d3d3d3; /* Change to desired background color */
        border: 2px solid #000; /* Change to desired border color */
        border-radius: 5px; /* Optional: make the border rounded */
    }
</style>
        <div class="form-group">
            <button type="submit">Submit</button>
        </div>
    </form>
</body>
</html>