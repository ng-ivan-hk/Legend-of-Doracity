/**
 * All Chinese characters are stored here.
 * 
 * @author Ivan Ng
 * 
 */
public class Lang {

	/* Application */
	public final static String legendOfDoracity = "小城聯盟";
	public final static String frameTitle = legendOfDoracity + " - " + "Legend of Doracity";
	public final static String font = "新細明體";
	public final static String confirm = "決定";
	public final static String loading = "正在啟動…";

	public final static String menu_option = "選項";
	public final static String menu_help = "幫助";
	public final static String menu_about = "關於";
	public final static String menu_aboutInfo = "這是為茶所設立的「小城聯盟」卡片對戰遊戲而開發的電腦程式。<br><br>遊戲設計：烏龍茶<br>程式製作：古怪<br>程式協力：射手小朋友<br>角色設計：蝶、山大、烏龍茶、淋琳<br>封面繪圖：羽須智行";
	public final static String menu_drawCardOneOff = "一次過抽所有卡片";
	public final static String menu_exit = "結束";

	public final static String help_debug = "DEBUG說明";
	public final static String help_debugPast = "過往更新";
	public final static String help_gameRules = "遊戲規則";
	public final static String help_beforeGame = "遊戲開始前的準備";
	public final static String help_gameFlow = "遊戲流程";
	public final static String help_vocab = "遊戲名詞";
	public final static String help_cards = "卡牌種類";
	public final static String help_QA = "Q & A";
	public final static String help_appHelp = "程式教學";
	public final static String help_preload = "起始畫面";
	public final static String help_icons = "圖案說明";
	public final static String help_appFAQ = "常見問題";

	public final static String comma = "，";
	public final static String comma2 = "、";

	/* Character Names */
	public final static String[] CharNames = new String[] { "", "烏龍茶", "淋琳", "古怪", "地圖", "鐵皮使",
			"魚丸串", "白銀劍士", "悠閒的大雄", "奈奈由宇", "野味大雄", "山大", "七色彩蝶", "羽須智行", "黑川米古", "勇者謙", "奈特", "雲",
			"蚊", "曹長", "AK", "古夢", "怪盜多啦", "小城響叮噹", "風音之鈴", "辛", "迷你", "T8", "安東尼", "射手小朋友", "心連心" };

	/* Character Titles */
	public final static String[] CharTitles1 = new String[] { "", "紳士的", "破壞女神", "魔劍士", "刺客",
			"新手的", "格鬥家", "魔法之劍", "祌射手", "黑魔女", "十歲打倒熊的", "德魯依學徒", "狂暴的", "普通的魔法使", "無情之箭", "人偶師",
			"唱詠使", "學習中的", "呆呆的", "不明的科學家", "流浪者", "魔術師", "DX", "狂戰士", "喵", "便利店職員", "狂氣科學者",
			"魔賦師", "畫家", "藥師", "童貞魔法師" };

	public final static String[] CharTitles2 = new String[] { "", "蘿莉控", "愛心天使", "火炎魔法師", "旅行詩人",
			"騎士王", "雙槍手", "大法師", "工口英雄", "理事長", "叮噹小城城主", "國家鍊金術師", "大小姐", "魔法少女", "暴力的", "傳說中的",
			"龍騎士", "獵人", "愛心使者", "宅宅", "戰士宗師", "詐騙師", "吸血鬼", "學園最強", "過剩者", "工口之王", "中二作家", "奏樂師",
			"再生所", "小朋友", "盜賊之首" };

