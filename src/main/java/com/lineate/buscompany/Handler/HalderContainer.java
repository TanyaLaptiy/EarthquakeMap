package com.lineate.buscompany.Handler;

import java.util.ArrayList;
import java.util.List;

public class HalderContainer implements HandlerInterface {
	private List<HandlerInterface> handlerList = new ArrayList<>();

	@Override
	public boolean markerCheckClick(float mouseX, float mouseY) {

		for (HandlerInterface handler : handlerList) {
			if (handler.markerCheckClick(mouseX, mouseY)) {
				return true;
			}
		}
		return false;

	}

	public void addHandler(HandlerInterface handler) {
		handlerList.add(handler);
	}

}
