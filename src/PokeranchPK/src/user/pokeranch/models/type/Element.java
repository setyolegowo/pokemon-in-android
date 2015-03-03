/**
 * 
 */
package user.pokeranch.models.type;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * @author Setyo Lego
 *
 */
public class Element {
	
	// DATA MEMBER
	private int ID;
	private String Name;
	
	// GETTER
	public int getID() { return ID; }
	public String getName() { return Name; }
	
	public abstract class ElementTable implements BaseColumns {
		public static final String TABLE_NAME = "elemen";
		public static final String COLUMN_NAME_NAME = "name";
	}
	public abstract class ElementCD {
		private static final String INTEGER_TYPE = " INTEGER";
		private static final String TEXT_TYPE = " VARCHAR(20)";
		private static final String NOT_NULL = " NOT NULL";
		private static final String COMMA_SEP = ", ";
		public static final String SQL_CREATE_ENTRIES =
			"CREATE TABLE " + Element.ElementTable.TABLE_NAME + " (" +
			Element.ElementTable._ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			Element.ElementTable.COLUMN_NAME_NAME + TEXT_TYPE + NOT_NULL + COMMA_SEP +
			"PRIMARY KEY (" + Element.ElementTable._ID + ")" +
		")";
		public static final String SQL_DELETE_ENTRIES =
			"DROP TABLE IF EXISTS " + Element.ElementTable.TABLE_NAME;
		
		public static final String SQL_DATA_ENTRIES =
			"INSERT INTO " + Element.ElementTable.TABLE_NAME +
			" SELECT 0 AS " + Element.ElementTable._ID + ", 'NOT ELEMENT' AS " + Element.ElementTable.TABLE_NAME +
			" UNION SELECT  1, 'Psychic' " + 
			" UNION SELECT  2, 'Fight' " +
			" UNION SELECT  3, 'Normal' " +
			" UNION SELECT  4, 'Water' " +
			" UNION SELECT  5, 'Grass' " +
			" UNION SELECT  6, 'Ground' " +
			" UNION SELECT  7, 'Flying' " +
			" UNION SELECT  8, 'Ice' " +
			" UNION SELECT  9, 'Electric' " +
			" UNION SELECT 10, 'Dragon'";
	}
	public static String getNameElement(SQLiteDatabase db, int element_id) {
		String[] projection = {
			Element.ElementTable.COLUMN_NAME_NAME
			};
		
		String where_col = Element.ElementTable._ID + " LIKE ?";
		String[] where_val = {"" + element_id};

		Cursor c = db.query(
			Element.ElementTable.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			where_col,                  // The columns for the WHERE clause (String)
			where_val,                  // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);
		if(c.moveToFirst()) {
			return c.getString(c.getColumnIndexOrThrow(Element.ElementTable.COLUMN_NAME_NAME));
		} else 
		return null;
	}
	public static String getData(SQLiteDatabase db) {
		String[] projection = {
			Element.ElementTable._ID,
			Element.ElementTable.COLUMN_NAME_NAME
			};

		Cursor c = db.query(
			Element.ElementTable.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			null,                       // The columns for the WHERE clause (String)
			null,                       // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);
		String message = "";
		if(c.moveToFirst()) {
			do {
				message += "\nID:" + (int) c.getInt(c.getColumnIndexOrThrow(Element.ElementTable._ID));
			    message += "\nName:" + (String) c.getString(c.getColumnIndexOrThrow(Element.ElementTable.COLUMN_NAME_NAME));
			    message += "\n";
			} while(c.moveToNext());
		}
		return message;
	}
}