	/* Character Skills */
	// [Characters][Passive,Active][Skills][Name,Description]
	public final static String[][][][] CharSkills1 = new String[][][][] { {},
			// 1. Tea
			{
					// Passive
					{ { "紳士", "場上若有女性角色攻擊+2" }, { "M魂", "受到女性角色攻擊時，回復1點HP" } },
					// Active
					{ { "抖M", "該回合所有敵方女性只能攻擊自己且不會進入防禦狀態，並提升1物防/1魔防" } } },
			// 2. Livia
			{
					// Passive
					{ { "相伴", "若古怪在場時，物防+1/魔防+1" } },
					// Active
					{ { "巨魔像", "使用一張祭品，攻擊+3/物防+2/魔防+2" } } },
			// 3. Phoebell
			{
					// Passive
					{ { "相伴", "若琳琳在場時+1點攻擊" }, { "戰法雙修", "攻擊可為物攻/魔攻" } },

					// Active
					{ { "穿透", "該回合減少一名敵方1點物防或1點魔防" } } },
			// 4. Map
			{
					// Passive
					{ { "不存在", "放棄此回合的行動，則不受任何傷害" } },
					// Active
					{ { "刺殺", "選擇一名敵方，在該回合當其受傷害時，額外造成1點真實傷害" } } },
			// 5. Iron
			{
					// Passive: SPECIAL CASE
					{ { "勤奮", "轉職時需額外使用5點MP" }, { "堅忍", "受到的傷害-1" } },

					// Active
					{ { "士氣", "+1點攻擊，每一名己方支援再+1點" } } },
			// 6. FishBall
			{
					// Passive
					{ { "極限", "若該回合發動自身技能，攻擊+1，並在戰鬥後回復MP2" } },
					// Active: SPECIAL CASE
					{ { "升龍拳", "在該回合破壞一名敵方的物防" }, { "翻跟斗", "在該回合禁止一名敵方使用技能" } } },
			// 7. Shirogane
			{
					// Passive
					{ { "共嗚", "己方每名法師MP回復+1" } },
					// Active
					{ { "冥想", "放棄該回合行動 回復3點 MP" } } },
			// 8. Nonki-Nobita
			{
					// Passive
					{ { "狙擊", "攻擊時無視對方裝備" } },
					// Active
					{ { "魔法箭", "對一名目標造成3+(自身普攻)點魔法傷害" } } },
			// 9. Nana
			{
					// Passive
					{ { "邪氣", "敵方速度-1" } },
					// Active
					{ { "心理攻擊", "除[法師]以外，對五名敵方造成1點真實傷害" } } },
			// 10. Game-Nobita
			{
					// Passive
					{ { "裝可愛", "受到男性角色攻擊時的傷害-1" } },
					// Active
					{ { "同行", "當己方受到攻擊時發動，讓目標轉向自己" } } },
			// 11. Xander
			{
					// Passive
					{ { "計算", "攻擊成功時額外造成1點真實傷害" } },
					// Active
					{ { "防壁", "物防+1" } } },
			// 12. Butterfly
			{
					// Passive
					{ { "反噬", "當傷害時，對攻擊者造成2點真實傷害" } },
					// Active
					{ { "報仇", "選取一名敵方，在回合內每攻擊一名己方，對其造成1點真實傷害" } } },
			// 13. Feather
			{
					// Passive
					{ { "天資", "轉職減少使用5MP" } },
					// Active
					{ { "修練", "對一名敵方造成5點魔法傷害，攻擊成功則回復1MP" } } },
			// 14. Kurokawa
			{
					// Passive
					{ { "攻擊箭", "攻擊成功時破壞目標裝備" } },
					// Active
					{ { "剛強", "攻擊+1" } } },
			// 15. Herohim
			{
					// Passive
					{ { "破防", "攻擊時該回合目標魔防-1" } },
					// Active
					{ { "人偶", "使用一張祭品並替換人偶戰鬥，人偶為5魔攻/2物防/3魔防 回合結束時消失" } } },
			// 16. Knight
			{
					// Passive
					{ { "魔環", "若該回合發動唱詠則在戰鬥後回復1MP" } },
					// Active
					{ { "唱詠", "選擇一項能力在該回合提升  1.攻擊+1 2.物防+1 3.魔防+1" } } },
			// 17. Cloud
			{
					// Passive
					{ { "教訓", "一回合內只能受到一名角色攻擊" } },
					// Active
					{ { "復仇", "攻擊+1，對該回合的攻擊者進行普攻" } } },
			// 18. Mandy Lee
			{
					// Passive
					{ { "過度善忘", "最高只能受到2點傷害" } },
					// Active
					{ { "兔兔！", " 選取一名已方進入防禦狀態" } } },
			// 19. Kuru
			{
					// Passive
					{ { "陰謀", "使用裝備時，MP回復+1" } },
					// Active
					{ { "發明", "使用一張祭品，複製場上任何一張裝備卡" } } },
			// 20. AK
			{
					// Passive
					{ { "反擊", "受攻擊時，能獲得一次機會對攻擊者普攻" } },
					// Active
					{ { "回避", "使一次普攻失敗，每回合一次" } } },
			// 21. Kuzmon
			{
					// Passive
					{ { "掩眼法", "可棄置兩張手牌，再重新抽回兩張" } },
					// Active
					{ { "轉移魔術", "與敵方強制交換一張手牌" } } },
			// 22. KaitoDora
			{
					// Passive
					{ { "銷魂", "每受到1點傷害，吸收對方1MP" } },
					// Active
					{ { "偷竊", "隨機抽取對方一張手牌" } } },
			// 23. LittleCity
			{
					// Passive
					{ { "自虐", "己方-1HP" } },
					// Active
					{ { "狂化", "該回合每損失3HP，攻擊+2 後制:順位時將會是最後行動" } } },
			// 24. Wind Sound
			{
					// Passive
					{ { "貓型態", "4物攻/2物防/2魔防/4AC" } },
					// Active
					{ { "貓化", "能隨時轉為貓型態" } } },
			// 25. Shin
			{
					// Passive
					{ { "贈品", "受敵方技能卡攻擊時，能獲取該卡" } },
					// Active
					{ { "購物", "拾取一張該回合使用的道具卡" } } },
			// 26. Mini
			{
					// Passive
					{ { "狂氣", "受挑釁的角色攻擊時，所受的傷害-1" } },
					// Active
					{ { "挑釁", "被選取的目標在該回合只能攻擊自己" } } },
			// 27. T8
			{
					// Passive
					{ { "魔賦", "提升己方陣營裝備效果 武器時攻擊+1 防具時物防+1" } },
					// Active
					{ { "魔力消除", "放棄一張裝備卡，回復5點MP" } } },
			// 28. Anthony
			{
					// Passive
					{ { "藝術", "每當全上有角色使用技能卡時，回復己方1MP" } },
					// Active
					{ { "繪畫", "受到敵方技能卡攻擊時，使用一張祭品以同樣的技能反擊，一回合一次" } } },
			// 29. Sasa
			{
					// Passive
					{ { "藥師", "使用道具回復時+1HP" } },
					// Active
					{ { "製藥", "使用一張祭品回復3點HP" } } },
			// 30. SunnyShum
			{
					// Passive
					{ { "童貞", "己方沒有女性角色時，攻擊+3" } },
					// Active
					{ { "激勵", "己方男性角色攻擊+1" } } },
	// End
	};

