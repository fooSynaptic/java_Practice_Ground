## 反射
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

## 动态加载
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

## 总结
Java的反射API提供的Method对象封装了方法的所有信息：

通过Class实例的方法可以获取Method实例：getMethod()，getMethods()，getDeclaredMethod()，getDeclaredMethods()；

通过Method实例可以获取方法信息：getName()，getReturnType()，getParameterTypes()，getModifiers()；

通过Method实例可以调用某个对象的方法：Object invoke(Object instance, Object... parameters)；

通过设置setAccessible(true)来访问非public方法；
通过反射调用方法时，仍然遵循多态原则。



## java collections framework 概览

`容器，就是可以容纳其他java对象的对象，java collections framwork 为java开发者提供了通用的容器`
优点：
- 降低编程难度
- 提高程序性能
- 提高API见的互操作性
- 降低学习难度
- 降低设计和实现相关api的难度
- 增加程序的重用性

Java容器里只能放对象，对于基本类型（int, long, float, double等），需要将其包装成对象类型后（Integer, Long, Float, Double等）才能放到容器里。很多时候拆包装和解包装能够自动完成。这虽然会导致额外的性能和空间开销，但简化了设计和编程。

## 泛型
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

## 内存管理
根cpp复杂的内存管理机制不同，java GC自动包揽了一切，java程序并不需要处理令人头疼的内存问题，因此JCF并不想C++ stl那样需要专门的空间适配器。
另外，由于java中对象都在堆上，且对象只能通过引用访问，容器里放的其实是对象的引用而不是对象本身，也就不存在C++容器的复制拷贝问题。

