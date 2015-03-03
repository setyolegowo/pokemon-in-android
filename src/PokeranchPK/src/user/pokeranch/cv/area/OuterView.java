/**
 * 
 */
package user.pokeranch.cv.area;


import java.util.ArrayList;
import java.util.Random;

import user.pokeranch.Main;
import user.pokeranch.cv.DrawableManager;
import user.pokeranch.cv.area.bitmap.Hero;
import user.pokeranch.cv.area.bitmap.MonsterArea;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author Fawwaz Muhammad
 *
 */
public class OuterView extends SurfaceView implements SurfaceHolder.Callback {

	private static int screenWidth;
	private static int screenHeight;
	public static float density;
	private Bitmap cut_tree=DrawableManager.getInstance().get_tree();
	private OuterViewListener mOuterViewListener;
	
	public interface OuterViewListener {
		public void onToCityListener(int callAct);
		public void onToNextLevel(int callAct);
		public void onToPreviousLevel(int callAct);
		public void onMeetMonster(int callAct); // on batlle
		public void onAfterBattle(int callAct); // on returned to btalle
	}
	
	private static final String TAG = OuterView.class.getSimpleName();
	public static final String FLOOR_LEVEL = "FLOOR_LEVEL";
	public GameLoopThread thread;
	private Matrix matrix = new Matrix();

	private boolean pause_event;
	private Hero hero;
	private OuterArea outerarea;
	
	private int callAct;
	
	private boolean KetemuMonster;
	
	private static int floor_level;
	
	Paint paint = new Paint();
	private Bitmap batuBitmap;
	private ArrayList<Position> listPosisiBatu;
	private ArrayList<Position> listPosisiPohon;
	
	public static boolean isUseCut;
	public static boolean isUsePush;
	public static boolean isUseTorch;
	
