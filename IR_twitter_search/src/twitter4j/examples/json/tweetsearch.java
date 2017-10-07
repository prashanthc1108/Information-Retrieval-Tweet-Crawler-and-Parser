package twitter4j.examples.json;
//import org.json.simple.JSONObject;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.Query;
import twitter4j.Query.ResultType;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterObjectFactory;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;
import twitter4j.conf.ConfigurationBuilder;

public class tweetsearch {

	public static void main(String[] args) {
//		Toolkit.getDefaultToolkit().beep();
//		System.exit(0);
		String fromDate = "2016-09-07";
		String toDate = "2016-09-21";
		String topic = "tech";
		String tweetLang = "ko";
//		String queryString ="#election2016 OR (Estados Unidos AND (elección OR presidente)) OR Hillary OR Trump +exclude:retweets";
		String queryString ="아이폰 +exclude:retweets";
//		String queryString ="(도날드 AND 트럼프) OR (힐러리 AND 클린턴) OR (미국 AND (대통령 OR 선거)) +exclude:retweets";//politics
		long tweetMaxId = -1;
		


		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
				.format(new Timestamp(System.currentTimeMillis()));

		String[] emoticonStrings = new String[] { ":‑)", ":)", ":-]", ":]", ":-3", ":3", ":->", ":>", "8-)", "8)",
				":-}", ":}", ":o)", ":c)", ":^)", ":‑D", ":D", "8‑D", "8D", "x‑D", "xD", "X‑D", "XD", "B^D", ":-))",
				":‑(", ":(", ":‑c", ":c", ":‑<", ":<", ":‑[", ":[", ":-||", ">:[", ":{", ":@", ">:(", ":'‑(", ":'(",
				":'‑)", ":')", "D‑':", "D:<", "D:", "D8", "D;", "D=", "DX", ":‑O", ":O", ":‑o", ":o", ":-0", "8‑0",
				">:O", ":-*", ":*", ":×", ";‑)", ";)", "*-)", "*)", ";‑]", ";]", ";^)", ":‑,", ";D", ":‑P", ":P", "X‑P",
				"XP", "x‑p", "xp", ":‑p", ":p", ":‑Þ", ":Þ", ":‑þ", ":þ", ":‑b", ":b", "d:", ">:P", ":‑/", ":/", ":‑.",
				">:'\'", ">:/", ":'\'", ":L", ":‑|", ":|", ":$", ":‑X", ":X", ":‑#", ":#", ":‑&", ":&", "O:‑)", "O:)",
				"0:‑3", "0:3", "0:‑)", "0:)", "0;^)", ">:‑)", ">:)", "}:‑)", "}:)", "3:‑)", "3:)", ">;)", "|;‑)", "|‑O",
				":‑J", "#‑)", "%‑)", "%)", ":‑###..", ":###..", "<:‑|", "=]", "=)", "=3)", "=D", "=P", "='\'", "=/",
				"=L" };

		String[] kaomojiStrings = new String[] { "(>_<)", "(>_<)>", "(';')", "(^^ゞ", "(^_^;)", "(-_-;)", "(~_~;)",
				"(・。・;)", "(・_・;)", "(・・;)", "^^;", "^_^;", "(#^.^#)", "(^^;)", "(^。^)y-.。o○", "(-。-)y-゜゜゜", "(-_-)zzz",
				"(^_-)", "(^_-)-☆", "((+_+))", "(+o+)", "(゜゜)", "(゜-゜)", "(゜.゜)", "(゜_゜)", "(゜_゜>)", "(゜レ゜)", "(o|o)",
				"<(｀^´)>", "^_^", "(゜o゜)", "(^_^)/", "(^O^)／", "(^o^)／", "(^^)/", "(≧∇≦)/", "(/◕ヮ◕)/", "(^o^)丿",
				"∩(・ω・)∩", "(・ω・)", "^ω^", "(__)", "_(._.)_", "_(_^_)_", "<(_ _)>", "<m(__)m>", "m(__)m", "m(_ _)m",
				"＼(゜ロ＼)ココハドコ?", "(／ロ゜)／アタシハダアレ?", "('_')", "(/_;)", "(T_T)", "(;_;)", "(;_;", "(;_:)", "(;O;)", "(:_;)",
				"(ToT)", "(Ｔ▽Ｔ)", ";_;", ";-;", ";n;", ";;", "Q.Q", "T.T", "QQ", "Q_Q", "(ー_ー)!!", "(-.-)", "(-_-)",
				"(一一)", "(；一_一)", "(=_=)", "(=^・^=)", "(=^・・^=)", "(..)", "(._.)", "^m^", "(・・?", "(?_?)", "(－‸ლ)",
				">^_^<", "<^!^>", "^/^", "（*^_^*）", "§^。^§", "(^<^)", "(^.^)", "(^ム^)", "(^・^)", "(^。^)", "(^_^.)",
				"(^_^)", "(^^)", "(^J^)", "(*^。^*)", "^_^", "(#^.^#)", "（＾－＾）", "(^^)/~~~", "(^_^)/~", "(;_;)/~~~",
				"(^.^)/~~~", "($・・)/~~~", "(@^^)/~~~", "(T_T)/~~~", "(ToT)/~~~", "(V)o￥o(V)", "＼(~o~)／", "＼(^o^)／",
				"＼(-o-)／", "ヽ(^。^)ノ", "ヽ(^o^)丿", "(*^0^*)", "(*_*)", "(*_*;", "(+_+)", "(@_@)", "(@_@。", "(＠_＠;)",
				"＼(◎o◎)／！", "(-_-)/~~~ピシー!ピシー!", " !(^^)!", "(*^^)v", "(^^)v", "(^_^)v", "(＾▽＾)", "（・∀・）", "（´∀｀）",
				"（⌒▽⌒）", "（＾ｖ＾）", "（’-’*)", "(~o~)", "(~_~)", "(^^ゞ", "(p_-)", "((d[-_-]b))", "(ーー゛)", "(^_^メ)",
				"(-_-メ)", "(｀´）", "(~_~メ)", "(－－〆)", "(・へ・)", "<`～´>", "<`ヘ´>", "(ーー;)", "(^0_0^)", "( ..)φメモメモ",
				"φ(..)メモメモ", "（●＾o＾●）", "（＾ｖ＾）", "（＾ｕ＾）", "（＾◇＾）", "( ^)o(^ )", "(^O^)", "(^o^)", "(^○^)", ")^o^(",
				"(*^▽^*)", "(✿◠‿◠)", "（￣ー￣）", "（￣□￣；）", "°o°", "°O°", ":O", "o_O", "o_0", "o.O", "(o.o)", "（*´▽｀*）",
				"(*°∀°)=3", "（ ﾟ Дﾟ）", "（゜◇゜）", "(*￣m￣)", "ヽ（´ー｀）┌", "(´･ω･`)", "(‘A`)", "(*^3^)/~☆", ".....φ(・∀・＊)",
				"キタ━━━(゜∀゜)━━━!!!!! ", "=_^=" };



		String[] en_stopwords = new String[] { "'ve ", "'s ", " a ", " about ", " above ", " after ", " again ",
				" against ", " all ", " am ", " an ", " and ", " any ", " are ", " aren't ", " as ", " at ", " be ",
				" because ", " been ", " before ", " being ", " below ", " between ", " both ", " but ", " by ",
				" can't ", " cannot ", " could ", " couldn't ", " did ", " didn't ", " do ", " does ", " doesn't ",
				" doing ", " don't ", " down ", " during ", " each ", " few ", " for ", " from ", " further ", " had ",
				" hadn't ", " has ", " hasn't ", " have ", " haven't ", " having ", " he ", " he'd ", " he'll ",
				" he's ", " her ", " here ", " here's ", " hers ", " herself ", " him ", " himself ", " his ", " how ",
				" how's ", " i ", " i'd ", " i'll ", " i'm ", " i've ", " if ", " in ", " into ", " is ", " isn't ",
				" it ", " it's ", " its ", " itself ", " let's ", " me ", " more ", " most ", " mustn't ", " my ",
				" myself ", " no ", " nor ", " not ", " of ", " off ", " on ", " once ", " only ", " or ", " other ",
				" ought ", " our ", " ours ", " ourselves ", " out ", " over ", " own ", " same ", " shan't ", " she ",
				" she'd ", " she'll ", " she's ", " should ", " shouldn't ", " so ", " some ", " such ", " than ",
				" that ", " that's ", " the ", " their ", " theirs ", " them ", " themselves ", " then ", " there ",
				" there's ", " these ", " they ", " they'd ", " they'll ", " they're ", " they've ", " this ",
				" those ", " through ", " to ", " too ", " under ", " until ", " up ", " very ", " was ", " wasn't ",
				" we ", " we'd ", " we'll ", " we're ", " we've ", " were ", " weren't ", " what ", " what's ",
				" when ", " when's ", " where ", " where's ", " which ", " while ", " who ", " who's ", " whom ",
				" why ", " why's ", " with ", " won't ", " would ", " wouldn't ", " you ", " you'd ", " you'll ",
				" you're ", " you've ", " your ", " yours ", " yourself ", " yourselves " };


		String[] es_stopwords = new String[] { " un ", " una ", " unas ", " unos ", " uno ", " sobre ", " todo ",
				" también ", " tras ", " otro ", " algún ", " alguno ", " alguna ", " algunos ", " algunas ", " ser ",
				" es ", " soy ", " eres ", " somos ", " sois ", " estoy ", " esta ", " estamos ", " estais ", " estan ",
				" como ", " en ", " para ", " atras ", " porque ", " por qué ", " estado ", " estaba ", " ante ",
				" antes ", " siendo ", " ambos ", " pero ", " por ", " poder ", " puede ", " puedo ", " podemos ",
				" podeis ", " pueden ", " fui ", " fue ", " fuimos ", " fueron ", " hacer ", " hago ", " hace ",
				" hacemos ", " haceis ", " hacen ", " cada ", " fin ", " incluso ", " primero   desde ", " conseguir ",
				" consigo ", " consigue ", " consigues ", " conseguimos ", " consiguen ", " ir ", " voy ", " va ",
				" vamos ", " vais ", " van ", " vaya ", " gueno ", " ha ", " tener ", " tengo ", " tiene ", " tenemos ",
				" teneis ", " tienen ", " el ", " la ", " lo ", " las ", " los ", " su ", " aqui ", " mio ", " tuyo ",
				" ellos ", " ellas ", " nos ", " nosotros ", " vosotros ", " vosotras ", " si ", " dentro ", " solo ",
				" solamente ", " saber ", " sabes ", " sabe ", " sabemos ", " sabeis ", " saben ", " ultimo ",
				" largo ", " bastante ", " haces ", " muchos ", " aquellos ", " aquellas ", " sus ", " entonces ",
				" tiempo ", " verdad ", " verdadero ", " verdadera cierto ", " ciertos ", " cierta ", " ciertas ",
				" intentar ", " intento ", " intenta ", " intentas ", " intentamos ", " intentais ", " intentan ",
				" dos ", " bajo ", " arriba ", " encima ", " usar ", " uso ", " usas ", " usa ", " usamos ", " usais ",
				" usan ", " emplear ", " empleo ", " empleas ", " emplean ", " ampleamos ", " empleais ", " valor ",
				" muy ", " era ", " eras ", " eramos ", " eran ", " modo ", " bien ", " cual ", " cuando ", " donde ",
				" mientras ", " quien ", " con ", " entre ", " sin ", " trabajo ", " trabajar ", " trabajas ",
				" trabaja ", " trabajamos ", " trabajais ", " trabajan ", " podria ", " podrias ", " podriamos ",
				" podrian ", " podriais ", " yo ", " aquel " };
		String[] tr_stopwords = new String[] { "'a "," acaba ", " altmýþ ", " altý ", " ama ", " bana ", " bazý ", " belki ",
				" ben ", " benden ", " beni ", " benim ", " beþ ", " bin ", " bir ", " biri ", " birkaç ", " birkez ",
				" birþey ", " birþeyi ", " biz ", " bizden ", " bizi ", " bizim ", " bu ", " buna ", " bunda ",
				" bundan ", " bunu ", " bunun ", " da ", " daha ", " dahi ", " de ", " defa ", " diye ", " doksan ",
				" dokuz ", " dört ", " elli ", " en ", " gibi ", " hem ", " hep ", " hepsi ", " her ", " hiç ", " iki ",
				" ile ", " INSERmi ", " ise ", " için ", " katrilyon ", " kez ", " ki ", " kim ", " kimden ", " kime ",
				" kimi ", " kýrk ", " milyar ", " milyon ", " mu ", " mü ", " mý ", " nasýl ", " ne ", " neden ",
				" nerde ", " nerede ", " nereye ", " niye ", " niçin ", " on ", " ona ", " ondan ", " onlar ",
				" onlardan ", " onlari ", " onlarýn ", " onu ", " otuz ", " sanki ", " sekiz ", " seksen ", " sen ",
				" senden ", " seni ", " senin ", " siz ", " sizden ", " sizi ", " sizin ", " trilyon ", " tüm ", " ve ",
				" veya ", " ya ", " yani ", " yedi ", " yetmiþ ", " yirmi ", " yüz ", " çok ", " çünkü ", " üç ",
				" þey ", " þeyden ", " þeyi ", " þeyler ", " þu ", " þuna ", " þunda ", " þundan ", " þunu " };
		String[] ko_stopwords = new String[] {" 아 "," 휴 "," 아이구 "," 아이쿠 "," 아이고 "," 어 "," 나 "," 우리 "," 저희 "," 따라 "," 의해 "," 을 "," 를 "," 에 "," 의 "," 가 "," 으로 "," 로 "," 에게 "," 뿐이다 "," 의거하여 "," 근거하여 "," 입각하여 "," 기준으로 "," 예하면 "," 예를 들면 "," 예를 들자면 "," 저 "," 소인 "," 소생 "," 저희 "," 지말고 "," 하지마 "," 하지마라 "," 다른 " 
		," 물론 "," 또한 "," 그리고 "," 비길수 없다 "," 해서는 안된다 "," 뿐만 아니라 "," 만이 아니다 "," 만은 아니다 "," 막론하고 "," 관계없이 "," 그치지 않다 "," 그러나 "," 그런데 "," 하지만 "," 든간에 "," 논하지 않다 "," 따지지 않다 "," 설사 "," 비록 "," 더라도 "," 아니면 "," 만 못하다 "," 하는 편이 낫다 "," 불문하고 "," 향하여 "," 향해>서 "," 향하다 "," 쪽으로 "," 틈타 "," 이용하여 "," 타다 "," 오르다 ",
		" 제외하고 "," 이 외에 "," 이 밖에 "," 하여야 "," 비로소 "," 한다면 몰라도 "," 외에도 "," 이곳 "," 여기 "," 부터 "," 기점으로 "," 따라서 "," 할 생각이다 "," 하려고하다 "," 이리하여 "," 그리하여 "," 그렇게 함으로써 "," 하지만 "," 일때 "," 할때 "," 앞에서 "," 중에서 "," 보는데서 "," 으로써 "," 로써 "," 까지 "," 해야한다 "," 일것이다 "," 반드시 "," 할줄알다 "," 할수있다 "," 할수있어 "," 임에 틀림없다 ",
		" 한다면 "," 등 "," 등등 "," 제 "," 겨우 "," 단지 "," 다만 "," 할뿐 "," 딩동 "," 댕그 "," 대해서 "," 대하여 "," 대하면 "," 훨씬 "," 얼마나 "," 얼마만큼 "," 얼마큼 "," 남짓 "," 여 "," 얼마간 "," 약간 "," 다소 "," 좀 "," 조금 "," 다수 "," 몇 "," 얼마 "," 지만 "," 하물며 "," 또한 "," 그러나 "," 그렇지만 "," 하지만 "," 이외에도 "," 대해 말하자면 "," 뿐이다 "," 다음에 "," 반대로 "," 반대로 말하자면 ",
		" 이와 반대로 "," 바꾸어서 말하면 "," 바꾸어서 한다면 "," 만약 "," 그렇지않으면 "," 까악 "," 툭 "," 딱 "," 삐걱거리다 "," 보드득 "," 비걱거리다 "," 꽈당 "," 응당 "," 해야한다 "," 에 가서 "," 각 "," 각각 "," 여러분 "," 각종 "," 각자 "," 제각기 "," 하도록하다 "," 와 "," 과 "," 그러므로 "," 그래서 "," 고로 "," 한 까닭에 "," 하기 때문에 "," 거니와 "," 이지만 "," 대하여 "," 관하여 "," 관한 "," 과연 "," 실로 ",
		" 아니나다를가 "," 생각한대로 "," 진짜로 "," 한적이있다 "," 하곤>하였다 "," 하 "," 하하 "," 허허 "," 아하 "," 거바 "," 와 "," 오 "," 왜 "," 어째서 "," 무엇때문에 "," 어찌 "," 하겠는가 "," 무슨 "," 어디 "," 어느곳 "," 더군다나 "," 하>물며 "," 더욱이는 "," 어느때 "," 언제 "," 야 "," 이봐 "," 어이 "," 여보시오 "," 흐흐 "," 흥 "," 휴 "," 헉헉 "," 헐떡헐떡 "," 영차 "," 여차 "," 어기여차 "," 끙끙 "," 아>야 "," 앗 "," 아야 "," 콸콸 "," 졸졸 "," 좍좍 "," 뚝뚝 "," 주룩주룩 "," 솨 "," 우르르 "," 그래도 "," 또 "," 그리고 "," 바꾸어말하면 "," 바꾸어말하자면 "," 혹은 "," 혹시 "," 답다 "," 및 "," 그에 따르는 "," 때가 되어 "," 즉 "," 지든지 "," 설령 "," 가령 "," 하더라도 "," 할지라도 "," 일지라도 "," 지든지 "," 몇 "," 거의 "," 하마터면 "," 인젠 "," 이젠 "," 된바에야 "," 된이상 "," 만큼 "," 어찌됏든 "," 그위에 "," 게다가 "," 점에서 보아 "," 비추어 보아 "," 고려하면 "," 하게될것이다 "," 일것이다 "," 비교적 "," 좀 "," 보다더 "," 비하면 "," 시키다 "," 하게하다 "," 할만하다 "," 의해서 "," 연이서 "," 이어서 "," 잇따라 "," 뒤따라 "," 뒤이어 "," 결국 "," 의지하여 "," 기대여 "," >통하여 "," 자마자 "," 더욱더 "," 불구하고 "," 얼마든지 "," 마음대로 "," 주저하지 않고 "," 곧 "," 즉시 "," 바로 "," 당장 "," 하자마자 "," 밖에 안된다 "," 하면된다 "," 그래 "," 그렇지 "," 요컨대 "," 다시 말하자면 "," 바꿔 말하면 "," 즉 "," 구체적으로 "," 말하자면 "," 시작하여 "," 시초에 "," 이상 "," 허 "," 헉 "," 허걱 "," 바와같이 "," >해도좋다 "," 해도된다 "," 게다가 "," 더구나 "," 하물며 "," 와르르 "," 팍 "," 퍽 "," 펄렁 "," 동안 "," 이래 "," 하고있었다 "," 이었다 "," 에서 "," 로부터 "," 까지 "," 예하면 "," 했어요 "," 해요 "," 함께 "," 같이 "," 더불어 "," 마저 "," 마저도 "," 양자 "," 모두 "," 습니다 "," 가까스로 "," 하려고하다 "," 즈음하여 "," 다른 "," 다른 방면으로 "," 해봐요 "," 습니까 "," 했어요 "," 말할것도 없고 "," 무릎쓰고 "," 개의치않고 "," 하는것만 못하다 "," 하는것이 낫다 "," 매 "," 매번 "," 들 "," 모 "," 어느것 "," 어>느 "," 로써 "," 갖고말하자면 "," 어디 "," 어느쪽 "," 어느것 "," 어느해 "," 어느 년도 "," 라 해도 "," 언젠가 "," 어떤것 "," 어느것 "," 저기 "," 저쪽 "," 저것 "," 그때 "," 그럼 "," 그러면 "," 요만한걸 "," 그래 "," 그때 "," 저것만큼 "," 그저 "," 이르기까지 "," 할 줄 안다 "," 할 힘이 있다 "," 너 "," 너희 "," 당신 "," 어찌 "," 설마 "," 차>라리 "," 할지언정 "," 할지라도 "," 할망정 "," 할지언정 "," 구토하다 "," 게우다 "," 토하다 "," 메쓰겁다 "," 옆사람 "," 퉤 "," 쳇 "," 의거하여 "," 근거하여 "," 의해 "," >따라 "," 힘입어 "," 그 "," 다음 "," 버금 "," 두번째로 "," 기타 "," 첫번째로 "," 나머지는 "," 그중에서 "," 견지에서 "," 형식으로 쓰여 "," 입장에서 "," 위해서 "," 단지 "," 의해되다 "," 하도록시키다 "," 뿐만아니라 "," 반대로 "," 전후 "," 전자 "," 앞의것 "," 잠시 "," 잠깐 "," 하면서 "," 그렇지만 "," 다음에 "," 그러한즉 "," 그런즉 "," 남들 "," 아무거나 "," 어찌하든지 "," 같다 "," 비슷하다 "," 예컨대 "," 이럴정도로 "," 어떻게 "," 만약 "," 만일 "," 위에서 서술한바와같이 "," 인 듯하다 "," 하지 않는다면 "," 만약에 "," 무엇 "," 무슨 "," 어느 "," 어떤 "," 아래윗 "," 조차 "," 한데 "," 그럼에도 불구하고 "," 여전히 "," 심지어 "," 까지도 "," 조차도 "," 하지 않도록 "," 않기 위하>여 "," 때 "," 시각 "," 무렵 "," 시간 "," 동안 "," 어때 "," 어떠한 "," 하여금 "," 네 "," 예 "," 우선 "," 누구 "," 누가 알겠는가 "," 아무도 "," 줄은모른다 "," 줄은 몰랏다 "," 하는 김에 "," 겸사겸사 "," 하는바 "," 그런 까닭에 "," 한 이유는 "," 그러니 "," 그러니까 "," 때문에 "," 그 "," 너희 "," 그들 "," 너희들 "," 타인 "," 것 "," 것들 "," 너 "," 위하여 "," 공동으로 "," 동시에 "," 하기 위하여 "," 어찌하여 "," 무엇때문에 "," 붕붕 "," 윙윙 "," 나 "," 우리 "," 엉엉 "," 휘익 "," 윙윙 "," 오호 "," 아하 "," 어쨋든 "," 만 못하다 ","   하기보다는 "," 차라리 "," 하는 편이 낫다 "," 흐흐 "," 놀라다 "," 상대적으로 말하자면 "," 마치 "," 아니라면 "," 쉿 "," 그렇지 않으면 "," 그렇지 않다면 "," 안 그러면 "," 아니었다면 "," 하든지 "," 아니면 "," 이라면 "," 좋아 "," 알았어 "," 하는것도 "," 그만이다 "," 어쩔수 없다 "," 하나 "," 일 "," 일반적으로 "," 일단 "," 한켠으로는 "," 오자마자 "," 이렇게되면 "," 이와같다면 "," 전부 "," 한마디 "," 한항목 "," 근거로 "," 하기에 "," 아울러 "," 하지 않도록 "," 않기 위해서 "," 이르기>까지 "," 이 되다 "," 로 인하여 "," 까닭으로 "," 이유만으로 "," 이로 인하여 "," 그래서 "," 이 때문에 "," 그러므로 "," 그런 까닭에 "," 알 수 있다 "," 결론을 낼 수 있다 "," 으로 인하여 "," 있다 "," 어떤것 "," 관계가 있다 "," 관련이 있다 "," 연관되다 "," 어떤것들 "," 에 대해 "," 이리하여 "," 그리하여 "," 여부 "," 하기보다는 "," 하느니 "," 하면 할수록 "," 운운 "," 이러이러하다 "," 하구나 "," 하도다 "," 다시말하면 "," 다음으로 "," 에 있다 "," 에 달려 있다 "," 우리 "," 우리들 "," 오히려 "," 하기는한데 "," 어떻게 "," 어떻해 "," 어찌됏어 "," 어때 "," 어째서 "," 본대로 "," 자 "," 이 "," 이쪽 "," 여기 "," 이것 "," 이번 "," 이렇게말하자면 "," 이런 "," 이러한 "," 이와 같은 "," 요만큼 "," 요만한 것 "," 얼마 안 되는 것 "," 이만큼 "," 이 정도의 "," 이렇게 많은 것 "," 이와 같다 "," 이때 "," 이렇구나 "," 것과 같이 "," 끼익 "," 삐걱 "," 따위 "," >와 같은 사람들 "," 부류의 사람들 "," 왜냐하면 "," 중의하나 "," 오직 "," 오로지 "," 에 한하다 "," 하기만 하면 "," 도착하다 "," 까지 미치다 "," 도달하다 "," 정도에 이르다 "," 할 지경이다 "," 결과에 이르다 "," 관해서는 "," 여러분 "," 하고 있다 "," 한 후 "," 혼자 "," 자기 "," 자기집 "," 자신 "," 우에 종합한것과같이 "," 총적으로 보면 "," >총적으로 말하면 "," 총적으로 "," 대로 하다 "," 으로서 "," 참 "," 그만이다 "," 할 따름이다 "," 쿵 "," 탕탕 "," 쾅쾅 "," 둥둥 "," 봐 "," 봐라 "," 아이야 "," 아니 "," 와아 " ," 응 "," 아이 "," 참나 "," 년 "," 월 "," 일 "," 령 "," 영 "," 일 "," 이 "," 삼 "," 사 "," 오 "," 육 "," 륙 "," 칠 "," 팔 "," 구 "," 이천육 "," 이천칠 "," 이천팔 "," 이천구 "," 하나 "," 둘 "," 셋 "," 넷 "," 다섯 "," 여섯 "," 일곱 "," 여덟 "," 아홉 "," 령 "," 영 "};

		JSONObject megaTweetObj = new JSONObject();

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("hIBVsFDoROyMS9JFI8lQXKJBd")
				.setOAuthConsumerSecret("OOSFxdOc6OjJOqso197zmVcyMQa9r7fMzmoZ7btwvYRp29Ot4r")
				.setOAuthAccessToken("166158638-xkvE0HrIpStoAidpYeSWvcFsQubXpAw46dGWyCLz")
				.setOAuthAccessTokenSecret("CKdBaEjUQV0iPNoAaOCaChxuQpYAL7JOmGJUAxsd2U5Od");
		cb.setJSONStoreEnabled(true);

		TwitterFactory tf = new TwitterFactory(cb.build());

		twitter4j.Twitter twitter = tf.getInstance();

		int tweetCountInFile = 0;
		try {
			//TODO change query
			Query query = new Query(queryString);
			query.setCount(5000);
			query.setSince(fromDate);
			query.setUntil(toDate);
			query.resultType(ResultType.recent);
			query.setLang(tweetLang);
			Query tempQuery = query;
			QueryResult result;
			String filename1 = "C:\\Users\\presh\\Desktop\\Prashanth\\Actual_Tweet\\" + topic + "\\" + topic + "_"
					+ timestamp;
			String filename2 = "C:\\Users\\presh\\Desktop\\Prashanth\\My_Tweet\\" + topic + "\\" + topic + "_"
					+ timestamp;
			String emoticon_regex = "((?::|;|=)(?:-)?(?:\\)|D|P))";
			Pattern emoticonPattern = Pattern.compile(emoticon_regex);
			String url_regex = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
			Pattern urlPattern = Pattern.compile(url_regex);  

			// String emoji_regex =
			// "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
			String specialRegex = "[^A-Za-z0-9À-ÿ\u00C0-\u017F\u3130-\u318F\uAC00-\uD7AF ]";
			// String emo_regex = "u'(' u'\ud83c[\udf00-\udfff]|'
			// u'\ud83d[\udc00-\ude4f\ude80-\udeff]|'
			// u'[\u2600-\u26FF\u2700-\u27BF])+'";

			FileOutputStream fout1 = new FileOutputStream(filename1);
			FileOutputStream fout2 = new FileOutputStream(filename2);
			String jsonstring = "";
			String myJsonstring = "";

			String onlyText;
			String tweetDate;
			GeoLocation geoLoc;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:00:00Z");
//			 int loopCounter = 100, i = 0, 
			int tweetCounterMax = 5000;
//			 while (i < loopCounter) {
//			 query = tempQuery;
			do {
//				query.setMaxId(777163440892710911);
				if (tweetMaxId != -1) {
					System.out.println("tweetMaxId: " + tweetMaxId );
					query.setMaxId(tweetMaxId - 1);
				}
//				System.out.println(query.getMaxId());
				result = twitter.search(query);

				List<Status> tweets = result.getTweets();
				// tweetMaxId = tweets.get(0).getId();
//				int ii=0;
				for (Status tweet : tweets) {
//					if(ii++ == 3) break;
					List<String> emojiList = new ArrayList<String>();
					List<String> emoticonList = new ArrayList<String>();
					List<String> kaomojiList = new ArrayList<String>();

					JSONObject obj = new JSONObject();
					JSONArray tweetHashtags = new JSONArray();
					JSONArray tweetMentions = new JSONArray();
					JSONArray tweetURLs = new JSONArray();
					JSONArray tweetEmoticons = new JSONArray();

					if (tweetMaxId == -1 || tweet.getId() < tweetMaxId) {
						tweetMaxId = tweet.getId();
					}

//					System.out.println("date:" + tweet.getCreatedAt().toString());
					tweetDate = df.format(tweet.getCreatedAt());
					tweetDate = tweetDate.replaceAll(" ", "T");
//					System.out.println("tweet_date:" + tweetDate);

					geoLoc = tweet.getGeoLocation();
					String tweetLoc = "";
					if (geoLoc != null) {
						double geoLat = geoLoc.getLatitude();
						double geoLong = geoLoc.getLongitude();
						tweetLoc = geoLat + "," + geoLong;
//						System.out.println("tweet_loc:" + tweetLoc);
					}

					onlyText = tweet.getText();
//					onlyText = onlyText+"QQ OO （●＾o＾●） 😛😛❤️❤️ :):D :D QQ :) :) OO （●＾o＾●） （●＾o＾●） #housemusic :‑###.. :L :) :-) https://t.co/813uKKIRrw";
//					System.out.println("onlyText:" + onlyText);
					for (HashtagEntity hashTags : tweet.getHashtagEntities()) {
						onlyText = onlyText.replaceAll("#" + hashTags.getText(), " ");
						// tweetHashtags.append(hashTags.getText());
						tweetHashtags.put(hashTags.getText());
					}
//					System.out.println("NewonlyText wo HT:" + onlyText);


					for (URLEntity urle : tweet.getURLEntities()) {
						onlyText = onlyText.replaceAll(urle.getText(), " ");
						tweetURLs.put(urle.getText());
					}
					Matcher urlMatcher = urlPattern.matcher(onlyText);
					while (urlMatcher.find()) {
						tweetURLs.put(urlMatcher.group(0).toString());//TODO is below line right? doing in a different way for the other one
					}
					onlyText = onlyText.replaceAll(url_regex, " ");

					
//					System.out.println("NewonlyText wo URLs:" + onlyText);

					for (UserMentionEntity mentions : tweet.getUserMentionEntities()) {
						onlyText = onlyText.replaceAll("@" + mentions.getText(), " ");
						tweetMentions.put(mentions.getText());
					}
//					System.out.println("NewonlyText wo mentions:" + onlyText);

					Matcher emoticonMatcher = emoticonPattern.matcher(onlyText);
					while (emoticonMatcher.find()) {
						//TODO why arent we replacing from the string???
						tweetEmoticons.put(emoticonMatcher.group(0));
					}
					onlyText = onlyText.replaceAll(emoticon_regex, " ");
//					System.out.println("NewonlyText wo emoticons:" + onlyText);
					
					for (String emoticon : emoticonStrings) {
						if (onlyText.contains(emoticon)) {
							onlyText = onlyText.replace(emoticon, " ");
//							emoticonList.add(emoticon);
							tweetEmoticons.put(emoticon);
						}
					}

//					System.out.println("NewonlyText wo emoticons2:" + onlyText);


					emojiList = getEmojilist(onlyText);
					for (String emoji : emojiList) {
						onlyText = onlyText.replaceFirst(emoji, " ");
						tweetEmoticons.put(emoji);
					}
//					if (!(emojiList.isEmpty())) {
//						tweetEmoticons.put(emojiList);
//					}
//					System.out.println("NewonlyText wo emoji:" + onlyText);

					for (String kaomoji : kaomojiStrings) {
						if (onlyText.contains(kaomoji)) {
							onlyText = onlyText.replace(kaomoji, " ");
//							kaomojiList.add(kaomoji);
							tweetEmoticons.put(kaomoji);
						}
					}
//					if (!(kaomojiList.isEmpty())) {
//						tweetEmoticons.put(kaomojiList);
//					}
					
//					System.out.println("NewonlyText wo kaomoji:" + onlyText);


					String[] stopWordString;
					switch(tweetLang){
					case "en": 	stopWordString = en_stopwords;
						break;
					case "es":	stopWordString = es_stopwords;
						break;
					case "tr":	stopWordString = tr_stopwords;
						break;
					case "ko":	stopWordString = ko_stopwords;
						break;
					default: stopWordString = en_stopwords;
						break;
					}
					onlyText = " "+onlyText+" ";
					for (String stopwords : stopWordString) {
						if (tweetLang == "en" || tweetLang == "es"){
							if (onlyText.toLowerCase().contains(stopwords)) {
								onlyText = onlyText.replaceAll("(?i)"+stopwords, " ");
							}
						}
						else{
							if (onlyText.contains(stopwords)) {
								onlyText = onlyText.replace(stopwords, " ");
							}
						}
					}
					
					onlyText = onlyText.substring(1, onlyText.length()-1);
//					System.out.println("NewonlyText wo stopwords:" + onlyText);

//					if(tweetLang != "ko"){
						onlyText = onlyText.replaceAll(specialRegex, " ");
//					}
//					System.out.println("NewonlyText wo speclchars:" + onlyText);

					 onlyText = onlyText.replaceAll("\\s\\s+", " ");
//					 System.out.println("NewonlyText wo space:" + onlyText);
					
					try {
						obj.put("tweet_id", tweet.getId());
						obj.put("topic", topic);
						obj.put("tweet_text", tweet.getText());
						obj.put("text_lang", tweetLang);
						obj.put("text_" + tweetLang, onlyText);
						obj.put("hashtags", tweetHashtags);
						obj.put("mentions", tweetMentions);
						obj.put("tweet_urls", tweetURLs);
						obj.put("tweet_emoticons", tweetEmoticons);
						obj.put("tweet_date", tweetDate);
						obj.put("tweet_loc", tweetLoc);
					} catch (JSONException e) { 
						e.printStackTrace();
					}


					jsonstring = jsonstring + TwitterObjectFactory.getRawJSON(tweet) + "\n";
					myJsonstring = myJsonstring + obj.toString() + "\n";

					// jsonstring = jsonstring + megaTweetObj + "\n";
					tweetCountInFile++;
//					break;
					if (tweetCountInFile >= tweetCounterMax)
						break;
				}
				if (tweetCountInFile >= tweetCounterMax)
					break;
//				break;
//			i++; }
			} while ((query = result.nextQuery()) != null);
			jsonstring = jsonstring.trim();
			byte b[] = jsonstring.getBytes();
			fout1.write(b);
			fout1.close();

			myJsonstring = myJsonstring.trim();
			b = myJsonstring.getBytes();
			fout2.write(b);
			fout2.close();
			// i++;
			// System.out.println(i + "\n");
			// }

			// tweetCountInFile = 0;
			new File(filename1).renameTo(new File(filename1 + "_" + tweetLang + "_" + tweetMaxId + "_"
					+ tweetCountInFile + "_" + fromDate + "-" + toDate + ".json"));
			new File(filename2).renameTo(new File(filename2 + "_" + tweetLang + "_" + tweetMaxId + "_"
					+ tweetCountInFile + "_" + fromDate + "-" + toDate + ".json"));
			System.out.println(tweetCountInFile);
			System.out.println(tweetMaxId);
			Toolkit.getDefaultToolkit().beep();

		}catch(

	TwitterException te)
	{
		te.printStackTrace();
		System.out.println("Failed to search tweets: " + te.getMessage());
		System.exit(-1);
	}catch(
	FileNotFoundException e)
	{
		e.printStackTrace();
	}catch(
	IOException e)
	{
		e.printStackTrace();
	}

	}

