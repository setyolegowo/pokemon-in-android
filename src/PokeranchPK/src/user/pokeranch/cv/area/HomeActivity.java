package user.pokeranch.cv.area;

import user.pokeranch.Main;
import user.pokeranch.cv.DrawableManager;
import user.pokeranch.cv.area.bitmap.Hero;
import user.pokeranch.cv.menu.Bag;
import user.pokeranch.cv.menu.MonsterParty;
import user.pokeranch.cv.menu.Pokedex;
import user.pokeranch.cv.menu.Profile;
import user.pokeranch.models.PlayerItem;
import user.pokeranch.models.PlayerMonster;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class HomeActivity extends Activity implements HomeView.HomeViewListener {
	private static final String TAG = HomeActivity.class.getSimpleName();
	private DisplayMetrics metrics;
	private boolean isViewMenu;
	private LinearLayout GameWidgets;
	
	// BUTTONS
	private Button EndGameButton;
	private Button Monster;
	private Button Pokedex;
	private Button Bag;
	private Button Profile;
	private Button Sleep;
	private Button Save;
	
	private ImageView menu_button;
	
	private RelativeLayout Game;
	HomeView A;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics); // dapetin ukuran layar
		// DISABLE SCREEN OUT
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		Log.d(TAG , "METRICS INFORMATION: Density = " + metrics.density + "; Density DPI = " + metrics.densityDpi + 
				"; Height Pixel = " + metrics.heightPixels + "; Width Pixel = " + metrics.widthPixels +
				"; Scaled Density = " + metrics.scaledDensity + "; XDPI = " + metrics.xdpi + 
				"; YDPI = " + metrics.ydpi);
		
		Game = new RelativeLayout(this);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		Game.setLayoutParams(params);
		Game.setGravity(0x05);
		HomeView.density = metrics.density;
		A = new HomeView(this,metrics.widthPixels,metrics.heightPixels);
		Intent intent = getIntent();
		A.SetActivityFrom(intent.getStringExtra(Main.EXTRA_MESSAGE));
        GameWidgets = new LinearLayout (this);
        GameWidgets.setOrientation(1);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        GameWidgets.setLayoutParams(params);
        GameWidgets.setPadding(10, 10, 10, 10);
		
        EndGameButton = new Button(this);
        Monster = new Button(this);
        Pokedex = new Button(this);
        Bag = new Button(this);
        Profile = new Button(this);
        Save = new Button(this);
        Sleep = new Button(this);

        EndGameButton.setText("Start Game");
        Monster.setText("Monster");
        Pokedex.setText("Pokedex");
        Bag.setText("Tas");
        Profile.setText(Main.player.getName());
        Save.setText("Simpan");
        Sleep.setText("Tidur");
        
        // LISTENER
        Monster.setOnClickListener(new MonsterListener());
        Pokedex.setOnClickListener(new PokedexListener());
        Profile.setOnClickListener(new PlayerListener());
        Bag.setOnClickListener(new BagListener());
        Sleep.setOnClickListener(new SleepListener());
        Save.setOnClickListener(new SaveListener());
        
        params.width = 300;
        params.height = LayoutParams.WRAP_CONTENT;
        params.setMargins(10, 10, 10, 10);

        GameWidgets.addView(Monster, params);
        GameWidgets.addView(Pokedex, params);
        GameWidgets.addView(Bag, params);
        GameWidgets.addView(Profile, params);
        GameWidgets.addView(Sleep, params);
        GameWidgets.addView(Save, params);
        GameWidgets.setVisibility(View.GONE);
        
        // WHEN HAVE EGG
