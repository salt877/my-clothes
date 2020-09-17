package jp.co.example.my.clothes.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * パスワード変更時に使用するDTOです.
 * 
 * @author rinashioda
 *
 */
@Entity
@Table(name = "password_reset")
@Getter
@Setter
public class PasswordReset {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "random_url")
	private String randomUrl;

	@Column(name = "expire_date")
	private Date expireDate;

	@Override
	public String toString() {
		return "PasswordReset [id=" + id + ", userId=" + userId + ", randomUrl=" + randomUrl + ", expireDate="
				+ expireDate + "]";
	}

}
