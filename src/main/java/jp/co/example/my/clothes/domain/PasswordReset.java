package jp.co.example.my.clothes.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author rinashioda
 *
 */
@Entity
@Table(name = "password_reset")
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	@Override
	public String toString() {
		return "PasswordReset [id=" + id + ", userId=" + userId + ", randomUrl=" + randomUrl + ", expireDate="
				+ expireDate + "]";
	}

}
