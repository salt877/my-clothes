package jp.co.example.my.clothes.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * ユーザーごとのアイテム平均金額情報をもつドメイン
 * 
 * @author masashi.nose
 *
 */
@Getter
@Setter
public class AverageDto {

	private Integer average;

	@Override
	public String toString() {
		return "AverageDto [average=" + average + "]";
	}

}
