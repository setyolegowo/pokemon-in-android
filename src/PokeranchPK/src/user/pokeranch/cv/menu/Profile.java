package user.pokeranch.cv.menu;

import user.pokeranch.Main;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Profile extends Activity {

	private DisplayMetrics metrics;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics); // dapetin ukuran layar
		
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(metrics.widthPixels, metrics.heightPixels);
		layout.setLayoutParams(params);
		
		TextView name = new TextView(this);
		name.setText("Player Name : " + Main.player.getName());
		TextView TotalPlayTime = new TextView(this);
		TotalPlayTime.setText("Total Play Time : " + Main.player.getTotalTimePlay());
		TextView DayOrNight = new TextView(this);
		DayOrNight.setText("Day Or Night : " + Main.player.getDayOrNight());
		TextView curMoney = new TextView(this);
		curMoney.setText("Current Money : " + Main.player.getCurMoney());
		TextView TotalMoney = new TextView(this);
		TotalMoney.setText("Total get Money : " + Main.player.getTotalGetMoney());
		TextView win = new TextView(this);
		win.setText("Total battle win : " + Main.player.getWin());
		TextView lose = new TextView(this);
		lose.setText("Total battle lose : " + Main.player.getLose());
		TextView score = new TextView(this);
		score.setText("Score stadium : " + Main.player.getScoreStadium());
		
		layout.addView(name);
		layout.addView(TotalPlayTime);
		layout.addView(DayOrNight);
		layout.addView(curMoney);
		layout.addView(TotalMoney);
		layout.addView(win);
		layout.addView(lose);
		layout.addView(score);
		
		setContentView(layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.profile, menu);
		return false;
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}

}
