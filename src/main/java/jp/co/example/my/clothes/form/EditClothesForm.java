package jp.co.example.my.clothes.form;


import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

public class EditClothesForm {

	/** アイテムID */
	private String clothesId;
	/** 画像 */
	private MultipartFile imageFile;
	/** カテゴリーID */
	private String category;
	/** ブランドID */
	@NotBlank(message="入力必須項目です")
	private String brand;
	/** カラーID */
	private String color;
	/** タグ1 */
	private String tag1;
	/** タグ2 */
	private String tag2;
	/** タグ3 */
	private String tag3;
	/** シーズン */
	private String season;
	/** サイズID */
	private String size;
	/** 購入日 */
	private String perchaseDate;
	/** 価格 */
	private String price;
	/** コメント */
	private String comment;

	@Override
	public String toString() {
		return "EditClothesForm [clothesId=" + clothesId + ", imageFile=" + imageFile + ", category=" + category
				+ ", brand=" + brand + ", color=" + color + ", tag1=" + tag1 + ", tag2=" + tag2 + ", tag3=" + tag3
				+ ", season=" + season + ", size=" + size + ", perchaseDate=" + perchaseDate + ", price=" + price
				+ ", comment=" + comment + "]";
	}

	public String getClothesId() {
		return clothesId;
	}

	public void setClothesId(String clothesId) {
		this.clothesId = clothesId;
	}

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTag1() {
		return tag1;
	}

	public void setTag1(String tag1) {
		this.tag1 = tag1;
	}

	public String getTag2() {
		return tag2;
	}

	public void setTag2(String tag2) {
		this.tag2 = tag2;
	}

	public String getTag3() {
		return tag3;
	}

	public void setTag3(String tag3) {
		this.tag3 = tag3;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getPerchaseDate() {
		return perchaseDate;
	}

	public void setPerchaseDate(String perchaseDate) {
		this.perchaseDate = perchaseDate;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
