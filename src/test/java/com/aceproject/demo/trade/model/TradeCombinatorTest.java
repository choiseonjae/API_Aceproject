//package com.aceproject.demo.trade.model;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.function.Predicate;
//
//import org.junit.Test;
//
//import com.aceproject.demo.common.model.Player;
//
////@RunWith(SpringRunner.class)
////@SpringBootTest
////@Transactional
//public class TradeCombinatorTest {
//	
//	@Test
//	public void combinateTest() {
//		
//		System.out.println("ACE 100%");
//		playerTypeTest(new ArrayList<>(Arrays.asList(PlayerType.ACE, PlayerType.ACE, PlayerType.ACE, PlayerType.ACE)));
//		
//		System.out.println("MONSTER 60%");
//		playerTypeTest(new ArrayList<>(Arrays.asList(PlayerType.MONSTER, PlayerType.MONSTER, PlayerType.MONSTER, PlayerType.MONSTER)));
//		
//		System.out.println("MONSTER 55%");
//		playerTypeTest(new ArrayList<>(Arrays.asList(PlayerType.MONSTER, PlayerType.MONSTER, PlayerType.MONSTER)));
//		
//		System.out.println("MONSTER 50%");
//		playerTypeTest(new ArrayList<>(Arrays.asList(PlayerType.MONSTER, PlayerType.MONSTER)));
//		
//		System.out.println("SPECIAL 70%");
//		playerTypeTest(new ArrayList<>(Arrays.asList(PlayerType.SPECIAL, PlayerType.SPECIAL, PlayerType.SPECIAL, PlayerType.SPECIAL)));
//		
//		System.out.println("SPECIAL 65%");
//		playerTypeTest(new ArrayList<>(Arrays.asList(PlayerType.SPECIAL, PlayerType.SPECIAL, PlayerType.SPECIAL)));
//		
//		System.out.println("SPECIAL 60%");
//		playerTypeTest(new ArrayList<>(Arrays.asList(PlayerType.SPECIAL, PlayerType.SPECIAL)));
//		
//		System.out.println("ACE 제외 33%");
//		playerTypeTest(new ArrayList<>(Arrays.asList(PlayerType.ACE, PlayerType.MONSTER, PlayerType.SPECIAL, PlayerType.NORMAL)));
//	}
//	
//	
//	
//	public void playerTypeTest(List<PlayerType> pts) {
//		
//		int cnt = 30000;
//		
//		TradeCombinator ptc = new TradeCombinator();
//		
//		Map<PlayerType, Integer> map = new HashMap<>();
//		map.put(PlayerType.ACE, 0);
//		map.put(PlayerType.MONSTER, 0);
//		map.put(PlayerType.SPECIAL, 0);
//		map.put(PlayerType.NORMAL, 0);
//		
//		Player p1 = new Player();
//		p1.setPlayerType(PlayerType.ACE.intValue());
//		
//		Player p2 = new Player();
//		p2.setPlayerType(PlayerType.MONSTER.intValue());
//		
//		Player p3 = new Player();
//		p3.setPlayerType(PlayerType.SPECIAL.intValue());
//		
//		Player p4 = new Player();
//		p4.setPlayerType(PlayerType.NORMAL.intValue());
//		
//		List<Player> ptList = new ArrayList<Player>(Arrays.asList(p1, p2, p3, p4));
//		
//		
//		
//		for(int i =0; i < cnt; i++) {
//			Predicate<Player> filter = ptc.playerTypeFilter(false); 
//			ptList.stream().filter(filter).forEach(p -> map.put(p.getPlayerType(), map.get(p.getPlayerType()) + 1));
//		}
//		
//		for(PlayerType p : map.keySet())
//			System.out.print(p + " - " + (int)(((double)map.get(p) / cnt) * 100) + "%" + "\t");
//		
//		System.out.println();
//		System.out.println("##########################################################");
//		System.out.println();
//	}
//	
//	//@Test
//	public void costTest() {
//		TradeCombinator ptc = new TradeCombinator();
//		List<Integer> costs = new ArrayList<Integer>();
//		costs.add(7);
//		costs.add(7);
//		costs.add(7);
//		
//		ptc.costFilter(costs, false);
//		ptc.costFilter(costs, true);
//		
//		
//	}
//	
//	public void testPlayer() {
//		//List<Player>
//	}
//
//	
//}
