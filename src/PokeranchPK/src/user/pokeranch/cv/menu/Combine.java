package user.pokeranch.cv.menu;

import java.util.ArrayList;

import user.pokeranch.Main;
import user.pokeranch.cv.DrawableManager;
import user.pokeranch.models.Monster;
import user.pokeranch.models.PlayerMonster;
import user.pokeranch.models.PlayerParty;
import user.pokeranch.models.PokeDB;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Combine extends Activity {

	private DisplayMetrics metrics;
	private PokeDB db;
	
	private int monster_selected;
	private int monster1sel;
	private int monster2sel;
	private TextView monster1selected;
	private TextView monster2selected;
	private LinearLayout boxButton;
//	private TextView monsterdesc;
	private ListView listParty;
	private TextView bool;
	private Bitmap monster;
	private ImageView image;
	

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
		
		// Right Info
		listParty = new ListView(this);
		if(PlayerParty.getTotalParty() > 0) {
			ArrayList<String> listOfNameParty = PlayerParty.getNameMonster(db.getReadableDatabase());
			if(listOfNameParty != null) {
				listParty.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfNameParty));
			}
		}
		params = new LinearLayout.LayoutParams(300, LayoutParams.MATCH_PARENT);
		// params.setMargins(metrics.widthPixels - 300, 0, 0, 0);
		listParty.setLayoutParams(params);
		
		// Left Side
		LinearLayout leftLayout = new LinearLayout(this);
		leftLayout.setOrientation(LinearLayout.VERTICAL);
		params.width = metrics.widthPixels - 360;
		leftLayout.setLayoutParams(params);
//		monsterdesc = new TextView(this);
		monster1selected = new TextView(this);
		monster1selected.setText("Monster 1: ");
		
		monster2selected = new TextView(this);
		monster2selected.setText("Monster 2: ");
		
		//leftLayout.addView(monsterdesc);
		leftLayout.addView(monster1selected);
		leftLayout.addView(monster2selected);

		
		// Tombol untuk add monster 1, monster 2, combine utk left side
        boxButton = new LinearLayout(this);
        boxButton.setOrientation(LinearLayout.VERTICAL);
        Button Monster1 = new Button(this);
        Button Monster2 = new Button(this);
        Button Combine = new Button(this);
        Monster1.setText("Monster 1");
        Monster2.setText("Monster 2");
        Combine.setText("Combine");
        boxButton.addView(Monster1);
        boxButton.addView(Monster2);
//        boxButton.addView(Combine);
        
        leftLayout.addView(Combine);
        
        //scroll view
        ScrollView kiriScroll = new ScrollView(this);
		LinearLayout kiri = new LinearLayout(this);
		kiri.setOrientation(LinearLayout.VERTICAL);
		kiri.setGravity(Gravity.CENTER_HORIZONTAL);
		kiri.setScrollContainer(true);
		kiri.setScrollbarFadingEnabled(true);
		bool = new TextView(this);
		if(PlayerParty.getTotalParty() > 0) {
			bool.setText(PlayerParty.getFullInfoMonster(0));
			monster = DrawableManager.getInstance().MonsterPicture(PlayerParty.getMonsterNumberFromIndex(0));
		}
		else bool.setText("Kosong.");
		image = new ImageView(this);
		image.setImageBitmap(monster);
//		params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//		params.setMargins((int) (10*metrics.density),0, 0, 0);
		image.setLayoutParams(params);
//		params = new LinearLayout.LayoutParams(metrics.widthPixels - 300, LayoutParams.MATCH_PARENT);
//		kiri.setLayoutParams(params);
		kiri.addView(image);
		kiri.addView(bool);
		kiri.setScrollbarFadingEnabled(true);
        
		
		kiri.addView(boxButton);
		kiriScroll.addView(kiri);
		leftLayout.addView(kiriScroll);
		
		
		listParty.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				monster_selected = PlayerParty.getMonsterIndex((int) arg3);