	public final static String[][][][] CharSkills2 = new String[][][][] { {},
			// 1. Tea
			{
					// Passive
					{ { "工口", "按場上每名女性角色攻擊+1" } },
					// Active
					{ { "蘿莉之魂", "按場上每名女性角色+自身1點攻擊" } } },
			// 2. Livia
			{
					// Passive
					{ { "相伴", "若古怪在場時，回復額外+1HP" } },
					// Active
					{ { "回復", "回復2點HP" } } },
			// 3. Phoebell
			{
					// Passive
					{ { "相伴", "若琳琳在場時，MP回復+1" } },
					// Active
					{ { "隕石術", "對敵方陣營造成(自身普攻)點魔法傷害及物理傷害" } } },
			// 4. Map
			{
					// Passive
					{ { "妹控", "場上若有女性角色物防+2" } },
					// Active
					{ { "誘惑之歌", "選擇一名目標在該回合無法行動" } } },
			// 5. Iron
			{
					// Passive
					{ { "英勇", "攻擊劍士時，攻擊+1" } },
					// Active
					{ { "突擊", "對一名防禦狀態的敵方使用，對其進行普攻" } } },
			// 6. FishBall
			{
					// Passive
					{ { "雙槍射擊", "對相同目標進行兩次普攻" } },
					// Active
					{ { "彈雨", "對三名敵方造成5點魔法傷害" } } },
			// 7. Shirogane
			{
					// Passive
					{ { "洞察", "攻擊時目標魔防-1，[劍士]再-1" } },
					// Active
					{ { "古代魔法", "對三名敵方造成5點魔法傷害，[劍士]額外+2" } } },
			// 8. Nonki-Nobita
			{
					// Passive
					{ { "變態", "受到女性角色攻擊時能獲得一張手牌" } },
					// Active
					{ { "工口寶庫", "對一名目標造成2點魔法傷害，每使用一張祭品額外+2" } } },
			// 9. Nana
			{
					// Passive
					{ { "學園", "學園陣營角色攻擊+1(若有三名學園角色+2 五位+3)" } },
					// Active
					{ { "威嚴", "曾攻擊自身的敵方於下回合跳過戰鬥時" } } },
			// 10. Game-Nobita
			{
					// Passive
					{ { "小城", "小城陣營角色物防+1(若有兩名小城角色+1魔防 四名+2物防 五名+2魔防)" } },
					// Active
					{ { "城主", "當己方受攻擊時發動，傷害-1" } } },
			// 11. Xander
			{
					// Passive
					{ { "魔凝", "失去最後一張手牌時立即回復2點MP" } },
					// Active
					{ { "合成", "獲得兩張手牌" }, { "分解", " 放棄一張手牌回復2點MP" } } },
			// 12. Butterfly
			{
					// Passive
					{ { "富貴", "摸牌數量額外+1" } },
					// Active
					{ { "揮霍", "使用一張祭品，攻擊+2" } } },
			// 13. Feather
			{
					// Passive
					{ { "屬性", "技能攻擊成功時附帶一種屬性 火:造成1點真實傷害 冰:降低目標2MP 雷:回復2MP" } },
					// Active
					{ { "集束炮", "對一名敵方造成8點魔法傷害" }, { "廣域炮", "對三名敵方造成4點魔法傷害" } } },
			// 14. Kurokawa
			{
					// Passive
					{ { "暴力", "當對目標造成等於或大於2點傷害時，額外造成1點真實傷害" } },
					// Active
					{ { "多重箭", "該回合可對三名目標普攻" } } },
			// 15. Herohim
			{
					// Passive
					{ { "傳說", "當目標魔防比自身低時，傷害+1" } },
					// Active
					{ { "爆裂術", "向一名敵方造成10點魔法傷害，若攻擊成功將轉移至另一名敵方，攻擊為上次造成的傷害，直至攻擊失敗" } } },
			// 16. Knight
			{
					// Passive
					{ { "龍屬性", "當自身物防比目標高，攻擊+2  當己方魔防比對方底，魔防+1" } },
					// Active
					{ { "龍笛", "對五位敵方造成1點真實傷害" } } },
			// 17. Cloud
			{
					// Passive
					{ { "魔女獵人", "攻擊[法師]時傷害+2" } },
					// Active
					{ { "全神貫注", "放棄自身魔防，攻擊+3" } } },
			// 18. Mandy Lee
			{
					// Passive
					{ { "憐憫之心", "對目標造成傷害時，可選擇放棄攻擊並回復相當於傷害的HP" } },
					// Active
					{ { "休息", "放棄此回合行動，HP+2" } } },
			// 19. Kuru
			{
					// Passive
					{ { "翻譯", "受到攻擊時，能將攻擊隨意轉化為物傷/魔傷(包括真傷)" } },
					// Active
					{ { "賭博", "使用一張手牌做賭注並抽一張牌，若為[裝備卡]則沒有損失，若非則無法獲得任何卡" } } },
			// 20. AK
			{
					// Passive
					{ { "劍藝", "攻擊時無視目標提升的物防" } },
					// Active
					{ { "無雙", "對目標造成(自身普攻)+(目標每2點攻擊+1)+(屬性為物理+2)點的物理修害" } } },
			// 21. Kuzmon
			{
					// Passive
					{ { "幻術", "受攻擊時，如使用了[詐騙]則能把攻擊轉移到該目標" } },
					// Active
					{ { "詐騙", "跟一名敵方交換(包括裝備卡及己覆蓋的卡牌，可使用角色技能)，維持一回合" } } },
			// 22. KaitoDora
			{
					// Passive
					{ { "吸血", "每造成1點傷害，回復己方1HP" } },
					// Active
					{ { "嗜血", "敵方使用技能/道具回復HP時，能偷取其效果，每回合一次" } } },
			// 23. LittleCity
			{
					// Passive
					{ { "最強", "與「學園」角色戰鬥時 攻擊+2/物防+1/魔防+1" } },
					// Active
					{ { "魔盾", "每使用1點MP使吸收2點傷害，不限使用次數 若發動此技能，[戰鬥後]回復2點MP" } } },
			// 24. Wind Sound
			{
					// Passive
					{ { "雜耍", "能使用任何職業的裝備及技能卡" } },
					// Active
					{ { "轉蛋", "可以抽取一張卡，若該卡為裝備卡，則該回合不受任何傷害" } } },
			// 25. Shin
			{
					// Passive
					{ { "工口意志", "當攻擊男性角色時，可回復2點MP" } },
					// Active
					{ { "工口遊戲", "男性角色限定，對一名敵方造成5點魔法傷害及5點物理傷害，不限使用次數" } } },
			// 26. Mini
			{
					// Passive
					{ { "中二", "對敵方使用自身技能時，+2點物理或+2魔法傷害" } },
					// Active
					{ { "創作", "對 (每目標/2MP)造成( 1點/每2MP)  +的 (物理/魔法)傷害" } } },
			// 27. T8
			{
					// Passive
					{ { "豐年曲", "當使用自身技能時，回復1點HP" }, { "安哥", "沒有限制使用技能的次數，若再次使用則減少1點MP消耗" } },
					// Active
					{ { "狂想曲", "五名己方攻擊+1" }, { "奏嗚曲", "五名己方物防+1" }, { "協奏曲", "五名己方魔防+1" } } },
			// 28. Anthony
			{
					// Passive
					{ { "利用", "每次使用技能卡，再用一張祭品發動第二次效果" } },
					// Active
					{ { "再生", "拾取一張該回合使用的技能卡" } } },
			// 29. Sasa
			{
					// Passive
					{ { "天才", "任意選擇一名目標的被動，維持該回合" } },
					// Active
					{ { "學習", "任意使用一名角色的技能[限戰鬥時]，額外使用5MP" } } },
			// 30. SunnyShum
			{
					// Passive
					{ { "盜竊", "攻擊成功時，偷取目標的裝備卡放置手牌" } },
					// Active
					{ { "遊擊", "只能對物防高於自身攻擊的目標使用，對其造成1點真實傷害並計算作「攻擊成功」" } } },
	// End
	};

