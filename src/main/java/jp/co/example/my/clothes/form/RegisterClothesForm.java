package jp.co.example.my.clothes.form;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegisterClothesForm {

	/**
	 * カテゴリID
	 */
	@NotBlank(message = "選択必須です")
	private String category;

	/**
	 * ブランドID
	 */
	@NotBlank(message = "選択必須です")
	private String brand;

	/**
	 * カラーID
	 */
	@NotBlank(message = "選択必須です")
	private String color;

	/**
	 * タグ
	 */
	private String tag;

	/**
	 * 季節
	 */
	private String season;

	/**
	 * サイズID
	 */
	private String size;

	/**
	 * 購入日
	 */
	private String perchaseDate;

	/**
	 * 価格
	 */
	@Pattern(regexp = "^[0-9]{0,10}$", message = "半角英数字で入力してください")
	private String price;

	/**
	 * コメント
	 */
	@Size(max=100 ,message="100文字以内で入力してください")
	private String comment;

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

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
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

	@Override
	public String toString() {
		return "RegisterClothesForm [category=" + category + ", brand=" + brand + ", color=" + color + ", tag=" + tag
				+ ", season=" + season + ", size=" + size + ", perchaseDate=" + perchaseDate + ", price=" + price
				+ ", comment=" + comment + "]";
	}

}
