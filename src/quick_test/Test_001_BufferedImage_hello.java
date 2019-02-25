package quick_test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.junit.Test;

/**
* @author sqh
* @version 创建时间：2019年2月22日 下午7:55:10
*/

/**
 * 图片的读写等操作如果使用最基本的方法，就是按字节读取
 * 这就要求知道各种图片类型的文件格式，其中bmp图片格式最为简单
 * 
 * 但是这次不这样，为了开发的进度，使用Java类BufferedImage:java.awt.image.BufferedImage
 * 这里简到的test一下该类读写文件的操作
 * 
 * 其他的话题：
 * 	图片文件格式
 * 	字节方式读写文件
 * 	其他java类读写文件
 * 	BufferedImage类的其他用法
 * 以后再来研究。
 * 
 * 但是图片处理，java始终不是一个最好的选择
 * 	c有更高的效率
 * 	python有更丰富的库
 */

public class Test_001_BufferedImage_hello {
	
	/**
	 * 参考：https://blog.csdn.net/xietansheng/article/details/78453570
	 * @throws IOException 
	 * 
	 */
//	@Test//读取文件，封装为BufferedImage类
	public void test_1() throws IOException {
		System.out.println("test1:");
		
		File file = new File("src/pic/pic.png");
		BufferedImage bufferedImage = ImageIO.read(file);
//		BufferedImage bi = new BufferedImage(width, height, imageType);
		
		int h = bufferedImage.getHeight();
		int w = bufferedImage.getWidth();
		
		System.out.println(h);
		System.out.println(w);
	}
	/**
	 * 1.BufferedImage初始化的方式是初始宽和高。不能从文件初始。
	 * 2.可以使用ImageIO读取图片文件为BufferedImage
	 * 3.BufferImage封装了不同类型的图片。
	 * @throws IOException 
	 */
	
//	@Test//读取指定位置的像素
	public void test_2() throws IOException {
		System.out.println("test2:");
		
		File file = new File("src/pic/red.bmp");
		BufferedImage bufferedImage = ImageIO.read(file);
		
		int a = bufferedImage.getRGB(10, 10);
//		int b = bufferedImage.getRGB(-1,-1);
		int b2 = bufferedImage.getRGB(0, 0);
//		int c = bufferedImage.getRGB(-10, -10);
//		int d = bufferedImage.getRGB(100, 100);
//		int e = bufferedImage.getRGB(101, 101);
//		int f = bufferedImage.getRGB(200, 200);
		
		System.out.println(a);
//		System.out.println(b);
		System.out.println(b2);
//		System.out.println(c);
//		System.out.println(d);
//		System.out.println(e);
//		System.out.println(f);
	}
	/**
	 * BufferedImage.readRGB(x,y)获取(x,y)处的rgb值
	 * x,y的范围为x:[0,width-1] y:[0,height-1]
	 * 超出范围的取值会抛出异常
	 * @throws IOException 
	 */
	
	
//	@Test//readRGB()返回的值
	public void test_3() throws IOException {
		System.out.println("test3:");
		
		BufferedImage bi1 = ImageIO.read(new File("src/pic/red.bmp"));
		BufferedImage bi2 = ImageIO.read(new File("src/pic/green.bmp"));
		BufferedImage bi3 = ImageIO.read(new File("src/pic/blue.bmp"));
		
		int r = bi1.getRGB(10, 10);
		int g = bi2.getRGB(10, 10);
		int b = bi3.getRGB(10, 10);
		
		System.out.println(r);
		System.out.println(g);
		System.out.println(b);
		
		Color cr = new Color(r);
		Color cg = new Color(g);
		Color cb = new Color(b);
		
		System.out.println(cr);
		System.out.println(cg);
		System.out.println(cb);
	}
	/**
	 * 本来可以分析一下返回的int值的，但是可以直接使用封装好的Color类解析成rgb
	 * 参考：https://codeday.me/bug/20180811/213586.html
	 */
	
	/**
	 * 以上的三个test都是关于读取的
	 * 包括：
	 * 	读取文件
	 * 	读取像素
	 * 	像素值格式化
	 * 下面test一下反过程：
	 * 	写入文件
	 * 	set像素
	 * 	编码像素值
	 * @throws IOException 
	 */
	
//	@Test//初始化一个BufferedImage类
	public void test_4() throws IOException {
		System.out.println("test4:");

		BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		Color c1 = new Color(bi.getRGB(10, 10));
		
		System.out.println(c1);
	}
	/**
	 * 初始化BufferedImage类
	 * 初始化中图片的类型可以参考:https://zhidao.baidu.com/question/1381871954685102220.html
	 * 也可以直接到java的api看看
	 * 初始化的BufferedImage都是0 0 0即黑色
	 * @throws IOException 
	 */
	
	@Test
	public void test_5() throws IOException {
		System.out.println("test5:");
		
		BufferedImage bi = new BufferedImage(100,100,BufferedImage.TYPE_3BYTE_BGR);
		
		Color red  = new Color(255, 0, 0);
		Color green  = new Color(0, 255, 0);
		Color blue  = new Color(0, 0, 255);
		
		int red_int = red.getRGB();
		int green_int = green.getRGB();
		int blue_int = blue.getRGB();
		
		for(int i=0;i<20;i++) {
			for(int j=0;j<20;j++) {
				bi.setRGB(j, i, red_int);
			}
		}
		
		for(int i=0;i<20;i++) {
			for(int j=50;j<70;j++) {
				bi.setRGB(j, i, green_int);
			}
		}
		
		for(int i=70;i<100;i++) {
			for(int j=0;j<20;j++) {
				bi.setRGB(j, i, blue_int);
			}
		}
		
		ImageIO.write(bi, "bmp", new File("src/pic/out.bmp"));
	}
	/**
	 * 可见：
	 * 	1.BI的坐标是从左上角为（0，0）的
	 * 	2.使用ImageIO.write()方法写入文件
	 * 	3.无论使用的什么编码，都可以使用color的rgb，不会乱的
	 * @throws IOException 
	 */
	
	@Test//关于读取图片效率的问题
	public void test_6() throws IOException {
		Calendar c1 = Calendar.getInstance();
		ImageIO.read(new File("src/pic/photo.jpg"));
		Calendar c2 = Calendar.getInstance();
		
		ImageIO.read(new File("src/pic/red.bmp"));
		Calendar c3 = Calendar.getInstance();
		
		System.out.println(c2.getTimeInMillis()-c1.getTimeInMillis());
		System.out.println(c3.getTimeInMillis()-c2.getTimeInMillis());
	}
	/**
	 * 这个test回答了这个问题：BI读取rgb是读的内存还是io
	 * 如此看来，应该是读的内存。因为这里应该是一开始就将整个文件加载进内存了。
	 */
	
}