	/* Jobs */
	public final static String[] JobNames = new String[] { "劍士", "弓手", "法師", "支援者", "N/A" };

	/* Card Names and Descriptions */
	public final static String equipment = "裝備";

	public final static String[] EquipmentTypes = new String[] { "", "騎士之鎧", "神聖之盾", "雙影狂舞", "暴走鞋",
			"羽毛弓", "狙擊槍", "法師帽", "寒冰杖", "火炎杖", "充雷杖", "救護服", "物理結界", "魔法結界", "魔法石", "冒險者之劍",
			"吸血鐮刀", "真實之刃", "先制之爪", "魔法學徒杖", "巫師袍", "治療杖", "輕飄飄鞋", "布甲", "抗魔法蓬", "學園之界", "小城之壁" };

	public final static String[] EquipmentInfos = new String[] { "", "物防+2<br>減少1點所受的普攻傷害",
			"已方陣營角色：物防+1、魔防+1", "速度+1<br>對相同目標進行額外一次普攻", "速度+2<br>先制: 順位時將在最先行動",
			"攻擊+1、速度+1<br>增加1點普攻傷害", "攻擊+1<br>普通攻擊無視防禦狀態", "攻擊+1、魔防+1<br>使用技能攻擊時傷害+1",
			"攻擊+1<br>使用技能攻擊成功時減少敵方1MP", "攻擊+1<br>使用技能攻擊成功時追+1點真實傷害", "攻擊+1<br>使用技能攻擊成功增加回復1MP",
			"物防+1、魔防+1<br>使用技能時回復1HP", "提升己方陣營1點物防", "提升己方陣營1點魔防", "增加1點MP回復量", "攻擊+1",
			"攻擊成功時額外偷取對方1HP回復", "攻擊+1<br>攻擊成功時額外造成1點真實傷害", "攻擊+1、速度+1<br>先制: 順位時將在最先行動", "攻擊+1",
			"攻擊+1、魔防+2<br>減少1點所受的技能傷害", "攻擊+1、物防+2<br>使用技能時若消耗3MP以上則回復1HP", "速度+1", "物防+1", "魔防+1",
			"限「學園」陣營角色裝備, 已方學園陣營角色：物防+1、魔防+1", "限「小城」陣營角色裝備, 已方小城陣營角色：物防+1、魔防+1" };

