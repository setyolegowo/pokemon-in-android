/**
 * 
 */
package user.pokeranch.models;

import java.util.ArrayList;

import user.pokeranch.Main;
import user.pokeranch.models.type.StatusEffect;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;


/**
 * @author Setyo Lego
 *
 */
public class PlayerMonster {
	
	private static final int MinimumAgeDie = 1000;
	private static final int MaksimumAgeDie = 1050;
	
	private static ArrayList<PlayerMonster> ListPlayerMonster;
	
	//Data member
	private int PlayerID;
	private int MonsterID;
    private int MonsterNumber;
    private String Name;
    private int Age;
    private int Level;
    private int Exp;	
    private int HP;
    private int CurHP;
    private int Attack;	    
	private int Defense;
	private int Speed;
	private int Skill1;
	private int Skill2;
	private int Skill3;
	private int Skill4;
	private int PPSkill1;
	private int PPSkill2;
	private int PPSkill3;
	private int PPSkill4;
	private int Status_Effect;
	
	//Getter
    public int getPlayerID() { return PlayerID; }
    public int getMonsterID() { return MonsterID; }
	public int getMonsterNumber() { return MonsterNumber; }
	public String getName() { return Name; }
	public int getAge() { return Age; }
	public int getLevel() { return Level; }
	public int getExp() { return Exp; }
	public int getHP() { return HP; }
	public int getCurHP() { return CurHP; }
	public int getAttack() { return Attack; }
	public int getDefense() { return Defense; }
	public int getSpeed() { return Speed; }
	public int getSkill1() { return Skill1; }
	public int getSkill2() { return Skill2; }
	public int getSkill3() { return Skill3; }
	public int getSkill4() { return Skill4; }
	public int getPPSkill1() { return PPSkill1; }
	public int getPPSkill2() { return PPSkill2; }
	public int getPPSkill3() { return PPSkill3; }
	public int getPPSkill4() {return PPSkill4; }
	public int getStatusEffect() { return Status_Effect; }
	
	//Setter
	private void setPlayerID(int playerID) { PlayerID = playerID; }	
	private void setMonsterID(int monsterID) { MonsterID = monsterID; }	
	private void setMonsterNumber(int monsterNumber) { MonsterNumber = monsterNumber; }	
	public void setName(String name) { Name = name; }	
	public void setAge(int age) { Age = age; }	
	public void setLevel(int level) { Level = level; }	
	public void setExp(int exp) { Exp = exp; }	
	public void setHP(int hP) { HP = hP; }
	public void setCurHP(int curHP) { CurHP = curHP; }
	public void setAttack(int attack) { Attack = attack; }	
	public void setDefense(int defense) { Defense = defense; }	
	public void setSpeed(int speed) { Speed = speed; }	
	public void setSkill1(int skill1) { Skill1 = skill1; }	
	public void setSkill2(int skill2) { Skill2 = skill2; }	
	public void setSkill3(int skill3) { Skill3 = skill3; }
	public void setSkill4(int skill4) { Skill4 = skill4; }	
	public void setPPSkill1(int pPSkill1) { PPSkill1 = pPSkill1; }
	public void setPPSkill2(int pPSkill2) { PPSkill2 = pPSkill2; }	
	public void setPPSkill3(int pPSkill3) { PPSkill3 = pPSkill3; }
	public void setPPSkill4(int pPSkill4) { PPSkill4 = pPSkill4; }	
	public void setStatusEffect(int statusEffect) {Status_Effect = statusEffect; }	
	
