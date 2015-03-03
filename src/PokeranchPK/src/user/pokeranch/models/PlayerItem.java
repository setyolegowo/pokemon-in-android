/**
 * 
 */
package user.pokeranch.models;

import java.util.ArrayList;

import user.pokeranch.Main;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


/**
 * @author Setyo Lego
 *
 */
public class PlayerItem {

	private static final String TAG = PlayerItem.class.getSimpleName();

	private static ArrayList<PlayerItem> ListPlayerItem;
	
/* tambahan kalau diperlukan */
	private int Player_Id;
	private int Item_Id;
	private int Qty;
	private String Name;
	private int Type;
	private String Description;
	
	public int GetPlayerId() {return Player_Id;}
	public int GetItemId() {return Item_Id;}
	public int GetQty() {return Qty;}
	public String GetName() {return Name;}
	public int GetType() {return Type;}
	public String GetDesc() {return Description;}
	
	public void SetPlayerId(int PlayerId) {this.Player_Id = PlayerId;}
	public void SetItemId(int ItemId) {this.Item_Id = ItemId;}
	public void SetQty(int Qty) {this.Qty = Qty;}
	public void SetName(String name) {this.Name = name;}
	public void SetType(int type) {this.Type = type;}
	public void SetDesc(String desc) {this.Description = desc;}
/* end of tambahan 1 */

	public abstract class PlayerItemTable {
		public static final String TABLE_NAME = "player_item";
		public static final String COLUMN_NAME_PLAYER_ID = "player_id";
		public static final String COLUMN_NAME_ITEM_ID = "item_id";
		public static final String COLUMN_NAME_QTY = "qty";
	}
	public abstract class PlayerItemCD {
		private static final String INTEGER_TYPE = " INTEGER";
		private static final String NOT_NULL = " NOT NULL";
		private static final String COMMA_SEP = ",";
		public static final String SQL_CREATE_ENTRIES =
			"CREATE TABLE " + PlayerItem.PlayerItemTable.TABLE_NAME + " (" +
			PlayerItem.PlayerItemTable.COLUMN_NAME_PLAYER_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			PlayerItem.PlayerItemTable.COLUMN_NAME_ITEM_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			PlayerItem.PlayerItemTable.COLUMN_NAME_QTY + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
			"PRIMARY KEY (" + PlayerItem.PlayerItemTable.COLUMN_NAME_PLAYER_ID + COMMA_SEP +
				PlayerItem.PlayerItemTable.COLUMN_NAME_ITEM_ID + ")" +
			"FOREIGN KEY (" + PlayerItem.PlayerItemTable.COLUMN_NAME_PLAYER_ID + ") REFERENCES " +
				Player.PlayerTabel.TABLE_NAME + " (" + Player.PlayerTabel._ID + ")" + COMMA_SEP +
			"FOREIGN KEY (" + PlayerItem.PlayerItemTable.COLUMN_NAME_ITEM_ID + ") REFERENCES " +
				Item.ItemTable.TABLE_NAME + " (" + Item.ItemTable._ID + ")" +
		")";
		public static final String SQL_DELETE_ENTRIES = 
			"DROP TABLE IF EXISTS " + PlayerItem.PlayerItemTable.TABLE_NAME;
		
		public static final String SQL_DATA_ENTRIES = 
			"INSERT INTO " + PlayerItem.PlayerItemTable.TABLE_NAME + " VALUES " + 
			"(0,1,1)";
	}
	
