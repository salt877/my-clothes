package jp.co.example.my.clothes.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * カテゴリー別金額データ情報を持つDTO
 * 
 * @author masashi.nose
 *
 */
@Getter
@Setter
public class CategorySumDto {

	/** カテゴリーID */
	private Integer categoryId;

	/** カテゴリー名 */
	private String categoryName;

	/** 合計金額 */
	private Integer categorySum;

	@Override
	public String toString() {
		return "CategorySumDto [categoryId=" + categoryId + ", categoryName=" + categoryName + ", categorySum="
				+ categorySum + "]";
	}

}
