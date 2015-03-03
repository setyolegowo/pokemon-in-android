package user.pokeranch.cv.battle;

import user.pokeranch.Main;
import user.pokeranch.cv.DrawableManager;
import user.pokeranch.cv.area.GameLoopThread;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BattleView extends SurfaceView implements SurfaceHolder.Callback {
	
	public interface BattleViewListener {
		public void FirstInteraction(int callAct);
		public void isMonsterCaught(int callAct);
	}
	
	private static final String TAG = BattleView.class.getSimpleName();
	private static volatile float screenWidth;
	private static volatile float screenHeight;
	
	public static float scale;
	private static int scaling;
	
	public GameLoopThread thread;
	private Matrix matrix = new Matrix();
	private BattleViewListener mBattleView;

	private Paint background;
	private Bitmap battleGrass;
	private Bitmap foe_box;
	private Bitmap player_box;
	private Bitmap health_bar;
	private Bitmap foe_hp;
	private Bitmap player_hp;
	private boolean FirstTransition;
	private boolean wait_event;
	private int callAct;
	private Paint TextName;
	private Bitmap monsterball;
	
	private static int foe_number;
	private Bitmap foe_bitmap;
	private String foe_name;
	private String foe_level;
	private Bitmap foe_status;
	private String player_name;
	private String player_level;
	private Bitmap player_status;
	private String player_maxHP;
	private String player_curHP;
	
	private static int trans;
	private static int trans2;
	
	private Position foeGrass;
	private Position foebitmap;
	private Position foeBox;
	private Position foeName;
	private Position foeLevel;
	private Position foeHPBar;
	private Position foeStatus;
	
	private Position playerGrass;
	private Position playerBox;
	private Position playerName;
	private Position playerLevel;
	private Position playerHPBar;
	private Position playerHPStatus;
	private Position playerStatus;
	
	public static boolean isUseMonsterBall;
	public static boolean isMonsterHilang;
	public static boolean isMonsterCaught;
	private static int player_number;
	private boolean isChangeFoeHP;
	private boolean isChangePlayerHP;
	private int foeMaxHP;
	private int foeCurHP;
	private int foeHPDraw;
	private int playerMaxHP;
	private int playerCurHP;
	private int playerHPDraw;
	private Bitmap player_bitmap;
	private Position playerbitmap;
	
	public BattleView(Context context) {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		Log.d(TAG,TAG + " initial bitmap...");
		BitmapShader shader = new BitmapShader(DrawableManager.getInstance().BattleBack(), Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
		background = new Paint();
		background.setShader(shader);
			battleGrass = DrawableManager.getInstance().BattleGrass();
			foe_box = DrawableManager.getInstance().foe_properties();
			foe_bitmap = DrawableManager.getInstance().MonsterPicture(foe_number);
			player_box = DrawableManager.getInstance().self_properties();
			player_bitmap = DrawableManager.getInstance().MonsterPictureBack(player_number);
			health_bar = DrawableManager.getInstance().health_bar();
		mBattleView = (BattleViewListener) context;
		TextName = new Paint();
		TextName.setTextSize(24);
		TextName.setTypeface(Typeface.SANS_SERIF);
		TextName.setColor(Color.BLACK);
		
		isMonsterHilang = false;
		isUseMonsterBall = false;
		isMonsterCaught = false;
		if(monsterball != null) monsterball.recycle();
		monsterball = monsterBall(UseItem.ball_use);
		
		if(Main.metrics.heightPixels > 800) {
			scaling = 2;
		} else {
			scaling = 1;
		}
		
		foeGrass = new Position(screenWidth/2, screenHeight/3 - battleGrass.getHeight()/8);
		foebitmap = new Position((screenWidth/2) + (foe_bitmap.getWidth()/2),screenHeight/3 - battleGrass.getHeight()/8 - foe_bitmap.getHeight()/2);
		foeBox = new Position(screenWidth/16, screenHeight/3 - battleGrass.getHeight()/4 - foe_box.getHeight()/4);
		foeName = new Position(screenWidth/16 + (20f*scale*scaling), screenHeight/3 + (27f*scale*scaling) - battleGrass.getHeight()/4 - foe_box.getHeight()/4);
		foeLevel = new Position(screenWidth/16 + (140f*scale*scaling), screenHeight/3 + (27f*scale*scaling) - battleGrass.getHeight()/4 - foe_box.getHeight()/4);
		foeHPBar = new Position(screenWidth/16 + (69f*scale*scaling), screenHeight/3 - battleGrass.getHeight()/4 - foe_box.getHeight()/4 + (37*scale*scaling));
		foeStatus = new Position(screenWidth/16 + (16f*scale*scaling), screenHeight/3 - battleGrass.getHeight()/4 - foe_box.getHeight()/4 + (35f*scale*scaling));
		
		
		playerGrass = new Position(0, screenHeight - battleGrass.getHeight()/2);
		playerbitmap = new Position(battleGrass.getWidth()/2 - player_bitmap.getWidth()/2, screenHeight - battleGrass.getHeight());
		playerBox = new Position((screenWidth*17/32), screenHeight - player_box.getHeight());
		playerName = new Position((screenWidth*17/32) + (30f*scale*scaling), screenHeight + (27f*scale*scaling) - player_box.getHeight());
		playerLevel = new Position((screenWidth*17/32) + (150f*scale*scaling), screenHeight + (27f*scale*scaling) - player_box.getHeight());
		playerHPBar = new Position((screenWidth*17/32) + (78f*scale*scaling), screenHeight - player_box.getHeight() + (37*scale*scaling));
		playerHPStatus = new Position((screenWidth*17/32) + (125f*scale*scaling), screenHeight + (60f*scale*scaling) - player_box.getHeight());
		playerStatus = new Position((screenWidth*17/32) + (25f*scale*scaling), screenHeight - player_box.getHeight() + (35f*scale*scaling));
		
		Log.d(TAG,TAG + " create successfully");
	}
	
	public void useBall() {
		monsterball = monsterBall(UseItem.ball_use - 1);
	}

	public static void screenSize(float width, float height) {
		screenWidth = width;
		trans = 0;
		trans2 = 0;
		screenHeight = height - 90*scale;
	}
	
	public static void foe_picture(int idFoeMonster) {
		foe_number = idFoeMonster;
	}
	
	public static void player_picture(int idFoeMonster) {
		player_number = idFoeMonster;
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
		FirstTransition = true;
		wait_event = false;
		Log.d(TAG, "surface created");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		releaseThread();
		callAct = 0;
		FirstTransition = false;
		wait_event = true;
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
		if(trans < screenWidth) {
			trans+=8;
		} else if(FirstTransition) {
			mBattleView.FirstInteraction(callAct);
			callAct++;
			FirstTransition = false;
			wait_event = true;
		}
		if (isChangeFoeHP) {
			if(foeHPDraw == foeCurHP) {
				isChangeFoeHP = false;
				wait_event = true;
			} else {
				if(foeHPDraw < foeCurHP) {
					foeHPDraw ++;
				} else {
					foeHPDraw --;
				}
			}
			foe_hp(100*foeHPDraw/foeMaxHP);
		}
		if(isChangePlayerHP) {
			if(playerHPDraw == playerCurHP) {
				isChangePlayerHP = false;
				wait_event = true;
			} else {
				if(playerHPDraw < playerCurHP) {
					playerHPDraw ++;
				} else {
					playerHPDraw --;
				}
			}
			player_hp(100*playerHPDraw/playerMaxHP);
		}
		if (isUseMonsterBall) {
			if(trans2 < screenWidth) {
				trans2+=8; 
			} else {
				mBattleView.isMonsterCaught(callAct);
				callAct++;
				if (isMonsterCaught) {
					isMonsterHilang = true;
				} else {
					isMonsterHilang = false;
				}
				isUseMonsterBall = false;
				trans2 = 0;
			}
		}
	}
	
	public void resetCallAct() {
		callAct = 0;
	}
	
	public void render(Canvas canvas) {
		canvas.setMatrix(matrix);
		canvas.drawColor(Color.WHITE);
		canvas.drawPaint(background);
		// FOE
		canvas.drawBitmap(battleGrass, foeGrass.getX() - (screenWidth - trans), foeGrass.getY(), null);
		if (!isMonsterHilang) {
			canvas.drawBitmap(foe_bitmap, foebitmap.getX() - (screenWidth - trans), foebitmap.getY(), null);
		}
		if (!isUseMonsterBall) {
			canvas.drawBitmap(foe_box, foeBox.getX() - (screenWidth - trans), foeBox.getY(), null);
			canvas.drawBitmap(foe_hp, foeHPBar.getX() - (screenWidth - trans), foeHPBar.getY(), null);
			canvas.drawText(foe_name, foeName.getX() - (screenWidth - trans), foeName.getY(), TextName);
			canvas.drawText("Lv" + foe_level, foeLevel.getX() - (screenWidth - trans), foeLevel.getY(), TextName);
			if(foe_status != null) canvas.drawBitmap(foe_status, foeStatus.getX() - (screenWidth - trans), foeStatus.getY(), null);
		} else {
			canvas.drawBitmap(monsterball, foebitmap.getX() - (screenWidth - trans2), foebitmap.getY(), null);
		}
		// PLAYER
		canvas.drawBitmap(battleGrass, playerGrass.getX() + (screenWidth - trans), playerGrass.getY(), null);
		canvas.drawBitmap(player_bitmap, playerbitmap.getX() + (screenWidth - trans), playerbitmap.getY(), null);
		canvas.drawBitmap(player_box, playerBox.getX() + (screenWidth - trans), playerBox.getY(), null);
		canvas.drawBitmap(player_hp, playerHPBar.getX() + (screenWidth - trans), playerHPBar.getY(), null);
		canvas.drawText(player_name, playerName.getX() + (screenWidth - trans), playerName.getY(), TextName);
		canvas.drawText("Lv"+player_level, playerLevel.getX() + (screenWidth - trans), playerLevel.getY(), TextName);
		if(player_status != null) canvas.drawBitmap(player_status, playerStatus.getX() + (screenWidth - trans), playerStatus.getY(), null);
		canvas.drawText(player_curHP + "/" + player_maxHP, playerHPStatus.getX() + (screenWidth - trans), playerHPStatus.getY(), TextName);
	}
	
	public void foe_hp(int percent) {
		foe_hp = Bitmap.createBitmap(health_bar, 0, 0, 5 + ((health_bar.getWidth() - 5)*percent/100), health_bar.getHeight());
	}
	
	public void update_foe_hp(int fromHP, int toHP, int maxHP) {
		foeMaxHP = maxHP;
		foeHPDraw = fromHP;
		foeCurHP = toHP;
		isChangeFoeHP = true;
		wait_event = false;
	}
	
	public void player_hp(int percent) {
		player_hp = Bitmap.createBitmap(health_bar, 0, 0,5 + ((health_bar.getWidth() - 5)*percent/100), health_bar.getHeight());
	}
	
	public void update_player_hp(int fromHP, int toHP, int maxHP) {
		playerMaxHP = maxHP;
		playerHPDraw = fromHP;
		playerCurHP = toHP;
		isChangePlayerHP = true;
		wait_event = false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d(TAG, "On Clik at : (" + event.getX() + "," + event.getY() + ")");
		return true;
	}
	
	public boolean isWaitEvent() {
		return wait_event;
	}
	
	public void FoeName(String foe_name) { this.foe_name = foe_name; }
	public void FoeLevel(String foe_level) { this.foe_level = foe_level; }
	public void FoeStat(int foe_status) { this.foe_status = status(foe_status); }
	
	public void PlayerName(String player_name) {this.player_name = player_name; }
	public void PlayerPicture(int id) {
		player_number = id;
		this.player_bitmap = DrawableManager.getInstance().MonsterPictureBack(player_number);}
	public void PlayerLevel(String player_level) {this.player_level = player_level; }
	public void PlayerStatus(int player_status) {this.player_status = status(player_status); }
	public void PlayerMaxHP(String player_maxHP) {this.player_maxHP = player_maxHP; }
	public void PlayerCurHP(String player_curHP) {this.player_curHP = player_curHP; }
	
	private Bitmap status(int i_status) {
		switch(i_status) {
		case 1 : return DrawableManager.getInstance().Paralyzed();
		case 2 : 
		case 3 : return DrawableManager.getInstance().Poison();
		case 4 : return DrawableManager.getInstance().Burn();
		case 5 : return DrawableManager.getInstance().Frozen();
		case 6 : return DrawableManager.getInstance().Sleep();
		default: return null;
		}
	}
	
	private Bitmap monsterBall(int ballIndex) {
		switch(ballIndex) {
		case 1: return Bitmap.createScaledBitmap(DrawableManager.getInstance().ItemPicture(1), DrawableManager.getInstance().ItemPicture(1).getWidth()/3, DrawableManager.getInstance().ItemPicture(1).getHeight()/3, false);
		case 2: return Bitmap.createScaledBitmap(DrawableManager.getInstance().ItemPicture(2), DrawableManager.getInstance().ItemPicture(2).getWidth()/3, DrawableManager.getInstance().ItemPicture(2).getHeight()/3, false);
		case 3: return Bitmap.createScaledBitmap(DrawableManager.getInstance().ItemPicture(3), DrawableManager.getInstance().ItemPicture(3).getWidth()/3, DrawableManager.getInstance().ItemPicture(3).getHeight()/3, false);
		default: return null;
		}
	}
	
	private class Position {
		private float x;
		private float y;
		public Position(float X, float Y) {
			x = X;
			y = Y;
		}
		public float getX() {
			return x;
		}
		public float getY() {
			return y;
		}
	}

}
