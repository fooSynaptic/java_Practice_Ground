### 反射
什么是反射？
反射就是Reflection，java的反射是指程序在运行期可以拿到一个对象的所有信息。

正常情况下，如果我们要调用一个对象的方法，或者访问一个对象的字段，通常会传入对象实例：
```java
public class Main{
	String getFullName(Person p){
		return p.getFirstName() + " " + p.getLastNmae();
	}
}


```
但是在不能获得person类的时候，只有一个object实例
所以，反射为了解决在运行期，对某个实例一无所知的情况下， 如何调用其方法。

java中，除了基本类型，其他类型全部为class，class的本质是数据类型，无集成关系的数据类型无法赋值。
而class是由JVM在执行过程中动态加载的，每加载一种class，JVM就为其创建一个`Class`类型的实例并关联起来。这里的`Class`类型是一个名叫`Class`的class，长这样：
```java
public final class Class{
	private Class(){}
}
```
以String类为例，当JVM加载String类时，它首先读取String.class文件到内存，然后，为String类创建一个Class实例并关联起来：

`Class cls = new Class(String);`
这个Class实例是JVM内部创建的，如果我们查看JDK源码，可以发现Class类的构造方法是private，只有JVM能创建Class实例，我们自己的Java程序是U无法创建Class实例的。
而**一个Class实例包含了该class的所有完整信息**

由于JVM为每个加载的class创建了对应的Class实例，并在实例中保存了该class的所有信息，包括类名、包名、父类、实现的接口、所有方法、字段等，因此，如果获取了某个Class实例，我们就可以通过这个Class实例获取到该实例对应的class的所有信息。

这种通过Class实例获取class信息的方法称之为反射，有三种方法
```java
// 1. 直接通过一个class的静态变量class获取
Class cls = String.class;

// 2. 如果我们有一个实例变量，可以通过该实例变量提供的getClass()方法获取：
String s = "Hello";
Class cls = s.getCLass();

// 3. 如果知道一个class的完整类名，可以通过静态方法Class.forName()获取
Class cls = Class.forName("java.lang.String");
```

要从Class实例获取基本的信息，参考下面的代码：
```java
public class Main {
    public static void main(String[] args) {
        printClassInfo("".getClass());
        printClassInfo(Runnable.class);
        printClassInfo(java.time.Month.class);
        printClassInfo(String[].class);
        printClassInfo(int.class);
    }

    static void printClassInfo(Class cls) {
        System.out.println("Class name: " + cls.getName());
        System.out.println("Simple name: " + cls.getSimpleName());
        if (cls.getPackage() != null) {
            System.out.println("Package name: " + cls.getPackage().getName());
        }
        System.out.println("is interface: " + cls.isInterface());
        System.out.println("is enum: " + cls.isEnum());
        System.out.println("is array: " + cls.isArray());
        System.out.println("is primitive: " + cls.isPrimitive());
    }
}

```

# 动态加载
JVM在执行Java程序的时候，并不是一次性把所有用到的class全部加载到内存，而是第一次需要用到class时才加载。例如：
```java
// Main.java
public class Main {
    public static void main(String[] args) {
        if (args.length > 0) {
            create(args[0]);
        }
    }

    static void create(String name) {
        Person p = new Person(name);
    }
}
```
JVM为每个加载的class及interface创建了对应的Class实例来保存class及interface的所有信息；

获取一个class对应的Class实例后，就可以获取该class的所有信息；

通过Class实例获取class信息的方法称为反射（Reflection）；

JVM总是动态加载class，可以在运行期根据条件来控制加载class。

## 访问字段
Class类提供了以下几个方法来获取字段：
- Field getField(name)：根据字段名获取某个public的field（包括父类）
- Field getDeclaredField(name)：根据字段名获取当前类的某个field（不包括父类）
- Field[] getFields()：获取所有public的field（包括父类）
- Field[] getDeclaredFields()：获取当前类的所有field（不包括父类）

## 调用方法
能通过Class实例获取所有Field对象，同样的，可以通过Class实例获取所有Method信息。Class类提供了以下几个方法来获取Method：

- Method getMethod(name, Class...)：获取某个public的Method（包括父类）
- Method getDeclaredMethod(name, Class...)：获取当前类的某个Method（不包括父类）
- Method[] getMethods()：获取所有public的Method（包括父类）
- Method[] getDeclaredMethods()：获取当前类的所有Method（不包括父类）