	public final static String[] ItemTypes = new String[] { "", "生命水", "魔法水", "煙霧彈" };

	public final static String item = "道具";

	public final static String[] ItemInfos = new String[] { "", "回復5HP", "回復5MP", "消耗一次行動，無視一次攻擊" };

	public final static String skill = "技能";

	public final static String[] SkillTypes = new String[] { "", "突擊", "格鬥拳", "戰魂", "不滅", "狙擊箭",
			"支援箭", "回避", "鷹眼", "火雨術", "寒冰術", "雷電術", "治療術", "大治療術", "風之術", "禁言", "沉默", "牢固", "物理結界",
			"魔法結界", "神聖祝福", "吸血術" };

	public final static String[] SkillInfos = new String[] { "", "對一名已防禦狀態的角色造成5點物理傷害",
			"使目標在該回合物防歸0", "此回合提升2點物攻", "N/A", "N/A", "該目標在此回合所受到傷害額外+1", "自身受普通攻擊時，無效一次",
			"普通攻擊時無視對方一切提升的物防", "敵方陣營目標受到(自身普攻)的魔法傷", "敵方陣營降低1點魔防", "每當對敵方陣營造成傷害時，額外造成4點魔法傷害",
			"N/A", "回復10點HP", "N/A", "禁止一名目標在該回合發動任何技能", "N/A", "禁止一名目標在該回合進行普通攻擊" };

