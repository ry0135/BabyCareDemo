
package entity;

public class Brand {
    private String brandID;
    private String brandName;
    private String brandDescription;
    private String brandLogo;
    private String brandAddess;
    private String CTVID;
    private String bankName;
    private String accountNumber;
    private String identifiNumber;
    private String identifiImg;
    private String identifiImgFace;


    private int Status;
    
    
    public Brand() {
    }

    public Brand(String brandID, String brandName, String brandDescription, String brandLogo, String brandAddess) {
        this.brandID = brandID;
        this.brandName = brandName;
        this.brandDescription = brandDescription;
        this.brandLogo = brandLogo;
        this.brandAddess = brandAddess;
        
    }

    public Brand(String brandID, String brandName, String brandDescription, String brandLogo, String brandAddess, String CTVID, int Status) {
        this.brandID = brandID;
        this.brandName = brandName;
        this.brandDescription = brandDescription;
        this.brandLogo = brandLogo;
        this.brandAddess = brandAddess;
        this.CTVID = CTVID;
        this.Status = Status;
    }

    public Brand(String brandID, String brandName, String brandDescription, String brandLogo, String brandAddess, String CTVID, String bankName, String accountNumber, int Status) {
        this.brandID = brandID;
        this.brandName = brandName;
        this.brandDescription = brandDescription;
        this.brandLogo = brandLogo;
        this.brandAddess = brandAddess;
        this.CTVID = CTVID;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.Status = Status;
    }

    public Brand(String brandID, String brandName, String brandDescription, String brandLogo, String brandAddess, String CTVID, String bankName, String accountNumber, String identifiNumber, String identifiImg, String identifiImgFace, int Status) {
        this.brandID = brandID;
        this.brandName = brandName;
        this.brandDescription = brandDescription;
        this.brandLogo = brandLogo;
        this.brandAddess = brandAddess;
        this.CTVID = CTVID;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.identifiNumber = identifiNumber;
        this.identifiImg = identifiImg;
        this.identifiImgFace = identifiImgFace;
        this.Status = Status;
    }

    public String getIdentifiNumber() {
        return identifiNumber;
    }

    public void setIdentifiNumber(String identifiNumber) {
        this.identifiNumber = identifiNumber;
    }

    public String getIdentifiImg() {
        return identifiImg;
    }

    public void setIdentifiImg(String identifiImg) {
        this.identifiImg = identifiImg;
    }

    public String getIdentifiImgFace() {
        return identifiImgFace;
    }

    public void setIdentifiImgFace(String identifiImgFace) {
        this.identifiImgFace = identifiImgFace;
    }
    
    

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    
    
    
    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

   
    
    public String getCTVID() {
        return CTVID;
    }

    public void setCTVID(String CTVID) {
        this.CTVID = CTVID;
    }
    

    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandDescription() {
        return brandDescription;
    }

    public void setBrandDescription(String brandDescription) {
        this.brandDescription = brandDescription;
    }

    public String getBrandLogo() {
        return brandLogo;
    }

    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo;
    }

    public String getBrandAddess() {
        return brandAddess;
    }

    public void setBrandAddess(String brandAddess) {
        this.brandAddess = brandAddess;
    }

    @Override
    public String toString() {
        return "Brand{" + "brandID=" + brandID + ", brandName=" + brandName + ", brandDescription=" + brandDescription + ", brandLogo=" + brandLogo + ", brandAddess=" + brandAddess + ", CTVID=" + CTVID + ", bankName=" + bankName + ", accountNumber=" + accountNumber + ", Status=" + Status + '}';
    }
    
         

   
   
    

    
}
