/**
 * 
 */
package user.pokeranch.models;

import user.pokeranch.models.type.Element;
import user.pokeranch.models.type.StatusEffect;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * @author Setyo Lego
 *
 */
public class Skill {
	
	private static final String TAG = Skill.class.getSimpleName();
	private int no_skill;
	private String Name;
	private int Target;
	private int Elemen_No;
	private int Power;
	private int Power_Effect_No;
	private int DefaultPP;
	private int MaxPP;
	private int Accuracy;
	private int Status_Effect;
	private int Prob_Stat_Effect;
	private int Skill_Evo_To;
	private int Skill_Evo_At;
	private int Strength;
	private int Weakness;
	private String Description;
	
	// GETTER
	public int getID() { return no_skill; }
	public String getName() { return Name; }
	public int getTarget() { return Target; }
	public int getElemenID() { return Elemen_No; }
	public int getPower() { return Power; }
	public int getPowerEffectID() { return Power_Effect_No; }
	public int getAccuracy() { return Accuracy; }
	public int getStatusEffect() { return Status_Effect; }
	public int getProbStatEffect() { return Prob_Stat_Effect; }
	public int getSkillEvoTo() { return Skill_Evo_To; }
	public int getSkillEvoAt() { return Skill_Evo_At; }
	public int getStrength() { return Strength; }
	public int getWeakness() { return Weakness; }
	public String getDescription() { return Description; }
	public int getDefaultPP() { return DefaultPP; }
	public int getMaxPP() { return MaxPP; }
	
	// SETTER
	public void setName(String _name) {Name = _name; }
	public void setTarget(int _target) {Target = _target; }
	public void setElemenID(int _elemen_no) {Elemen_No = _elemen_no; }
	public void setPower(int _power) {Power = _power; }
	public void setPowerEffectID(int _power_effect_no) {Power_Effect_No = _power_effect_no; }
	public void setAccuracy(int _accuracy) {Accuracy = _accuracy; }
	public void setStatusEffect(int _status_effect) {Status_Effect = _status_effect; }
	public void setProbStatEffect(int _prob_stat_effect) {Prob_Stat_Effect = _prob_stat_effect; }
	public void setSkillEvoTo(int _skill_evo_to) {Skill_Evo_To = _skill_evo_to; }
	public void setSkillEvoAt(int _skill_evo_at) {Skill_Evo_At = _skill_evo_at; }
	public void setStrength(int _strength) {Strength = _strength; }
	public void setWeakness(int _weakness) {Weakness = _weakness; }
	public void setDescription(String _description) {Description = _description; }
	public void setDefaultPP(int defaultPP) { DefaultPP = defaultPP; }
	public void setMaxPP(int maxPP) { MaxPP = maxPP; }
	
	public abstract class SkillTable implements BaseColumns {
		public static final String TABLE_NAME = "item";
		public static final String COLUMN_NAME_NAME = "name";
		public static final String COLUMN_NAME_TARGET = "target";
		public static final String COLUMN_NAME_ELEMEN_NO = "elemen_no";
		public static final String COLUMN_NAME_POWER = "power";
		public static final String COLUMN_NAME_POWER_EFFECT_NO = "power_effect_no";
		public static final String COLUMN_NAME_ACCURACY = "accuracy";
		public static final String COLUMN_NAME_DEF_PP = "default_pp";
		public static final String COLUMN_NAME_MAX_PP = "max_pp";
		public static final String COLUMN_NAME_STATUS_EFFECT = "status_effect";
		public static final String COLUMN_NAME_PROB_STAT_EFFECT = "prob_stat_effect";
		public static final String COLUMN_NAME_SKILL_EVO_TO = "evo_to";
		public static final String COLUMN_NAME_SKILL_EVO_AT = "evo_at";
		public static final String COLUMN_NAME_STRENGTH = "strength";
		public static final String COLUMN_NAME_WEAKNESS = "weakness"; // 14
		public static final String COLUMN_NAME_DESCRIPTION = "description";
	}
	
