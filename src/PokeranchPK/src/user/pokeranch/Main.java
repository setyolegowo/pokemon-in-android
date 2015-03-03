package user.pokeranch;

import java.util.ArrayList;
import user.pokeranch.cv.DrawableManager;
import user.pokeranch.cv.area.CityArea;
import user.pokeranch.cv.area.HomeActivity;
import user.pokeranch.cv.area.OuterArea;
import user.pokeranch.cv.area.ShoreArea;
import user.pokeranch.cv.area.bitmap.Hero;
import user.pokeranch.cv.area.bitmap.MonsterArea;
import user.pokeranch.cv.area.bitmap.MonsterLaut;
import user.pokeranch.cv.area.building.Combinatorium;
import user.pokeranch.cv.area.building.Home;
import user.pokeranch.cv.area.building.Shop;
import user.pokeranch.cv.area.building.Stadium;
import user.pokeranch.cv.battle.BattleOuterArea;
import user.pokeranch.cv.main.Credits;
import user.pokeranch.cv.main.EmptyUserDialog;
import user.pokeranch.cv.main.Help;
import user.pokeranch.cv.main.LoadUser;
import user.pokeranch.cv.main.LoadUser.LoadUserListener;
import user.pokeranch.cv.main.MainMenu;
import user.pokeranch.cv.main.NewUser;
import user.pokeranch.cv.main.NewUser.OnNewUserListener;
import user.pokeranch.cv.main.TempDataView;
import user.pokeranch.models.Player;
import user.pokeranch.models.PlayerItem;
import user.pokeranch.models.PlayerMonster;
import user.pokeranch.models.PlayerParty;
import user.pokeranch.models.PokeDB;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Main extends FragmentActivity implements OnNewUserListener, LoadUserListener {

	public final static String EXTRA_MESSAGE = "pokeranch";
	public static Player player;
	public static DisplayMetrics metrics;
	public static int OneMove;
	public static int screenWidthPixels;
	public static int screenHeightPixels;
	private static final String TAG = Main.class.getSimpleName();
	
	public void onUserDefined(String dialog) {
		if((dialog == "") || (dialog == null)) {
			NewUser.message = "Not accepted input";
			DialogFragment newFragment = new NewUser();
	        newFragment.show(getSupportFragmentManager(), "newuser");
		} else {
			
			if(dialog.matches("[a-zA-Z]+") && dialog.length() < 20) {
				PokeDB db = new PokeDB(this);
				if(Player.getPlayer(db.getReadableDatabase(), dialog) == null) {
					NewUser.message = "";
					Intent intent = new Intent(this, Beginning.class);
					intent.putExtra(EXTRA_MESSAGE, Main.class.getSimpleName());
			    	intent.putExtra(Beginning.NEW_USER_MESSAGE, dialog);
			    	finish();
			    	startActivity(intent);
				} else {
					NewUser.message = "Player name was registered";
					DialogFragment newFragment = new NewUser();
			        newFragment.show(getSupportFragmentManager(), "newuser");
				}
				db.close();
			} else {
				NewUser.message = "Not accepted input";
				DialogFragment newFragment = new NewUser();
		        newFragment.show(getSupportFragmentManager(), "newuser");
			}
		}
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // INIT DATA STATIC
        metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics); // dapetin ukuran layar
		DrawableManager.initInstance(this);
		DrawableManager.getInstance().initAllBitmap();
		OneMove = (int) (20*metrics.density);
		Hero.initHero();
		BattleOuterArea.blackout = false;
		MonsterArea.init();
		MonsterLaut.init();
		CityArea.initCityArea();
		ShoreArea.init();
		OuterArea.init();
		Combinatorium.init();
		Home.init();
		Shop.init();
		Stadium.init();
		Log.d(TAG , "METRICS INFORMATION: Density = " + metrics.density + "; Density DPI = " + metrics.densityDpi + 
				"; Height Pixel = " + metrics.heightPixels + "; Width Pixel = " + metrics.widthPixels +
				"; Scaled Density = " + metrics.scaledDensity + "; XDPI = " + metrics.xdpi + 
				"; YDPI = " + metrics.ydpi);
        
        // SUKA LUPA
		setContentView(R.layout.fragment);
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ExampleFragment
            MainMenu firstFragment = new MainMenu();
            
            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());
            
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
	        case R.id.action_credits:
	            doCredit();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
        }
    }
    
    public void doCredit()
    {
    	Intent intent = new Intent(this, Credits.class);
    	intent.putExtra(EXTRA_MESSAGE, new String("Credits"));
    	startActivity(intent);
    }
    public void showHelp(View view)
    {
    	Intent intent = new Intent(this, Help.class);
    	startActivity(intent);
    }
    public void showBattleTemp(View view)
    {
    	Intent intent = new Intent(this, BattleOuterArea.class);
    	startActivity(intent);
    }
    
    public void showAreaTemp(View view)
    {
    	Intent intent=new Intent(this, HomeActivity.class);
    	intent.putExtra(EXTRA_MESSAGE, Main.class.getSimpleName());
    	startActivity(intent);
    	finish();
    }
    public void showDataTemp(View view)
    {
    	Intent intent = new Intent(this, TempDataView.class);
    	startActivity(intent);
    }
    public void new_user(View view)
    {
    	DialogFragment newFragment = new NewUser();
        newFragment.show(getSupportFragmentManager(), "newuser");
    }
    public void load_user(View view) {
        PokeDB db = new PokeDB(this);
        
        if(Player.IsEmpty(db.getReadableDatabase())) {
        	DialogFragment newFragment = new EmptyUserDialog();
        	newFragment.show(getSupportFragmentManager(), "emptyuser");
        } else {
        	LoadUser.listPlayerReg = new ArrayList<String>();
        	LoadUser.listPlayerReg.addAll(Player.getArrayName(db.getReadableDatabase()));
        	DialogFragment newFragment = new LoadUser();
        	newFragment.show(getSupportFragmentManager(), "loaduser");
        }
        db.close();
    }

	@Override
	public void onLoadUserLitener(String name) {
		// TODO Auto-generated method stub
		PokeDB db = new PokeDB(this);
		player = Player.getPlayer(db.getReadableDatabase(), name);
		PlayerItem.initPlayerItem(player.getID(), db.getReadableDatabase());
		PlayerMonster.initPlayerMonster(db.getReadableDatabase(), player.getID());
		PlayerParty.initPlayerParty(db.getReadableDatabase(), player.getID());
		db.close();
		
		Intent intent=new Intent(this, HomeActivity.class);
    	intent.putExtra(EXTRA_MESSAGE, Main.class.getSimpleName());
    	startActivity(intent);
    	finish();
	}
}
