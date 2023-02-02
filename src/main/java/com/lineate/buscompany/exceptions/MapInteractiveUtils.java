package com.lineate.buscompany.exceptions;

public class MapInteractiveUtils {

	public static void checkProperty(Object name) throws ServerException {
		if (name == null)
			throw new ServerException(ErrorCode.WRONG_PROPERTIES_KEY);
	}

	public static void checkProperty(String name) throws ServerException {
		if (name == null || name.isEmpty())
			throw new ServerException(ErrorCode.WRONG_PROPERTIES_KEY);
	}
}