	public abstract class PlayerMonsterTable implements BaseColumns {
		public static final String TABLE_NAME = "player_monster";
		public static final String COLUMN_NAME_PLAYER_ID = "player_id";
		public static final String COLUMN_NAME_MONSTER_ID = "monster_id";
		public static final String COLUMN_NAME_NAME = "name";
		public static final String COLUMN_NAME_AGE = "age";
		public static final String COLUMN_NAME_LEVEL = "level";
		public static final String COLUMN_NAME_EXP = "experience";
		public static final String COLUMN_NAME_HP = "HP";
		public static final String COLUMN_NAME_CUR_HP = "current_hp";
		public static final String COLUMN_NAME_ATTACK = "attack";
		public static final String COLUMN_NAME_DEFENSE = "defense";
		public static final String COLUMN_NAME_SPEED = "speed";
		public static final String COLUMN_NAME_NO_SKILL_1 = "skill_1";
		public static final String COLUMN_NAME_NO_SKILL_2 = "skill_2";
		public static final String COLUMN_NAME_NO_SKILL_3 = "skill_3";
		public static final String COLUMN_NAME_NO_SKILL_4 = "skill_4";
		public static final String COLUMN_NAME_PP_SKILL_1 = "pp_skill_1";
		public static final String COLUMN_NAME_PP_SKILL_2 = "pp_skill_2";
		public static final String COLUMN_NAME_PP_SKILL_3 = "pp_skill_3";
		public static final String COLUMN_NAME_PP_SKILL_4 = "pp_skill_4";
		public static final String COLUMN_NAME_STATUS_EFFECT = "status_effect";
	}
	public abstract class PlayerMonsterCD {
		private static final String TEXT_TYPE = " TEXT";
		private static final String INTEGER_TYPE = " INTEGER";
		private static final String NOT_NULL = " NOT NULL";
		private static final String COMMA_SEP = ", ";
		public static final String SQL_CREATE_ENTRIES = 
			"CREATE TABLE " + PlayerMonster.PlayerMonsterTable.TABLE_NAME + " (" +
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PLAYER_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_MONSTER_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			PlayerMonster.PlayerMonsterTable._ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NAME + TEXT_TYPE + NOT_NULL + COMMA_SEP +
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_AGE + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_LEVEL + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_EXP + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_HP + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_CUR_HP + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_ATTACK + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_DEFENSE + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_SPEED + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NO_SKILL_1 + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NO_SKILL_2 + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NO_SKILL_3 + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NO_SKILL_4 + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PP_SKILL_1 + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PP_SKILL_2 + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PP_SKILL_3 + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PP_SKILL_4 + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_STATUS_EFFECT + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			"PRIMARY KEY (" + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PLAYER_ID + COMMA_SEP +
				PlayerMonster.PlayerMonsterTable.COLUMN_NAME_MONSTER_ID + COMMA_SEP +
				PlayerMonster.PlayerMonsterTable._ID + ")" +
			"FOREIGN KEY (" + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PLAYER_ID + ") REFERENCES " +
				Player.PlayerTabel.TABLE_NAME + " (" + Player.PlayerTabel._ID + ")" + COMMA_SEP +
			"FOREIGN KEY (" + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_MONSTER_ID + ") REFERENCES " +
				Monster.MonsterTable.TABLE_NAME + " (" + Monster.MonsterTable._ID + ")" + COMMA_SEP +
			"FOREIGN KEY (" + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NO_SKILL_1 + ") REFERENCES " +
				Skill.SkillTable.TABLE_NAME + " (" + Skill.SkillTable._ID + ")" + COMMA_SEP +
			"FOREIGN KEY (" + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NO_SKILL_2 + ") REFERENCES " +
				Skill.SkillTable.TABLE_NAME + " (" + Skill.SkillTable._ID + ")" + COMMA_SEP +
			"FOREIGN KEY (" + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NO_SKILL_3 + ") REFERENCES " +
				Skill.SkillTable.TABLE_NAME + " (" + Skill.SkillTable._ID + ")" + COMMA_SEP +
			"FOREIGN KEY (" + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NO_SKILL_4 + ") REFERENCES " +
				Skill.SkillTable.TABLE_NAME + " (" + Skill.SkillTable._ID + ")" + COMMA_SEP +
			"FOREIGN KEY (" + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_STATUS_EFFECT + ") REFERENCES " +
				StatusEffect.StatusEffectTable.TABLE_NAME + " (" + StatusEffect.StatusEffectTable._ID + ")" +
			")"; // 21
		public static final String SQL_DELETE_ENTRIES = 
			"DROP TABLE IF EXISTS " + PlayerMonster.PlayerMonsterTable.TABLE_NAME;
		
		public static final String SQL_DATA_ENTRIES = 
			"INSERT INTO " + PlayerMonster.PlayerMonsterTable.TABLE_NAME + " VALUES " + 
			"(0,1,1,'Nama Monster',0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)"; //19
			
