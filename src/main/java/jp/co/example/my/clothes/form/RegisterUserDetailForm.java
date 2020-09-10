package jp.co.example.my.clothes.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

/**
 * ユーザの詳細情報を入力するフォームクラスです.
 * 
 * @author rinashioda
 *
 */
public class RegisterUserDetailForm {

	/** プロフィール写真 */
	private String imagePath;

	/** プロフィール写真 */
	private MultipartFile imageFile;

	/** ニックネーム */
	@NotBlank(message = "ニックネームは必須です")
	@Size(min = 1, max = 30, message = "1文字以上30文字以内で入力してください")
	private String userName;

	/** 性別 */
	private String gender;

	/** 年代 */
	private String age;

	/** 身長 */
	private Integer height;

	/** 自己紹介 */
	private String selfIntroduction;

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
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

}
