package entity;

public class OrderAccept {

    private String idOrder, username, date, discountId, total, orderStatus;
    private String address, CTVID;
    private String totalAmount;

    public OrderAccept() {
    }

    public OrderAccept(String idOrder, String username, String date, String discountId, String totalAmount, String orderStatus) {
        this.idOrder = idOrder;
        this.username = username;
        this.date = date;
        this.discountId = discountId;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
    }
   public OrderAccept(String idOrder, String username, String date, String discountId, String orderStatus, String address, String CTVID, String totalAmount) {
        this.idOrder = idOrder;
        this.username = username;
        this.date = date;
        this.discountId = discountId;
        this.orderStatus = orderStatus;
        this.address = address;
        this.CTVID = CTVID;
        this.totalAmount = totalAmount;
    }
    

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCTVID() {
        return CTVID;
    }

    public void setCTVID(String employeeID) {
        this.CTVID = employeeID;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "OrderAccept{" + "idOrder=" + idOrder + ", username=" + username + ", date=" + date + ", discountId=" + discountId + ", total=" + total + ", orderStatus=" + orderStatus + ", address=" + address + ", CTVID=" + CTVID + ", totalAmount=" + totalAmount + '}';
    }
 

   

}
