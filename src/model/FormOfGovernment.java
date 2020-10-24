package model;

public enum FormOfGovernment {
	
	//1
	DEMOCRACY,
	DICTATORSHIP,
	MONARCHY;
	
	// CONTAINS
	// INPUT:	String SearchString
	// OUTPUT:	true / false
	public boolean contains(String searchString) {
		return (this.name().contains(searchString));
	}
	
}
