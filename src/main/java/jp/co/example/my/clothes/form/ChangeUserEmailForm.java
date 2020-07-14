package jp.co.example.my.clothes.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * ユーザのメールアドレスを更新する際に使用するフォームクラスです.
 * 
 * @author rinashioda
 *
 */
public class ChangeUserEmailForm {

	/** メールアドレス */
	@Email(message = "アドレス形式が不正です")
	@NotBlank(message = "メールアドレスを入力して下さい")
	private String email;

	/** 確認用メールアドレス */
	@Email(message = "アドレス形式が不正です")
	@NotBlank(message = "確認用メールアドレスを入力して下さい")
	private String confirmEmail;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmEmail() {
		return confirmEmail;
	}

	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
	}

	@Override
	public String toString() {
		return "ChangeUserEmailForm [email=" + email + ", confirmEmail=" + confirmEmail + "]";
	}

}
