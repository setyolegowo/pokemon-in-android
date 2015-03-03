/**
 * 
 */
package user.pokeranch.models;

import java.util.ArrayList;

import user.pokeranch.Main;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * @author Setyo Lego
 *
 */
public class Player {

	private int ID;
	private String Name;
	private int TotalPlayTime;
	private int Day_or_Night;
	private int Cur_Money;
	private int Total_Get_Money;
	private int Win;
	private int Lose;
	private int Score_Stadium;
	
	// GETTER
	public int getID() { return ID; }
	public String getName() { return Name; }
	public int getTotalTimePlay() { return TotalPlayTime; }
	public int getDayOrNight() { return Day_or_Night; }
	public int getCurMoney() { return Cur_Money; }
	public int getTotalGetMoney() { return Total_Get_Money; }
	public int getWin() { return Win; }
	public int getLose() { return Lose; }
	public int getScoreStadium() { return Score_Stadium; }
	
	// SETTER
	private void setID(int Id) { this.ID = Id; }
	public void setName(String name) { this.Name = name; }
	public void setTotalTimePlay(int time) { this.TotalPlayTime = time; }
	public void setDayOrNight(int d) { this.Day_or_Night = d; }
	public void setCurMoney(int money) { this.Cur_Money = money; }
	public void setTotalGetMoney(int money) { this.Total_Get_Money = money; }
	public void setWin(int win) { this.Win = win; }
	public void setLose(int lose) { this.Lose = lose; }
	public void setScoreStadium(int score) { this.Score_Stadium = score; }
	
	/**
	 * Untuk membuat sebuah objek baru yang kosong namun tidak menyimpannya dalam database
	 */
	public Player() {
		
	}
	/**
	 * Membuat sebuah player baru dengan menyimpannya dalam database
	 * @param db
	 * @param name
	 */
	public Player(SQLiteDatabase db, String name) {
		this.ID = nextPlayerId(db);
		this.Name = name;
		this.TotalPlayTime = 0;
		this.Cur_Money = 1000000;
		this.Day_or_Night = 0;
		this.Total_Get_Money = 0;
		this.Win = 0;
		this.Lose = 0;
		this.Score_Stadium = 0;
		
		db.execSQL("INSERT INTO " + Player.PlayerTabel.TABLE_NAME + " VALUES " +
			"(" + this.ID + ", '" + this.Name + "', " + this.TotalPlayTime + ", " + this.Day_or_Night + 
				", " + this.Cur_Money + ", " + this.Total_Get_Money + ", " + this.Win + ", " + this.Lose + ", " + this.Score_Stadium + 
			")"
		);
	}
	
	public abstract class PlayerTabel implements BaseColumns {
		public static final String TABLE_NAME = "player";
		public static final String COLUMN_NAME_NAME = "name";
		public static final String COLUMN_NAME_TOTAL_PLAY = "total_play";
		public static final String COLUMN_NAME_DAY_OR_NIGHT = "day_or_night";
		public static final String COLUMN_NAME_CURRENT_MONEY = "cur_money";
		public static final String COLUMN_NAME_TOTAL_GET_MONEY = "total_get_money";
		public static final String COLUMN_NAME_WIN = "win";
		public static final String COLUMN_NAME_LOSE = "lose";
		public static final String COLUMN_NAME_SCORE_STADIUM = "score_stadium";
	}
	public abstract class PlayerCD {
		private static final String TEXT_TYPE = " VARCHAR(20)";
		private static final String INTEGER_TYPE = " INTEGER";
		private static final String NOT_NULL = " NOT NULL";
		private static final String COMMA_SEP = ",";
		public static final String SQL_CREATE_ENTRIES =
			"CREATE TABLE " + Player.PlayerTabel.TABLE_NAME + " (" +
			Player.PlayerTabel._ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			Player.PlayerTabel.COLUMN_NAME_NAME + TEXT_TYPE + NOT_NULL + COMMA_SEP +
			Player.PlayerTabel.COLUMN_NAME_TOTAL_PLAY + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			Player.PlayerTabel.COLUMN_NAME_DAY_OR_NIGHT + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			Player.PlayerTabel.COLUMN_NAME_CURRENT_MONEY + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			Player.PlayerTabel.COLUMN_NAME_TOTAL_GET_MONEY + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			Player.PlayerTabel.COLUMN_NAME_WIN + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			Player.PlayerTabel.COLUMN_NAME_LOSE + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			Player.PlayerTabel.COLUMN_NAME_SCORE_STADIUM + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			"PRIMARY KEY (" + Player.PlayerTabel._ID + ")" + 
		")";
		
