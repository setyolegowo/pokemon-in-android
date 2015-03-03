/**
 * 
 */
package user.pokeranch.models;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * @author Setyo Lego
 *
 */
public class PlayerParty {
	
	private static ArrayList<PlayerParty> ListPlayerParty;
	
	//Data member
	private int PlayerID;
	private int MonsterID;
    private int MonsterNumber;
    
    // Getter
    public int getPlayerID() { return PlayerID; }
    public int getMonsterID() { return MonsterID; }
	public int getMonsterNumber() { return MonsterNumber; }
	
	//Setter
	private void setPlayerID(int playerID) { PlayerID = playerID; }	
	private void setMonsterID(int monsterID) { MonsterID = monsterID; }	
	private void setMonsterNumber(int monsterNumber) { MonsterNumber = monsterNumber; }
	
	public abstract class PlayerPartyTable implements BaseColumns {
		public static final String TABLE_NAME = "player_party";
		public static final String COLUMN_NAME_PLAYER_ID = "player_id";
		public static final String COLUMN_NAME_MONSTER_ID = "monster_id";
	}
	
	public abstract class PlayerPartyCD {
		private static final String INTEGER_TYPE = " INTEGER";
		private static final String NOT_NULL = " NOT NULL";
		private static final String COMMA_SEP = ", ";
		public static final String SQL_CREATE_ENTRIES = 
			"CREATE TABLE " + PlayerParty.PlayerPartyTable.TABLE_NAME + " (" +
				PlayerParty.PlayerPartyTable._ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP + 
				PlayerParty.PlayerPartyTable.COLUMN_NAME_PLAYER_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
				PlayerParty.PlayerPartyTable.COLUMN_NAME_MONSTER_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
				" PRIMARY KEY (" + PlayerParty.PlayerPartyTable._ID + COMMA_SEP +
					PlayerParty.PlayerPartyTable.COLUMN_NAME_PLAYER_ID + COMMA_SEP +
					PlayerParty.PlayerPartyTable.COLUMN_NAME_MONSTER_ID + ")" + COMMA_SEP +
				" FOREIGN KEY (" + PlayerParty.PlayerPartyTable.COLUMN_NAME_PLAYER_ID + ") REFERENCES " +
					PlayerMonster.PlayerMonsterTable.TABLE_NAME + " (" + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PLAYER_ID + ") " + COMMA_SEP +
				" FOREIGN KEY (" + PlayerParty.PlayerPartyTable.COLUMN_NAME_MONSTER_ID + ") REFERENCES " +
					PlayerMonster.PlayerMonsterTable.TABLE_NAME + " (" + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_MONSTER_ID + ") " + COMMA_SEP +
				" FOREIGN KEY (" + PlayerParty.PlayerPartyTable._ID + ") REFERENCES " +
					PlayerMonster.PlayerMonsterTable.TABLE_NAME + " (" + PlayerMonster.PlayerMonsterTable._ID + ")" +
			")"
		;
		public static final String SQL_DELETE_ENTRIES = 
				"DROP TABLE IF EXISTS " + PlayerParty.PlayerPartyTable.TABLE_NAME;
			
		public static final String SQL_DATA_ENTRIES = 
				"INSERT INTO " + PlayerParty.PlayerPartyTable.TABLE_NAME + " VALUES " + "(1,0,1)";
	}
	
