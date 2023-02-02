package com.lineate.buscompany.secondHandler;


import com.lineate.buscompany.services.MarkerService;
import com.lineate.buscompany.services.MenuService;

public class ConcreteHandlerThird implements HandlerInterface {

	@Override
	public boolean markerSetClick() {
		if (MarkerService.getInstance().getLastClicked().equals(MenuService.getInstance().getMenuMarkerByIndex(2))) {
			MenuService.thirdIsUnclicked();
			return true;
		}
		return false;
	}

}
