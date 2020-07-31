package jp.co.example.my.clothes.domain;

/**
 * カテゴリー別金額データ情報を持つDTO
 * 
 * @author masashi.nose
 *
 */
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

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getCategorySum() {
		return categorySum;
	}

	public void setCategorySum(Integer categorySum) {
		this.categorySum = categorySum;
	}

}
