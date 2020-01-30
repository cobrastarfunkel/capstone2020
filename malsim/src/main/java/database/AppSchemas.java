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

	public static final class DocumentTableSchema {
		public static final String NAME = "documents";

		public static final class Cols {
			public static final String idNumber = "idNumber";
			public static final String document = "document";
			public static final String typeOfDoc = "typeOfDoc";
		}
	}

	public static final class ProgressTableSchema {
		public static final String NAME = "progress";

		public static final class Cols {
			public static final String idNumber = "idNumber";
			public static final String completed = "completed";
			public static final String timeSpent = "timeSpent";
		}
	}

	public static final class TraitsTableSchema {
		public static final String NAME = "scenarioTraits";

		public static final class Cols {
			public static final String idNumber = "idNumber";
			public static final String difficulty = "difficulty";
			public static final String avgTime = "avgTime";
			public static final String language = "language";
			public static final String os = "os";
			public static final String scType = "scType";
		}
	}
}
