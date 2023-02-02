package com.lineate.buscompany.Handler;

import com.lineate.buscompany.interactiveMap.*;
import com.lineate.buscompany.model.CommonMarker;
import com.lineate.buscompany.services.*;
import de.fhpotsdam.unfolding.marker.Marker;


public class ConcreteHandlerMenu implements HandlerInterface {

	@Override
	public boolean markerCheckClick(float mouseX, float mouseY) {
		CommonMarker lastClicked;
		for (Marker mark : MenuService.getInstance().getMenuMarkers()) {
			lastClicked = (CommonMarker) mark;
			if (mark.isInside(MarkerService.getInstance().getConsole(), mouseX, mouseY)) {
				checkEventsOnMap(mark);
				checkEventsWithInterface(mark, lastClicked);
				return true;
			}
		}
		return false;
	}

	private void checkEventsOnMap(Marker mark) {
		if (mark.equals(MenuService.getInstance().getMenuMarkerByIndex(0))) {
			CityService.getInstance().hideCityMarkers(mark);
		}

		if (mark.equals(MenuService.getInstance().getMenuMarkerByIndex(1))) {
			EarthquakeService.hideQuakeMarkers(mark);
		}

		if (mark.equals(MenuService.getInstance().getMenuMarkerByIndex(2))) {
			AirportService.getInstance().hideAirMarkers(mark);
		}
	}

	private void checkEventsWithInterface(Marker mark, CommonMarker lastClicked) {
		if (MenuService.getInstance().getIndexOfMenu(mark) == 2) {
			TextFieldTest t = Service.getTextField();
			boolean fielHidden = showHideField(lastClicked, Service.isHiddenField(), t);
			Service.setFieldHidden(fielHidden);

		}

		if (MenuService.getInstance().getIndexOfMenu(mark) == 3) {
			boolean keyIsHidden = showHideInterface(lastClicked, Service.isHiddenKey());
			Service.setKeyHidden(keyIsHidden);
		}

		if (MenuService.getInstance().getIndexOfMenu(mark) == 4) {
			boolean infoIsHidden = showHideInterface(lastClicked, MenuService.getInstance().isClickenSixMarket());
			Service.setInfoHidden(infoIsHidden);

		}
		if (MenuService.getInstance().getIndexOfMenu(mark) == 5) {
			TextFieldRoute field = Service.getTextFieldRoute();
			boolean secondFieldHidden = showHideField(lastClicked, Service.isHiddenSecondField(), field);
			Service.setSecondFieldHidden(secondFieldHidden);
		}
		if (MenuService.getInstance().getIndexOfMenu(mark) == 6) {
			TextFieldEarthQuake field = Service.getEarthQuakes();
			boolean secondFieldHidden = showHideField(lastClicked, Service.isHiddenQuakeField(), field);
			Service.setQuakeFieldHidden(secondFieldHidden);
		}
	}

	private boolean showHideField(CommonMarker lastClicked, boolean isHidden, ITextField t) {
		if (isHidden) {
			t.showTextPanel();
			isHidden = false;
			lastClicked.setClicked(true);
		} else {
			t.hideTextPanel();
			isHidden = true;
			lastClicked.setClicked(false);
		}
		return isHidden;
	}

	private boolean showHideInterface(CommonMarker lastClicked, boolean isHidden) {
		if (isHidden) {
			isHidden = false;
			lastClicked.setClicked(false);
		} else {
			isHidden = true;
			lastClicked.setClicked(true);
		}
		return isHidden;
	}

}
