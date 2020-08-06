package jp.co.example.my.clothes.domain;

/**
 * ユーザーごとのアイテム平均金額情報をもつドメイン
 * 
 * @author masashi.nose
 *
 */
public class AverageDto {

	private Integer average;

	@Override
	public String toString() {
		return "AverageDto [average=" + average + "]";
	}

	public Integer getAverage() {
		return average;
	}

	public void setAverage(Integer average) {
		this.average = average;
	}

}
