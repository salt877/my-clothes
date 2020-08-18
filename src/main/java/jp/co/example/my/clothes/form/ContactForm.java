package jp.co.example.my.clothes.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class ContactForm {

	@NotBlank(message="※名前を入力してください")
	private String name;
	
	@NotBlank(message="※メールアドレスを入力してください")
	@Email(message="※メールアドレスの形式が不正です")
	private String email;
	
	@NotBlank(message="※問い合わせ内容を入力してください")
	private String content;

	@Override
	public String toString() {
		return "ContactForm [name=" + name + ", email=" + email + ", content=" + content + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
