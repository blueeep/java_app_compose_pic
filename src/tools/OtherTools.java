package tools;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.Test;

import obj.ImageInfo;

/**
* @author sqh
* @version 创建时间：2019年2月23日 上午3:06:34
*/

/**
 * 一些其他的方法，都是静态方法
 * 
 * 1.生成6*6*6种颜色的模板图片
 * 2.加载目录下所有图片到内存
 * 3.BI缩小到指定的长宽。
 * 4.BI旋转90度。
 * 5.缩小文件夹下所有图片
 */
public class OtherTools {

	public static void genneratePics() throws IOException {
		int h = 75;
		int w = 100;
		int num = 6;
		int gap = 255/(num-1);
		
		int length = num*num*num;
		Color[] cs = new Color[length]; 
		
		for(int i=0;i<num;i++) {
			for(int j=0;j<num;j++) {
				for(int k=0;k<num;k++) {
					cs[i*num*num+j*num+k] = new Color(i*gap, j*gap, k*gap);
				}
			}
		}
		
		for(int i=0;i<length;i++) {
			
			BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
			for(int j=0;j<h;j++) {
				for(int k=0;k<w;k++) {
					bi.setRGB(k, j, cs[i].getRGB());
				}
			}
			String name = "src/templates/template_"+(i+1)+".bmp";
			ImageIO.write(bi, "bmp", new File(name));
		}
	}
	
	/**
	 * 要求的是目录下都是pic哟，不能有其他的文件和文件夹。
	 * @param dir
	 * @throws IOException 
	 */
	public static List<ImageInfo> loadPics(String dir) throws IOException {
		List<ImageInfo> result = new ArrayList<>();
		
		File fileDir  = new File(dir);
		File[] files = fileDir.listFiles();
		
		for(File i:files) {
			ImageInfo item = new ImageInfo();
			item.bi = ImageIO.read(i);
			result.add(item);
		}
		
		return result;
	}
	
	/**
	 * 缩小到指定的宽高，注意宽高比例，不然会变形。
	 * 这里仅仅是用的降采样，后续可以研究下其他的zoom in算法
	 * @param bi
	 */
	public static BufferedImage smallImage(BufferedImage bi,int h,int w) {
		BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
		
		int rawH = bi.getHeight();
		int rawW = bi.getWidth();
		int gapW = rawW/w;
		int gapH = rawH/h;
		
		for(int i=0;i<h;i++) {
			for(int j=0;j<w;j++) {
				result.setRGB(j, i, bi.getRGB(j*gapW, i*gapH));
			}
		}
		
		return result;
	}
	
	public static BufferedImage revolve90(BufferedImage bi) {
		int h = bi.getHeight();
		int w = bi.getWidth();
		BufferedImage result = new BufferedImage(h, w, BufferedImage.TYPE_3BYTE_BGR);
		
		for(int i=0;i<h;i++) {
			for(int j=0;j<w;j++) {
				result.setRGB(i, j, bi.getRGB(j, i));
			}
		}
		
		return result;
	}
	
	/**
	 * 将路径下的所有图片都变小
	 * 假设路径下的都是h:w为3：4的照片，如果是4：3的先旋转。
	 * @param path
	 * @throws IOException 
	 */
	public static void smallDir(String path,int h,int w) throws IOException {
		Calendar c1 = Calendar.getInstance();//Timing
		
		File dir = new File(path);
		File[] fs = dir.listFiles();
		
		int count = 1;
		for(File i:fs) {
			BufferedImage bi = ImageIO.read(i);
			if(bi.getHeight()>bi.getWidth()) {
				bi = OtherTools.revolve90(bi);
			}
			bi = OtherTools.smallImage(bi, h, w);
			
			ImageIO.write(bi, "bmp", new File("src/smalls/"+count+".bmp"));
			count++;
		}
		
		Calendar c2 = Calendar.getInstance();//Timing
		System.out.print("获取小图片完成，用时：");
		System.out.println(c2.getTimeInMillis()-c1.getTimeInMillis());
	}
	
	/**********************************************************************************************/
	
//	@Test
	public void test_1() throws IOException {
		System.out.println("test1:");
		OtherTools.genneratePics();
	}
	
//	@Test
	public void test_2() throws IOException {
		System.out.println("test2:");
		
		Calendar c1 = Calendar.getInstance();//Timing
		List<ImageInfo> list = OtherTools.loadPics("src/templates");
		System.out.println(list.size());
		Calendar c2 = Calendar.getInstance();//Timing
		
		System.out.print("加载用时：");
		System.out.println(c2.getTimeInMillis()-c1.getTimeInMillis());
	}
	
//	@Test
	public void test_3() throws IOException {
		System.out.println("test3:");
		
		BufferedImage bi = ImageIO.read(new File("src/pic/p3.jpg"));
		BufferedImage small = OtherTools.smallImage(bi, 100, 75);
		BufferedImage revolve = OtherTools.revolve90(small);
		
		ImageIO.write(revolve, "bmp", new File("src/revolve.bmp"));
		
	}
	
	@Test
	public void test_4() throws IOException {
		System.out.println("test4:");
		
		OtherTools.smallDir("src/raws/photos", 75, 100);
	}
}