	public static void initPlayerItem(int PlayerId, SQLiteDatabase db) {
		String[] projection = {
			PlayerItem.PlayerItemTable.COLUMN_NAME_ITEM_ID,
			PlayerItem.PlayerItemTable.COLUMN_NAME_QTY
			};
		
		Cursor c = db.query(
			PlayerItem.PlayerItemTable.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			PlayerItem.PlayerItemTable.COLUMN_NAME_PLAYER_ID + " = " + PlayerId,        // The columns for the WHERE clause (String)
			null,                       // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);
		
		ListPlayerItem = new ArrayList<PlayerItem>();
		if(c.moveToFirst()) {
			do {
				PlayerItem temp = new PlayerItem();
				temp.SetItemId(c.getInt(c.getColumnIndexOrThrow(PlayerItem.PlayerItemTable.COLUMN_NAME_ITEM_ID)));
				temp.SetPlayerId(PlayerId);
				temp.SetQty(c.getInt(c.getColumnIndexOrThrow(PlayerItem.PlayerItemTable.COLUMN_NAME_QTY)));
				Item item = new Item(temp.GetItemId(),db);
				temp.SetName(item.GetName());
				temp.SetType(item.GetType());
				temp.SetDesc(item.GetDescription());
				ListPlayerItem.add(temp);
			} while(c.moveToNext());
		}
		c.close();
	}
	
	public PlayerItem() {
		
	}
	
	public static String getNameAndQty(int PlayerId, int itemId) {
		if(ListPlayerItem.size() > itemId) {
			return ListPlayerItem.get(itemId).GetName() + " x" + ListPlayerItem.get(itemId).Qty;
		} else return null;
	}
	
	public static ArrayList<String> getNameItemCat(int player_id) {
		if(ListPlayerItem.size() > 0) {
			boolean isEmpty = true;
			ArrayList<String> temp = new ArrayList<String>();
			for(int i = 0; i < ListPlayerItem.size(); i++) {
				if((ListPlayerItem.get(i).GetType() == 1) || (ListPlayerItem.get(i).GetType() == 6) || (ListPlayerItem.get(i).GetType() == 7) || (ListPlayerItem.get(i).GetType() == 10)) { 
					temp.add(ListPlayerItem.get(i).GetName());
					isEmpty = false;
				}
			}
			if(isEmpty) return null;
			else return temp;
		} else return null;
	}
	
	public static ArrayList<String> getNameItemBattle(int player_id) {
		if(ListPlayerItem.size() > 0) {
			boolean isEmpty = true;
			ArrayList<String> temp = new ArrayList<String>();
			for(int i = 0; i < ListPlayerItem.size(); i++) {
				if((ListPlayerItem.get(i).GetType() == 1) || (ListPlayerItem.get(i).GetType() == 2) || (ListPlayerItem.get(i).GetType() == 3) || (ListPlayerItem.get(i).GetType() == 4)) { 
					temp.add(ListPlayerItem.get(i).GetName());
					isEmpty = false;
				}
			}
			if(isEmpty) return null;
			else return temp;
		} else return null;
	}
	
	public static PlayerItem getItemBattle(int itemPlayerId) {
		if(ListPlayerItem.size() > 0) {
			int i;
			int j = -1;
			for(i = 0; i < ListPlayerItem.size(); i++) {
				if((ListPlayerItem.get(i).GetType() == 1) || (ListPlayerItem.get(i).GetType() == 2) || (ListPlayerItem.get(i).GetType() == 3) || (ListPlayerItem.get(i).GetType() == 4))
					j++;
				if(j == itemPlayerId) break;
			}
			return ListPlayerItem.get(i);
		} else return null;
	}
	
	public static ContentValues getInfoItemCat(int player_id, int itemPlayerId) {
		if(ListPlayerItem.size() > 0) {
			ContentValues temp = new ContentValues();
			int i;
			int j = -1;
			for(i = 0; i < ListPlayerItem.size(); i++) {
				if((ListPlayerItem.get(i).GetType() == 1) || (ListPlayerItem.get(i).GetType() == 6) || (ListPlayerItem.get(i).GetType() == 7) || (ListPlayerItem.get(i).GetType() == 10))
					j++;
				if(j == itemPlayerId) break;
			}
			temp.put(Item.ItemTable.COLUMN_NAME_NAME, ListPlayerItem.get(i).GetName());
			temp.put(Item.ItemTable.COLUMN_NAME_DESCRIPTION, ListPlayerItem.get(i).GetDesc());
			temp.put(PlayerItem.PlayerItemTable.COLUMN_NAME_QTY, ListPlayerItem.get(i).GetQty());
			return temp;
		} else return null;
	}
	
	public static PlayerItem getItemItemCat(int itemPlayerId) {
		if(ListPlayerItem.size() > 0) {
			int i;
			int j = -1;
			for(i = 0; i < ListPlayerItem.size(); i++) {
				if((ListPlayerItem.get(i).GetType() == 1) || (ListPlayerItem.get(i).GetType() == 6) || (ListPlayerItem.get(i).GetType() == 7) || (ListPlayerItem.get(i).GetType() == 10))
					j++;
				if(j == itemPlayerId) break;
			}
			return ListPlayerItem.get(i);
		} else return null;
	}
	
	public static ArrayList<String> getNameMedicineCat(int player_id) {
		if(ListPlayerItem.size() > 0) {
			boolean isEmpty = true;
			ArrayList<String> temp = new ArrayList<String>();
			for(int i = 0; i < ListPlayerItem.size(); i++) {
				if((ListPlayerItem.get(i).GetType() >= 2) && (ListPlayerItem.get(i).GetType() <= 5)) {
					temp.add(ListPlayerItem.get(i).GetName());
					isEmpty = false;
				}
			}
			if(isEmpty) return null;
			else return temp;
		} else return null;
	}
	
	public static ContentValues getInfoMedicineCat(int player_id, int itemPlayerId) {
		if(ListPlayerItem.size() > 0) {
			ContentValues temp = new ContentValues();
			int i;
			int j = -1;
			for(i = 0; i < ListPlayerItem.size(); i++) {
				if((ListPlayerItem.get(i).GetType() >= 2) && (ListPlayerItem.get(i).GetType() <= 5))
					j++;
				if(j == itemPlayerId) break;
			}
			temp.put(Item.ItemTable.COLUMN_NAME_NAME, ListPlayerItem.get(i).GetName());
			temp.put(Item.ItemTable.COLUMN_NAME_DESCRIPTION, ListPlayerItem.get(i).GetDesc());
			temp.put(PlayerItem.PlayerItemTable.COLUMN_NAME_QTY, ListPlayerItem.get(i).GetQty());
			return temp;
		} else return null;
	}
	
	public static PlayerItem getMedicineItemCat(int itemPlayerId) {
		if(ListPlayerItem.size() > 0) {
			int i;
			int j = -1;
			for(i = 0; i < ListPlayerItem.size(); i++) {
				if((ListPlayerItem.get(i).GetType() >= 2) && (ListPlayerItem.get(i).GetType() <= 5))
					j++;
				if(j == itemPlayerId) break;
			}
			return ListPlayerItem.get(i);
		} else return null;
	}
	
	public static ArrayList<String> getNameTMCat(int player_id) {
		if(ListPlayerItem.size() > 0) {
			boolean isEmpty = true;
			ArrayList<String> temp = new ArrayList<String>();
			for(int i = 0; i < ListPlayerItem.size(); i++) {
				if(ListPlayerItem.get(i).GetType() == 9) {
					temp.add(ListPlayerItem.get(i).GetName());
					isEmpty = false;
				}
			}
			if(isEmpty) return null;
			else return temp;
		} else return null;
	}
	
	public static ContentValues getInfoTMCat(int player_id, int itemPlayerId) {
		if(ListPlayerItem.size() > 0) {
			ContentValues temp = new ContentValues();
			int i;
			int j = -1;
			for(i = 0; i < ListPlayerItem.size(); i++) {
				if(ListPlayerItem.get(i).GetType() == 9)
					j++;
				if(j == itemPlayerId) break;
			}
			temp.put(Item.ItemTable.COLUMN_NAME_NAME, ListPlayerItem.get(i).GetName());
			temp.put(Item.ItemTable.COLUMN_NAME_DESCRIPTION, ListPlayerItem.get(i).GetDesc());
			temp.put(PlayerItem.PlayerItemTable.COLUMN_NAME_QTY, ListPlayerItem.get(i).GetQty());
			return temp;
		} else return null;
	}
	
	public static PlayerItem getTMItemCat(int itemPlayerId) {
		if(ListPlayerItem.size() > 0) {
			int i;
			int j = -1;
			for(i = 0; i < ListPlayerItem.size(); i++) {
				if(ListPlayerItem.get(i).GetType() == 9)
					j++;
				if(j == itemPlayerId) break;
			}
			return ListPlayerItem.get(i);
		} else return null;
	}
	
	public static ArrayList<String> getNameToolsCat(int player_id) {
		if(ListPlayerItem.size() > 0) {
			boolean isEmpty = true;
			ArrayList<String> temp = new ArrayList<String>();
			for(int i = 0; i < ListPlayerItem.size(); i++) {
				if(ListPlayerItem.get(i).GetType() == 8) {
					temp.add(ListPlayerItem.get(i).GetName());
					isEmpty = false;
				}
			}
			if(isEmpty) return null;
			else return temp;
		} else return null;
	}
	
	public static ContentValues getInfoToolsCat(int player_id, int itemPlayerId) {
		if(ListPlayerItem.size() > 0) {
			ContentValues temp = new ContentValues();
			int i;
			int j = -1;
			for(i = 0; i < ListPlayerItem.size(); i++) {
				if(ListPlayerItem.get(i).GetType() == 8)
					j++;
				if(j == itemPlayerId) break;
			}
			temp.put(Item.ItemTable.COLUMN_NAME_NAME, ListPlayerItem.get(i).GetName());
			temp.put(Item.ItemTable.COLUMN_NAME_DESCRIPTION, ListPlayerItem.get(i).GetDesc());
			temp.put(PlayerItem.PlayerItemTable.COLUMN_NAME_QTY, ListPlayerItem.get(i).GetQty());
			return temp;
		} else return null;
	}
	
	public static PlayerItem getToolsItemCat(int itemPlayerId) {
		if(ListPlayerItem.size() > 0) {
			int i;
			int j = -1;
			for(i = 0; i < ListPlayerItem.size(); i++) {
				if(ListPlayerItem.get(i).GetType() == 8)
					j++;
				if(j == itemPlayerId) break;
			}
			return ListPlayerItem.get(i);
		} else return null;
	}
	
	public static String GetData() {
		String message = "";
		if(ListPlayerItem.size() > 0) {
			int i = 0;
			do {
				message += "\nPlayer ID:" + ListPlayerItem.get(i).GetPlayerId();
			    message += "\nItem ID:" + ListPlayerItem.get(i).GetItemId();
			    message += "\nQuantity: " + ListPlayerItem.get(i).GetQty();
			    message += "\n";
			    i++;
			} while(i < ListPlayerItem.size());
		}
		return message;
	}
	
	public static String GetAllData(SQLiteDatabase db) {
		String message = "";
		String[] projection = {
			PlayerItem.PlayerItemTable.COLUMN_NAME_PLAYER_ID,
			PlayerItem.PlayerItemTable.COLUMN_NAME_ITEM_ID,
			PlayerItem.PlayerItemTable.COLUMN_NAME_QTY
			};
		
		Cursor c = db.query(
			PlayerItem.PlayerItemTable.TABLE_NAME,  // The table to query (String)
			projection,                 // The columns to return (String[])
			null,        // The columns for the WHERE clause (String)
			null,                       // The values for the WHERE clause (String[])
			null,                       // don't group the rows
			null,                       // don't filter by row groups
			null                        // The sort order
		);
		if(c.moveToFirst()) {
			do {
				message += "\nPlayer id: " + c.getInt(c.getColumnIndexOrThrow(PlayerItem.PlayerItemTable.COLUMN_NAME_PLAYER_ID));
				message += "\nItem id: " + c.getInt(c.getColumnIndexOrThrow(PlayerItem.PlayerItemTable.COLUMN_NAME_ITEM_ID));
				message += "\nQty: " + c.getInt(c.getColumnIndexOrThrow(PlayerItem.PlayerItemTable.COLUMN_NAME_QTY));
				message += "\n";
			} while(c.moveToNext());
		}
		return message;
	}
	
	//method untuk menambah Qty
	public static void AddQty(int PlayerId, int ItemId, int Qty, SQLiteDatabase db) {
		// Searching item id is on the list
		boolean isFound = false;
		int i;
		int num = 0;
		for(i = 0; (i < ListPlayerItem.size()) && (!isFound); i++) {
			if(ListPlayerItem.get(i).GetItemId() == ItemId) { isFound = true; num = i; }
		}
			
        if (isFound) {
        	ListPlayerItem.get(num).SetQty(Qty + ListPlayerItem.get(num).GetQty());
        } else {
        	PlayerItem temp = new PlayerItem();
			temp.SetItemId(ItemId);
			temp.SetPlayerId(Main.player.getID());
			temp.SetQty(Qty);
			Item item = new Item(temp.GetItemId(),db);
			temp.SetName(item.GetName());
			temp.SetType(item.GetType());
			temp.SetDesc(item.GetDescription());
			ListPlayerItem.add(temp);
        }
	}
	
	public static int RemQTY_itemNum(int ItemId, int Qty) {
		boolean found = false;
		int i = 0;
		while(!found) {
			if(ListPlayerItem.get(i).GetItemId() == ItemId) found = true;
			else i++;
		}
		if(Qty > ListPlayerItem.get(i).GetQty()) return -1;
    	else {
    		
    		if(Qty == ListPlayerItem.get(i).GetQty()) ListPlayerItem.remove(i);
    		else {
    			ListPlayerItem.get(i).SetQty(ListPlayerItem.get(i).GetQty() - Qty);
    		}
    		return 0;
    	}
	}
	/**
	 * Menyebabkan nambah uang
	 * @param db
	 * @param ItemId
	 * @param Qty
	 * @return
	 */
	public static int RemQTY(SQLiteDatabase db, int ItemId, int Qty) {
    	if(Qty > ListPlayerItem.get(ItemId).GetQty()) return -1;
    	else {
    		Item item = new Item(ListPlayerItem.get(ItemId).GetItemId(), db);
    		Main.player.setCurMoney(Main.player.getCurMoney() + (item.GetSellPrice()*Qty));
    		if(Qty == ListPlayerItem.get(ItemId).GetQty()) ListPlayerItem.remove(ItemId);
    		else {
    			ListPlayerItem.get(ItemId).SetQty(ListPlayerItem.get(ItemId).GetQty() - Qty);
    		}
    		return 0;
    	}
	}
	
	public static ArrayList<PlayerItem> GetAllItem (String PlayerId) {
		return ListPlayerItem;
	}
	
	public static void savePlayerItemToDb(SQLiteDatabase db) {
		db.execSQL("DELETE FROM " + PlayerItem.PlayerItemTable.TABLE_NAME + 
			" WHERE " + PlayerItem.PlayerItemTable.COLUMN_NAME_PLAYER_ID + " LIKE " + Main.player.getID());
		
		String insert;
		for(int i = 0; i < ListPlayerItem.size(); i++) {
			insert = "INSERT INTO " + PlayerItem.PlayerItemTable.TABLE_NAME + " VALUES " +
				"(" + ListPlayerItem.get(i).GetPlayerId() + ", " + 
					ListPlayerItem.get(i).GetItemId() + ", " + 
					ListPlayerItem.get(i).GetQty() + ")";
			db.execSQL(insert);
		}
	}
	

	
	public static boolean isHaveEgg(SQLiteDatabase db, int player_id) {
		if(ListPlayerItem != null) {
			boolean found = false;
			for(int i = 0; ListPlayerItem.size() > i; i++) {
				if(ListPlayerItem.get(i).GetItemId() == 15) {
					found = true;
					Log.d(TAG," IS >>> FOUND!!!");
					break;
				}
			}
			return found;
		} else return false;
	}
}
