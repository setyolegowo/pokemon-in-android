package user.pokeranch.cv.menu;

import user.pokeranch.Main;
import user.pokeranch.cv.area.CityActivity;
import user.pokeranch.cv.area.HomeActivity;
import user.pokeranch.models.Monster;
import user.pokeranch.models.PlayerItem;
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

public class EggHatch extends Activity {

	private DisplayMetrics metrics;
	private LinearLayout Game;
	
	private PokeDB db;
	private Monster monster;
	private EditText newMonsterName;
	private TextView error_info;
	private Button submit;
	private Button ok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics); // dapetin ukuran layar
		
		db = new PokeDB(this);
		
		get_new_monster_number();
		
		Game = new LinearLayout(this);
		Game.setOrientation(LinearLayout.VERTICAL);
		Game.setBackgroundColor(Color.WHITE);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		Game.setLayoutParams(params);
		Game.setGravity(0x05);
		
		TextView Title = new TextView(this);
		Title.setText("Telor kamu sudah menetas...");
		Title.setTextSize(30);
		Title.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
		params.height = LayoutParams.WRAP_CONTENT;
		Title.setLayoutParams(params);
		
		TextView monster_spesies = new TextView(this);
		monster_spesies.setText("Spesies monster kamu adalah " + monster.getSpesies());
		
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
						PlayerMonster pm = new PlayerMonster(db.getReadableDatabase(), monster.getID(), Main.player.getID(), newMonsterName.getText().toString(), 5 + ((int) Math.random()*3));
						if(PlayerParty.isCanAddToParty()) new PlayerParty(Main.player.getID(), pm.getMonsterNumber(), pm.getMonsterID());
						PlayerItem.RemQTY_itemNum(15, 1);
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
		Game.addView(monster_spesies);
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
	
	private void get_new_monster_number() {
		int monster_num[] = {1,4,7,10,12,18,20,30};
		int index_t = (int) ((Math.random()*4) + (Math.random()*3));
		monster = new Monster(db.getReadableDatabase(), monster_num[index_t]);
	}
	
	private void ook() {
		finish();
		Intent intent = new Intent(this, HomeActivity.class);
		intent.putExtra(Main.EXTRA_MESSAGE, CityActivity.class.getSimpleName());
		startActivity(intent);
	}

}