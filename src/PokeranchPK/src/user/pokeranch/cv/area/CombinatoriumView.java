/**
 * 
 */
package user.pokeranch.cv.area;

import user.pokeranch.cv.area.bitmap.Hero;
import user.pokeranch.cv.area.building.Combinatorium;
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
public class CombinatoriumView extends SurfaceView implements SurfaceHolder.Callback {

	private static int screenWidth;
	private static int screenHeight;
	public static float density;
	
	private CombinatoriumViewListener mCombinatoriumListener;
	
	public interface CombinatoriumViewListener {
		public void onToCityListener(int callAct);
		public void startInteract(int callAct);
	}
	
	private static final String TAG = CombinatoriumView.class.getSimpleName();
	public GameLoopThread thread;
	private Matrix matrix = new Matrix();

	private boolean pause_event;
	private Hero hero;
	private Combinatorium combinatorium;
	private int callAct;
	
	public CombinatoriumView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);
		setFocusable(true);
		pause_event = false;
		Log.d(TAG,TAG + " initial bitmap...");
		hero = new Hero();
		combinatorium = new Combinatorium(screenWidth, screenHeight);
		hero.setStepped(combinatorium.Stepped());
		hero.setOffset(combinatorium.getXOffset(), combinatorium.getYOffset());
		mCombinatoriumListener = (CombinatoriumViewListener) context;
		Log.d(TAG,TAG + " create successfully");
	}
	
	public static void screenSize(int width, int height) {
		screenWidth = width;
		screenHeight = height;
	}
	
	public void SetActivityFrom(String activity_name) {
		if(activity_name.equals(CityActivity.class.getSimpleName())) {
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
	}

	public void render(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.setMatrix(matrix);
		combinatorium.DrawBackground(canvas); 
		hero.Draw3(canvas);
		if(pause_event) canvas.drawColor(0x99000000);
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
						if(isOutComb()) { mCombinatoriumListener.onToCityListener(callAct); callAct++; }
						else hero.Bawah();
					}
					break;
				case MotionEvent.ACTION_UP:
					Log.d(TAG, "up at " + event.getX() + " " + event.getY());
					if((abs(x - event.getX()) < margin_move) && (abs(y - event.getY()) < margin_move)) {
						Log.d(TAG, "Hero position: (" + hero.getX() + "," + hero.getY() + ")");
						if(isFrontPerson()) { mCombinatoriumListener.startInteract(callAct); callAct++; }
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
	private boolean isOutComb() {
		if(((hero.getX() == 15) || (hero.getX() == 14)) && (hero.getY() == 12)) return true;
		else return false;
	}
	
	private boolean isFrontPerson() {
		if((hero.getX() == 13) && (hero.getY() == 6)) return true; // City
		else
			return false;
	}
	
	public void resetCallAct() {
		callAct = 1;
	}
}
