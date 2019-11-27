# 第九章 接口

***接口和内部类为我们提供了一种将接口与实现分离的更加结构化的方法***

## 抽象类和抽象方法

下面是修改过的“管弦乐器”的例子，其中采用了抽象类和抽象方法：
```java
//： interfaces/music4/Music4.java
// Abstract classes and methods.
package interfaces.music4;
import polymorphism.music.Note;
import static net.mindview.util.Print.*;

abstract class Instrument {
	private int i; // Storage allocated for each
	public abstract void play (Note n);
	public String what() { return "Instrument"; }
	public abstract void adjust();
}


class Wind extends Instrumnet {
	public void play(Note n){
		print("Wind.play() " + n);
	}
	public String what() { return "Wind";}
	public void adjust() {}
}


class Percussion extends Instrument {
	public void play(Note n){
		print("Percussion.play() " + n);

	}
	public String what() { return "Percussion";}
	public void adjust() {}
}



class Stringed extends Instrument {
	public void play(Note n) {
		print("Stringed.play() " + n);
	}
	public String what() { return "Stringed"; }
	public void adjust() {}
}

class Brass extends Wind {
	public void play(Note n) {
		print("Brass.play() " + n);
	}
	public void adjust() {print("Brass.adjust()");}
}


class Woodwind extends Wind {
	public void play(Note n){
		print("Woodwind.play() " + n);

	}
	public String what() { return "Woodwind";}


}




public class Music4{
	// Doesn't care about type, so new types
	// added to the system still work right;
	static void tune(Instument i){
		i.play(Note.MIDDLE_C);
	}

	static void tuneAll(Instrument[] e){
		for(Instrument i: e)
			tune(i);
	}

	public static void main(String[] args) {
		//Upcasting during addition to the array:
		Instrument[] orchestra = {
			new Wind(),
			new Percussion(),
			new Stringed(),
			new Stringed(),
			new Brass(),
			new Woodwind()
		};
		tuneAll(orchestra);
	}
}

```
创建抽象类和抽象方法非常有用，因为它们可以使类的抽象性明确起来，并告诉用户和编译器打算怎么样来使用它们。抽象类还是很有用的重构工具，因为它们使得我们可以很容易地将公共方法沿着继承层次结构向上移动。

## 接口
interface关键字使得抽象的概念更向前迈进了一步。接口只提供了形式，而未提供任何具体实现。

## 完全解耦

## java中的多重继承
下面的例子展示了一个具体类组合数个接口之后产生了一个新类
```java
// Multiple interfaces.

interface CanFight {
	void fight();
}

interface CanSwin {
	void swin();
}

interface CanFly {
	void fly();
}

class ActionCharacter {
	public void fight() {}
}

class Hero extends ActionCharacter
	implements CanFight, CanSwin, CanFly {
		public void swim() {}
		public void fly() {}
	}


public class Adventure {
	public static void t(CanFight x) {x.fight();}
	public static void u(CanSwin x) {x.swim();}
	public static void v(CanFly x) {x.fly();}
	public static void w(ActionCharacter x) {x.fight(); }
	public statuci void main(String[] args) {
		Hero h = new Hero();
		t(h);
		u(h);
		v(h);
		w(h);
	}
}

```
	可以看到，hero组合了具体类ActionCharacter和接口CanFight, CanSwim和CanFly。当通过这种方式将一个具体类和多个接口组合到一起时，这个具体类必须放在前面，后面跟着的才是接口。


我们应该使用接口还是抽象类？
- 如果要创建不带任何方法定义和成员变量的基类，那么就应该选择接口而不是抽象类。

## 通过继承来扩展接口

### 组合接口时的名字冲突
（尽量避免将重载、覆盖和实现混在一起）

## 适配接口
- 策略设计模式：“你可以用任何你想要的对象来调用我的方法，只要你的对象遵循我的接口。”
因为这种方式中，我们可以在任何现有类之上添加新的接口，所以这异位这让方法接受接口类型，是一种让任何类都可以对该方法进行适配的方式。

## 接口中的域
放入接口中的任何域都自动是static和final的。
所以接口就成为了一种很边接的用来创建常量组的工具。
```java
//: interfaces/Months.java
// Using interfaces to create groups of constants.
package interfaces;

public interface Months {
	int 
		Jan = 1, Feb = 2, Mar = 3,
		Apr = 4, May = 5, Jun = 6, July = 7,
		Aug = 8, Sep = 9, Oct = 10, 
		Nov = 11, Dec = 12;
}
```
### 初始化接口中的域

## 嵌套接口
- 嵌套一个private的接口，可以强制该接口中的方法定义不要添加任何类型信息，也就是不允许向上转型。同时也不能再定义它的类之外被实现。


## 接口与工厂
更精巧的复用代码的模式

## 总结
“确定接口是理想选择，因而应该总是选择接口而不是具体的类”，这其实是一种引诱。当然，对于创建类，几乎在任何时刻，都可以替代为创建一个接口和一个工厂。
许多人都掉进了这种诱惑的陷进，只要有可能就去创建接口和工厂。这种逻辑看起来好像是因为需要使用不同的具体实现，因此总是应该添加这种抽象性。这实际上已经变成了一种草率的设计优化。
任何抽象性都应该是应真正的需求而产生的。当必需时，你应该重构接口而不是到处添加额外级别的间接性，并由此带来的额外的复杂性。这种额外的复杂性非常显著，如果你让某人去处理这种复杂性，只是因为你意识到由于以防万一而添加了新的接口，而没有其他更有说服力的原因，那么好吧，如果我碰上了这种事，那么就会之一此人所作的所有设计了。
恰当的原则应该是优先选择类而不是接口。从类开始，如果接口的必须行变得非常明确，那么就进行重构，接口是一种重要的工具，但是它容易被滥用。

2019-11-17
