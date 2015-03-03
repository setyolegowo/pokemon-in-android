package user.pokeranch.cv.battle;

import java.util.ArrayList;

import user.pokeranch.Main;
import user.pokeranch.cv.menu.ItemUse;
import user.pokeranch.models.Item;
import user.pokeranch.models.PlayerItem;
import user.pokeranch.models.PokeDB;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class UseItem extends Activity {

	private DisplayMetrics metrics;
	private PokeDB db;
	private ArrayList<String> listPlayerItem;
	
	private TextView quantity;
	private TextView itemDescription;
	private Button useitem;
	private Item item_selected;
	private LinearLayout empty;
	
	public static int ball_use;
	public static boolean isUseItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics); // dapetin ukuran layar
		
		LinearLayout mainLayout = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(metrics.widthPixels, metrics.heightPixels);
		mainLayout.setLayoutParams(params);
		mainLayout.setOrientation(LinearLayout.HORIZONTAL);
		
		db = new PokeDB(this);
		
		// Right Side
		ListView listViewItemSell = new ListView(this);
		listViewItemSell.setVerticalScrollBarEnabled(true);
		if(PlayerItem.getNameItemBattle(Main.player.getID()) != null) {
			listPlayerItem = PlayerItem.getNameItemBattle(Main.player.getID());
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listPlayerItem);
			listViewItemSell.setAdapter(adapter);
		} else {
			empty = new LinearLayout(this);
			empty.setOrientation(LinearLayout.VERTICAL);
			TextView emptyText = new TextView(this);
			emptyText.setText("Player tidak mempunyai item.");
			empty.addView(emptyText);
			mainLayout.addView(empty);
		}
		params.width = 350;
		params.height = LayoutParams.MATCH_PARENT;
		params.leftMargin = 10;
		listViewItemSell.setLayoutParams(params);
		
		// Left Side
		LinearLayout leftLayout = new LinearLayout(this);
		leftLayout.setOrientation(LinearLayout.VERTICAL);
		params.width = metrics.widthPixels - 360;
		leftLayout.setLayoutParams(params);
		quantity = new TextView(this);
		// quantity.setText("Quantity: " + Main.player.getCurMoney());
		leftLayout.addView(quantity);
		itemDescription = new TextView(this);
		leftLayout.addView(itemDescription);
		useitem = new Button(this);
		useitem.setText("Use this item");
		useitem.setVisibility(View.GONE);
		leftLayout.addView(useitem);
		
		listViewItemSell.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				PlayerItem itemP = PlayerItem.getItemBattle((int) arg3);
				item_selected = new Item(itemP.GetItemId(),db.getReadableDatabase());
				quantity.setText(itemP.GetName() + " x " + itemP.GetQty());
				itemDescription.setText(item_selected.GetDescription());
				if(useitem.getVisibility() == View.GONE) useitem.setVisibility(View.VISIBLE);
			}
		});
		
		useitem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
					usetheitem();
					
			}
			
		});
		
		mainLayout.addView(leftLayout);
		mainLayout.addView(listViewItemSell);
		
		ball_use = 0;
		isUseItem = false;
		
		setContentView(mainLayout);
	}

	protected void usetheitem() {
		// TODO Auto-generated method stub
		if(item_selected.GetType() != 1) {
			Intent intent = new Intent(this, ItemUse.class);
			intent.putExtra(ItemUse.ITEM_USE, item_selected.GetItemNumb());
			startActivity(intent);
		} else {
			catchmonster();
		}
		isUseItem = true;
		finish();
	}
	
	public void catchmonster() {
		ball_use = item_selected.GetItemValue();
		PlayerItem.RemQTY_itemNum(item_selected.GetItemNumb(), 1);
		BattleView.isUseMonsterBall = true;
		finish();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.shop_menu, menu);
		return false;
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
	
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}

}
