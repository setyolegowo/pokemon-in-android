package user.pokeranch.cv.area;

import user.pokeranch.Main;
import user.pokeranch.cv.DrawableManager;
import user.pokeranch.cv.area.bitmap.Hero;
import user.pokeranch.cv.battle.BattleOuterArea;
import user.pokeranch.cv.menu.Bag;
import user.pokeranch.cv.menu.MonsterParty;
import user.pokeranch.cv.menu.Pokedex;
import user.pokeranch.cv.menu.Profile;
import user.pokeranch.models.PlayerMonster;
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

public class ShoreActivity extends Activity implements ShoreView.ShoreListener {

	private static final String TAG = ShoreActivity.class.getSimpleName();
	private DisplayMetrics metrics;
	private ShoreView gameView;
	private RelativeLayout Game;
	private LinearLayout GameWidgets;
	private Button Monster;
	private Button Pokedex;
	private Button Bag;
	private Button Profile;
	private boolean isViewMenu;
	private ImageView menu_button;
	
	private static boolean isBounce = false;

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
		Intent intent = getIntent();
		ShoreView.screenSize(metrics.widthPixels, metrics.heightPixels);
		gameView = new ShoreView(this);
		gameView.SetActivityFrom(intent.getStringExtra(Main.EXTRA_MESSAGE));
		
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
        Bag.setText("Tas");
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
				gameView.setPauseEvent(true);
				
			}
        	
        });
		
        Game.addView(gameView);
        Game.addView(GameWidgets);
        Game.addView(menu_button);
        
        isViewMenu = false;
        isBounce = false;
        
		setContentView(Game);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		GameWidgets.setVisibility(View.VISIBLE);
		isViewMenu = true;
		gameView.setPauseEvent(true);
		
		return false;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if((Hero.getStatus() == 3) && MonsterParty.usingSkill) {
			gameView.trans_hero();
			MonsterParty.usingSkill = false;
		}
		if(BattleOuterArea.blackout) {
			isBounce = true;
			// Go to Home
			finish();
			Intent intent = new Intent(this, HomeActivity.class);
			intent.putExtra(Main.EXTRA_MESSAGE, OuterActivity.class.getSimpleName());
			startActivity(intent);
			BattleOuterArea.blackout = false;
			Hero.change_status(1);
		} else if(BattleOuterArea.isNextLevel > 0) {
			// TODO Tolong implementasikan bagaimana monster naik level
			if(BattleOuterArea.isNextLevel == 2) {
				PokeDB db = new PokeDB(this);
				Toast.makeText(getApplicationContext(), "Monster kamu bertambah experiencenya. Mungkin ada yang evolusi.", Toast.LENGTH_SHORT).show();
				PlayerMonster.renderEvolution(db.getReadableDatabase());
				db.close();
			} else {
				
			}
			BattleOuterArea.isNextLevel = 0;
		}
	}
	
	@Override
	protected void onPause() {
		Log.d(TAG,"onPause()");
		super.onPause();
		if(!isBounce) 
			if(gameView.thread != null) gameView.thread.setRunning(false);
		else isBounce = false;
		
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
			gameView.setPauseEvent(false);
			gameView.thread.setRender(true);
		}
	}

	@Override
	public void OnToCityListener(int callAct) {
		// TODO Auto-generated method stub
		if(callAct == 1) {
			finish();
			Intent intent = new Intent(this, CityActivity.class);
			intent.putExtra(Main.EXTRA_MESSAGE, TAG);
			startActivity(intent);
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
		intent.putExtra(Main.EXTRA_MESSAGE, ShoreActivity.class.getSimpleName());
		intent.putExtra(MonsterParty.IS_SWIM_TRANS, gameView.isCanTransSwim());
		startActivity(intent);
	}
	
	private void PokedexListen() {
		Intent intent = new Intent(this, Pokedex.class);
		intent.putExtra(Main.EXTRA_MESSAGE, ShoreActivity.class.getSimpleName());
		startActivity(intent);
	}

	private void PlayerListen() {
		Intent intent = new Intent(this, Profile.class);
		intent.putExtra(Main.EXTRA_MESSAGE, ShoreActivity.class.getSimpleName());
		startActivity(intent);
	}
	
	private void BagListen() {
		Intent intent = new Intent(this, Bag.class);
		intent.putExtra(Main.EXTRA_MESSAGE, ShoreActivity.class.getSimpleName());
		startActivity(intent);
	}

	@Override
	public void onMeetMonster(int callAct) {
		// TODO Auto-generated method stub
		if(callAct == 1) {
			Intent intent = new Intent(this, BattleOuterArea.class);
			intent.putExtra(Main.EXTRA_MESSAGE, ShoreActivity.class.getSimpleName());
			intent.putExtra(BattleOuterArea.FLOOR, 1);
			startActivity(intent);
		}
	}

}