/**
 * 
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
 * @author Setyo Lego
 *
 */
public class CityArea {
	private static int scaling;
	public static final String TAG = CityArea.class.getSimpleName();
	private static Bitmap background;
	private static Bitmap foreground;
	private static Paint grass;
	private int PosX;
	private int PosY;
	private int xOffset;
	private int yOffset;
	private boolean stepped[][] = {
			// 0     1     2     3     4     5     6     7    8      9    10    11    12    13    14    15    16    17    18    19    20    21    22    23    24    25    26    27
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 0
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 1
			{false,false, true, true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false, true,false,false,false,false,false,false,false,false}, // 2
			{false,false, true, true,false,false,false,false,false,false,false,false,false,false,false,false,false,false, true, true, true, true, true, true, true, true,false,false}, // 3
			{false,false, true, true,false,false,false,false,false,false,false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,false,false}, // 4
			{false,false, true, true,false,false,false,false,false,false,false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,false,false}, // 5
			{false,false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,false,false}, // 6
			{false,false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,false,false}, // 7
			{false,false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,false,false,false,false, true,false,false}, // 8
			{false,false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,false,false,false,false, true,false,false}, // 9
			{false,false, true, true, true, true, true,false,false,false,false,false,false,false,false,false,false, true, true, true, true,false,false,false,false, true,false,false}, // 10
			{false,false, true, true, true, true, true,false,false,false,false,false,false,false,false,false,false, true, true, true, true,false,false,false,false, true,false,false}, // 11
			{false,false, true, true, true, true, true,false,false,false,false,false,false,false,false,false,false, true, true, true, true, true, true, true, true, true,false,false}, // 12
			{false,false, true, true, true, true, true,false,false,false,false,false,false,false,false,false,false, true, true, true, true, true, true, true, true, true,false,false}, // 13
			{false,false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,false,false}, // 14
			{false,false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,false,false}, // 15
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}  // 16
		};
	public static void initCityArea() {
		background = DrawableManager.getInstance().getCityBackground();
		foreground = DrawableManager.getInstance().getCityForeground();
		BitmapShader grassBS = new BitmapShader(Bitmap.createScaledBitmap(DrawableManager.getInstance().Grass(), DrawableManager.getInstance().Grass().getWidth()*2, DrawableManager.getInstance().Grass().getHeight()*2, true), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
		grass = new Paint();
		grass.setShader(grassBS);
		if(Main.metrics.widthPixels > 800) {
			scaling = 2;
		} else {
			scaling = 1;
		}
	}
	public CityArea(int w, int h) {
		PosX = 0;
		PosY = 0;
		xOffset = 0;
		yOffset = 0;
		Log.d(TAG,"CityArea create successfully");
	}
	public CityArea() {
		PosX = 0;
		PosY = 0;
		xOffset = 0;
		yOffset = 0;
		Log.d(TAG,"CityArea create successfully");
	}
	public void DrawBackground(Canvas canvas) {
		canvas.drawPaint(grass);
		canvas.drawBitmap(background, PosX + xOffset, PosY + yOffset, null);
	}
	public void DrawForeground(Canvas canvas) {
		canvas.drawBitmap(foreground, PosX + xOffset, PosY + yOffset, null);
	}
	public boolean[][] Stepped() {
		// TODO Auto-generated method stub
		return stepped;
	}
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
		if((abs(heroRealX - xOffset) < 2*Main.OneMove*scaling) && (abs(xOffset) > 0)) { // Kiri
			this.xOffset = (this.xOffset - 2*Main.OneMove*scaling) + heroRealX;
		} else if((abs(heroRealY - yOffset) < 3*Main.OneMove*scaling) && (abs(yOffset) > 0)) { // Atas
			this.yOffset = (this.yOffset - 3*Main.OneMove*scaling) + heroRealY;
		} else if((abs(screenW - heroRealX) < 2*Main.OneMove*scaling) && (abs(background.getWidth() - screenW - xOffset) > 0)) { // Kanan
			this.xOffset = (background.getWidth() - heroRealX) - ((background.getWidth() - screenW) + 2*Main.OneMove*scaling);
		} else if ((abs(screenH - heroRealY) < 2*Main.OneMove*scaling) && (abs(background.getHeight() - screenH - yOffset) > 0)) { // Bawah
			this.yOffset = (background.getHeight() - heroRealY) - ((background.getHeight() - screenH) + 2*Main.OneMove*scaling);
		}
	}
	
	private int abs(int x) {
		return x > 0 ? x : -x;
	}
}
