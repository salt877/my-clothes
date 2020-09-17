package jp.co.example.my.clothes.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * ユーザーごとのブランド別アイテム数情報を保持するDTO
 * 
 * @author masashi.nose
 *
 */
@Getter
@Setter
public class BrandCountDto {

	/** ブランドID */
	private Integer brandId;

	/** ブランド名 */
	private String brandName;

	/** ブランド別アイテム数 */
	private Integer brandCount;

	@Override
	public String toString() {
		return "BrandCountDto [brandId=" + brandId + ", brandName=" + brandName + ", brandCount=" + brandCount + "]";
	}

}
