package user.pokeranch.cv.area;

import user.pokeranch.Beginning;
import user.pokeranch.cv.area.bitmap.Hero;
import user.pokeranch.cv.area.building.Home;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


@SuppressLint("ViewConstructor")
public class HomeView extends SurfaceView implements SurfaceHolder.Callback {

	private HomeViewListener mHomeViewListener;
	
	public interface HomeViewListener {
		public void OnHomeViewListener(int id);
	}
	
	private static final String TAG = HomeView.class.getSimpleName();
	public GameLoopThread thread;
	private Matrix matrix = new Matrix();
	public static float density;
	//private DefBackground background; // gambar latar belakang
	private Home home;
	private Hero hero;
	private boolean pause_event;
	private int callAct;
	
	public HomeView(Context context, int screenWidth, int screenHeight) {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		pause_event = false;
		
		home = new Home(screenWidth, screenHeight);
		hero = new Hero();
		hero.setStepped(home.Stepped());
		hero.setOffset(home.getXOffset(), home.getYOffset());
		mHomeViewListener = (HomeViewListener) context;
	}
	public void SetActivityFrom(String activity_name) {
		if(activity_name.equals(Beginning.class.getSimpleName())) {
			hero.setHeroPosition(16, 6);
		} else if(activity_name.equals(CityActivity.class.getSimpleName())) {
			hero.setHeroPosition(14,13);
			hero.Atas();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		Log.d(TAG, "creating surface");
		initThread();
		callAct = 1;
		Log.d(TAG, "surface created");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		Log.d(TAG, "destroying surface");
		releaseThread();
		callAct = 0;
		Log.d(TAG, "surface destroyed");
	}
	public void initThread() {
		if (thread == null || !thread.isAlive()) {
			// jika belom diinisialisasi threadnya atau threadnya sudah tidak hidup lagi, maka
			// instansiasi thread utama
			thread = new GameLoopThread(getHolder(), this);
		}
		thread.setRunning(true);
		thread.start();
	}
	public void releaseThread() {
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				thread = null;
				if(thread == null) retry = false;
			} catch (InterruptedException e) {
			}
		}
	}
	public void render(Canvas canvas) {
		canvas.setMatrix(matrix);
		home.Draw(canvas); 
		hero.Draw3(canvas);
		if(pause_event) canvas.drawColor(0x99000000);
	}
	public void update() {
		hero.dDraw();
		Hero.sumWalkCheck();
	}
	private float x;
	private float y;
	private final float margin_move = 10;
	public boolean onTouchEvent(MotionEvent event) {
		final int actioncode = event.getAction() & MotionEvent.ACTION_MASK;
		float delX;
		float delY;
		float margin_tolerance;
		if(!pause_event) {
			switch (actioncode) {
				case MotionEvent.ACTION_DOWN:
					Log.d(TAG, "down at " + event.getX() + " " + event.getY());
					x = event.getX();
					y = event.getY();
					break;
				case MotionEvent.ACTION_MOVE:
					Log.d(TAG, "move at " + event.getX() + " " + event.getY());
					delX = event.getX() - x;
					delY = event.getY() - y;
					margin_tolerance = abs(delX) + abs(delY);
					margin_tolerance = margin_tolerance > 20 ? margin_tolerance : 20;
					margin_tolerance = margin_tolerance < 60 ? margin_tolerance : 60;
					if((delX > margin_move) && (delY < margin_tolerance) && (delY > -margin_tolerance)) {
						// Kanan
						hero.Kanan();
					} else if((delY < -margin_move) && (delX < margin_tolerance) && (delX > -margin_tolerance)) {
						// Atas
						hero.Atas();
					} else if((delX < -margin_move) && (delY < margin_tolerance) && (delY > -margin_tolerance)) {
						// Kiri
						hero.Kiri();
					} else if((delY > margin_move) && (delX < margin_tolerance) && (delX > -margin_tolerance)) {
						// Bawah
						if(isOutHome()) { mHomeViewListener.OnHomeViewListener(callAct); callAct++; }
						else hero.Bawah();
					}
					break;
				case MotionEvent.ACTION_UP:
					Log.d(TAG, "up at " + event.getX() + " " + event.getY());
					break;		
			}
		}
		return true;
	}
	private float abs(float val) {
		if(val < 0) return -val;
		else return val;
	}
	public void setPauseEvent(boolean pause) {
		pause_event = pause;
	}
	private boolean isOutHome() {
		if(((hero.getX() == 15) || (hero.getX() == 14)) && (hero.getY() == 12)) return true;
		else return false;
	}
}
