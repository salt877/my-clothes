package jp.co.example.my.clothes.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * ユーザ情報登録時に使用するフォームクラスです.
 * 
 * @author rinashioda
 *
 */
public class RegisterUserForm {

	/** MYQLO ID */
	@NotBlank(message = "MYQLO IDを入力して下さい")
	@Pattern(regexp = "^([a-zA-Z0-9]{6,15})$", message = "半角英数字6文字以上15文字以内で入力して下さい")
	private String myqloId;

	/** メールアドレス */
	@NotBlank(message = "メールアドレスを入力して下さい")
	@Email(message = "アドレス形式が不正です")
	private String email;

	/** パスワード */
	@NotBlank(message = "パスワードを入力して下さい")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,20}$", message = "パスワード形式が不正です")
	private String password;

	/** 確認用パスワード */
	@NotBlank(message = "確認用パスワードを入力して下さい")
	private String confirmPassword;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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

}
