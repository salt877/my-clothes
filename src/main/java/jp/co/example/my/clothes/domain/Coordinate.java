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
	/** ユーザー情報格納用インスタンス */
	private User user;
	/** 服情報格納用インスタンス*/
	private Clothes clothes;
	/** 服情報を詰めるリスト（コーディネート情報） */
	private List<Clothes> clothesList;

	@Override
	public String toString() {
		return "Coordinate [id=" + id + ", userId=" + userId + ", tops1=" + tops1 + ", tops2=" + tops2 + ", outers="
				+ outers + ", bottoms=" + bottoms + ", shoes=" + shoes + ", bag=" + bag + ", dress=" + dress + ", user="
				+ user + ", clothes=" + clothes + ", clothesList=" + clothesList + "]";
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Clothes getClothes() {
		return clothes;
	}

	public void setClothes(Clothes clothes) {
		this.clothes = clothes;
	}

	public List<Clothes> getClothesList() {
		return clothesList;
	}

	public void setClothesList(List<Clothes> clothesList) {
		this.clothesList = clothesList;
	}

}