//        eggHatch = new LinearLayout(this);
//        eggHatch.setOrientation(LinearLayout.VERTICAL);
//        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(340, LayoutParams.WRAP_CONTENT);
//        params2.setMargins(30, 120, 30, 0);
//        eggHatch.setLayoutParams(params2);
//        newMonsterName = new EditText(this);
//        newMonsterName.setHint("New Monster Name");
//        params2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//        newMonsterName.setLayoutParams(params2);
//        newMonsterSpesies = new TextView(this);
//        newMonsterSpesies.setLayoutParams(params2);
//        params2.setMargins(50, 0, 50, 0);
//        monsterName = new Button(this);
//        monsterName.setText("Save Monster Name");
//        eggHatch.addView(newMonsterSpesies);
//        eggHatch.addView(newMonsterName);
//        eggHatch.addView(monsterName);
//        eggHatch.setVisibility(View.GONE);
        
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
        Game.addView(menu_button);
        Game.addView(GameWidgets);
        // Game.addView(eggHatch);
        
        isViewMenu = false;

        setContentView(Game);
        
        if(intent.getStringExtra(Main.EXTRA_MESSAGE).equals(OuterActivity.class.getSimpleName())) {
			SleepListen();
		}
	}
	LinearLayout eggHatch;
	EditText newMonsterName;
	TextView newMonsterSpesies;
	Button monsterName;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.battle, menu);
		menu_button.setVisibility(View.GONE);
		GameWidgets.setVisibility(View.VISIBLE);
		isViewMenu = true;
		A.setPauseEvent(true);
		
		return false;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(Hero.getStatus() != 1) Hero.change_status(1);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if(A.thread != null) A.thread.setRunning(false);
		Log.d(TAG,"onPause()");
	}
	
	@Override
	public void onBackPressed() {
		if(!isViewMenu) {
			finish();
			Intent intent = new Intent(this, Main.class);
			intent.putExtra(Main.EXTRA_MESSAGE, TAG);
			startActivity(intent);
		} else {
			menu_button.setVisibility(View.VISIBLE);
			isViewMenu = false;
			GameWidgets.setVisibility(View.GONE);
			A.setPauseEvent(false);
			A.thread.setRender(true);
		}
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
	
	private class SleepListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Log.d(TAG, "SLEEP LISTENER");
			SleepListen();
		}
		
	}
	
	private class SaveListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Log.d(TAG, "SAVE LISTENER");
			SaveListen();
		}
		
	}
	
	private void MonsterPartys() {
		Intent intent = new Intent(this, MonsterParty.class);
		intent.putExtra(Main.EXTRA_MESSAGE, HomeActivity.class.getSimpleName());
		startActivity(intent);
	}
	
	private void PokedexListen() {
		Intent intent = new Intent(this, Pokedex.class);
		intent.putExtra(Main.EXTRA_MESSAGE, HomeActivity.class.getSimpleName());
		startActivity(intent);
	}

	private void PlayerListen() {
		Intent intent = new Intent(this, Profile.class);
		intent.putExtra(Main.EXTRA_MESSAGE, HomeActivity.class.getSimpleName());
		startActivity(intent);
	}
	
	private void BagListen() {
		Intent intent = new Intent(this, Bag.class);
		intent.putExtra(Main.EXTRA_MESSAGE, HomeActivity.class.getSimpleName());
		startActivity(intent);
	}
	
	private void SleepListen() {
		// Set All Monster Player to FULL HP
		Log.d(TAG, "READY FOR SLEEPING");
		if(Main.player.getDayOrNight() == 0)
			Main.player.setDayOrNight(1);
		else {
			Main.player.setDayOrNight(0);
			Main.player.setTotalTimePlay(Main.player.getTotalTimePlay() + 1);
			PlayerMonster.incAgeOfMonster();
			String message = PlayerMonster.listNameMonsterDied();
			if(!message.equals("")) {
				Toast.makeText(this, "Your monster(s) is die: " + message, Toast.LENGTH_LONG).show();
			}
		}
		PlayerMonster.fullRestoreAllMonster();
		Log.d(TAG, "SLEEPING SUCCESSFULL");
		Toast.makeText(this, "Monster kamu sudah ditidurkan dan sekarang sudah bangun lagi.", Toast.LENGTH_LONG).show();
		isViewMenu = false;
		GameWidgets.setVisibility(View.GONE);
		A.setPauseEvent(false);
		Hero.setSumWalk(0);
	}
	
	private void SaveListen() {
		SleepListen();
		Log.d(TAG, "READY FOR SAVING");
		PokeDB db = new PokeDB(this);
		Main.player.saveDataToDatabase(db.getWritableDatabase());
		Log.d(TAG, "Saving player successfull");
		PlayerItem.savePlayerItemToDb(db.getWritableDatabase());
		Log.d(TAG, "Saving item successfull");
		PlayerMonster.savePlayerMonsterToDatabase(db.getWritableDatabase());
		Log.d(TAG, "Saving monster successfull");
		PlayerParty.savePlayerPartyToDB(db.getWritableDatabase(), Main.player.getID());
		Log.d(TAG, "Saving party successfull");
		Log.d(TAG, "DATA SAVED SUCCESSFULL.");
		Toast.makeText(this, "Data permainan telah disimpan.", Toast.LENGTH_LONG).show();
		db.close();
	}
	
	@Override
	public void OnHomeViewListener(int id) {
		// TODO Auto-generated method stub
		if(id == 1) {
			Intent intent = new Intent(this, CityActivity.class);
			intent.putExtra(Main.EXTRA_MESSAGE, HomeActivity.class.getSimpleName());
			startActivity(intent);
			finish();
		}
	}

}