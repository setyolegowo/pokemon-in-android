package user.pokeranch.cv.area;

import user.pokeranch.Main;
import user.pokeranch.cv.DrawableManager;
import user.pokeranch.cv.area.bitmap.Hero;
import user.pokeranch.cv.menu.Bag;
import user.pokeranch.cv.menu.Combine;
import user.pokeranch.cv.menu.MonsterParty;
import user.pokeranch.cv.menu.Pokedex;
import user.pokeranch.cv.menu.Profile;
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

public class CombinatoriumActivity extends Activity implements CombinatoriumView.CombinatoriumViewListener {
	
	private final String TAG = CombinatoriumActivity.class.getSimpleName();
	private DisplayMetrics metrics;
	private RelativeLayout Game;
	private CombinatoriumView mGameView;
	
	// BUTTONS
	private Button Monster;
	private Button Pokedex;
	private Button Bag;
	private Button Profile;
	
	// Interactive Text
	private TextView interact;
	private LinearLayout boxButton;
	private boolean isInteraction;
	private int i_interact;
	private LinearLayout GameWidgets;
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
		CombinatoriumView.screenSize(metrics.widthPixels, metrics.heightPixels);
		CombinatoriumView.density = metrics.density;
		mGameView = new CombinatoriumView(this);
		Intent intent = getIntent();
		mGameView.SetActivityFrom(intent.getStringExtra(Main.EXTRA_MESSAGE));
		
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
		
		// Interaction with person
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
        
        // Tombol untuk monster 1, monster 2, cancel
        boxButton = new LinearLayout(this);
        boxButton.setOrientation(LinearLayout.VERTICAL);
        Button combine = new Button(this);
        Button cancel = new Button(this);
        combine.setText("Combine Monster");
        combine.setOnClickListener(new CombineListener());
        cancel.setText("Cancel");
        cancel.setOnClickListener(new CancelListener());
        boxButton.addView(combine);
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
				mGameView.setPauseEvent(true);
				
			}
        	
        });

        Game.addView(mGameView);
        Game.addView(GameWidgets);
        Game.addView(interact);
        Game.addView(boxButton);
        Game.addView(menu_button);
        
        isInteraction = false;
        isViewMenu = false;
        setContentView(Game);
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
			mGameView.setPauseEvent(false);
			mGameView.thread.setRender(true);
		} else if(isInteraction) {
			startInteract(2);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		if(!isInteraction) {
			GameWidgets.setVisibility(View.VISIBLE);
			mGameView.setPauseEvent(true);
			isViewMenu = true;
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
		Log.d(TAG ,"onPause()");
		if(mGameView.thread != null) mGameView.thread.setRunning(false);
		super.onPause();
	}

	@Override
	public void onToCityListener(int callAct) {
		// TODO Auto-generated method stub
		if(callAct == 1) {
			finish();
			Intent intent = new Intent(this, CityActivity.class);
			intent.putExtra(Main.EXTRA_MESSAGE, CombinatoriumActivity.class.getSimpleName());
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
	
	private class CombineListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			CombineMonsterListen();
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
		intent.putExtra(Main.EXTRA_MESSAGE, CombinatoriumActivity.class.getSimpleName());
		startActivity(intent);
	}
	
	private void CombineMonsterListen() {
		Intent intent = new Intent(this, Combine.class);
		startActivity(intent);
	}
	
	
	@Override
	public void startInteract(int callAct) {
		// TODO Auto-generated method stub
		if(callAct == 1) {
			interact.setVisibility(View.VISIBLE);
			isInteraction = true;
			i_interact = 0;
			mGameView.setPauseEvent(true);
			
		} else if(isInteraction) {
			i_interact += 1;
			switch(i_interact) {
				case 1:
					interact.setText("Bagaimana saya melayani Anda?");
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
					mGameView.setPauseEvent(false);
					mGameView.thread.setRender(true);
					mGameView.resetCallAct();
					break;
			}
		}
	}
	
	public class InteractListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			startInteract(2);
		}
		
	}
	
}