	public abstract class SkillCD {
		private static final String TEXT_TYPE = " VARCHAR(20)";
		private static final String INTEGER_TYPE = " INTEGER";
		private static final String NOT_NULL = " NOT NULL";
		private static final String COMMA_SEP = ", ";
		public static final String SQL_CREATE_ENTRIES =
			"CREATE TABLE " + Skill.SkillTable.TABLE_NAME + " (" +
			Skill.SkillTable._ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			Skill.SkillTable.COLUMN_NAME_NAME + TEXT_TYPE + NOT_NULL + COMMA_SEP +
			Skill.SkillTable.COLUMN_NAME_TARGET + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			Skill.SkillTable.COLUMN_NAME_ELEMEN_NO + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			Skill.SkillTable.COLUMN_NAME_POWER + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			Skill.SkillTable.COLUMN_NAME_POWER_EFFECT_NO + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			Skill.SkillTable.COLUMN_NAME_ACCURACY + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			Skill.SkillTable.COLUMN_NAME_DEF_PP + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			Skill.SkillTable.COLUMN_NAME_MAX_PP + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			Skill.SkillTable.COLUMN_NAME_STATUS_EFFECT + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			Skill.SkillTable.COLUMN_NAME_PROB_STAT_EFFECT + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			Skill.SkillTable.COLUMN_NAME_SKILL_EVO_TO + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			Skill.SkillTable.COLUMN_NAME_SKILL_EVO_AT + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			Skill.SkillTable.COLUMN_NAME_STRENGTH + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			Skill.SkillTable.COLUMN_NAME_WEAKNESS + INTEGER_TYPE + NOT_NULL + COMMA_SEP + //15
			Skill.SkillTable.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + NOT_NULL + COMMA_SEP +
			"PRIMARY KEY (" + Skill.SkillTable._ID + ")" + COMMA_SEP +
			"FOREIGN KEY (" + Skill.SkillTable.COLUMN_NAME_ELEMEN_NO + ") REFERENCES " +
				Element.ElementTable.TABLE_NAME + " (" + Element.ElementTable._ID + ")" + COMMA_SEP +
			"FOREIGN KEY (" + Skill.SkillTable.COLUMN_NAME_STATUS_EFFECT + ") REFERENCES " +
				StatusEffect.StatusEffectTable.TABLE_NAME + " (" + 
				StatusEffect.StatusEffectTable._ID + ")" +
			")";
		public static final String SQL_DELETE_ENTRIES =
		    "DROP TABLE IF EXISTS " + Monster.MonsterTable.TABLE_NAME;
		
