package istia.st.springmvc.models;

import javax.validation.constraints.NotNull;

public class ActionModel01 {

	// data
	/**
	 * L'annotation [@NotNull] est une contrainte de validation qui indique que la
	 * donnée annotée ne peut avoir la valeur null.
	 */
	@NotNull
	private Integer a;
	@NotNull
	private Double b;

	// getters et setters
	public Integer getA() {
		return a;
	}

	public void setA(Integer a) {
		this.a = a;
	}

	public Double getB() {
		return b;
	}

	public void setB(Double b) {
		this.b = b;
	}

}