//				if(PlayerParty.getTotalParty() > 0) {
//					monsterdesc.setText(PlayerParty.getFullInfoMonster(monster_selected));
//				}
//				else monsterdesc.setText("Kosong.");
				if(boxButton.getVisibility() == View.GONE) boxButton.setVisibility(View.VISIBLE);
				
				if(PlayerParty.getTotalParty() > 0) {
					bool.setText(PlayerParty.getFullInfoMonster((int) arg3));
					monster = DrawableManager.getInstance().MonsterPicture(PlayerParty.getMonsterNumberFromIndex((int) arg3));
					image.setImageBitmap(monster);
				}
				else bool.setText("Kosong.");
			}
		});
		
		Monster1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
					monster1sel = monster_selected;
					monster1selected.setText("Monster 1: " + PlayerMonster.getPlayerMonster(monster_selected).getName());
			}
			
		});
		
		Monster2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
					monster2sel = monster_selected;
					monster2selected.setText("Monster 2: " + PlayerMonster.getPlayerMonster(monster_selected).getName());
			}
			
		});
		
		Combine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (IsCombinable(monster1sel,monster2sel)) {
					PlayerMonster PM1 = PlayerMonster.getPlayerMonster(monster1sel);
					PlayerMonster PM2 = PlayerMonster.getPlayerMonster(monster2sel);
					Monster monster = new Monster(db.getReadableDatabase(), PM1.getMonsterNumber());
					int NextLevel;
					if((PM1.getLevel() + PM2.getLevel() > 50) && (PM1.getLevel() + PM2.getLevel() < 100)) {
						NextLevel = (PM1.getLevel() + PM2.getLevel())*3/2;
					} else if(PM1.getLevel() + PM2.getLevel() >= 100) {
						NextLevel = (PM1.getLevel() + PM2.getLevel())*5/3;
					} else {
						NextLevel = PM1.getLevel() + PM2.getLevel();
					}
					
					// New PlayerMonster
					PlayerMonster NewPM = new PlayerMonster(db.getReadableDatabase(), monster.getNextEvoNo(), Main.player.getID(), "Temp Name", NextLevel);
					
					int getIncBy1 = PM1.getHP() - (((monster.getBaseHP() + 100)*PM1.getLevel())/50) + 10;
					int getIncBy2 = PM2.getHP() - (((monster.getBaseHP() + 100)*PM2.getLevel())/50) + 10;
					int HP = (((monster.getBaseHP() + 100)*NextLevel)/50) + 10 + MaxWithZero(getIncBy1 + getIncBy2)/2;
					NewPM.setHP(HP);
					
					getIncBy1 = PM1.getAttack() - (((monster.getBaseAttack() + 100)*PM1.getLevel())/50) + 10;
					getIncBy2 = PM2.getAttack() - (((monster.getBaseAttack() + 100)*PM2.getLevel())/50) + 10;
					int Attack = (((monster.getBaseAttack() + 100)*NextLevel)/50) + 10 + MaxWithZero(getIncBy1 + getIncBy2)/2;
					NewPM.setAttack(Attack);
					
					getIncBy1 = PM1.getDefense() - (((monster.getBaseDefense() + 100)*PM1.getLevel())/50) + 10;
					getIncBy2 = PM2.getDefense() - (((monster.getBaseDefense() + 100)*PM2.getLevel())/50) + 10;
					int Defense = (((monster.getBaseDefense() + 100)*NextLevel)/50) + 10 + MaxWithZero(getIncBy1 + getIncBy2)/2;
					NewPM.setDefense(Defense);
					
					getIncBy1 = PM1.getSpeed() - (((monster.getBaseSpeed() + 100)*PM1.getLevel())/50) + 10;
					getIncBy2 = PM2.getSpeed() - (((monster.getBaseSpeed() + 100)*PM2.getLevel())/50) + 10;
					int Speed = (((monster.getBaseSpeed() + 100)*NextLevel)/50) + 10 + MaxWithZero(getIncBy1 + getIncBy2)/2;
					NewPM.setSpeed(Speed);
	
					NewPM.setSkill1(PM1.getSkill1());
					NewPM.setSkill2(PM2.getSkill2());
					NewPM.setSkill3(PM1.getSkill3());
					NewPM.setSkill4(PM2.getSkill4());
					
					NewPM.setPPSkill1(PM1.getPPSkill1());
					NewPM.setPPSkill2(PM2.getPPSkill2());
					NewPM.setPPSkill3(PM1.getPPSkill3());
					NewPM.setPPSkill4(PM2.getPPSkill4());
					
					PlayerMonster.DeleteMonsterById(monster1sel);
					PlayerMonster.DeleteMonsterById(monster1sel);
					
					if(PlayerParty.getTotalParty() > 0) {
						ArrayList<String> listOfNameParty = PlayerParty.getNameMonster(db.getReadableDatabase());
						if(listOfNameParty != null) {
							listParty.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listOfNameParty));
						}
					}
				} else {
					Toast.makeText(getApplicationContext(), "Tidak bisa mengkombinasikan monster.", Toast.LENGTH_LONG).show();
				}
			}
			
		});
		
		
		
		mainLayout.addView(leftLayout);
		mainLayout.addView(listParty);
		
		setContentView(mainLayout);
	}
	
	private int MaxWithZero(int x) {
		return x > 0? x : 0;
	}

	public boolean IsCombinable(int monster1, int monster2) {
		if(monster1 != monster2) {
			PlayerMonster PM1 = PlayerMonster.getPlayerMonster(monster1sel);
			PlayerMonster PM2 = PlayerMonster.getPlayerMonster(monster2sel);
			Monster monster_1 = new Monster(db.getReadableDatabase(), PM1.getMonsterNumber());
			Monster monster_2 = new Monster(db.getReadableDatabase(), PM2.getMonsterNumber());
			if(monster_1.getID() == monster_2.getID()) {
//				Monster monster = new Monster(db.getReadableDatabase(), monster1);
//				return (monster.getNextEvoNo() > 0);
				return true;
			} else return false;
		} else return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.shop_menu, menu);
		return false;
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
	
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}

}