		public static final String SQL_DATA_ENTRIES =
			"INSERT INTO " + Skill.SkillTable.TABLE_NAME +
			" SELECT 0 AS " + Skill.SkillTable._ID + 
				", 'NOT SKILL' AS " + Skill.SkillTable.COLUMN_NAME_NAME + 
				", 0 AS " + Skill.SkillTable.COLUMN_NAME_TARGET + 
				", 0 AS " + Skill.SkillTable.COLUMN_NAME_ELEMEN_NO + 
				", 0 AS " + Skill.SkillTable.COLUMN_NAME_POWER + 
				", 0 AS " + Skill.SkillTable.COLUMN_NAME_POWER_EFFECT_NO + 
				", 0 AS " + Skill.SkillTable.COLUMN_NAME_ACCURACY + 
				", 0 AS " + Skill.SkillTable.COLUMN_NAME_DEF_PP + 
				", 0 AS " + Skill.SkillTable.COLUMN_NAME_MAX_PP + 
				", 0 AS " + Skill.SkillTable.COLUMN_NAME_STATUS_EFFECT + 
				", 0 AS " + Skill.SkillTable.COLUMN_NAME_PROB_STAT_EFFECT + 
				", 0 AS " + Skill.SkillTable.COLUMN_NAME_SKILL_EVO_TO +
				", 0 AS " + Skill.SkillTable.COLUMN_NAME_SKILL_EVO_AT +
				", 0 AS " + Skill.SkillTable.COLUMN_NAME_STRENGTH +
				", 0 AS " + Skill.SkillTable.COLUMN_NAME_WEAKNESS +
				", 'NO DESCRIPTION' AS " + Skill.SkillTable.COLUMN_NAME_DESCRIPTION +
			" UNION SELECT  1, 'DragonClaw'  , 1, 10,  80, 0, 100, 15, 30, 0,   0,   0,   0, 10,  0, 'The user slashes the target with huge, sharp claws.'" +
			" UNION SELECT  2, 'FreezeShock' , 1,  8, 140, 0,  90,  5, 20, 5,  70,   0,   0,  9,  7, 'On the second turn, the user hits the target with electrically charged ice. It may leave the target with paralysis.'" +
			" UNION SELECT  3, 'LeafStorm'   , 1,  5, 140, 0,  90,  5, 20, 0,   0,   0,   0,  8,  9, 'The user whips up a storm of leaves around the target. The attacks recoil harshly reduces the users Sp. Atk stat.'" +
			" UNION SELECT  4, 'Psyshock'    , 1,  1,  80, 0, 100, 10, 25, 0,   0,  20,  20,  2,  3, 'The user materializes an odd psychic wave to attack the target. This attack does physical damage.'" +
			" UNION SELECT  5, 'LeafBlade'   , 1,  5,  90, 0, 100, 15, 30, 0,   0,   3,  20,  8,  9, 'The user handles a sharp leaf like a sword and attacks by cutting its target. Critical hits land more easily.'" +
			" UNION SELECT  6, 'CalmMind'    , 0,  1,   1, 2, 100, 20, 35, 0,   0,   0,   0,  2,  3, 'The user quietly focuses its mind and calms its spirit to raise its Sp. Atk and Sp. Def stats.'" +
			" UNION SELECT  7, 'AirCutter'   , 1,  7,  40, 0, 100, 25, 40, 0,   0,  10,  20,  7,  8, 'The user launches razor-like wind to slash the opposing team. Critical hits land more easily.'" +
			" UNION SELECT  8, 'Toxic'       , 1,  3,   0, 0,  90, 10, 25, 3,  90,   0,   0,  1,  2, 'A move that leaves the target badly poisoned. Its poison damage worsens every turn.'" +
			" UNION SELECT  9, 'BulkUp'      , 0,  2,   1, 1, 100, 20, 35, 0,   0,   0,   0,  3,  1, 'The user tenses its muscles to bulk up its body, boosting both its Attack and Defense stats.'" +
			" UNION SELECT 10, 'AirSlash'    , 1,  7,  75, 0,  95, 20, 35, 0,   0,   0,   0,  7,  8, 'The user attacks with a blade of air that slices even the sky. It may also make the target flinch.'" +
			" UNION SELECT 11, 'Venoshock'   , 1,  3,  65, 0, 100, 10, 25, 2,  50,   0,   0,  1,  2, 'The user drenches the target in a special poisonous liquid. Its power is doubled if the target is poisoned.'" +
			" UNION SELECT 12, 'HiddenPower' , 1,  3,  60, 0, 100, 15, 30, 0,   0,   0,   0,  1,  2, 'A unique attack that varies in type and intensity depending on the Pokémon using it.'" +
			" UNION SELECT 13, 'Blizzard'    , 1,  8, 120, 0,  70,  5, 20, 5,  60,   0,   0,  9,  7, 'A howling blizzard is summoned to strike the opposing team. It may also freeze them solid.'" +
			" UNION SELECT 14, 'HyperBeam'   , 1,  3, 150, 0,  70,  5, 20, 0,   0,   0,   0,  1,  2, 'The target is attacked with a powerful beam. The user must rest on the next turn to regain its energy.'" +
			" UNION SELECT 15, 'LightScreen' , 0,  1,   1, 2, 100, 30, 45, 0,   0,   0,   0,  2,  3, 'A wondrous wall of light is put up to suppress damage from special attacks for five turns.'" +
			" UNION SELECT 16, 'Thunderbolt' , 1,  9,  95, 0, 100, 15, 30, 1,  50,  17,  20,  4,  5, 'A strong electric blast is loosed at the target. It may also leave the target with paralysis.'" +
			" UNION SELECT 17, 'Thunder'     , 1,  9, 120, 0,  70, 10, 25, 1,  60,   0,   0,  4,  5, 'A wicked thunderbolt is dropped on the target to inflict damage. It may also leave the target with paralysis.'" +
			" UNION SELECT 18, 'Earthqueke'  , 1,  6, 100, 0, 100, 10, 25, 0,   0,   0,   0,  6,  4, 'The user sets off an earthquake that strikes those around it.'" +
			" UNION SELECT 19, 'Dig'         , 1,  6,  80, 0, 100, 10, 25, 0,   0,   0,   0,  6,  4, 'The user burrows, then attacks on the second turn. It can also be used to exit dungeons.'" +
			" UNION SELECT 20, 'Psychic'     , 1,  1,  90, 0, 100, 10, 25, 6,  50,   0,   0,  2,  3, 'The target is hit by a strong telekinetic force. It may also reduce the targets Sp.Def stat.'" +
			" UNION SELECT 21, 'Flamethrower', 1, 10,  95, 0, 100, 15, 30, 4,  65,  23,  20, 10,  0, 'The target is scorched with an intense blast of fire. It may also leave the target with a burn.'" +
			" UNION SELECT 22, 'Sandtorm'    , 1,  6,   1, 4, 100, 10, 25, 0,   0,   0,   0,  6,  4, 'A five-turn sandstorm is summoned to hurt all combatants except the Rock, Ground, and Steel types.'" +
			" UNION SELECT 23, 'FireBlast'   , 1, 10, 120, 0,  85,  5, 20, 4,  70,   0,   0, 10,  0, 'The target is attacked with an intense blast of all-consuming fire. It may also leave the target with a burn.'" +
			" UNION SELECT 24, 'AerialAce'   , 1,  7,  60, 0, 120, 20, 35, 0,   0,   0,   0,  7,  8, 'The user confounds the target with speed, then slashes. The attack lands without fail.'" +
			" UNION SELECT 25, 'Rest'        , 0,  1, 300, 0,  80, 10, 25, 0,   0,   0,   0,  2,  3, 'The user goes to sleep for two turns. It fully restores the users HP and heals any status problem.'" +
			" UNION SELECT 26, 'LowSweep'    , 1,  2,  60, 0, 100, 20, 35, 1,  60,   0,   0,  3,  1, 'The user attacks the targets legs swiftly, reducing the targets Speed stat.'" +
			" UNION SELECT 27, 'FocusBlast'  , 1,  2, 120, 0,  70,  5, 20, 0,   0,   0,   0,  3,  1, 'The user heightens its mental focus and unleashes its power. It may also lower the targets Sp. Def.'" +
			" UNION SELECT 28, 'EnergyBall'  , 1,  5,  80, 0, 100, 10, 25, 0,   0,   0,   0,  8,  9, 'The user draws power from nature and fires it at the target. It may also lower the targets Sp. Def.'" +
			" UNION SELECT 29, 'FalseSwipe'  , 1,  3,  40, 0, 100, 40, 55, 1,  50,   0,   0,  1,  2, 'A restrained attack that prevents the target from fainting. The target is left with at least 1 HP.'" +
			" UNION SELECT 30, 'Scald'       , 1,  4,  80, 0, 100, 15, 30, 4,  75,   0,   0,  5,  6, 'The user shoots boiling hot water at its target. It may also leave the target with a burn.'" +
			" UNION SELECT 31, 'Will-O-Wisp' , 1,  3,   0, 0,  75, 15, 30, 4,  75,   0,   0,  1,  2, 'The user shoots a sinister, bluish-white flame at the target to inflict a burn.'" +
			" UNION SELECT 32, 'Acrobatics'  , 1,  7,  55, 0, 100, 15, 30, 0,   0,   0,   0,  7,  8, 'The user nimbly strikes the target. If the user is not holding an item, this attack inflicts massive damage.'" +
			" UNION SELECT 33, 'Retaliate'   , 1,  3,  70, 0, 100,  5, 20, 0,   0,  34,  20,  1,  2, 'The user gets revenge for a fainted ally. If an ally fainted in the previous turn, this attacks damage increases.'" +
			" UNION SELECT 34, 'GigaImpact'  , 1,  3, 150, 0,  90,  5, 20, 0,   0,   0,   0,  1,  2, 'The user charges at the target using every bit of its power. The user must rest on the next turn.'" +
			" UNION SELECT 35, 'Flash'       , 1,  3,   1, 4, 100, 20, 35, 0,   0,   0,   0,  1,  2, 'The user flashes a bright light that cuts the targets accuracy. It can also be used to illuminate caves.'" +
			" UNION SELECT 36, 'ThunderWave' , 1,  9,   0, 0, 100, 20, 35, 1, 100,   0,   0,  4,  5, 'A weak electric charge is launched at the target. It causes paralysis if it hits.'" +
			" UNION SELECT 37, 'SwordsDance' , 0,  3,   1, 1, 100, 30, 45, 0,   0,   0,   0,  1,  2, 'A frenetic dance to uplift the fighting spirit. It sharply raises the user’s Attack stat.'" +
			" UNION SELECT 38, 'PsychUp'     , 0,  3,   1, 2, 100, 10, 25, 0,   0,   0,   0,  1,  2, 'The user hypnotizes itself into copying any stat change made by the target.'" +
			" UNION SELECT 39, 'Bulldoze'    , 1,  6,  60, 0, 100, 20, 35, 0,   0,  18,  20,  6,  4, 'The user stomps down on the ground and attacks everything in the area. Hit Pokémons Speed stat is reduced.'" +
			" UNION SELECT 40, 'FrostBreath' , 1,  8,  40, 0,  90, 10, 25, 5,  90,  13,  20,  9,  7, 'The user blows a cold breath on the target. This attack always results in a critical hit.'" +
			" UNION SELECT 41, 'DragonTail'  , 1, 10,  60, 0,  90, 10, 25, 0,   0,   0,   0, 10,  0, 'The target is knocked away and switched. In the wild, a battle against a single Pokémon ends.'" +
			" UNION SELECT 42, 'WorkUp'      , 0,  3,   1, 1, 100, 30, 45, 0,   0,   0,   0,  1,  2, 'The user is roused, and its Attack and Sp. Atk stats increase.'" +
			" UNION SELECT 43, 'GrassKnot'   , 1,  5,  70, 0, 100, 20, 35, 0,   0,   0,   0,  8,  9, 'The user snares the target with grass and trips it. The heavier the target, the greater the damage.'" +
			" UNION SELECT 44, 'Pluck'       , 1,  7,  60, 0, 100, 20, 35, 0,   0,   0,   0,  7,  8, 'The user pecks the target. If the target is holding a Berry, the user eats it and gains its effect.'" +
			" UNION SELECT 45, 'RockSmash'   , 1,  2,  40, 0, 100, 15, 30, 0,   0,  27,  20,  3,  1, 'The user attacks with a punch that can shatter a rock. It may also lower the targets Defense stat.'" +
			" UNION SELECT 46, 'Swim'        , 1,  4,  95, 0, 100, 15, 30, 0,   0,   0,   0,  5,  6, 'It swamps the area around the user with a giant wave. It can also be used for crossing water'" +
			" UNION SELECT 47, 'Waterfall'   , 1,  4,  80, 0, 100, 10, 35, 0,   0,   0,   0,  5,  6, 'The user charges at the target and may make it flinch. It can also be used to climb a waterfall.'" +
			" UNION SELECT 48, 'Dive'        , 1,  4,  80, 0, 100, 10, 35, 0,   0,   0,   0,  5,  6, 'Diving on the first turn, the user floats up and attacks on the second turn. It can be used to dive deep in the ocean.'" +
			" UNION SELECT 49, 'AquaTail'    , 1,  4,  90, 0,  90, 10, 35, 0,   0,   0,   0,  5,  6, 'The user attacks by swinging its tail as if it were a vicious wave in a raging storm.'" +
			" UNION SELECT 50, 'IceBeam'     , 1,  8,  95, 0, 100, 10, 35, 5,  70,   0,   0,  9,  7, 'The target is struck with an icy-cold beam of energy. It may also freeze the target solid.'" +
			" UNION SELECT 51, 'Brine'       , 1,  4,  65, 0, 100, 10, 35, 0,   0,   0,   0,  5,  6, 'If the targets HP is down to about half, this attack will hit with double the power.'" +
			" UNION SELECT 52, 'Tackle'      , 1,  3,  50, 0, 100, 35, 50, 0,   0,   0,   0,  1,  2, 'A physical attack in which the user charges and slams into the target with its whole body.'" +
			" UNION SELECT 53, 'Leer'        , 1,  3,   1, 2, 100, 30, 45, 0,   0,   0,   0,  1,  2, 'The user gains an intimidating leer with sharp eyes. The opposing teams Defense stats are reduced.'" +
			" UNION SELECT 54, 'Slam'        , 1,  3,  80, 0,  75, 20, 35, 0,   0,   0,   0,  1,  2, 'The target is slammed with a long tail, vines, etc., to inflict damage.'" +
			" UNION SELECT 55, 'Bubble'      , 1,  4,  50, 0, 100, 35, 50, 0,   0,  48,  10,  5,  6, 'A spray of countless bubbles is jetted at the opposing team. It may also lower the targets Speed stats.'" +
			" UNION SELECT 56, 'WaterGun'    , 1,  4,  55, 0, 100, 25, 40, 0,   0,   0,   0,  5,  6, 'The target is blasted with a forceful shot of water.'" +
			" UNION SELECT 57, 'WaterPulse'  , 1,  4,  60, 0, 100, 20, 35, 0,   0,   0,   0,  5,  6, 'The user attacks the target with a pulsing blast of water. It may also confuse the target.'" +
			" UNION SELECT 58, 'TailWhip'    , 1,  3,   1, 1, 100, 35, 50, 0,   0,   0,   0,  1,  2, 'The user wags its tail cutely, making opposing Pokémon less wary and lowering their Defense stat.'" +
			" UNION SELECT 59, 'Cut'         , 1,  3,  50, 0,  95, 30, 45, 0,   0,   0,   0,  1,  2, 'The target is cut with a scythe or a claw. It can also be used to cut down thin trees.'" +
			" UNION SELECT 60, 'Push'        , 1,  3,  80, 0, 100, 15, 30, 0,   0,   0,   0,  1,  2, 'The target is slugged with a punch thrown at maximum power. This move can also be used to move boulders.'";
	}
	
