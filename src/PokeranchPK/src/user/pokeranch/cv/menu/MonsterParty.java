package user.pokeranch.cv.menu;

import java.util.ArrayList;

import user.pokeranch.cv.DrawableManager;
import user.pokeranch.cv.area.OuterView;
import user.pokeranch.cv.area.bitmap.Hero;
import user.pokeranch.models.PlayerMonster;
import user.pokeranch.models.PlayerParty;
import user.pokeranch.models.PokeDB;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MonsterParty extends Activity {

	public static final String IS_SWIM_TRANS = "IS_CAN_SWIM";
	public static final String IS_CAN_CUT = "IS_CAN_CUT";
	public static final String IS_USE_PUSH = "IS_USE_PUSH";
	public static final String TAG = MonsterParty.class.getSimpleName();
	
	private DisplayMetrics metrics;
	private PokeDB db;
	private TextView bool;
	private Bitmap monster;
	private ImageView image;
	private PlayerMonster monsterParty;
	private Button skillSwim;
	private Button skillCut;
	private Button skillPush;
	
	public static boolean usingSkill;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics); // dapetin ukuran layar
		
		db = new PokeDB(this);
		
		RelativeLayout list = new RelativeLayout(this);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(metrics.widthPixels, LayoutParams.MATCH_PARENT);
		list.setLayoutParams(params);
		
		// Right Info
		ListView listParty = new ListView(this);
		if(PlayerParty.getTotalParty() > 0) {
			ArrayList<String> listOfNameParty = PlayerParty.getNameMonster(db.getReadableDatabase());
			if(listOfNameParty != null) {
				listParty.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfNameParty));
			}
		}
		params = new RelativeLayout.LayoutParams(300, LayoutParams.MATCH_PARENT);
		params.setMargins(metrics.widthPixels - 300, 0, 0, 0);
		listParty.setLayoutParams(params);
		
		// Left Info
		ScrollView kiriScroll = new ScrollView(this);
		LinearLayout kiri = new LinearLayout(this);
		kiri.setOrientation(LinearLayout.VERTICAL);
		kiri.setGravity(Gravity.CENTER_HORIZONTAL);
		kiri.setScrollContainer(true);
		kiri.setScrollbarFadingEnabled(true);
		bool = new TextView(this);
		if(PlayerParty.getTotalParty() > 0) {
			bool.setText(PlayerParty.getFullInfoMonster(0));
			monsterParty = PlayerMonster.getPlayerMonster(PlayerParty.getMonsterIndex(0));
			monster = DrawableManager.getInstance().MonsterPicture(PlayerParty.getMonsterNumberFromIndex(0));
		}
		else bool.setText("Kosong.");
		image = new ImageView(this);
		image.setImageBitmap(monster);
		params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins((int) (10*metrics.density),0, 0, 0);
		image.setLayoutParams(params);
		params = new RelativeLayout.LayoutParams(metrics.widthPixels - 300, LayoutParams.MATCH_PARENT);
		kiri.setLayoutParams(params);
		kiri.addView(image);
		kiri.addView(bool);
		
		// Skill........
		skillSwim = new Button(this);
		skillSwim.setText("Swim");
		skillSwim.setOnClickListener(new SwimListener());
		kiri.addView(skillSwim);
		skillCut = new Button(this);
		skillCut.setText("Cut");
		skillCut.setOnClickListener(new CutListener());
		kiri.addView(skillCut);
		skillPush = new Button(this);
		skillPush.setText("Push");
		skillPush.setOnClickListener(new PushListener());
		kiri.addView(skillPush);
		if(PlayerParty.getTotalParty() > 0) {
			check_skill();
		} else {
			skillSwim.setVisibility(View.GONE);
			skillCut.setVisibility(View.GONE);
			skillPush.setVisibility(View.GONE);
		}
		
		kiri.setScrollbarFadingEnabled(true);
		
		listParty.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				bool.setText(PlayerParty.getFullInfoMonster((int) arg3));
				monsterParty = PlayerMonster.getPlayerMonster(PlayerParty.getMonsterIndex((int) arg3));
				monster = DrawableManager.getInstance().MonsterPicture(PlayerParty.getMonsterNumberFromIndex((int) arg3));
				image.setImageBitmap(monster);
				check_skill();
			}
			
		});
		
		kiriScroll.addView(kiri);
		list.addView(kiriScroll);
		list.addView(listParty);
		
		usingSkill = false;
		
		setContentView(list);
	}
	
	private void check_skill() {
		if((monsterParty.getSkill1() != 46) && 
			(monsterParty.getSkill2() != 46) && 
			(monsterParty.getSkill3() != 46) &&
			(monsterParty.getSkill4() != 46)) {
			skillSwim.setVisibility(View.GONE);
		} else skillSwim.setVisibility(View.VISIBLE);
		if((monsterParty.getSkill1() != 59) && 
			(monsterParty.getSkill2() != 59) && 
			(monsterParty.getSkill3() != 59) &&
			(monsterParty.getSkill4() != 59)) {
			skillCut.setVisibility(View.GONE);
		} else skillCut.setVisibility(View.VISIBLE);
		if((monsterParty.getSkill1() != 60) && 
			(monsterParty.getSkill2() != 60) && 
			(monsterParty.getSkill3() != 60) &&
			(monsterParty.getSkill4() != 60)) {
			skillPush.setVisibility(View.GONE);
		} else skillPush.setVisibility(View.VISIBLE);
	}

	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.monster_party, menu);
		return false;
	}
	
	private class SwimListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = getIntent();
			if(intent.getBooleanExtra(IS_SWIM_TRANS, false)) {
				if(Hero.getStatus() == 1) Hero.change_status(3);
				else Hero.change_status(1);
				usingSkill = true;
				finish();
			} else {
				Toast.makeText(getApplicationContext(), "Maaf, tidak dapat menggunakan skill ini sekarang.", Toast.LENGTH_LONG).show();
			}
		}
		
	}
	
	private class CutListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			Intent intent = getIntent();
			if(intent.getBooleanExtra(IS_CAN_CUT, false)) {
				OuterView.isUseCut = true;
				finish();
			} else {
				Toast.makeText(getApplicationContext(), "Maaf, tidak dapat menggunakan skill ini sekarang.", Toast.LENGTH_LONG).show();
			}
		}
		
	}
	
	private class PushListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			Intent intent = getIntent();
			if(intent.getBooleanExtra(IS_USE_PUSH, false)) {
				OuterView.isUsePush = true;
				finish();
			} else {
				Toast.makeText(getApplicationContext(), "Maaf, tidak dapat menggunakan skill ini sekarang.", Toast.LENGTH_LONG).show();
			}
		}
		
	}

}
