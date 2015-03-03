package user.pokeranch.cv.battle;

import java.util.ArrayList;

import user.pokeranch.cv.DrawableManager;
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
import android.view.View.OnClickListener;
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

public class BattleChangeMonster extends Activity {

	private DisplayMetrics metrics;
	private PokeDB db;
	private TextView bool;
	private Bitmap monster;
	private ImageView image;
	@SuppressWarnings("unused")
	private int monster_party_active;
	public static PlayerMonster monsterParty;
	
	public static boolean isChange = false;
	public static int monster_party_number;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		monster_party_active = intent.getIntExtra(BattleOuterArea.MONSTER_PARTY_ACTIVE, 0);
		
		isChange = false;
		
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
		
		// USE IT
		Button useMonster = new Button(this);
		useMonster.setText("Ganti Monster");
		useMonster.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(monsterParty.getCurHP() > 0) {
					isChange = true;
					finish();
				} else {
					Toast.makeText(getApplicationContext(), "Tidak dapat mengganti ke monster ini.", Toast.LENGTH_SHORT).show();
				}
			}
			
		});
		kiri.addView(useMonster);
		
		kiri.setScrollbarFadingEnabled(true);
		
		listParty.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				bool.setText(PlayerParty.getFullInfoMonster((int) arg3));
				monster_party_number = (int) arg3;
				monsterParty = PlayerMonster.getPlayerMonster(PlayerParty.getMonsterIndex((int) arg3));
				monster = DrawableManager.getInstance().MonsterPicture(PlayerParty.getMonsterNumberFromIndex((int) arg3));
				image.setImageBitmap(monster);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.monster_party, menu);
		return false;
	}

}
