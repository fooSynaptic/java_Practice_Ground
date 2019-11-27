# chapter 7 复用类

组合 vs 继承

## 组合语法
```java
//: reusing/SprinklerSystem.java
//composition for code use

class waterSource{
	private String s;
	waterSource() {
		System.out.println("waterSource()");
		s = "Constructed";
	}
	public String toString() {
		return s;
	}
}


public class SprinklerSystem{
	private String value1, value2, value3, value4;
	private waterSource source = new waterSource();
	private int i;
	private float f;
	public String toString() {
		return 
			"value1 = " + value1 + " " + 
			"value2 = " + value2 + " " + 
			"value3 = " + value3 + " " + 
			"value4 = " + value4 + "\n" +
			"i = " + i + " " + "f = " + f + " " + 
			"source = " + source;
	}

	// unit test
	public static void main(String[] args){
		SprinklerSystem sprinklers = new SprinklerSystem();
		System.out.println(sprinklers);
	}
}

```