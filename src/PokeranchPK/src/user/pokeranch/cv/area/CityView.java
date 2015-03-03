/**
 * 
 */
package user.pokeranch.cv.area;

import user.pokeranch.Main;
import user.pokeranch.cv.area.bitmap.Hero;
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
public class CityView extends SurfaceView implements SurfaceHolder.Callback {

	public static float density;
	private static int screenWidth;
	private static int screenHeight;
	
	private CityViewListener mCityViewListener;
	
	public interface CityViewListener {
		public void onToHomeListener(int callAct);
		public void onToShopListener(int callAct);
		public void onToCombinatoriumListener(int callAct);
		public void onToStadiumListener(int callAct);
		public void onToOuterAreaListener(int callAct);
		public void onToSeaListener(int callAct);
	}
	
	private static final String TAG = CityView.class.getSimpleName();
	public GameLoopThread thread;
	private Matrix matrix = new Matrix();

	private boolean pause_event;
	private Hero hero;
	private CityArea city;
	private int callAct;
	
	public CityView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);
		setFocusable(true);
		pause_event = false;
		Log.d(TAG,TAG + " initial bitmap...");
		city = new CityArea();
		hero = new Hero();
		hero.setStepped(city.Stepped());
		mCityViewListener = (CityViewListener) context;
		Log.d(TAG,TAG + " create successfully");
	}
	
	public static void screenSize(int width, int height) {
		screenWidth = width;
		screenHeight = height;
	}
	
	public void SetActivityFrom(String activity_name) {
		if(activity_name.equals(HomeActivity.class.getSimpleName())) {
			hero.setHeroPosition( 5, 5);
			hero.Bawah();
		} else if(activity_name.equals(ShopActivity.class.getSimpleName())) {
			hero.setHeroPosition(23,11);
			hero.Bawah();
		} else if(activity_name.equals(CombinatoriumActivity.class.getSimpleName())) {
			hero.setHeroPosition(10,13);
			hero.Bawah();
		} else if(activity_name.equals(StadiumActivity.class.getSimpleName())) {
			hero.setHeroPosition(13,3);
			hero.Bawah();
		} else if(activity_name.equals(OuterActivity.class.getSimpleName())) {
			hero.setHeroPosition(19,1);
			hero.Bawah();
		} else if(activity_name.equals(ShoreActivity.class.getSimpleName())) {
			hero.setHeroPosition(3,1);
			hero.Bawah();
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
		city.ChangeOffset(hero.getRealX(Main.OneMove), hero.getRealY(Main.OneMove), screenWidth, screenHeight);
		hero.setOffset(city.getxOffset(), city.getyOffset());
		hero.dDraw();
	}

	public void render(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.setMatrix(matrix);
		city.DrawBackground(canvas); 
		hero.Draw3(canvas);
		city.DrawForeground(canvas);
		if(Main.player.getDayOrNight() == 1) {
			canvas.drawColor(0x80000000);
			if(pause_event) canvas.drawColor(0x30000000);
		} else {
			if(pause_event) canvas.drawColor(0x99000000);
		}
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
						if(callAct != 1) resetCallAct();
					} else if((delY < -margin_move) && (delX < margin_tolerance) && (delX > -margin_tolerance)) {
						// Atas
						switch(isToChangeView()) {
							case 1: 
								mCityViewListener.onToHomeListener(callAct); callAct++;
								break;
							case 2:
								mCityViewListener.onToStadiumListener(callAct); callAct++;
								break;
							case 3:
								mCityViewListener.onToOuterAreaListener(callAct); callAct++;
								break;
							case 4:
								mCityViewListener.onToSeaListener(callAct); callAct++;
								break;
							case 5:
								mCityViewListener.onToShopListener(callAct); callAct++;
								break;
							case 6:
								mCityViewListener.onToCombinatoriumListener(callAct); callAct++;
								break;
							default: 
								hero.Atas(); 
								break; 
						}
					} else if((delX < -margin_move) && (delY < margin_tolerance) && (delY > -margin_tolerance)) {
						// Kiri
						hero.Kiri();
						if(callAct != 1) resetCallAct();
					} else if((delY > margin_move) && (delX < margin_tolerance) && (delX > -margin_tolerance)) {
						// Bawah
						hero.Bawah();
						if(callAct != 1) resetCallAct();
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
	private int isToChangeView() {
		if((hero.getX() == 5) && (hero.getY() == 6)) return 1; // HOME
		else if((hero.getX() == 13) && (hero.getY() == 4)) return 2; // ke stadium
		else if((hero.getX() == 19) && (hero.getY()==2)) return 3; // KE OuterArea
		else if(((hero.getX() == 3) || (hero.getX() == 2)) && (hero.getY()==2)) return 4; // Ke Pantai
		else if((hero.getX() == 23) && (hero.getY() == 12)) return 5; // Ke shop
		else if((hero.getX() == 10) && (hero.getY() == 14)) return 6; // Ke kombinatorium
		else 
			return 0;
	}
	public void resetCallAct() {
		callAct = 1;
	}
}
