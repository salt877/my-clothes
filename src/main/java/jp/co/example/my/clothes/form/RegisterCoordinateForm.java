package jp.co.example.my.clothes.form;

/**
 * コーディネート画面からパラメータを受け取るフォーム.
 * 
 * @author masashi.nose
 *
 */
public class RegisterCoordinateForm {

	/** ファッション雑貨 */
	private String fashionAccessories;
	/** トップス１ */
	private String tops1;
	/** トップス２ */
	private String tops2;
	/** アウター */
	private String outers;
	/** ボトムス */
	private String bottoms;
	/** シューズ */
	private String shoes;
	/** バッグ */
	private String bag;
	/** ワンピース */
	private String dress;

	@Override
	public String toString() {
		return "RegisterCoordinateForm [fashionAccessories=" + fashionAccessories + ", tops1=" + tops1 + ", tops2="
				+ tops2 + ", outers=" + outers + ", bottoms=" + bottoms + ", shoes=" + shoes + ", bag=" + bag
				+ ", dress=" + dress + "]";
	}

	public String getFashionAccessories() {
		return fashionAccessories;
	}

	public void setFashionAccessories(String fashionAccessories) {
		this.fashionAccessories = fashionAccessories;
	}

	public String getTops1() {
		return tops1;
	}

	public void setTops1(String tops1) {
		this.tops1 = tops1;
	}

	public String getTops2() {
		return tops2;
	}

	public void setTops2(String tops2) {
		this.tops2 = tops2;
	}

	public String getOuters() {
		return outers;
	}

	public void setOuters(String outers) {
		this.outers = outers;
	}

	public String getBottoms() {
		return bottoms;
	}

	public void setBottoms(String bottoms) {
		this.bottoms = bottoms;
	}

	public String getShoes() {
		return shoes;
	}

	public void setShoes(String shoes) {
		this.shoes = shoes;
	}

	public String getBag() {
		return bag;
	}

	public void setBag(String bag) {
		this.bag = bag;
	}

	public String getDress() {
		return dress;
	}

	public void setDress(String dress) {
		this.dress = dress;
	}

}
