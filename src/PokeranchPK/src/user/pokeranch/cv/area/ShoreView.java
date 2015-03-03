/**
 * 
 */
package user.pokeranch.cv.area;

import java.util.ArrayList;
import java.util.Random;

import user.pokeranch.Main;
import user.pokeranch.cv.area.bitmap.Hero;
import user.pokeranch.cv.area.bitmap.MonsterArea;
import user.pokeranch.cv.area.bitmap.MonsterLaut;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author Setyo Lego
 *
 */
public class ShoreView extends SurfaceView implements SurfaceHolder.Callback {
	
	public interface ShoreListener {
		public void OnToCityListener(int callAct);

		public void onMeetMonster(int callAct);
	}

	private static final String TAG = ShoreView.class.getSimpleName();
	public GameLoopThread thread;
	private Matrix matrix = new Matrix();

	private static int screenWidth;
	private static int screenHeight;
	private boolean pause_event;

	private Hero hero;
	private ShoreArea shore;
	private ShoreListener mShoreListener;
	private int callAct;
	
	Paint paint = new Paint();
	private boolean KetemuMonster;
	public static boolean isUseTorch;
	
	public ShoreView(Context context) {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		
		pause_event = false;
		Log.d(TAG,TAG + " initial bitmap...");
		hero = new Hero();
		shore = new ShoreArea();
		hero.setStepped(shore.Stepped());
		hero.setSurfed(shore.Surfed());
		mShoreListener = (ShoreListener) context;
		paint.setColor(Color.BLACK);
		RandomizeMonster();
		isUseTorch = false;
		Log.d(TAG,TAG + " create successfully");
	}
	
	public static void screenSize(int width, int height) {
		screenWidth = width; 
		screenHeight = height;
	}
	
