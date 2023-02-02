package com.lineate.buscompany.secondHandler;

import com.lineate.buscompany.services.MarkerService;
import com.lineate.buscompany.services.MenuService;


public class ConcreteHandlerFirst implements HandlerInterface {

	@Override
	public boolean markerSetClick() {
		if (MarkerService.getInstance().getLastClicked().equals(MenuService.getInstance().getMenuMarkerByIndex(0))) {
			MenuService.firstIsUnclicked();
			
			return true;
		}
		return false;
	}
}
