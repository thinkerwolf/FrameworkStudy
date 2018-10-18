package com.thinkerwolf.frameworkstudy.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class PersionValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == Person.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
		Person person = (Person) target;
		if (person.getAge() < 0) {
			errors.rejectValue("age", "negativevalue");
		} else if (person.getAge() > 110) {
			errors.rejectValue("age", "too.darn.old");
		}
	}

}
