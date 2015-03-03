package user.pokeranch.cv.menu;

import java.util.ArrayList;

import user.pokeranch.Main;
import user.pokeranch.cv.DrawableManager;
import user.pokeranch.cv.battle.UseItem;
import user.pokeranch.models.Item;
import user.pokeranch.models.Monster;
import user.pokeranch.models.PlayerItem;
import user.pokeranch.models.PlayerMonster;
import user.pokeranch.models.PlayerParty;
import user.pokeranch.models.PokeDB;
import user.pokeranch.models.Skill;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

public class ItemUse extends Activity {

	public static final String ITEM_USE = "ITEM_USE_BAG";
	
	private PokeDB db;
	private TextView bool;
	private Bitmap monster;
	private ImageView image;
	private Button useItem;
	private static int scaling;
	private int monster_selected;
	private int itemIdUse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		itemIdUse = intent.getIntExtra(ITEM_USE, 4);
		
		if(Main.metrics.heightPixels > 800) {
			scaling = 2;
		} else {
			scaling = 1;
		}
		
		db = new PokeDB(this);
		
		RelativeLayout list = new RelativeLayout(this);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Main.metrics.heightPixels, LayoutParams.MATCH_PARENT);
		list.setLayoutParams(params);
		
		// Right Info
		ListView listParty = new ListView(this);
		if(PlayerParty.getTotalParty() > 0) {
			ArrayList<String> listOfNameParty = PlayerParty.getNameMonster(db.getReadableDatabase());
			if(listOfNameParty != null) {
				listParty.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfNameParty));
			}
		}
		params = new RelativeLayout.LayoutParams(Main.metrics.heightPixels/2, LayoutParams.MATCH_PARENT);
		params.setMargins(Main.metrics.heightPixels/2, 0, 0, 0);
		listParty.setLayoutParams(params);
		
		// Left Info
		ScrollView kiriScroll = new ScrollView(this);
		LinearLayout kiri = new LinearLayout(this);
		kiri.setOrientation(LinearLayout.VERTICAL);
		kiri.setGravity(Gravity.CENTER_HORIZONTAL);
		kiri.setScrollContainer(true);
		kiri.setScrollbarFadingEnabled(true);
		useItem = new Button(this);
		useItem.setText("Gunakan");
		useItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Item item = new Item(itemIdUse, db.getReadableDatabase());
				PlayerMonster monster = PlayerMonster.getPlayerMonster(PlayerParty.getMonsterIndex(monster_selected));
				
				// Pilih jenis penggunaan
				boolean skip = false; 
				switch(item.GetType()) {
					case 3: monster.setStatusEffect(0);
					case 2:
						if(monster.getHP() <= (monster.getCurHP() + item.GetItemValue())) {
							monster.setCurHP(monster.getHP());
						} else {
							monster.setCurHP(monster.getCurHP() + item.GetItemValue());
						}
						break;
					case 4:
						if(monster.getStatusEffect() == item.GetItemValue()) {
							monster.setStatusEffect(0);
						}
						break;
					case 5:
						switch(item.GetItemValue()) {
							case 1: monster.setHP(monster.getHP() + 1); break;
							case 2: monster.setAttack(monster.getAttack() + 1); break;
							case 3: monster.setDefense(monster.getDefense() + 1); break;
							case 4: monster.setSpeed(monster.getSpeed() + 1); break;
						}
						break;
					case 9:
						Skill skill = new Skill(item.GetItemValue(),db.getReadableDatabase());
						Monster monsterp = new Monster(db.getReadableDatabase(), monster.getMonsterNumber());
						if(monsterp.getElemenNo() == skill.getElemenID()) {
							if(monster.getSkill1() == 0) {
								monster.setSkill1(skill.getID());
								monster.setPPSkill1(skill.getDefaultPP());
							} else if(monster.getSkill2() == 0) {
								monster.setSkill2(skill.getID());
								monster.setPPSkill2(skill.getDefaultPP());
							} else if(monster.getSkill3() == 0) {
								monster.setSkill3(skill.getID());
								monster.setPPSkill3(skill.getDefaultPP());
							} else if(monster.getSkill4() == 0) {
								monster.setSkill4(skill.getID());
								monster.setPPSkill4(skill.getDefaultPP());
							} else {
								skip = true;
							}
						}
				}
				if(!skip) {
					PlayerItem.RemQTY_itemNum(item.GetItemNumb(), 1);
					PlayerMonster.updatePlayerMonster(monster);
				} else {
					Intent intent = new Intent(getApplicationContext(), ChangeSkillMonster.class);
					intent.putExtra(ChangeSkillMonster.INSTALL_MONSTER_ID, monster.getMonsterID());
					intent.putExtra(ChangeSkillMonster.INSTALL_SKILL_ID, item.GetItemValue());
					intent.putExtra(ChangeSkillMonster.INSTALL_USE_ITEM_ID, item.GetItemNumb());
					startActivity(intent);
				}
				finish();
			}
			
		});
		bool = new TextView(this);
		bool.setTextSize(12*scaling);
		if(PlayerParty.getTotalParty() > 0) {
			bool.setText(PlayerParty.getFullInfoMonster(0));
			monster = DrawableManager.getInstance().MonsterPicture(PlayerParty.getMonsterNumberFromIndex(0));
			monster_selected = 0;
		}
		else {
			bool.setText("Kosong.");
			useItem.setVisibility(View.GONE);
		}
		image = new ImageView(this);
		image.setImageBitmap(monster);
		params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins((int) (10*Main.metrics.density),0, 0, 0);
		image.setLayoutParams(params);
		params = new RelativeLayout.LayoutParams(Main.metrics.heightPixels/2, LayoutParams.MATCH_PARENT);
		kiri.setLayoutParams(params);
		kiri.addView(image);
		kiri.addView(bool);
		kiri.addView(useItem);
		kiri.setScrollbarFadingEnabled(true);
		
		listParty.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				bool.setText(PlayerParty.getFullInfoMonster((int) arg3));
				monster = DrawableManager.getInstance().MonsterPicture(PlayerParty.getMonsterNumberFromIndex((int) arg3));
				image.setImageBitmap(monster);
				monster_selected = (int) arg3;
			}
			
		});
		
		kiriScroll.addView(kiri);
		list.addView(kiriScroll);
		list.addView(listParty);
		setContentView(list);
	}
	
	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}
	
	@Override
	public void onBackPressed() {
		UseItem.isUseItem = false;
		super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.monster_party, menu);
		return false;
	}
	

}
