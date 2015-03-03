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
import android.widget.TextView;
import android.widget.Toast;


public class OuterActivity extends Activity implements OuterView.OuterViewListener {
	private static final String TAG = OuterActivity.class.getSimpleName();
	private DisplayMetrics metrics;
	private boolean isViewMenu;
	private LinearLayout GameWidgets;
	
	// BUTTONS
	private Button EndGameButton;
	private Button Monster;
	private Button Pokedex;
	private Button bagButton;
	private Button Profile;
	
	private RelativeLayout Game;
	OuterView A;
	private ImageView menu_button;
	private static boolean isBounce = false;
	public static int floor_suggest;
	
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
		OuterView.screenSize(metrics.widthPixels, metrics.heightPixels);
		OuterView.density = metrics.density;
		A = new OuterView(this);
		
		Intent intent = getIntent();
		floor_suggest = intent.getIntExtra(OuterView.FLOOR_LEVEL, 1);
		A.SetActivityFrom(intent.getStringExtra(Main.EXTRA_MESSAGE));
        GameWidgets = new LinearLayout (this);
        GameWidgets.setOrientation(1);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        GameWidgets.setLayoutParams(params);
        GameWidgets.setPadding(10, 10, 10, 10);
		
        EndGameButton = new Button(this);
        Monster = new Button(this);
        Pokedex = new Button(this);
        bagButton = new Button(this);
        Profile = new Button(this);

        EndGameButton.setText("Start Game");
        Monster.setText("Monster");
        Pokedex.setText("Pokedex");
        bagButton.setText("Bag");
        Profile.setText(Main.player.getName());
        
        // Debugging
        TextView textview = new TextView(this);
        textview.setText("Isi variabel");
        Game.addView(textview);
        
        // LISTENER
        Monster.setOnClickListener(new MonsterListener());
        Pokedex.setOnClickListener(new PokedexListener());
        bagButton.setOnClickListener(new BagListener());
        Profile.setOnClickListener(new PlayerListener());
        
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
				A.setPauseEvent(true);
				
			}
        	
        });
        
        Game.addView(A);
        Game.addView(GameWidgets);
        Game.addView(menu_button);
        
        isViewMenu = false;
        isBounce = false;

        setContentView(Game);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.battle, menu);
		GameWidgets.setVisibility(View.VISIBLE);
		isViewMenu = true;
		A.setPauseEvent(true);
		
		return false;
	}
	
	@Override
	public void onResume() {
		Log.d(TAG, "onResume()");
		if(Bag.useItemId == 18) {
			if(Hero.getStatus() == 1) Hero.change_status(2);
			else if(Hero.getStatus() == 2) Hero.change_status(1);
		}
		if(BattleOuterArea.blackout) {
			isBounce = true;
			// Go to Home
			finish();
			Intent intent = new Intent(this, HomeActivity.class);
			intent.putExtra(Main.EXTRA_MESSAGE, OuterActivity.class.getSimpleName());
			startActivity(intent);
			BattleOuterArea.blackout = false;
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
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		Log.d(TAG,"onPause()");
		if(!isBounce) 
			if(A.thread != null) A.thread.setRunning(false);
		else isBounce = false;
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		Log.d(TAG,"onDestroy()");
		super.onDestroy();
	}
	
	@Override
	public void onBackPressed() {
		if(!isViewMenu) {
			finish();
			Intent intent = new Intent(this, Main.class);
			intent.putExtra(Main.EXTRA_MESSAGE, OuterActivity.class.getSimpleName());
			startActivity(intent);
		} else {
			isViewMenu = false;
			menu_button.setVisibility(View.VISIBLE);
			GameWidgets.setVisibility(View.GONE);
			A.setPauseEvent(false);
			A.thread.setRender(true);
		}
	}

//	@Override
//	public void OnHomeViewListener(int id) {
//		// TODO Auto-generated method stub
//		if(id == 1) {
//			Intent intent = new Intent(this, CityActivity.class);
//			intent.putExtra(EXTRA_MESSAGE, HomeActivity.class.getSimpleName());
//			startActivity(intent);
//			finish();
//		}
//	}

	@Override
	public void onToCityListener(int callAct) {
		// TODO Auto-generated method stub
		if(callAct == 1) {
			finish();
			Intent intent = new Intent(this, CityActivity.class);
			intent.putExtra(Main.EXTRA_MESSAGE, OuterActivity.class.getSimpleName());
			startActivity(intent);
		}
	}

	@Override
	public void onToNextLevel(int callAct) {
		// TODO Auto-generated method stub
		if(callAct == 1) {
			if(A.floor_level() < 10) {
				finish();
				Intent intent = new Intent(this, OuterActivity.class);
				intent.putExtra(Main.EXTRA_MESSAGE, OuterActivity.class.getSimpleName());
				intent.putExtra(OuterView.FLOOR_LEVEL, A.floor_level()+1);
				startActivity(intent);
			}
		}
	}

	@Override
	public void onToPreviousLevel(int callAct) {
		// TODO Auto-generated method stub
		if(callAct == 1) {
			finish();
			if(A.floor_level() == 1) {
				Intent intent = new Intent(this, CityActivity.class);
				intent.putExtra(Main.EXTRA_MESSAGE, OuterActivity.class.getSimpleName());
				startActivity(intent);
			} else {
				Intent intent = new Intent(this, OuterActivity.class);
				intent.putExtra(Main.EXTRA_MESSAGE, OuterActivity.class.getSimpleName());
				intent.putExtra(OuterView.FLOOR_LEVEL, A.floor_level()-1);
				startActivity(intent);
			}
		}
	}

	@Override
	public void onMeetMonster(int callAct) {
		// TODO Auto-generated method stub
		// finish();
		if(callAct == 1) {
			Intent intent = new Intent(this, BattleOuterArea.class);
			intent.putExtra(Main.EXTRA_MESSAGE, OuterActivity.class.getSimpleName());
			intent.putExtra(BattleOuterArea.FLOOR, A.floor_level());
			startActivity(intent);
		}
	}

	@Override
	public void onAfterBattle(int callAct) {
		// TODO Auto-generated method stub
		
	}
	
	private class MonsterListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
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
		intent.putExtra(Main.EXTRA_MESSAGE, OuterActivity.class.getSimpleName());
		intent.putExtra(MonsterParty.IS_CAN_CUT, true);
		intent.putExtra(MonsterParty.IS_USE_PUSH, true);
		startActivity(intent);
	}
	
	private void PokedexListen() {
		Intent intent = new Intent(this, Pokedex.class);
		intent.putExtra(Main.EXTRA_MESSAGE, OuterActivity.class.getSimpleName());
		startActivity(intent);
	}

	private void PlayerListen() {
		Intent intent = new Intent(this, Profile.class);
		intent.putExtra(Main.EXTRA_MESSAGE, OuterActivity.class.getSimpleName());
		startActivity(intent);
	}
	
	private void BagListen() {
		Intent intent = new Intent(this, Bag.class);
		intent.putExtra(Main.EXTRA_MESSAGE, OuterActivity.class.getSimpleName());
		startActivity(intent);
	}

}