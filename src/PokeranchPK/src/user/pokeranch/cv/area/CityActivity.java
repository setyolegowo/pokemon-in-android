package user.pokeranch.cv.area;

import user.pokeranch.Main;
import user.pokeranch.cv.DrawableManager;
import user.pokeranch.cv.area.bitmap.Hero;
import user.pokeranch.cv.menu.Bag;
import user.pokeranch.cv.menu.EggHatch;
import user.pokeranch.cv.menu.MonsterParty;
import user.pokeranch.cv.menu.Pokedex;
import user.pokeranch.cv.menu.Profile;
import user.pokeranch.models.PlayerItem;
import user.pokeranch.models.PlayerParty;
import user.pokeranch.models.PokeDB;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class CityActivity extends Activity implements CityView.CityViewListener {
	
	private CityView cityView;
	private final String TAG = CityActivity.class.getSimpleName();
	private DisplayMetrics metrics;
	private RelativeLayout Game;
	private LinearLayout GameWidgets;
	private Button Monster;
	private Button Pokedex;
	private Button bagButton;
	private Button Profile;
	private boolean isViewMenu;
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
		CityView.screenSize(metrics.widthPixels, metrics.heightPixels);
		CityView.density = metrics.density;
		cityView = new CityView(this);
		Intent intent = getIntent();
		cityView.SetActivityFrom(intent.getStringExtra(Main.EXTRA_MESSAGE));
		GameWidgets = new LinearLayout (this);
        GameWidgets.setOrientation(1);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        GameWidgets.setLayoutParams(params);
        GameWidgets.setPadding(10, 10, 10, 10);
        
        Monster = new Button(this);
        Pokedex = new Button(this);
        bagButton = new Button(this);
        Profile = new Button(this);
        
        Monster.setText("Monster");
        Pokedex.setText("Pokedex");
        bagButton.setText("Tas");
        Profile.setText(Main.player.getName());
        
        // LISTENER
        Monster.setOnClickListener(new MonsterListener());
        Pokedex.setOnClickListener(new PokedexListener());
        Profile.setOnClickListener(new PlayerListener());
        bagButton.setOnClickListener(new BagListener());
        
        params.width = 300;
        params.height = LayoutParams.WRAP_CONTENT;
        params.setMargins(10, 10, 10, 10);

        GameWidgets.addView(Monster, params);
        GameWidgets.addView(Pokedex, params);
        GameWidgets.addView(bagButton, params);
        GameWidgets.addView(Profile, params);
        GameWidgets.setVisibility(View.GONE);
        
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
				cityView.setPauseEvent(true);
			}
        	
        });
		
        Game.addView(cityView);
        Game.addView(GameWidgets);
        Game.addView(menu_button);
        
        isViewMenu = false;
        
		setContentView(Game);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		GameWidgets.setVisibility(View.VISIBLE);
		isViewMenu = true;
		cityView.setPauseEvent(true);
		
		return false;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(Bag.useItemId == 18) {
			if(Hero.getStatus() == 1) Hero.change_status(2);
			else if(Hero.getStatus() == 2) Hero.change_status(1);
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG ,"onPause()");
		if(cityView.thread != null) cityView.thread.setRunning(false); //matiin thread
	}
	
	@Override
	public void onBackPressed() {
		if(!isViewMenu) {
			finish();
			Intent intent = new Intent(this, Main.class);
			intent.putExtra(Main.EXTRA_MESSAGE, TAG);
			startActivity(intent);
		} else {
			isViewMenu = false;
			menu_button.setVisibility(View.VISIBLE);
			GameWidgets.setVisibility(View.GONE);
			cityView.setPauseEvent(false);
			cityView.thread.setRender(true);
		}
	}

	@Override
	public void onToHomeListener(int callAct) {
		// TODO Auto-generated method stub
		Log.d(TAG, "To home callAct = " + callAct);
		if(callAct == 1) {
			PokeDB db = new PokeDB(this);
			finish();
			Intent intent;
			if(PlayerItem.isHaveEgg(db.getReadableDatabase(), Main.player.getID())) {
				intent = new Intent(this, EggHatch.class);
			} else {
				intent = new Intent(this, HomeActivity.class);
				intent.putExtra(Main.EXTRA_MESSAGE, TAG);
			}
			db.close();
			startActivity(intent);
		}
	}

	@Override
	public void onToShopListener(int callAct) {
		// TODO Auto-generated method stub
		Log.d(TAG, "callAct = " + callAct);
		if(callAct == 1) {
			finish();
			Intent intent = new Intent(this, ShopActivity.class);
			intent.putExtra(Main.EXTRA_MESSAGE, TAG);
			startActivity(intent);
		}
	}

	@Override
	public void onToCombinatoriumListener(int callAct) {
		// TODO Auto-generated method stub
		Log.d(TAG, "callAct = " + callAct);
		if(callAct == 1) {
			if(PlayerParty.getTotalParty() > 0) {
				finish();
				Intent intent = new Intent(this, CombinatoriumActivity.class);
				intent.putExtra(Main.EXTRA_MESSAGE, TAG);
				startActivity(intent);
			} else {
				Toast.makeText(this, "Kamu setidaknya mempunyai satu monster didalam party kamu.", Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public void onToStadiumListener(int callAct) {
		Log.d(TAG, "callAct = " + callAct);
		if(callAct==1){
			if(PlayerParty.getTotalParty() > 0) {
				finish();
				Intent intent = new Intent(this, StadiumActivity.class);
				intent.putExtra(Main.EXTRA_MESSAGE, TAG);
				startActivity(intent);
			} else {
				Toast.makeText(this, "Kamu setidaknya mempunyai satu monster didalam party kamu.", Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public void onToOuterAreaListener(int callAct) {
		Log.d(TAG, "callAct = " + callAct);
		if(callAct==1){
			if(PlayerParty.getTotalParty() > 0) {
				finish();
				Intent intent = new Intent(this, OuterActivity.class);
				intent.putExtra(Main.EXTRA_MESSAGE, TAG);
				startActivity(intent);
			} else {
				Toast.makeText(this, "Kamu setidaknya mempunyai satu monster didalam party kamu.", Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public void onToSeaListener(int callAct) {
		Log.d(TAG, "callAct = " + callAct);
		if(callAct==1){
			if(PlayerParty.getTotalParty() > 0) {
				finish();
				Intent intent = new Intent(this, ShoreActivity.class);
				intent.putExtra(Main.EXTRA_MESSAGE, TAG);
				startActivity(intent);
			} else {
				Toast.makeText(this, "Kamu setidaknya mempunyai satu monster didalam party kamu.", Toast.LENGTH_LONG).show();
			}
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
	
	private void MonsterPartys() {
		Intent intent = new Intent(this, MonsterParty.class);
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
		intent.putExtra(Main.EXTRA_MESSAGE, CityActivity.class.getSimpleName());
		startActivity(intent);
	}

}