# 总结
Java的反射API提供的Method对象封装了方法的所有信息：

通过Class实例的方法可以获取Method实例：getMethod()，getMethods()，getDeclaredMethod()，getDeclaredMethods()；

通过Method实例可以获取方法信息：getName()，getReturnType()，getParameterTypes()，getModifiers()；

通过Method实例可以调用某个对象的方法：Object invoke(Object instance, Object... parameters)；

通过设置setAccessible(true)来访问非public方法；
通过反射调用方法时，仍然遵循多态原则。



### java collections framework 概览

`容器，就是可以容纳其他java对象的对象，java collections framwork 为java开发者提供了通用的容器`
优点：
- 降低编程难度
- 提高程序性能
- 提高API见的互操作性
- 降低学习难度
- 降低设计和实现相关api的难度
- 增加程序的重用性

Java容器里只能放对象，对于基本类型（int, long, float, double等），需要将其包装成对象类型后（Integer, Long, Float, Double等）才能放到容器里。很多时候拆包装和解包装能够自动完成。这虽然会导致额外的性能和空间开销，但简化了设计和编程。

# 泛型
Java容器能够容纳任何类型的对象，这一点表面上是通过泛型机制完成，Java泛型不是什么神奇的东西，只是编译器为我们提供的一个“语法糖”，泛型本身并不需要Java虚拟机的支持，只需要在编译阶段做一下简单的字符串替换即可。实质上Java的单继承机制才是保证这一特性的根本，因为所有的对象都是Object的子类，容器里只要能够存放Object对象就行了。

事实上，所有容器的内部存放的都是Object对象，泛型机制只是简化了编程，由编译器自动帮我们完成了强制类型转换而已。JDK 1.4以及之前版本不支持泛型，类型转换需要程序员显式完成。

```java
ArrayList<String> list = new ArrayList<String>;
list.add(new String("Monday"));
list.add(new String("Tuesday"));

for(int i=0;i<list.size();i++){
	String weekday = list.get(i);
	System.out.println(weekday.toUpperCase());
}

```

# 内存管理
根cpp复杂的内存管理机制不同，java GC自动包揽了一切，java程序并不需要处理令人头疼的内存问题，因此JCF并不想C++ stl那样需要专门的空间适配器。
另外，由于java中对象都在堆上，且对象只能通过引用访问，容器里放的其实是对象的引用而不是对象本身，也就不存在C++容器的复制拷贝问题。

