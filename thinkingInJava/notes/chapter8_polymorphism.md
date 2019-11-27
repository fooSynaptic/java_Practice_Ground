# 第八章 多态

***在面向对象的程序设计语言中，多态是继数据抽象和继承之后的第三种基本特征。***

## 再论向上转型

对象既可以作为它自己本身的类型使用，也可以作为它的基本类型使用。而这种把对某个对象的引用视为对其基类型的引用的做法被称作为向上转型——因为在继承书的画法中，基类是放置在上方的。

看下面有关乐器的例子：
```java
//: polymorphism/music/Note/java
// notes to play on musical instruments.
package polymorphism.music;

public enum Note {
	MIDDLE_C, C_SHAPE, B_FLAT; // eetc.
}

//: polymorphism/musicInstrucment.java
package polymorphism.music;
import static net.mindview.util.Print.*;

class Instrucment {
	public void play(Note n){
		print("Instrucment.play()");
	}
}

//: polymorphism/music.Wind.java
package polymorphism.music;

// Wind objects are instruments
// because they have the same interface:
public class Wind extends Instrucment {
	// Redefine interface method
	public void play(Note n){
		System.out.println("Wind.play()" + n);
	}
}


//: polymorphism/music/Music.java
// Inhertance & upcasting.
package polymorphism.music;

public class Music {
	public static void tune(Instrument i){
		i.paly(Note.MIDDLE_C);
	}

	public static void main(String[] args) {
		Wind flute = new Wind();
		tune(flute); //Upcasting
	}
}


```
Music.tune()方法接受一个Instrument引用，同时也接受任何导出自Instrument的类。在mian()方法中，当一个Wind引用传递到tune()方法时，就会出现这种情况，而不需要任何类型转换。

## 转机


## 构造器和多态

## 协变返回类型

## 用继承进行设计

## 总结