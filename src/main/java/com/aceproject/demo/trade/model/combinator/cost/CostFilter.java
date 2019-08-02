package com.aceproject.demo.trade.model.combinator.cost;

import com.aceproject.demo.trade.TradeUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class CostFilter {
	int cnt;
	int percent;

	boolean isCostUp(int cnt) {
		return this.cnt == cnt && TradeUtils.isSuccess(percent);
	}
}
