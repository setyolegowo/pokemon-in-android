package user.pokeranch.cv.main;

import user.pokeranch.R;
import user.pokeranch.models.Player;
import user.pokeranch.models.PlayerItem;
import user.pokeranch.models.PlayerMonster;
import user.pokeranch.models.PlayerParty;
import user.pokeranch.models.PokeDB;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class TempDataView extends Activity {

	public final static String EXTRA_MESSAGE = "pokeranch";
	
	TextView textView;
	PokeDB db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_temp_data_view);
		
		db = new PokeDB(this);
		
		// Element
		String itm = Player.getAllPlayerData(db.getReadableDatabase());
	    
	    // Create the text view
	    textView = (TextView) findViewById(R.id.textView1);
	    textView.setText((CharSequence) itm);
	    
	    // Status Effect
	    itm = PlayerItem.GetAllData(db.getReadableDatabase());
	    
	    textView = (TextView) findViewById(R.id.textView3);
	    textView.setText((CharSequence) itm);
	    
	    // Items
	    itm = PlayerMonster.getSomeDataPM(db.getReadableDatabase());
	    
	    textView = (TextView) findViewById(R.id.textView5);
	    textView.setText((CharSequence) itm);
	    
	    // Skill
	    itm = PlayerParty.GetData(db.getReadableDatabase());
	    
	    textView = (TextView) findViewById(R.id.textView7);
	    textView.setText((CharSequence) itm);
//	    
//	    // Player
//	    
//	    itm = Monster.GetData(db.getReadableDatabase());
//	    
//	    textView = (TextView) findViewById(R.id.textView9);
//	    textView.setText((CharSequence) itm);
//	    
//	    // tolong buat text view yang baru (textView11)
//	    itm = PlayerMonster.GetData(db.getReadableDatabase());
//	    
//	    textView = (TextView) findViewById(R.id.textView11);
//	    textView.setText((CharSequence) itm);
	    
	    db.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return false;
	}

}
