package user.pokeranch;

import java.util.ArrayList;

import user.pokeranch.cv.area.HomeActivity;
import user.pokeranch.models.Player;
import user.pokeranch.models.PlayerItem;
import user.pokeranch.models.PlayerMonster;
import user.pokeranch.models.PlayerParty;
import user.pokeranch.models.PokeDB;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Beginning extends Activity {
	
	public final static String NEW_USER_MESSAGE = "poke_new_user";
	
	private PokeDB db;

	private Button buttonAgree;

	private TextView textRun;

	private int message_index;

	private ArrayList<String> message_prof;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		db = new PokeDB(this);
		
		Intent intent = getIntent();
		String message = intent.getStringExtra(NEW_USER_MESSAGE);
		MakeNewPlayer(message);
		
		message_prof = new ArrayList<String>();
		message_prof.add("Halo. Sangat senang bertemu denganmu.");
		message_prof.add("Selamat datang di dunia PokeRanch.");
		message_prof.add("Nama saya Virtue.");
		message_prof.add("Oh ya, saya biasanya disebut Poke Profesor.");
		message_prof.add("Sebelum bergerak lebih jauh,\napakah ini adalah petualangan pertama kamu?");
		message_prof.add("Di dunia ini, terdapat makhluk hidup dan\nmemiliki habitatnya sendiri yang disebut Poke.");
		message_prof.add("Orang-orang disini hidup bersama dengan\ndamai bersama Poke.");
		message_prof.add("Saat orang-orang bermain bersama-sama,\ndan sekali-kali bekerja besama dengannya.");
		message_prof.add("Beberapa orang menggunakan Poke untuk\nbertarung dan membentuk ikatan kuat dengannya.");
		message_prof.add("Apa yang saya lakukan?");
		message_prof.add("Saya meneliti Poke secara terus menerus\nselama hidup saya.");
		message_prof.add("Sekarang, kenapa kamu tidak menceritakan\nsedikit tentang dirimu?");
		message_prof.add("Oh, tentu saja.\nNama kamu " + message + " kan?");
		message_prof.add("Wah, nama yang sangaaaaaaaaat bagus.");
		message_prof.add("Nah, semua orang di dunia ini tahu\nbahwa");
		message_prof.add("terdapat seorang Trainer Poke yang\nsangat kuat.");
		message_prof.add("Kamu dapat mencari tahu siapa dia setelah kamu\nmerasa mampu untuk melawan dia di stadium.");
		message_prof.add("Dalam perjalananmu nanti, kamu akan\nbertemu Poke-Poke yang tak terhitung jumlahnya.");
		message_prof.add("Saya percaya bahwa kamu akan membantu saya\nuntuk mengumpulkan semua Poke yang ada.");
		message_prof.add("Sebelum mulai, kamu harus membeli dulu\ntelor monster yang ada di toko.");
		message_prof.add("Setelah itu, kamu harus bergerak beberapa\nlangkah di dalam kota.");
		message_prof.add("Setelah telor monstermu menetas, maka\nkamu akan bebas berpetualang di dunia Poke.");
		message_prof.add("Satu hal lagi, mungkin ini adalah pertemuan\npertama dan terakhir aku dengan kamu sebagai trainer baru.");
		message_prof.add("Huahahaha.......\nv(^_^)v");
		message_prof.add("Baiklah, sekarang waktunya kamu Berpetualang.");
		
		setContentView(R.layout.activity_beginning);
		
		buttonAgree = (Button) findViewById(R.id.button_agree);
		buttonAgree.setVisibility(View.INVISIBLE);
		
		message_index = 0;
		textRun = (TextView) findViewById(R.id.textView1);
		textRun.setText(message_prof.get(message_index));
		textRun.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(message_index < message_prof.size() - 1) {
					message_index++;
					textRun.setText(message_prof.get(message_index));
				} else {
					if(buttonAgree.getVisibility() != View.VISIBLE) buttonAgree.setVisibility(View.VISIBLE);
				}
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.beginning, menu);
		return false;
	}
	
	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}
	
	private void MakeNewPlayer(String name) {
		Main.player = new Player(db.getWritableDatabase(),name);
		PlayerItem.initPlayerItem(Main.player.getID(), db.getReadableDatabase());
		PlayerMonster.initPlayerMonster(db.getReadableDatabase(), Main.player.getID());
		PlayerParty.initPlayerParty(db.getReadableDatabase(), Main.player.getID());
	}
	
	@Override
	public void onBackPressed() {
		toHome();
	}
	
	public void toHome(View view) {
		toHome();
	}
	
	private void toHome() {
		Intent intent = new Intent(this, HomeActivity.class);
		intent.putExtra(Main.EXTRA_MESSAGE, Beginning.class.getSimpleName());
		startActivity(intent);
		finish();
	}

}