	private static List<String> getEmojilist(String emojiStrippedText) {
		List<String> emojis = new ArrayList<String>();
		char chars[] = emojiStrippedText.toCharArray();
		int index;
		char ch1;
		char ch2;
		index = 0;
		// remove uncategorized emoji
		while (index < chars.length - 1) { // -1 because we're looking for
											// two-char-long things
			// System.out.println(index + " of "+ chars.length + " or "+
			// emojiStrippedText.length());
			ch1 = chars[index];
			if ((int) ch1 == 0xD83C) {
				ch2 = chars[index + 1];
				if ((int) ch2 >= 0xDF00 && (int) ch2 <= 0xDFFF) {
					// chars[index] = ' ';
					// chars[index + 1] = ' ';
					emojis.add(emojiStrippedText.substring(index, index + 2));
					index += 2;
					continue;
				}
			} else if ((int) ch1 == 0xD83D) {
				ch2 = chars[index + 1];
				if ((int) ch2 >= 0xDC00 && (int) ch2 <= 0xDDFF) {
					// System.out.println("Found emoji at index " + index);
					// chars[index] = ' ';
					// chars[index + 1] = ' ';
					emojis.add(emojiStrippedText.substring(index, index + 2));
					index += 2;
					continue;
				}
			}
			++index;
		}
		index = 0;
		// remove emoticons
		while (index < chars.length - 1) { // -1 because we're looking for
											// two-char-long things
			ch1 = chars[index];
			if ((int) ch1 == 0xD83D) {
				ch2 = chars[index + 1];
				if ((int) ch2 >= 0xDE01 && (int) ch2 <= 0xDE4F) {
					// System.out.println("Found emoji at index " + index);
					// chars[index] = ' ';
					// chars[index + 1] = ' ';
					// emojis.add(emojiStrippedText.substring(index, 2));
					emojis.add(emojiStrippedText.substring(index, index + 2));
					index += 2;
					continue;
				}
			}
			++index;
		}

		index = 0;
		// remove dingbats
		while (index < chars.length - 1) {
			ch1 = chars[index];
			if ((int) ch1 >= 0x2702 && (int) ch1 <= 0x27B0) {
				// chars[index] = ' ';
				emojis.add(emojiStrippedText.substring(index, index + 1));
				index += 1;
				continue;
			}
			++index;
		}
		index = 0;
		// remove transport and map
		while (index < chars.length - 1) { // -1 because we're looking for
											// two-char-long things
			ch1 = chars[index];
			if ((int) ch1 == 0xD83D) {
				ch2 = chars[index + 1];
				if ((int) ch2 >= 0xDE80 && (int) ch2 <= 0xDEC0) {
					// System.out.println("Found emoji at index " + index);
					// chars[index] = ' ';
					// chars[index + 1] = ' ';
					emojis.add(emojiStrippedText.substring(index, index + 2));
					index += 2;
					continue;
				}
			}
			++index;
		}
		index = 0;
		// remove enclosed
		while (index < chars.length - 1) { // -1 because we're looking for
											// two-char-long things
			ch1 = chars[index];
			if ((int) ch1 == 0xD83C) {
				ch2 = chars[index + 1];
				if ((int) ch2 >= 0xDD70 && (int) ch2 <= 0xDE51) {
					// System.out.println("Found emoji at index " + index);
					// chars[index] = ' ';
					// chars[index + 1] = ' ';
					emojis.add(emojiStrippedText.substring(index, index + 2));
					index += 2;
					continue;
				}
			}
			++index;
		}

		index = 0;
		// remove additional transport and map
		while (index < chars.length - 1) { // -1 because we're looking for
											// two-char-long things
			ch1 = chars[index];
			if ((int) ch1 == 0xD83D) {
				ch2 = chars[index + 1];
				if ((int) ch2 >= 0xDE81 && (int) ch2 <= 0xDEC5) {
					// System.out.println("Found emoji at index " + index);
					// chars[index] = ' ';
					// chars[index + 1] = ' ';
					emojis.add(emojiStrippedText.substring(index, index + 2));
					index += 2;
					continue;
				}
			}
			++index;
		}

		index = 0;
		// remove additional symbols
		while (index < chars.length - 1) { // -1 because we're looking for
											// two-char-long things
			ch1 = chars[index];
			if ((int) ch1 == 0xD83C) {
				ch2 = chars[index + 1];
				if ((int) ch2 >= 0xDF0D && (int) ch2 <= 0xDFFF) {
					// System.out.println("Found emoji at index " + index);
					/// chars[index] = ' ';
					// chars[index + 1] = ' ';
					emojis.add(emojiStrippedText.substring(index, index + 2));
					index += 2;
					continue;
				}
			} else if ((int) ch1 == 0xD83D) {
				ch2 = chars[index + 1];
				if ((int) ch2 >= 0xDC00 && (int) ch2 <= 0xDD67) {
					// System.out.println("Found emoji at index " + index);
					// chars[index] = ' ';
					// chars[index + 1] = ' ';
					emojis.add(emojiStrippedText.substring(index, index + 2));
					index += 2;
					continue;
				}
			}
			++index;
		}

		return emojis;
	}

}
