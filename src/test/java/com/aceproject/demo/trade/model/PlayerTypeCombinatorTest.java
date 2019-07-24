package com.aceproject.demo.trade.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PlayerTypeCombinatorTest {
	
	@Test
	public void combinateTest() {
	
		
		List<PlayerType> pts = new ArrayList<>();
		
		pts.add(PlayerType.ACE);
		pts.add(PlayerType.ACE);
		pts.add(PlayerType.ACE);
		pts.add(PlayerType.ACE);
		System.out.println("ACE 100%");
		tradeRandTest(pts);
		
		pts.clear();
		pts.add(PlayerType.MONSTER);
		pts.add(PlayerType.MONSTER);
		pts.add(PlayerType.MONSTER);
		pts.add(PlayerType.MONSTER);
		System.out.println("MONSTER 60%");
		tradeRandTest(pts);
		
		pts.clear();
		pts.add(PlayerType.MONSTER);
		pts.add(PlayerType.MONSTER);
		pts.add(PlayerType.MONSTER);
		System.out.println("MONSTER 55%");
		tradeRandTest(pts);
		
		pts.clear();
		pts.add(PlayerType.MONSTER);
		pts.add(PlayerType.MONSTER);
		System.out.println("MONSTER 50%");
		tradeRandTest(pts);
		
		pts.clear();
		pts.add(PlayerType.SPECIAL);
		pts.add(PlayerType.SPECIAL);
		pts.add(PlayerType.SPECIAL);
		pts.add(PlayerType.SPECIAL);
		System.out.println("SPECIAL 70%");
		tradeRandTest(pts);
		
		pts.clear();
		pts.add(PlayerType.SPECIAL);
		pts.add(PlayerType.SPECIAL);
		pts.add(PlayerType.SPECIAL);
		System.out.println("SPECIAL 65%");
		tradeRandTest(pts);
		
		pts.clear();
		pts.add(PlayerType.SPECIAL);
		pts.add(PlayerType.SPECIAL);
		System.out.println("SPECIAL 60%");
		tradeRandTest(pts);		
		
		pts.clear();
		pts.add(PlayerType.ACE);
		pts.add(PlayerType.MONSTER);
		pts.add(PlayerType.SPECIAL);
		pts.add(PlayerType.NORMAL);
		System.out.println("ACE 제외 33%");
		tradeRandTest(pts);
	}
	
	public void tradeRandTest(List<PlayerType> pts) {
		
		int cnt = 10000;
		
		PlayerTypeCombinator ptc = new PlayerTypeCombinator();
		
		Map<PlayerType, Integer> map = new HashMap<>();
		map.put(PlayerType.ACE, 0);
		map.put(PlayerType.MONSTER, 0);
		map.put(PlayerType.SPECIAL, 0);
		map.put(PlayerType.NORMAL, 0);
		
		for(int i =0; i < cnt; i++) {
			PlayerType type = ptc.combinate(pts, false); 
			map.put(type, map.get(type) + 1);
		}
		
		for(PlayerType p : map.keySet())
			System.out.print(p + " - " + (int)(((double)map.get(p) / cnt) * 100) + "%" + "\t");
		
		System.out.println();
		System.out.println("##########################################################");
		System.out.println();
	}
	
	
}
