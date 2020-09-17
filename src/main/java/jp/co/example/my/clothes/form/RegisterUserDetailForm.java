package jp.co.example.my.clothes.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

/**
 * ユーザの詳細情報を入力するフォームクラスです.
 * 
 * @author rinashioda
 *
 */
@Getter
@Setter
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

}
