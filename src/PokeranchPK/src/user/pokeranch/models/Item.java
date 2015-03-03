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
public class Item {

	private int ItemNumb;
	private String Name;
	private int Type;
	private int BuyPrice;
	private int SellPrice;
	private int ItemValue;
	private String Description;
	
	public int GetItemNumb() {return ItemNumb;}
	public String GetName() {return Name;}
	public int GetType() {return Type;}
	public int GetBuyPrice() {return BuyPrice;}
	public int GetSellPrice() {return SellPrice;}
	public int GetItemValue() {return ItemValue;}
	public String GetDescription() {return Description;}
	
	public void SetName(String Name)  {this.Name = Name;}
	public void SetType(int Type) {this.Type = Type;}
	public void SetBuyPrice(int BuyPrice) {this.BuyPrice = BuyPrice;}
	public void SetSellPrice(int SellPrice) {this.SellPrice = SellPrice;}
	public void SetItemValue(int ItemValue) {this.ItemValue = ItemValue;}
	public void SetDescription(String Description) {this.Description = Description;}	
	
	public abstract class ItemTable implements BaseColumns {
		public static final String TABLE_NAME = "poke_item";
	    public static final String COLUMN_NAME_NAME = "name";
	    public static final String COLUMN_NAME_TYPE = "type";
	    public static final String COLUMN_NAME_SELL_PRICE = "sellPrice";
	    public static final String COLUMN_NAME_BUY_PRICE = "buyPrice";
	    public static final String COLUMN_NAME_ITEM_VALUE = "item_value";
		public static final String COLUMN_NAME_DESCRIPTION = "description";
	}
	public abstract class ItemCD {
		private static final String TEXT_TYPE = " VARCHAR(100)";
	    private static final String INTEGER_TYPE = " INTEGER";
	    private static final String VARCHAR_20 = " VARCHAR(20)";
	    private static final String NOT_NULL = " NOT NULL";
	    private static final String COMMA_SEP = ",";
	    public static final String SQL_CREATE_ENTRIES =
	        "CREATE TABLE " + Item.ItemTable.TABLE_NAME + " (" +
	        Item.ItemTable._ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
	        Item.ItemTable.COLUMN_NAME_NAME + VARCHAR_20 + NOT_NULL + COMMA_SEP +
	        Item.ItemTable.COLUMN_NAME_TYPE + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
	        Item.ItemTable.COLUMN_NAME_BUY_PRICE + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
	        Item.ItemTable.COLUMN_NAME_SELL_PRICE + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
	        Item.ItemTable.COLUMN_NAME_ITEM_VALUE + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			Item.ItemTable.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + NOT_NULL + COMMA_SEP +
	        "PRIMARY KEY (" + Item.ItemTable._ID + ")" +
	    " )";
	    
	    public static final String SQL_DELETE_ENTRIES =
		    "DROP TABLE IF EXISTS " + Item.ItemTable.TABLE_NAME;
	    