	public void SetActivityFrom(String activity_name) {
		if(activity_name.equals(CityActivity.class.getSimpleName())) {
			hero.setHeroPosition( 23, 5);
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
		Log.d(TAG, TAG + "surface created");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		releaseThread();
		callAct = 0;
		Log.d(TAG, TAG + "surface destroyed");
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
		if(!pause_event) {
			NemuMonsterGaYa();
			updatemonsterlist(hero.getX(), hero.getY());
			shore.ChangeOffset(hero.getRealX(Main.OneMove), hero.getRealY(Main.OneMove), screenWidth, screenHeight);
			hero.setOffset(shore.getxOffset(), shore.getyOffset());
			hero.dDraw();
			if(Hero.getStatus() == 3) ChangeToLand();
			Hero.sumWalkCheck();
		}
	}

	public void render(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.setMatrix(matrix);
		shore.DrawBackground(canvas);
		drawmonsterarea(canvas);
		
		hero.Draw3(canvas);
		if(Main.player.getDayOrNight() == 1) {
			drawNightMode(canvas);
			canvas.drawColor(0x80000000);
			if(pause_event) canvas.drawColor(0x30000000);
		} else {
			if(pause_event) canvas.drawColor(0x99000000);
		}
	}
	
	public void NemuMonsterGaYa(){
		int x=hero.getX(), y=hero.getY();
		if(findInMonsterList(x, y)){
			this.KetemuMonster=true;
			delMonsterList(x, y);
		} else if(findInMonsterList(x, (y-2))){
			this.KetemuMonster=true;
			delMonsterList(x, (y-2));
		}else if(findInMonsterList((x + 1), (y-1))){
			this.KetemuMonster=true;
			delMonsterList((x + 1), (y-1));
		} else if(findInMonsterList((x - 1), (y-1))){
			this.KetemuMonster=true;
			delMonsterList((x - 1), (y-1));
		}
		if(this.KetemuMonster) {
			mShoreListener.onMeetMonster(callAct);
			callAct++;
			this.KetemuMonster = false;
		}
	}
	
	private float x;
	private float y;
	private final float margin_move = 10;
	private boolean isHadBeenRandomized;
//	private boolean isHadBeenRandomized = false;
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
						if(isToChangeView() == 1) { mShoreListener.OnToCityListener(callAct); callAct++; }
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
	private int isToChangeView() {
		if((hero.getX() == 23) && (hero.getY() == 4)) return 1; // Ke CITY
		else 
			return 0;
	}
	private void drawNightMode(Canvas canvas) {
		int a = hero.getRealX(Main.OneMove) + Main.OneMove + 20;
		int b = hero.getRealY(Main.OneMove) - hero.getSpriteHeight() - 10;
		int c = hero.getRealX(Main.OneMove) - 20;
		int d = hero.getRealY(Main.OneMove) + 10;
		canvas.drawRect(0, 0, a, b, paint);
		canvas.drawRect(0, b, c, screenHeight, paint);
		canvas.drawRect(c, d, screenWidth, screenHeight, paint);
		canvas.drawRect(a, 0, screenWidth, d, paint);
	}
	/**
	 * Digunakan jika dan hanya jika setelah penggunaan skill swim
	 * Jangan digunakan untuk RENDER
	 */
	public void trans_hero() {
		if(Hero.getStatus() == 3) hero.Kiri();
	}
	
	public boolean isCanTransSwim() {
		Log.d("CHECK IsCanTransSwim", "Player: (" + hero.getX() + "," + hero.getY() + "). GetPointer: " + hero.getPointer2());
		if(hero.getPointer2() == 2) {
			boolean[][] surfed = shore.Surfed();
			boolean[][] stepped = shore.Stepped();
			Log.d("CHECK IsCanTransSwim", "IsCanSwimmed: " + surfed[hero.getY()][hero.getX() - 1]);
			if((surfed[hero.getY()][hero.getX() - 1]) && (stepped[hero.getY()][hero.getX()])) {
				return true;
			} else return false;
		} else 
			return false;
	}
	
	private void ChangeToLand() {
		if(hero.getPointer2() == 4) {
			boolean[][] surfed = shore.Surfed();
			boolean[][] stepped = shore.Stepped();
			if((surfed[hero.getY()][hero.getX()]) && (stepped[hero.getY()][hero.getX() + 1])) {
				Hero.change_status(1);
			}
		}
	}
	
	private static ArrayList<MonsterLaut> MA = new ArrayList<MonsterLaut>();
	
	public void RandomizeMonster(){
		int MaxMonster=5;
		int tempx,tempy;
		
		
		if(!isHadBeenRandomized && MA.size() < 10){
			Random rand = new Random();
			for (int i = 0; i < MaxMonster; i++) {
				do{
					tempx=rand.nextInt(27)+1;
					tempy=rand.nextInt(16)+1;
				}while(!isValidPositionsForMonster(tempx, tempy));
				Log.d(TAG, "RANDOM POSITION FOR i = " + i + "at (" + tempx + "," + tempy + ")");
				int j = rand.nextInt(3);
				MonsterLaut m = new MonsterLaut(tempx, tempy, j);
				m.setSurfed(shore.Surfed());
				MA.add(m);
			}
			isHadBeenRandomized=true;
		}
				
	}

	private boolean isValidPositionsForMonster(int tempx, int tempy) {
		if(tempx > 0 && tempy > 0 && tempx < 27 && tempy < 16) {
			boolean[][] surfed = shore.Surfed();
			if(surfed[tempy][tempx]) return true;
			else return false;
		} else
			return false;
	}
	
	public boolean findInMonsterList(int tempx, int tempy){
		MonsterArea Mtemp = new MonsterArea(tempx,tempy, 0);
		boolean found=false;
		for (MonsterLaut M : MA) {
			if((M.getPosX()==Mtemp.getPosX())&&(M.getPosY()==Mtemp.getPosY())){
				found=true;
				break;
			}
		}
		return found;

	}
	
	public void delMonsterList(int tempx, int tempy) {
		MonsterLaut Mtemp = new MonsterLaut(tempx,tempy, 0);
		for(int i = 0; i < MA.size(); i++) {
			if((MA.get(i).getPosX() == Mtemp.getPosX()) && (MA.get(i).getPosY() == Mtemp.getPosY())) {
				MA.remove(i);
				break;
			}
		}
	}
	
	public void drawmonsterarea(Canvas canvas){
		for(MonsterLaut M:MA){
			M.setOffset(shore.getxOffset(), shore.getyOffset());
			M.Draw(canvas, Main.OneMove);
		}
	}
	
	public void updatemonsterlist(int HeroIX, int HeroIY){
		for(MonsterLaut M:MA){
			M.update_pos(HeroIX, HeroIY);
			M.dDraw();
		}
	}
//	// Added by fawwaz
//	private static ArrayList<Batu> arraybatu = new ArrayList<Batu>();
//	
//	public boolean isValidPositionsForBatu(int x,int y){
//		//return true when x and y on rumput
//		// switch by row
//		switch(y){
//			case 1:
//			case 3:
//			case 2:
//				return false;
//			case 4:
//				if ((4<=x) && (x<=5)) {
//					return true;
//				}else{
//					return false;
//				}
//				
//			case 5:
//				if ((4<=x) && (x<=5)) {
//					return true;
//				}else{
//					return false;
//				}
//
//			case 6:
//				if ((5<=x) && (x<=6)) {
//					return true;
//				}else if ((11<=x) && (x<=12)) {
//					return true;
//				}else if ((19<=x) && (x<=24)) {
//					return true;
//				}else {
//					return false;
//				}
//				
//			case 7:
//				if ((1<=x) && (x<=2)) {
//					return true;
//				}else if ((5<=x) && (x<=7)) {
//					return true;
//				}else if ((11<=x) && (x<=12)) {
//					return true;
//				}else if ((16<=x) && (x<=20)) {
//					return true;
//				}else if ((23<=x) && (x<=24)) {
//					return true;
//				} else {
//					return false;
//				}
//				
//			case 8:
//				if ((1<=x) && (x<=2)) {
//					return true;
//				}else if ((4<=x) && (x<=6)) {
//					return true;
//				}else if ((11<=x) && (x<=13)) {
//					return true;
//				}else if ((16<=x) && (x<=20)) {
//					return true;
//				}else if ((23<=x) && (x<=24)) {
//					return true;
//				} else {
//					return false;
//				}
//				 
//			case 9:
//				if ((4<=x) && (x<=5)) {
//					return true;
//				}else if ((11<=x) && (x<=12)) {
//					return true;
//				}else if ((16<=x) && (x<=24)) {
//					return true;
//				} else {
//					return false;
//				}
//
//			case 10:
//				return false;
//				
//			case 11:
//				if ((1<=x) && (x<=2)) {
//					return true;
//				}else if (5==x) {
//					return true;
//				} else {
//					return false;
//				}
//
//				
//			case 12:
//				if ((1<=x) && (x<=2)) {
//					return true;
//				}else if ((5<=x) && (x<=6)) {
//					return true;
//				} else {
//					return false;
//				}
//				
//			case 13:
//				if ((4<=x) && (x<=7)) {
//					return true;
//				} else {
//					return false;
//				}
//
//			case 14:
//				if ((3<=x) && (x<=7)) {
//					return true;
//				}else if ((12<=x) && (x<=13)) {
//					return true;
//				} else {
//					return false;
//				}
//				
//			case 15:
//			 	if ((6<=x) && (x<=7)) {
//					return true;
//				}else if ((11<=x) && (x<=14)) {
//					return true;
//				} else {
//					return false;
//				}
//				
//			case 16:
//				// TODO not added yet
//			case 17:
//			// Not added yet
//				return false;
//				
//			case 18:
//			// Not added yet
//				return false;
//			default:
//				return false;
//		}
//	}
//	
//	public void PutBatu(){
//		int MaxMonster=5;
//		int tempx,tempy;
//		
//		
//		if(!isHadBeenRandomized && arraybatu.size() < 10){
//			Random rand = new Random();
//			for (int i = 0; i < MaxMonster; i++) {
//				do{
//					tempx=rand.nextInt(27)+1;
//					tempy=rand.nextInt(16)+1;
//				}while(!isValidPositionsForBatu(tempx, tempy));
//				Log.d(TAG, "RANDOM POSITION FOR i = " + i + "at (" + tempx + "," + tempy + ")");
//				
//				Batu m = new Batu(tempx, tempy);
//				m.setStepped(shore.Stepped());
//				arraybatu.add(m);			
//			}
//			isHadBeenRandomized=true;
//			Log.d("ShoreView","I nya ada segini lho  : ("+arraybatu.size()+")");
//		}
//				
//	}
//	
//	public void drawbatulist(Canvas canvas){
//		for(Batu B:arraybatu){
//			B.setOffset(shore.getxOffset(), shore.getyOffset());
//			B.Draw(canvas, 30);
//			Log.d("ShoreView","Gambar terletak pada : ("+B.getPosX()+","+B.getPosY()+")");
//			Log.d("ShoreView","SIZE : ("+arraybatu.size()+")");
//		}
//		Log.d("ShoreView","SIZE : ("+arraybatu.size()+")");
//	}
//	
//	
//	public void updateBatuList(int HeroIX, int HeroIY, int direction){
//		for(Batu B:arraybatu){
//			B.update_pos(HeroIX, HeroIY,direction);
//			B.dDraw();
//		}
//	}
//	
//	public boolean findInBatuList(int tempx, int tempy){
//		Batu batutemp = new Batu(tempx,tempy);
//		boolean found=false;
//		for (Batu B : arraybatu) {
//			if((B.getPosX()==batutemp.getPosX())&&(B.getPosY()==batutemp.getPosY())){
//				found=true;
//				break;
//			}
//		}
//		return found;
//
//	}
}
