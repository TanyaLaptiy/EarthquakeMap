package com.lineate.buscompany.secondHandler;


import com.lineate.buscompany.services.MarkerService;
import com.lineate.buscompany.services.MenuService;

public class ConcreteHandlerSecond implements HandlerInterface {

	@Override
	public boolean markerSetClick() {
		if (MarkerService.getInstance().getLastClicked().equals(MenuService.getInstance().getMenuMarkerByIndex(1))) {
			MenuService.secondIsUnclicked();
			return true;
		}
		return false;
	}

}
