/**
 * 
 */
package user.pokeranch.models;

import java.util.ArrayList;

import user.pokeranch.models.type.Element;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * @author Setyo Lego
 *
 */
public class Monster {
	//Data member
	private int ID;
	private String Spesies;
	private int Elemen_No;
	private int BaseHP;
	private int BaseAttack;
	private int BaseDefense;
	private int BaseSpeed;
	private int DefaultSkill1;
	private int DefaultSkill2;
	private int DefaultSkill3;
	private int DefaultSkill4;
	private int MinEvoLevel;
	private int NextEvoNo;
	private int Gentayangan; // Keluar malam atau siang. 1 == Keluar malam. 0 == keluar siang.
	
	//constructor
	public Monster(SQLiteDatabase db, long noMonster) {
		//do something here
		String[] projection = {
			Monster.MonsterTable._ID,
			Monster.MonsterTable.COLUMN_NAME_SPESIES,
	        Monster.MonsterTable.COLUMN_NAME_ELEMEN_NO,
	        Monster.MonsterTable.COLUMN_NAME_BASE_HP,
	        Monster.MonsterTable.COLUMN_NAME_BASE_ATTACK,
	        Monster.MonsterTable.COLUMN_NAME_BASE_DEFENSE,
			Monster.MonsterTable.COLUMN_NAME_BASE_SPEED,
			Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_1,
			Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_2,
			Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_3,
			Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_4,
			Monster.MonsterTable.COLUMN_NAME_MIN_EVOLUTION_LEVEL,
			Monster.MonsterTable.COLUMN_NAME_NEXT_EVOLUTION_NUM,
			Monster.MonsterTable.COLUMN_NAME_GENTAYANGAN
			};
		
		String where_clause = Monster.MonsterTable._ID + " = " + noMonster;

		Cursor c = db.query(
			Monster.MonsterTable.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			where_clause,               // The columns for the WHERE clause (String)
			null,                       // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);
		
		if(c.moveToFirst()) {
			ID = (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable._ID));
		    Spesies = (String) c.getString(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_SPESIES));
		    Elemen_No = (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_ELEMEN_NO));
		    BaseHP = (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_BASE_HP));
		    BaseAttack = (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_BASE_ATTACK));
		    BaseDefense = (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_BASE_DEFENSE));
		    BaseSpeed = (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_BASE_SPEED));
		    DefaultSkill1 = (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_1));
		    DefaultSkill2 = (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_2));
		    DefaultSkill3 = (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_3));
		    DefaultSkill4 = (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_4));
		    MinEvoLevel = (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_MIN_EVOLUTION_LEVEL));
		    NextEvoNo = (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_NEXT_EVOLUTION_NUM));
			Gentayangan = (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_GENTAYANGAN));
		}
	}
	
	//Setter
	private void setID(int ID) {this.ID = ID;};
	public void setElemen_No(int elemen_No) { Elemen_No = elemen_No; }
	public void setSpesies(String spesies) { Spesies = spesies; }
	public void setBaseHP(int baseHP) { BaseHP = baseHP; }
	public void setBaseAttack(int baseAttack) { BaseAttack = baseAttack; }
	public void setBaseDefense(int baseDefense) { BaseDefense = baseDefense; }
	public void setBaseSpeed(int baseSpeed) { BaseSpeed = baseSpeed; }
	public void setDefaultSkill1(int defaultSkill1) { DefaultSkill1 = defaultSkill1; }
	public void setDefaultSkill2(int defaultSkill2) { DefaultSkill2 = defaultSkill2; }
	public void setDefaultSkill3(int defaultSkill3) { DefaultSkill3 = defaultSkill3; }
	public void setDefaultSkill4(int defaultSkill4) { DefaultSkill4 = defaultSkill4; }
	public void setMinEvoLevel(int minEvoLevel) { MinEvoLevel = minEvoLevel; }
	public void setNextEvoNo(int nextEvoNo) { NextEvoNo = nextEvoNo; }
	public void setGentayangan(int gentayangan) { Gentayangan = gentayangan; }
	
	// GETTER
	public int getID() { return ID; }
	public String getSpesies() { return Spesies; }
	public int getElemenNo() { return Elemen_No; }
	public int getBaseHP() { return BaseHP; }
	public int getBaseAttack() { return BaseAttack; }
	public int getBaseDefense() { return BaseDefense; }
	public int getBaseSpeed() { return BaseSpeed; }
	public int getDefaultSkill1() { return DefaultSkill1; }
	public int getDefaultSkill2() { return DefaultSkill2; }
	public int getDefaultSkill3() { return DefaultSkill3; }
	public int getDefaultSkill4() { return DefaultSkill4; }
	public int getMinEvoLevel() { return MinEvoLevel; }
	public int getNextEvoNo() { return NextEvoNo; }
	public int getGentayangan() { return Gentayangan; }
	
	public abstract class MonsterTable implements BaseColumns {
		public static final String TABLE_NAME = "monster";
	    public static final String COLUMN_NAME_SPESIES = "spesies";
	    public static final String COLUMN_NAME_ELEMEN_NO = "elemen_no";
	    public static final String COLUMN_NAME_BASE_HP = "baseHP";
	    public static final String COLUMN_NAME_BASE_ATTACK = "baseAttack";
	    public static final String COLUMN_NAME_BASE_DEFENSE = "baseDefense";
	    public static final String COLUMN_NAME_BASE_SPEED = "baseSpeed";
	    public static final String COLUMN_NAME_DEFAULT_SKILL_1 = "defaultSkill1";
	    public static final String COLUMN_NAME_DEFAULT_SKILL_2 = "defaultSkill2";
	    public static final String COLUMN_NAME_DEFAULT_SKILL_3 = "defaultSkill3";
	    public static final String COLUMN_NAME_DEFAULT_SKILL_4 = "defaultSkill4";
	    public static final String COLUMN_NAME_MIN_EVOLUTION_LEVEL = "minEvoLevel";
	    public static final String COLUMN_NAME_NEXT_EVOLUTION_NUM = "nextEvoNo";
		public static final String COLUMN_NAME_GENTAYANGAN = "gentayangan";
	}
	
	public abstract class MonsterCD {
	    private static final String TEXT_TYPE = " VARCHAR(30)";
	    private static final String INTEGER_TYPE = " INTEGER";
	    private static final String NOT_NULL = " NOT NULL";
	    private static final String COMMA_SEP = ", ";
	    public static final String SQL_CREATE_ENTRIES =
	        "CREATE TABLE " + Monster.MonsterTable.TABLE_NAME + " (" +
	        Monster.MonsterTable._ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
	        Monster.MonsterTable.COLUMN_NAME_SPESIES + TEXT_TYPE + NOT_NULL + COMMA_SEP +
	        Monster.MonsterTable.COLUMN_NAME_ELEMEN_NO + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
	        Monster.MonsterTable.COLUMN_NAME_BASE_HP + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
	        Monster.MonsterTable.COLUMN_NAME_BASE_ATTACK + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
	        Monster.MonsterTable.COLUMN_NAME_BASE_DEFENSE + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
	        Monster.MonsterTable.COLUMN_NAME_BASE_SPEED + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
	        Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_1 + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
	        Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_2 + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
	        Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_3 + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
	        Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_4 + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
	        Monster.MonsterTable.COLUMN_NAME_MIN_EVOLUTION_LEVEL + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
	        Monster.MonsterTable.COLUMN_NAME_NEXT_EVOLUTION_NUM + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
	        Monster.MonsterTable.COLUMN_NAME_GENTAYANGAN + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
	        "PRIMARY KEY (" + Monster.MonsterTable._ID + ")" + COMMA_SEP +
	        "FOREIGN KEY (" + Monster.MonsterTable.COLUMN_NAME_ELEMEN_NO + ") REFERENCES " +
	        	Element.ElementTable.TABLE_NAME + " (" + Element.ElementTable._ID + ")" + COMMA_SEP +
	        "FOREIGN KEY (" + Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_1 + ") REFERENCES " +
	        	Skill.SkillTable.TABLE_NAME + " (" + Skill.SkillTable._ID + ")" + COMMA_SEP +
	        "FOREIGN KEY (" + Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_2 + ") REFERENCES " +
	        	Skill.SkillTable.TABLE_NAME + " (" + Skill.SkillTable._ID + ")" + COMMA_SEP +
	        "FOREIGN KEY (" + Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_3 + ") REFERENCES " +
	        	Skill.SkillTable.TABLE_NAME + " (" + Skill.SkillTable._ID + ")" + COMMA_SEP +
	        "FOREIGN KEY (" + Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_4 + ") REFERENCES " +
	        	Skill.SkillTable.TABLE_NAME + " (" + Skill.SkillTable._ID + ")" + 
	        ")"; // 14

	    public static final String SQL_DELETE_ENTRIES =
	        "DROP TABLE IF EXISTS " + Monster.MonsterTable.TABLE_NAME;
	    
	    public static final String SQL_DATA_ENTRIES =
	    	"INSERT INTO " + Monster.MonsterTable.TABLE_NAME +
	    	" SELECT 0 AS " + Monster.MonsterTable._ID + COMMA_SEP +
	    		"'NOT MONSTER' AS " + Monster.MonsterTable.COLUMN_NAME_SPESIES + COMMA_SEP +
	    		"0 AS " + Monster.MonsterTable.COLUMN_NAME_ELEMEN_NO + COMMA_SEP +
	    		"0 AS " + Monster.MonsterTable.COLUMN_NAME_BASE_HP + COMMA_SEP +
	    		"0 AS " + Monster.MonsterTable.COLUMN_NAME_BASE_ATTACK + COMMA_SEP +
	    		"0 AS " + Monster.MonsterTable.COLUMN_NAME_BASE_DEFENSE + COMMA_SEP +
	    		"0 AS " + Monster.MonsterTable.COLUMN_NAME_BASE_SPEED + COMMA_SEP +
	    		"0 AS " + Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_1 + COMMA_SEP +
	    		"0 AS " + Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_2 + COMMA_SEP +
	    		"0 AS " + Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_3 + COMMA_SEP +
	    		"0 AS " + Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_4 + COMMA_SEP +
	    		"0 AS " + Monster.MonsterTable.COLUMN_NAME_MIN_EVOLUTION_LEVEL + COMMA_SEP +
	    		"0 AS " + Monster.MonsterTable.COLUMN_NAME_NEXT_EVOLUTION_NUM + COMMA_SEP +
				"0 AS " + Monster.MonsterTable.COLUMN_NAME_GENTAYANGAN +
	   		" UNION SELECT 1  , 'Turtwig'   , 5, 55, 68, 64, 31, 52, 0, 3, 0, 18, 1, 1" +
			" UNION SELECT 2  , 'Grotle'    , 5, 75, 89, 85, 36, 0, 43, 0, 3, 32, 2, 0" +
			" UNION SELECT 3  , 'Torterra'  , 5, 95, 109, 105, 56, 52, 0, 5, 0, 0, 0, 0" +
			" UNION SELECT 4  , 'Piplup'    , 4, 53, 51, 53, 40, 0, 55, 0, 55, 16, 7, 0" +
			" UNION SELECT 5  , 'Prinplup'  , 4, 64, 66, 68, 50, 0, 56, 0, 55, 36, 8, 0" +
			" UNION SELECT 6  , 'Empoleon'  , 4, 84, 86, 88, 60, 52, 0, 12, 0, 0, 0, 1" +
			" UNION SELECT 7  , 'Starly'    , 7, 40, 55, 30, 60, 52, 0, 53, 0, 14, 10, 1" +
			" UNION SELECT 8  , 'Staravia'  , 7, 55, 75, 50, 80, 52, 0, 32, 0, 34, 11, 1" +
			" UNION SELECT 9  , 'Staraptor' , 7, 85, 120, 70, 100, 52, 0, 7, 0, 0, 0, 0" +
			" UNION SELECT 10 , 'Bidoof'    , 3, 59, 45, 40, 31, 52, 0, 33, 0, 15, 13, 0" +
			" UNION SELECT 11 , 'Bibarel'   , 3, 79, 85, 60, 71, 52, 0, 11, 0, 0, 0, 0" +
			" UNION SELECT 12 , 'Shinx'     , 9, 45, 65, 34, 45, 0, 36, 0, 17, 15, 15, 1" +
			" UNION SELECT 13 , 'Luxio'     , 9, 60, 85, 49, 60, 0, 16, 0, 17, 30, 16, 0" +
			" UNION SELECT 14 , 'Luxray'    , 9, 80, 120, 79, 70, 52, 0, 34, 0, 0, 0, 0" +
			" UNION SELECT 15 , 'Abra'      , 1, 25, 20, 15, 90, 0, 4, 0, 15, 16, 17, 1" +
			" UNION SELECT 16 , 'Kadabra'   , 1, 40, 35, 30, 105, 52, 0, 4, 0, 67, 19, 0" +
			" UNION SELECT 17 , 'Alakazam'  , 1, 55, 50, 45, 120, 52, 0, 15, 0, 0, 0, 0" +
			" UNION SELECT 18 , 'Magikarp'  , 4, 20, 10, 55, 80, 52, 0, 51, 0, 20, 21, 0" +
			" UNION SELECT 19 , 'Gyarados'  , 4, 95, 125, 79, 81, 52, 0, 49, 0, 0, 0, 1" +
			" UNION SELECT 20 , 'Budew'     , 5, 40, 30, 35, 55, 52, 0, 28, 0, 15, 23, 0" +
			" UNION SELECT 21 , 'Roselia'   , 5, 50, 60, 45, 65, 52, 0, 43, 0, 30, 24, 1" +
			" UNION SELECT 22 , 'Roserade'  , 5, 60, 70, 55, 90, 0, 5, 0, 43, 0, 0, 1" +
			" UNION SELECT 23 , 'Zubat'     , 7, 40, 45, 35, 55, 52, 0, 32, 0, 22, 26, 0" +
			" UNION SELECT 24 , 'Golbat'    , 7, 75, 80, 70, 90, 52, 0, 32, 0, 44, 27, 1" +
			" UNION SELECT 25 , 'Crobat'    , 7, 85, 90, 80, 130, 52, 0, 24, 0, 0, 0, 0" +
			" UNION SELECT 26 , 'Geodude'   , 6, 40, 80, 100, 20, 52, 0, 19, 0, 25, 29, 0" +
			" UNION SELECT 27 , 'Graveler'  , 6, 55, 95, 115, 35, 0, 19, 0, 22, 33, 30, 1" +
			" UNION SELECT 28 , 'Golem'     , 6, 80, 110, 130, 45, 52, 0, 22, 0, 40, 31, 1" +
			" UNION SELECT 29 , 'Onix'      , 6, 35, 45, 160, 70, 52, 0, 39, 0, 0, 0, 1" +
			" UNION SELECT 30 , 'Machop'    , 2, 70, 80, 50, 35, 52, 0, 27, 0, 28, 33, 0" +
			" UNION SELECT 31 , 'Machoke'   , 2, 80, 100, 70, 45, 0, 9, 0, 27, 44, 34, 1" +
			" UNION SELECT 32 , 'Machamp'   , 2, 90, 130, 80, 55, 0, 9, 0, 29, 0, 0, 0" +
			" UNION SELECT 33 , 'Psyduck'   , 4, 50, 52, 48, 55, 0, 47, 0, 55, 33, 36, 0" +
			" UNION SELECT 34 , 'Golduck'   , 4, 80, 82, 78, 85, 0, 49, 0, 51, 0, 0, 0" +
			" UNION SELECT 35 , 'Buizel'    , 4, 55, 65, 35, 85, 0, 48, 0, 56, 26, 38, 0" +
			" UNION SELECT 36 , 'Floatzel'  , 4, 85, 105, 55, 115, 0, 51, 0, 56, 0, 0, 1" +
			" UNION SELECT 37 , 'Cherubi'   , 5, 45, 35, 45, 35, 0, 3, 0, 43, 25, 40, 1" +
			" UNION SELECT 38 , 'Cherrim'   , 5, 70, 60, 70, 85, 0, 43, 0, 28, 0, 0, 0" +
			" UNION SELECT 39 , 'Shellos'   , 4, 76, 48, 48, 34, 0, 56, 0, 51, 30, 42, 1" +
			" UNION SELECT 40 , 'Gastrodon' , 4, 111, 83, 68, 39, 52, 0, 56, 0, 0, 0, 0" +
			" UNION SELECT 41 , 'Heracross' , 2, 80, 125, 75, 85, 52, 0, 37, 0, 0, 0, 1" +
			" UNION SELECT 42 , 'Aipom'     , 3, 55, 70, 55, 85, 0, 11, 0, 14, 25, 45, 0" +
			" UNION SELECT 43 , 'Ambipom'   , 3, 75, 100, 66, 115, 0, 11, 0, 29, 0, 0, 0" +
			" UNION SELECT 44 , 'Buneary'   , 3, 55, 66, 44, 85, 0, 12, 0, 29, 34, 49, 0" +
			" UNION SELECT 45 , 'Lopunny'   , 3, 65, 76, 84, 105, 52, 0, 14, 0, 0, 0, 0" +
			" UNION SELECT 46 , 'Glameow'   , 3, 49, 55, 42, 85, 0, 14, 0, 29, 38, 51, 1" +
			" UNION SELECT 47 , 'Purugly'   , 3, 71, 82, 64, 112, 0, 12, 0, 31, 0, 0, 1" +
			" UNION SELECT 48 , 'Goldeen'   , 4, 45, 67, 60, 63, 52, 0, 51, 0, 0, 53, 0" +
			" UNION SELECT 49 , 'Seaking'   , 4, 80, 92, 65, 68, 0, 49, 0, 57, 0, 0, 0" +
			" UNION SELECT 50 , 'Barboach'  , 4, 50, 48, 43, 60, 52, 0, 51, 0, 0, 55, 1" +
			" UNION SELECT 51 , 'Whiscash'  , 4, 110, 78, 73, 60, 52, 0, 55, 0, 0, 0, 1" +
			" UNION SELECT 52 , 'Chingling' , 1, 45, 30, 50, 45, 52, 0, 15, 0, 0, 57, 0" +
			" UNION SELECT 53 , 'Chimecho'  , 1, 65, 50, 70, 65, 0, 4, 0, 15, 0, 0, 0" +
			" UNION SELECT 54 , 'Meditite'  , 2, 30, 40, 55, 60, 52, 0, 26, 0, 0, 59, 0" +
			" UNION SELECT 55 , 'Medicham'  , 2, 60, 60, 75, 80, 52, 0, 45, 0, 0, 0, 0" +
			" UNION SELECT 56 , 'Bronzor'   , 1, 57, 24, 86, 23, 52, 0, 4, 0, 0, 61, 1" +
			" UNION SELECT 57 , 'Bronzong'  , 1, 67, 89, 116, 33, 0, 20, 0, 4, 0, 0, 0" +
			" UNION SELECT 58 , 'MimeJr'    , 1, 20, 25, 45, 60, 0, 25, 0, 6, 0, 65, 1" +
			" UNION SELECT 59 , 'MrMime'    , 1, 40, 45, 65, 90, 52, 0, 15, 0, 0, 0, 1" +
			" UNION SELECT 60 , 'Happiny'   , 3, 100, 5, 5, 30, 52, 0, 14, 0, 0, 67, 0" +
			" UNION SELECT 61 , 'Chansey'   , 3, 250, 5, 5, 50, 0, 14, 0, 31, 0, 68, 0" +
			" UNION SELECT 62 , 'Blissey'   , 3, 255, 10, 10, 55, 52, 0, 31, 0, 0, 0, 0" +
			" UNION SELECT 63 , 'Cleffa'    , 3, 50, 25, 28, 15, 52, 0, 29, 0, 0, 70, 1" +
			" UNION SELECT 64 , 'Clefairy'  , 3, 70, 45, 48, 35, 52, 0, 29, 0, 0, 71, 0" +
			" UNION SELECT 65 , 'Clefable'  , 3, 95, 70, 73, 60, 52, 0, 34, 0, 0, 0, 0" +
			" UNION SELECT 66 , 'Pikachu'   , 9, 35, 55, 30, 90, 0, 16, 0, 34, 0, 73, 0" +
			" UNION SELECT 67 , 'Raichu'    , 9, 60, 90, 55, 100, 52, 0, 34, 0, 0, 0, 0" +
			" UNION SELECT 68 , 'Hoothoot'  , 3, 60, 30, 30, 50, 0, 29, 0, 35, 0, 75, 0" +
			" UNION SELECT 69 , 'Noctowl'   , 3, 100, 50, 50, 70, 52, 0, 31, 0, 0, 0, 1" +
			" UNION SELECT 70 , 'Gible'     ,10, 58, 70, 45, 42, 41, 0, 38, 0, 0, 77, 1" +
			" UNION SELECT 71 , 'Gabite'    ,10, 68, 90, 65, 82, 41, 0, 38, 0, 0, 78, 1" +
			" UNION SELECT 72 , 'Garchomp'  ,10, 108, 130, 95, 102, 41, 0, 38, 0, 0, 0, 1" +
			" UNION SELECT 73 , 'Munchlax'  , 3, 135, 85, 40, 5, 52, 0, 34, 0, 0, 80, 1" +
			" UNION SELECT 74 , 'Snorlax'   , 3, 160, 110, 65, 30, 52, 0, 35, 0, 0, 0, 0" +
			" UNION SELECT 75 , 'Wooper'    , 4, 55, 45, 45, 15, 52, 0, 51, 0, 0, 82, 0" +
			" UNION SELECT 76 , 'Quagsire'  , 4, 95, 85, 85, 35, 0, 49, 0, 55, 0, 0, 0" +
			" UNION SELECT 77 , 'Hippopotas', 6, 68, 72, 78, 32, 0, 18, 0, 39, 0, 84, 0" +
			" UNION SELECT 78 , 'Hippowdon' , 6, 108, 112, 118, 47, 52, 0, 22, 0, 0, 0, 0" +
			" UNION SELECT 79 , 'Azurill'   , 3, 50, 20, 40, 20, 0, 37, 0, 38, 0, 86, 1" +
			" UNION SELECT 80 , 'Marill'    , 4, 70, 20, 50, 40, 0, 48, 0, 51, 0, 87, 1" +
			" UNION SELECT 81 , 'Azumarill' , 4, 100, 50, 80, 50, 0, 56, 0, 57, 0, 0, 1" +
			" UNION SELECT 82 , 'Croagunk'  , 3, 48, 61, 40, 50, 0, 54, 0, 42, 0, 89, 1" +
			" UNION SELECT 83 , 'Toxicroak' , 3, 83, 106, 65, 85, 0, 54, 0, 42, 0, 0, 1" +
			" UNION SELECT 84 , 'Carnivine' , 5, 74, 100, 72, 46, 52, 0, 5, 0, 0, 0, 1" +
			" UNION SELECT 85 , 'Tentacool' , 4, 40, 40, 35, 70, 0, 55, 0, 57, 0, 92, 0" +
			" UNION SELECT 86 , 'Tentacruel', 4, 80, 70, 65, 100, 52, 0, 55, 0, 0, 0, 0" +
			" UNION SELECT 87 , 'Snover'    , 8, 60, 62, 50, 40, 0, 2, 0, 40, 0, 94, 0" +
			" UNION SELECT 88 , 'Abomasnow' , 8, 90, 92, 75, 60, 0, 50, 0, 13, 0, 0, 1" +
			" UNION SELECT 89 , 'Dialga'    ,10, 100, 120, 120, 90, 0, 11, 0, 2, 0, 0, 1" +
			" UNION SELECT 90 , 'Palkia'    , 4, 90, 120, 100, 100, 0, 57, 0, 56, 0, 0, 0" +
			" UNION SELECT 91 , 'Manaphy'   , 4, 100, 100, 100, 100, 52, 0, 57, 0, 0, 0, 0" +
			" UNION SELECT 92 , 'Bagon'     ,10, 45, 75, 60, 50, 41, 0, 38, 0, 0, 92, 1" +
			" UNION SELECT 93 , 'Shelgon'   ,10, 65, 95, 100, 50, 0, 11, 0, 23, 0, 93, 0" +
			" UNION SELECT 94 , 'Salamance' ,10, 95, 135, 80, 100, 0, 11, 0, 41, 0, 0, 0" +
			" UNION SELECT 95 , 'Electrike' , 9, 40, 45, 40, 65, 52, 0, 16, 0, 0, 95, 1" +
			" UNION SELECT 96 , 'Manectric' , 9, 70, 75, 60, 105, 0, 54, 0, 36, 0, 0, 1" +
			" UNION SELECT 97 , 'Sneasel'   , 8, 55, 95, 55, 115, 0, 40, 0, 13, 0, 97, 1" +
			" UNION SELECT 98 , 'Weavile'   , 8, 70, 120, 65, 125, 52, 0, 13, 0, 0, 0, 1" +
			" UNION SELECT 99 , 'Vanillite' , 8, 36, 50, 50, 44, 0, 40, 0, 50, 0, 99, 1" +
			" UNION SELECT 100, 'Vanilllish', 8, 51, 65, 65, 59, 52, 0, 50, 0, 0, 100, 0" +
			" UNION SELECT 101, 'Vanilluxe' , 8, 71, 95, 85, 79, 52, 0, 40, 0, 0, 0, 0";
	}
    
	public static ArrayList<String> getMonsterSpesies(SQLiteDatabase db) {
		
		String[] projection = {
			Monster.MonsterTable.COLUMN_NAME_SPESIES,
		};

		Cursor c = db.query(
			Monster.MonsterTable.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			null,                       // The columns for the WHERE clause (String)
			null,                       // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);
		ArrayList<String> list = new ArrayList<String>();
		if(c.moveToFirst()) {
			while(c.moveToNext()) {
			    list.add(c.getString(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_SPESIES)));
			};
		}
		return list;
	}
	private Monster() {
		
	}
	
	public static Monster getRandomMonsterOuterArea(SQLiteDatabase db, int floor, int day_or_night) {
		ArrayList<Integer> mNumber = new ArrayList<Integer>();
		if(day_or_night == 1) {
			switch(floor) {
				case 10: mNumber.add(89); break;
				case 9 : mNumber.add(99);
				case 8 : mNumber.add(92); mNumber.add(95); mNumber.add(97); break;
				case 7 : mNumber.add(79); mNumber.add(82); 
				case 6 : mNumber.add(70); mNumber.add(73); 
				case 5 : mNumber.add(63); break;
				case 4 : mNumber.add(56); mNumber.add(58);
				case 3 : mNumber.add(46); break;
				case 2 : mNumber.add(37);
				case 1 : mNumber.add(7); mNumber.add(12); break;
			}
		} else {
			switch(floor) {
				case 10: mNumber.add(94); mNumber.add(91); mNumber.add(90); mNumber.add(78); break;
				case 9 : 
				case 8 : mNumber.add(23); break;
				case 7 : mNumber.add(85);
				case 6 : mNumber.add(75); mNumber.add(77); 
				case 5 : mNumber.add(61); mNumber.add(66); mNumber.add(68); break;
				case 4 : mNumber.add(60);
				case 3 : mNumber.add(44); mNumber.add(52); mNumber.add(54); break;
				case 2 : mNumber.add(23); mNumber.add(26); mNumber.add(30);
				case 1 : mNumber.add(10); mNumber.add(20); break;
			}
		}
		
		Log.d(Monster.class.getSimpleName(),"Floor: " + floor + "Size: " + mNumber.size());
		
		// RANDOMING
		int i = mNumber.size() - 1;
		i = (int) (Math.random()*i);
		int N = mNumber.get(i);
		
		String[] projection = {
			Monster.MonsterTable._ID,
			Monster.MonsterTable.COLUMN_NAME_SPESIES,
	        Monster.MonsterTable.COLUMN_NAME_ELEMEN_NO,
	        Monster.MonsterTable.COLUMN_NAME_BASE_HP,
	        Monster.MonsterTable.COLUMN_NAME_BASE_ATTACK,
	        Monster.MonsterTable.COLUMN_NAME_BASE_DEFENSE,
			Monster.MonsterTable.COLUMN_NAME_BASE_SPEED,
			Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_1,
			Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_2,
			Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_3,
			Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_4,
			Monster.MonsterTable.COLUMN_NAME_MIN_EVOLUTION_LEVEL,
			Monster.MonsterTable.COLUMN_NAME_NEXT_EVOLUTION_NUM,
			Monster.MonsterTable.COLUMN_NAME_GENTAYANGAN
			};
		
		String where_column_clause = Monster.MonsterTable.COLUMN_NAME_GENTAYANGAN + " LIKE ? AND " + Monster.MonsterTable._ID + " LIKE ?";
		String[] where_values_clause = {"" + day_or_night, "" + N};

		Cursor c = db.query(
			Monster.MonsterTable.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			where_column_clause,        // The columns for the WHERE clause (String)
			where_values_clause,        // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);
		
		c.moveToFirst();
		
		Monster monster = new Monster();
		monster.setID(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable._ID)));
		monster.setSpesies(c.getString(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_SPESIES)));
		monster.setElemen_No(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_ELEMEN_NO)));

	    monster.setBaseHP(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_BASE_HP)));
	    monster.setBaseAttack(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_BASE_ATTACK)));
	    monster.setBaseDefense(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_BASE_DEFENSE)));
	    monster.setBaseSpeed(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_BASE_SPEED)));
	    monster.setDefaultSkill1(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_1)));
	    monster.setDefaultSkill2(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_2)));
	    monster.setDefaultSkill3(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_3)));
	    monster.setDefaultSkill4(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_4)));
	    monster.setMinEvoLevel(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_MIN_EVOLUTION_LEVEL)));
	    monster.setNextEvoNo(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_NEXT_EVOLUTION_NUM)));
		monster.setGentayangan(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_GENTAYANGAN)));
		
		return monster;
	}
	
	public static Monster getRandomMonsterLaut(SQLiteDatabase db, int floor, int day_or_night) {
		ArrayList<Integer> mNumber = new ArrayList<Integer>();
		mNumber.add(18);
		mNumber.add(33);
		mNumber.add(39);
		mNumber.add(48);
		mNumber.add(49);
		mNumber.add(51);
		mNumber.add(85);
		
		switch(floor) {
			case 10: mNumber.add(94); mNumber.add(91); mNumber.add(90); mNumber.add(78); break;
			case 9 : 
			case 8 : mNumber.add(23); break;
			case 7 : mNumber.add(85);
			case 6 : mNumber.add(75); mNumber.add(77); 
			case 5 : mNumber.add(61); mNumber.add(66); mNumber.add(68); break;
			case 4 : mNumber.add(60);
			case 3 : mNumber.add(44); mNumber.add(52); mNumber.add(54); break;
			case 2 : mNumber.add(23); mNumber.add(26); mNumber.add(30);
			case 1 : mNumber.add(10); mNumber.add(18); mNumber.add(20); break;
		}
		
		Log.d(Monster.class.getSimpleName(),"Floor: " + floor + "Size: " + mNumber.size());
		
		// RANDOMING
		int i = mNumber.size() - 1;
		i = (int) (Math.random()*i);
		int N = mNumber.get(i);
		
		String[] projection = {
			Monster.MonsterTable._ID,
			Monster.MonsterTable.COLUMN_NAME_SPESIES,
	        Monster.MonsterTable.COLUMN_NAME_ELEMEN_NO,
	        Monster.MonsterTable.COLUMN_NAME_BASE_HP,
	        Monster.MonsterTable.COLUMN_NAME_BASE_ATTACK,
	        Monster.MonsterTable.COLUMN_NAME_BASE_DEFENSE,
			Monster.MonsterTable.COLUMN_NAME_BASE_SPEED,
			Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_1,
			Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_2,
			Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_3,
			Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_4,
			Monster.MonsterTable.COLUMN_NAME_MIN_EVOLUTION_LEVEL,
			Monster.MonsterTable.COLUMN_NAME_NEXT_EVOLUTION_NUM,
			Monster.MonsterTable.COLUMN_NAME_GENTAYANGAN
			};
		
		String where_column_clause = Monster.MonsterTable._ID + " LIKE ?";
		String[] where_values_clause = {"" + N};

		Cursor c = db.query(
			Monster.MonsterTable.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			where_column_clause,        // The columns for the WHERE clause (String)
			where_values_clause,        // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);
		
		c.moveToFirst();
		
		Monster monster = new Monster();
		monster.setID(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable._ID)));
		monster.setSpesies(c.getString(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_SPESIES)));
		monster.setElemen_No(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_ELEMEN_NO)));

	    monster.setBaseHP(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_BASE_HP)));
	    monster.setBaseAttack(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_BASE_ATTACK)));
	    monster.setBaseDefense(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_BASE_DEFENSE)));
	    monster.setBaseSpeed(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_BASE_SPEED)));
	    monster.setDefaultSkill1(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_1)));
	    monster.setDefaultSkill2(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_2)));
	    monster.setDefaultSkill3(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_3)));
	    monster.setDefaultSkill4(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_4)));
	    monster.setMinEvoLevel(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_MIN_EVOLUTION_LEVEL)));
	    monster.setNextEvoNo(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_NEXT_EVOLUTION_NUM)));
		monster.setGentayangan(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_GENTAYANGAN)));
		
		return monster;
	}
	
	public static Monster getRandomMonsterStadium(SQLiteDatabase db) {
		ArrayList<Integer> mNumber = new ArrayList<Integer>();
		mNumber.add(9);
		mNumber.add(19);
		mNumber.add(22);
		mNumber.add(72);
		mNumber.add(74);
		mNumber.add(81);
		
		// RANDOMING
		int i = mNumber.size();
		i = (int) (Math.random()*i);
		int N = mNumber.get(i);
		
		String[] projection = {
			Monster.MonsterTable._ID,
			Monster.MonsterTable.COLUMN_NAME_SPESIES,
	        Monster.MonsterTable.COLUMN_NAME_ELEMEN_NO,
	        Monster.MonsterTable.COLUMN_NAME_BASE_HP,
	        Monster.MonsterTable.COLUMN_NAME_BASE_ATTACK,
	        Monster.MonsterTable.COLUMN_NAME_BASE_DEFENSE,
			Monster.MonsterTable.COLUMN_NAME_BASE_SPEED,
			Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_1,
			Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_2,
			Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_3,
			Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_4,
			Monster.MonsterTable.COLUMN_NAME_MIN_EVOLUTION_LEVEL,
			Monster.MonsterTable.COLUMN_NAME_NEXT_EVOLUTION_NUM,
			Monster.MonsterTable.COLUMN_NAME_GENTAYANGAN
			};
		
		String where_column_clause = Monster.MonsterTable._ID + " LIKE ?";
		String[] where_values_clause = {"" + N};

		Cursor c = db.query(
			Monster.MonsterTable.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			where_column_clause,        // The columns for the WHERE clause (String)
			where_values_clause,        // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);
		
		c.moveToFirst();
		
		Monster monster = new Monster();
		monster.setID(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable._ID)));
		monster.setSpesies(c.getString(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_SPESIES)));
		monster.setElemen_No(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_ELEMEN_NO)));

	    monster.setBaseHP(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_BASE_HP)));
	    monster.setBaseAttack(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_BASE_ATTACK)));
	    monster.setBaseDefense(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_BASE_DEFENSE)));
	    monster.setBaseSpeed(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_BASE_SPEED)));
	    monster.setDefaultSkill1(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_1)));
	    monster.setDefaultSkill2(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_2)));
	    monster.setDefaultSkill3(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_3)));
	    monster.setDefaultSkill4(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_4)));
	    monster.setMinEvoLevel(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_MIN_EVOLUTION_LEVEL)));
	    monster.setNextEvoNo(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_NEXT_EVOLUTION_NUM)));
		monster.setGentayangan(c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_GENTAYANGAN)));
		
		return monster;
	}
    public static String GetData(SQLiteDatabase db) {

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
			Monster.MonsterTable._ID,
			Monster.MonsterTable.COLUMN_NAME_SPESIES,
	        Monster.MonsterTable.COLUMN_NAME_ELEMEN_NO,
	        Monster.MonsterTable.COLUMN_NAME_BASE_HP,
	        Monster.MonsterTable.COLUMN_NAME_BASE_ATTACK,
	        Monster.MonsterTable.COLUMN_NAME_BASE_DEFENSE,
			Monster.MonsterTable.COLUMN_NAME_BASE_SPEED,
			Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_1,
			Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_2,
			Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_3,
			Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_4,
			Monster.MonsterTable.COLUMN_NAME_MIN_EVOLUTION_LEVEL,
			Monster.MonsterTable.COLUMN_NAME_NEXT_EVOLUTION_NUM,
			Monster.MonsterTable.COLUMN_NAME_GENTAYANGAN
			};

		Cursor c = db.query(
			Monster.MonsterTable.TABLE_NAME,  // The table to query (String)
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
				message += "\nID:" + (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable._ID));
			    message += "\nSpesies:" + (String) c.getString(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_SPESIES));
			    message += "\nElemen : " + (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_ELEMEN_NO));
			    message += "\nBase HP: " + (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_BASE_HP));
			    message += "\nBase Attack: " + (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_BASE_ATTACK));
			    message += "\nBase Defense: " + (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_BASE_DEFENSE));
			    message += "\nBase Speed: " + (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_BASE_SPEED));
			    message += "\nDefault Skill 1: " + (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_1));
			    message += "\nDefault Skill 2: " + (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_2));
			    message += "\nDefault Skill 3: " + (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_3));
			    message += "\nDefault Skill 4: " + (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_DEFAULT_SKILL_4));
			    message += "\nMinimum Evolution Level: " + (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_MIN_EVOLUTION_LEVEL));
			    message += "\nNext Evolution Monster No: " + (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_NEXT_EVOLUTION_NUM));
				message += "\nGentayangan: " + (int) c.getInt(c.getColumnIndexOrThrow(Monster.MonsterTable.COLUMN_NAME_GENTAYANGAN));
			    message += "\n";
			} while(c.moveToNext());
		} else {
			message += "Table is empty";
		}
		return message;
	}
}
