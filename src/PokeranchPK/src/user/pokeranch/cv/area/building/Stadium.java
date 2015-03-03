/**
 * 
 */
package user.pokeranch.cv.area.building;

import user.pokeranch.Main;
import user.pokeranch.cv.DrawableManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

/**
 * @author Setyo Lego
 *
 */
public class Stadium {
	private static int scaling;
	private static Bitmap background;
	private int PosX;
	private int PosY;
	private boolean stepped[][] = {
			// 0     1     2     3     4     5     6     7    8      9    10    11    12    13    14    15    16    17    18    19    20    21    22    23    24    25    26    27
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 0
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 1
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 2
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 3
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 4
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false, true, true,false, true,false,false,false,false,false,false,false,false,false,false}, // 5, HERE
			{false,false,false,false,false,false,false,false,false, true, true, true, true, true, true, true, true, true,false,false,false,false,false,false,false,false,false,false}, // 6
			{false,false,false,false,false,false,false,false, true, true, true, true, true, true, true, true, true, true, true, true,false,false,false,false,false,false,false,false}, // 7
			{false,false,false,false,false,false,false,false, true, true, true,false,false, true, true, true, true, true, true, true,false,false,false,false,false,false,false,false}, // 8
			{false,false,false,false,false,false,false,false, true, true, true,false,false, true, true, true, true, true, true, true,false,false,false,false,false,false,false,false}, // 9
			{false,false,false,false,false,false,false,false, true, true, true, true, true, true, true, true, true, true, true, true,false,false,false,false,false,false,false,false}, // 10
			{false,false,false,false,false,false,false,false, true, true, true, true, true, true, true, true, true, true, true, true,false,false,false,false,false,false,false,false}, // 11
			{false,false,false,false,false,false,false,false, true, true, true, true, true, true, true, true, true, true, true, true,false,false,false,false,false,false,false,false}, // 12
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 13
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 14
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}, // 15
			{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}  // 16
		};
	private int xOffset;
	private int yOffset;
	public static void init() {
		background = DrawableManager.getInstance().getStadiumBackground();
		if(Main.metrics.heightPixels > 800) {
			scaling = 2;
		} else {
			scaling = 1;
		}
	}
	public Stadium (int w, int h) { 
		PosX = Main.OneMove*8*scaling;
		PosY = Main.OneMove*12*scaling - background.getHeight();
		if(w < 800) {
			xOffset = - (PosX - (w - background.getWidth())/2);
			yOffset = - (PosY - (h - background.getHeight())/2);
		}
	}
	public void Draw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		canvas.drawBitmap(background, PosX + xOffset, PosY + yOffset, null);
	}
	public boolean[][] Stepped() {
		return stepped;
	}
	public int getXOffset() {
		return xOffset;
	}
	public int getYOffset() {
		return yOffset;
	}
}