	public final static String availableJob = "可用職業：";
	public final static String consume = "消耗";
	public final static String weapon = "武器";
	public final static String armor = "防具";

	/* Properties */
	public final static String HP = "HP";
	public final static String MP = "MP";
	public final static String job = "職業";
	public final static String property = "屬性";
	public final static String physical = "物理";
	public final static String mana = "魔法";
	public final static String attack = "攻擊";
	public final static String defP = "物防";
	public final static String defM = "魔防";
	public final static String speed = "速度";
	public final static String side = "陣營";
	public final static String doracity = "小城";
	public final static String academy = "學園";

	public final static String job1 = "一職";
	public final static String job2 = "二職";

	/* Buttons */
	public final static String normalAttack = "普通攻擊";
	public final static String castSkill = "發動技能";
	public final static String jobChange = "轉職";
	public final static String drawCard = "抽牌";
	public final static String unequip = "解除裝備";
	public final static String pass = "Pass";

	/* Button Descriptions */
	public final static String normalAttackInfo = "選取對方一名角色，對其進行普通攻擊。";
	public final static String castSkillInfo = "查看角色技能，並選擇使用其中一項。";
	public final static String[] jobChangeInfo = { "消耗", "MP進行轉職。" };
	public final static String drawCardInfo = "抽一張手牌。";
	public final static String unequipInfo = "解除角色當前的裝備。";
	public final static String passInfo = "結束此階段。";

	/* Skill messages */
	public final static String[] occasion = { "", "[戰鬥前]", "[戰鬥時]", "[戰鬥後]", "[摸牌階段]" };

	/* Skill Selection */
	public final static String skillSelection = "選擇技能";
	public final static String active = "主動";
	public final static String passive = "被動";

