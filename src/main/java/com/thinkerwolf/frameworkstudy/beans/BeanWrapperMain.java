package com.thinkerwolf.frameworkstudy.beans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.thinkerwolf.frameworkstudy.beans.Company.Employee;

public class BeanWrapperMain {
	public static void main(String[] args) {
		BeanWrapper bw = new BeanWrapperImpl(Company.class);
		bw.setPropertyValue("name", "傲世堂");
		List<Employee> list = new ArrayList<Employee>();
		Employee employee1 = new Employee();
		employee1.setAge(20);
		employee1.setName("Jack Willium");
		list.add(employee1);
		bw.setPropertyValue("employees", list);
		System.out.println(bw.getPropertyValue("employees[0]"));
	}
}
