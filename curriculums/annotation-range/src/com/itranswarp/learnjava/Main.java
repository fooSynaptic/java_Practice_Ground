package com.itranswarp.learnjava;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class Main {
	public static void main(String [] args) throws Exception {
		Method h = Person.class.getMethod("hello");
		h.invoke(new Student());
		
		
	}
}







class Student extends Person {
  public void hello() {
      System.out.println("Student:hello");
  }
}



void check(Person person) throws IllegalArgumentException, ReflectiveOperationException {
    // 遍历所有Field:
    for (java.lang.reflect.Field field : person.getClass().getFields()) {
        // 获取Field定义的@Range:
        Range range = field.getAnnotation(Range.class);
        // 如果@Range存在:
        if (range != null) {
            // 获取Field的值:
            Object value = field.get(person);
            // 如果值是String:
            if (value instanceof String) {
                String s = (String) value;
                // 判断值是否满足@Range的min/max:
                if (s.length() < range.min() || s.length() > range.max()) {
                    throw new IllegalArgumentException("Invalid field: " + field.getName());
                }
            }
        }
    }
}











