package user.pokeranch.cv.menu;

import java.util.ArrayList;

import user.pokeranch.Main;
import user.pokeranch.cv.DrawableManager;
import user.pokeranch.models.Monster;
import user.pokeranch.models.PlayerMonster;
import user.pokeranch.models.PlayerParty;
import user.pokeranch.models.PokeDB;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Pokedex extends Activity {

	private DisplayMetrics metrics;
	private PokeDB db;
	private ArrayList<String> listSpesies;

	private TextView MonsterNum;
	private TextView elemen;
	private TextView HP;
	private TextView attack;
	private TextView defense;
	private TextView speed;
	private Monster monster;
	private ImageView monsterPicture;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics); // dapetin ukuran layar
		
		LinearLayout mainLayout = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(metrics.widthPixels, metrics.heightPixels);
		mainLayout.setLayoutParams(params);
		mainLayout.setOrientation(LinearLayout.HORIZONTAL);
		
		db = new PokeDB(this);
		
		// Right Side
		listSpesies = new ArrayList<String>();
		listSpesies.addAll(Monster.getMonsterSpesies(db.getReadableDatabase()));
		
		ListView listMonster = new ListView(this);
		listMonster.setVerticalScrollBarEnabled(true);
		if(listSpesies.size() > 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listSpesies);
			listMonster.setAdapter(adapter);
		}
		params.width = 250;
		params.height = LayoutParams.MATCH_PARENT;
		params.leftMargin = 10;
		listMonster.setLayoutParams(params);
		
		// Left Side
		LinearLayout monsterInfo = new LinearLayout(this);
		monsterInfo.setOrientation(LinearLayout.VERTICAL);
		
		monsterPicture = new ImageView(this);
		
		params.width = LayoutParams.MATCH_PARENT;
		params.height = LayoutParams.WRAP_CONTENT;
		LinearLayout desc = new LinearLayout(this);
			desc.setOrientation(LinearLayout.VERTICAL);
			MonsterNum = new TextView(this);
			elemen = new TextView(this);
			HP = new TextView(this);
			desc.addView(MonsterNum, params);
			desc.addView(elemen, params);
			desc.addView(HP, params);
			attack = new TextView(this);
			defense = new TextView(this);
			speed = new TextView(this);
			desc.addView(attack, params);
			desc.addView(defense, params);
			desc.addView(speed, params);
		
		params.width = metrics.widthPixels - 260;
		monsterPicture.setLayoutParams(params);
		desc.setLayoutParams(params);
		monsterInfo.setLayoutParams(params);
		monsterInfo.addView(monsterPicture);
		monsterInfo.addView(desc);
		
		// MAIN
		listMonster.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				monster = new Monster(db.getReadableDatabase(), arg3 + 1);
				MonsterNum.setText("MonsterNumber = " + monster.getID());
				elemen.setText("Elemen Number = " + monster.getElemenNo());
				HP.setText("HP = " + (100*monster.getBaseHP()/150) + "%");
				attack.setText("Attack = " + (100*monster.getBaseAttack()/150) + "%");
				defense.setText("Defense = " + (100*monster.getBaseDefense()/150) + "%");
				speed.setText("Speed = " + (100*monster.getBaseSpeed()/150) + "%");
				monsterPicture.setImageBitmap(DrawableManager.getInstance().MonsterPicture(monster.getID()));
			}
			
		});
		mainLayout.addView(monsterInfo);
		mainLayout.addView(listMonster);
		
		// MY CHEAT
		PlayerMonster pm = new PlayerMonster(db.getWritableDatabase(), 10, Main.player.getID(), "setyob", 12);
		if(PlayerParty.isCanAddToParty()) new PlayerParty(Main.player.getID(), pm.getMonsterNumber(), pm.getMonsterID());
		pm = new PlayerMonster(db.getWritableDatabase(), 65, Main.player.getID(), "setyoc", 19);
		if(PlayerParty.isCanAddToParty()) new PlayerParty(Main.player.getID(), pm.getMonsterNumber(), pm.getMonsterID());
		pm = new PlayerMonster(db.getWritableDatabase(), 77, Main.player.getID(), "setyoc", 19);
		if(PlayerParty.isCanAddToParty()) new PlayerParty(Main.player.getID(), pm.getMonsterNumber(), pm.getMonsterID());
		
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
		// getMenuInflater().inflate(R.menu.pokedex, menu);
		return false;
	}

}