	    public static final String SQL_DATA_ENTRIES =
	    	"INSERT INTO " + Item.ItemTable.TABLE_NAME + 
	    	" SELECT 1 AS " + Item.ItemTable._ID +
	    		", 'Monster Ball' AS " + Item.ItemTable.COLUMN_NAME_NAME +
	    		", 1 AS " + Item.ItemTable.COLUMN_NAME_TYPE +
	    		", 200 AS " + Item.ItemTable.COLUMN_NAME_BUY_PRICE +  
	    		", 100 AS " + Item.ItemTable.COLUMN_NAME_SELL_PRICE + 
	    		", 2 AS " + Item.ItemTable.COLUMN_NAME_ITEM_VALUE +
	    		", 'Menangkap moster di area luar' AS " + Item.ItemTable.COLUMN_NAME_DESCRIPTION + 
	    	" UNION SELECT 2, 'Great Ball', 1, 600, 300, 3, 'Menangkap moster dalam area luar'" + 
	    	" UNION SELECT 3, 'Ultra Ball', 1, 1200, 600, 4, 'Menangkap moster dalam area luar'" + 
	    	" UNION SELECT 4, 'Potion', 2, 300, 150, 20, 'Menyembuhkan monster sebesar 20 HP'" + 
	    	" UNION SELECT 5, 'Super Potion', 2, 700, 350, 50, 'Menyembuhkan monster sebesar 50 HP'" + 
	    	" UNION SELECT 6, 'Hyper Potion', 2, 1200, 600, 200, 'Menyembuhkan monster sebesar 200 HP'" + 
	    	" UNION SELECT 7, 'Max Potion', 2, 2500, 1250, 400, 'Menyembuhkan monster sebesar 400 HP'" + 
	    	" UNION SELECT 8, 'Full Restore', 3, 3000, 1500, 400, 'Mengembalikan HP seutuhnya dan menyembuhkan semua penyakit, seperti keracunan'" + 
	    	" UNION SELECT 9, 'Burn Heal', 4, 250, 125, 4, 'Obat untuk menyembuhkan monster yang menderita luka bakar'" + 
	    	" UNION SELECT 10, 'Awakening', 4, 250, 125, 6, 'Membangunkan monster yang tertidur'" +
	    	" UNION SELECT 11, 'Protein', 5, 9800, 4900, 2, 'Meningkatkan kekuatan serangan monster'" +
	    	" UNION SELECT 12, 'Iron', 5, 9800, 4900, 3, 'Meningkatkan tingkat pertahanan monster'" +
	    	" UNION SELECT 13, 'Carbos', 5, 9800, 4900, 4, 'Meningkatkan kecepatan monster'" +
	    	" UNION SELECT 14, 'HP UP', 5, 9800, 4900, 1, 'Meningkatkan Maksimum Health Point monster'" +
	    	" UNION SELECT 15, 'Monster Egg', 6, 20000, 10000, 1, 'Membeli telur monster'" +
	    	" UNION SELECT 16, 'Repel', 7, 350, 175, 100, 'Semprot aerosol yang membuat monster liar pergi'" +
	    	" UNION SELECT 17, 'Super Repel', 7, 500, 250, 200, 'Membuat monster liar pergi dan lebih tahan lama dibandingkan Repel'" +
	    	" UNION SELECT 18, 'Bicycle', 8, 200000, 100000, 1, 'Bersepeda'" +
	    	" UNION SELECT 19, 'Super Rod', 8, 150000, 75000, 2, 'Memancing monster di perairan'" +
	    	" UNION SELECT 20, 'TM: LeafStorm', 9, 25000, 12500, 3, 'The user whips up a storm of leaves around the target. The attacks recoil harshly reduces the users Sp. Atk stat.'" +
	    	" UNION SELECT 21, 'TM: AirSlash', 9, 25000, 12500, 10, 'The user attacks with a blade of air that slices even the sky. It may also make the target flinch.'" +
	    	" UNION SELECT 22, 'TM: Blizzard', 9, 25000, 12500, 13, 'A howling blizzard is summoned to strike the opposing team. It may also freeze them solid.'" +
	    	" UNION SELECT 23, 'TM: Thunder', 9, 25000, 12500, 17, 'A wicked thunderbolt is dropped on the target to inflict damage. It may also leave the target with paralysis.'" +
	    	" UNION SELECT 24, 'TM: Psychic', 9, 25000, 12500, 20, 'The target is hit by a strong telekinetic force. It may also reduce the targets Sp.Def stat.'" +
	    	" UNION SELECT 25, 'TM: FireBlast', 9, 25000, 12500, 23, 'The target is attacked with an intense blast of all-consuming fire. It may also leave the target with a burn.'" +
	    	" UNION SELECT 26, 'TM: FocusBlast', 9, 25000, 12500, 27, 'The user heightens its mental focus and unleashes its power. It may also lower the targets Sp. Def.'" +
	    	" UNION SELECT 27, 'TM: GigaImpact', 9, 25000, 12500, 34, 'The user charges at the target using every bit of its power. The user must rest on the next turn.'" +
	    	" UNION SELECT 28, 'TM: Dive', 9, 25000, 12500, 48, 'Diving on the first turn, the user floats up and attacks on the second turn. It can be used to dive deep in the ocean.'" +
            " UNION SELECT 29, 'Torch', 10, 250, 125, 1, 'Will make all wild area visible to see when night.'" + 
	    	" UNION SELECT 30, 'Antidote', 4, 100, 50, 2, 'Dapat menyembuhkan monster dari keracunan.'" + 
            " UNION SELECT 31, 'Ice Heal', 4, 250, 125, 5, 'Menyembuhkan monster dari rasa beku.'" + 
	    	" UNION SELECT 32, 'Parlyz Heal', 4, 200, 100, 1, 'Menyembuhkan monster dari rasa kaku.'" + 
            " UNION SELECT 33, 'TM: Swim', 9, 25000, 12500, 46, 'It swamps the area around the user with a giant wave. It can also be used for crossing water'" +
	    	" UNION SELECT 34, 'TM: Cut', 9, 25000, 12500, 59, 'The target is cut with a scythe or a claw. It can also be used to cut down thin trees.'" + 
            " UNION SELECT 35, 'TM: Push', 9, 25000, 12500, 60, 'The target is slugged with a punch thrown at maximum power. This move can also be used to move boulders.'" +
            " UNION SELECT 36, 'TM: Sandstorm', 9, 0, 0, 22, 'BLA BLA BLA'";
	}
	
