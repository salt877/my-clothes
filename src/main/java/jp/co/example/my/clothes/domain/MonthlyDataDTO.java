package jp.co.example.my.clothes.domain;

/**
 * 購入月毎の服データを格納するDTO.
 * 
 * @author rinashioda
 *
 */
public class MonthlyDataDTO {

	/**
	 * タイトル
	 */
	private String title;

	/**
	 * 購入日
	 */
	private String start;

	/**
	 * URL
	 */
	private String url;

	/**
	 * イベントの色
	 */
	private String color;

	/**
	 * 合計金額
	 */
	private Integer totalPrice;

	/**
	 * アイテム数量
	 */
	private Integer itemQuantity;

	/**
	 * アイテム購入平均額
	 */
	private Integer priceAverage;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Integer itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public Integer getPriceAverage() {
		return priceAverage;
	}

	public void setPriceAverage(Integer priceAverage) {
		this.priceAverage = priceAverage;
	}

}
