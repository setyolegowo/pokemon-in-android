package user.pokeranch.cv.area.bitmap;

import user.pokeranch.Main;
import user.pokeranch.cv.DrawableManager;
import user.pokeranch.models.PlayerMonster;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Hero {
	private static Bitmap hero;
	private int PosIX = 16; // posisi default
	private int PosIY = 6;
	private float PosX = (float) PosIX; // versi float
	private float PosY = (float) PosIY;
	private int sprite_no = 0; // nomor merepresentasikan sdraw
	private int wps = 4; // banyaknya jalan sebanyak satu detik
	private final int fps = 60;
	private int xOffset; //kalau map kebesaran
	private int yOffset;
	private static int spriteHeight;
	private int no_frame = 0; //
	private boolean kanan = false; // kondisi geraknya 
	private boolean kiri = false;
	private boolean atas = false;
	private boolean bawah = false;
	private int move_b = 1; // move before
	private int move_act = 0; // 0 tidak aktif, 1 bawah A, 2 bawah B, 3 kiri A, 4 kiri B, 5 atas A, 6 atas B, 7 kanan A, 8 kanan B
	private boolean stepped[][];
	private boolean surfed[][];
	private static int status;
	
	private static Bitmap hero0;
	private static Bitmap hero1;
	private static Bitmap hero2;
	private static Bitmap hero3;
	private static Bitmap hero4;
	private static Bitmap hero5;
	private static Bitmap hero6;
	private static Bitmap hero7;
	private static Bitmap hero8;
	private static Bitmap hero9;
	private static Bitmap hero10;
	private static Bitmap hero11;
	private static int scaling;
	
	private static int sum_walk;
	private static final int maxWalkSession = 1000;
	
	public static void initHero() {
		status = 1;

		if(Main.metrics.heightPixels <= 800) {
			scaling = 1;
		} else {
			scaling = 2;
		}
		
		sum_walk = 0;
		
		recycleAllBitmap();
		changeImageStatus();
	}
	
	public Hero() {
		
	}
	public void Draw3(Canvas canvas) {
		float x1 = PosX * Main.OneMove * scaling;
		float y1 = (PosY * Main.OneMove * scaling) - spriteHeight;
		if(status == 1) {
			switch(sprite_no) {
				case 0 : canvas.drawBitmap(hero0 , x1 + xOffset, y1 + yOffset, null); break;
				case 1 : canvas.drawBitmap(hero1 , x1 + xOffset, y1 + yOffset, null); break;
				case 2 : canvas.drawBitmap(hero2 , x1 + xOffset, y1 + yOffset, null); break;
				case 3 : canvas.drawBitmap(hero3 , x1 + xOffset, y1 + yOffset, null); break;
				case 4 : canvas.drawBitmap(hero4 , x1 + xOffset, y1 + yOffset, null); break;
				case 5 : canvas.drawBitmap(hero5 , x1 + xOffset, y1 + yOffset, null); break;
				case 6 : canvas.drawBitmap(hero6 , x1 + xOffset, y1 + yOffset, null); break;
				case 7 : canvas.drawBitmap(hero7 , x1 + xOffset, y1 + yOffset, null); break;
				case 8 : canvas.drawBitmap(hero8 , x1 + xOffset, y1 + yOffset, null); break;
				case 9 : canvas.drawBitmap(hero9 , x1 + xOffset, y1 + yOffset, null); break;
				case 10: canvas.drawBitmap(hero10, x1 + xOffset, y1 + yOffset, null); break;
				case 11: canvas.drawBitmap(hero11, x1 + xOffset, y1 + yOffset, null); break;
			}
		} else if(status == 2) {
			switch(sprite_no) {
				case 0 : canvas.drawBitmap(hero0 , x1 + xOffset, y1 + yOffset, null); break;
				case 1 : canvas.drawBitmap(hero1 , x1 + xOffset, y1 + yOffset, null); break;
				case 2 : canvas.drawBitmap(hero2 , x1 + xOffset, y1 + yOffset, null); break;
				case 3 : canvas.drawBitmap(hero3 , x1 + xOffset, y1 + yOffset, null); break;
				case 4 : 
				case 5 : canvas.drawBitmap(hero4 , x1 + xOffset, y1 + yOffset, null); break;
				case 6 : canvas.drawBitmap(hero5 , x1 + xOffset, y1 + yOffset, null); break;
				case 7 : canvas.drawBitmap(hero6 , x1 + xOffset, y1 + yOffset, null); break;
				case 8 : canvas.drawBitmap(hero7 , x1 + xOffset, y1 + yOffset, null); break;
				case 9 : canvas.drawBitmap(hero8 , x1 + xOffset, y1 + yOffset, null); break;
				case 10: 
				case 11: canvas.drawBitmap(hero9, x1 + xOffset, y1 + yOffset, null); break;
			}
		} else if(status == 3) {
			switch(sprite_no) {
				case 0 : canvas.drawBitmap(hero0 , x1 + xOffset, y1 + yOffset, null); break;
				case 1 : 
				case 2 : canvas.drawBitmap(hero1 , x1 + xOffset, y1 + yOffset, null); break;
				case 3 : canvas.drawBitmap(hero2 , x1 + xOffset, y1 + yOffset, null); break;
				case 4 : 
				case 5 : canvas.drawBitmap(hero3 , x1 + xOffset, y1 + yOffset, null); break;
				case 6 : canvas.drawBitmap(hero4 , x1 + xOffset, y1 + yOffset, null); break;
				case 7 : 
				case 8 : canvas.drawBitmap(hero5 , x1 + xOffset, y1 + yOffset, null); break;
				case 9 : canvas.drawBitmap(hero6 , x1 + xOffset, y1 + yOffset, null); break;
				case 10: 
				case 11: canvas.drawBitmap(hero7 , x1 + xOffset, y1 + yOffset, null); break;
			}
		}
	}
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	public int getRealX(int move) {
		return (int) (PosX * move);
	}
	public int getRealY(int move) {
		return (int) (PosY * move);
	}
	public static void sumWalkCheck() {
		if(sum_walk == maxWalkSession) {
			sum_walk = 0;
			if(Main.player.getDayOrNight() == 0) {
				Main.player.setDayOrNight(1);
			} else {
				Main.player.setDayOrNight(0);
				Main.player.setTotalTimePlay(Main.player.getTotalTimePlay() + 1);
				PlayerMonster.incAgeOfMonster();
			}
		}
	}
	public void dDraw() {
		assert (move_b > 8) || (move_b < 1);
		assert (move_act > 8) || (move_act < 1);
		assert (PosIX <= 0) || (PosIY <= 0);
		//Log.i("position", "Y: " + PosIY + " X: " + PosIX);
		if(move_act > 0) {
			no_frame++;
			
			// Switch or not
			switch(move_b) {
				case 1:
				case 2:
					if(move_act != 1) move_b = move_act;
					break;
				case 3:
				case 4:
					if(move_act != 3) move_b = move_act;
					break;
				case 5:
				case 6:
					if(move_act != 5) move_b = move_act;
					break;
				case 7:
				case 8:
					if(move_act != 7) move_b = move_act;
					break;
			}
			
			if(no_frame > (fps/(2*wps*status))) {
				switch(move_b) {
					case 1: sprite_no = 2; AddBawah(); break;
					case 2: sprite_no = 1; AddBawah(); break;
					case 3: sprite_no = 5; AddKiri(); break;
					case 4: sprite_no = 4; AddKiri(); break;
					case 5: sprite_no = 8; AddAtas(); break;
					case 6: sprite_no = 7; AddAtas(); break;
					case 7: sprite_no = 11; AddKanan(); break;
					case 8: sprite_no = 10; AddKanan(); break;
				}
				if(no_frame == (fps/(wps*status))) {
					switch(move_b) {
						case 1: move_b = 2; break;
						case 2: move_b = 1; break;
						case 3: move_b = 4; break;
						case 4: move_b = 3; break;
						case 5: move_b = 6; break;
						case 6: move_b = 5; break;
						case 7: move_b = 8; break;
						case 8: move_b = 7; break;
					}
					no_frame = 0;
					move_act = 0;
					kanan = false; kiri = false; atas = false; bawah = false;
					sum_walk++;
				}
			} else {
				switch(move_b) {
					case 1:
					case 2:
						sprite_no = 0;
						AddBawah();
						break;
					case 3:
					case 4:
						sprite_no = 3;
						AddKiri();
						break;
					case 5:
					case 6:
						sprite_no = 6;
						AddAtas();
						break;
					case 7:
					case 8:
						sprite_no = 9;
						AddKanan();
						break;
				}
			}
		} 
		if(move_act == 0) {
			switch(move_b) {
				case 1:
				case 2:
					sprite_no = 0;
					break;
				case 3:
				case 4:
					sprite_no = 3;
					break;
				case 5:
				case 6:
					sprite_no = 6;
					break;
				case 7:
				case 8:
					sprite_no = 9;
					break;
			}
		}
	}
	private void ChangePos() {
		if((kanan || kiri || atas || bawah) && (move_act == 0)) {
			if(kanan) move_act = 7;
			else if(kiri) move_act = 3;
			else if(atas) move_act = 5;
			else move_act = 1;
		}
	}
	public void Kanan() {
		kanan = true;
		ChangePos();
	}
	public void Kiri() {
		kiri = true;
		ChangePos();
	}
	public void Atas() {
		atas = true;
		ChangePos();
	}
	public void Bawah() {
		bawah = true;
		ChangePos();
	}
	private void AddKanan() {
		if(status < 3) {
			if((stepped[PosIY][PosIX + 1]) && (status < 3)) {
				PosX += ((float) wps)*status/fps;
				if((PosX + 0.03) > (PosIX + 1)) { PosIX++; PosX = (float) PosIX; }
			}
		} else if((surfed[PosIY][PosIX + 1]) && (status == 3)) {
			PosX += ((float) wps)*status/fps;
			if((PosX + 0.03) > (PosIX + 1)) { PosIX++; PosX = (float) PosIX; }
		}
	}
	private void AddKiri() {
		if(status < 3) {
			if((stepped[PosIY][PosIX - 1]) && (status < 3)) {
				PosX -= ((float) wps)*status/fps;
				if((PosX - 0.03) < (PosIX - 1)) { PosIX--; PosX = (float) PosIX; }
			}
		} else if(surfed[PosIY][PosIX - 1]) {
			PosX -= ((float) wps)*status/fps;
			if((PosX - 0.03) < (PosIX - 1)) { PosIX--; PosX = (float) PosIX; }
		}
	}
	private void AddAtas() {
		if(status < 3) {
			if(stepped[PosIY - 1][PosIX]) {
				PosY -= ((float) wps)*status/fps;
				if((PosY - 0.03) < (PosIY - 1)) { PosIY--; PosY = (float) PosIY; }
			}
		} else if(surfed[PosIY - 1][PosIX]) {
			PosY -= ((float) wps)*status/fps;
			if((PosY - 0.03) < (PosIY - 1)) { PosIY--; PosY = (float) PosIY; }
		}
	}
	private void AddBawah() {
		if(status < 3) {
			if(stepped[PosIY + 1][PosIX]) {
				PosY += ((float) wps)*status/fps;
				if((PosY + 0.03) > (PosIY + 1)) { PosIY++; PosY = (float) PosIY; }
			}
		} else if(surfed[PosIY + 1][PosIX]) {
			PosY += ((float) wps)*status/fps;
			if((PosY + 0.03) > (PosIY + 1)) { PosIY++; PosY = (float) PosIY; }
		}
	}
	/**
	 * 1 jalan
	 * 2 naik sepeda
	 * 3 berenang
	 * @param i
	 */
	public static void change_status(int i) {
		status = i;
		recycleAllBitmap();
		changeImageStatus();
	}
	public static int getStatus() {
		return status;
	}
	public void setStepped(boolean[][] _stepped) {
		stepped = _stepped;
	}
	public void setSurfed(boolean[][] _surfed) {
		surfed = _surfed;
	}
	public void setHeroPosition(int X, int Y) {
		PosIX = X; PosX = (float) X;
		PosIY = Y; PosY = (float) Y;
	}
	public int getX() {
		return PosIX;
	}
	public int getY() {
		return PosIY;
	}
	/**
	 * 1 -> Bottom
	 * 2 -> Left
	 * 3 -> Top
	 * 4 -> Right
	 * @return int
	 */
	public int getPointer() {
		return (move_act + 1)/2;
	}
	public int getPointer2() {
		return (move_b + 1)/2;
	}
	public int getSpriteHeight() {
		return hero.getHeight();
	}
	public int getSpriteWidth() {
		return hero.getWidth();
	}
	public static int getSumWalk() {
		return sum_walk;
	}
	public static void setSumWalk(int i) {
		sum_walk = i;
	}
	
	private static void recycleAllBitmap() {
		if(hero0 != null) hero0.recycle();
		if(hero1 != null) hero1.recycle();
		if(hero2 != null) hero2.recycle();
		if(hero3 != null) hero3.recycle();
		if(hero4 != null) hero4.recycle();
		if(hero5 != null) hero5.recycle();
		if(hero6 != null) hero6.recycle();
		if(hero7 != null) hero7.recycle();
		if(hero8 != null) hero8.recycle();
		if(hero9 != null) hero9.recycle();
		if(hero10 != null) hero10.recycle();
		if(hero11 != null) hero11.recycle();
	}
	
	private static void changeImageStatus() {
		// Get BITMAP
		if(status == 1) hero = DrawableManager.getInstance().getHero();
		else if(status == 2) hero = DrawableManager.getInstance().getHeroBike();
		else if(status == 3) hero = DrawableManager.getInstance().getHeroSwim();
		
		spriteHeight = hero.getHeight();
		
		hero0 = Bitmap.createBitmap(hero, 0, 0, Main.OneMove*scaling, spriteHeight);
		hero1 = Bitmap.createBitmap(hero, Main.OneMove*scaling, 0, Main.OneMove*scaling, spriteHeight);
		hero2 = Bitmap.createBitmap(hero, Main.OneMove*2*scaling, 0, Main.OneMove*scaling, spriteHeight);
		hero3 = Bitmap.createBitmap(hero, Main.OneMove*3*scaling, 0, Main.OneMove*scaling, spriteHeight);
		hero4 = Bitmap.createBitmap(hero, Main.OneMove*4*scaling, 0, Main.OneMove*scaling, spriteHeight);
		hero5 = Bitmap.createBitmap(hero, Main.OneMove*5*scaling, 0, Main.OneMove*scaling, spriteHeight);
		hero6 = Bitmap.createBitmap(hero, Main.OneMove*6*scaling, 0, Main.OneMove*scaling, spriteHeight);
		hero7 = Bitmap.createBitmap(hero, Main.OneMove*7*scaling, 0, Main.OneMove*scaling, spriteHeight);
		if(status < 3) {
			hero8 = Bitmap.createBitmap(hero, Main.OneMove*8*scaling, 0, Main.OneMove*scaling, spriteHeight);
			hero9 = Bitmap.createBitmap(hero, Main.OneMove*9*scaling, 0, Main.OneMove*scaling, spriteHeight);
			if(status == 1) {
				hero10 = Bitmap.createBitmap(hero, Main.OneMove*10*scaling, 0, Main.OneMove*scaling, spriteHeight);
				hero11 = Bitmap.createBitmap(hero, Main.OneMove*11*scaling, 0, Main.OneMove*scaling, spriteHeight);
			}
		}
	}
}
