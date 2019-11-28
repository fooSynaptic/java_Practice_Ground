# 你知道java对象的序列化与反序列化背后的原理吗？
---

## 序列化与反序列化
序列化-Serialization是将对象的状态信息转换为可以存储或传输的形式的过程。一般将一个对象存储至一个存储媒介，例如档案或者是记忆体缓冲等。在网络传输过程中，可以是字节或者是XML等格式。而字节的或者XML编码格式可以还原完全相等的对象，这个相反的过程又称为反序列化。

## java对象的序列化与反序列化
在javaa中，我们可以通过多种方式来创建对象，并且只要对象没有被回收我们都可以复用该对象。但是，我们创建出来的这些java对象都是存在于JVM的堆内存中。只有JVM处于运行状态的时候，这些对象才可能存在。一旦JVM停止运行，这些对象的状态也就随之而丢失了。

但是在真实的应用场景中，我们需要将这些对象持久化下来，并且能够在需要的时候把对象重新读取出来。Java的对象序列化可以帮助我们实现该功能。
- 对象序列化机制（Object serialization）是java语言内建的一种对象持久化方式，通过对象序列化，可以把对象的状态保存为字节数组，并且可以在有需要的时候将这个字节数组通过反序列化的方式再转换成对象。对象序列化可以很容易的在JVM中的活动对象和字节数组（流）之间进行转换。

在Java中，对象的序列化与反序列化被广泛应用到RMI（远程方法调用）及网络传输中。

### 相关接口和类
java为了方便开发人员将java对象进行序列化以及反序列化提供了一套方便的API来支持。其中包括了一下接口和类：
`
java.io.Serializable
java.io.Externalizable
ObjectOutput
ObjectInput
ObjectOutputStream
ObjectInputStream
`

### Serializable接口
类通过实现java.io.Serializable接口一起用其序列化功能。为实现此接口的类将无法使其任何状态序列化或者反序列化。可序列化类的所有子类型本身都是可序列化的。序列化接口没有方法或者字段，仅用于标识可序列化的语义。

当时图对一个对象进行序列化的时候，如果遇到不支持Serializable接口的对象，在此情况下，将抛出`NotSerializableException`.
- 虽然Serializable接口中并没有定义任何属性和方法，但是如果一个类想要具备序列化能力也必须要实现它。其实，主要是因为序列化在真正的执行过程中会使用instanceof判断一个类是否实现类Serializable，如果未实现则直接抛出异常。

如果要序列化的类有弗雷，要想同时将在父类中定义过的变量持久化下来，那么弗雷也应该继承`java.io.Serializable`接口。

如下是一个实现了`java.io.Serializable`接口的类。
```java
package com.hollischaung.serialization.SerializableDemos;
import java.io.Serializable;

public class User1 implements Serializable {
	private String name;
	private int age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		this.age = age;
	}
	@Override
	public String toString() {
		return "Userr{" +
				"name=" + name + '\'' +
				", age=" + age +
				'}';
	}
}
```

通过下面的代码进行序列化以及反序列化
```java
package com.hollischaung.serialization.SerializableDemos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class SerializableDemo1 {

	public static void main(String[] args){
		User1 = user = new User1();
		user.setName("hollis");
		user.setAge(23);
		System.out.println(user);


	// write object to file
	try(FileOutputStream fos = new FileOutputStream("tmpFile");
		ObjectOutputStream oos = new ObjectOutputStream(fos)){
		oos.writeObject(user);
	} catch (IOException e){
		e.printStackTrace();
	}

	// read object from File
	File file = new File("tempFile");
	try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
		User1 = newUser = (User1)ois.readObject();
		System.out.println(newUser);
	} catch (IOException|ClassNotFoundException e){
		e.printStackTrace();
	}

	}
	


}
```
上面的代码中，我们将代码中定义出来的User对象通过序列化的方式保存到文件中，然后再冲文件中将他反序列化成java对象，结果是我们的对象的数型均被持久化下来了。

