package com.thinkerwolf.frameworkstudy.beans;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Java Bean PropertyEditorSupport
 * 
 * @author wukai
 *
 */
public class CustomDatePropertyEditor extends PropertyEditorSupport {

	private String datePattern;

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		DateFormat dateFormat = new SimpleDateFormat(datePattern);
		try {
			setValue(dateFormat.parse(text));
		} catch (ParseException e) {
			throw new java.lang.IllegalArgumentException(text, e);
		}
	}

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}

}
