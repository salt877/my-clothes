package jp.co.example.my.clothes.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

/**
 * 新規アイテム登録用のフォーム.
 * 
 * @author ashibe
 *
 */
@Getter
@Setter
public class RegisterClothesForm {

	/** 画像 */
	private MultipartFile imageFile;

	/** カテゴリID */
	@NotBlank(message = "選択必須です")
	private String category;

	/** ブランドID */
	@NotBlank(message = "選択必須です")
	private String brand;

	/** カラーID */
	@NotBlank(message = "選択必須です")
	private String color;

	/** タグ1 */
	private String tag1;

	/** タグ2 */
	private String tag2;

	/** タグ3 */
	private String tag3;

	/** 季節 */
	private String season;

	/** サイズID */
	private String size;

	/** 購入日 */
	private String perchaseDate;

	/** 価格 */
	@Pattern(regexp = "^[0-9]{0,10}$", message = "半角英数字で入力してください")
	private String price;

	/** コメント */
	@Size(max = 100, message = "100文字以内で入力してください")
	private String comment;

	@Override
	public String toString() {
		return "RegisterClothesForm [imageFile=" + imageFile + ", category=" + category + ", brand=" + brand
				+ ", color=" + color + ", tag1=" + tag1 + ", tag2=" + tag2 + ", tag3=" + tag3 + ", season=" + season
				+ ", size=" + size + ", perchaseDate=" + perchaseDate + ", price=" + price + ", comment=" + comment
				+ "]";
	}

}
