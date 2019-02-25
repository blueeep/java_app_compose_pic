package tools;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.Test;

import obj.ImageInfo;

/**
* @author sqh
* @version 创建时间：2019年2月23日 上午12:48:46
*/

/**
 * 正式处理需要用到的一些方法，也都是写静态方法。
 * 
 * 1.计算一张图的平均rgb值
 * 2.计算两个Color的距离
 * 3.查找ImageInfo中Color最接近指定Color的一个
 */
public class PreprecessTools {
	
	/**
	 * 应该换一种更好的平均算法了，避免求和时sum大于int的范围了
	 * 假设都是些小图片
	 * @param bi
	 * @return
	 */
	public static Color averageRGB(BufferedImage bi) {
		Color result = null;
		
		int h = bi.getHeight();
		int w = bi.getWidth();
		int size = w*h;
		
		int sumR = 0;
		int sumG = 0;
		int sumB = 0;
		
		for(int i=0;i<h;i++) {
			for(int j=0;j<w;j++) {
				Color temp = new Color(bi.getRGB(j, i));
				sumR = sumR+temp.getRed();
				sumG = sumG+temp.getGreen();
				sumB = sumB+temp.getBlue();
			}
		}
		
		result = new Color(sumR/size, sumG/size, sumB/size);
		
		return result;
	}
	
	/**
	 * 没有开平方。
	 * @param c1
	 * @param c2
	 * @return
	 */
	public static int colorDis(Color c1,Color c2) {
		int result =  (c1.getRed()-c2.getRed())*(c1.getRed()-c2.getRed())
				+(c1.getGreen()-c2.getGreen())*(c1.getGreen()-c2.getGreen())
				+(c1.getBlue()-c2.getBlue())*(c1.getBlue()-c2.getBlue());
				
		return result;
	}
	
	/**
	 * 没有排序，安排之后会快一些。
	 * @param images
	 * @param c
	 * @return
	 */
	public static ImageInfo nearestImage(List<ImageInfo> images,Color c) {
		int disMin = 256*256*3;
		ImageInfo result = null;
		
		for(ImageInfo i:images) {
			int dis = PreprecessTools.colorDis(c, i.avgColor);
			if(dis<disMin) {
				disMin = dis;
				result = i;
			}
		}
		
		return result;
	}
	
	/**
	 * @throws IOException ********************************************************************************************/
	
	@Test
	public void test_1() throws IOException {
		System.out.println("test1:");
		
		BufferedImage bi1 = ImageIO.read(new File("src/small/pic1.jpg"));
		BufferedImage bi2 = ImageIO.read(new File("src/small/pic2.jpg"));
		BufferedImage bi3 = ImageIO.read(new File("src/small/pic3.jpg"));
		BufferedImage bi4 = ImageIO.read(new File("src/small/pic4.jpg"));
		BufferedImage bi5 = ImageIO.read(new File("src/small/pic5.jpg"));
		
		Color c1 = PreprecessTools.averageRGB(bi1);
		Color c2 = PreprecessTools.averageRGB(bi2);
		Color c3 = PreprecessTools.averageRGB(bi3);
		Color c4 = PreprecessTools.averageRGB(bi4);
		Color c5 = PreprecessTools.averageRGB(bi5);
		
		System.out.println(c1);
		System.out.println(c2);
		System.out.println(c3);
		System.out.println(c4);
		System.out.println(c5);
		
	}
	
}