	public OuterView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);
		setFocusable(true);
		pause_event = false;
		Log.d(TAG,TAG + " initial bitmap...");
		paint.setColor(Color.BLACK);
		hero = new Hero();
		outerarea = new OuterArea(screenWidth, screenHeight);
		hero.setStepped(outerarea.Stepped());
		batuBitmap = DrawableManager.getInstance().get_batu();
		mOuterViewListener = (OuterViewListener) context;
		RandomizeMonster();
		listPosisiBatu = new ArrayList<Position>();
		listPosisiPohon = new ArrayList<Position>();
		BuildRockPosition();
		BuildListPohon();
		isUsePush = false;
		isUseCut = false;
		isUseTorch = false;
		Log.d(TAG,TAG + " create successfully");
	}
	
	private void BuildRockPosition() {
		listPosisiBatu.clear();
		listPosisiBatu.add(new Position(2,3));
		listPosisiBatu.add(new Position(3,3));	
	}
	
	private void BuildListPohon(){
		listPosisiPohon.clear();
		listPosisiPohon.add(new Position(12,9));
	}

	public static void screenSize(int width, int height) {
		screenWidth = width;
		screenHeight = height;
	}
	
	public int floor_level() {
		return floor_level;
	}
	
	public void SetActivityFrom(String activity_name) {
		if(activity_name.equals(CityActivity.class.getSimpleName())) {
			hero.setHeroPosition(18,16);
			hero.Atas();
			floor_level = 1;
		} else {
			if(floor_level > OuterActivity.floor_suggest) {
				hero.setHeroPosition(2,2);
				hero.Bawah();
			} else {
				hero.setHeroPosition(18,16);
				hero.Atas();
			}
			floor_level = OuterActivity.floor_suggest;
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
		if(!pause_event) {
			NemuMonsterGaYa();
			updatemonsterlist(hero.getX(), hero.getY());
			UpdateBatulist(hero.getX(), hero.getY(), hero.getPointer(), false);
			UpdateCutTree();
			outerarea.ChangeOffset(hero.getRealX(outerarea.getOneMove()), hero.getRealY(outerarea.getOneMove()), screenWidth, screenHeight);
			hero.setOffset(outerarea.getxOffset(), outerarea.getyOffset());
			hero.dDraw();
			Hero.sumWalkCheck();
		}
	}

	public void GambarKetemuMonsterLho(Canvas canvas){
		if(KetemuMonster && (callAct == 1)){
			canvas.drawRGB(255, 255, 255);
		}
	}
	
	public void render(Canvas canvas) {
		canvas.setMatrix(matrix);
		outerarea.DrawBackground(canvas);
		outerarea.DrawOverlay1(canvas);
		DrawAllBatu(canvas);
		DrawCutTree(canvas);
		drawmonsterarea(canvas);
		
		hero.Draw3(canvas);
		
		// outerarea.DrawOverlay2(canvas);
		if(Main.player.getDayOrNight() == 1) {
			if(!pause_event) {
				drawNightMode(canvas);
				canvas.drawColor(0x80000000);
			} else canvas.drawColor(0x30000000);
		} else {
			if(pause_event) canvas.drawColor(0x99000000);
		}
	}
	

	
	private void DrawAllBatu(Canvas canvas) {
		for(Position batu:listPosisiBatu) {
			canvas.drawBitmap(batuBitmap, batu.getX()*Main.OneMove*OuterArea.scaling, batu.getY()*Main.OneMove*OuterArea.scaling - batuBitmap.getHeight(), null);
		}
	}

	private void UpdateBatulist(int herox,int heroy, int direction,boolean onpush){
		onpush=true;
		if(isUsePush){
			for (Position P : listPosisiBatu) {
				geserBatuList(listPosisiBatu.indexOf(P), P.getX(), P.getY(), hero.getX(), hero.getY(), hero.getPointer());
			}
		}
	}
	/**
	 * 1 -> Bottom
	 * 2 -> Left
	 * 3 -> Top
	 * 4 -> Right
	 * @return int
	 */
	private void geserBatuList(int indexbatu,int batux,int batuy,int herox,int heroy,int direction){
		Position p;
		switch(direction){
			case 1: // bottom
				if((batuy-1==heroy)&&(batux==herox)){
					p=new Position(batux,batuy+1);
					if(isValidGeserBatu(batux, batuy, direction)){
						listPosisiBatu.set(indexbatu, p);
					}
				}
				break;
			case 2: // left
				if((batuy==heroy)&&(batux+1==herox)){
					p=new Position(batux-1,batuy);
					if(isValidGeserBatu(batux, batuy, direction)){
						listPosisiBatu.set(indexbatu, p);
					}
				}
				break;
			case 3: // top
				if((batuy+1==heroy)&&(batux==herox)){
					p=new Position(batux,batuy-1);
					if(isValidGeserBatu(batux, batuy, direction)){
						listPosisiBatu.set(indexbatu, p);
					}
				}
				break;
			case 4: // right
				if((batuy==heroy)&&(batux-1==herox)){
					p=new Position(batux+1,batuy);
					if(isValidGeserBatu(batux, batuy, direction)){
						listPosisiBatu.set(indexbatu, p);
					}
				}
				break;
		}
	}
	
	
	private boolean isValidGeserBatu(int batux,int batuy,int arahgeser){
		int tempx,tempy;
		boolean[][] stepped = outerarea.Stepped();
		switch(arahgeser){
			case 1://bottom
				tempy=batuy+1;
				tempx=batux;
				if(!stepped[tempy][tempx]){
					stepped[batuy][batux]=false;
					return false;
				}else if(listPosisiBatu.contains(new Position(tempy, tempx))){
					return false;
				}else{
					stepped[batuy][batux]=true;
					return true;
				}
				
			case 2: //left
				tempy=batuy;
				tempx=batux-1;
				if(!stepped[tempy][tempx]){
					stepped[batuy][batux]=false;
					return false;
				}else if(listPosisiBatu.contains(new Position(tempy, tempx))){
					return false;
				}else{
					stepped[batuy][batux]=true;
					return true;
				}
				
			case 3: // top
			default: 
				tempy=batuy-1;
				tempx=batux;
				if(!stepped[tempy][tempx]){
					stepped[batuy][batux]=false;
					return false;
				}else if(listPosisiBatu.contains(new Position(tempy, tempx))){
					return false;
				}else{
					stepped[batuy][batux]=true;
					return true;
				}
			case 4: // right
				tempy=batuy;
				tempx=batux+1;
				if(!stepped[tempy][tempx]){
					stepped[batuy][batux]=false;
					return false;
				}else if(listPosisiBatu.contains(new Position(tempy, tempx))){
					return false;
				}else{
					stepped[batuy][batux]=true;
					return true;
				}
		}				
	}
	
	private void DrawCutTree(Canvas canvas){
		for (Position P : listPosisiPohon) {
			canvas.drawBitmap(cut_tree, P.getX()*Main.OneMove*OuterArea.scaling, P.getY()*Main.OneMove*OuterArea.scaling- batuBitmap.getHeight(), null);
		}
		
	}
	
	private void UpdateCutTree(){
		if(isUseCut) {
			isUseCut = false;
			for (Position P : listPosisiPohon) {
				CutTree(listPosisiPohon.indexOf(P), hero.getX(), hero.getY(), P.getX(), P.getY());
			}
		}
	}
	
	private void CutTree(int indexpohon,int herox,int heroy,int pohonx,int pohony){
		boolean kondisi1=((pohonx+1==herox)&&(pohony==heroy));
		boolean kondisi2=((pohonx-1==herox)&&(pohony==heroy));
		boolean kondisi3=((pohonx==herox)&&(pohony+1==heroy));
		boolean kondisi4=((pohonx==herox)&&(pohony-1==heroy));
		
		if(kondisi1 || kondisi2 || kondisi3 || kondisi4){
			listPosisiPohon.remove(indexpohon);
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
					} else if((delY < -margin_move) && (delX < margin_tolerance) && (delX > -margin_tolerance)) {
						// Atas
						switch(isToChangeView()) {
							case 2:
								mOuterViewListener.onToNextLevel(callAct);
								callAct++;
							default: 
								hero.Atas(); 
								break; 
						}
					} else if((delX < -margin_move) && (delY < margin_tolerance) && (delY > -margin_tolerance)) {
						// Kiri
						hero.Kiri();
					} else if((delY > margin_move) && (delX < margin_tolerance) && (delX > -margin_tolerance)) {
						// Bawah
						if(isToChangeView() == 1) { mOuterViewListener.onToPreviousLevel(callAct); callAct++; }
						hero.Bawah();
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
		if((hero.getX() == 18) && (hero.getY() == 15)) return 1; // HOME
		else if(((hero.getX() == 2) ||(hero.getX() == 3)) && (hero.getY() == 1)) return 2; // ke lantai selanjutnya
		else 
			return 0;
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
			mOuterViewListener.onMeetMonster(callAct);
			callAct++;
			this.KetemuMonster = false;
		}
	}
	
	
	private boolean isHadBeenRandomized =false;
	private static ArrayList<MonsterArea> MA=new ArrayList<MonsterArea>();
	
	public boolean isValidPositionsForMonster(int x,int y){
		//return true when x and y on rumput
		// switch by row
		switch(y){
			case 1:
			case 3:
			case 2:
				return false;
			case 4:
				if ((4<=x) && (x<=5)) {
					return true;
				}else{
					return false;
				}
				
			case 5:
				if ((4<=x) && (x<=5)) {
					return true;
				}else{
					return false;
				}

			case 6:
				if ((5<=x) && (x<=6)) {
					return true;
				}else if ((11<=x) && (x<=12)) {
					return true;
				}else if ((19<=x) && (x<=24)) {
					return true;
				}else {
					return false;
				}
				
			case 7:
				if ((1<=x) && (x<=2)) {
					return true;
				}else if ((5<=x) && (x<=7)) {
					return true;
				}else if ((11<=x) && (x<=12)) {
					return true;
				}else if ((16<=x) && (x<=20)) {
					return true;
				}else if ((23<=x) && (x<=24)) {
					return true;
				} else {
					return false;
				}
				
			case 8:
				if ((1<=x) && (x<=2)) {
					return true;
				}else if ((4<=x) && (x<=6)) {
					return true;
				}else if ((11<=x) && (x<=13)) {
					return true;
				}else if ((16<=x) && (x<=20)) {
					return true;
				}else if ((23<=x) && (x<=24)) {
					return true;
				} else {
					return false;
				}
				 
			case 9:
				if ((4<=x) && (x<=5)) {
					return true;
				}else if ((11<=x) && (x<=12)) {
					return true;
				}else if ((16<=x) && (x<=24)) {
					return true;
				} else {
					return false;
				}

			case 10:
				return false;
				
			case 11:
				if ((1<=x) && (x<=2)) {
					return true;
				}else if (5==x) {
					return true;
				} else {
					return false;
				}

				
			case 12:
				if ((1<=x) && (x<=2)) {
					return true;
				}else if ((5<=x) && (x<=6)) {
					return true;
				} else {
					return false;
				}
				
			case 13:
				if ((4<=x) && (x<=7)) {
					return true;
				} else {
					return false;
				}

			case 14:
				if ((3<=x) && (x<=7)) {
					return true;
				}else if ((12<=x) && (x<=13)) {
					return true;
				} else {
					return false;
				}
				
			case 15:
			 	if ((6<=x) && (x<=7)) {
					return true;
				}else if ((11<=x) && (x<=14)) {
					return true;
				} else {
					return false;
				}
				
			case 16:
				// TODO not added yet
			case 17:
			// Not added yet
				return false;
				
			case 18:
			// Not added yet
				return false;
			default:
				return false;
		}
	}
	
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
				MonsterArea m = new MonsterArea(tempx, tempy, j);
				m.setStepped(outerarea.Stepped());
				MA.add(m);			
			}
			isHadBeenRandomized=true;
		}
				
	}
	
	public boolean findInMonsterList(int tempx, int tempy){
		MonsterArea Mtemp = new MonsterArea(tempx,tempy, 0);
		boolean found=false;
		for (MonsterArea M : MA) {
			if((M.getPosX()==Mtemp.getPosX())&&(M.getPosY()==Mtemp.getPosY())){
				found=true;
				break;
			}
		}
		return found;

	}
	
	public void delMonsterList(int tempx, int tempy) {
		MonsterArea Mtemp = new MonsterArea(tempx,tempy, 0);
		for(int i = 0; i < MA.size(); i++) {
			if((MA.get(i).getPosX() == Mtemp.getPosX()) && (MA.get(i).getPosY() == Mtemp.getPosY())) {
				MA.remove(i);
				break;
			}
		}
	}
	
	public void drawmonsterarea(Canvas canvas){
		for(MonsterArea M:MA){
			M.setOffset(outerarea.getxOffset(), outerarea.getyOffset());
			M.Draw(canvas, outerarea.getOneMove());
		}
	}
	
	public void updatemonsterlist(int HeroIX, int HeroIY){
		for(MonsterArea M:MA){
			M.update_pos(HeroIX, HeroIY);
			M.dDraw();
		}
	}
	private void drawNightMode(Canvas canvas) {
		if(!isUseTorch){
			int a = hero.getRealX(outerarea.getOneMove()) + outerarea.getOneMove() + 20;
			int b = hero.getRealY(outerarea.getOneMove()) - hero.getSpriteHeight() - 10;
			int c = hero.getRealX(outerarea.getOneMove()) - 20;
			int d = hero.getRealY(outerarea.getOneMove()) + 10;
			canvas.drawRect(0, 0, a, b, paint);
			canvas.drawRect(0, b, c, screenHeight, paint);
			canvas.drawRect(c, d, screenWidth, screenHeight, paint);
			canvas.drawRect(a, 0, screenWidth, d, paint);
		}
		
	}
	
	private class Position {
		private int x;
		private int y;
		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
	}
	
	
	
	
	
}
