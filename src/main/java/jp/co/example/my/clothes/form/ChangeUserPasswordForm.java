package jp.co.example.my.clothes.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

/**
 * ユーザのパスワードを変更する際に使用するフォームクラスです.
 * 
 * @author rinashioda
 *
 */
@Getter
@Setter
public class ChangeUserPasswordForm {

	/** ユーザID */
	private Integer userId;

	/** ランダムURL */
	private String randomUrl;

	/** パスワード */
	@NotBlank(message = "パスワードを入力して下さい")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,20}$", message = "パスワード形式が不正です")
	private String newPassword;

	/** 確認用パスワード */
	@NotBlank(message = "確認用パスワードを入力して下さい")
	private String confirmNewPassword;

	@Override
	public String toString() {
		return "ChangeUserPasswordForm [userId=" + userId + ", randomUrl=" + randomUrl + ", newPassword=" + newPassword
				+ ", confirmNewPassword=" + confirmNewPassword + "]";
	}

}
