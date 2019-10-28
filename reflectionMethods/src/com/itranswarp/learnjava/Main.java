package com.itranswarp.learnjava;
//import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class Main {
	public static void main(String [] args) throws Exception {
		Method h = Person.class.getMethod("hello");
		h.invoke(new Student());
		
		
	}
}





class Person {
    public void hello() {
        System.out.println("Person:hello");
    }
}

class Student extends Person {
    public void hello() {
        System.out.println("Student:hello");
    }
}
