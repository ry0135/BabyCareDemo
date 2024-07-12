package entity;

public class Category {
    private int CategoryID;
    private String CategoryName;
    private String img;
    public Category() {
    }

    public Category(int CategoryID, String CategoryName) {
        this.CategoryID = CategoryID;
        this.CategoryName = CategoryName;
    }

    public Category(int CategoryID, String CategoryName, String img) {
        this.CategoryID = CategoryID;
        this.CategoryName = CategoryName;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    
    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int CategoryID) {
        this.CategoryID = CategoryID;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }

    @Override
    public String toString() {
        return "Category{" + "CategoryID=" + CategoryID + ", CategoryName=" + CategoryName + '}';
    }
}