	/* Selection Dialogs */
	public final static String charSelection = "選擇角色";
	public final static String cardSelection = "選擇祭品";
	public final static String effectSelection = "選擇效果";

	/* Status */
	public final static String player = "玩家";
	public final static String round = "回合";
	public final static String cardsLeft = "剩餘卡片數量";
	public final static String none = "<font color=gray>無</font>";

	/* Stages */
	public final static String stage_readyToPlay = "遊戲準備完畢";
	public final static String stage_drawCards = "摸牌階段";
	public final static String stage_prepare = "準備階段";
	public final static String stage_autoHealing = "自動回復";
	public final static String stage_useItem = "使用道具";
	public final static String stage_equipOrJobChange = "裝備、轉職";
	public final static String stage_beforeBattle = "戰鬥前";
	public final static String stage_duringBattle = "戰鬥時";
	public final static String stage_afterBattle = "戰鬥後";

	/* Log */
	public final static String log = "對戰紀錄";
	public final static String log_gameStart = "遊戲開始";
	public final static String log_round = "的回合：";
	public final static String log_attackSuccess = "攻擊成功";
	public final static String log_attackFailed = "攻擊失敗";
	public final static String log_castSkill = "發動了技能：";
	public final static String log_useSkillCard = "使用了技能卡：";
	public final static String log_skillEffect = "技能效果：";
	public final static String log_lost = "戰敗！";
	public final static String log_jobChange = "轉成";
	public final static String log_equip = "裝備了";
	public final static String log_removeEquipment = "移除了裝備";

