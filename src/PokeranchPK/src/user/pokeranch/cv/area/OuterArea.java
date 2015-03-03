/**
 * @author Fawwaz Muhamamad 
 */
package user.pokeranch.cv.area;

import user.pokeranch.Main;
import user.pokeranch.cv.DrawableManager;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.Log;

/**
 *
 *
 */
public class OuterArea {
	public static final String TAG = CityArea.class.getSimpleName();
	private static Bitmap background;
	private static Bitmap overlay1;
	private static Bitmap overlay2;
	
	private int PosX;
	private int PosY;
	private int OneMove;
	
	private boolean stepped[][] = {
		// 0     1     2     3     4     5     6     7    8      9    10    11    12    13    14    15    16    17    18    19    20    21    22    23    24    25    26    27
		{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 0
		{false, true, true, true,false,false,false,false, true, true, true, true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, //1
		{false,false, true, true, true, true, true,false, true, true, true, true, true, true, true, true,false, true, true, true,false, true, true, true, true,false,false,false}, // 2
		{false,false, true, true, true, true, true,false, true, true,false,false, true, true, true,false,false,false, true, true, true, true, true, true, true,false,false,false}, // 3
		{false, true, true, true, true, true, true, true, true, true,false,false,false,false,false,false, true, true, true,false,false, true, true, true, true,false,false,false}, // 4
		{false, true, true,false, true, true, true, true, true,false,false,false,false, true, true, true, true,false,false, true, true, true, true, true, true,false,false,false}, // 5
		{false, true, true,false,false, true, true, true, true, true,false, true, true, true, true,false,false,false,false, true, true, true, true, true, true,false,false,false}, // 6
		{false, true, true,false,false, true, true, true, true, true,false, true, true,false,false,false,true, true, true, true, true,false,false, true, true,false,false,false}, // 7
		{false, true, true,false, true, true, true, true,false,false,false, true, true, true, true,false,true, true, true, true, true,false,false, true, true,false,false,false}, // 8
		{false, true, true, true, true, true, true, true,false,false,false, true, true, true, true,false,true, true, true, true, true, true, true, true, true,false,false,false}, // 9
		{false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,false,false,false}, // 10
		{false, true, true,false,false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,false,false,false}, // 11
		{false, true, true,false,false, true, true, true, true, true, true, true, true, true, true, true,false,false, true, true, true, true, true, true, true,false,false,false}, // 12
		{false, true, true, true, true, true, true, true, true, true, true,false, true, true, true, true,false,false, true, true, true, true, true, true, true,false,false,false}, // 13
		{false, true, true, true, true, true, true, true, true, true, true,false, true, true, true, true, true, true, true, true, true, true, true, true, true,false,false,false}, // 14
		{false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,false,false,false}, // 15
		{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}  // 16
	};
	
	private static Paint grass;
	public static int scaling;
	private int xOffset;
	private int yOffset;

	public static void init() {
		background = DrawableManager.getInstance().getOuterBackground1();
		overlay1=DrawableManager.getInstance().getOuterBackground2();
		overlay2=DrawableManager.getInstance().getOuterBackground3();
		Bitmap shaderBitmap = null;;
		shaderBitmap = DrawableManager.getInstance().Grass();
		if(Main.metrics.widthPixels > 800) {
			scaling = 2;
		} else {
			scaling = 1;
		}
		BitmapShader grassBS = new BitmapShader(shaderBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
		grass = new Paint();
		grass.setShader(grassBS);
	}
	
	public OuterArea(int w, int h) {
		OneMove = (int) (20*OuterView.density);
		PosX = 0;
		PosY = 0;
		xOffset = 0;
		yOffset = 0;
		Log.d(TAG,"OuterArea create successfully");
	}
	public void DrawBackground(Canvas canvas) {
		canvas.drawPaint(grass);
		canvas.drawBitmap(background, PosX, PosY, null);
	}
	
	public void DrawOverlay1(Canvas canvas) {
		canvas.drawBitmap(overlay1, PosX, PosY, null);
	}
	
	public void DrawOverlay2(Canvas canvas) {
		canvas.drawBitmap(overlay2, PosX, PosY, null);
	}
	
	// Not sure if it can be implemented
//	public void DrawMonster(Canvas canvas, int x, int y){
//		// temporary
//		int tempx=30*x, tempy=30*y;
////		canvas.drawBitmap(monstersample, tempx, tempy, null);
//	}
//	public void DrawMonster(Canvas canvas,MonsterArea M){
//		DrawMonster(canvas,(int) M.getPosX(), (int) M.getPosY());
//	}
//	
	public int getOneMove() {
		return OneMove*scaling;
	}
	public boolean[][] Stepped() {
		// TODO Auto-generated method stub
		return stepped;
	}

	
	
	
	
	
	
	


//	public void kanankirioye(){
//		for (MonsterArea M : MA) {
//			M.kanankiriaaah();
//		}
//	}
//	public void PutRandomMonster(Canvas canvas){
//		/*
//		 * Put an array of monster 
//		 * 
//		 * */
//		if (!isHadBeenRandomized) {
//			int tempx,tempy,MinX,MaxX,MaxY,MaxMonster;
//			MonsterArea tempmonster;
//			Random rand=new Random();
//			
//			MinX=0;
//			MaxX=16;
//			MaxY=21;
//			MaxMonster=9;
//			for (int i = 0; i < MaxMonster; i++) {
//				do{
//					tempx=MinX + (int)(Math.random() * ((MaxX - MinX) + 1));
//					tempy=MinX + (int)(Math.random() * ((MaxY - MinX) + 1));	
//				}while(!isValidPositionsForMonster(tempx, tempy));
//				
//				tempmonster = new MonsterArea((int) tempx, (int) tempy);
//				monsterlist.add(tempmonster);
//				DrawMonster(canvas, tempx,tempy);
//			}
//			isHadBeenRandomized=true;
//		}
//		
//	}
//	public void ResetIsHadBeenRandomized(){
//		this.isHadBeenRandomized=false;
//	}
	
//	public int GetMonsterlistID(int tempx,int tempy,int type ){
//		MonsterArea Mtemp = new MonsterArea(tempx, tempy, type);
//		return MA.indexOf(Mtemp);
//		
//	}
	// 0 
//	public void GeserMonster(int direction, int MonsterID){
//		MonsterArea Mtemp;
//		switch(direction){
//			case 0:
//				Mtemp = MA.get(MonsterID);
//				Mtemp.AddAtas();
//				MA.set(MonsterID, Mtemp);
//				break;
//			case 1:
//				Mtemp = MA.get(MonsterID);
//				Mtemp.AddKanan();
//				MA.set(MonsterID, Mtemp);
//				break;
//			case 2:
//				Mtemp = MA.get(MonsterID);
//				Mtemp.AddBawah();
//				MA.set(MonsterID, Mtemp);
//				break;
//			case 3:
//				Mtemp = MA.get(MonsterID);
//				Mtemp.AddKiri();
//				MA.set(MonsterID, Mtemp);
//				break;
//			default:
//				break;
//		}
//	}
//	
	
//	
//	public boolean findmonsterinrange(boolean isHorizontal, int x, int y){
//		boolean ketemu=false;
//		if (isHorizontal){
//			for (int i = 0; i < x; i++) {
//				if(findInMonsterList(i, y)){
//					ketemu=true;
//					break;
//				}
//			}
//		}else{
//			for (int i = 0; i < y; i++) {
//				if(findInMonsterList(x, i)){
//					ketemu=true;
//					break;
//				}
//			}
//		}
//		return ketemu;
//	}
	
//	
//	public void DrawMonsters(Canvas canvas){
////		int move=30;	
////		MonsterArea M;
//		int ix,iy;
//		for (int i = 0; i < monsterlist.size(); i++) {			
////			M=monsterlist.get(i);
//			ix=(int) monsterlist.get(i).getPosX();
//			iy=(int) monsterlist.get(i).getPosY();
//			Log.d(TAG, "pisisi x,"+ix+" posisi y"+iy);
//			System.out.println("pisisi x,"+ix+" posisi y"+iy);
//			DrawMonster(canvas, ix, iy);
//		}
//	}	
	
	public void forceChangeOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	public int getxOffset() {
		return xOffset;
	}
	public int getyOffset() {
		return yOffset;
	}
	public void ChangeOffset(int heroRealX, int heroRealY, int screenW, int screenH) {
		if((abs(heroRealX - xOffset) < 2*OneMove) && (abs(xOffset) > 0)) { // Kiri
			this.xOffset = (this.xOffset - 2*OneMove) + heroRealX;
		} else if((abs(heroRealY - yOffset) < 3*OneMove) && (abs(yOffset) > 0)) { // Atas
			this.yOffset = (this.yOffset - 3*OneMove) + heroRealY;
		} else if((abs(screenW - heroRealX) < 2*OneMove) && (abs(background.getWidth() - screenW - xOffset) > 0)) { // Kanan
			this.xOffset = (background.getWidth() - heroRealX) - ((background.getWidth() - screenW) + 2*OneMove);
		} else if ((abs(screenH - heroRealY) < 2*OneMove) && (abs(background.getHeight() - screenH - yOffset) > 0)) { // Bawah
			this.yOffset = (background.getHeight() - heroRealY) - ((background.getHeight() - screenH) + 2*OneMove);
		}
	}
	
	private int abs(int x) {
		return x > 0 ? x : -x;
	}
	
	
}

