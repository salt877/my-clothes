package jp.co.example.my.clothes.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * ユーザのメールアドレスを更新する際に使用するフォームクラスです.
 * 
 * @author rinashioda
 *
 */
@Getter
@Setter
public class ChangeUserEmailForm {

	/** メールアドレス */
	@Email(message = "アドレス形式が不正です")
	@NotBlank(message = "メールアドレスを入力して下さい")
	private String email;

	/** 確認用メールアドレス */
	@Email(message = "アドレス形式が不正です")
	@NotBlank(message = "確認用メールアドレスを入力して下さい")
	private String confirmEmail;

	@Override
	public String toString() {
		return "ChangeUserEmailForm [email=" + email + ", confirmEmail=" + confirmEmail + "]";
	}

}
