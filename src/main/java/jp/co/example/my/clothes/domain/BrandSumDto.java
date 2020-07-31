package jp.co.example.my.clothes.domain;

/**
 * ユーザーごとのブランド別金額合計情報を保持するDTO
 * 
 * @author masashi.nose
 *
 */
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

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getBrandSum() {
		return brandSum;
	}

	public void setBrandSum(Integer brandSum) {
		this.brandSum = brandSum;
	}

}
