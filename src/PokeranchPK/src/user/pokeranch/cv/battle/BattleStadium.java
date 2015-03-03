package user.pokeranch.cv.battle;

import user.pokeranch.Main;
import user.pokeranch.cv.DrawableManager;
import user.pokeranch.cv.area.StadiumActivity;
import user.pokeranch.models.Monster;
import user.pokeranch.models.PlayerMonster;
import user.pokeranch.models.PlayerParty;
import user.pokeranch.models.PokeDB;
import user.pokeranch.models.Skill;
import user.pokeranch.models.type.Element;
import user.pokeranch.models.type.StatusEffect;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BattleStadium extends Activity implements BattleView.BattleViewListener {

	private static final String TAG = BattleStadium.class.getSimpleName();
	public static final String MONSTER_PARTY_ACTIVE = "MONSTER_PARTY_ACTIVE";
	public static int isNextLevel = 0;
	public static boolean blackout = false;
	private byte skill_use;
	private byte skill_use_turn;
	private byte interaction_skill_use;
	private DisplayMetrics metrics;
	private RelativeLayout Game;
	private BattleView battle;
	private TextView interact;
	private int number_interaction;
	private boolean finalize = false;
	
	private LinearLayout boxFront;
	TextView fight;
	TextView monster;
	TextView bag;
	TextView escape;
	
	LinearLayout skillUse;
	TextView infoSkill;
	TextView skill1;
	TextView skill2;
	TextView skill3;
	TextView skill4;
	private PokeDB db;
	
	private int foe_id;
	MonsterBattle foeMonster;
	MonsterBattle playerMonster;
	private boolean IsPlayerFirst;
	private Skill[] foeSkill = new Skill[4];
	private Skill[] playerSkill = new Skill[4];
	private boolean pending_effect;
	private byte player_skill_selected;
	private int total_bet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		db = new PokeDB(this);
		
		Intent intent = getIntent();
		
		total_bet = intent.getIntExtra(StadiumActivity.TOTAL_BET, 50000);
		
		init_battle_content();
		
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics); // dapetin ukuran layar
		// DISABLE SCREEN OUT
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		Game = new RelativeLayout(this);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		Game.setLayoutParams(params);
		Game.setGravity(0x05);
		
		// TEXT VIEW
		interact = new TextView(this);
        interact.setBackgroundDrawable(DrawableManager.getInstance().getBlueBoxBitmap());
        params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.setMargins(0, (int) (metrics.heightPixels - 90*metrics.density), 0, 0);
        interact.setGravity(Gravity.TOP);
        interact.setLayoutParams(params);
        interact.setOnClickListener(new InteractorListener());
		interact.setText("...");
        interact.setTextSize(18);
        interact.setTextColor(Color.BLACK);
        number_interaction = 0;
        
        // Button Fight, Monster, Bag, Run
        ColorStateList fontColor = new ColorStateList (
		   new int [] [] {
			      new int [] {android.R.attr.state_pressed},
			      new int [] {}
			   },
			   new int [] {
			      Color.rgb (255, 128, 192),
			      Color.BLACK
			   }
			);
        boxFront = new LinearLayout(this);
        boxFront.setOrientation(LinearLayout.HORIZONTAL);
        boxFront.setBackgroundDrawable(DrawableManager.getInstance().getBlackBoxBitmap());
        params = new RelativeLayout.LayoutParams((int) (225*metrics.density), (int) (90*metrics.density));
        params.setMargins((int) (metrics.widthPixels - 225*metrics.density), (int) (metrics.heightPixels - 90*metrics.density), 0, 0);
        boxFront.setLayoutParams(params);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams((int) (112.5*metrics.density), (int)(90*metrics.density));
        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params3.setMargins(0, (int) (10*metrics.density), 0, (int) (10*metrics.density));
        LinearLayout kiri = new LinearLayout(this);
        	kiri.setOrientation(LinearLayout.VERTICAL);
        	kiri.setLayoutParams(params2);
        	// Fight
        	fight = new TextView(this);
        	fight.setText("Fight");
        	fight.setTextColor(fontColor);
        	fight.setTextSize(20);
        	fight.setOnClickListener(new FightListener());
        	kiri.addView(fight);
        	// Monster
        	monster = new TextView(this);
        	monster.setText("Monster");
        	monster.setTextColor(fontColor);
        	monster.setTextSize(20);
        	monster.setLayoutParams(params3);
        	monster.setOnClickListener(new MonsterListener());
        	kiri.addView(monster);
        LinearLayout kanan = new LinearLayout(this);
        	kanan.setOrientation(LinearLayout.VERTICAL);
        	kanan.setLayoutParams(params2);
        	// BAG
	        bag = new TextView(this);
	    	bag.setText("");
	    	bag.setTextColor(fontColor);
	    	bag.setTextSize(20);
	    	kanan.addView(bag);
	    	// Escape
	    	escape = new TextView(this);
	    	escape.setText("");
	    	escape.setTextColor(fontColor);
	    	escape.setTextSize(20);
	    	escape.setLayoutParams(params3);
	    	kanan.addView(escape);
	    boxFront.addView(kiri);
	    boxFront.addView(kanan);
        boxFront.setVisibility(View.GONE);
        
        // Use Skill
        skillUse = new LinearLayout(this);
        skillUse.setOrientation(LinearLayout.HORIZONTAL);
        skillUse.setBackgroundDrawable(DrawableManager.getInstance().getBoxBitmap());
        params3 = new RelativeLayout.LayoutParams((int) (metrics.widthPixels - 150*metrics.density), (int) (90*metrics.density));
        params3.setMargins(0, (int) (metrics.heightPixels - 90*metrics.density), 0, 0);
        skillUse.setLayoutParams(params3);
	        params3 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	        params3.setMargins(0, (int) (10*metrics.density), 0, (int) (10*metrics.density));
        	// Skill 1 3
        	LinearLayout skill_1_3 = new LinearLayout(this);
        	skill_1_3.setOrientation(LinearLayout.VERTICAL);
        	params2 = new LinearLayout.LayoutParams((int) (metrics.widthPixels - 180*metrics.density)/2, (int) (90*metrics.density));
        	skill_1_3.setLayoutParams(params2);
        	//skill_1_3.setBackgroundColor(0xFF00FF00);
        	skill1 = new TextView(this);
        	skill1.setTextColor(fontColor);
        	skill1.setTextSize(20);
        	skill1.setOnClickListener(new Skill1Listener());
        	skill3 = new TextView(this);
        	skill3.setTextColor(fontColor);
        	skill3.setTextSize(20);
        	skill3.setLayoutParams(params3);
        	skill3.setOnClickListener(new Skill3Listener());
        	skill_1_3.addView(skill1);
        	skill_1_3.addView(skill3);
        	// Skill 2 4
        	LinearLayout skill_2_4 = new LinearLayout(this);
        	skill_2_4.setOrientation(LinearLayout.VERTICAL);
        	skill_2_4.setLayoutParams(params2);
        	//skill_2_4.setBackgroundColor(0xFF0000FF);
        	skill2 = new TextView(this);
        	skill2.setTextColor(fontColor);
        	skill2.setTextSize(20);
        	skill2.setOnClickListener(new Skill2Listener());
        	skill4 = new TextView(this);
        	skill4.setTextColor(fontColor);
        	skill4.setTextSize(20);
        	skill4.setLayoutParams(params3);
        	skill4.setOnClickListener(new Skill4Listener());
        	skill_2_4.addView(skill2);
        	skill_2_4.addView(skill4);
        skillUse.addView(skill_1_3);
        skillUse.addView(skill_2_4);
        skillUse.setVisibility(View.GONE);
        
        // Info Skill
        infoSkill = new TextView(this);
        params = new RelativeLayout.LayoutParams((int) (150*metrics.density), (int) (90*metrics.density));
        params.setMargins((int) (metrics.widthPixels - 150*metrics.density), (int) (metrics.heightPixels - (90*metrics.density)), 0, 0);
        infoSkill.setLayoutParams(params);
        infoSkill.setText("");
        infoSkill.setTextColor(Color.BLACK);
        infoSkill.setTextSize(16);
        infoSkill.setBackgroundDrawable(DrawableManager.getInstance().getBlackBox2Bitmap());
        infoSkill.setOnClickListener(new InfoSkillListener());
        infoSkill.setVisibility(View.GONE);

		BattleView.scale = metrics.density;
		BattleView.screenSize(metrics.widthPixels, metrics.heightPixels);
		BattleView.foe_picture(foe_id);
		battle = new BattleView(this);
		
		battle_content();
        
        Game.addView(battle);
        Game.addView(interact);
        Game.addView(boxFront);
        Game.addView(skillUse);
        Game.addView(infoSkill);
        
    	isNextLevel = 0;
    	blackout = false;
    	try_escape = 0;
    	player_skill_selected = 0;
		
		setContentView(Game);
	}
	private int monster_party_number;
	private void init_battle_content() {
		Monster monster = Monster.getRandomMonsterStadium(db.getReadableDatabase());
		foe_id = monster.getID();
		foeMonster = new MonsterBattle(monster);
		int i = 0;
		playerMonster = new MonsterBattle(PlayerMonster.getPlayerMonster(PlayerParty.getMonsterIndex(i)));
		while(playerMonster.getCurHP() == 0) {
			i = i + 1;
			playerMonster = new MonsterBattle(PlayerMonster.getPlayerMonster(PlayerParty.getMonsterIndex(i)));
		}
		monster_party_number = i;
		foeSkill[0] = new Skill(foeMonster.getSkill1(),db.getReadableDatabase());
		foeSkill[1] = new Skill(foeMonster.getSkill2(),db.getReadableDatabase());
		foeSkill[2] = new Skill(foeMonster.getSkill3(),db.getReadableDatabase());
		foeSkill[3] = new Skill(foeMonster.getSkill4(),db.getReadableDatabase());
		playerSkill[0] = new Skill(playerMonster.getSkill1(),db.getReadableDatabase());
		playerSkill[1] = new Skill(playerMonster.getSkill2(),db.getReadableDatabase());
		playerSkill[2] = new Skill(playerMonster.getSkill3(),db.getReadableDatabase());
		playerSkill[3] = new Skill(playerMonster.getSkill4(),db.getReadableDatabase());
	}
	
	private void battle_content() {
		// BATTLE CONTENT
        battle.FoeName(foeMonster.getName());
        battle.FoeLevel("" + foeMonster.getLevel());
        battle.FoeStat(foeMonster.getNoStatus());
        battle.PlayerName(playerMonster.getName());
        battle.PlayerLevel("" + playerMonster.getLevel());
        battle.PlayerStatus(playerMonster.getNoStatus());
        battle.PlayerCurHP("" + playerMonster.getCurHP());
        battle.PlayerMaxHP("" + playerMonster.getMaxHP());
        
		if(playerMonster.getSkill1() > 0) skill1.setText(playerSkill[0].getName());
		if(playerMonster.getSkill2() > 0) skill2.setText(playerSkill[1].getName());
		if(playerMonster.getSkill3() > 0) skill3.setText(playerSkill[2].getName());
		if(playerMonster.getSkill4() > 0) skill4.setText(playerSkill[3].getName());
        
        if(number_interaction < 3) {
	        battle.foe_hp(100*foeMonster.getCurHP()/foeMonster.getMaxHP());
	        battle.player_hp(100*playerMonster.getCurHP()/playerMonster.getMaxHP());
        }
	}
	
	@Override
	public void onBackPressed() {
		if(infoSkill.getVisibility() == View.VISIBLE) {
			player_skill_selected = 0;
			infoSkill.setVisibility(View.GONE);
			skillUse.setVisibility(View.GONE);
			interact.setVisibility(View.VISIBLE);
			boxFront.setVisibility(View.VISIBLE);
			infoSkill.setText("");
		} else {
			super.onBackPressed();
		}
	}
	
	private void resume_battle_content() {
		playerMonster = new MonsterBattle(PlayerMonster.getPlayerMonster(PlayerParty.getMonsterIndex(monster_party_number)));
		playerSkill[0] = new Skill(playerMonster.getSkill1(),db.getReadableDatabase());
		playerSkill[1] = new Skill(playerMonster.getSkill2(),db.getReadableDatabase());
		playerSkill[2] = new Skill(playerMonster.getSkill3(),db.getReadableDatabase());
		playerSkill[3] = new Skill(playerMonster.getSkill4(),db.getReadableDatabase());
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(BattleChangeMonster.isChange) {
			this.monster_party_number = BattleChangeMonster.monster_party_number;
			resume_battle_content();
			battle_content();
			IsPlayerFirst = true;
			skill_use_turn = 2;
			interaction_skill_use = 1;
			boxFront.setVisibility(View.GONE);
			interact.setText(Main.player.getName() + " mengganti monster\nmenjadi " + playerMonster.getName());
			BattleChangeMonster.isChange = false;
		}
	}
	
	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if(battle.thread != null) battle.thread.setRunning(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return false;
	}
	
	private class InteractorListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			if(battle.isWaitEvent()) {
				if((number_interaction < 3) && (skill_use == 0)) {
					interact(number_interaction);
					number_interaction++;
				} else if(skill_use_turn > 0) {
					player_use_skill();
					foe_use_skill();
					check_finalize();
				} else if(finalize) {
					Log.d(TAG, "finalize, HP FOE = " + foeMonster.getCurHP());
					if(foeMonster.getCurHP() == 0) {
						if(exp_get == 0) { // change HP too
							exp_get = foeMonster.getLevel()*foeMonster.getLevel()*foeMonster.getLevel()*foeMonster.getLevel()/4;
							Log.d(TAG, "EXP = " + exp_get);
							isNextLevel = PlayerMonster.incEXPOfMonsterIndex(exp_get, PlayerParty.getMonsterIndex(monster_party_number),db.getReadableDatabase());
							// Change HP
							PlayerMonster.changeHPOfMonsterIndex(playerMonster.getCurHP(), PlayerParty.getMonsterIndex(monster_party_number));
						}
						if(money_get == 0) {
							Intent intent = getIntent();
							money_get = intent.getIntExtra(StadiumActivity.TOTAL_BET, 50000);
							Main.player.setCurMoney(Main.player.getCurMoney() + money_get);
						}
						interact(number_interaction);
						number_interaction++;
						if(number_interaction > 6)
							outFromBattleMode();
					} else if(EscapeSuccesfully) {
						if(number_interaction + 8 <= 11) {
							interact(number_interaction + 8);
							number_interaction++;
						} else {
							outFromBattleMode();
						}
					} else {
						if(number_interaction + 4 <= 10) {
							interact(number_interaction + 4);
							number_interaction++;
						} else {
							outFromBattleMode();
						}
					}
				}
				Log.d(TAG, "number_interaction: " + number_interaction + "; skill_use_turn: " + skill_use_turn);
			}
		}
		
	}

	@Override
	public void FirstInteraction(int callAct) {
		Log.d(TAG,"First Interaction");
		if(callAct == 1) {
			// interact(0);
			// number_interaction = 1;
		}
	}
	
	public void check_finalize() {
		if((playerMonster.getCurHP() == 0) || (foeMonster.getCurHP() == 0)) {
			finalize = true;
			skill_use_turn = 0;
			exp_get = 0;
			money_get = 0;
		} else if(EscapeSuccesfully) {
			finalize = true;
		}
		else finalize = false;
	}
	
	private int exp_get;
	private int money_get;
	public int try_escape;
	public boolean EscapeSuccesfully;

	public void interact(int i) {
		switch(i) {
			case 0: interact.setText("Monster liar " + foeMonster.getName() + " muncul!"); break;
			case 1: interact.setText("Ayo! " + playerMonster.getName() + "!"); break; 
			case 2: interact.setText("Apa yang akan\n" + playerMonster.getName() + " lakukan?");
				boxFront.setVisibility(View.VISIBLE);
				break;
			case 3: interact.setText("Monster liar \n" + foeMonster.getName() + " kalah."); break;
			case 4: interact.setText(playerMonster.getName() + " mendapatkan\n" + exp_get + " poin EXP.!"); break;
			case 5: interact.setText(Main.player.getName() + " mendapatkan\n" + money_get + " Zeny dari hasil bet!"); break;
			
			case 7: interact.setText(playerMonster.getName() + " kalah!"); break;
			case 8: interact.setText("Menggunakan monster lain?"); Intent intent = new Intent(getApplicationContext(), BattleChangeMonster.class);
				intent.putExtra(MONSTER_PARTY_ACTIVE, monster_party_number);
				startActivity(intent);break;
			case 9: interact.setText("Pemain tidak mempunyai lagi\nmonster dalam party-nya."); break;
			case 10: interact.setText("Pemain akan dikeluarkan.");
				Main.player.setCurMoney(Main.player.getCurMoney()/2);
				blackout = true;
			break;
			case 11 : interact.setText("Kabur berhasil."); break;
		}
	}
	
	private void outFromBattleMode() {
		finish();
	}
	
	private class FightListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			boxFront.setVisibility(View.GONE);
			interact.setVisibility(View.GONE);
			skillUse.setVisibility(View.VISIBLE);
			infoSkill.setVisibility(View.VISIBLE);
		}
		
	}
	
	private class MonsterListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(getApplicationContext(), BattleChangeMonster.class);
			intent.putExtra(MONSTER_PARTY_ACTIVE, monster_party_number);
			startActivity(intent);
		}
	}	
	
	private class Skill1Listener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			if(playerMonster.getSkill1() != 0) {
				if(player_skill_selected != 1) {
					player_skill_selected = 1;
					infoSkill.setTextColor(Color.BLACK);
					infoSkill.setText("PP   " + playerMonster.getPPSkill1() + "/" + playerSkill[0].getDefaultPP() + "\n" +
						"Elemen: " + Element.getNameElement(db.getReadableDatabase(), playerSkill[0].getElemenID()) + "\n" + 
						"Use it");
				}
			}
		}
		
	}
	
	private class Skill2Listener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			if(playerMonster.getSkill2() != 0) {
				if(player_skill_selected != 2) {
					player_skill_selected = 2;
					infoSkill.setTextColor(Color.BLACK);
					infoSkill.setText("PP   " + playerMonster.getPPSkill2() + "/" + playerSkill[1].getDefaultPP() + "\n" +
						"Elemen: " + Element.getNameElement(db.getReadableDatabase(), playerSkill[1].getElemenID()) + "\n" + 
						"Use it");
				}
			}
		}
		
	}
	
	private class Skill3Listener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			if(playerMonster.getSkill3() != 0) {
				if(player_skill_selected != 3) {
					player_skill_selected = 3;
					infoSkill.setTextColor(Color.BLACK);
					infoSkill.setText("PP   " + playerMonster.getPPSkill3() + "/" + playerSkill[2].getDefaultPP() + "\n" +
						"Elemen: " + Element.getNameElement(db.getReadableDatabase(), playerSkill[2].getElemenID()) + "\n" + 
						"Use it");
				}
			}
		}
		
	}
	
	private class Skill4Listener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			if(playerMonster.getSkill4() != 0) {
				if(player_skill_selected != 4) {
					player_skill_selected = 4;
					infoSkill.setTextColor(Color.BLACK);
					infoSkill.setText("PP   " + playerMonster.getPPSkill4() + "/" + playerSkill[3].getDefaultPP() + "\n" +
						"Elemen: " + Element.getNameElement(db.getReadableDatabase(), playerSkill[3].getElemenID()) + "\n" + 
						"Gunakan");
				}
			}
		}
		
	}
	
	private class InfoSkillListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(player_skill_selected != 0)
				using_skill();
		}
		
	}
	
	private void using_skill() {
		boolean useSkill = true;
		switch(player_skill_selected) {
			case 1: if(playerMonster.getPPSkill1() == 0) useSkill = false; break;
			case 2: if(playerMonster.getPPSkill2() == 0) useSkill = false; break;
			case 3: if(playerMonster.getPPSkill3() == 0) useSkill = false; break;
			case 4: if(playerMonster.getPPSkill4() == 0) useSkill = false; break;
		}
		if(useSkill) {
			skill_use = player_skill_selected;
			player_skill_selected = 0;
			skill_use_turn = 1;
			interaction_skill_use = 1;
			if((foeMonster.getSpeed() < playerMonster.getSpeed()) && (Math.random() > 0.2))
	            IsPlayerFirst = true;
	        else
	            IsPlayerFirst = false;
			use_skill();
			player_use_skill();
		} else {
			infoSkill.setTextColor(Color.RED);
		}
	}
	
	private void use_skill() {
		interact.setText("...");
		interact.setVisibility(View.VISIBLE);
		skillUse.setVisibility(View.GONE);
		infoSkill.setVisibility(View.GONE);
	}
	
	private void player_use_skill() {
		if((IsPlayerFirst && skill_use_turn == 1) || (!IsPlayerFirst && skill_use_turn == 2)) {
			Log.d(TAG, "Player: skill_use_turn = " + skill_use_turn);
			switch(interaction_skill_use) {
				case 1:
					Log.d(TAG, "PLAYER SKILL NUM: " + skill_use);
					if(playerSkill[skill_use - 1] == null) {
						finish();
						Log.d(TAG, "SKILL IS NULL!!!");
					}
					interact.setText(playerMonster.getName() + " menggunakan skill\n"
					+ playerSkill[skill_use - 1].getName());
					if((int) ((double) (playerSkill[skill_use - 1].getAccuracy() + (((Math.random()*20) + 96)/100))*playerMonster.getAccuracy()) > 100) {
						if(playerSkill[skill_use - 1].getTarget() == 1) {
							if(playerSkill[skill_use - 1].getPowerEffectID() == 0) {
								interaction_skill_use = 2;
							} else {
								interaction_skill_use = 3;
							}
						} else {
							interaction_skill_use = 4;
						}
						playerMonster.setPPSkill1(playerMonster.getPPSkill1() - 1);
					} else {
						interaction_skill_use = 5;
					}
					pending_effect = false;
				break;
				case 2: // Target FOE HP
					double Damage = (((((playerMonster.getLevel()*2/5)+2)*playerMonster.getAttack()*playerSkill[skill_use - 1].getPower()/(50*foeMonster.getDefense()))+2)*(((Math.random()*20)+194)/100)*(100 - (Math.random()*16))/100);
					if(playerSkill[skill_use - 1].getStrength() == foeMonster.getElemen_No()) {
						Damage = Damage*2;
						interaction_skill_use = 6;
					} else if(playerSkill[skill_use - 1].getWeakness() == foeMonster.getElemen_No()) {
						Damage = Damage/2;
						interaction_skill_use = 7;
					} else {
						if(skill_use_turn == 1) {
							skill_use_turn = 2;
							interaction_skill_use = 1;
						} else {
							interaction_skill_use = 8;
						}
					}
					int fromHP = foeMonster.getCurHP();
					if(foeMonster.getCurHP() < Damage) foeMonster.setCurHP(0);
                    else foeMonster.setCurHP((int) (foeMonster.getCurHP()-Damage));
					battle.update_foe_hp(fromHP, foeMonster.getCurHP(), foeMonster.getMaxHP());
					
					// Add effect to foo
                    if(playerSkill[skill_use - 1].getStatusEffect() > 0) {
                        if((playerSkill[skill_use - 1].getProbStatEffect()*((Math.random()*20) + 95)/100) > 100) {
                            if(foeMonster.getNoStatus() == 0){
                                pending_effect = true;
                            }
                        }
                    }
					break;
				case 3: // Target FOE non-HP
					switch(playerSkill[skill_use - 1].getPowerEffectID()) {
						case 1: foeMonster.setAttack(foeMonster.getAttack() - foeMonster.getAttack()/10); 
							interact.setText("Kekuatan serang " + foeMonster.getName() + " menurun.");
							break;
						case 2: foeMonster.setDefense(foeMonster.getDefense() - foeMonster.getDefense()/10); 
							interact.setText("Pertahanan serang " + foeMonster.getName() + " menurun.");
							break;
						case 3: foeMonster.setSpeed(foeMonster.getSpeed() - foeMonster.getSpeed()/10); 
							interact.setText("Kecepatan serang " + foeMonster.getName() + " menurun.");
							break;
						case 4: foeMonster.setAccuracy(foeMonster.getAccuracy() - foeMonster.getAccuracy()/10); 
							interact.setText("Akurasi serang " + foeMonster.getName() + " menurun.");
							break;
					}
					skill_use = 0;
					if(skill_use_turn == 1) {
						skill_use_turn = 2;
						interaction_skill_use = 1;
					} else {
						interaction_skill_use = 8;
					}
					break;
				case 4: // Target self
					switch(playerSkill[skill_use - 1].getPowerEffectID()) {
	                    case 0:
	                        interact.setText("Health point " + playerMonster.getName() + "\nmeningkat");
	                        if(playerMonster.getCurHP()+playerSkill[skill_use - 1].getPower() >= playerMonster.getMaxHP())
	                            playerMonster.setCurHP(playerMonster.getMaxHP());
	                        else
	                            playerMonster.setCurHP(playerMonster.getCurHP() + playerSkill[skill_use - 1].getPower());
	                        break;
	                    case 1: playerMonster.setAttack(playerMonster.getAttack() + playerMonster.getAttack()/10); 
	                    	interact.setText("Kekuatan serang " + playerMonster.getName() + "\nmeningkat");
	                    	break;
	                    case 2: playerMonster.setAttack(playerMonster.getDefense()+playerMonster.getDefense()/10); 
	                    	interact.setText("Pertahanan " + playerMonster.getName() + "\nmeningkat");
	                    	break;
	                    case 3: playerMonster.setSpeed(playerMonster.getSpeed()+playerMonster.getSpeed()/10);
	                    	interact.setText("Kecepatan " + playerMonster.getName() + " meningkat");
	                    	break;
	                    case 4: playerMonster.setAccuracy(playerMonster.getAccuracy()+playerMonster.getAccuracy()/10);
	                    	interact.setText("Akurasi " + playerMonster.getName() + " meningkat");
	                    	break;
					}
					if(skill_use_turn == 1) {
						skill_use_turn = 2;
						interaction_skill_use = 1;
					} else {
						interaction_skill_use = 8;
					}
					break;
				case 5: interact.setText("Serangan " + playerMonster.getName() + " gagal.");
						if(skill_use_turn == 1) {
							skill_use_turn = 2;
							interaction_skill_use = 1;
						} else {
							interaction_skill_use = 8;
						}
						break;
				case 6: 
					interact.setText("Skill " + playerSkill[skill_use - 1].getName() + "\nsangat efektif.");
					if(skill_use_turn == 1) {
						skill_use_turn = 2;
						interaction_skill_use = 1;
					} else {
						interaction_skill_use = 8;
					}
					break;
				case 7:
					interact.setText("Skill " + playerSkill[skill_use - 1].getName() + "\nsangat tidak efektif.");
					if(skill_use_turn == 1) {
						skill_use_turn = 2;
						interaction_skill_use = 1;
					} else {
						interaction_skill_use = 8;
					}
					break;
				case 8:
					if(pending_effect) {
						foeMonster.setNoStatus(playerSkill[skill_use - 1].getStatusEffect());
						interact.setText("Monster liar terkena efek " +
							StatusEffect.getNameStatusEffect(db.getReadableDatabase(), playerSkill[skill_use - 1].getStatusEffect()));
						pending_effect = false;
					}
					skill_use = 0;
					interact(2);
					skill_use_turn = 0;
					break;
			}
		}
		battle_content();
	}
	private int monster_skill_use;
	private void foe_use_skill() {
		if((!IsPlayerFirst && skill_use_turn == 1) || (IsPlayerFirst && skill_use_turn == 2)) {
			Log.d(TAG, "Foe: skill_use_turn = " + skill_use_turn);
			switch(interaction_skill_use) {
				case 1: 
					int num = (int) (Math.random()*3) + 1;
					Log.d(TAG, "FOE SKILL NUM: " + num);
					if(foeSkill[num - 1] == null) {
						finish();
						Log.d(TAG, "SKILL IS NULL!!!");
					}
					while(foeSkill[num - 1].getID() == 0) {
						num = (int) (Math.random()*3) + 1;
						Log.d(TAG, "FOE SKILL NUM: " + num);
						if(foeSkill[num - 1] == null) {
							finish();
							Log.d(TAG, "SKILL IS NULL!!!");
						}
					}
					monster_skill_use = num;
					interact.setText(foeMonster.getName() + " menggunakan skill \n"
					+ foeSkill[monster_skill_use - 1].getName());
					if((int) ((double) (foeSkill[monster_skill_use - 1].getAccuracy() + (((Math.random()*20) + 96)/100))*playerMonster.getAccuracy()) > 100) {
						if(foeSkill[monster_skill_use - 1].getTarget() == 1) {
							if(foeSkill[monster_skill_use - 1].getPowerEffectID() == 0) {
								interaction_skill_use = 2;
							} else {
								interaction_skill_use = 3;
							}
						} else {
							interaction_skill_use = 4;
						}
					} else {
						interaction_skill_use = 5;
					}
					pending_effect = false;
				break;
				case 2: // Target PLAYER HP
					double Damage = (((((foeMonster.getLevel()*2/5)+2)*foeMonster.getAttack()*foeSkill[monster_skill_use - 1].getPower()/(50*playerMonster.getDefense()))+2)*(((Math.random()*20)+194)/100)*(100 - (Math.random()*16))/100);
					if(foeSkill[monster_skill_use - 1].getStrength() == playerMonster.getElemen_No()) {
						Damage = Damage*2;
						interaction_skill_use = 6;
					} else if(foeSkill[monster_skill_use - 1].getWeakness() == playerMonster.getElemen_No()) {
						Damage = Damage/2;
						interaction_skill_use = 7;
					} else {
						if(skill_use_turn == 1) {
							skill_use_turn = 2;
							interaction_skill_use = 1;
						} else {
							interaction_skill_use = 8;
						}
					}
					int fromHP = playerMonster.getCurHP();
					if(playerMonster.getCurHP() < Damage) playerMonster.setCurHP(0);
                    else playerMonster.setCurHP((int) (playerMonster.getCurHP()-Damage));
					battle.update_player_hp(fromHP, playerMonster.getCurHP(), playerMonster.getMaxHP());
					
					// Add effect to player
                    if(foeSkill[monster_skill_use - 1].getStatusEffect() > 0) {
                        if((foeSkill[monster_skill_use - 1].getProbStatEffect()*((Math.random()*20) + 95)/100) > 100) {
                            if(playerMonster.getNoStatus() == 0){
                                pending_effect = true;
                            }
                        }
                    }
					break;
				case 3: // Target PLAYER non-HP
					switch(foeSkill[monster_skill_use - 1].getPowerEffectID()) {
						case 1: playerMonster.setAttack(playerMonster.getAttack() - playerMonster.getAttack()/10); 
							interact.setText("Kekuatan serang " + playerMonster.getName() + " menurun.");
							break;
						case 2: playerMonster.setDefense(playerMonster.getDefense() - playerMonster.getDefense()/10); 
							interact.setText("Pertahanan serang " + playerMonster.getName() + " menurun.");
							break;
						case 3: playerMonster.setSpeed(playerMonster.getSpeed() - playerMonster.getSpeed()/10); 
							interact.setText("Kecepatan serang " + playerMonster.getName() + " menurun.");
							break;
						case 4: playerMonster.setAccuracy(playerMonster.getAccuracy() - playerMonster.getAccuracy()/10); 
							interact.setText("Akurasi serang " + playerMonster.getName() + " menurun.");
							break;
					}
					if(skill_use_turn == 1) {
						skill_use_turn = 2;
						interaction_skill_use = 1;
					} else {
						interaction_skill_use = 8;
					}
					break;
				case 4: // Target self
					switch(foeSkill[monster_skill_use - 1].getPowerEffectID()) {
	                    case 0:
	                        interact.setText("Health point " + foeMonster.getName() + "\nmeningkat");
	                        if(foeMonster.getCurHP()+foeSkill[monster_skill_use - 1].getPower() >= foeMonster.getMaxHP())
	                            foeMonster.setCurHP(foeMonster.getMaxHP());
	                        else
	                            foeMonster.setCurHP(foeMonster.getCurHP() + foeSkill[monster_skill_use - 1].getPower());
	                        break;
	                    case 1: foeMonster.setAttack(foeMonster.getAttack() + foeMonster.getAttack()/10); 
	                    	interact.setText("Kekuatan serang " + foeMonster.getName() + "\nmeningkat");
	                    	break;
	                    case 2: foeMonster.setAttack(foeMonster.getDefense()+foeMonster.getDefense()/10); 
	                    	interact.setText("Pertahanan " + foeMonster.getName() + "\nmeningkat");
	                    	break;
	                    case 3: foeMonster.setSpeed(foeMonster.getSpeed()+foeMonster.getSpeed()/10);
	                    	interact.setText("Kecepatan " + foeMonster.getName() + " meningkat");
	                    	break;
	                    case 4: foeMonster.setAccuracy(foeMonster.getAccuracy()+foeMonster.getAccuracy()/10);
	                    	interact.setText("Akurasi " + foeMonster.getName() + " meningkat");
	                    	break;
					}
					if(skill_use_turn == 1) {
						skill_use_turn = 2;
						interaction_skill_use = 1;
					} else {
						interaction_skill_use = 8;
					}
					break;
				case 5: interact.setText("Serangan " + foeMonster.getName() + " gagal.");
						if(skill_use_turn == 1) {
							skill_use_turn = 2;
							interaction_skill_use = 1;
						} else {
							interaction_skill_use = 8;
							skill_use_turn = 0;
						}
						break;
				case 6: 
					interact.setText("Skill " + playerSkill[monster_skill_use - 1].getName() + " is\nsangat efektif.");
					if(skill_use_turn == 1) {
						skill_use_turn = 2;
						interaction_skill_use = 1;
					} else {
						interaction_skill_use = 8;
					}
					break;
				case 7:
					interact.setText("Skill " + playerSkill[monster_skill_use - 1].getName() + " is\nsangat tidak efektif.");
					if(skill_use_turn == 1) {
						skill_use_turn = 2;
						interaction_skill_use = 1;
					} else {
						interaction_skill_use = 8;
					}
					break;
				case 8:
					if(pending_effect) {
						foeMonster.setNoStatus(playerSkill[monster_skill_use - 1].getStatusEffect());
						interact.setText(playerMonster.getName() + " terkena efek " +
							StatusEffect.getNameStatusEffect(db.getReadableDatabase(), playerSkill[monster_skill_use - 1].getStatusEffect()));
						pending_effect = false;
					}
					monster_skill_use = 0;
					interact(2);
					skill_use_turn = 0;
					break;
			}
		}
		battle_content();
	}
	
	private class MonsterBattle {
		private String Name;
		private int Elemen_No;
		private int Level;
		private int MaxHP;
		private int CurHP;
		private int Attack;
		private int Defense;
		private int Speed;
		private float Accuracy;
		private int NoSkill1;
		private int NoSkill2;
		private int NoSkill3;
		private int NoSkill4;
		private int PPSkill1;
		private int PPSkill2;
		private int PPSkill3;
		private int PPSkill4;
		private int NoStatus;
		
		public MonsterBattle(Monster monster) {
			this.setName(monster.getSpesies());
			this.setElemen_No(monster.getElemenNo());
			this.Level = ((total_bet/25000) + 1)*4 +10;
			this.MaxHP = (((monster.getBaseHP() + 100)*this.Level)/50) + 10;
			this.setCurHP(this.MaxHP);
			this.setAttack((((monster.getBaseAttack() + 100)*this.Level)/50) + 10);
			this.setDefense((((monster.getBaseDefense() + 100)*this.Level)/50) + 10);
			this.setSpeed((((monster.getBaseSpeed() + 100)*this.Level)/50) + 10);
			this.NoSkill1 = monster.getDefaultSkill1();
			this.NoSkill2 = monster.getDefaultSkill2();
			this.NoSkill3 = monster.getDefaultSkill3();
			this.NoSkill4 = monster.getDefaultSkill4();
			if(this.NoSkill1 > 0) this.setPPSkill1(Skill.getDefPPSkill(db.getReadableDatabase(), this.NoSkill1));
			if(this.NoSkill2 > 0) this.setPPSkill2(Skill.getDefPPSkill(db.getReadableDatabase(), this.NoSkill2));
			if(this.NoSkill3 > 0) this.setPPSkill3(Skill.getDefPPSkill(db.getReadableDatabase(), this.NoSkill3));
			if(this.NoSkill4 > 0) this.setPPSkill4(Skill.getDefPPSkill(db.getReadableDatabase(), this.NoSkill4));
			this.Accuracy = 100;
			this.setNoStatus(0);
		}
		
		public MonsterBattle(PlayerMonster Pmonster) {
			this.setName(Pmonster.getName());
			Monster monster = new Monster(db.getReadableDatabase(),Pmonster.getMonsterNumber());
			this.setElemen_No(monster.getElemenNo());
			this.Level = Pmonster.getLevel();
			this.MaxHP = Pmonster.getHP();
			this.setCurHP(Pmonster.getCurHP());
			this.setAttack(Pmonster.getAttack());
			this.setDefense(Pmonster.getDefense());
			this.setSpeed(Pmonster.getSpeed());
			this.NoSkill1 = Pmonster.getSkill1();
			this.NoSkill2 = Pmonster.getSkill2();
			this.NoSkill3 = Pmonster.getSkill3();
			this.NoSkill4 = Pmonster.getSkill4();
			this.setPPSkill1(Pmonster.getPPSkill1());
			this.setPPSkill2(Pmonster.getPPSkill2());
			this.setPPSkill3(Pmonster.getPPSkill3());
			this.setPPSkill4(Pmonster.getPPSkill4());
			this.setNoStatus(Pmonster.getStatusEffect());
			this.Accuracy = 100;
		}

		public String getName() {
			return Name;
		}

		public void setName(String name) {
			Name = name;
		}

		public int getElemen_No() {
			return Elemen_No;
		}

		public void setElemen_No(int elemen_No) {
			Elemen_No = elemen_No;
		}
		
		public int getLevel() {
			return Level;
		}
		
		public int getMaxHP() {
			return MaxHP;
		}

		public int getCurHP() {
			return CurHP;
		}

		public void setCurHP(int curHP) {
			CurHP = curHP;
		}

		public int getAttack() {
			return Attack;
		}

		public void setAttack(int attack) {
			Attack = attack;
		}

		public int getDefense() {
			return Defense;
		}

		public void setDefense(int defense) {
			Defense = defense;
		}

		public int getSpeed() {
			return Speed;
		}

		public void setSpeed(int speed) {
			Speed = speed;
		}
		
		public int getSkill1() {
			return NoSkill1;
		}
		
		public int getSkill2() {
			return NoSkill2;
		}
		
		public int getSkill3() {
			return NoSkill3;
		}
		
		public int getSkill4() {
			return NoSkill4;
		}

		public int getPPSkill1() {
			return PPSkill1;
		}

		public void setPPSkill1(int pPSkill1) {
			PPSkill1 = pPSkill1;
		}

		public int getPPSkill2() {
			return PPSkill2;
		}

		public void setPPSkill2(int pPSkill2) {
			PPSkill2 = pPSkill2;
		}

		public int getPPSkill3() {
			return PPSkill3;
		}

		public void setPPSkill3(int pPSkill3) {
			PPSkill3 = pPSkill3;
		}

		public int getPPSkill4() {
			return PPSkill4;
		}

		public void setPPSkill4(int pPSkill4) {
			PPSkill4 = pPSkill4;
		}

		public int getNoStatus() {
			return NoStatus;
		}

		public void setNoStatus(int noStatus) {
			NoStatus = noStatus;
		}
		
		public float getAccuracy() {
			return Accuracy;
		}
		
		public void setAccuracy(float acc) {
			this.Accuracy = acc;
		}
		
	}

	@Override
	public void isMonsterCaught(int callAct) {
		// TODO Auto-generated method stub
		
	}

}
