package user.pokeranch.cv.area;

import user.pokeranch.cv.battle.BattleView;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoopThread extends Thread {
	private boolean running;
	private SurfaceHolder surfaceHolder;
	private HomeView homeView;	
	private CityView cityView;
	private OuterView outerView;
	private ShopView shopView;
	private CombinatoriumView combView;
	private StadiumView stadView;
	private ShoreView shoreView;
	private BattleView battleView;
	public final static int MAX_FPS = 60; //fps yang diinginkan	
	private final static int MAX_FRAME_SKIPS = 5; //maksimum jumlah frame yang bisa diskip
	private final static int FRAME_PERIOD = 1000/MAX_FPS;
	// 1) Home 2) 3) 4) 5) City 6) 7) 8) 9)outerarea 
	private int threadView;
	private boolean render;
	
	public GameLoopThread(SurfaceHolder surfaceHolder,HomeView homeView2 ) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.homeView = homeView2;
		this.threadView = 1;
		this.render = true;
	}
	
	public GameLoopThread(SurfaceHolder surfaceHolder, ShopView gameView) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.shopView = gameView;
		this.threadView = 2;
		this.render = true;
	}
	
	public GameLoopThread(SurfaceHolder surfaceHolder, CombinatoriumView gameView) {
		// TODO Auto-generated constructor stub
		super();
		this.surfaceHolder = surfaceHolder;
		this.combView = gameView;
		this.threadView = 3;
		this.render = true;
	}

	public GameLoopThread(SurfaceHolder surfaceHolder, StadiumView stadiumView) {
		// TODO Auto-generated constructor stub
		super();
		this.surfaceHolder = surfaceHolder;
		this.stadView = stadiumView;
		this.threadView = 4;
		this.render = true;
	}
	
	public GameLoopThread(SurfaceHolder surfaceHolder, CityView gameView) {
		// TODO Auto-generated constructor stub
		super();
		this.surfaceHolder = surfaceHolder;
		this.cityView = gameView;
		this.threadView = 5;
		this.render = true;
	}
	
	public GameLoopThread(SurfaceHolder surfaceHolder, OuterView gameView) {
		// TODO Auto-generated constructor stub
		super();
		this.surfaceHolder = surfaceHolder;
		this.outerView = gameView;
		this.threadView = 9;
		this.render = true;
	}

	public GameLoopThread(SurfaceHolder holder, ShoreView shoreView) {
		// TODO Auto-generated constructor stub
		super();
		this.surfaceHolder = holder;
		this.shoreView = shoreView;
		this.threadView = 8;
		this.render = true;
	}

	public GameLoopThread(SurfaceHolder holder, BattleView battleView) {
		super();
		this.surfaceHolder = holder;
		this.battleView = battleView;
		this.threadView = 6;
		this.render = true;
	}

	public void setRunning(boolean val) {
		running = val;
	}
	
	public void run() {	
		Canvas canvas;
		long beginTime; //waktu mulai siklus
		long timeDiff; //waktu yang diperlukan satu siklus untuk selesai
		int sleepTime; //ms untuk tidur(<0 jika ketinggalan)
		int framesSkipped; //jumlah frame yang akan diskip
		
		sleepTime = 0;
		
		while(running) {
			canvas = null;
			//ngunci canvas untuk digambar
			try{
				canvas = this.surfaceHolder.lockCanvas();
				synchronized(surfaceHolder) {
					beginTime = System.currentTimeMillis();
					framesSkipped = 0; // reset jumlah frame yang pengen diskip
					//update game state
					//draw canvas di panel
					if(render) {
						switch(threadView) {
							case 1:
								homeView.update();
								homeView.render(canvas); break;
							case 2:
								shopView.update();
								shopView.render(canvas); break;
							case 3:
								combView.update();
								combView.render(canvas); break;
							case 4:
								stadView.update();
								stadView.render(canvas); break;
							case 5:
								cityView.update();
								cityView.render(canvas); break;
							case 6:
								battleView.update();
								battleView.render(canvas); break;
							case 8:
								shoreView.update();
								shoreView.render(canvas); break;
							case 9:
								outerView.update();
								outerView.render(canvas); break;
						}
					}
					//hitung berapa lama satu siklus
					timeDiff = System.currentTimeMillis() - beginTime;					
					//hitung waktu tidur
					sleepTime = (int)(FRAME_PERIOD - timeDiff);
					
					if(sleepTime > 0) {
						//kalo waktu tidur positif
						//tidurin thread selama waktu tidur tsb
						//cycle lebih cepat dari fps
						try{							
							Thread.sleep(sleepTime);
						}catch(InterruptedException e) {							
						}
					}
					
					while(sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
						//ketinggalan fps, update tanpa manggil render
						switch(threadView) {
							case 1: homeView.update(); break;
							case 2: shopView.update(); break;
							case 3: combView.update(); break;
							case 4: stadView.update(); break;
							case 5: cityView.update(); break;
							case 6: battleView.update(); break;
							case 8: shoreView.update(); break;
							case 9: outerView.update(); break;
						}
						sleepTime += FRAME_PERIOD;
						framesSkipped++;
					}
				}
			} catch(Exception e) {
				
			} finally{
				// in case of an exception the surface is not left in
                // an inconsistent state
				if(canvas!=null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}			
		}
	}
	
	public void setRender(boolean render) {
		this.render = render;
	}
}
