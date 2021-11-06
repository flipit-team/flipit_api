package com.flip.service.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author Charles on 21/06/2021
 */
@Component
public class TextUtil {

	public String toCamelCase(String input) {
		if (StringUtils.isBlank(input))
			return input;
		
		return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
	}
}