package jp.co.example.my.clothes.domain;

/**
 * 
 * ユーザーごとのカテゴリー別アイテム数情報を保持するDTO
 * 
 * @author masashi.nose
 *
 */
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

	public Integer getCategoryCount() {
		return categoryCount;
	}

	public void setCategoryCount(Integer categoryCount) {
		this.categoryCount = categoryCount;
	}

}
