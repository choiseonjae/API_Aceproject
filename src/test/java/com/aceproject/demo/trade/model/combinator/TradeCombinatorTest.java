package com.aceproject.demo.trade.model.combinator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.aceproject.demo.common.model.Player;
import com.aceproject.demo.trade.TradeUtils;
import com.aceproject.demo.trade.model.PlayerType;
import com.aceproject.demo.trade.model.TradeOption;
import com.aceproject.demo.trade.model.combinator.playertype.AceProbability;
import com.aceproject.demo.trade.model.combinator.playertype.MonsterProbability;
import com.aceproject.demo.trade.model.combinator.playertype.NormalProbability;
import com.aceproject.demo.trade.model.combinator.playertype.SpecialProbability;
import com.aceproject.demo.trade.model.combinator.playertype.TypeProbability;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TradeCombinatorTest {

//	@Autowired
//	List<TypeProbability> typeProbs;
	
	private static final List<TypeProbability> typeProbs = Arrays.asList(
			new AceProbability()
			, new MonsterProbability()
			, new SpecialProbability()
			, new NormalProbability()
			
	);
	
	@Test
	public void combinateTest() {
		System.out.println(PlayerType.ACE.compareTo(PlayerType.MONSTER));
		System.out.println(PlayerType.ACE.compareTo(PlayerType.SPECIAL));
		System.out.println(PlayerType.ACE.compareTo(PlayerType.NORMAL));
		System.out.println(PlayerType.MONSTER.compareTo(PlayerType.SPECIAL));
		System.out.println(PlayerType.MONSTER.compareTo(PlayerType.NORMAL));
		System.out.println(PlayerType.SPECIAL.compareTo(PlayerType.NORMAL));

		System.out.println("ACE 100%");
		playerTypeTest(new ArrayList<>(Arrays.asList(PlayerType.ACE, PlayerType.ACE, PlayerType.ACE, PlayerType.ACE)));
		
		System.out.println("MONSTER 60%");
		playerTypeTest(new ArrayList<>(Arrays.asList(PlayerType.MONSTER, PlayerType.MONSTER, PlayerType.MONSTER, PlayerType.MONSTER)));
		
		System.out.println("MONSTER 55%");
		playerTypeTest(new ArrayList<>(Arrays.asList(PlayerType.MONSTER, PlayerType.MONSTER, PlayerType.MONSTER)));
		
		System.out.println("MONSTER 50%");
		playerTypeTest(new ArrayList<>(Arrays.asList(PlayerType.MONSTER, PlayerType.MONSTER)));
		
		System.out.println("SPECIAL 70%");
		playerTypeTest(new ArrayList<>(Arrays.asList(PlayerType.SPECIAL, PlayerType.SPECIAL, PlayerType.SPECIAL, PlayerType.SPECIAL)));
		
		System.out.println("SPECIAL 65%");
		playerTypeTest(new ArrayList<>(Arrays.asList(PlayerType.SPECIAL, PlayerType.SPECIAL, PlayerType.SPECIAL)));
		
		System.out.println("SPECIAL 60%");
		playerTypeTest(new ArrayList<>(Arrays.asList(PlayerType.SPECIAL, PlayerType.SPECIAL)));
		
		System.out.println("ACE 제외 33%");
		playerTypeTest(new ArrayList<>(Arrays.asList(PlayerType.ACE, PlayerType.MONSTER, PlayerType.SPECIAL, PlayerType.NORMAL)));
	}

	public void playerTypeTest(List<PlayerType> pts) {
		
		int cnt = 30000;
		
		Map<PlayerType, Integer> map = new HashMap<>();
		map.put(PlayerType.ACE, 0);
		map.put(PlayerType.MONSTER, 0);
		map.put(PlayerType.SPECIAL, 0);
		map.put(PlayerType.NORMAL, 0);
		
		Player p1 = new Player();
		p1.setPlayerType(PlayerType.ACE);
		
		Player p2 = new Player();
		p2.setPlayerType(PlayerType.MONSTER);
		
		Player p3 = new Player();
		p3.setPlayerType(PlayerType.SPECIAL);
		
		Player p4 = new Player();
		p4.setPlayerType(PlayerType.NORMAL);      
		
		List<Player> players = new ArrayList<Player>(Arrays.asList(p1, p2, p3, p4));
		
		Set<Integer> years = null;
		TradeOption option = new TradeOption(years, false, false);
		
		for(int i =0; i < cnt; i++) {
			PlayerType type = null;
			for(TypeProbability tp : typeProbs) {
				type = tp.getPlayerType(TradeUtils.getCountMap(players, TradeUtils.playerTypeConverter), option);
				if(type != null) break;
			}	
			map.put(type, map.get(type) + 1);
			//players.stream().forEach(p -> map.put(p.getPlayerType(), map.get(p.getPlayerType()) + 1));
		}
		
		for(PlayerType p : map.keySet())
			System.out.print(p + " - " + (int)(((double)map.get(p) / cnt) * 100) + "%" + "\t");
		
		System.out.println();
		System.out.println("##########################################################");
		System.out.println();
	}
	
	@Test
	public void tradeFilterTest() {
		
		Map<PlayerType, Integer> map = new HashMap<>();
		
		List<PlayerType> types = Arrays.asList(PlayerType.ACE, PlayerType.MONSTER, PlayerType.SPECIAL, PlayerType.NORMAL);
		
		List<Player> players = getPlayers(types);
		
		TradeOption option = new TradeOption(null, false, false);
		
		TradeCombinator tradeComb = new TradeCombinator();
		Predicate<Player> filter = tradeComb.tradeFilter(players, option);
		
		IntStream.range(0, 100).forEach(i ->{
			
		});
	}
	
	private List<Player> getPlayers(List<PlayerType> types){
		return types.stream().map(t -> new Player(t)).collect(Collectors.toList());
	}
	
	//@Test
	public void costTest() {
//		List<Integer> costs = new ArrayList<Integer>();
//		TradeCombinator ptc = new TradeCombinator();
//		costs.add(7);
//		costs.add(7);
//		costs.add(7);
//		
//		ptc.costFilter(costs, false);
//		ptc.costFilter(costs, true);
		
		
	}

}
