/**
 * 
 */
package user.pokeranch.models;

import user.pokeranch.models.type.Element;
import user.pokeranch.models.type.StatusEffect;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Setyo Lego
 *
 */
public class PokeDB extends SQLiteOpenHelper {
	// If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 33;
    public static final String DATABASE_NAME = "PokeRanchPejuangKemerdekaan.db";
    
    public PokeDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    	// CREATE TABLE
    	db.execSQL(Element.ElementCD.SQL_CREATE_ENTRIES);
    	db.execSQL(StatusEffect.StatusEffectCD.SQL_CREATE_ENTRIES);
    	db.execSQL(Skill.SkillCD.SQL_CREATE_ENTRIES);
    	db.execSQL(Monster.MonsterCD.SQL_CREATE_ENTRIES);
        db.execSQL(Item.ItemCD.SQL_CREATE_ENTRIES);
        db.execSQL(Player.PlayerCD.SQL_CREATE_ENTRIES);
        db.execSQL(PlayerMonster.PlayerMonsterCD.SQL_CREATE_ENTRIES);
        db.execSQL(PlayerItem.PlayerItemCD.SQL_CREATE_ENTRIES);
        db.execSQL(PlayerParty.PlayerPartyCD.SQL_CREATE_ENTRIES);
        
        // CREATE BASE DATA
        db.execSQL(Element.ElementCD.SQL_DATA_ENTRIES);
        db.execSQL(StatusEffect.StatusEffectCD.SQL_DATA_ENTRIES);
        db.execSQL(Skill.SkillCD.SQL_DATA_ENTRIES);
        db.execSQL(Monster.MonsterCD.SQL_DATA_ENTRIES);
        db.execSQL(Item.ItemCD.SQL_DATA_ENTRIES);
        db.execSQL(Player.PlayerCD.SQL_DATA_ENTRIES);
        db.execSQL(PlayerItem.PlayerItemCD.SQL_DATA_ENTRIES);
        db.execSQL(PlayerMonster.PlayerMonsterCD.SQL_DATA_ENTRIES);
        db.execSQL(PlayerParty.PlayerPartyCD.SQL_DATA_ENTRIES);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
    	db.execSQL(Element.ElementCD.SQL_DELETE_ENTRIES);
    	db.execSQL(StatusEffect.StatusEffectCD.SQL_DELETE_ENTRIES);
    	db.execSQL(Skill.SkillCD.SQL_DELETE_ENTRIES);
    	db.execSQL(Monster.MonsterCD.SQL_DELETE_ENTRIES);
    	db.execSQL(Item.ItemCD.SQL_DELETE_ENTRIES);
    	db.execSQL(Player.PlayerCD.SQL_DELETE_ENTRIES);
    	db.execSQL(PlayerMonster.PlayerMonsterCD.SQL_DELETE_ENTRIES);
    	db.execSQL(PlayerItem.PlayerItemCD.SQL_DELETE_ENTRIES);
    	
    	onCreate(db);
    }
    @SuppressLint("Override")
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
