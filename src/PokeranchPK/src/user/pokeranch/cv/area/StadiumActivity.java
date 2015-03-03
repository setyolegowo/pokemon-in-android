package user.pokeranch.cv.area;

import user.pokeranch.Main;
import user.pokeranch.cv.DrawableManager;
import user.pokeranch.cv.area.bitmap.Hero;
import user.pokeranch.cv.battle.BattleStadium;
import user.pokeranch.cv.menu.Bag;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StadiumActivity extends Activity implements StadiumView.StadiumViewListener {

	public static String TAG = StadiumActivity.class.getSimpleName();
	public static final String TOTAL_BET = "TOTAL_BET_STADIUM";
	private DisplayMetrics metrics;
	
	private StadiumView gameView;
	private TextView interact;
	private int i_interact;
	private boolean isInteraction;
	private LinearLayout boxButton;
	private LinearLayout boxBet;
	private EditText totalBet;
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
		
		RelativeLayout game = new RelativeLayout(this);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		game.setLayoutParams(params);
		
		Intent intent = getIntent();
		StadiumView.changeScreen(metrics.widthPixels, metrics.heightPixels);
		StadiumView.density = metrics.density;
		gameView = new StadiumView(this);
		gameView.SetActivityFrom(intent.getStringExtra(Main.EXTRA_MESSAGE));
		game.addView(gameView);
		GameWidgets = new LinearLayout (this);
        GameWidgets.setOrientation(1);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        GameWidgets.setLayoutParams(params);
        GameWidgets.setPadding(10, 10, 10, 10);
        
        Button Monster = new Button(this);
        Button Pokedex = new Button(this);
        Button Bag = new Button(this);
        Button Profile = new Button(this);
        
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
        
        game.addView(GameWidgets);
		
		// Interact
		interact = new TextView(this);
        interact.setTextColor(Color.RED);
        interact.setGravity(Gravity.BOTTOM);
        interact.setBackgroundDrawable(DrawableManager.getInstance().getBoxBitmap());
        params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.height = 160;
        params.setMargins(0, metrics.heightPixels - params.height, 300, 0);
        interact.setText("Selamat datang di Stadium Pokeranch.");
        interact.setGravity(Gravity.TOP);
        interact.setLayoutParams(params);
        interact.setVisibility(View.GONE);
        interact.setOnClickListener(new InteractListener());
        i_interact = -1;
        game.addView(interact);
        
        // Button
        boxButton = new LinearLayout(this);
        boxButton.setOrientation(LinearLayout.VERTICAL);
        Button participate = new Button(this);
        Button cancel = new Button(this);
        participate.setText("Berpatisipasi");
        participate.setOnClickListener(new ParticipateListener());
        cancel.setText("Batal");
        cancel.setOnClickListener(new CancelListener());
        boxButton.addView(participate);
        boxButton.addView(cancel);
        boxButton.setVisibility(View.GONE);
        game.addView(boxButton);
		
        // Set Bet
        boxBet = new LinearLayout(this);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(200, LayoutParams.WRAP_CONTENT);
        boxBet.setOrientation(LinearLayout.VERTICAL);
        totalBet = new EditText(this);
        totalBet.setHint("Total taruhan");
        totalBet.setLayoutParams(params2);
        Button checkBet = new Button(this);
        checkBet.setText("Cek taruhan");
        checkBet.setOnClickListener(new ParticipateButtonListener());
        Button cancel2 = new Button(this);
        cancel2.setText("Batal");
        cancel2.setOnClickListener(new Cancel2Listener());
        checkBet.setLayoutParams(params2);
        boxBet.addView(totalBet);
        boxBet.addView(checkBet);
        boxBet.addView(cancel2);
        boxBet.setVisibility(View.GONE);
        game.addView(boxBet);
        
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
        game.addView(menu_button);
        
        isInteraction = false;
        isViewMenu = false;
        
		setContentView(game);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		if(!isInteraction) {
			GameWidgets.setVisibility(View.VISIBLE);
			isViewMenu = true;
			gameView.setPauseEvent(true);
			
		}
		return false;
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
			gameView.setPauseEvent(false);
		} else if(isInteraction) {
			OnToInteract(2);
		}
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
		if(gameView.thread != null) gameView.thread.setRunning(false);
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
	
	private class InteractListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			OnToInteract(2);
		}
		
	}

	@Override
	public void OnToInteract(int callAct) {
		// TODO Auto-generated method stub
		if(callAct == 1) {
			interact.setVisibility(View.VISIBLE);
			isInteraction = true;
			i_interact = 0;
			gameView.setPauseEvent(true);
		} else if(isInteraction) {
			i_interact += 1;
			switch(i_interact) {
				case 1:
					interact.setText("Kamu mau berpartisipasi?");
					boxButton.setVisibility(View.VISIBLE);
					break;
				case 2:
					interact.setText("Kamu harap Anda datang kembali!");
					boxButton.setVisibility(View.GONE);
					break;
				case 3:
					interact.setVisibility(View.GONE);
					interact.setText("Selamat datang.");
					isInteraction = false;
					i_interact = -1;
					gameView.setPauseEvent(false);
					gameView.resetCallAct();
					break;
			}
		}
	}
	
	private class ParticipateListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			boxBet.setVisibility(View.VISIBLE);
			boxButton.setVisibility(View.GONE);
			i_interact-=1;
			interact.setText("Berapa banyak uang anda yang akan Anda taruhkan?");
			isInteraction = false;
		}
		
	}
	
	private class CancelListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			OnToInteract(2);
		}
		
	}
	
	private class ParticipateButtonListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			int id = 0;
			int val = 0;
			if(totalBet.getText() != null) {
				String bet = totalBet.getText().toString();
				String regex = "[0-9]+";
				if(bet.matches(regex)) {
					val = Integer.parseInt(bet);
					if(val > Main.player.getCurMoney()) id = 1;
					else if(val < 50000) id = 2;
					else id = 3;
				}
			}
			switch(id) {
				case 0: interact.setText("Masukan tidak diterima.\nBerapa banyak uang anda yang akan Anda taruhkan?"); break;
				case 1: interact.setText("Uang tidak cukup.\nberapa banyak uang anda yang akan Anda taruhkan?"); break; 
				case 2: interact.setText("Taruhan harus lebih dari 50000 Zeny.\nBerapa banyak uang anda yang akan Anda taruhkan?"); break;
				case 3: 
					Intent intent = new Intent(getApplicationContext(), BattleStadium.class);
					intent.putExtra(TOTAL_BET, val);
					startActivity(intent);
					break; // Go to battle stadium
			}
		}
		
	}
	
	private class Cancel2Listener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			boxBet.setVisibility(View.GONE);
			boxButton.setVisibility(View.VISIBLE);
			i_interact = 1;
			interact.setText("Kamu mau berpartisipasi?");
			isInteraction = true;
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
		intent.putExtra(Main.EXTRA_MESSAGE, StadiumActivity.class.getSimpleName());
		startActivity(intent);
	}

}
