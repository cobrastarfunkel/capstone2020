/**
 * Create Database Schemas for the project in here
 */

package database;

public class AppSchemas {

	public static final class ScenariosTableSchema {
		public static final String NAME = "scenarios";

		public static final class Cols {
			public static final String idNumber = "idNumber";
			public static final String scName = "scName";
		}
	}

	public static final class MalwareTableSchema {
		public static final String NAME = "malware";

		public static final class Cols {
			public static final String idNumber = "idNumber";
			public static final String dMalware = "dMalware";
		}
	}
}
