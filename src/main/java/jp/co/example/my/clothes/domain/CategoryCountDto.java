package jp.co.example.my.clothes.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * ユーザーごとのカテゴリー別アイテム数情報を保持するDTO
 * 
 * @author masashi.nose
 *
 */
@Getter
@Setter
public class CategoryCountDto {

	/** カテゴリーID */
	private Integer categoryId;

	/** カテゴリー名 */
	private String categoryName;

	/** カテゴリー別アイテム数 */
	private Integer categoryCount;

	@Override
	public String toString() {
		return "CategoryCountDto [categoryId=" + categoryId + ", categoryName=" + categoryName + ", categoryCount="
				+ categoryCount + "]";
	}

}
