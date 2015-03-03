package user.pokeranch.cv.battle;

import user.pokeranch.Main;
import user.pokeranch.models.Monster;
import user.pokeranch.models.PlayerMonster;
import user.pokeranch.models.PlayerParty;
import user.pokeranch.models.PokeDB;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SaveMonster extends Activity {

	public static final String LEVEL = "LEVEL";
	public static final String FOE_ID = "FOE_ID";
	
	private DisplayMetrics metrics;
	private LinearLayout Game;
	
	private PokeDB db;
	private Monster monster;
	private EditText newMonsterName;
	private TextView error_info;
	private Button submit;
	private Button ok;
	private int foe_id;
	private int foe_level;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		foe_id = intent.getIntExtra(FOE_ID, 0);
		foe_level = intent.getIntExtra(LEVEL, 0);
		
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics); // dapetin ukuran layar
		
		db = new PokeDB(this);
		
		get_new_monster();
		
		Game = new LinearLayout(this);
		Game.setOrientation(LinearLayout.VERTICAL);
		Game.setBackgroundColor(Color.WHITE);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		Game.setLayoutParams(params);
		Game.setGravity(0x05);
		
		TextView Title = new TextView(this);
		Title.setText("Selamat, Anda berhasil menangkap monster");
		Title.setTextSize(30);
		Title.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
		params.height = LayoutParams.WRAP_CONTENT;
		Title.setLayoutParams(params);
		
		
		error_info = new TextView(this);
		error_info.setTextColor(Color.RED);
		error_info.setVisibility(View.INVISIBLE);
		
		newMonsterName = new EditText(this);
		newMonsterName.setHint("Nama monster baru");
		
		submit = new Button(this);
		submit.setText("Simpan");
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if((newMonsterName.getText().toString() == "") || (newMonsterName.getText().toString() == null)) {
					error_info.setText("Masukan tidak diterima");
					error_info.setVisibility(View.VISIBLE);
				} else {
					if(newMonsterName.getText().toString().matches("[a-zA-Z]+") && newMonsterName.getText().toString().length() < 10) {
						PlayerMonster pm = new PlayerMonster(db.getReadableDatabase(), monster.getID(), Main.player.getID(), newMonsterName.getText().toString(), foe_level);
						if(PlayerParty.isCanAddToParty()) new PlayerParty(Main.player.getID(), pm.getMonsterNumber(), pm.getMonsterID());
						newMonsterName.setInputType(0);
						ok.setVisibility(View.VISIBLE);
						submit.setVisibility(View.INVISIBLE);
					} else {
						error_info.setText("Masukan tidak diterima");
						error_info.setVisibility(View.VISIBLE);
					}
				}
			}
			
		});
		
		ok = new Button(this);
		ok.setText("Baiklah");
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ook();
			}
			
		});
		ok.setVisibility(View.INVISIBLE);
		
		Game.addView(Title);
		Game.addView(error_info);
		Game.addView(newMonsterName);
		Game.addView(submit);
		Game.addView(ok);
		
		setContentView(Game);
	}
	
	@Override
	public void onBackPressed() {
		
	}
	
	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}
	
	private void get_new_monster() {
		monster = new Monster(db.getReadableDatabase(), foe_id);
	}
	
	private void ook() {
		finish();
	}

}