	public static void initPlayerParty(SQLiteDatabase db, int PlayerId) {
		String[] projection = {
			PlayerParty.PlayerPartyTable._ID,
			PlayerParty.PlayerPartyTable.COLUMN_NAME_PLAYER_ID,
			PlayerParty.PlayerPartyTable.COLUMN_NAME_MONSTER_ID,
			};
		
		Cursor c = db.query(
			PlayerParty.PlayerPartyTable.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			PlayerParty.PlayerPartyTable.COLUMN_NAME_PLAYER_ID + " = " + PlayerId,        // The columns for the WHERE clause (String)
			null,                       // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);
		
		ListPlayerParty = new ArrayList<PlayerParty>();
		if(c.moveToFirst()) {
			do {
				PlayerParty temp = new PlayerParty();
				temp.setPlayerID(PlayerId);
				temp.setMonsterID(c.getInt(c.getColumnIndexOrThrow(PlayerParty.PlayerPartyTable._ID)));
				temp.setMonsterNumber(c.getInt(c.getColumnIndexOrThrow(PlayerParty.PlayerPartyTable.COLUMN_NAME_MONSTER_ID)));
				ListPlayerParty.add(temp);
			} while(c.moveToNext());
		}
		c.close();
	}
	
	private PlayerParty() {
		
	}
	
	public PlayerParty(int player_id, int monster_num, int monster_id) {
		this.PlayerID = player_id;
		this.MonsterNumber = monster_num;
		this.MonsterID = monster_id;
		PlayerParty temp = new PlayerParty();
		temp.setPlayerID(player_id);
		temp.setMonsterNumber(monster_num);
		temp.setMonsterID(monster_id);
		ListPlayerParty.add(temp);
	}
	public PlayerParty(int player_id, PlayerMonster playerMonster) {
		this.PlayerID = player_id;
		this.MonsterNumber = playerMonster.getMonsterNumber();
		this.MonsterID = playerMonster.getMonsterID();
		PlayerParty temp = new PlayerParty();
		temp.setPlayerID(player_id);
		temp.setMonsterNumber(this.MonsterNumber);
		temp.setMonsterID(this.MonsterID);
		ListPlayerParty.add(temp);
	}
	
	public static int getMonsterIndex(int index) {
		return ListPlayerParty.get(index).getMonsterID();
	}
	
	public static int getMonsterNumberFromIndex(int index) {
		return ListPlayerParty.get(index).getMonsterNumber();
	}
	
	public static ArrayList<String> getNameMonster(SQLiteDatabase db) {
		if(ListPlayerParty.size() > 0) {
			ArrayList<String> list = new ArrayList<String>();
			for(int i = 0; i < ListPlayerParty.size(); i++) {
				PlayerMonster pm = PlayerMonster.getPlayerMonster(ListPlayerParty.get(i).getMonsterID());
				list.add(pm.getName());
			}
			return list;
		} else return null;
	}
	
	public static void DeleteMonsterAndPartyMonster(int MonsterId) {
		if(ListPlayerParty != null) {
			int i = 0;
			boolean found = false;
			while((i < ListPlayerParty.size()) && !found) {
				if(ListPlayerParty.get(i).getMonsterID() == MonsterId) {
					found = true;
					ListPlayerParty.remove(i);
					break;
				}
				else i++;
			}
			PlayerMonster.DeleteMonsterById(MonsterId);
		}
	}
	
	public static String getFullInfoMonster(int index_monster) {
		if(ListPlayerParty.size() > 0) {
			String message = "";
			PlayerMonster pm = PlayerMonster.getPlayerMonster(ListPlayerParty.get(index_monster).getMonsterID());
			message += "Monster Number: " + pm.getMonsterNumber() + "\n";
			message += "Name    : " + pm.getName() + "\n";
			message += "Level   : " + pm.getLevel() + "\n";
			message += "Age     : " + pm.getAge() + "\n";
			message += "Exp     : " + pm.getExp() + "\n";
			message += "HP      : " + pm.getCurHP() + "/" + pm.getHP() + "\n";
			message += "Attack  : " + pm.getAttack() + "\n";
			message += "Defense : " + pm.getDefense() + "\n";
			message += "Speed   : " + pm.getSpeed() + "\n";
			message += "Skill 1 : " + pm.getSkill1() + "\n";
			message += "Skill 2 : " + pm.getSkill2() + "\n";
			message += "Skill 3 : " + pm.getSkill3() + "\n";
			message += "Skill 4 : " + pm.getSkill4() + "\n";
			message += "PP Skill 1 : " + pm.getPPSkill1() + "\n";
			message += "PP Skill 2 : " + pm.getPPSkill2() + "\n";
			message += "PP Skill 3 : " + pm.getPPSkill3() + "\n";
			message += "PP Skill 4    : " + pm.getPPSkill4() + "\n";
			message += "Status Effect : " + pm.getStatusEffect() + "\n";
			return message;
		} else return null;
	}
	
	public static void savePlayerPartyToDB(SQLiteDatabase db, int player_id) {
		if(ListPlayerParty != null) {
			if(ListPlayerParty.size() > 0) {
				db.execSQL("DELETE FROM " + PlayerParty.PlayerPartyTable.TABLE_NAME + " WHERE " + 
					PlayerParty.PlayerPartyTable.COLUMN_NAME_PLAYER_ID + " LIKE " + player_id);
				for(int i = 0; i < ListPlayerParty.size(); i++) {
					String insert = "INSERT INTO " + PlayerParty.PlayerPartyTable.TABLE_NAME + " VALUES " +
						"(" + ListPlayerParty.get(i).getMonsterID() + ", " + 
						ListPlayerParty.get(i).getPlayerID() + ", " +
						ListPlayerParty.get(i).getMonsterNumber() + ")";
					db.execSQL(insert);
				}
			}
		}
	}
	
	public static String GetData(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String message = "";
		String[] projection = {
			PlayerParty.PlayerPartyTable._ID,
			PlayerParty.PlayerPartyTable.COLUMN_NAME_PLAYER_ID,
			PlayerParty.PlayerPartyTable.COLUMN_NAME_MONSTER_ID,
			};
		
		Cursor c = db.query(
			PlayerParty.PlayerPartyTable.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			null,                       // The columns for the WHERE clause (String)
			null,                       // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);
		
		if(c.moveToFirst()) {
			do {
				message += "\nPlayer id: " + c.getInt(c.getColumnIndexOrThrow(PlayerParty.PlayerPartyTable.COLUMN_NAME_PLAYER_ID));
				message += "\nMonster id: " + c.getInt(c.getColumnIndexOrThrow(PlayerParty.PlayerPartyTable.COLUMN_NAME_MONSTER_ID));
				message += "\nMonster index: " + c.getInt(c.getColumnIndexOrThrow(PlayerParty.PlayerPartyTable._ID));
				message += "\n";
			} while(c.moveToNext());
		}
		return message;
	}
	
	public static boolean isCanAddToParty() {
		return ListPlayerParty.size() < 6;
	}
	
	public static int getTotalParty() {
		return ListPlayerParty.size();
	}
}