[java collection interfaces](https://zhuanlan.zhihu.com/p/24690125)

# 迭代器iterator
根C++ stl一样，jcf的迭代器为我们提供了遍历容器中元素的方法，只有容器本身清楚容器里的元素的组织方式，因此迭代器只能通过容器本身得到。每个容器都会通过内部类的形式实现自己的迭代器。

```java
// visit a list with iterator
ArrayList<String> list = new ArrayList<String>();
list.add(new String("Monday"));
list.add(new String("tuesday"));

Iterator<String> it = list.iterator(); //得到迭代器
while(it.hasNext()){
	String weekday = it.next();
	System.out.println(weekday.toUpperCase());
}
```
JDK 1.5 引入了增强的for循环，简化了迭代容器时的写法。
```java
//使用增强for迭代
ArrayList<String> list = new ArrayList<String>();
list.add(new String("Monday"));
list.add(new String("Tuesday"));
list.add(new String("Wensday"));
for(String weekday : list){//enhanced for statement
    System.out.println(weekday.toUpperCase());
}
```






### chapter 9 set


队列接口嘴贱形势可能类似下面这样：
```java
pulic interface Queue<E>{
	void add(E element);
	E remove();
	int size();

}

```

队列可以用循环数组和链表来实现

```java
public class CircularArrayQueue<E> implements Queue<E>{
	private int head;
	private int tail;

	CircularArrayQueue(int capacity) {...}
	public void add(E element) {...}
	public E remove() {...}
	public int size() {...}
	private E[] element;
}


public class LinkedListQueue<E> implements Queue<E>{
	private Link head;
	private Link tail;

	LinkedListQueue() {...}
	public void add(E element) {...}
	public E remove() {...}
	private E[] element;
}
```
- 在 Java 类库中， 集合类的基本接口是 Collection 接口。这个接口有两个基本方法
```java
public interface Collection<E>{
	boolean add(E element);
	Iterator<E> iterator();
}
```

- Iterator 接口包含 4 个方法:
```java
public interface Iterator<E>{
	E next();
	boolean hasNext();
	void remove();
	default void forEachRemaining(Consumer<? super E> action);

}
```
通过反复调用 next 方法， 可以逐个访问集合中的每个元素。 但是， 如果到达了集合的末 尾，next 方法将抛出一个`NoSuchElementException`。 因此， 需要在调用 next 之前调用 hasNext 方法。如果迭代器对象还有多个供访问的元素， 这个方法就返回 true。如果想要査看集合中的 所有元素， 就请求一个迭代器， 并在 hasNext 返回 true 时反复地调用 next 方法.
iterator在遍历集合的元素时，要首先调用next在调用remove，否则将会抛出一个`IllegalStateException`.
```java
it.remove();
it.remove(); //Error!

it.remove();
it.next();
it.remove(); //OK

```

- 泛型使用方法
由于collection于iterator都是泛型接口，可以编写操作任何集合类型的实用方法，例如，下面是一个检测任意集合是否包含指定元素的泛型方法：
```java
public static <E> boolean contains(Collection<E> c, Object obj){
	for (E element:c)
		if (element.equals(obj))
			return true;
	return false;
}
```
COllection 接口声明的部分有用的方法
```java
int sizeO
boolean isEmptyO
boolean contains(Object obj)
boolean containsAl1(Col1ection<?> c)
boolean equals(Object other)
boolean addAll (Collection<? extends E> from) 
boolean remove(Object obj)
boolean removeAl1(Col1ection<?> c)
void clear()
boolean retainAl1(Col1ection<?> c)
Object[] toArrayO
<T> T[] toArray(T[] arrayToFill)
```
当然， 如果实现 Collection接口的每一个类都要提供如此多的例行方法将是一件很烦人的 事情。 为了能够让实现者更容易地实现这个接口， Java 类库提供了一个类 AbstractCollection， 它将基础方法 size 和 iterator 抽象化了， 但是在此提供了例行方法。
java.util.iterator<E> 1.2
- boolean hasNext()
如果存在可访问的元素，则返回true
- E next()
返回将要访问的下一个对象，如果达到了集合的尾部，则抛出一个`NoSuchElementException`
- void remove()
删除上次访问的对象，这个方法必须紧跟在访问一个元素之后执行。如果上次访问之后，集合已经发生了变化，这个时候会抛出一个`IllegalStateException`

# 集合框架中的接口[java collection interfaces](https://zhuanlan.zhihu.com/p/24690125)
List是一个有序的集合。元素会增加到容器中的特定位置，可以采用两种方式访问元素：使用迭代器访问，或者使用一个整数索引来访问。后一种方法称为随机访问，因为这样可以按任意顺序访问元素。而迭代器的访问只能按照顺序访问元素。

java中提供了LinkedList
在下面的代码中，先添加了三个元素，然后再将第二个元素删除
```java
List<string> staff = new LinkedList<>();
staff.add("amy");
staff.add("bod");
staff.add("car");

Iterator iter = staff.iterator();
String first = iter.next();
String second = iter.next();
iter.remove();
```
- 9-1 linkedList/LinkedListTest.java

```java
package linkedList;
import java.util.*;

public class LinkedListTest{
	public static void main(String[] args){
		List<String> a = new LinkedList<>();
		a.add("AMY");
		a.add("CARL");
		a.add("Erica");

		List<String> b = new LinkedList<>();
		b.add("bob");
		b.add("Doug");
		b.add("FRACES");
		b.add("Gloria");

		//merge the woeds from b into a
		ListIterator<String> aIter = a.listIterator();
		Iterator<String> bIter = b.iterator();

		while (bIter.hasNext()){
			if (aIter.hasNext()) aIter.next();

			aIter.add(bIter.next());
		System.out.println(a);

		// remove every second word from b
		bIter = b.iterator();
		while (bIter.hasNext()){
			bIter.next();
			bIter.remove();

		}
		System.put.println(b);
		//bulk operation: remove all words in b from a
		a.removeAll(b);
		System.out.println(a);
		}
	}
}
```