	// Constructor dengan parameter Nama Item
	Item(String ItemName, SQLiteDatabase db) {
		String[] projection = {
			Item.ItemTable._ID,
			Item.ItemTable.COLUMN_NAME_NAME,
	        Item.ItemTable.COLUMN_NAME_TYPE,
	        Item.ItemTable.COLUMN_NAME_BUY_PRICE,
	        Item.ItemTable.COLUMN_NAME_SELL_PRICE,
	        Item.ItemTable.COLUMN_NAME_ITEM_VALUE,
			Item.ItemTable.COLUMN_NAME_DESCRIPTION
			};
		
		Cursor c = db.query(
			Item.ItemTable.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			Item.ItemTable.COLUMN_NAME_NAME + " = '" + ItemName + "'",        // The columns for the WHERE clause (String)
			null,                       // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);
		
		if(c.moveToFirst()) {
			ItemNumb = (int) c.getInt(c.getColumnIndexOrThrow(Item.ItemTable._ID));
			Name = (String) c.getString(c.getColumnIndexOrThrow(Item.ItemTable.COLUMN_NAME_NAME));
			Type = (int) c.getInt(c.getColumnIndexOrThrow(Item.ItemTable.COLUMN_NAME_TYPE));
			BuyPrice = (int) c.getInt(c.getColumnIndexOrThrow(Item.ItemTable.COLUMN_NAME_BUY_PRICE));
			SellPrice = (int) c.getInt(c.getColumnIndexOrThrow(Item.ItemTable.COLUMN_NAME_SELL_PRICE));
			ItemValue = (int) c.getInt(c.getColumnIndexOrThrow(Item.ItemTable.COLUMN_NAME_ITEM_VALUE));
			Description = (String) c.getString(c.getColumnIndexOrThrow(Item.ItemTable.COLUMN_NAME_DESCRIPTION));
		} else {
			ItemNumb = -1;
			Name = "";
			Type = 0;
			BuyPrice = 0;
			SellPrice = 0;
			ItemValue = 0;
			Description = "";
		}
	
	}
	
	public static ArrayList<String> getItemNameAndPrice(SQLiteDatabase db) {
		String[] projection = {
			Item.ItemTable.COLUMN_NAME_NAME,
	        Item.ItemTable.COLUMN_NAME_BUY_PRICE
			};

		Cursor c = db.query(
			Item.ItemTable.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			null,                       // The columns for the WHERE clause (String)
			null,                       // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);
		ArrayList<String> temp = new ArrayList<String>();
		if(c.moveToFirst()) {
			do {
			    temp.add((String) c.getString(c.getColumnIndexOrThrow(Item.ItemTable.COLUMN_NAME_NAME)) + " ($" + 
			    		(int) c.getInt(c.getColumnIndexOrThrow(Item.ItemTable.COLUMN_NAME_BUY_PRICE)) + ")");
			} while(c.moveToNext());
			return temp;
		} else return null;
	}
	
	public static String getDescription(SQLiteDatabase db, int item_num) {
		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
			Item.ItemTable.COLUMN_NAME_DESCRIPTION
			};
		
		String where_clause = Item.ItemTable._ID + " = " + item_num;

		Cursor c = db.query(
			Item.ItemTable.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			where_clause,                       // The columns for the WHERE clause (String)
			null,                       // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);
		if(c.moveToFirst()) {
			return (String) c.getString(c.getColumnIndexOrThrow(Item.ItemTable.COLUMN_NAME_DESCRIPTION));
		} else return "";
	}
	
	public static String getName(SQLiteDatabase db, int item_num) {
		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
			Item.ItemTable.COLUMN_NAME_NAME
			};
		
		String where_clause = Item.ItemTable._ID + " = " + item_num;

		Cursor c = db.query(
			Item.ItemTable.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			where_clause,                       // The columns for the WHERE clause (String)
			null,                       // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);
		if(c.moveToFirst()) {
			return (String) c.getString(c.getColumnIndexOrThrow(Item.ItemTable.COLUMN_NAME_NAME));
		} else return null;
	}
	
	// Constructor dengan parameter Nomor/ID Item
	public Item(int IDItem, SQLiteDatabase db) {
		String[] projection = {
			Item.ItemTable._ID,
			Item.ItemTable.COLUMN_NAME_NAME,
	        Item.ItemTable.COLUMN_NAME_TYPE,
	        Item.ItemTable.COLUMN_NAME_BUY_PRICE,
	        Item.ItemTable.COLUMN_NAME_SELL_PRICE,
	        Item.ItemTable.COLUMN_NAME_ITEM_VALUE,
			Item.ItemTable.COLUMN_NAME_DESCRIPTION
			};
				
		Cursor c = db.query(
			Item.ItemTable.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			Item.ItemTable._ID + "=" + IDItem,         // The columns for the WHERE clause (String)
			null,                       // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);
		if(c.moveToFirst()) {
			ItemNumb = (int) c.getInt(c.getColumnIndexOrThrow(Item.ItemTable._ID));
			Name = (String) c.getString(c.getColumnIndexOrThrow(Item.ItemTable.COLUMN_NAME_NAME));
			Type = (int) c.getInt(c.getColumnIndexOrThrow(Item.ItemTable.COLUMN_NAME_TYPE));
			BuyPrice = (int) c.getInt(c.getColumnIndexOrThrow(Item.ItemTable.COLUMN_NAME_BUY_PRICE));
			SellPrice = (int) c.getInt(c.getColumnIndexOrThrow(Item.ItemTable.COLUMN_NAME_SELL_PRICE));
			ItemValue = (int) c.getInt(c.getColumnIndexOrThrow(Item.ItemTable.COLUMN_NAME_ITEM_VALUE));
			Description = (String) c.getString(c.getColumnIndexOrThrow(Item.ItemTable.COLUMN_NAME_DESCRIPTION));
		} else {
			ItemNumb = -1;
			Name = "";
			Type = 0;
			BuyPrice = 0;
			SellPrice = 0;
			ItemValue = 0;
			Description = "";
		}
	}
	
	public static String GetFirstItemRead(SQLiteDatabase db) {

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
			Item.ItemTable._ID,
			Item.ItemTable.COLUMN_NAME_NAME,
	        Item.ItemTable.COLUMN_NAME_TYPE,
	        Item.ItemTable.COLUMN_NAME_BUY_PRICE,
	        Item.ItemTable.COLUMN_NAME_SELL_PRICE,
	        Item.ItemTable.COLUMN_NAME_ITEM_VALUE,
			Item.ItemTable.COLUMN_NAME_DESCRIPTION
			};

		Cursor c = db.query(
			Item.ItemTable.TABLE_NAME,  // The table to query (String)
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
				message += "\nItem ID:" + (int) c.getInt(c.getColumnIndexOrThrow(Item.ItemTable._ID));
			    message += "\nItem Name:" + (String) c.getString(c.getColumnIndexOrThrow(Item.ItemTable.COLUMN_NAME_NAME));
			    message += "\nItem Type: " + (int) c.getInt(c.getColumnIndexOrThrow(Item.ItemTable.COLUMN_NAME_TYPE));
			    message += "\nItem Buy Price: " + (int) c.getInt(c.getColumnIndexOrThrow(Item.ItemTable.COLUMN_NAME_BUY_PRICE));
			    message += "\nItem Sell Price: " + (int) c.getInt(c.getColumnIndexOrThrow(Item.ItemTable.COLUMN_NAME_SELL_PRICE));
				message += "\nItem Value: " + (int) c.getInt(c.getColumnIndexOrThrow(Item.ItemTable.COLUMN_NAME_ITEM_VALUE));
			    message += "\nItem Description: " + (String) c.getString(c.getColumnIndexOrThrow(Item.ItemTable.COLUMN_NAME_DESCRIPTION));
			    message += "\n";
			} while(c.moveToNext());
		}
		return message;
	}
}