		public static final String SQL_DELETE_ENTRIES =
			"DROP TABLE IF EXISTS " + Player.PlayerTabel.TABLE_NAME;
		
		public static final String SQL_DATA_ENTRIES = 
			"INSERT INTO " + Player.PlayerTabel.TABLE_NAME + " VALUES " + 
			"(0,'DUMMY',10,0,30000,100000,2,3,0)";
	}
	public static int nextPlayerId(SQLiteDatabase db) {
		String[] projection = {
			Player.PlayerTabel._ID
			};

		Cursor c = db.query(
			Player.PlayerTabel.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			null,                       // The columns for the WHERE clause (String)
			null,                       // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);
		int max;
		int c_max;
		c.moveToFirst();
		max = c.getInt(c.getColumnIndexOrThrow(Player.PlayerTabel._ID));
		while(c.moveToNext()) {
			c_max = c.getInt(c.getColumnIndexOrThrow(Player.PlayerTabel._ID));
			if(c_max > max) max = c_max;
		}
		c.close();
		return max + 1;
	}
	public static boolean IsEmpty(SQLiteDatabase db) {
		String[] projection = {
			Player.PlayerTabel.COLUMN_NAME_NAME
			};

		Cursor c = db.query(
			Player.PlayerTabel.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			null,                       // The columns for the WHERE clause (String)
			null,                       // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);
		c.moveToFirst();
		boolean isEmpty = !c.moveToNext();
		c.close();
		return isEmpty;
	}
	public static ArrayList<String> getArrayName(SQLiteDatabase db) {
		ArrayList<String> st = new ArrayList<String>();
		
		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
			Player.PlayerTabel.COLUMN_NAME_NAME
			};

		Cursor c = db.query(
			Player.PlayerTabel.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			null,                       // The columns for the WHERE clause (String)
			null,                       // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);
		if(c.moveToFirst()) {
			while(c.moveToNext()) {
			    st.add((String) c.getString(c.getColumnIndexOrThrow(Item.ItemTable.COLUMN_NAME_NAME)));
			};
		}
		c.close();
		return st;
	}
	public static Player getPlayer(SQLiteDatabase db, String username) {
		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
			Player.PlayerTabel._ID,
			Player.PlayerTabel.COLUMN_NAME_NAME,
			Player.PlayerTabel.COLUMN_NAME_TOTAL_PLAY,
			Player.PlayerTabel.COLUMN_NAME_DAY_OR_NIGHT,
			Player.PlayerTabel.COLUMN_NAME_CURRENT_MONEY,
			Player.PlayerTabel.COLUMN_NAME_TOTAL_GET_MONEY,
			Player.PlayerTabel.COLUMN_NAME_WIN,
			Player.PlayerTabel.COLUMN_NAME_LOSE,
			Player.PlayerTabel.COLUMN_NAME_SCORE_STADIUM
			};

		String where = Player.PlayerTabel.COLUMN_NAME_NAME + " = '" + username + "'";
		
		Cursor c = db.query(
			Player.PlayerTabel.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			where,                      // The columns for the WHERE clause (String)
			null,                       // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);
		if(c.moveToFirst()) {
			Player player = new Player();
			player.setID(c.getInt(c.getColumnIndexOrThrow(Player.PlayerTabel._ID)));
			player.setName(c.getString(c.getColumnIndexOrThrow(Player.PlayerTabel.COLUMN_NAME_NAME)));
			player.setTotalTimePlay(c.getInt(c.getColumnIndexOrThrow(Player.PlayerTabel.COLUMN_NAME_TOTAL_PLAY)));
			player.setDayOrNight(c.getInt(c.getColumnIndexOrThrow(Player.PlayerTabel.COLUMN_NAME_DAY_OR_NIGHT)));
			player.setCurMoney(c.getInt(c.getColumnIndexOrThrow(Player.PlayerTabel.COLUMN_NAME_CURRENT_MONEY)));
			player.setTotalGetMoney(c.getInt(c.getColumnIndexOrThrow(Player.PlayerTabel.COLUMN_NAME_TOTAL_GET_MONEY)));
			player.setWin(c.getInt(c.getColumnIndexOrThrow(Player.PlayerTabel.COLUMN_NAME_WIN)));
			player.setLose(c.getInt(c.getColumnIndexOrThrow(Player.PlayerTabel.COLUMN_NAME_LOSE)));
			player.setScoreStadium(c.getInt(c.getColumnIndexOrThrow(Player.PlayerTabel.COLUMN_NAME_SCORE_STADIUM)));
			c.close();
			return player;
		} else {
			c.close();
			return null;
		}
	}
	
	public void saveDataToDatabase(SQLiteDatabase db) {
		// New value for one column
		ContentValues values = new ContentValues();
		values.put(Player.PlayerTabel.COLUMN_NAME_NAME, Main.player.getName());
		values.put(Player.PlayerTabel.COLUMN_NAME_CURRENT_MONEY, Main.player.getCurMoney());
		values.put(Player.PlayerTabel.COLUMN_NAME_TOTAL_GET_MONEY, Main.player.getTotalGetMoney());
		values.put(Player.PlayerTabel.COLUMN_NAME_TOTAL_PLAY, Main.player.getTotalTimePlay());
		values.put(Player.PlayerTabel.COLUMN_NAME_DAY_OR_NIGHT, Main.player.getDayOrNight());
		values.put(Player.PlayerTabel.COLUMN_NAME_WIN, Main.player.getWin());
		values.put(Player.PlayerTabel.COLUMN_NAME_LOSE, Main.player.getLose());
		values.put(Player.PlayerTabel.COLUMN_NAME_SCORE_STADIUM, Main.player.getScoreStadium());

		String selection = Player.PlayerTabel._ID + " = " + Main.player.getID();
		
		// Which row to update, based on the ID
		db.update(
			Player.PlayerTabel.TABLE_NAME,
			values,
			selection,
			null);
	}
	
	public static String getAllPlayerData(SQLiteDatabase db) {
		String[] projection = {
				Player.PlayerTabel._ID,
				Player.PlayerTabel.COLUMN_NAME_NAME,
				Player.PlayerTabel.COLUMN_NAME_TOTAL_PLAY,
				Player.PlayerTabel.COLUMN_NAME_DAY_OR_NIGHT,
				Player.PlayerTabel.COLUMN_NAME_CURRENT_MONEY,
				Player.PlayerTabel.COLUMN_NAME_TOTAL_GET_MONEY,
				Player.PlayerTabel.COLUMN_NAME_WIN,
				Player.PlayerTabel.COLUMN_NAME_LOSE,
				Player.PlayerTabel.COLUMN_NAME_SCORE_STADIUM
				};
			
			Cursor c = db.query(
				Player.PlayerTabel.TABLE_NAME,  // The table to query (String)
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
					message += "\nPlayer ID : " + c.getInt(c.getColumnIndexOrThrow(Player.PlayerTabel._ID));
					message += "\nName : " + c.getString(c.getColumnIndexOrThrow(Player.PlayerTabel.COLUMN_NAME_NAME));
					message += "\nTotal Play : " + c.getInt(c.getColumnIndexOrThrow(Player.PlayerTabel.COLUMN_NAME_TOTAL_PLAY));
					message += "\nDay or Night: " + c.getInt(c.getColumnIndexOrThrow(Player.PlayerTabel.COLUMN_NAME_DAY_OR_NIGHT));
					message += "\nCurrent Money: " + c.getInt(c.getColumnIndexOrThrow(Player.PlayerTabel.COLUMN_NAME_CURRENT_MONEY));
					message += "\nTotal spend money: " + c.getInt(c.getColumnIndexOrThrow(Player.PlayerTabel.COLUMN_NAME_TOTAL_GET_MONEY));
					message += "\nTotal win: " + c.getInt(c.getColumnIndexOrThrow(Player.PlayerTabel.COLUMN_NAME_WIN));
					message += "\nTotal lose: " + c.getInt(c.getColumnIndexOrThrow(Player.PlayerTabel.COLUMN_NAME_LOSE));
					message += "\nTotal Stadium Score" + c.getInt(c.getColumnIndexOrThrow(Player.PlayerTabel.COLUMN_NAME_SCORE_STADIUM));
					message += "\n";
				} while(c.moveToNext());
			}
			c.close();
			return message;
	}
}
