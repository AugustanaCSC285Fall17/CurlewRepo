package edu.augustana.csc285.game.datamodel;

public enum RelationOperator {
	LESS_THAN {
		public boolean apply(double a, double b) { return a < b;}
	},
	LESS_THAN_OR_EQUAL {
		public boolean apply(double a, double b) { return a <= b;}
	},
	GREATER_THAN {
		public boolean apply(double a, double b) { return a > b;}
	},
	GREATER_THAN_OR_EQUAL {
		public boolean apply(double a, double b) { return a >= b;}
	},
	EQUAL {
		public boolean apply(double a, double b) { return a == b;}
	},
	NOT_EQUAL {
		public boolean apply(double a, double b) { return a != b;}
	};
	
	public abstract boolean apply(double a, double b);

}
