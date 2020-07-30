package jp.co.example.my.clothes.domain;

/**
 * タグ情報を扱うドメインクラス.
 * 
 * @author ashibe
 *
 */
public class Tag {

	/**
	 * タグID
	 */
	private Integer id;

	/**
	 * 結び付く服のID
	 */
	private Integer clothesId;

	/**
	 * 結び付けるタグ内容のID
	 */
	private Integer tagContentId;

	/**
	 * 結び付けられたタグ内容
	 */
	private TagContent tagContent;

	/**
	 * ユーザID
	 */
	private Integer userId;

	@Override
	public String toString() {
		return "Tag [id=" + id + ", clothesId=" + clothesId + ", tagContentId=" + tagContentId + ", tagContent="
				+ tagContent + ", userId=" + userId + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClothesId() {
		return clothesId;
	}

	public void setClothesId(Integer clothesId) {
		this.clothesId = clothesId;
	}

	public Integer getTagContentId() {
		return tagContentId;
	}

	public void setTagContentId(Integer tagContentId) {
		this.tagContentId = tagContentId;
	}

	public TagContent getTagContent() {
		return tagContent;
	}

	public void setTagContent(TagContent tagContent) {
		this.tagContent = tagContent;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
