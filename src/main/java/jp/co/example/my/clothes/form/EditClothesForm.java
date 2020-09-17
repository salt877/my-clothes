package jp.co.example.my.clothes.form;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditClothesForm {

	/** アイテムID */
	private String clothesId;

	/** 画像 */
	private MultipartFile imageFile;

	/** カテゴリーID */
	private String category;

	/** ブランドID */
	@NotBlank(message = "入力必須項目です")
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

}
