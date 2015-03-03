package user.pokeranch.models.type;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class StatusEffect {
	
	// DATA MEMBER
	private int ID;
	private String Name;
	
	// GETTER
	public int getID() { return ID; }
	public String getString() { return Name; }
	
	public abstract class StatusEffectTable implements BaseColumns {
		public static final String TABLE_NAME = "status_effect";
		public static final String COLUMN_NAME_NAME = "name";
	}
	public abstract class StatusEffectCD {
		private static final String INTEGER_TYPE = " INTEGER";
		private static final String TEXT_TYPE = " VARCHAR(20)";
		private static final String NOT_NULL = " NOT NULL";
		private static final String COMMA_SEP = ",";
		public static final String SQL_CREATE_ENTRIES =
			"CREATE TABLE " + StatusEffect.StatusEffectTable.TABLE_NAME + " (" +
			StatusEffect.StatusEffectTable._ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			StatusEffect.StatusEffectTable.COLUMN_NAME_NAME + TEXT_TYPE + NOT_NULL + COMMA_SEP +
			"PRIMARY KEY (" + StatusEffect.StatusEffectTable._ID + ")" +
			")";
		public static final String SQL_DELETE_ENTRIES =
			"DROP TABLE IF EXISTS " + StatusEffect.StatusEffectTable.TABLE_NAME;
		
		public static final String SQL_DATA_ENTRIES =
			"INSERT INTO " + StatusEffect.StatusEffectTable.TABLE_NAME +
			" SELECT 0 AS " + StatusEffect.StatusEffectTable._ID + ", 'No Effect' AS " + StatusEffect.StatusEffectTable.TABLE_NAME + 
			" UNION SELECT 1, 'Paralyz'    " +
			" UNION SELECT 2, 'Poison'     " +
			" UNION SELECT 3, 'Bad Poison' " +
			" UNION SELECT 4, 'Burn'       " + 
			" UNION SELECT 5, 'Frozen'     " + 
			" UNION SELECT 6, 'Sleep'      ";
	}
	public static String getNameStatusEffect(SQLiteDatabase db, int no_status) {
		String[] projection = {
			StatusEffect.StatusEffectTable.COLUMN_NAME_NAME
			};
		
		String where_col = StatusEffect.StatusEffectTable._ID + " LIKE ?";
		String[] where_val = {"" + no_status};

		Cursor c = db.query(
			StatusEffect.StatusEffectTable.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			where_col,                  // The columns for the WHERE clause (String)
			where_val,                  // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);
		c.moveToFirst();
		return c.getString(c.getColumnIndexOrThrow(StatusEffect.StatusEffectTable.COLUMN_NAME_NAME));
	}
	public static String getData(SQLiteDatabase db) {
		String[] projection = {
			StatusEffect.StatusEffectTable._ID,
			StatusEffect.StatusEffectTable.COLUMN_NAME_NAME
			};

		Cursor c = db.query(
			StatusEffect.StatusEffectTable.TABLE_NAME,  // The table to query (String)
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
				message += "\nID:" + (int) c.getInt(c.getColumnIndexOrThrow(StatusEffect.StatusEffectTable._ID));
			    message += "\nName:" + (String) c.getString(c.getColumnIndexOrThrow(StatusEffect.StatusEffectTable.COLUMN_NAME_NAME));
			    message += "\n";
			} while(c.moveToNext());
		}
		return message;
	}
}