	public Skill(int id, SQLiteDatabase db) {
		String[] projection = {
			Skill.SkillTable._ID,
			Skill.SkillTable.COLUMN_NAME_NAME,
			Skill.SkillTable.COLUMN_NAME_TARGET,
			Skill.SkillTable.COLUMN_NAME_ELEMEN_NO,
			Skill.SkillTable.COLUMN_NAME_POWER,
			Skill.SkillTable.COLUMN_NAME_POWER_EFFECT_NO,
			Skill.SkillTable.COLUMN_NAME_ACCURACY,
			Skill.SkillTable.COLUMN_NAME_DEF_PP,
			Skill.SkillTable.COLUMN_NAME_MAX_PP,
			Skill.SkillTable.COLUMN_NAME_STATUS_EFFECT,
			Skill.SkillTable.COLUMN_NAME_PROB_STAT_EFFECT,
			Skill.SkillTable.COLUMN_NAME_SKILL_EVO_TO,
			Skill.SkillTable.COLUMN_NAME_SKILL_EVO_AT,
			Skill.SkillTable.COLUMN_NAME_STRENGTH,
			Skill.SkillTable.COLUMN_NAME_WEAKNESS,
			Skill.SkillTable.COLUMN_NAME_DESCRIPTION
			};
		String where_col = Skill.SkillTable._ID + " LIKE ?";
		String[] where_val = {"" + id};

		Cursor c = db.query(
			Skill.SkillTable.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			where_col,                  // The columns for the WHERE clause (String)
			where_val,                  // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);
		if(c.moveToFirst()) {
			no_skill = (int) c.getInt(c.getColumnIndexOrThrow(Skill.SkillTable._ID));
			Name = (String) c.getString(c.getColumnIndexOrThrow(Skill.SkillTable.COLUMN_NAME_NAME));
		    Target = (int) c.getInt(c.getColumnIndex(Skill.SkillTable.COLUMN_NAME_TARGET));
		    Elemen_No = (int) c.getInt(c.getColumnIndex(Skill.SkillTable.COLUMN_NAME_ELEMEN_NO));
		    Power = (int) c.getInt(c.getColumnIndex(Skill.SkillTable.COLUMN_NAME_POWER));
		    Power_Effect_No = (int) c.getInt(c.getColumnIndex(Skill.SkillTable.COLUMN_NAME_POWER_EFFECT_NO));
		    DefaultPP = (int) c.getInt(c.getColumnIndex(Skill.SkillTable.COLUMN_NAME_DEF_PP));
		    MaxPP = (int) c.getInt(c.getColumnIndex(Skill.SkillTable.COLUMN_NAME_MAX_PP));
		    Accuracy = (int) c.getInt(c.getColumnIndex(Skill.SkillTable.COLUMN_NAME_ACCURACY));
		    Status_Effect = (int) c.getInt(c.getColumnIndex(Skill.SkillTable.COLUMN_NAME_STATUS_EFFECT));
		    Prob_Stat_Effect = (int) c.getInt(c.getColumnIndex(Skill.SkillTable.COLUMN_NAME_PROB_STAT_EFFECT));
		    Skill_Evo_To = (int) c.getInt(c.getColumnIndex(Skill.SkillTable.COLUMN_NAME_SKILL_EVO_TO));
		    Skill_Evo_At = (int) c.getInt(c.getColumnIndex(Skill.SkillTable.COLUMN_NAME_SKILL_EVO_AT));
		    Strength = (int) c.getInt(c.getColumnIndex(Skill.SkillTable.COLUMN_NAME_STRENGTH));
		    Weakness = (int) c.getInt(c.getColumnIndex(Skill.SkillTable.COLUMN_NAME_WEAKNESS));
		    Description = (String) c.getString(c.getColumnIndexOrThrow(Skill.SkillTable.COLUMN_NAME_DESCRIPTION));
		    Log.d(TAG, "Get skill number: " + no_skill);
		} else {
			Log.d(TAG, "Skill number not found");
		}
	}
	
	public static int getDefPPSkill(SQLiteDatabase db, int skill_num) {
		String[] projection = {
			Skill.SkillTable.COLUMN_NAME_DEF_PP
		};
		
		String where_clause = Skill.SkillTable._ID + " = " + skill_num;

		Cursor c = db.query(
			Skill.SkillTable.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			where_clause,               // The columns for the WHERE clause (String)
			null,                       // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);

		if(c.moveToFirst()) {
			return c.getInt(c.getColumnIndexOrThrow(Skill.SkillTable.COLUMN_NAME_DEF_PP));
		} else return 0;
	}
	
	public static String getNameSkill(SQLiteDatabase db, int skill_num) {
		String[] projection = {
			Skill.SkillTable.COLUMN_NAME_NAME
		};
		
		String where_clause = Skill.SkillTable._ID + " = " + skill_num;

		Cursor c = db.query(
			Skill.SkillTable.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			where_clause,               // The columns for the WHERE clause (String)
			null,                       // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);

		if(c.moveToFirst()) {
			return c.getString(c.getColumnIndexOrThrow(Skill.SkillTable.COLUMN_NAME_NAME));
		} else return null;
	}
	
	public static String getData(SQLiteDatabase db) {
		String[] projection = {
			Skill.SkillTable._ID,
			Skill.SkillTable.COLUMN_NAME_NAME,
			Skill.SkillTable.COLUMN_NAME_TARGET,
			Skill.SkillTable.COLUMN_NAME_ELEMEN_NO,
			Skill.SkillTable.COLUMN_NAME_POWER,
			Skill.SkillTable.COLUMN_NAME_POWER_EFFECT_NO,
			Skill.SkillTable.COLUMN_NAME_ACCURACY,
			Skill.SkillTable.COLUMN_NAME_DEF_PP,
			Skill.SkillTable.COLUMN_NAME_MAX_PP,
			Skill.SkillTable.COLUMN_NAME_STATUS_EFFECT,
			Skill.SkillTable.COLUMN_NAME_PROB_STAT_EFFECT,
			Skill.SkillTable.COLUMN_NAME_SKILL_EVO_TO,
			Skill.SkillTable.COLUMN_NAME_SKILL_EVO_AT,
			Skill.SkillTable.COLUMN_NAME_STRENGTH,
			Skill.SkillTable.COLUMN_NAME_WEAKNESS
			};

		Cursor c = db.query(
			Skill.SkillTable.TABLE_NAME,  // The table to query (String)
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
				message += "\nID:" + (int) c.getInt(c.getColumnIndexOrThrow(Skill.SkillTable._ID));
			    message += "\nName:" + (String) c.getString(c.getColumnIndexOrThrow(Skill.SkillTable.COLUMN_NAME_NAME));
			    message += "\nTarget: " + (int) c.getInt(c.getColumnIndex(Skill.SkillTable.COLUMN_NAME_TARGET));
			    message += "\nElement: " + (int) c.getInt(c.getColumnIndex(Skill.SkillTable.COLUMN_NAME_ELEMEN_NO));
			    message += "\nPower: " + (int) c.getInt(c.getColumnIndex(Skill.SkillTable.COLUMN_NAME_POWER));
			    message += "\nPower Effect No: " + (int) c.getInt(c.getColumnIndex(Skill.SkillTable.COLUMN_NAME_POWER_EFFECT_NO));
			    message += "\nAccuracy: " + (int) c.getInt(c.getColumnIndex(Skill.SkillTable.COLUMN_NAME_ACCURACY));
			    message += "\nStatus Effect: " + (int) c.getInt(c.getColumnIndex(Skill.SkillTable.COLUMN_NAME_STATUS_EFFECT));
			    message += "\nProb Status Effect: " + (int) c.getInt(c.getColumnIndex(Skill.SkillTable.COLUMN_NAME_PROB_STAT_EFFECT));
			    message += "\nSkill Evo To: " + (int) c.getInt(c.getColumnIndex(Skill.SkillTable.COLUMN_NAME_SKILL_EVO_TO));
			    message += "\nSkill Evo At Level: " + (int) c.getInt(c.getColumnIndex(Skill.SkillTable.COLUMN_NAME_SKILL_EVO_AT));
			    message += "\nSkill Strength: " + (int) c.getInt(c.getColumnIndex(Skill.SkillTable.COLUMN_NAME_STRENGTH));
			    message += "\nSkill Weakness: " + (int) c.getInt(c.getColumnIndex(Skill.SkillTable.COLUMN_NAME_WEAKNESS));
			    message += "\n";
			} while(c.moveToNext());
		}
		return message;
	}
}
