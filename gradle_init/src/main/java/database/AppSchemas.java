/**
 * Create Database Schemas for the project in here
 */

package database;

public class AppSchemas {

	public static final class ScenariosTableSchema {
		public static final String NAME = "scenarios";

		public static final class Cols {
			public static final String filename = "filename";
			public static final String contentCol = "content";
		}
	}
}
