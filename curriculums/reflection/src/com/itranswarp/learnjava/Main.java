package com.itranswarp.learnjava;
//import java.lang.reflect.Field;
import java.lang.reflect.Field;





public class Main {
	public static void main(String[] args) throws Exception {
		Person p = new Person("xiao ming");
		System.out.println(p.getName());
		
		Class c = p.getClass();
		Field f = c.getDeclaredField("name");
		
		f.setAccessible(true);
		f.set(p, "hujiaxin");
		
		System.out.println(p.getName());
		
	}
}





class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
