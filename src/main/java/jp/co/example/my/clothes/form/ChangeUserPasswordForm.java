package jp.co.example.my.clothes.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * ユーザのパスワードを変更する際に使用するフォームクラスです.
 * 
 * @author rinashioda
 *
 */
public class ChangeUserPasswordForm {

	/** ユーザID */
	private Integer userId;
	
	/** ランダムURL */
	private String randomUrl;

	/** パスワード */
	@NotBlank(message = "パスワードを入力して下さい")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,20}$", message = "パスワードは半角英数字（大文字・小文字を1文字以上含む）8文字以上20文字以下で入力して下さい")
	private String newPassword;

	/** 確認用パスワード */
	@NotBlank(message = "確認用パスワードを入力して下さい")
	private String confirmNewPassword;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRandomUrl() {
		return randomUrl;
	}

	public void setRandomUrl(String randomUrl) {
		this.randomUrl = randomUrl;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	@Override
	public String toString() {
		return "ChangeUserPasswordForm [userId=" + userId + ", randomUrl=" + randomUrl + ", newPassword=" + newPassword
				+ ", confirmNewPassword=" + confirmNewPassword + "]";
	}

	
}
