package user.pokeranch.cv;

import java.util.Random;

import user.pokeranch.Main;
import user.pokeranch.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.NinePatchDrawable;

public class DrawableManager {
	/**
	 * construct pake aplication context
	 * @param context
	 */
	private Resources res;
	private DrawableManager(Context context) {
		res = context.getResources();
		rand = new Random();
	}
	/**
	 * must be called only once
	 * @param context
	 */
	public static void initInstance(Context context) {
		assert instance == null;
		instance = new DrawableManager(context);
		last_index_monster = 4;
		monster_load = new int[4];
		monster_load[0] = 0;
		monster_load[1] = 0;
		monster_load[2] = 0;
		monster_load[3] = 0;
		last_index_item = 4;
		item_load = new int[4];
		item_load[0] = 0;
		item_load[1] = 0;
		item_load[2] = 0;
		item_load[3] = 0;
		last_index_monster_back = 4;
		monster_back_load = new int[4];
		monster_back_load[0] = 0;
		monster_back_load[1] = 0;
		monster_back_load[2] = 0;
		monster_back_load[3] = 0;
	}
	public static DrawableManager getInstance() {
		assert instance != null;
		return instance;
	}
	public void initAllBitmap() {
		if(box == null) {
			box = (NinePatchDrawable) res.getDrawable(R.drawable.box);
		}
		if(blackBox == null) {
			blackBox = (NinePatchDrawable) res.getDrawable(R.drawable.blackbox);
		}
		if(blackBox2 == null) {
			blackBox2 = (NinePatchDrawable) res.getDrawable(R.drawable.blackbox);
		}
		if(blueBox == null) {
			blueBox = (NinePatchDrawable) res.getDrawable(R.drawable.bluebox);
		}
		if(pokeranch == null) {
			pokeranch = BitmapFactory.decodeResource(res, R.drawable.pokeranch);
		}
		if(hero == null) {
			hero = BitmapFactory.decodeResource(res, R.drawable.hero);
		}
		if(hero_swim == null) {
			hero_swim = BitmapFactory.decodeResource(res, R.drawable.hero_swim);
		}
		if(hero_bike == null) {
			hero_bike = BitmapFactory.decodeResource(res, R.drawable.hero_bike);
		}
		if(home1 == null) {
			home1 = BitmapFactory.decodeResource(res, R.drawable.home);
		}
		if(city_background == null) {
			city_background = BitmapFactory.decodeResource(res, R.drawable.city_b);
		}
		if(city_foreground == null) {
			city_foreground = BitmapFactory.decodeResource(res, R.drawable.city_a);
		}
		if(outer_background1== null) {
			outer_background1= BitmapFactory.decodeResource(res, R.drawable.outbg);
		}
		if(outer_background2== null) {
			outer_background2= BitmapFactory.decodeResource(res, R.drawable.outsemakoverlay);
		}
		if(outer_background3== null) {
			outer_background3= BitmapFactory.decodeResource(res, R.drawable.outsemakoverlay2);
		}
		if(shop_background == null) {
			shop_background = BitmapFactory.decodeResource(res, R.drawable.shop);
		}
		if(combinatorium_background == null) {
			combinatorium_background = BitmapFactory.decodeResource(res, R.drawable.kombinatorium);
		}
		if(stadium_background == null) {
			stadium_background = BitmapFactory.decodeResource(res, R.drawable.stadium);
		}
		if(sea == null) {
			sea = BitmapFactory.decodeResource(res, R.drawable.seas);
		}
		if(battle_background == null) {
			battle_background = BitmapFactory.decodeResource(res, R.drawable.battleback);
		}
		if(battle_grass == null) {
			battle_grass = BitmapFactory.decodeResource(res, R.drawable.battle_grass);
		}
		if(monster_shadow == null) {
			monster_shadow = BitmapFactory.decodeResource(res, R.drawable.monster_shadow);
		}
		if(monstersample== null) {
			monstersample = BitmapFactory.decodeResource(res, R.drawable.monstersample);
		}
		if(foe_properties == null) {
			foe_properties = BitmapFactory.decodeResource(res, R.drawable.foe_properties);
		}
		if(self_properties == null) {
			self_properties = BitmapFactory.decodeResource(res, R.drawable.player_properties);
		}
		if(health_bar == null) {
			health_bar = BitmapFactory.decodeResource(res, R.drawable.health_bar);
		}
		if(frz == null) {
			frz = BitmapFactory.decodeResource(res, R.drawable.frz);
		}
		if(brn == null) {
			brn = BitmapFactory.decodeResource(res, R.drawable.brn);
		}
		if(slp == null) {
			slp = BitmapFactory.decodeResource(res, R.drawable.slp);
		}
		if(plz == null) {
			plz = BitmapFactory.decodeResource(res, R.drawable.plz);
		}
		if(psn == null) {
			psn = BitmapFactory.decodeResource(res, R.drawable.psn);
		}
		if(grass == null) {
			grass = BitmapFactory.decodeResource(res, R.drawable.grass);
		}
		if(sea_animate == null) {
			sea_animate = BitmapFactory.decodeResource(res, R.drawable.sea_animate);
		}
		if(menu_button == null) {
			menu_button = BitmapFactory.decodeResource(res, R.drawable.menu_button);
		}
		if(Main.metrics.heightPixels > 800) {
			Bitmap temp;
			temp = Bitmap.createScaledBitmap(pokeranch, pokeranch.getWidth()*2, pokeranch.getHeight()*2, true);
			pokeranch.recycle();
			pokeranch = temp;
			temp = Bitmap.createScaledBitmap(hero, hero.getWidth()*2, hero.getHeight()*2, true);
			hero.recycle();
			hero = temp;
			temp = Bitmap.createScaledBitmap(hero_bike, hero_bike.getWidth()*2, hero_bike.getHeight()*2, true);
			hero_bike.recycle();
			hero_bike = temp;
			temp = Bitmap.createScaledBitmap(hero_swim, hero_swim.getWidth()*2, hero_swim.getHeight()*2, true);
			hero_swim.recycle();
			hero_swim = temp;
			temp = Bitmap.createScaledBitmap(home1, home1.getWidth()*2, home1.getHeight()*2, true);
			home1.recycle();
			home1 = temp;
			temp = Bitmap.createScaledBitmap(city_background, city_background.getWidth()*2, city_background.getHeight()*2, true);
			city_background.recycle();
			city_background = temp;
			temp = Bitmap.createScaledBitmap(city_foreground, city_foreground.getWidth()*2, city_foreground.getHeight()*2, true);
			city_foreground.recycle();
			city_foreground = temp;
			temp = Bitmap.createScaledBitmap(outer_background1, outer_background1.getWidth()*2, outer_background1.getHeight()*2, true);
			outer_background1.recycle();
			outer_background1 = temp;
			temp = Bitmap.createScaledBitmap(outer_background2, outer_background2.getWidth()*2, outer_background2.getHeight()*2, true);
			outer_background2.recycle();
			outer_background2 = temp;
			temp = Bitmap.createScaledBitmap(outer_background3, outer_background3.getWidth()*2, outer_background3.getHeight()*2, true);
			outer_background3.recycle();
			outer_background3 = temp;
			temp = Bitmap.createScaledBitmap(shop_background, shop_background.getWidth()*2, shop_background.getHeight()*2, true);
			shop_background.recycle();
			shop_background = temp;
			temp = Bitmap.createScaledBitmap(combinatorium_background, combinatorium_background.getWidth()*2, combinatorium_background.getHeight()*2, true);
			combinatorium_background.recycle();
			combinatorium_background = temp;
			temp = Bitmap.createScaledBitmap(stadium_background, stadium_background.getWidth()*2, stadium_background.getHeight()*2, true);
			stadium_background.recycle();
			stadium_background = temp;
			temp = Bitmap.createScaledBitmap(sea, sea.getWidth()*2, sea.getHeight()*2, true);
			sea.recycle();
			sea = temp;
			temp = Bitmap.createScaledBitmap(battle_background, battle_background.getWidth()*2, battle_background.getHeight()*2, true);
			battle_background.recycle();
			battle_background = temp;
			temp = Bitmap.createScaledBitmap(battle_grass, battle_grass.getWidth()*2, battle_grass.getHeight()*2, true);
			battle_grass.recycle();
			battle_grass = temp;
			temp = Bitmap.createScaledBitmap(monster_shadow, monster_shadow.getWidth()*2, monster_shadow.getHeight()*2, true);
			monster_shadow.recycle();
			monster_shadow = temp;
			temp = Bitmap.createScaledBitmap(monstersample, monstersample.getWidth()*2, monstersample.getHeight()*2, true);
			monstersample.recycle();
			monstersample = temp;
			temp = Bitmap.createScaledBitmap(foe_properties, foe_properties.getWidth()*2, foe_properties.getHeight()*2, true);
			foe_properties.recycle();
			foe_properties = temp;
			temp = Bitmap.createScaledBitmap(self_properties, self_properties.getWidth()*2, self_properties.getHeight()*2, true);
			self_properties.recycle();
			self_properties = temp;
			temp = Bitmap.createScaledBitmap(health_bar, health_bar.getWidth()*2, health_bar.getHeight()*2, true);
			health_bar.recycle();
			health_bar = temp;
			temp = Bitmap.createScaledBitmap(frz, frz.getWidth()*2, frz.getHeight()*2, true);
			frz.recycle();
			frz = temp;
			temp = Bitmap.createScaledBitmap(brn, brn.getWidth()*2, brn.getHeight()*2, true);
			brn.recycle();
			brn = temp;
			temp = Bitmap.createScaledBitmap(slp, slp.getWidth()*2, slp.getHeight()*2, true);
			slp.recycle();
			slp = temp;
			temp = Bitmap.createScaledBitmap(plz, plz.getWidth()*2, plz.getHeight()*2, true);
			plz.recycle();
			plz = temp;
			temp = Bitmap.createScaledBitmap(psn, psn.getWidth()*2, psn.getHeight()*2, true);
			psn.recycle();
			psn = temp;
			temp = Bitmap.createScaledBitmap(grass, grass.getWidth()*2, grass.getHeight()*2, true);
			grass.recycle();
			grass = temp;
			temp = Bitmap.createScaledBitmap(sea_animate, sea_animate.getWidth()*2, sea_animate.getHeight()*2, true);
			sea_animate.recycle();
			sea_animate = temp;
			temp = Bitmap.createScaledBitmap(menu_button, menu_button.getWidth()*2, menu_button.getHeight()*2, true);
			menu_button.recycle();
			menu_button = temp;
		}
	}
	public NinePatchDrawable getBoxBitmap() {
		if(box == null) {
			box = (NinePatchDrawable) res.getDrawable(R.drawable.box);
		}
		return box;
	}
	public NinePatchDrawable getBlackBoxBitmap() {
		if(blackBox == null) {
			blackBox = (NinePatchDrawable) res.getDrawable(R.drawable.blackbox);
		}
		return blackBox;
	}
	public NinePatchDrawable getBlackBox2Bitmap() {
		if(blackBox2 == null) {
			blackBox2 = (NinePatchDrawable) res.getDrawable(R.drawable.blackbox);
		}
		return blackBox2;
	}
	public NinePatchDrawable getBlueBoxBitmap() {
		if(blueBox == null) {
			blueBox = (NinePatchDrawable) res.getDrawable(R.drawable.bluebox);
		}
		return blueBox;
	}
	public Bitmap getPokeranch() {
		if(pokeranch == null) {
			pokeranch = BitmapFactory.decodeResource(res, R.drawable.pokeranch);
		}
		return pokeranch;
	}
	public Bitmap getHero() {
		if(hero == null) {
			hero = BitmapFactory.decodeResource(res, R.drawable.hero);
		}
		return hero;
	}
	public Bitmap getHeroSwim() {
		if(hero_swim == null) {
			hero_swim = BitmapFactory.decodeResource(res, R.drawable.hero_swim);
		}
		return hero_swim;
	}
	public Bitmap getHeroBike() {
		if(hero_bike == null) {
			hero_bike = BitmapFactory.decodeResource(res, R.drawable.hero_bike);
		}
		return hero_bike;
	}
	public Bitmap getHomeArea1() {
		if(home1 == null) {
			home1 = BitmapFactory.decodeResource(res, R.drawable.home);
		}
		return home1;
	}
	public Bitmap getCityBackground() {
		if(city_background == null) {
			city_background = BitmapFactory.decodeResource(res, R.drawable.city_b);
		}
		return city_background;
	}
	public Bitmap getCityForeground() {
		if(city_foreground == null) {
			city_foreground = BitmapFactory.decodeResource(res, R.drawable.city_a);
		}
		return city_foreground;
	}
	public Bitmap getOuterBackground1() {
		if(outer_background1== null) {
			outer_background1= BitmapFactory.decodeResource(res, R.drawable.outbg);
		}
		return outer_background1;
	}
	public Bitmap getOuterBackground2() {
		if(outer_background2== null) {
			outer_background2= BitmapFactory.decodeResource(res, R.drawable.outsemakoverlay);
		}
		return outer_background2;
	}
	public Bitmap getOuterBackground3() {
		if(outer_background3== null) {
			outer_background3= BitmapFactory.decodeResource(res, R.drawable.outsemakoverlay2);
		}
		return outer_background3;
	}
	public Bitmap getShopBackground() {
		if(shop_background == null) {
			shop_background = BitmapFactory.decodeResource(res, R.drawable.shop);
		}
		return shop_background;
	}
	public Bitmap getCombinatoriumBackground() {
		if(combinatorium_background == null) {
			combinatorium_background = BitmapFactory.decodeResource(res, R.drawable.kombinatorium);
		}
		return combinatorium_background;
	}
	public Bitmap getStadiumBackground() {
		if(stadium_background == null) {
			stadium_background = BitmapFactory.decodeResource(res, R.drawable.stadium);
		}
		return stadium_background;
	}
	public Bitmap getSea() {
		if(sea == null) {
			sea = BitmapFactory.decodeResource(res, R.drawable.seas);
		}
		return sea;
	}
	public Bitmap BattleBack() {
		if(battle_background == null) {
			battle_background = BitmapFactory.decodeResource(res, R.drawable.battleback);
		}
		return battle_background;
	}
	public Bitmap BattleGrass() {
		if(battle_grass == null) {
			battle_grass = BitmapFactory.decodeResource(res, R.drawable.battle_grass);
		}
		return battle_grass;
	}
	public Bitmap getMonsterShadow() {
		if(monster_shadow == null) {
			monster_shadow = BitmapFactory.decodeResource(res, R.drawable.monster_shadow);
		}
		return monster_shadow;
	}
	public Bitmap getMonster() {
		if(monstersample== null) {
			monstersample = BitmapFactory.decodeResource(res, R.drawable.monstersample);
		}
		return monstersample;
	}
	public Bitmap foe_properties() {
		if(foe_properties == null) {
			foe_properties = BitmapFactory.decodeResource(res, R.drawable.foe_properties);
		}
		return foe_properties;
	}
	public Bitmap self_properties() {
		if(self_properties == null) {
			self_properties = BitmapFactory.decodeResource(res, R.drawable.player_properties);
		}
		return self_properties;
	}
	public Bitmap health_bar() {
		if(health_bar == null) {
			health_bar = BitmapFactory.decodeResource(res, R.drawable.health_bar);
		}
		return health_bar;
	}
	public Bitmap Frozen() {
		if(frz == null) {
			frz = BitmapFactory.decodeResource(res, R.drawable.frz);
		}
		return frz;
	}
	public Bitmap Burn() {
		if(brn == null) {
			brn = BitmapFactory.decodeResource(res, R.drawable.brn);
		}
		return brn;
	}
	public Bitmap Sleep() {
		if(slp == null) {
			slp = BitmapFactory.decodeResource(res, R.drawable.slp);
		}
		return slp;
	}
	public Bitmap Paralyzed() {
		if(plz == null) {
			plz = BitmapFactory.decodeResource(res, R.drawable.plz);
		}
		return plz;
	}
	public Bitmap Poison() {
		if(psn == null) {
			psn = BitmapFactory.decodeResource(res, R.drawable.psn);
		}
		return psn;
	}
	public Bitmap Grass() {
		if(grass == null) {
			grass = BitmapFactory.decodeResource(res, R.drawable.grass);
		}
		return grass;
	}
	public Bitmap SeaAnimation() {
		if(sea_animate == null) {
			sea_animate = BitmapFactory.decodeResource(res, R.drawable.sea_animate);
		}
		return sea_animate;
	}
	public Bitmap MonsterPicture(int i) {
		assert (i > 0) && (i <= 101);
		int k = 0;
		while(k < 4) {
			if(monster_load[k] == i)
				break;
			else
				k++;
		}
		if(k == 4) { // Jika belum pernah di load
			int j = 0;
			switch(j) { // Cari yang masih null
				case 0: if(monster1 == null) break; else j = 1;
				case 1: if(monster2 == null) break; else j = 2;
				case 2: if(monster3 == null) break; else j = 3;
				case 3: if(monster4 == null) break; else j = 4;
			}
			if(j == 4) { // Jika tidak ada yang null
				k = (last_index_monster + 1) % 4;
				switch(k) {
					case 0: monster1.recycle(); break;
					case 1: monster2.recycle(); break;
					case 2: monster3.recycle(); break;
					case 3: monster4.recycle(); break;
				}
			} else k = j;
			last_index_monster = k;
			Bitmap monsterTemp = null;
			switch(i) {
				case 1  : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m1); break;
				case 2  : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m2); break;
				case 3  : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m3); break;
				case 4  : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m4); break;
				case 5  : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m5); break;
				case 6  : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m6); break;
				case 7  : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m7); break;
				case 8  : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m8); break;
				case 9  : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m9); break;
				case 10 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m10); break;
				case 11 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m11); break;
				case 12 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m12); break;
				case 13 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m13); break;
				case 14 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m14); break;
				case 15 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m15); break;
				case 16 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m16); break;
				case 17 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m17); break;
				case 18 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m18); break;
				case 19 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m19); break;
				case 20 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m20); break;
				case 21 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m21); break;
				case 22 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m22); break;
				case 23 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m23); break;
				case 24 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m24); break;
				case 25 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m25); break;
				case 26 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m26); break;
				case 27 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m27); break;
				case 28 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m28); break;
				case 29 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m29); break;
				case 30 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m30); break;
				case 31 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m31); break;
				case 32 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m32); break;
				case 33 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m33); break;
				case 34 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m34); break;
				case 35 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m35); break;
				case 36 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m36); break;
				case 37 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m37); break;
				case 38 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m38); break;
				case 39 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m39); break;
				case 40 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m40); break;
				case 41 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m41); break;
				case 42 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m42); break;
				case 43 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m43); break;
				case 44 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m44); break;
				case 45 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m45); break;
				case 46 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m46); break;
				case 47 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m47); break;
				case 48 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m48); break;
				case 49 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m49); break;
				case 50 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m50); break;
				case 51 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m51); break;
				case 52 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m52); break;
				case 53 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m53); break;
				case 54 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m54); break;
				case 55 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m55); break;
				case 56 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m56); break;
				case 57 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m57); break;
				case 58 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m58); break;
				case 59 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m59); break;
				case 60 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m60); break;
				case 61 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m61); break;
				case 62 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m62); break;
				case 63 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m63); break;
				case 64 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m64); break;
				case 65 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m65); break;
				case 66 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m66); break;
				case 67 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m67); break;
				case 68 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m68); break;
				case 69 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m69); break;
				case 70 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m70); break;
				case 71 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m71); break;
				case 72 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m72); break;
				case 73 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m73); break;
				case 74 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m74); break;
				case 75 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m75); break;
				case 76 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m76); break;
				case 77 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m77); break;
				case 78 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m78); break;
				case 79 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m79); break;
				case 80 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m80); break;
				case 81 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m81); break;
				case 82 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m82); break;
				case 83 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m83); break;
				case 84 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m84); break;
				case 85 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m85); break;
				case 86 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m86); break;
				case 87 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m87); break;
				case 88 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m88); break;
				case 89 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m89); break;
				case 90 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m90); break;
				case 91 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m91); break;
				case 92 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m92); break;
				case 93 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m93); break;
				case 94 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m94); break;
				case 95 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m95); break;
				case 96 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m96); break;
				case 97 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m97); break;
				case 98 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m98); break;
				case 99 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m99); break;
				case 100: monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m100); break;
				case 101: monsterTemp = BitmapFactory.decodeResource(res, R.drawable.m101); break;
			}
			switch(k) {
				case 0: monster1 = Bitmap.createScaledBitmap(monsterTemp, monsterTemp.getWidth()*2, monsterTemp.getHeight()*2,false); break;
				case 1: monster2 = Bitmap.createScaledBitmap(monsterTemp, monsterTemp.getWidth()*2, monsterTemp.getHeight()*2,false); break;
				case 2: monster3 = Bitmap.createScaledBitmap(monsterTemp, monsterTemp.getWidth()*2, monsterTemp.getHeight()*2,false); break;
				case 3: monster4 = Bitmap.createScaledBitmap(monsterTemp, monsterTemp.getWidth()*2, monsterTemp.getHeight()*2,false); break;
			}
			monsterTemp.recycle();
			monster_load[k] = i;
		}
		Bitmap monster_temp = null;
		switch(k) {
			case 0: monster_temp = monster1; break;
			case 1: monster_temp = monster2; break;
			case 2: monster_temp = monster3; break;
			case 3: monster_temp = monster4; break;
		}
		return monster_temp;
	}

	public Bitmap MonsterPictureBack(int i) {
		assert (i > 0) && (i <= 101);
		int k = 0;
		while(k < 4) {
			if(monster_back_load[k] == i)
				break;
			else
				k++;
		}
		if(k == 4) { // Jika belum pernah di load
			int j = 0;
			switch(j) { // Cari yang masih null
				case 0: if(monsterBack1 == null) break; else j = 1;
				case 1: if(monsterBack2 == null) break; else j = 2;
				case 2: if(monsterBack3 == null) break; else j = 3;
				case 3: if(monsterBack4 == null) break; else j = 4;
			}
			if(j == 4) { // Jika tidak ada yang null
				k = (last_index_monster_back + 1) % 4;
				switch(k) {
					case 0: monsterBack1.recycle(); break;
					case 1: monsterBack2.recycle(); break;
					case 2: monsterBack3.recycle(); break;
					case 3: monsterBack4.recycle(); break;
				}
			} else k = j;
			last_index_monster_back = k;
			Bitmap monsterTemp = null;
			switch(i) {
				case 1  : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb1); break;
				case 2  : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb2); break;
				case 3  : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb3); break;
				case 4  : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb4); break;
				case 5  : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb5); break;
				case 6  : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb6); break;
				case 7  : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb7); break;
				case 8  : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb8); break;
				case 9  : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb9); break;
				case 10 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb10); break;
				case 11 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb11); break;
				case 12 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb12); break;
				case 13 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb13); break;
				case 14 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb14); break;
				case 15 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb15); break;
				case 16 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb16); break;
				case 17 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb17); break;
				case 18 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb18); break;
				case 19 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb19); break;
				case 20 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb20); break;
				case 21 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb21); break;
				case 22 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb22); break;
				case 23 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb23); break;
				case 24 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb24); break;
				case 25 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb25); break;
				case 26 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb26); break;
				case 27 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb27); break;
				case 28 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb28); break;
				case 29 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb29); break;
				case 30 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb30); break;
				case 31 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb31); break;
				case 32 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb32); break;
				case 33 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb33); break;
				case 34 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb34); break;
				case 35 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb35); break;
				case 36 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb36); break;
				case 37 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb37); break;
				case 38 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb38); break;
				case 39 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb39); break;
				case 40 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb40); break;
				case 41 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb41); break;
				case 42 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb42); break;
				case 43 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb43); break;
				case 44 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb44); break;
				case 45 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb45); break;
				case 46 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb46); break;
				case 47 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb47); break;
				case 48 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb48); break;
				case 49 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb49); break;
				case 50 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb50); break;
				case 51 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb51); break;
				case 52 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb52); break;
				case 53 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb53); break;
				case 54 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb54); break;
				case 55 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb55); break;
				case 56 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb56); break;
				case 57 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb57); break;
				case 58 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb58); break;
				case 59 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb59); break;
				case 60 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb60); break;
				case 61 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb61); break;
				case 62 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb62); break;
				case 63 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb63); break;
				case 64 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb64); break;
				case 65 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb65); break;
				case 66 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb66); break;
				case 67 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb67); break;
				case 68 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb68); break;
				case 69 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb69); break;
				case 70 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb70); break;
				case 71 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb71); break;
				case 72 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb72); break;
				case 73 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb73); break;
				case 74 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb74); break;
				case 75 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb75); break;
				case 76 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb76); break;
				case 77 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb77); break;
				case 78 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb78); break;
				case 79 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb79); break;
				case 80 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb80); break;
				case 81 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb81); break;
				case 82 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb82); break;
				case 83 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb83); break;
				case 84 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb84); break;
				case 85 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb85); break;
				case 86 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb86); break;
				case 87 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb87); break;
				case 88 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb88); break;
				case 89 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb89); break;
				case 90 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb90); break;
				case 91 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb91); break;
				case 92 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb92); break;
				case 93 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb93); break;
				case 94 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb94); break;
				case 95 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb95); break;
				case 96 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb96); break;
				case 97 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb97); break;
				case 98 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb98); break;
				case 99 : monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb99); break;
				case 100: monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb100); break;
				case 101: monsterTemp = BitmapFactory.decodeResource(res, R.drawable.mb101); break;
			}
			switch(k) {
				case 0: monsterBack1 = Bitmap.createScaledBitmap(monsterTemp, monsterTemp.getWidth()*2, monsterTemp.getHeight()*2,false); break;
				case 1: monsterBack2 = Bitmap.createScaledBitmap(monsterTemp, monsterTemp.getWidth()*2, monsterTemp.getHeight()*2,false); break;
				case 2: monsterBack3 = Bitmap.createScaledBitmap(monsterTemp, monsterTemp.getWidth()*2, monsterTemp.getHeight()*2,false); break;
				case 3: monsterBack4 = Bitmap.createScaledBitmap(monsterTemp, monsterTemp.getWidth()*2, monsterTemp.getHeight()*2,false); break;
			}
			monsterTemp.recycle();
			monster_back_load[k] = i;
		}
		Bitmap monster_temp = null;
		switch(k) {
			case 0: monster_temp = monsterBack1; break;
			case 1: monster_temp = monsterBack2; break;
			case 2: monster_temp = monsterBack3; break;
			case 3: monster_temp = monsterBack4; break;
		}
		return monster_temp;
	}
	public Bitmap ItemPicture(int i) {
		assert (i > 0) && (i <= 35);
		int k = 0;
		while(k < 4) {
			if(item_load[k] == i)
				break;
			else
				k++;
		}
		if(k == 4) { // Jika belum pernah di load
			int j = 0;
			switch(j) { // Cari yang masih null
				case 0: if(item1 == null) break; else j = 1;
				case 1: if(item2 == null) break; else j = 2;
				case 2: if(item3 == null) break; else j = 3;
				case 3: if(item4 == null) break; else j = 4;
			}
			if(j == 4) { // Jika tidak ada yang null
				k = (last_index_item + 1) % 4;
				switch(k) {
					case 0: item1.recycle(); break;
					case 1: item2.recycle(); break;
					case 2: item3.recycle(); break;
					case 3: item4.recycle(); break;
				}
			} else k = j;
			last_index_item = k;
			Bitmap itemTemp = null;
			switch(i) {
				case 1  : itemTemp = BitmapFactory.decodeResource(res, R.drawable.it1); break;
				case 2  : itemTemp = BitmapFactory.decodeResource(res, R.drawable.it2); break;
				case 3  : itemTemp = BitmapFactory.decodeResource(res, R.drawable.it3); break;
				case 4  : itemTemp = BitmapFactory.decodeResource(res, R.drawable.it4); break;
				case 5  : itemTemp = BitmapFactory.decodeResource(res, R.drawable.it5); break;
				case 6  : itemTemp = BitmapFactory.decodeResource(res, R.drawable.it6); break;
				case 7  : itemTemp = BitmapFactory.decodeResource(res, R.drawable.it7); break;
				case 8  : itemTemp = BitmapFactory.decodeResource(res, R.drawable.it8); break;
				case 9  : itemTemp = BitmapFactory.decodeResource(res, R.drawable.it9); break;
				case 10 : itemTemp = BitmapFactory.decodeResource(res, R.drawable.it10); break;
				case 11 : itemTemp = BitmapFactory.decodeResource(res, R.drawable.it11); break;
				case 12 : itemTemp = BitmapFactory.decodeResource(res, R.drawable.it12); break;
				case 13 : itemTemp = BitmapFactory.decodeResource(res, R.drawable.it13); break;
				case 14 : itemTemp = BitmapFactory.decodeResource(res, R.drawable.it14); break;
				case 15 : itemTemp = BitmapFactory.decodeResource(res, R.drawable.it15); break;
				case 16 : itemTemp = BitmapFactory.decodeResource(res, R.drawable.it16); break;
				case 17 : itemTemp = BitmapFactory.decodeResource(res, R.drawable.it17); break;
				case 18 : itemTemp = BitmapFactory.decodeResource(res, R.drawable.it18); break;
				case 19 : itemTemp = BitmapFactory.decodeResource(res, R.drawable.it19); break;
				case 20 : 
				case 21 : 
				case 22 : 
				case 23 : 
				case 24 : 
				case 25 : 
				case 26 : 
				case 27 : 
				case 28 : itemTemp = BitmapFactory.decodeResource(res, R.drawable.ittm); break;
				case 29 : itemTemp = BitmapFactory.decodeResource(res, R.drawable.it29); break;
				case 30 : itemTemp = BitmapFactory.decodeResource(res, R.drawable.it30); break;
				case 31 : itemTemp = BitmapFactory.decodeResource(res, R.drawable.it31); break;
				case 32 : itemTemp = BitmapFactory.decodeResource(res, R.drawable.it32); break;
				case 33 : 
				case 34 : 
				case 35 : itemTemp = BitmapFactory.decodeResource(res, R.drawable.ittm); break;
			}
			switch(k) {
				case 0: item1 = Bitmap.createScaledBitmap(itemTemp, itemTemp.getWidth()*2, itemTemp.getHeight()*2,false); break;
				case 1: item2 = Bitmap.createScaledBitmap(itemTemp, itemTemp.getWidth()*2, itemTemp.getHeight()*2,false); break;
				case 2: item3 = Bitmap.createScaledBitmap(itemTemp, itemTemp.getWidth()*2, itemTemp.getHeight()*2,false); break;
				case 3: item4 = Bitmap.createScaledBitmap(itemTemp, itemTemp.getWidth()*2, itemTemp.getHeight()*2,false); break;
			}
			itemTemp.recycle();
			item_load[k] = i;
		}
		Bitmap item_temp = null;
		switch(k) {
			case 0: item_temp = item1; break;
			case 1: item_temp = item2; break;
			case 2: item_temp = item3; break;
			case 3: item_temp = item4; break;
		}
		return item_temp;
	}
	public Bitmap menu_button() {
		if(menu_button == null) {
			menu_button = BitmapFactory.decodeResource(res, R.drawable.menu_button);
		}
		return menu_button;
	}
	public Bitmap get_batu() {
		if(batu == null) {
			batu = BitmapFactory.decodeResource(res, R.drawable.rock);
		}
		return batu;
	}
	
	public Bitmap get_tree(){
		if(tree==null){
			tree = BitmapFactory.decodeResource(res, R.drawable.cut_tree);
		}
		return tree;
	}
	private static DrawableManager instance;
	private Bitmap tree;
	private NinePatchDrawable box;
	private NinePatchDrawable blackBox;
	private NinePatchDrawable blackBox2;
	private NinePatchDrawable blueBox;
	private Bitmap outer_background1;
	private Bitmap outer_background2;
	private Bitmap outer_background3;
	private Bitmap hero;
	private Bitmap hero_bike;
	private Bitmap hero_swim;
	private Bitmap city_background;
	private Bitmap city_foreground;
	private Bitmap shop_background;
	private Bitmap combinatorium_background;
	private Bitmap stadium_background;
	private Bitmap sea;
	private Bitmap pokeranch;
	private Bitmap home1;
	private Bitmap monster_shadow;
	private Bitmap monstersample;
	private Bitmap battle_background;
	private Bitmap battle_grass;
	private Bitmap foe_properties;
	private Bitmap self_properties;
	private Bitmap health_bar;
	private Bitmap frz;
	private Bitmap brn;
	private Bitmap slp;
	private Bitmap plz;
	private Bitmap psn;
	private Bitmap grass;
	private Bitmap sea_animate;
	private static int[] monster_load;
	private Bitmap monster1;
	private Bitmap monster2;
	private Bitmap monster3;
	private Bitmap monster4;
	private static int[] item_load;
	private Bitmap item1;
	private Bitmap item2;
	private Bitmap item3;
	private Bitmap item4;
	private Bitmap menu_button;
	private Bitmap batu;
	private static int[] monster_back_load;
	private Bitmap monsterBack1;
	private Bitmap monsterBack2;
	private Bitmap monsterBack3;
	private Bitmap monsterBack4;
	private static int last_index_monster;
	private static int last_index_item;
	private static int last_index_monster_back;
	public final Random rand;
}
