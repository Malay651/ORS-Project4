package in.co.rays.proj4.bean;

import java.util.Date;

public class ItemInformationBean extends BaseBean {

    private long id;
    private String title;
    private String overview;
    private long cost;
    private Date purchasedate;
    private String category;

    public long getId() {
        return id;
    }

    // FIX: primitive long use karo
    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public Date getPurchasedate() {
        return purchasedate;
    }

    public void setPurchasedate(Date purchasedate) {
        this.purchasedate = purchasedate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /* ================= ORS REQUIRED METHODS ================= */

    @Override
    public String getKey() {
        return id+"";
    }

    @Override
    public String getValue() {
        return title;
    }

	@Override
	public String toString() {
		return "ItemInformationBean [id=" + id + ", title=" + title + ", overview=" + overview + ", cost=" + cost
				+ ", purchasedate=" + purchasedate + ", category=" + category + "]";
	}
    
    
}
