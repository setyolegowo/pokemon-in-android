package user.pokeranch.cv.menu;

import java.util.ArrayList;

import user.pokeranch.Main;
import user.pokeranch.models.Item;
import user.pokeranch.models.PlayerItem;
import user.pokeranch.models.PokeDB;
import android.app.Activity;
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

public class Buy extends Activity {

	private DisplayMetrics metrics;
	private PokeDB db;
	private ArrayList<String> listItemSell;
	
	private TextView currentPlayerMoney;
	private TextView itemDescription;
	private Button buy;
	private Item item_selected;

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
		listItemSell = Item.getItemNameAndPrice(db.getReadableDatabase());
		
		ListView listViewItemSell = new ListView(this);
		listViewItemSell.setVerticalScrollBarEnabled(true);
		if(listItemSell.size() > 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItemSell);
			listViewItemSell.setAdapter(adapter);
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
		currentPlayerMoney = new TextView(this);
		currentPlayerMoney.setText("Money: " + Main.player.getCurMoney());
		leftLayout.addView(currentPlayerMoney);
		itemDescription = new TextView(this);
		leftLayout.addView(itemDescription);
		buy = new Button(this);
		buy.setText("Buy");
		buy.setVisibility(View.GONE);
		leftLayout.addView(buy);
		
		listViewItemSell.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				item_selected = new Item((int) arg3 + 1,db.getReadableDatabase());
				itemDescription.setText(item_selected.GetDescription());
				if(buy.getVisibility() == View.GONE) buy.setVisibility(View.VISIBLE);
			}
		});
		
		buy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(Main.player.getCurMoney() > item_selected.GetBuyPrice()) {
					PlayerItem.AddQty(Main.player.getID(), item_selected.GetItemNumb(), 1, db.getWritableDatabase());
					Main.player.setCurMoney(Main.player.getCurMoney() - item_selected.GetBuyPrice());
					currentPlayerMoney.setText("Money: " + Main.player.getCurMoney());
				}
			}
			
		});
		
		mainLayout.addView(leftLayout);
		mainLayout.addView(listViewItemSell);
		
		setContentView(mainLayout);
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
