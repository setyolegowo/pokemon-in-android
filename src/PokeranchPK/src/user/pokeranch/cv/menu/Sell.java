package user.pokeranch.cv.menu;

import java.util.ArrayList;

import user.pokeranch.Main;
import user.pokeranch.models.PlayerItem;
import user.pokeranch.models.PokeDB;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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

public class Sell extends Activity {

	private DisplayMetrics metrics;
	private PokeDB db;
	private ArrayList<String> listItemSell;
	
	private ListView listViewItemSell;
	private LinearLayout leftLayout;
	private TextView itemPlayer;
	private int itemWillSell;
	private Button sellButton;
	private LinearLayout blank;
	
	private Context thisContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		thisContext = this;

		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics); // dapetin ukuran layar
		
		LinearLayout mainLayout = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(metrics.widthPixels, metrics.heightPixels);
		mainLayout.setLayoutParams(params);
		mainLayout.setOrientation(LinearLayout.HORIZONTAL);
		
		db = new PokeDB(this);
		
		// BLANK VIEW
		blank = new LinearLayout(this);
		blank.setLayoutParams(params);
		blank.setOrientation(LinearLayout.VERTICAL);
		TextView blankText = new TextView(this);
		blankText.setText("Item tidak ada.");
		blank.addView(blankText);
		mainLayout.addView(blank);
		
		// Right Side
		ArrayList<PlayerItem> allPI = PlayerItem.GetAllItem("" + Main.player.getID());
		listItemSell = new ArrayList<String>();
		for(int i = 0; i < allPI.size(); i++) {
			listItemSell.add(allPI.get(i).GetName());
		}
		
		listViewItemSell = new ListView(this);
		listViewItemSell.setVerticalScrollBarEnabled(true);
		if(listItemSell.size() > 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItemSell);
			listViewItemSell.setAdapter(adapter);
		}
		params.width = 250;
		params.height = LayoutParams.MATCH_PARENT;
		params.leftMargin = 10;
		listViewItemSell.setLayoutParams(params);
		
		// Left Side
		leftLayout = new LinearLayout(this);
		leftLayout.setOrientation(LinearLayout.VERTICAL);
		params.width = metrics.widthPixels - 300;
		leftLayout.setLayoutParams(params);
		
		itemPlayer = new TextView(this);
		itemPlayer.setVisibility(View.GONE);
		leftLayout.addView(itemPlayer);
		sellButton = new Button(this);
		sellButton.setVisibility(View.GONE);
		sellButton.setText("Sell");
		leftLayout.addView(sellButton);
		
		listViewItemSell.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(itemPlayer.getVisibility() != View.VISIBLE) itemPlayer.setVisibility(View.VISIBLE);
				if(sellButton.getVisibility() != View.VISIBLE) sellButton.setVisibility(View.VISIBLE);
				itemPlayer.setText(PlayerItem.getNameAndQty(Main.player.getID(), (int) arg3));
				setItemWillSell((int) arg3);
			}
			
		});
		
		sellButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(PlayerItem.RemQTY(db.getReadableDatabase(), getItemWillSell(), 1) == -1) Log.d(Sell.class.getSimpleName(), "Tidak menghapus");
				if(itemPlayer.getVisibility() != View.GONE) itemPlayer.setVisibility(View.GONE);
				if(sellButton.getVisibility() != View.GONE) sellButton.setVisibility(View.GONE);
				ArrayList<PlayerItem> allPI = PlayerItem.GetAllItem("" + Main.player.getID());
				listItemSell = new ArrayList<String>();
				for(int i = 0; i < allPI.size(); i++) {
					listItemSell.add(allPI.get(i).GetName());
				}
				if(listItemSell.size() > 0) {
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(thisContext, android.R.layout.simple_list_item_1, listItemSell);
					listViewItemSell.setAdapter(adapter);
				} else {
					blank.setVisibility(View.VISIBLE);
					listViewItemSell.setVisibility(View.GONE);
					leftLayout.setVisibility(View.GONE);
				}
			}
			
		});
		
		if(listItemSell.size() > 0) {
			blank.setVisibility(View.GONE);
		} else {
			listViewItemSell.setVisibility(View.GONE);
			leftLayout.setVisibility(View.GONE);
		}
		
		mainLayout.addView(listViewItemSell);
		mainLayout.addView(leftLayout);
		
		setContentView(mainLayout);
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
	
	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.sell, menu);
		return false;
	}

	public int getItemWillSell() {
		return itemWillSell;
	}

	public void setItemWillSell(int itemWillSell) {
		this.itemWillSell = itemWillSell;
	}

}
