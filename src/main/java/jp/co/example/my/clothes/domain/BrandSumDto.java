package jp.co.example.my.clothes.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * ユーザーごとのブランド別金額合計情報を保持するDTO
 * 
 * @author masashi.nose
 *
 */
@Getter
@Setter
public class BrandSumDto {

	/** ブランドID */
	private Integer brandId;

	/** ブランド名 */
	private String brandName;

	/** ブランド別合計金額 */
	private Integer brandSum;

	@Override
	public String toString() {
		return "BrandSumDto [brandId=" + brandId + ", brandName=" + brandName + ", brandSum=" + brandSum + "]";
	}

}
