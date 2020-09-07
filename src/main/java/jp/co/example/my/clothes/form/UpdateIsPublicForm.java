package jp.co.example.my.clothes.form;

public class UpdateIsPublicForm {

	/** コーデID */
	private Integer coordinateId;
	/** 公開フラグ */
	private boolean isPublic;

	@Override
	public String toString() {
		return "UpdateIsPublicForm [coordinateId=" + coordinateId + ", isPublic=" + isPublic + "]";
	}

	public Integer getCoordinateId() {
		return coordinateId;
	}

	public void setCoordinateId(Integer coordinateId) {
		this.coordinateId = coordinateId;
	}

	public boolean getIsPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

}
