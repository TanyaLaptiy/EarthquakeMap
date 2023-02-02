package com.lineate.buscompany.model;

public class ButtonModel {
	public static String url;
	public static boolean isClicked=false;
	public static void setUrl(String currenURL) {
		url = currenURL;
	}

	public static String getUrl() {
		return url;
	}
	public static void setClick(boolean currenClicked) {
		isClicked = currenClicked;
	}

	public static boolean getClick() {
		return isClicked;
	}

}
