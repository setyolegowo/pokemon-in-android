package user.pokeranch.cv.menu;

import java.util.ArrayList;

import user.pokeranch.Main;
import user.pokeranch.models.PlayerItem;
import user.pokeranch.models.PlayerMonster;
import user.pokeranch.models.PokeDB;
import user.pokeranch.models.Skill;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ChangeSkillMonster extends Activity {
	
	public static final String INSTALL_USE_ITEM_ID = "INSTALL_USE_ITEM_ID";
	public static final String INSTALL_SKILL_ID = "INSTALL_SKILL_ID";
	public static final String INSTALL_MONSTER_ID = "INSTALL_MONSTER_ID";
	private PokeDB db;
	private TextView bool;
	
	private Skill skill;
	private Skill skill1;
	private Skill skill2;
	private Skill skill3;
	private Skill skill4;
	
	private int index_skill_use;
	private PlayerMonster monster;
	private int item_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		db = new PokeDB(this);
		
		Intent intent = getIntent();
		skill = new Skill(intent.getIntExtra(INSTALL_SKILL_ID, 1), db.getReadableDatabase());
		monster = PlayerMonster.getPlayerMonster(intent.getIntExtra(INSTALL_MONSTER_ID, 0));
		skill1 = new Skill(monster.getSkill1(), db.getReadableDatabase());
		skill2 = new Skill(monster.getSkill2(), db.getReadableDatabase());
		skill3 = new Skill(monster.getSkill3(), db.getReadableDatabase());
		skill4 = new Skill(monster.getSkill4(), db.getReadableDatabase());
		item_id = intent.getIntExtra(INSTALL_USE_ITEM_ID, 20);
		
		RelativeLayout list = new RelativeLayout(this);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Main.metrics.heightPixels, LayoutParams.MATCH_PARENT);
		list.setLayoutParams(params);
		
		// Right Info
		ListView listSkill = new ListView(this);
		ArrayList<String> listOfNameParty = new ArrayList<String>();
		listOfNameParty.add(skill1.getName());
		listOfNameParty.add(skill2.getName());
		listOfNameParty.add(skill3.getName());
		listOfNameParty.add(skill4.getName());
		listSkill.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfNameParty));
		params = new RelativeLayout.LayoutParams(300, LayoutParams.MATCH_PARENT);
		params.setMargins(Main.metrics.heightPixels/2, 0, 0, 0);
		listSkill.setLayoutParams(params);
		
		// Left Info
		ScrollView kiriScroll = new ScrollView(this);
		LinearLayout kiri = new LinearLayout(this);
		kiri.setOrientation(LinearLayout.VERTICAL);
		kiri.setGravity(Gravity.CENTER_HORIZONTAL);
		kiri.setScrollContainer(true);
		kiri.setScrollbarFadingEnabled(true);
		bool = new TextView(this);
		bool.setText(skill1.getDescription());
		index_skill_use = 0;
		Button install = new Button(this);
		install.setText("Pasang");
		install.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				switch(index_skill_use) {
					case 0: monster.setSkill1(skill.getID());
					monster.setPPSkill1(skill.getDefaultPP());
					break;
					case 1: monster.setSkill2(skill.getID());
					monster.setPPSkill2(skill.getDefaultPP());
					break;
					case 2: monster.setSkill3(skill.getID());
					monster.setPPSkill3(skill.getDefaultPP());
					break;
					case 3: monster.setSkill4(skill.getID());
					monster.setPPSkill4(skill.getDefaultPP());
					break;
				}
				PlayerItem.RemQTY_itemNum(item_id, 1);
				PlayerMonster.updatePlayerMonster(monster);
				finish();
			}
			
		});
		params = new RelativeLayout.LayoutParams(Main.metrics.heightPixels/2, LayoutParams.MATCH_PARENT);
		kiri.setLayoutParams(params);
		kiri.addView(bool);
		kiri.addView(install);
		kiri.setScrollbarFadingEnabled(true);
		
		listSkill.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch((int) arg3) {
					case 0: bool.setText(skill1.getDescription()); break;
					case 1: bool.setText(skill2.getDescription()); break;
					case 2: bool.setText(skill3.getDescription()); break;
					case 3: bool.setText(skill4.getDescription()); break;
				}
				index_skill_use = (int) arg3;
			}
		});
		
		kiriScroll.addView(kiri);
		list.addView(kiriScroll);
		list.addView(listSkill);
		setContentView(list);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.change_skill_monster, menu);
		return false;
	}
	
	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}

}
