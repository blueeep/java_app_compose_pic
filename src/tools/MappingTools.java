package tools;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.Test;

import obj.ImageInfo;

/**
* @author sqh
* @version 创建时间：2019年2月23日 下午2:30:10
*/

/**
 * 这是最关键的也是最后的一步映射
 * 	这次采用的主要的方式是：大图划分求均值，小图求均值
 * 	所以主要的还是大图均值和小图均值的映射
 * 	由大图均值矩阵通过小图的信息集合。映射成一个BI矩阵
 */
public class MappingTools {

	public static BufferedImage[][] SimpleMapping_1(Color[][] avgMatrix,List<ImageInfo> images){
		Calendar c1 = Calendar.getInstance();//Timing
		
		int rows = avgMatrix.length;
		int colums = avgMatrix[0].length;
		BufferedImage[][] result = new BufferedImage[rows][colums];
		
		for(int i=0;i<rows;i++) {//每行的图片
			for(int j=0;j<colums;j++) {//每张图片
				Color cNow = avgMatrix[i][j];
				result[i][j] = PreprecessTools.nearestImage(images, cNow).bi;
			}
		}
		
		Calendar c2 = Calendar.getInstance();//Timeing
		System.out.print("映射用时：");
		System.out.println(c2.getTimeInMillis()-c1.getTimeInMillis());
		return result;
	}
	
	@Test
	public void test_1() throws IOException {
		System.out.println("test1:");
		
		//1.获取图片。主图和子图们
		BufferedImage mainPic = ImageIO.read(new File("src/p10.jpg"));
		List<ImageInfo> images = OtherTools.loadPics("src/smalls-1");
		
		//2主图转为均值矩阵，子图列表获取均值。
		Color[][] mainMatrix = ProcessTools.averageMatrix(ProcessTools.splitImage(mainPic, 100, 100));
		ProcessTools.calculateAvg(images);
		
		//3.映射
		BufferedImage[][] resultMatrix = MappingTools.SimpleMapping_1(mainMatrix, images);
		
		//4.拼接
		BufferedImage result = ProcessTools.joinImageMatrix(resultMatrix);
		
		//5.写入
		ImageIO.write(result, "bmp", new File("src/out10.bmp"));
	}
}
