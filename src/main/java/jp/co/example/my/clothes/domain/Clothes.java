package jp.co.example.my.clothes.domain;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * アイテム情報を扱うドメインクラス.
 * 
 * @author ashibe
 *
 */
@Getter
@Setter
public class Clothes {

	/** 商品ID */
	private Integer id;

	/** ユーザーID */
	private Integer userId;

	/** ブランドID */
	private Integer brandId;

	/** カテゴリID */
	private Integer categoryId;

	/** 画像パス */
	private String imagePath;

	/** 価格 */
	private Integer price;

	/** カラーID */
	private Integer colorId;

	/** シーズン */
	private String season;

	/** サイズID */
	private Integer sizeId;

	/** 購入日 */
	private Date perchaseDate;

	/** コメント */
	private String comment;

	/** 削除フラグ */
	private Boolean deleted;

	/**
	 * カテゴリ情報
	 */
	private Category category;

	/** ブランド情報 */
	private Brand brand;

	/** カラー情報 */
	private Color color;

	/** サイズ情報 */
	private Size size;

	/** タグリスト */
	private List<Tag> tagList;

	/** ブランドリスト */
	private List<Brand> brandList;

	public Clothes(Integer id, Integer userId, Integer brandId, Integer categoryId, String imagePath, Integer price,
			Integer colorId, String season, Integer sizeId, Date perchaseDate, String comment, Boolean deleted,
			Category category, Brand brand, Color color, Size size, List<Tag> tagList, List<Brand> brandList) {
		super();
		this.id = id;
		this.userId = userId;
		this.brandId = brandId;
		this.categoryId = categoryId;
		this.imagePath = imagePath;
		this.price = price;
		this.colorId = colorId;
		this.season = season;
		this.sizeId = sizeId;
		this.perchaseDate = perchaseDate;
		this.comment = comment;
		this.deleted = deleted;
		this.category = category;
		this.brand = brand;
		this.color = color;
		this.size = size;
		this.tagList = tagList;
		this.brandList = brandList;
	}

	public Clothes() {
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
