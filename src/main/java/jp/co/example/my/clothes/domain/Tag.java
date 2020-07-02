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
	private Integer clothesID;

	/**
	 * 結び付けるタグ内容のID
	 */
	private Integer tagContentId;

	/**
	 * 結び付けられたタグ内容
	 */
	private TagContent tagContent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClothesID() {
		return clothesID;
	}

	public void setClothesID(Integer clothesID) {
		this.clothesID = clothesID;
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

	@Override
	public String toString() {
		return "Tag [id=" + id + ", clothesID=" + clothesID + ", tagContentId=" + tagContentId + ", tagContent="
				+ tagContent + "]";
	}

}
