package jp.co.example.my.clothes.domain;

import java.util.Date;
import java.util.List;

/**
 * アイテム情報を扱うドメインクラス.
 * 
 * @author ashibe
 *
 */
public class Clothes {

	/**
	 * 商品ID
	 */
	private Integer id;

	/**
	 * ユーザーID
	 */
	private Integer userId;

	/**
	 * ブランドID
	 */
	private Integer brandId;
	/**
	 * カテゴリID
	 */
	private Integer categoryId;

	/**
	 * 画像パス
	 */
	private String imagePath;

	/**
	 * 価格
	 */
	private Integer price;

	/**
	 * カラーID
	 */
	private Integer colorId;

	/**
	 * シーズン
	 */
	private String season;

	/**
	 * サイズID
	 */
	private Integer sizeId;

	/**
	 * 購入日
	 */
	private Date perchaseDate;

	/**
	 * コメント
	 */
	private String comment;

	/**
	 * 削除フラグ
	 */
	private Boolean deleted;

	/**
	 * カテゴリ情報
	 */
	private Category category;

	/**
	 * ブランド情報
	 */
	private Brand brand;

	/**
	 * カラー情報
	 */
	private Color color;

	/**
	 * サイズ情報
	 */
	private Size size;

	/**
	 * タグリスト
	 */
	private List<Tag> tagList;

	/**
	 * ブランドリスト
	 */
	private List<Brand> brandList;

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

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getColorId() {
		return colorId;
	}

	public void setColorId(Integer colorId) {
		this.colorId = colorId;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public Integer getSizeId() {
		return sizeId;
	}

	public void setSizeId(Integer sizeId) {
		this.sizeId = sizeId;
	}

	public Date getPerchaseDate() {
		return perchaseDate;
	}

	public void setPerchaseDate(Date perchaseDate) {
		this.perchaseDate = perchaseDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public List<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

	public List<Brand> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<Brand> brandList) {
		this.brandList = brandList;
	}

	@Override
	public String toString() {
		return "Clothes [id=" + id + ", userId=" + userId + ", brandId=" + brandId + ", categoryId=" + categoryId
				+ ", imagePath=" + imagePath + ", price=" + price + ", colorId=" + colorId + ", season=" + season
				+ ", sizeId=" + sizeId + ", perchaseDate=" + perchaseDate + ", comment=" + comment + ", deleted="
				+ deleted + ", category=" + category + ", brand=" + brand + ", color=" + color + ", size=" + size
				+ ", tagList=" + tagList + ", brandList=" + brandList + "]";
	}

}