				/*"INSERT INTO " + PlayerMonster.PlayerMonsterTable.TABLE_NAME +
				" SELECT 0 AS " + PlayerMonster.PlayerMonsterTable._ID + COMMA_SEP +
					//"'NOT MONSTER' AS " + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_SPESIES + COMMA_SEP +
					"0 AS " + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PLAYER_ID + COMMA_SEP +
					"0 AS " + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_MONSTER_ID + COMMA_SEP +
					"0 AS " + PlayerMonster.PlayerMonsterTable._ID + COMMA_SEP +
					"0 AS " + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NAME + COMMA_SEP +
					"0 AS " + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_AGE + COMMA_SEP +
					"0 AS " + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_LEVEL + COMMA_SEP +
					"0 AS " + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_EXP + COMMA_SEP +
					"0 AS " + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_HP + COMMA_SEP +
					"0 AS " + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_CUR_HP + COMMA_SEP +
					"0 AS " + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_ATTACK + COMMA_SEP +
					"0 AS " + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_DEFENSE + COMMA_SEP +
					"0 AS " + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_SPEED+ COMMA_SEP +
					"0 AS " + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NO_SKILL_1 + COMMA_SEP +
					"0 AS " + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NO_SKILL_2 + COMMA_SEP +
					"0 AS " + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NO_SKILL_3 + COMMA_SEP +
					"0 AS " + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NO_SKILL_4 + COMMA_SEP +
					"0 AS " + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PP_SKILL_1 + COMMA_SEP +
					"0 AS " + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PP_SKILL_2 + COMMA_SEP +
					"0 AS " + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PP_SKILL_3 + COMMA_SEP +
					"0 AS " + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PP_SKILL_4 + COMMA_SEP +
					"0 AS " + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_STATUS_EFFECT;*/
	}
	
