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
public class ShoreArea {
	private static final String TAG = ShoreArea.class.getSimpleName();
	private static Bitmap background;
	private static Paint cur_background;
	private static Bitmap background2;
	private static BitmapShader shader0;
	private static BitmapShader shader1;
	private static BitmapShader shader2;
	private static int scaling;
	private int cur_frame;
	private final int fps = 180;
	private int PosX;
	private int PosY;
	private static final boolean stepped[][] = {
			// 0     1     2     3     4     5     6     7    8      9    10    11    12    13    14    15    16    17    18    19    20    21    22    23    24    25    26    27
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 0
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false, true, true, true, true, true, true, true, true, true, true,false,false,false,false}, // 1
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false, true, true, true, true, true, true, true,false, true, true,false,false,false,false}, // 2
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false, true, true,false, true, true, true, true, true, true, true,false,false,false,false}, // 3
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false, true, true, true, true, true, true, true, true, true, true,false,false,false,false}, // 4
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false, true, true, true, true, true, true,false, true, true, true,false,false,false,false}, // 5
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false, true, true, true, true, true, true, true, true, true, true,false,false,false,false}, // 6
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false, true, true, true, true, true, true, true, true, true, true,false,false,false,false}, // 7
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false, true, true, true,false, true, true, true, true, true, true,false,false,false,false}, // 8
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false, true, true, true, true, true, true, true, true, true, true,false,false,false,false}, // 9
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false, true, true, true, true, true, true, true, true, true, true,false,false,false,false}, // 10
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false, true,false, true, true, true, true, true, true,false, true,false,false,false,false}, // 11
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false, true, true, true, true, true, true, true, true, true, true,false,false,false,false}, // 12
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false, true, true, true, true, true, true, true, true, true, true,false,false,false,false}, // 13
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 14
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 15
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}  // 16
		};
	private static final boolean surfed[][] = {
			// 0     1     2     3     4     5     6     7    8      9    10    11    12    13    14    15    16    17    18    19    20    21    22    23    24    25    26    27
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 0
			{false, true, true, true, true, true, true, true, true, true, true, true, true, true,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 1
			{false, true, true, true, true, true, true, true, true, true, true, true, true, true,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 2
			{false, true, true, true, true, true, true, true, true, true, true, true, true, true,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 3
			{false, true, true, true, true, true, true, true, true, true, true, true, true, true,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 4
			{false, true, true, true, true, true, true, true, true, true, true, true, true, true,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 5
			{false, true, true, true, true, true, true, true, true, true, true, true, true, true,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 6
			{false, true, true, true, true, true, true, true, true, true, true, true, true, true,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 7
			{false, true, true, true, true, true, true, true, true, true, true, true, true, true,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 8
			{false, true, true, true, true, true, true, true, true, true, true, true, true, true,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 9
			{false, true, true, true, true, true, true, true, true, true, true, true, true, true,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 10
			{false, true, true, true, true, true, true, true, true, true, true, true, true, true,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 11
			{false, true, true, true, true, true, true, true, true, true, true, true, true, true,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 12
			{false, true, true, true, true, true, true, true, true, true, true, true, true, true,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 13
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 14
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 15
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}  // 16
		};
	private int xOffset;
	private int yOffset;
	
	public static void init() {
		background = DrawableManager.getInstance().SeaAnimation();
		background2 = DrawableManager.getInstance().getSea();
		if(Main.metrics.heightPixels > 800) {
			scaling = 2;
		} else {
			scaling = 1;
		}
		cur_background = new Paint();
		shader0 = new BitmapShader(Bitmap.createBitmap(background, 0, 0, background.getWidth()/3, background.getHeight()), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
		shader1 = new BitmapShader(Bitmap.createBitmap(background, background.getWidth()/3, 0, background.getWidth()/3, background.getHeight()), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
		shader2 = new BitmapShader(Bitmap.createBitmap(background, background.getWidth()*2/3, 0, background.getWidth()/3, background.getHeight()), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
		cur_background.setShader(shader0);
	}
	public ShoreArea() {
		cur_frame = 0;
		PosX = 0;
		PosY = 0;
		xOffset = 0;
		yOffset = 0;
		Log.d(TAG,TAG + " create successfully");
	}
	public void DrawBackground(Canvas canvas) {
		cur_frame++;
		if(cur_frame == fps/3) {
			cur_background.setShader(shader1);
		} else if(cur_frame == fps*2/3) {
			cur_background.setShader(shader2);
		} else if(cur_frame == fps) {
			cur_background.setShader(shader0);
			cur_frame = 0;
		}
		canvas.drawPaint(cur_background);
		canvas.drawBitmap(background2, PosX, PosY, null);
	}
	public boolean[][] Stepped() {
		// TODO Auto-generated method stub
		return stepped;
	}
	public boolean[][] Surfed() {
		return surfed;
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
