package user.pokeranch.cv.menu;

import java.util.ArrayList;

import user.pokeranch.Main;
import user.pokeranch.R;
import user.pokeranch.cv.area.CityActivity;
import user.pokeranch.cv.area.OuterActivity;
import user.pokeranch.cv.area.ShoreActivity;
import user.pokeranch.models.Item;
import user.pokeranch.models.PlayerItem;
import user.pokeranch.models.PokeDB;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Bag extends Activity {

	public static final String TAG = Bag.class.getSimpleName();
	public static final String KEY_ROD_USE = "KEY_ROD_USE";
	public static final String KEY_AT_SEA = "KEY_AT_SEA";
	
	private DisplayMetrics metrics;
	private PokeDB db;
	private ArrayAdapter<String> adapterItem;
	private ArrayAdapter<String> adapterMedicine;
	private ArrayAdapter<String> adapterTM;
	private ArrayAdapter<String> adapterTools;
	private LinearLayout mainLayout;
	private LinearLayout itemInfo;
	private LinearLayout blank;
	private ListView listLeftView;
	private TextView ItemTitle;
	private TextView ItemDescription;
	private TextView tm_type;
	private TextView tm_power;
	private TextView tm_accuracy;
	private TextView tm_PP;
	private Button use;
	
	// 
	private int selected_bag;

	private int selected_item;

	private int filterItem;
	
	private boolean isRodCanUse;
	
	public static int useItemId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		startActivityFrom(intent);
		
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics); // dapetin ukuran layar
		
		db = new PokeDB(this);
		
		// LIST KEY
		ArrayList<String> listItem = PlayerItem.getNameItemCat(Main.player.getID());
		if(listItem != null) adapterItem = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItem);
		listItem = PlayerItem.getNameMedicineCat(Main.player.getID());
		if(listItem != null) adapterMedicine = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItem);
		listItem = PlayerItem.getNameTMCat(Main.player.getID());
		if(listItem != null) adapterTM = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItem);
		listItem = PlayerItem.getNameToolsCat(Main.player.getID());
		if(listItem != null) adapterTools = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItem);
		
		/* ------- VIEW -------- */
		mainLayout = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(metrics.widthPixels, metrics.heightPixels);
		mainLayout.setLayoutParams(params);
		mainLayout.setOrientation(LinearLayout.HORIZONTAL);
		
		// BLANK VIEW
		
		blank = new LinearLayout(this);
		blank.setLayoutParams(params);
		blank.setOrientation(LinearLayout.VERTICAL);
		TextView blankText = new TextView(this);
		blankText.setText("Item tidak ada.");
		blank.addView(blankText);
		mainLayout.addView(blank);
		
		// NON BLANK VIEW
		listLeftView = new ListView(this);
		listLeftView.setVerticalScrollBarEnabled(true);
		params.width = metrics.widthPixels/2;
		params.height = LayoutParams.MATCH_PARENT;
		params.leftMargin = 10;
		listLeftView.setLayoutParams(params);
		
		itemInfo = new LinearLayout(this);
		itemInfo .setOrientation(LinearLayout.VERTICAL);
		params.width = metrics.widthPixels/2;
		params.height = LayoutParams.WRAP_CONTENT;
		
		ItemTitle = new TextView(this);
		ItemTitle.setText(" ");
		ItemDescription = new TextView(this);
		ItemDescription.setText(" ");
		tm_type = new TextView(this);
		tm_power = new TextView(this);
		tm_accuracy = new TextView(this);
		tm_PP = new TextView(this);
		itemInfo.addView(ItemTitle);
		itemInfo.addView(ItemDescription);
		itemInfo.addView(tm_type);
		itemInfo.addView(tm_power);
		itemInfo.addView(tm_accuracy);
		itemInfo.addView(tm_PP);
		
		use = new Button(this);
		use.setText("Gunakan");
		use.setGravity(Gravity.CENTER);
		use.setVisibility(View.GONE);
		use.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				use_item();
			}
			
		});
		itemInfo.addView(use);
		
		mainLayout.addView(listLeftView);
		mainLayout.addView(itemInfo);
		
		showItemCat();
		
		useItemId = 0;
		
		setContentView(mainLayout);
	}

	private void startActivityFrom(Intent intent) {
		String string = intent.getStringExtra(Main.EXTRA_MESSAGE);
		if(string.equals(OuterActivity.class.getSimpleName())) {
			filterItem = 2;
		} else if(string.equals(ShoreActivity.class.getSimpleName())) {
			if(!intent.getBooleanExtra(KEY_AT_SEA, false)) {
				filterItem = 3;
			} else
				filterItem = 4;
			
			isRodCanUse = intent.getBooleanExtra(KEY_ROD_USE, false);
		} else if(string.equals(CityActivity.class.getSimpleName())) {
			filterItem = 5;
		} else {
			filterItem = 1;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bag, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	        case R.id.action_items:
	        	ItemDescription.setText(" ");
				ItemTitle.setText(" ");
				use.setVisibility(View.GONE);
	            showItemCat();
	            return true;
	        case R.id.action_medicine:
	        	ItemDescription.setText(" ");
				ItemTitle.setText(" ");
				use.setVisibility(View.GONE);
	        	showMedicineCat();
	        	return true;
	        case R.id.action_tm:
	        	ItemDescription.setText(" ");
				ItemTitle.setText(" ");
				use.setVisibility(View.GONE);
	        	showTMCat();
	        	return true;
	        case R.id.action_tools:
	        	ItemDescription.setText(" ");
				ItemTitle.setText(" ");
				use.setVisibility(View.GONE);
	        	showToolsCat();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		if(db == null) db = new PokeDB(this);
		
		ArrayList<String> listItem = PlayerItem.getNameItemCat(Main.player.getID());
		if(listItem != null) adapterItem = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItem);
		listItem = PlayerItem.getNameMedicineCat(Main.player.getID());
		if(listItem != null) adapterMedicine = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItem);
		listItem = PlayerItem.getNameTMCat(Main.player.getID());
		if(listItem != null) adapterTM = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItem);
		listItem = PlayerItem.getNameToolsCat(Main.player.getID());
		if(listItem != null) adapterTools = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItem);
		
    	ItemDescription.setText(" ");
		ItemTitle.setText(" ");
		use.setVisibility(View.GONE);
		
		showItemCat();
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
	
	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy()");
		db.close();
		super.onDestroy();
	}
	
	private void showItemCat() {
		if(adapterItem != null) {
			blank.setVisibility(View.GONE);
			listLeftView.setVisibility(View.VISIBLE);
			itemInfo.setVisibility(View.VISIBLE);
			listLeftView.setAdapter(adapterItem);
			listLeftView.setOnItemClickListener(new ItemListener());
			selected_bag = 1;
		} else {
			listLeftView.setVisibility(View.GONE);
			itemInfo.setVisibility(View.GONE);
			blank.setVisibility(View.VISIBLE);
		} 
	}
	
	private void showMedicineCat() {
		if(adapterMedicine != null) {
			blank.setVisibility(View.GONE);
			listLeftView.setVisibility(View.VISIBLE);
			itemInfo.setVisibility(View.VISIBLE);
			listLeftView.setAdapter(adapterMedicine);
			listLeftView.setOnItemClickListener(new MedicineListener());
			selected_bag = 2;
			// GET FROM DATABASE
		} else {
			listLeftView.setVisibility(View.GONE);
			itemInfo.setVisibility(View.GONE);
			blank.setVisibility(View.VISIBLE);
		} 
	}
	
	private void showTMCat() {
		if(adapterTM != null) {
			blank.setVisibility(View.GONE);
			listLeftView.setVisibility(View.VISIBLE);
			itemInfo.setVisibility(View.VISIBLE);
			listLeftView.setAdapter(adapterTM);
			listLeftView.setOnItemClickListener(new TMListener());
			selected_bag = 3;
		} else {
			listLeftView.setVisibility(View.GONE);
			itemInfo.setVisibility(View.GONE);
			blank.setVisibility(View.VISIBLE);
		} 
	}
	
	private void showToolsCat() {
		if(adapterTools != null) {
			blank.setVisibility(View.GONE);
			listLeftView.setVisibility(View.VISIBLE);
			itemInfo.setVisibility(View.VISIBLE);
			listLeftView.setAdapter(adapterTools);
			listLeftView.setOnItemClickListener(new ToolsListener());
			selected_bag = 4;
		} else {
			listLeftView.setVisibility(View.GONE);
			itemInfo.setVisibility(View.GONE);
			blank.setVisibility(View.VISIBLE);
		} 
	}
	
	private class ItemListener implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			ContentValues cv = PlayerItem.getInfoItemCat(Main.player.getID(), (int) arg3);
			selected_item = (int) arg3;
			use.setVisibility(View.VISIBLE);
			ItemTitle.setText(cv.getAsString(Item.ItemTable.COLUMN_NAME_NAME) + "  X " + cv.getAsString(PlayerItem.PlayerItemTable.COLUMN_NAME_QTY));
			ItemDescription.setText("Description: " + cv.getAsString(Item.ItemTable.COLUMN_NAME_DESCRIPTION));
		}
		
	}
	
	private class MedicineListener implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			ContentValues cv = PlayerItem.getInfoMedicineCat(Main.player.getID(), (int) arg3);
			selected_item = (int) arg3;
			use.setVisibility(View.VISIBLE);
			ItemTitle.setText(cv.getAsString(Item.ItemTable.COLUMN_NAME_NAME) + "  X " + cv.getAsString(PlayerItem.PlayerItemTable.COLUMN_NAME_QTY));
			ItemDescription.setText("Description: " + cv.getAsString(Item.ItemTable.COLUMN_NAME_DESCRIPTION));
		}
		
	}
	
	private class TMListener implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			ContentValues cv = PlayerItem.getInfoTMCat(Main.player.getID(), (int) arg3);
			selected_item = (int) arg3;
			use.setVisibility(View.VISIBLE);
			ItemTitle.setText(cv.getAsString(Item.ItemTable.COLUMN_NAME_NAME) + "  X " + cv.getAsString(PlayerItem.PlayerItemTable.COLUMN_NAME_QTY));
			ItemDescription.setText("Description: " + cv.getAsString(Item.ItemTable.COLUMN_NAME_DESCRIPTION));
		}
		
	}
	
	private class ToolsListener implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			ContentValues cv = PlayerItem.getInfoToolsCat(Main.player.getID(), (int) arg3);
			selected_item = (int) arg3;
			use.setVisibility(View.VISIBLE);
			ItemTitle.setText(cv.getAsString(Item.ItemTable.COLUMN_NAME_NAME) + "  X " + cv.getAsString(PlayerItem.PlayerItemTable.COLUMN_NAME_QTY));
			ItemDescription.setText("Description: " + cv.getAsString(Item.ItemTable.COLUMN_NAME_DESCRIPTION));
		}
		
	}
	
	private void use_item() {
		assert selected_bag > 0;
		switch(selected_bag) {
			case 1:
				PlayerItem pItem = PlayerItem.getItemItemCat(selected_item);
				if((pItem.GetType() == 1) || (pItem.GetType() == 6)) {
					Toast.makeText(this, "Maaf, item ini tidak bisa digunakan sekarang.", Toast.LENGTH_LONG).show();
				} else {
					 if((filterItem >= 2) && (filterItem <= 5)) {
						 PlayerItem.RemQTY_itemNum(pItem.GetItemId(), 1);
						 useItemId = pItem.GetItemId();
						 finish();
					 } else {
						 Toast.makeText(this, "Maaf, item ini tidak bisa digunakan sekarang.", Toast.LENGTH_LONG).show();
					 }
				}
				break;
			case 2:
				PlayerItem pItem1 = PlayerItem.getMedicineItemCat(selected_item);
				
				Intent intent = new Intent(this, ItemUse.class);
				intent.putExtra(ItemUse.ITEM_USE, pItem1.GetItemId());
				startActivity(intent);
				break;
			case 3:
				PlayerItem pItem2 = PlayerItem.getTMItemCat(selected_item);
				
				Intent intent2 = new Intent(this, ItemUse.class);
				intent2.putExtra(ItemUse.ITEM_USE, pItem2.GetItemId());
				startActivity(intent2);
				break;
			case 4:
				PlayerItem pItem3 = PlayerItem.getToolsItemCat(selected_item);
				if((filterItem >= 2) && (filterItem <= 5)) {
					if(isRodCanUse && (pItem3.GetItemId() == 19)) {
						useItemId = pItem3.GetItemId();
						finish();
					} else if(pItem3.GetItemId() != 19){
						useItemId = pItem3.GetItemId();
						finish();
					}
				} else {
					Toast.makeText(this, "Maaf, item ini tidak bisa digunakan sekarang.", Toast.LENGTH_LONG).show();
				}
				break;
		}
	}

}