[java collection interfaces](https://zhuanlan.zhihu.com/p/24690125)

## 迭代器iterator
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






# chapter 9 set



队列接口实现形式可能类似下面这样：
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


# 树集
TreeSet类和散列表十分类似，不过他比散列表有所改进，树集是一个有序集合（sorted collection）.可以以任何顺序将元素插入到集合中。在对集合进行遍历时，每个值将自动的按照排序后的顺序呈现。
- example
```java
SortedSet<String> sorter = new TreeSet<>();
sorter.add("Bob");
sorter.add("Amy");
sorter.add("Carl");

for (String s: sorter) System.println(s);

```
将一个元素添加到树中要比添加到散列表中慢（因为散列表的实现是链表），但是与检查数组或者链表中的重复元素相比还是要更快的。平均需要log2(n)。

树集的特点是排序有开销，并且对于元素对象需要提供一个Comparator。
如下演示了一个定制的比较器来按照描述信息排序。

java.util.NavigableSet<E> 返回大于value的最小元素或者小于value的最大元素，如果没有这样的元素则返回null


***treeSet/TreeSetTest.java***
```java
package treeSet;

import java.util.*;
/**
This program sorts a set of item by comparing their descriptions.
**/

public class TreeSetTest{
	public static void main(String[] args){
		SrotedSet<Item> parts = new TreeSet<>();
		parts.add(new Item("Toaster", 1234));
		parts.add(new Item("Widget", 4562));
		parts.add(new Item("Modem", 9912));
		System.out.println(parts);

		NavigableSet<Item> sortByDescription = new TreeSet<>(
			Comparator.comparing(Item::getDescription));

		sortByDescription.addAll(parts);
		System.out.println(sortByDescription);


	}


}

```
***treeSet/Item.java***
```java
package treeSet;

import java.util.*;
/**
An item with a description and a part number.
**/
public class Item implements Comparable<Item>{
	private String description;
	private int partNumber;
	/*
	*Constructs an item.
	*
	*@param aDescription
	*	the item's description
	*@param aPartNumber
	*	the item's part number
	*
	*/
	public Item(String aDescription, int aPartNumber){
		description = aDescription;
		partNumber = aPartNumber;

	}
	/*
	*Gets the description of this item.
	*@return the description
	*/
	public String getDescription(){
		return description;
	}


	public String toString(){
		return "[description=" + description + ", partNumber=" + partNumber + "]";
	}

	public boolean equals(Object otherObject){
		if (this==otherObject) return true;
		if (otherObject==null) return false;
		if (getClass()!=otherObject.getClass()) return false;
		Item other = (Item) otherObject;
		return Object.equals(description, other.description) && partNumber == other.partNumber;
	}

	public int hashCode(){
		return Objects.hash(description, partNumber);
	}

	public int comparaTo(Item other){
		int diff = Integer.compara(partNumber, other.partNumber);
		return diff!=0 ? diff: description.comparaTo(other.description);
	}

```

# 队列和双端队列
队列可以让人们有效地在尾部添加一个元素，在头部删除一个元素。
双端队列，可以让人们有效地在头部和尾部同时添加或者删除元素，不支持在队列中间添加元素。

# 优先级队列
priority queue中的元素可以按照任意的顺序插入，却总是按照排序的顺序进行检索。
也就是说，无论何时调用remove方法，总会获得当前优先级队列中最小的元素。然而优先级队列内部并没有对所有的元素进行排序。如果用迭代的方式处理这些元素，并不需要进行排序。优先级队列使用一个优雅且高效地数据结构，称为堆。堆是一个可以自我调整的二叉树，对树进行添加和删除操作，可以让最小的元素移动到根，而不必花费时间对元素进行排序。

## 映射
映射（map）用来存放键值对，如果提供了键，就能够查找到值。

# 基本映射操作
java类库为映射提供了两个通用的实现：HashMao和TreeMap，这两个类都实现了map接口。
要迭代处理映射的键和值，最容易的方法是使用forEach方法。可以提供一个接收键和值得lambda表达式。映射中的每一项会依序调用这个表达式。
```java
/* iterative for map */
scores.forEach((k, v) ->
	System.out.println("key=" + k+", value=" + v));


```
***map/MapTest.java***
```java
package map;

import java.util.*;
/**
 * this program demonstrates the use of a map with key type String and value typle Employee.
 */
public class mapTest{
	public static void main(String[] args){
		Map<String, Employee> staff = new HashMap<>();
		staff.put("144-25-5464", new Employee("Amy Lee"));
		staff.put("547-24-2546", new Employee("Harry Hacker"));
		staff.put("157-62-7935", new Employee("Gary Cooper"));
		staff.put("456-62-5527", new Employee("Francesca Cruz"));

		System.out.println(staff);
		staff.remove("567-24-2546");

		// iterate through all entries
		staff.forEach((k, v) ->
			System.out.println("key:"+k+"value: "+v));

	}
}

```

# 更新映射项的方法
```java

//1
counts.put(word, counts,getOrDefault(word, 0) + 1);


//2
counts.putIfAbsent(word, 0);
counts.put(word, count.get(word) + 1); // now we know that get will succeed

// 3 merge
counts.merge(word, 1, Integer::sum)

```

# 映射视图
集合框架不认为映射本身是一个集合。其他的数据结构框架认为映射是一个键、值对集合，或者是有键索引的值集合。不过可以得到映射的视图-这是实现了Collection接口或某个子接口的对象。
有三种视图：
- 键集合 	keySet()
- 值集合 	values()
- 键、值对集合 	entrySet()
键和键值对可以构成一个集合，因为映射中一个键只能有一个副本。下面的方法：
```java
Set<K> keySet()
Collection<V> values()
Set<Map.Entry<K, v>> entrySet()


for (Map.Entry<String, Employee> entry : staff.entrySet()){
	String k = entry.getKey(); 
	Employee v = entry.getValue(); 
	//do something with k, v
```

如果在键集合视图上调用得带器的remove方法，实际上会从映射中删除这个键和与它关联的值。不过，不能向键集合视图添加元素。另外，如果增加一个键而没有同时增加值也是没有意义的。如果视图调用add方法，它会抛出一个`UnsupportedOperationException`。

# 弱散列映射
运行情况：
WeakHashMap使用弱引用保存键。WeakReference对象将引用保存到另外一个对象中，在这里就是散列键。对于这种类型的对象，垃圾回收器用一种特有的方式进行处理。通常如果垃圾回收器发现某个特定的对象已经没有他人引用了，就将其回收。然而，如果某个对象只能由WeakReference引用，垃圾回收器仍然回收它，但要将引用这个对象的弱引用放入队列中。WeakHashMap将周期性的检查队列，以便找到新添加的弱引用。一个弱引用进入队列意味着这个键不再被他人使用，并且已经被收集起来，于是WeakHashMap将删除对应的条目。

# 链接散列表和集合
LinkedHashSet和LinkedHashMap类用来记住插入元素项的顺序，避免在散列表中的项从表面上看起来是随机排列的。

# 标识散列映射

## 视图和包装器

## 算法
泛型集合接口有一个很大的优点，即算法只需要实现一次，考虑一下计算集合中最大元素就是这样一个简单的算法。使用传统方式，程序设计人团可能会用循环实现这个算法。
```java
if (a.length==0) throw new NoSuchElementException();
T largest = a[0];
for(int i =1;i<a.length, i++)
	if (largest.compareTo(a[i])<0)
		largest = a[i];

```
当然，为找出数组列表中的最大元素所编写的代码与此稍有差别。
```java
if (v.size()==0) throw new NoSuchElementException();
T largest = v.get(0);
for (int i=1; i<v.size(), i++)
	if (largest.comparaTo(v.get(i))<0)
		largest = v.get(i);
```

对于链表来说，无法实施高效地随机访问，但是却可以使用迭代器。
```java
if (l.isEmpty()) throw new NoSuchElementException();
Iterator<T> iter = l.iterator();
T largest = iter.next();

while (iter.hasNext()){
	T next = iter.next();
	if (largest.comparaTo(next)<0)
		largest = next;
}

```
在计算最大元素中并不需要随机访问，直接使用迭代器遍历是一个可行的方法，因此，可以将max方法实现为能够接收任何实现了Collection接口的对象。
```java
public static <T extends Comparable> T max(Collection<T> c){
	if (c.isEmpty()) throw new NoSuchElementException();
	Iterator<T> iter = c.iterator();
	T largest = iter.next();

	while (iter.hasNext()){
		T next = iter.next();
		if (largest.comparaTo(next)<0)
			largest = next;
	}
	return largest;
}
```
现在这个方法可以用于计算链表、数组列表或者数组中最大的元素。
这是一个非常重要的概念。事实上，标准的CPP类库已经有几十种非常有用的算法，每个算法都是在泛型集合上进行操作。java类库中的算法没有如此丰富，但是，也包含了最基本的排序，二分查找等使用算法。

### 排序和混排
- Collections类中的sort方法可以对实现了List接口的集合进行排序。
```java
List<String> staff = new LinkList<>();
// fill collection
Collections.sort(staff);
```
以上这个方法假定了列表元素实现了Comparable接口，如果想采用其他方式对列表进行排序，可以使用List接口的sort方法并传入一个Comparator对象。
```java
staff.sort(Comparator.comparingDouble(Employee::getSalary))
```
按照降序对列表进行排序：
```java
staff.sort(Comparator.reverseOrder());

//按照工资逆序排序
staff.sort(Comparator.comparingDouble(Employee::getSalary).reversed());
```

集合类库中使用的排序算法（归并排序）比快速排序要慢一些，但是归并排序有一个主要的有点： `稳定`。即不需要交换相同的元素。
***shuffle/ShuffleTest.java***
```java
package shuffle;

import java.util.*;

public class ShuffleTest{
	public static void main(String[] args){
		List<Integer> numbers = new ArrayList<>();
		for(int i = 1; i< 50; i++)
			numbers.add(i);

		Collections.shuffle(numbers);
		List<integer> winningCombination = numbers.subList(0, 6);
		Collections.sort(winningCombination);
		System.out.println(winningCombination);
	}
}
```

### 二分查找
Collections类的binarySearch方法实现了这个算法。需要注意，集合必须是排好序的，否则算法将返回错误的答案。并且如果集合没有采用Comparable接口的compareTo方法进行排序，就还要提供一个比较器对象。
```java
i = Collections.binarySearch(c, element);
i = Collections.binarySearch(c, element, Comparator);
```
如果返回负数，则表示没有匹配的元素。
```java
//插入
if (i<0)
	c.add(-i-1, element);
```
只有采用随机访问，二分查找才有意义。如果必须利用得带方式一次次地遍历链表的一般元素来找到中间位置的元素，二分查找就完全失去了优势。因此，如果为binarySearch算法提供了一个链表，它将自动地变为线性查找。


### 简单算法
- 将一个列表中的元素复制到另外一个列表中；
- 用一个常量值填充容器；
- 逆置一个列表的元素顺序。
- 替换所有元素
`Collections.replaceAll("C++", "Java")`
`words.replaceAll(String::toLowerCase)`
- 删除所有元素
```java
words.removeAll(w->w.length()<=3);

```

### 批操作
- coll1.removeAll(coll2);
- coll1.retainAll(coll2); 将从coll1中删除所有未在coll2中出现的元素。
```java
//找到连个集合的交集
Set<String> result = new HashSet<>(a);

result.retainAll(b);
```


### 集合和数组的转换
- Arrays.asList包装器
```java
String values = ...;
HashSet<String> staff = new HasgSet<>(Arrays.asList(values));

//reverse
Object[] values = staff.toArray();
//这样做的结果是一个对象数组。尽管你知道集合中包含了一个特定类型的对象，但是不能使用强制类型转换：
String[] values = (String []) staff.toArray(); //Error！

//以下的方法可能是你想要的
//使用toArray方法的一个变体形式，提供一个所需类型并且长度为0的数组。这样一来，返回的数组就会创建为相同的数组类型：
String[] values = staff.toArray(new String[0]);

//如下这种情况不会创建新的数组
String[] values = staff.toArray(new Stirng[staff.size()]);
```

### 编写自己的算法
如果编写自己的算法，应该尽可能地使用接口，而不要使用具体的实现。例如，假设想用一组菜单项填充JMenu。传统上，这种方法可能会按照下列方式实现：
```java
void fillMenu(JMenu menu, ArrayList<JMenuItem> items){
	for(JMenuItem item: items)
		menu.add(item);
}
```

但是这个程序调用时必须在ArrayList中提供选项，因此一个更好地方案是提供更加通用的集合。如下的改进版本任何人都可以用ArrayList或者LinkedList，甚至用Arrays.asList包装器包装的数组调用这个方法。
```java
void fillMenu(JMenu menu, Collection<JMenuItem> items){
	for(JMenuItem item: items)
		menu.add(item);
}

```

返回类的方法
```java
List<JMenuItem> getAllItems(Jmenu menu){
	List<JMenuItem> items = new ArrayList();

	for(int i=0; i<menu.getItemCount();i++)
		items.add(menu.getItem(i));

	return items;
}
```
返回一个接口，这样做可以在日后改变想法，并用另一个集合重新实现这个方法：
例如：不复制所有的菜单项，而仅仅提供这些菜单项的视图。要做到这一点，只需要返回AbstractList的匿名子类。
```java
List<JmenuItem> getAllItems(final JMenu menu){
	return new
		AbstractList<>(){

			public JNenuItem get(int i){
				return menu.getItem(i);
			}

			public int size(){
				return menu.getItemCount();
			}
		};
}

```
这是一项高级技术，如果使用它，就应该将它支持的那些“可选”操作准确的记录在文档中。在这种情况下，必须提醒调用者返回的对象是一个不可修改的列表。



## 遗留的集合


