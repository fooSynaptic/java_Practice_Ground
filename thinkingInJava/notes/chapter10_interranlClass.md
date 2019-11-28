# 第十章 内部类

***可以将一个类的定义放在另一个类的定义内部，这就是内部类***
必须要了解，内部类与组合是完全不同的概念，这一点很重要。

---

## 创建内部类
```java
// Creating inner classes.

public class Parcell1 {
	class Contents {
		private int i = 11;
		public int value() {return i;}
	}

	class Destination {
		private String label;
		Destination(String whereTo) {
			label = whereTo;
		}
		String readLabel() {return label; }
	}

	// Using inner classes looks just like using any other class, whthin Oarcel1;
	public void ship(String dest) {
		Contents c = new Contents();
		Destination d = new Destination(dest);
		Ststem.out.println(d.readLabel());
	}

	public static void main(String[] args) {
		Parcel1 p = new Paracel1();
		p.ship("Tassmania");
	}

}

```

```java
// Creating inner classes.

public class Parcel2 {
	class Contents {
		private int i = 11;
		public int value() {return i;}
	}

	class Destination {
		private String label;
		Destination(String whereTo) {
			label = whereTo;
		}
		String readLabel() {return label; }
	}

	public Destination to(String s) {
		return new Destination(s);
	}
	public Contents contents() {
		return new Contents();
	}

	public void ship(String dest) {
		Contents c = contents;
		Destination d = to(dest);
		Ststem.out.println(d.readLabel());
	}

	public static void main(String[] args) {
		Parcel2 p = new Paracel2();
		p.ship("Tassmania");

		// defining references to inner classes
		Parcel2 q = new Parcel2();
		Parcel2.Contents c = q.contents();
		Parcel2.Destination d = q.to("Borneo");

	}

}

```

## 链接到外部类
内部类可以访问其外围对象的所有成员而不需要任何特殊条件，此外内部类还拥有其外围类的所有元素的访问权。
- 为什么？
`当某个外围类的对象创建了一个内部类对象时，此内部类对象必定会秘密捕获一个指向那个外围类对象的引用，然而，在你访问此外围类的成员时，就是用那个引用来选择外围类的成员。
`

## 使用.this 和 .new
如果你需要生成对外部类对象的引用，可以使用外部类的名字后面紧跟圆点和this.这样产生的引用自动地具有正确的类型，这一点在编译器就被知晓并受到检查，因此没有任何运行时开销。
```java
// Qualifying access to the outer-class object.

public class DotThis {
	void f() {System.out.println("DotThis.f()"); }
	public class Inner {
		public DotThis outer() {
			return DotThis.this;

		}
	}

	public Inner inner() {return new Inner(); }
	public static void main(String[] args) {
		DotThis dt = new DotThis();
		DotThis.Inner dti = dt.inner();
		dti.outer().f();
	}

}
```

有时你可能想要告知某些其他对象，去创建其某个内部类的对象。要实现此目的，你必须在new表达式中提供对其他外部类对象的引用，这时需要使用.new语法，就想下面这样：
```java
// Creating an inner class directly using the .new syntax.
public class DotNew{
	public class Inner {}
	public static void main(String[] args){
		DotNew dn = new DotNew();
		DotNew.Inner dni = dn.new Inner();
	}
}
```
在拥有外部类对象之前是不可能创建内部类对象的。这事因为内部类对象会暗暗地连接到创建它的外部类对象上。但是如果你创建的是嵌套类（静态内部类），那么它就不需要对外部类对象的引用。
	下面你可以看到将.new应用于Parcel的示例：

```java
// Using .new to create instances of inner classes.

public class Parcel3 {
	class Contents {
		private int i = 11;
		public int value() {return i;}
	}

	class Destination {
		private String label;
		Destination(String whereTo) {
			label = whereTo;
		}
		String readLabel() {return label; }
	}

	

	public static void main(String[] args) {
		Parcel3 p = new Paracel3();
		//Must use instance of outer class to create an instace of the inner class
		Parcel3.Contents c = p.new contents();
		Parcel3.Destination d = p.new Destination("Tasmania");

	}

}

```

