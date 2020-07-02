package jp.co.example.my.clothes.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

	private Integer id;

	private String email;

	private String password;

	private Boolean deleted;

}
