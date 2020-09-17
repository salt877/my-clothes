package jp.co.example.my.clothes.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * ユーザの詳細情報を表すドメインクラス.
 * 
 * @author rinashioda
 *
 */
@Getter
@Setter
public class UserDetail {

	/** MYQLO ID */
	private String myqloId;

	/** 詳細情報ID */
	private Integer id;

	/** ユーザID */
	private Integer userId;

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

	@Override
	public String toString() {
		return "UserDetail [myqloId=" + myqloId + ", id=" + id + ", userId=" + userId + ", imagePath=" + imagePath
				+ ", userName=" + userName + ", gender=" + gender + ", age=" + age + ", height=" + height
				+ ", selfIntroduction=" + selfIntroduction + "]";
	}

}
