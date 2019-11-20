# 第14章 并发
---
- 什么是线程
- 终端线程
- 线程状态
- 线程属性
- 同步
- 阻塞队列
- 线程安全的集合
- Callable与Future
- 执行器
- 同步器
- 线程与Swing
---

多任务： 在同一时刻运行多个程序的能力。
多线程：一个程序同时执行多个任务。每一个任务称为一个线程。

- 多线程和多进程的区别：
本质的区别在于每个进程拥有自己的一整套变量，而进程则共享数据。
共享变量使得线程之间的通信比进程之间的通信更为有效，也更容易。
线程相对于进程更轻量级，创建、撤销一个线程比启动新进程的开销要小得多。

**参考书目**
- 《Java Concurrency in Practice》

## 什么是线程
```java
//单线程, 在球自己结束弹跳之前无法与程序进行交互
Ball ball = new Ball();
panel.add(ball);

for(int i=1; i<=STEPS; i++){
	ball.move(panel.getBounds());
	panel.paint(panel.getGraphics());
	Thread.sleep(DELAY);
}

```
如何在线程运行过程中用户对于程序的控制权
```java
// bounce/Bounce.java
package bounce;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// shows an animatd bouncing ball.
public class Bounce{
	public static void main(String[] args){
		EventQueue.invokeLater(()->{
			JFrame frame = new BounceFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}
}


// The frame with ball component and buttons
class BounceFrame extends JFrame{
	private BallComponent comp;
	public static final int STEPS = 1000;
	public static final int DELAY = 3;

	// constructs the frame with the component for showing the bouncing ball and start and close buttons
	public BounceFrame(){
		setTitle("Bounce");
		comp = new BallComponent();
		add(comp, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		addButton(buttonPanel, "Start", event -> addBall());
		addButton(buttonPanel, "Close", event -> Ststem.exit(0));
		add(buttonPanel, BorderLayout.SOUTH);
		pack();

	}

	// Adds a button to a container.
	public void addButton(Container c, String title, ActionListener listener){
		JButton button = new JButton(title);
		c.add(button);
		button.addActionListener(listener);

	}

	// Adds a bouncing ball to the panel and makes it bounce 1000 times.
	public void addBall(){
		try{
			Ball ball = new Ball();
			comp.add(ball);

			for (int i=1; i<=STEPS; i++){
				ball.move(comp.getBounds());
				comp.paint(comp.getGraphics());
				Thread.sleep(DELAY);

			}

		}
		catch (InterruptedException e){

		}
	}
}
```

```java
package bounce;

import java.awt.geom.*;

// A ball that moves and bounces off the edges of a rectangle

public class Ball{
	private static final int XSIZE = 15;
	private static final int YSIZE = 15;
	private double x = 0;
	private double y = 0;
	private double dx = 1;
	private double dy = 1;

	// Moves the ball to the next position, reversing direction if it hits one of the edges
	public void move(Rectangle2D bounds){
		x += dx;
		y += dy;
		if (x<bounds.getMinX()){
			x = bounds.getMinX();
			dx = -dx;
		}
		if (x+XSIZE>=bounds.getMaxX()){
			x = bounds.getMaxX() - XSIZE;
			dx = -dx;
		}
		if (y<bounds.getMinY()){
			y - bounds.getMinY();
			dy = -dy;
		}
		if (y+YSIZE>=bounds.getMaxY()){
			y = bounds.getMaxY() - YSIZE;
			dy = -dy;
		}
	}

	// Gets the shape of the ball at its current position.
	public Ellipse2D getShape(){
		return new Ellipse2D.Double(x, y, XSIZE, YSIZE);
	}

}
```
```java
// bounce/BallComponent.java
package bounce;

import java.awt.*;
import java.util.*;
import javax.swing.*;

// The component that draws the balls.

public class BallComponent extends JPanel{
	private static final int DEFAULT_WIDTH = 450;
	private static final int DEFAULT_HEIGHT = 350;

	private.java.util.List<Ball> balls = new ArrayList<>();

	// Add a ball to the component.
	public void add(Ball b){
		balls.add(b);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g); // erase background
		Graphics2D g2 = (Graphics2D) g;
		for (Ball b: balls){
			g2.fill(b.getShape());
		}
	}
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
}

```
- java.lang.Thread 1.0
`
-- static void sleep(long millis)
休眠给定的毫秒数
参数：millis 休眠的毫秒数
`

### 使用线程给其他任务提供机会
可以将移动求的代码放置在一个独立的线程中，运行这段代码可以提高弹跳球的响应能力。实际上可以发起多个球，每个球都在自己的线程中运行。另外AWT的事件分派线程（event dispatch thread）将一直并行运行，以处理用户界面的事件。由于每个线程都有机会得以运行，所以在球弹跳期间，当用户点集close，事件调度线程将有机会关注到这个事件，并处理“关闭”这一动作。
```java
package bounce;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

// Shows animated bouncing balls.

public class BounceThread{
	public static void main(String[] args){
		EventQueue.invokeLater(() -> {
			JFrame frame = new BounceFrame();
			frame.setTitle("BounceThread");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisivle(true);
		});
	}
}

// The frame with panel and buttons
class BounceFrame extends JFrame{
	private BallComponent comp;
	public static final int STEPS = 1000;
	public static final int DELAY = 5;

	//Constructs the frame with the component for showing the bouncing ball and Start and Close button

	public BounceFrame(){
		comp = new BallComponent();
		add(comp, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		addButton(buttonPanel, "Start", event->addBall());
		addButton(buttonPanel, "Close", event->System.exit(0));
		add(buttonPanel, BorderLayout.SOUTH);
		pack();
	}

	// Adds a button to a container
	public void addButton(Container c, String title, ActionListener listener){
		JButton button = new JButton(title);
		c.add(button);
		button.addActionListener(listener);
	}

	// Adds a bouncing ball to the canvas and starts a thread to make it bounce

	public void addBall(){
		Ball ball = new Ball();
		comp.add(ball);
		Runnable r = ()->{
			try{
				for (int i=1, i<=STEPS; i++){
					ball.move(comp.getBounds());
					comp.repaint();
					Thread.sleep(DELAY);
				}
			}
			catch (InterruptedException e){

			}

		};
		Thread t = new Thread(r);
		t.start();
	}

}
```




