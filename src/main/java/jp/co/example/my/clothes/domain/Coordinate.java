package jp.co.example.my.clothes.domain;

import java.util.List;

/**
 * coorinatesテーブルのカラム情報を持つドメイン.
 * 
 * @author masashi.nose
 *
 */
public class Coordinate {
	/** コーディネートID */
	private Integer id;
	/** ユーザーID */
	private Integer userId;
	/** ファッション雑貨 */
	private Integer fashionAccessories;
	/** トップス１の服ID */
	private Integer tops1;
	/** トップス２の服ID */
	private Integer tops2;
	/** アウターの服ID */
	private Integer outers;
	/** ボトムスの服ID */
	private Integer bottoms;
	/** シューズの服ID */
	private Integer shoes;
	/** バッグの服ID */
	private Integer bag;
	/** ワンピースの服ID */
	private Integer dress;
	/** 削除フラグ */
	private boolean deleted;
	/** コーディネート名 */
	private String name;
	/** 公開フラグ */
	private boolean isPublic;
	/** ユーザー情報格納用インスタンス */
	private User user;
	/** 服情報を詰めるリスト（コーディネート情報） */
	private List<Clothes> clothesList;

	@Override
	public String toString() {
		return "Coordinate [id=" + id + ", userId=" + userId + ", fashionAccessories=" + fashionAccessories + ", tops1="
				+ tops1 + ", tops2=" + tops2 + ", outers=" + outers + ", bottoms=" + bottoms + ", shoes=" + shoes
				+ ", bag=" + bag + ", dress=" + dress + ", deleted=" + deleted + ", name=" + name + ", isPublic="
				+ isPublic + ", user=" + user + ", clothesList=" + clothesList + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getFashionAccessories() {
		return fashionAccessories;
	}

	public void setFashionAccessories(Integer fashionAccessories) {
		this.fashionAccessories = fashionAccessories;
	}

	public Integer getTops1() {
		return tops1;
	}

	public void setTops1(Integer tops1) {
		this.tops1 = tops1;
	}

	public Integer getTops2() {
		return tops2;
	}

	public void setTops2(Integer tops2) {
		this.tops2 = tops2;
	}

	public Integer getOuters() {
		return outers;
	}

	public void setOuters(Integer outers) {
		this.outers = outers;
	}

	public Integer getBottoms() {
		return bottoms;
	}

	public void setBottoms(Integer bottoms) {
		this.bottoms = bottoms;
	}

	public Integer getShoes() {
		return shoes;
	}

	public void setShoes(Integer shoes) {
		this.shoes = shoes;
	}

	public Integer getBag() {
		return bag;
	}

	public void setBag(Integer bag) {
		this.bag = bag;
	}

	public Integer getDress() {
		return dress;
	}

	public void setDress(Integer dress) {
		this.dress = dress;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getIsPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Clothes> getClothesList() {
		return clothesList;
	}

	public void setClothesList(List<Clothes> clothesList) {
		this.clothesList = clothesList;
	}

}