## 内部类和向上转型
接口：
```java
public interface Destination{
	String readLabel();
}

public interface Contents{
	int value();
}
```

当取得了一个指向基类或者接口的引用时，甚至无法找出它确切的类型：
```java
class Parcel4{
	private class PContents implements Contents{
		private int i = 11;
		public int value() {return i; }
	}

	protected class PDestination implements Destination{
		private String label;
		private PDestination(String whereTo){
			label = whereTo;
		}
		public String readLabel() {return label; }
	}

	public Destination destination(Strng s){
		return new PDestination(s);
	}
	public Contents contents(){
		return new PContents();
	}
}


public class TestParcel{
	public static void main(String[] args){
		Parcele4 p = new Parcel4();
		Contents c = p.contents();
		Destination d = p.destination("Tasmania");

	}
}
```



private内部类给类的设计者提供了一种途径，通过这种方式可以完全组织任何依赖于类型的编码，并且完全隐藏了实现的细节。此外，从珂u段程序员的角度来看，由于不能访问任何新增加的，原本不属于公共接口的方法，所以扩展接口是没有价值的。
这也给Java编译器提供了生成更高效代码的机会。


## 在方法和作用域内的内部类（***我附庸的附庸不是我的附庸***）
- 局部内部类

## 匿名内部类
```java
// returning an instance of an anonymous inner class.

public class Parcel7{
	public Contents contents(){
		return new Contents() {	//insert a class defination
			private int i = 11;
			public int value() { return i; }
		};
	}
	public static void main(String[] args){
		Paracel7 p = new Parcel7();
		Contents c = p.contents();
	}
}
```
如果需要一个有参数的构造器：
```java
// Calling the base-class constructor.

public class Parcel8{
	public Wrapping wrapping(int x){
		// Base constructor call
		return new Wrapping(x) {	//insert a class defination
			public int value() { 
				return super.value() * 47;
			}
		};	// exp end
	}
	public static void main(String[] args){
		Paracel8 p = new Parcel8();
		Wrapping w = p.wrapping(10);
	}
}

```

### 再访工厂方法


## 嵌套类
- 要创建嵌套类的对象，并不需要其外围类的对象。
- 不能从嵌套类的对象中访问非静态的外围类对象。
在本章之前，在一个普通的非static内部类中，通过一个特殊的this引用可以连接到其外围类对象。嵌套类就没有这个特殊的this引用，这使得它类似于一个static的方法。

### 接口内部类

### 从多层嵌套类中访问外部类的成员
一个内部类被嵌套多少层并不重要——它能透明地访问所有它所嵌入的外围类的所有成员


## 为什么需要内部类
- 内部类实现一个接口与外围类实现这个接口有什么区别？
后者不是总能享用到接口带来的方便，有时需要用哀悼接口的实现，所以使用内部类最吸引人的原因是：*每个内部类都能独立地继承自一个接口的实现，所以无论外围类是否已经集成了某个接口的实现，对于内部类都没有影响*（接口的组合）

### 闭包与回调

### 内部类与控制框架

## 内部类的继承

## 内部类可以被覆盖吗

## 局部内部类

## 内部类标识符
加上“$”


## 总结
比起面向对象编程中其他的概念来，接口和内部类更深奥复杂，比如C++就没有这些。将两者结合起来，同样额能够解决C++中的用多成功继承能够解决的问题。然而多重继承在C++中被证明是相当难以使用的，相比较而言，Java中的接口和内部类就容易理解的多。
虽然这写特性本身是相当直观的，但是就像多态机制一样，这些特性的使用应该是设计阶段考虑的问题。而随着时间的推移，读者将能够更好的识别什么情况下应该使用接口，什么情况下使用内部类，或者两者同时使用。但此时，读者至少应该已经完全理解了它们的语法和语义。