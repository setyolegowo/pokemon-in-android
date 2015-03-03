/**
 * 
 */
package user.pokeranch.cv.area;

import user.pokeranch.cv.area.bitmap.Hero;
import user.pokeranch.cv.area.building.Stadium;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author Setyo Lego
 *
 */
public class StadiumView extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = StadiumView.class.getSimpleName();
	private Matrix matrix = new Matrix();
	public GameLoopThread thread;
	private boolean pause_event;
	private Hero hero;
	private static int screenWidth;
	private static int screenHeight;
	public static float density;
	private Stadium stadium;
	private StadiumViewListener mStadiumViewListener;
	private int callAct;
	
	public interface StadiumViewListener {
		public void OnToCityListener(int callAct);
		public void OnToInteract(int callAct);
	}

	public StadiumView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);
		setFocusable(true);
		
		pause_event = false;
		Log.d(TAG,TAG + " initial bitmap...");
		hero = new Hero();
		stadium = new Stadium(screenWidth, screenHeight);
		hero.setStepped(stadium.Stepped());
		hero.setOffset(stadium.getXOffset(), stadium.getYOffset());
		mStadiumViewListener = (StadiumViewListener) context;
		Log.d(TAG,TAG + " create successfully");
	}
	
	public static void changeScreen(int width, int height) {
		screenWidth = width;
		screenHeight = height;
	}
	
	public void SetActivityFrom(String activity_name) {
		if(activity_name.equals(CityActivity.class.getSimpleName())) {
			hero.setHeroPosition( 14, 13);
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
		initThread();
		callAct = 1;
		Log.d(TAG, "surface created");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		releaseThread();
		callAct = 0;
		Log.d(TAG, "surface destroyed");
	}
	
	private void initThread() {
		// TODO Auto-generated method stub
		if (thread == null || !thread.isAlive()) {
			// jika belom diinisialisasi threadnya atau threadnya sudah tidak hidup lagi, maka
			// instansiasi thread utama
			thread = new GameLoopThread(getHolder(), this);
			thread.start();
		}
		thread.setRunning(true);
	}
	
	private void releaseThread() {
		// TODO Auto-generated method stub
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
				thread = null;
			} catch (InterruptedException e) {
			}
		}
	}
	
	public void update() {
		// TODO Auto-generated method stub
		hero.dDraw();
		Hero.sumWalkCheck();
	}

	public void render(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.setMatrix(matrix);
		stadium.Draw(canvas);
		hero.Draw3(canvas);
		if(pause_event) canvas.drawColor(0x99000000);
	}
	
	private float x;
	private float y;
	private final float margin_move = 10;
	
	@Override
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
						hero.Atas();
					} else if((delX < -margin_move) && (delY < margin_tolerance) && (delY > -margin_tolerance)) {
						// Kiri
						hero.Kiri();
					} else if((delY > margin_move) && (delX < margin_tolerance) && (delX > -margin_tolerance)) {
						// Bawah
						if(isToChangeView() == 1) { mStadiumViewListener.OnToCityListener(callAct); callAct++; }
						hero.Bawah();
					}
					break;
				case MotionEvent.ACTION_UP:
					Log.d(TAG, "up at " + event.getX() + " " + event.getY());
					if((abs(x - event.getX()) < margin_move) && (abs(y - event.getY()) < margin_move)) {
						Log.d(TAG, "Hero position: (" + hero.getX() + "," + hero.getY() + ")");
						if(isFrontDoor() == 1) { mStadiumViewListener.OnToInteract(callAct); callAct++; }
					}
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
	
	private int isToChangeView() {
		if(((hero.getX() == 14) || (hero.getX() == 15)) && (hero.getY() == 12)) return 1; // City
		else
			return 0;
	}
	
	private int isFrontDoor() {
		if((hero.getX() >= 12) && (hero.getX() <= 14) && (hero.getY() == 6)) return 1; // Interaction
		else
			return 0;
	}
	
	public void resetCallAct() {
		callAct = 1;
	}

}
