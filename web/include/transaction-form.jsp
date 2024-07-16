
    <div class="container">
        <h1>L?ch s? giao d?ch</h1>
        <table class="table">
            <thead>
                <tr>
                    <th>Ngày</th>
                    <th>Lo?i</th>
                    <th>Ti?n</th>

                 
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${completedOrders}" var="completedOrders">
                    <tr>
                        <td><c:out value="${completedOrders.date}"/></td>
                        <td><c:out value="${completedOrders.idOrder}"/></td>
                                            <td>+<c:out value="${completedOrders.totalAmount}"/>vnd</td>

                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
 