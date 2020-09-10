package jp.co.example.my.clothes.domain;

/**
 * ユーザの詳細情報を表すドメインクラス.
 * 
 * @author rinashioda
 *
 */
public class UserDetail {

	/** MYQLO ID */
	private String myqloId;

	/** 詳細情報ID */
	private Integer id;

	/** ユーザID */
	private Integer userId;

	/** プロフィール写真 */
	private String imagePath;

	/** ニックネーム */
	private String userName;

	/** 性別 */
	private String gender;

	/** 年代 */
	private String age;

	/** 身長 */
	private Integer height;

	/** 自己紹介 */
	private String selfIntroduction;

	public String getMyqloId() {
		return myqloId;
	}

	public void setMyqloId(String myqloId) {
		this.myqloId = myqloId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getSelfIntroduction() {
		return selfIntroduction;
	}

	public void setSelfIntroduction(String selfIntroduction) {
		this.selfIntroduction = selfIntroduction;
	}

	@Override
	public String toString() {
		return "UserDetail [myqloId=" + myqloId + ", id=" + id + ", userId=" + userId + ", imagePath=" + imagePath
				+ ", userName=" + userName + ", gender=" + gender + ", age=" + age + ", height=" + height
				+ ", selfIntroduction=" + selfIntroduction + "]";
	}

}
