package jp.co.example.my.clothes.domain;

/**
 * ユーザーごとのブランド別アイテム数情報を保持するDTO
 * 
 * @author masashi.nose
 *
 */
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

	public Integer getBrandCount() {
		return brandCount;
	}

	public void setBrandCount(Integer brandCount) {
		this.brandCount = brandCount;
	}

}
