package jp.co.example.my.clothes.form;

import javax.validation.constraints.NotBlank;

import org.springframework.util.StringUtils;

/**
 * コーディネート画面からパラメータを受け取るフォーム.
 * 
 * @author masashi.nose
 *
 */
public class RegisterCoordinateForm {

	/** ID */
	private String id;
	
	/** ユーザーID */
	private String userId;
	
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
	
	/** コーデ名 */
	@NotBlank(message = "コーディネート名は必須です。")
	private String name;
	
	/** 公開コーデフラグ */
	private String isPublic;

	@Override
	public String toString() {
		return "RegisterCoordinateForm [id=" + id + ", userId=" + userId + ", fashionAccessories=" + fashionAccessories
				+ ", tops1=" + tops1 + ", tops2=" + tops2 + ", outers=" + outers + ", bottoms=" + bottoms + ", shoes="
				+ shoes + ", bag=" + bag + ", dress=" + dress + ", name=" + name + ", isPublic=" + isPublic + "]";
	}

	/**
	 * Integer型IDのgetter
	 * 
	 * @return
	 */
	public Integer getIntId() {
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		return Integer.parseInt(id);
	}

	/**
	 * Integer型fashionAccessoriesのgetter
	 * 
	 * @return
	 */
	public Integer getIntFashionAccessories() {
		if (StringUtils.isEmpty(fashionAccessories)) {
			return null;
		}
		return Integer.parseInt(fashionAccessories);
	}

	/**
	 * Integer型tops1のgetter
	 * 
	 * @return
	 */
	public Integer getIntTops1() {
		if (StringUtils.isEmpty(tops1)) {
			return null;
		}
		return Integer.parseInt(tops1);
	}

	/**
	 * Integer型tops2のgetter
	 * 
	 * @return
	 */
	public Integer getIntTops2() {
		if (StringUtils.isEmpty(tops2)) {
			return null;
		}
		return Integer.parseInt(tops2);
	}

	/**
	 * Integer型outersのgetter
	 * 
	 * @return
	 */
	public Integer getIntOuters() {
		if (StringUtils.isEmpty(outers)) {
			return null;
		}
		return Integer.parseInt(outers);
	}

	/**
	 * Integer型bottomsのgetter
	 * 
	 * @return
	 */
	public Integer getIntBottoms() {
		if (StringUtils.isEmpty(bottoms)) {
			return null;
		}
		return Integer.parseInt(bottoms);
	}

	/**
	 * Integer型shoesのgetter
	 * 
	 * @return
	 */
	public Integer getIntShoes() {
		if (StringUtils.isEmpty(shoes)) {
			return null;
		}
		return Integer.parseInt(shoes);
	}

	/**
	 * Integer型bagのgetter
	 * 
	 * @return
	 */
	public Integer getIntBag() {
		if (StringUtils.isEmpty(bag)) {
			return null;
		}
		return Integer.parseInt(bag);
	}

	/**
	 * Integer型dressのgetter
	 * 
	 * @return
	 */
	public Integer getIntDress() {

		if (StringUtils.isEmpty(dress)) {
			return null;
		}
		return Integer.parseInt(dress);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}

}
