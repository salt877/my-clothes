package jp.co.example.my.clothes.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

/**
 * ユーザ情報登録時に使用するフォームクラスです.
 * 
 * @author rinashioda
 *
 */
@Getter
@Setter
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

}