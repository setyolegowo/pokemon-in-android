package user.pokeranch.cv.area;

import user.pokeranch.Main;
import user.pokeranch.cv.DrawableManager;
import user.pokeranch.cv.area.bitmap.Hero;
import user.pokeranch.cv.menu.Bag;
import user.pokeranch.cv.menu.Buy;
import user.pokeranch.cv.menu.MonsterParty;
import user.pokeranch.cv.menu.Pokedex;
import user.pokeranch.cv.menu.Profile;
import user.pokeranch.cv.menu.Sell;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ShopActivity extends Activity implements ShopView.ShopViewListener {

	private static final String TAG = ShopActivity.class.getSimpleName();
	private DisplayMetrics metrics;
	private boolean isViewMenu;
	private LinearLayout GameWidgets;
	
	// BUTTONS
	private Button Monster;
	private Button Pokedex;
	private Button Bag;
	private Button Profile;
	
	// Interactive Text
	private TextView interact;
	private LinearLayout boxButton;
	
	private RelativeLayout Game;
	private ShopView A;
	private boolean isInteraction;
	private int i_interact;
	private ImageView menu_button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics); // dapetin ukuran layar
		// DISABLE SCREEN OUT
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		Game = new RelativeLayout(this);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		Game.setLayoutParams(params);
		Game.setGravity(0x05);
		ShopView.changeScreen(metrics.widthPixels, metrics.heightPixels);
		ShopView.density = metrics.density;
		A = new ShopView(this);
		Intent intent = getIntent();
		A.SetActivityFrom(intent.getStringExtra(Main.EXTRA_MESSAGE));
        GameWidgets = new LinearLayout (this);
        GameWidgets.setOrientation(1);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        GameWidgets.setLayoutParams(params);
        GameWidgets.setPadding(10, 10, 10, 10);
		
        Monster = new Button(this);
        Pokedex = new Button(this);
        Bag = new Button(this);
        Profile = new Button(this);
        
        Monster.setText("Monster");
        Pokedex.setText("Pokedex");
        Bag.setText("Bag");
        Profile.setText(Main.player.getName());
        
        // LISTENER
        Monster.setOnClickListener(new MonsterListener());
        Pokedex.setOnClickListener(new PokedexListener());
        Profile.setOnClickListener(new PlayerListener());
        Bag.setOnClickListener(new BagListener());
        
        params.width = 300;
        params.height = LayoutParams.WRAP_CONTENT;
        params.setMargins(10, 10, 10, 10);

        GameWidgets.addView(Monster, params);
        GameWidgets.addView(Pokedex, params);
        GameWidgets.addView(Bag, params);
        GameWidgets.addView(Profile, params);
        GameWidgets.setVisibility(View.GONE);
        
        // Interaction with shop
        interact = new TextView(this);
        interact.setTextColor(Color.RED);
        interact.setGravity(Gravity.TOP);
        interact.setBackgroundDrawable(DrawableManager.getInstance().getBoxBitmap());
        params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.height = 160;
        params.setMargins(0, metrics.heightPixels - params.height, 300, 0);
        interact.setText("Selamat datang.");
        interact.setGravity(Gravity.TOP);
        interact.setLayoutParams(params);
        interact.setVisibility(View.GONE);
        interact.setOnClickListener(new InteractListener());
        i_interact = -1;
        
        // Tombol untuk buy, sell, cancel
        boxButton = new LinearLayout(this);
        boxButton.setOrientation(LinearLayout.VERTICAL);
        Button buy = new Button(this);
        Button sell = new Button(this);
        Button cancel = new Button(this);
        buy.setText("Beli");
        buy.setOnClickListener(new BuyListener());
        sell.setText("Jual");
        sell.setOnClickListener(new SellListener());
        cancel.setText("Batal");
        cancel.setOnClickListener(new CancelListener());
        boxButton.addView(buy);
        boxButton.addView(sell);
        boxButton.addView(cancel);
        boxButton.setVisibility(View.GONE);
        
        menu_button = new ImageView(this);
        menu_button.setImageBitmap(DrawableManager.getInstance().menu_button());
        params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.setMargins(0, 0, (int) (10*metrics.density), (int) (10*metrics.density));
        menu_button.setLayoutParams(params);
        menu_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				menu_button.setVisibility(View.GONE);
				GameWidgets.setVisibility(View.VISIBLE);
				isViewMenu = true;
				A.setPauseEvent(true);
			}
        	
        });

        Game.addView(A);
        Game.addView(GameWidgets);
        Game.addView(interact);
        Game.addView(boxButton);
        Game.addView(menu_button);
        
        isInteraction = false;
        isViewMenu = false;

        setContentView(Game);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		if(!isInteraction) {
			GameWidgets.setVisibility(View.VISIBLE);
			isViewMenu = true;
			A.setPauseEvent(true);
			
		}
		return false;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(Hero.getStatus() != 1) Hero.change_status(1);
	}
	
	@Override
	protected void onPause() {
		Log.d(TAG,"onPause()");
		super.onPause();
		if(A.thread != null) A.thread.setRunning(false);
	}
	
	@Override
	public void onBackPressed() {
		if(!isViewMenu && !isInteraction) {
			finish();
			Intent intent = new Intent(this, Main.class);
			intent.putExtra(Main.EXTRA_MESSAGE, TAG);
			startActivity(intent);
		} else if(isViewMenu) {
			isViewMenu = false;
			menu_button.setVisibility(View.VISIBLE);
			GameWidgets.setVisibility(View.GONE);
			A.setPauseEvent(false);
			A.thread.setRender(true);
		} else if(isInteraction) {
			startInteract(2);
		}
	}

	@Override
	public void onOutToCity(int callAct) {
		// TODO Auto-generated method stub
		if(callAct == 1) {
			Intent intent = new Intent(this, CityActivity.class);
			intent.putExtra(Main.EXTRA_MESSAGE, ShopActivity.class.getSimpleName());
			startActivity(intent);
			finish();
		}
	}
	
	private class MonsterListener implements View.OnClickListener {
		
		@Override
		public void onClick(View arg0) {
			MonsterPartys();
		}
	}
	
	private class PokedexListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			PokedexListen();
		}
		
	}
	
	private class PlayerListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			PlayerListen();
		}
		
	}
	
	private class BagListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			BagListen();
		}
		
	}
	
	private class BuyListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			BuyListen();
		}
		
	}
	
	private class SellListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			SellListen();
		}
		
	}
	
	private class CancelListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			startInteract(2);
		}
		
	}
	
	private void MonsterPartys() {
		Intent intent = new Intent(this, MonsterParty.class);
		intent.putExtra(Main.EXTRA_MESSAGE, HomeActivity.class.getSimpleName());
		startActivity(intent);
	}
	
	private void PokedexListen() {
		Intent intent = new Intent(this, Pokedex.class);
		startActivity(intent);
	}

	private void PlayerListen() {
		Intent intent = new Intent(this, Profile.class);
		startActivity(intent);
	}
	
	private void BagListen() {
		Intent intent = new Intent(this, Bag.class);
		intent.putExtra(Main.EXTRA_MESSAGE, ShopActivity.class.getSimpleName());
		startActivity(intent);
	}
	
	private void BuyListen() {
		Intent intent = new Intent(this, Buy.class);
		startActivity(intent);
	}
	
	private void SellListen() {
		Intent intent = new Intent(this, Sell.class);
		startActivity(intent);
	}

	@Override
	public void startInteract(int callAct) {
		// TODO Auto-generated method stub
		if(callAct == 1) {
			interact.setVisibility(View.VISIBLE);
			isInteraction = true;
			i_interact = 0;
			A.setPauseEvent(true);
			
		} else if(isInteraction) {
			i_interact += 1;
			switch(i_interact) {
				case 1:
					interact.setText("Ada apa?");
					boxButton.setVisibility(View.VISIBLE);
					break;
				case 2:
					interact.setText("Dimohon datang kembali!");
					boxButton.setVisibility(View.GONE);
					break;
				case 3:
					interact.setVisibility(View.GONE);
					interact.setText("Selamat datang.");
					isInteraction = false;
					i_interact = -1;
					A.setPauseEvent(false);
					A.thread.setRender(true);
					A.resetCallAct();
					break;
			}
		}
	}
	
	private class InteractListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			startInteract(2);
		}
	}

}