	/* Log: Skill messages */
	public final static String log_passiveSkillEnd = "的前職被動技能效果完結";
	public final static String[] log_for_round_end = { "回合結束，施加在", "上的技能效果移除" };
	public final static String choose = "選擇了：";
	public final static String totalDamage = "總計：";
	public final static String giveUp = "於本回合無法行動";
	public final static String givenUp = "已無法於本回合行動，因此自動跳過";
	public final static String giveUpSkills = "於本回合無法使用任何技能";
	public final static String giveUpNormalAttack = "於本回合無法使用普通攻擊";
	// 1. Tea
	public final static String tea_gentleman = "的紳士技能因場上有女性角色而發動：";
	public final static String tea_MSoul = CharNames[1] + "的M魂技能發動：受到女性角色攻擊時，回復1HP";
	public final static String tea_DoM = CharNames[1] + "的抖M技能正在發動：";
	public final static String tea_DoM_attackTeaOnly = tea_DoM + "女性只能攻擊" + CharNames[1];
	public final static String tea_DoM_lessDamage = tea_DoM + "傷害-1";
	public final static String tea_ero = CharNames[1] + "的工口技能發動：場上的女性角色數量是";
	// 2. Livia
	public final static String livia_together = CharNames[3] + "在場，" + CharNames[2] + "的相伴技能發動：";
	public final static String livia_together_job1 = livia_together;
	public final static String livia_together_job2 = livia_together + "額外回復1HP";
	// 3. Phoebell
	public final static String phoebell_together = CharNames[2] + "在場，" + CharNames[3] + "的相伴技能發動：";
	public final static String phoebell_together_job2 = CharNames[3] + "的相伴技能發動：MP回復+1";
	// 4. Map
	public final static String map_assassin = CharNames[4] + "的刺殺技能正在發動：";
	public final static String map_assassin_moreDamage = map_assassin + "傷害+1";
	public final static String map_siscon = "場上有女性角色，" + CharNames[4] + "的妹控技能發動：";
	// 5. Iron
	public final static String iron_fortitude_lessDamage = CharNames[5] + "的堅忍技能發動：傷害-1";
	public final static String iron_heroic = CharNames[5] + "的英勇技能發動：攻擊劍士時攻擊+1";
	// 6. FishBall
	public final static String fishball_limit = CharNames[6] + "的極限技能發動：攻擊+1，並在戰鬥後回復2MP";
	public final static String fishball_limit2 = CharNames[6] + "的極限技能發動：戰鬥後回復2MP";
	// 7. Shirogane
	public final static String shirogane_resonance = CharNames[7] + "的共鳴技能發動：每名法師額外回復1MP";
	// 9. Nana
	public final static String nana_evil = CharNames[9] + "的邪氣技能發動：敵方速度-1";
	public final static String nana_evil_end = CharNames[9] + "的邪氣技能效果完結";
	// 10. GameNobita
	public final static String gamenobita_doracity[] = { "己方陣型共有", "名小城陣型角色，因此技能效果為：每名小城陣型角色" };
	public final static String gamenobita_doracity_effect[] = { "物防+1", "物防+1、魔防+1", "物防+2、魔防+1",
			"物防+2、魔防+2" };
	public final static String gamenobita_doracity_end = CharNames[10] + "的小城技能效果完結";
	// 11. Xander
	public final static String xander_manaForming = CharNames[11] + "的魔凝技能發動：沒手牌時+2MP";
	// 12. Buttefly
	public final static String butterfly_wealth = "的富貴技能發動：摸牌數量+1";
	// 14. Kurokawa
	public final static String kurokawa_strong_end = CharNames[14] + "的剛強技能效果完結";
	// 15. Herohim
	public final static String herohim_doll_toString = "的人偶";
	public final static String herohim_doll_end = "的人偶消失";
	// 19. Kuru
	public final static String kuru_badIntention = "的陰謀技能發動：使用裝備時，MP回復+1";
	public final static String kuru_gambling_result = "抽到的卡是：";
	public final static String kuru_gambling_equipment = "此卡乃裝備卡，因此兩張卡均得以保留";
	public final static String kuru_gambling_notEquipment = "此卡並非裝備卡，因此兩張卡均無法獲得";
	// 21. Kuzmon
	public final static String kuzmon_transferMagic1 = "以";
	public final static String kuzmon_transferMagic2 = "與對方交換了";
	// 22. KaitoDora
	public final static String kaitodora_steal = "偷竊了";
	// 24. WindSound
	public final static String windsound_catForm_cat = CharNames[24] + "已轉為貓形態！";
	public final static String windsound_catForm_toString = "（貓形態）";
	public final static String windsound_catForm_human = CharNames[24] + "已轉回人形！";
	// 27. T8
	public final static String t8_enchant_weapon_start = CharNames[27] + "的魔賦技能發動：裝備武器時攻擊+1";
	public final static String t8_enchant_armor_start = CharNames[27] + "的魔賦技能發動：裝備防具時物防+1";
	public final static String t8_enchant_weapon_end = CharNames[27] + "的魔賦技能效果移除：武器的攻擊-1";
	public final static String t8_enchant_armor_end = CharNames[27] + "的魔賦技能效果移除：防具的物防-1";
	// 29. Sasa
	public final static String sasa_pharmacist = CharNames[29] + "的藥師技能發動：額外使用道具回復時+1HP";
	// 30. SunnyShum
	public final static String sunnyshum_chasteness = "己方沒有女性角色，" + CharNames[30] + "的童貞技能發動：";

	/* Error Messages */
	public final static String error = "錯誤";

	public final static String playerNameEmpty = "請填入玩家名稱";
	public final static String charCountWrong = "請選擇五名角色";
	public final static String repeatedChar = "雙方角色重複\n雙方均選擇了";

	public final static String wrongJob = "當前角色的職業無法使用";
	public final static String notDoracity = "這角色不屬於小城陣營";
	public final static String notAcademy = "這角色不屬於學園陣營";
	public final static String noCardsLeft = "卡組沒有牌了";
	public final static String charInDefense = "角色防禦中";
	public final static String attackFailed = "攻擊失敗";
	public final static String noMP = "MP不足";
	public final static String removeEquip = "角色裝備將被移除";
	public final static String charDefensing = "角色正在防禦中";
	public final static String cannotAttack = "無法攻擊";

	public final static String badURL = "URL出錯";
	public final static String fileNotFound = "找不到文件：";

	/* Game Over Messages */
	public final static String gameOver = "遊戲結束";
	public final static String winner = "勝者";
	public final static String loser = "敗者";

}
