package jp.co.example.my.clothes.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactForm {

	@NotBlank(message = "※名前を入力してください")
	private String name;

	@NotBlank(message = "※メールアドレスを入力してください")
	@Email(message = "※メールアドレスの形式が不正です")
	private String email;

	@NotBlank(message = "※問い合わせ内容を入力してください")
	private String content;

	@Override
	public String toString() {
		return "ContactForm [name=" + name + ", email=" + email + ", content=" + content + "]";
	}

}