### Externalizable接口
除了Serializable之外，java中还提供了另外一个序列化接口`Externalizable`
参考如下代码：
```java
package com.hollischaung.serialization.ExternalizableDemos;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
* Created by hollis on 16/2/17.
* 实现Externalizable接口
*/
public class User1 implements Externalizable {

   private String name;
   private int age;

   public String getName() {
       return name;
   }
   public void setName(String name) {
       this.name = name;
   }
   public int getAge() {
       return age;
   }
   public void setAge(int age) {
       this.age = age;
   }
   public void writeExternal(ObjectOutput out) throws IOException {

   }
   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

   }
   @Override
   public String toString() {
       return "User{" +
               "name='" + name + '\'' +
               ", age=" + age +
               '}';
   }
}


package com.hollischaung.serialization.ExternalizableDemos;

import java.io.*;

/**
* Created by hollis on 16/2/17.
* 对一个实现了Externalizable接口的类进行序列化及反序列化
*/
public class ExternalizableDemo1 {

  public static void main(String[] args) {
      //Write Obj to file
      User1 user = new User1();
      user.setName("hollis");
      user.setAge(23);
      try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tempFile"))){
          oos.writeObject(user);
      } catch (IOException e) {
          e.printStackTrace();
      }

      //Read Obj from file
      File file = new File("tempFile");
      try(ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(file))){
          User1 newInstance = (User1) ois.readObject();
          //output
          System.out.println(newInstance);
      } catch (IOException | ClassNotFoundException e ) {
          e.printStackTrace();
      }
  }
}
//OutPut:
//User{name='null', age=0}
```
通过观察上面的示例的输出结果可以发现，对User1类进行序列化以及反序列化之后得到的对象的所有属性的值都变成了默认值。也就是说，之前的那个对象的状态并没有被持久化下来。这就是Externalizable接口和Serializable接口的区别：

***Externalizable继承了Serializable，该接口中定义了两个抽象方法：writeExternal()和readExternal()。当使用Externalizable接口来进行序列化与反序列化的时候需要开发人员重写writeExternal()与readExternal()方法。***

由于上面的代码中，并没有在这两个方法中定义序列化实现细节，所以输出的内容为空。还有一点值得注意：在使用Externalizable进行序列化的时候，在读取对象时会调用被序列化类的无参构造器去创建一个新的对象，然后再将被保存对象的字段的值分别填充到新对象中。所以实现Externalizable接口的类必须要提供一个public的无参数的构造器。

修改过后的EXternalizable接口实现：
```java
package com.hollischaung.serialization.ExternalizableDemos;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
* Created by hollis on 16/2/17.
* 实现Externalizable接口,并实现writeExternal和readExternal方法
*/
public class User2 implements Externalizable {

   private String name;
   private int age;

   public String getName() {
       return name;
   }
   public void setName(String name) {
       this.name = name;
   }
   public int getAge() {
       return age;
   }
   public void setAge(int age) {
       this.age = age;
   }
   public void writeExternal(ObjectOutput out) throws IOException {
       out.writeObject(name);
       out.writeInt(age);
   }
   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
       name = (String) in.readObject();
       age = in.readInt();
   }

   @Override
   public String toString() {
       return "User{" +
               "name='" + name + '\'' +
               ", age=" + age +
               '}';
   }
}


//OutPut:
//User{name='hollis', age=23}
```

### ObjectOutput和ObjectInput接口
上面的writeExternal方法和readExternal方法分别接收ObjectOutput和ObjectInput类型参数。这两个类作用如下：

ObjectInput扩展自DataInput接口以包含对象的读操作。
**DataInput接口用于从二进制流中读取字节，并且根据所有java基本类型数据进行重构。同时还提供了根据UTF-8修改版格式的数据重构String的工具。
对于此接口中的所有数据读取例程来说，如果在读取所需字节数之前已经到达文件末尾（end of file），则将怕熬出EOFException（IOException的一种）。如果因为到达文件末尾之外的其他原因无法读取字节，则将抛出IOException而不是EOFException，尤其是在输入流已经关闭的情况下，将抛出IOException。**

ObjectOutput扩展自DataOutput接口以包含对象的写入操作。
**DataOutput接口用于将数据从任意java基本类型转换为一系列字节，并将这些字节写入二进制流。同时还提供了一个将Stirng转换成UTF-8修改版格式并写入所得到的系列字节的工具。
对于此接口中写入字节的所有方法，如果由于某种原因无法写入某个字节，则抛出IOException。**

### ObjectOutputStream, ObjectInputStream类
通过前面的代码片段中我们也能知道，我们一般使用ObjectOutputStream的writeObject方法把一个对象进行持久化，再使用ObjectInputStream的readObject从持久化存储中把对象读取出来。

### transient关键字
transient关键字的作用是控制变量的序列化，在变量声明前加入该关键字，可以组织该变量被序列化到文件中，在被反序列化后，transient变量的值被设置为初始值，如int型的是0， 对象型的是null。

### 序列化ID
虚拟机是否允许反序列化，不仅取决于类路径和功能代码是否一致，一个非常重要的一i电视两个类的序列化ID是否一致（`private static final long serialVersionUID`）
序列化ID在Eclipse下提供了两种生成策略，一个是固定的1L，一个是随机生成一个不重复的Long类型数据（实际上是使用JDK工具生成）,如果没有特殊需求，使用默认的1L就可以，这样可以确保代码一致时反序列化成功。那么随机生成的序列化ID有什么作用呢，有些时候，通过改变序列化ID可以来限制某些用户的使用。