	public static void initPlayerMonster(SQLiteDatabase db, int PlayerId) {
		String[] projection = {
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PLAYER_ID,
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_MONSTER_ID,
			PlayerMonster.PlayerMonsterTable._ID,
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NAME, // BELUM SEMUA
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_AGE,
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_LEVEL,
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_EXP,
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_HP,
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_CUR_HP,
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_ATTACK,
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_DEFENSE,
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_SPEED,
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NO_SKILL_1,
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NO_SKILL_2,
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NO_SKILL_3,
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NO_SKILL_4,
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PP_SKILL_1,
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PP_SKILL_2,
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PP_SKILL_3,
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PP_SKILL_4,
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_STATUS_EFFECT
			};
		
		Cursor c = db.query(
			PlayerMonster.PlayerMonsterTable.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PLAYER_ID + " = " + PlayerId,        // The columns for the WHERE clause (String)
			null,                       // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);
		
		ListPlayerMonster = new ArrayList<PlayerMonster>();
		if(c.moveToFirst()) {
			do {
				PlayerMonster temp = new PlayerMonster();
				temp.setPlayerID(PlayerId);
				temp.setMonsterID(c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable._ID)));
				temp.setMonsterNumber(c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_MONSTER_ID)));
				temp.setName(c.getString(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NAME)));
				temp.setAge(c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_AGE)));
				temp.setLevel(c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_LEVEL)));
				temp.setExp(c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_EXP)));
				temp.setHP(c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_HP)));
				temp.setCurHP(c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_CUR_HP)));
				temp.setAttack(c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_ATTACK)));
				temp.setDefense(c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_DEFENSE)));
				temp.setSpeed(c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_SPEED)));
				temp.setSkill1(c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NO_SKILL_1)));
				temp.setSkill2(c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NO_SKILL_2)));
				temp.setSkill3(c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NO_SKILL_3)));
				temp.setSkill4(c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NO_SKILL_4)));
				temp.setPPSkill1(c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PP_SKILL_1)));
				temp.setPPSkill2(c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PP_SKILL_2)));
				temp.setPPSkill3(c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PP_SKILL_3)));
				temp.setPPSkill4(c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PP_SKILL_4)));
				temp.setStatusEffect(c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_STATUS_EFFECT)));
				ListPlayerMonster.add(temp);
			} while(c.moveToNext());
		}
		c.close();
	}
	
	private PlayerMonster() {
		
	}
	
	public PlayerMonster(SQLiteDatabase db, int id_monster, int player_id, String name, int level) {
		String[] projection = {
			PlayerMonster.PlayerMonsterTable._ID
			};
		
		String where_col = PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PLAYER_ID + " LIKE ?";
		String[] where_val = {"" + player_id};
		
		Cursor c = db.query(
			PlayerMonster.PlayerMonsterTable.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			where_col,                  // The columns for the WHERE clause (String)
			where_val,                  // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);
		int next_id = 0;
		if(c.moveToFirst()) {
			int max = c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable._ID));
			while(c.moveToNext()) {
				int get = c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable._ID));
				if(get > max) max = get;
			};
			next_id = max + 1;
		}
		c.close();
		
		// START to BUILD
		Monster monster = new Monster(db, id_monster);
		this.PlayerID = Main.player.getID();
		this.MonsterID = next_id;
		this.MonsterNumber = id_monster;
		this.Name = name;
		this.Age = 0;
		this.Level = level;
		this.Exp = 0;
		this.HP = (((monster.getBaseHP() + 100)*level)/50) + 10;
		this.CurHP = Integer.valueOf(this.HP);
		this.Attack = (((monster.getBaseAttack() + 100)*level)/50) + 10;
		this.Defense = (((monster.getBaseDefense() + 100)*level)/50) + 10;
		this.Speed = (((monster.getBaseSpeed() + 100)*level)/50) + 10;
		this.Skill1 = monster.getDefaultSkill1();
		this.Skill2 = monster.getDefaultSkill2();
		this.Skill3 = monster.getDefaultSkill3();
		this.Skill4 = monster.getDefaultSkill4();
		if(this.Skill1 > 0) this.PPSkill1 = Skill.getDefPPSkill(db, this.Skill1);
		if(this.Skill2 > 0) this.PPSkill2 = Skill.getDefPPSkill(db, this.Skill2);
		if(this.Skill3 > 0) this.PPSkill3 = Skill.getDefPPSkill(db, this.Skill3);
		if(this.Skill4 > 0) this.PPSkill4 = Skill.getDefPPSkill(db, this.Skill4);
		this.Status_Effect = 0;
		PlayerMonster temp = new PlayerMonster();
		temp.setPlayerID(this.PlayerID);
		temp.setMonsterID(this.MonsterID);
		temp.setMonsterNumber(this.MonsterNumber);
		temp.setName(this.Name);
		temp.setAge(this.Age);
		temp.setLevel(this.Level);
		temp.setExp(this.Exp);
		temp.setHP(this.HP);
		temp.setCurHP(this.CurHP);
		temp.setAttack(this.Attack);
		temp.setDefense(this.Defense);
		temp.setSpeed(this.Speed);
		temp.setSkill1(this.Skill1);
		temp.setSkill2(this.Skill2);
		temp.setSkill3(this.Skill3);
		temp.setSkill4(this.Skill4);
		temp.setPPSkill1(this.PPSkill1);
		temp.setPPSkill2(this.PPSkill2);
		temp.setPPSkill3(this.PPSkill3);
		temp.setPPSkill4(this.PPSkill4);
		temp.setStatusEffect(this.Status_Effect);
		ListPlayerMonster.add(temp);
	}
	
	
	public PlayerMonster(SQLiteDatabase db, Monster monster, int player_id, String name, int level) {
		String[] projection = {
			PlayerMonster.PlayerMonsterTable._ID
			};
		
		String where_col = PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PLAYER_ID + " LIKE ?";
		String[] where_val = {"" + player_id};
		
		Cursor c = db.query(
			PlayerMonster.PlayerMonsterTable.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			where_col,                  // The columns for the WHERE clause (String)
			where_val,                  // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);
		int next_id = 0;
		if(c.moveToFirst()) {
			int max = c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable._ID));
			while(c.moveToNext()) {
				int get = c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable._ID));
				if(get > max) max = get;
			};
			next_id = max + 1;
		}
		c.close();
		
		// START to BUILD
		this.PlayerID = Main.player.getID();
		this.MonsterID = next_id;
		// this.MonsterNumber = id_monster;
		this.Name = name;
		this.Age = 0;
		this.Level = level;
		this.Exp = 0;
		this.HP = (((monster.getBaseHP() + 100)*level)/50) + 10;
		this.CurHP = this.HP;
		this.Attack = (((monster.getBaseAttack() + 100)*level)/50) + 10;
		this.Defense = (((monster.getBaseDefense() + 100)*level)/50) + 10;
		this.Speed = (((monster.getBaseSpeed() + 100)*level)/50) + 10;
		this.Skill1 = monster.getDefaultSkill1();
		this.Skill2 = monster.getDefaultSkill2();
		this.Skill3 = monster.getDefaultSkill3();
		this.Skill4 = monster.getDefaultSkill4();
		if(this.Skill1 > 0) this.PPSkill1 = Skill.getDefPPSkill(db, this.Skill1);
		if(this.Skill2 > 0) this.PPSkill2 = Skill.getDefPPSkill(db, this.Skill2);
		if(this.Skill3 > 0) this.PPSkill3 = Skill.getDefPPSkill(db, this.Skill3);
		if(this.Skill4 > 0) this.PPSkill4 = Skill.getDefPPSkill(db, this.Skill4);
		this.Status_Effect = 0;
		PlayerMonster temp = new PlayerMonster();
		temp.setPlayerID(this.PlayerID);
		temp.setMonsterID(this.MonsterID);
		// temp.setMonsterNumber(this.MonsterNumber);
		temp.setName(this.Name);
		temp.setAge(this.Age);
		temp.setLevel(this.Level);
		temp.setExp(this.Exp);
		temp.setHP(this.HP);
		temp.setCurHP(this.CurHP);
		temp.setAttack(this.Attack);
		temp.setDefense(this.Defense);
		temp.setSpeed(this.Speed);
		temp.setSkill1(this.Skill1);
		temp.setSkill2(this.Skill2);
		temp.setSkill3(this.Skill3);
		temp.setSkill4(this.Skill4);
		temp.setPPSkill1(this.PPSkill1);
		temp.setPPSkill2(this.PPSkill2);
		temp.setPPSkill3(this.PPSkill3);
		temp.setPPSkill4(this.PPSkill4);
		temp.setStatusEffect(this.Status_Effect);
		ListPlayerMonster.add(temp);
	}
	
	
	public static void DeleteMonsterById(int MonsterId) {
		if(ListPlayerMonster != null) {
			int i = 0;
			boolean found = false;
			while((i < ListPlayerMonster.size()) && !found) {
				if(ListPlayerMonster.get(i).getMonsterID() == MonsterId) {
					found = true;
					ListPlayerMonster.remove(i);
					break;
				}
				else i++;
			}
		}
	}
	
	public static String getSomeDataPM(SQLiteDatabase db) {
		String message = "";
		String[] selection = {
			PlayerMonster.PlayerMonsterTable._ID,
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PLAYER_ID,
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_MONSTER_ID,
			PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NAME
		};
		
		Cursor c = db.query(PlayerMonster.PlayerMonsterTable.TABLE_NAME,
			selection,
			null, null, null, null, null);
		
		if(c.moveToFirst()) {
			do {
				message += "\nPlayer ID : " + c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PLAYER_ID));
				message += "\nMonster ID : " + c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_MONSTER_ID));
				message += "\nIndex Monster : " + c.getInt(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable._ID));
				message += "\nName : " + c.getString(c.getColumnIndexOrThrow(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NAME));
				message += "\n";
			} while(c.moveToNext());
		}
		return message;
	}
	
	public static PlayerMonster getPlayerMonster(int monster_Id) {
		if(ListPlayerMonster != null) {
			int i = 0;
			boolean found = false;
			while((i < ListPlayerMonster.size()) && !found) {
				if(ListPlayerMonster.get(i).getMonsterID() == monster_Id) {
					found = true;
					break;
				}
				else i++;
			}
			if(!found) return null;
			else {
				return ListPlayerMonster.get(i);
			}
		} else return null;
	}
	
	public static String GetData(SQLiteDatabase readableDatabase) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void savePlayerMonsterToDatabase(SQLiteDatabase db) {
		db.execSQL("DELETE FROM " + PlayerMonster.PlayerMonsterTable.TABLE_NAME + 
				" WHERE " + PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PLAYER_ID + " LIKE " + Main.player.getID());
		
		for(int i = 0; i < ListPlayerMonster.size(); i++) {
			ContentValues cv = new ContentValues();
			cv.put(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PLAYER_ID, ListPlayerMonster.get(i).getPlayerID());
			cv.put(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_MONSTER_ID, ListPlayerMonster.get(i).getMonsterNumber());
			cv.put(PlayerMonster.PlayerMonsterTable._ID, ListPlayerMonster.get(i).getMonsterID());
			cv.put(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NAME, ListPlayerMonster.get(i).getName());
			cv.put(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_AGE, ListPlayerMonster.get(i).getAge());
			cv.put(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_LEVEL, ListPlayerMonster.get(i).getLevel());
			cv.put(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_EXP, ListPlayerMonster.get(i).getExp());
			cv.put(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_HP, ListPlayerMonster.get(i).getHP());
			cv.put(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_CUR_HP, ListPlayerMonster.get(i).getCurHP());
			cv.put(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_ATTACK, ListPlayerMonster.get(i).getAttack());
			cv.put(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_DEFENSE, ListPlayerMonster.get(i).getDefense());
			cv.put(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_SPEED, ListPlayerMonster.get(i).getSpeed());
			cv.put(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NO_SKILL_1, ListPlayerMonster.get(i).getSkill1());
			cv.put(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NO_SKILL_2, ListPlayerMonster.get(i).getSkill2());
			cv.put(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NO_SKILL_3, ListPlayerMonster.get(i).getSkill3());
			cv.put(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_NO_SKILL_4, ListPlayerMonster.get(i).getSkill4());
			cv.put(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PP_SKILL_1, ListPlayerMonster.get(i).getPPSkill1());
			cv.put(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PP_SKILL_2, ListPlayerMonster.get(i).getPPSkill2());
			cv.put(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PP_SKILL_3, ListPlayerMonster.get(i).getPPSkill3());
			cv.put(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_PP_SKILL_4, ListPlayerMonster.get(i).getPPSkill4());
			cv.put(PlayerMonster.PlayerMonsterTable.COLUMN_NAME_STATUS_EFFECT, ListPlayerMonster.get(i).getStatusEffect());
			db.insert(PlayerMonster.PlayerMonsterTable.TABLE_NAME, 
				null, 
				cv);
		}
	}
	
	public static int getTotalParty() {
		return ListPlayerMonster.size();
	}
	
	public static void renderEvolution(SQLiteDatabase db) {
		for(PlayerMonster pm:ListPlayerMonster) {
			Monster monster = new Monster(db,pm.getMonsterNumber());
			if((monster.getMinEvoLevel() != 0) && (monster.getMinEvoLevel() <= pm.getLevel())) {
				
				// HP
				int incHP = pm.getHP() - (((monster.getBaseHP() + 100)*(pm.getLevel() - 1))/50) + 10;
				int incAtk = pm.getAttack() - (((monster.getBaseAttack() + 100)*(pm.getLevel() - 1))/50) + 10;
				int incDfn = pm.getDefense() - (((monster.getBaseDefense() + 100)*(pm.getLevel() - 1))/50) + 10;
				int incSpd = pm.getSpeed() - (((monster.getBaseSpeed() + 100)*(pm.getLevel() - 1))/50) + 10;
				
				pm.setMonsterNumber(monster.getNextEvoNo());
				Monster monsterEvo = new Monster(db,pm.getMonsterNumber());
				
				pm.setHP((((monsterEvo.getBaseHP() + 100)*(pm.getLevel()))/50) + 10 + incHP);
				pm.setAttack((((monsterEvo.getBaseAttack() + 100)*(pm.getLevel()))/50) + 10 + incAtk);
				pm.setDefense((((monsterEvo.getBaseDefense() + 100)*(pm.getLevel()))/50) + 10 + incDfn);
				pm.setSpeed((((monsterEvo.getBaseSpeed() + 100)*(pm.getLevel()))/50) + 10 + incSpd);
			}
		}
	}
	
	public static int incEXPOfMonsterIndex(int incExp, int monster_id, SQLiteDatabase db) {
		int index = 0;
		while(ListPlayerMonster.get(index).getMonsterID() != monster_id) {
			index++;
			Log.d("CHECK CHECK", "DUARRRR");
		}
		Log.d("CHECK CHECK", "DUARRRR 2");
		ListPlayerMonster.get(index).setExp(ListPlayerMonster.get(index).getExp() + incExp);
		if(ListPlayerMonster.get(index).getExp() > (ListPlayerMonster.get(index).getLevel()*ListPlayerMonster.get(index).getLevel()*ListPlayerMonster.get(index).getLevel())) {
			ListPlayerMonster.get(index).setExp(ListPlayerMonster.get(index).getExp() - (ListPlayerMonster.get(index).getLevel()*ListPlayerMonster.get(index).getLevel()*ListPlayerMonster.get(index).getLevel()));
			Log.d("CHECK CHECK", "DUARRRR 3");
			ListPlayerMonster.get(index).setLevel(ListPlayerMonster.get(index).getLevel() + 1);
			Log.d("CHECK CHECK", "DUARRRR NAIK LEVEL");
			// .:::: REKONSTRUKSI
			Monster monster = new Monster(db,ListPlayerMonster.get(index).getMonsterNumber());
			// ATTACK
			int inc = ListPlayerMonster.get(index).getHP() - (((monster.getBaseHP() + 100)*(ListPlayerMonster.get(index).getLevel() - 1))/50) + 10;
			ListPlayerMonster.get(index).setHP((((monster.getBaseHP() + 100)*(ListPlayerMonster.get(index).getLevel()))/50) + 10 + inc);
			
			inc = ListPlayerMonster.get(index).getAttack() - (((monster.getBaseAttack() + 100)*(ListPlayerMonster.get(index).getLevel() - 1))/50) + 10;
			ListPlayerMonster.get(index).setAttack((((monster.getBaseAttack() + 100)*(ListPlayerMonster.get(index).getLevel()))/50) + 10 + inc);
			
			inc = ListPlayerMonster.get(index).getDefense() - (((monster.getBaseDefense() + 100)*(ListPlayerMonster.get(index).getLevel() - 1))/50) + 10;
			ListPlayerMonster.get(index).setDefense((((monster.getBaseDefense() + 100)*(ListPlayerMonster.get(index).getLevel()))/50) + 10 + inc);
			
			inc = ListPlayerMonster.get(index).getSpeed() - (((monster.getBaseSpeed() + 100)*(ListPlayerMonster.get(index).getLevel() - 1))/50) + 10;
			ListPlayerMonster.get(index).setSpeed((((monster.getBaseSpeed() + 100)*(ListPlayerMonster.get(index).getLevel()))/50) + 10 + inc);
			Log.d("CHECK CHECK", "Before skill");
			Skill skill;
			if(ListPlayerMonster.get(index).getSkill1() != 0) {
				skill = new Skill(ListPlayerMonster.get(index).getSkill1(), db);
				if((skill.getSkillEvoAt() <= ListPlayerMonster.get(index).getLevel()) && (skill.getSkillEvoTo() != 0)) {
					ListPlayerMonster.get(index).setSkill1(skill.getSkillEvoTo());
					ListPlayerMonster.get(index).setPPSkill1(skill.getDefaultPP() + 2);
				}
			}
			Log.d("CHECK CHECK", "Before skill 2");
			if(ListPlayerMonster.get(index).getSkill2() != 0) {
				skill = new Skill(ListPlayerMonster.get(index).getSkill2(), db);
				if((skill.getSkillEvoAt() <= ListPlayerMonster.get(index).getLevel()) && (skill.getSkillEvoTo() != 0)) {
					ListPlayerMonster.get(index).setSkill2(skill.getSkillEvoTo());
					ListPlayerMonster.get(index).setPPSkill2(skill.getDefaultPP() + 2);
				}
			}
			Log.d("CHECK CHECK", "Before skill 3");
			if(ListPlayerMonster.get(index).getSkill3() != 0) {
				skill = new Skill(ListPlayerMonster.get(index).getSkill3(), db);
				if((skill.getSkillEvoAt() <= ListPlayerMonster.get(index).getLevel()) && (skill.getSkillEvoTo() != 0)) {
					ListPlayerMonster.get(index).setSkill3(skill.getSkillEvoTo());
					ListPlayerMonster.get(index).setPPSkill3(skill.getDefaultPP() + 2);
				}
			}
			Log.d("CHECK CHECK", "Before skill 4");
			if(ListPlayerMonster.get(index).getSkill4() != 0) {
				skill = new Skill(ListPlayerMonster.get(index).getSkill4(), db);
				if((skill.getSkillEvoAt() <= ListPlayerMonster.get(index).getLevel()) && (skill.getSkillEvoTo() != 0)) {
					ListPlayerMonster.get(index).setSkill4(skill.getSkillEvoTo());
					ListPlayerMonster.get(index).setPPSkill4(skill.getDefaultPP() + 2);
				}
			}
			Log.d("CHECK CHECK", "Before skill after");
			return 2;
		} else return 1;
	}
	
	public static void fullRestoreAllMonster() {
		int index = 0;
		while(index < ListPlayerMonster.size()) {
			int HP = ListPlayerMonster.get(index).getHP();
			ListPlayerMonster.get(index).setCurHP(HP);
			ListPlayerMonster.get(index).setStatusEffect(0);
			index++;
		}
	}
	
	public static void changeHPOfMonsterIndex(int curHP, int monster_id) {
		int index = 0;
		while(true) {
			if(ListPlayerMonster.get(index).getMonsterID() == monster_id)
				break;
		}
		ListPlayerMonster.get(index).setCurHP(curHP);
	}
	
	public static String listNameMonsterDied() {
		String message = "";
		int index = 0;
		int count = 0;
		while(index < ListPlayerMonster.size()) {
			if((ListPlayerMonster.get(index).getAge()
					+ (Math.random()*(MaksimumAgeDie - MinimumAgeDie)/2)
					+ (Math.random()*(MaksimumAgeDie - MinimumAgeDie)/2) > MinimumAgeDie) 
				&& (ListPlayerMonster.get(index).getAge() > MinimumAgeDie)
				) {
				count = count + 1;
				if(count > 1) message += ", ";
				message += ListPlayerMonster.get(index).getName();
				ListPlayerMonster.remove(index);
			} else
				index++;
		}
		if(count == 0) {
			return "";
		} else {
			return message;
		}
	}
	
	public static void incAgeOfMonster() {
		int index = 0;
		while(index < ListPlayerMonster.size()) {
			ListPlayerMonster.get(index).setAge(ListPlayerMonster.get(index).getAge() + 1);
			index++;
		}
	}
	
	public static void updatePlayerMonster(PlayerMonster monster) {
		int i = 0;
		while(ListPlayerMonster.get(i).getMonsterID() != monster.getMonsterID()) {
			i++;
		}
		ListPlayerMonster.get(i).setName(monster.getName());
		ListPlayerMonster.get(i).setAge(monster.getAge());
		ListPlayerMonster.get(i).setLevel(monster.getLevel());
		ListPlayerMonster.get(i).setExp(monster.getExp());
		ListPlayerMonster.get(i).setHP(monster.getHP());
		ListPlayerMonster.get(i).setCurHP(monster.getCurHP());
		ListPlayerMonster.get(i).setAttack(monster.getAttack());
		ListPlayerMonster.get(i).setDefense(monster.getDefense());
		ListPlayerMonster.get(i).setSpeed(monster.getSpeed());
		ListPlayerMonster.get(i).setSkill1(monster.getSkill1());
		ListPlayerMonster.get(i).setSkill2(monster.getSkill2());
		ListPlayerMonster.get(i).setSkill3(monster.getSkill3());
		ListPlayerMonster.get(i).setSkill4(monster.getSkill4());
		ListPlayerMonster.get(i).setPPSkill1(monster.getPPSkill1());
		ListPlayerMonster.get(i).setPPSkill2(monster.getPPSkill2());
		ListPlayerMonster.get(i).setPPSkill3(monster.getPPSkill3());
		ListPlayerMonster.get(i).setPPSkill4(monster.getPPSkill4());
		ListPlayerMonster.get(i).setStatusEffect(monster.getStatusEffect());
	}
}
