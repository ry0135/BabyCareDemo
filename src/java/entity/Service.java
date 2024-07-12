package entity;
import java.text.DecimalFormat;
public class Service {
  DecimalFormat formatter = new DecimalFormat("#,###,###.##");
    private int serviceID;
    private String serviceName;
    private double servicePrice;
    private String description;
    private String listImg;

    public Service() {
    }

    public Service(int serviceID, String serviceName, double servicePrice, String description, String listImg) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.description = description;
        this.listImg = listImg;
    }

    public int getServiceID() {
        return serviceID;
    }
      public DecimalFormat getFormatter() {
        return formatter;
    }
public void setFormatter(DecimalFormat formatter) {
        this.formatter = formatter;
    }
    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePrice() {
        return formatter.format(servicePrice);
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getListImg() {
        return listImg;
    }

    public void setListImg(String listImg) {
        this.listImg = listImg;
    }

    @Override
    public String toString() {
        return "Service{" + "serviceID=" + serviceID + ", serviceName=" + serviceName + ", servicePrice=" + servicePrice + ", description=" + description + ", listImg=" + listImg + '}';
    }

    

    

   

}