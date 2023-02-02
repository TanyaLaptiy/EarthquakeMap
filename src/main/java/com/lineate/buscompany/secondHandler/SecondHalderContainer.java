package com.lineate.buscompany.secondHandler;

import java.util.ArrayList;
import java.util.List;

public class SecondHalderContainer implements HandlerInterface {
	private List<HandlerInterface> handlerList = new ArrayList<>();

	@Override
	public boolean markerSetClick() {

		for (HandlerInterface handler : handlerList) {
			if (handler.markerSetClick()) {
				return true;
			}
		}
		return false;

	}

	public void addHandler(HandlerInterface handler) {
		handlerList.add(handler);
	}

}
