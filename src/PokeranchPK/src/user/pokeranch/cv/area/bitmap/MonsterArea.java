package user.pokeranch.cv.area.bitmap;

import java.util.Random;

import user.pokeranch.Main;
import user.pokeranch.cv.DrawableManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;


public class MonsterArea {
	private static Bitmap monster;
	private int PosIX;
	private int PosIY;
	private float PosX = (float) PosIX;
	private float PosY = (float) PosIY;
	private boolean stepped[][];
	private int xOffset;
	private int yOffset;
	private final int fps=60;
	private boolean kanan = false; // kondisi geraknya 
	private boolean kiri = false;
	private boolean atas = false;
	private boolean bawah = false;
	private int move_act;
	private int no_frame;
	private int type; // 0 jika random, 1 jika mendekat, 2 jika menjauh
	private int rangearea = 3; // area yang dijelajahi monster
	private static int scaling;
	
	public static void init() {
		monster = DrawableManager.getInstance().getMonster();
		if(Main.metrics.heightPixels > 800) {
			scaling = 2;
		} else {
			scaling = 1;
		}
	}
	
	public MonsterArea(int IX, int IY, int type){
		this.PosIX=IX;
		this.PosIY=IY;
		this.PosX = this.PosIX;
		this.PosY = this.PosIY;
		Log.d("MonsterArea", "CreateMonsterArea(" + IX + "," + IY +  ")");
		no_frame = 0;
		move_act = 0;
		this.type = type;
	}
	public int getPosX(){
		return PosIX;
	}
	public int getPosY(){
		return PosIY;
	}
	public void update_pos(int HeroIX, int HeroIY) {
		if(move_act == 0) {
			switch(type) {
				case 0:
					Random rand = new Random();
					int i=rand.nextInt(4) + 1;
					switch(i){
						case 1: Atas();
							break;
						case 2: Bawah();
							break;
						case 3: Kiri();
							break;
						case 4: Kanan();
							break;
					}
					Log.d("MonsterArea", "UPDATE POS!!! type = " + type + " RANDOM TO " + i);
					break;
				case 1: 
					decideMendekatMovement(HeroIX,HeroIY);
					break;
				case 2:
					decideMenjauhMovement(HeroIX, HeroIY);
					break;
			}
		}
	}
	
	private void decideMendekatMovement(int HeroIX,int HeroIY){
		int deltax=HeroIX-PosIX;
		int deltay=HeroIY-PosIY;
		int absdeltax=abs(deltax);
		int absdeltay=abs(deltay);
		
		if ((absdeltax<=rangearea) || (absdeltay<=rangearea)){
			if(absdeltax>=absdeltay){
				if(deltax>0)
					Kanan();
				else
					Kiri();
			}else{ // means deltax <= deltay ke kiri lebih jauh daripada ke kanan
				if(deltay>0)
					Bawah();
				else
					Atas();
			}
			Log.d("MonsterArea", "UPDATE POS!!! type = " + type);
		}
	}
	private void decideMenjauhMovement(int HeroIX,int HeroIY){
		int deltax=HeroIX-PosIX;
		int deltay=HeroIY-PosIY;
		int absdeltax=abs(deltax);
		int absdeltay=abs(deltay);
		
		if ((absdeltax<=rangearea) || (absdeltay<=rangearea)){
			if(absdeltax>=absdeltay){
				if(deltax>=0)
					Kiri();
				else
					Kanan();
			}else{ // means deltax <= deltay ke kiri lebih jauh daripada ke kanan
				if(deltay>=0)
					Atas();
				else
					Bawah();
			}
			Log.d("MonsterArea", "UPDATE POS!!! type = " + type);
		}
	}
	private int abs(int x){
		return x>=0 ? x : -x;
	}
	private boolean isLog = true;
	public void Draw(Canvas canvas, int move) {
		float x1 = PosX * move * scaling;
		float y1 = (PosY * move * scaling) - monster.getHeight();
		canvas.drawBitmap(monster, (x1 + xOffset), (y1 + yOffset), null);
		if(isLog) {
			Log.d("MonsterArea", "Draw at: (" + x1 + "," + y1 + ")");
			isLog = false;
		}
	}
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	public void dDraw() {
//		AddAtas();
		if(move_act == 1) {
			no_frame++;
			if(kanan){
				AddKanan();
			}else if(kiri){
				AddKiri();
			}else if(atas){
				AddAtas();
			}else{
				AddBawah();
			}
			if(no_frame == fps/2) {
				no_frame = 0;
				kanan = false;
				kiri = false;
				atas = false;
				bawah = false;
				move_act = 0;
			}
		}
	}

	private void ChangePos() {
		if((kanan || kiri || atas || bawah) && (move_act == 0)) {
			move_act = 1;
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
		if(stepped[PosIY][PosIX + 1]) {
			PosX += 2f/fps;
			if((PosX + 0.03) > (PosIX + 1)) { PosIX++; PosX = (float) PosIX; }
		}
	}
	private void AddKiri() {
		if(stepped[PosIY][PosIX - 1]) {
			PosX -= 2f/fps;
			if((PosX - 0.03) < (PosIX - 1)) { PosIX--; PosX = (float) PosIX; }
		}
	}
	private void AddAtas() {
		if(stepped[PosIY - 1][PosIX]) {
			PosY -= 2f/fps;
			if((PosY - 0.03) < (PosIY - 1)) { PosIY--; PosY = (float) PosIY; }
		}
	}
	private void AddBawah() {
		if(stepped[PosIY + 1][PosIX]) {
			PosY += 2f/fps;
			if((PosY + 0.03) > (PosIY + 1)) { PosIY++; PosY = (float) PosIY; }
		}
	}
	public void setStepped(boolean[][] _stepped) {
		stepped = _stepped;
	}
}
