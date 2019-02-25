package tools;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;


import obj.ImageInfo;

/**
* @author sqh
* @version 创建时间：2019年2月22日 下午11:36:17
*/

/**
 * 正式处理时用到的一些方法，都是写静态方法。
 * 
 * 1.将BI的矩阵拼接成一个大的矩阵。
 * 2.将一张大图片拆分为一个BI矩阵。
 * 3.由BI矩阵得到rgb均值矩阵
 * 4.计算得到ImageInfo的均值数据。
 */
public class ProcessTools {
	
	/**
	 * 假定所有的图片都是一样大小的。
	 * @param images
	 * @throws IOException 
	 */
	public static BufferedImage joinImageMatrix(BufferedImage[][] images) throws IOException {
		int h = images[0][0].getHeight();
		int w = images[0][0].getWidth();
		int rows = images.length;
		int colums = images[0].length;
		int bigH = h*rows;
		int bigW = w*colums;
		
		BufferedImage result = new BufferedImage(bigW, bigH, BufferedImage.TYPE_3BYTE_BGR);
		
		for(int i=0;i<rows;i++) {//每行图片
			for(int j=0;j<colums;j++) {//每张图片
				int startX = j*w;
				int startY = i*h;
				for(int k=0;k<h;k++) {//每张图的每行
					for(int l=0;l<w;l++) {//每张的每个像素
						result.setRGB(startX+l, startY+k, images[i][j].getRGB(l, k));
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 这里划分之后需要考虑rows和colums的比例，否则到时候拼接子图时导致图片变形
	 * 由于所有的照片处理成相同大小，且主图子图的比例相同，所以最好rows=colums
	 * @param bi
	 * @param rows
	 * @param colums
	 * @return
	 */
	public static BufferedImage[][] splitImage(BufferedImage bi,int rows,int colums){
		BufferedImage[][] bis = new BufferedImage[rows][colums];
		
		int bigH = bi.getHeight();
		int bigW = bi.getWidth();
		int h = bigH/rows;
		int w = bigW/colums;
		
		for(int i=0;i<rows;i++) {
			for(int j=0;j<colums;j++) {
				BufferedImage sbi = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
				int startX = j*w;
				int startY = i*h;
				for(int k=0;k<h;k++) {
					for(int l=0;l<w;l++) {
						sbi.setRGB(l, k, bi.getRGB(startX+l, startY+k));
					}
				}
				bis[i][j] = sbi;
			}
		}
		
		return bis;
	}
	
	
	public static Color[][] averageMatrix(BufferedImage[][] bis){
		int rows = bis.length;
		int colums = bis[0].length;
		Color[][] result = new Color[rows][colums];
		
		for(int i=0;i<rows;i++) {
			for(int j=0;j<colums;j++) {
				result[i][j] = PreprecessTools.averageRGB(bis[i][j]);
			}
		}
		
		return result;
	}
	
	public static void calculateAvg(List<ImageInfo> infos) {
		for(ImageInfo i:infos) {
			Color avg = PreprecessTools.averageRGB(i.bi);
			i.avgColor = avg;
		}
	}
	
	/**********************************************************************************************/
	
//	@Test
	public void test_1() throws IOException {
		System.out.println("test1:");
		
		BufferedImage bi1 = ImageIO.read(new File("src/pic/blue.bmp"));
		BufferedImage bi2 = ImageIO.read(new File("src/pic/green.bmp"));
		BufferedImage bi3 = ImageIO.read(new File("src/pic/red.bmp"));
		
		BufferedImage[][] biMatrix = new BufferedImage[3][3];
		
		biMatrix[0][0] = bi1;
		biMatrix[1][1] = bi1;
		biMatrix[2][2] = bi1;
		
		biMatrix[0][1] = bi2;
		biMatrix[1][2] = bi2;
		biMatrix[2][0] = bi2;
		
		biMatrix[0][2] = bi3;
		biMatrix[1][0] = bi3;
		biMatrix[2][1] = bi3;
		
		ProcessTools.joinImageMatrix(biMatrix);
	}
	
//	@Test
	public void test_2() throws IOException {
		System.out.println("test2:");
		
		BufferedImage bi1 = ImageIO.read(new File("src/small/pic1.jpg"));
		BufferedImage bi2 = ImageIO.read(new File("src/small/pic2.jpg"));
		BufferedImage bi3 = ImageIO.read(new File("src/small/pic3.jpg"));
		BufferedImage bi4 = ImageIO.read(new File("src/small/pic4.jpg"));
		BufferedImage bi5 = ImageIO.read(new File("src/small/pic5.jpg"));
		
		BufferedImage[][] bis = new BufferedImage[50][50];
		
		//左上
		for(int i=0;i<20;i++) {
			for(int j=0;j<20;j++) {
				bis[i][j] = bi1;
			}
		}
		//左下
		for(int i=30;i<50;i++) {
			for(int j=0;j<20;j++) {
				bis[i][j] = bi2;
			}
		}
		//右上
		for(int i=0;i<20;i++) {
			for(int j=30;j<50;j++) {
				bis[i][j] = bi3;
			}
		}
		//右下
		for(int i=30;i<50;i++) {
			for(int j=30;j<50;j++) {
				bis[i][j] = bi4;
			}
		}
		//中
		for(int i=20;i<30;i++) {
			for(int j=0;j<50;j++) {
				bis[i][j] = bi5;
			}
		}
		for(int i=0;i<50;i++) {
			for(int j=20;j<30;j++) {
				bis[i][j] = bi5;
			}
		}
		
		ProcessTools.joinImageMatrix(bis);
	}
	
//	@Test
	public void test_3() throws IOException {
		System.out.println("test3:");
		
		BufferedImage bi1 = ImageIO.read(new File("src/small/fake1.jpg"));
		BufferedImage bi2 = ImageIO.read(new File("src/small/fake2.jpg"));
		BufferedImage bi3 = ImageIO.read(new File("src/small/fake3.jpg"));
		BufferedImage bi4 = ImageIO.read(new File("src/small/fake4.jpg"));
		BufferedImage bi5 = ImageIO.read(new File("src/small/fake5.jpg"));
		
		BufferedImage[][] bis = new BufferedImage[50][50];
		
		//左上
		for(int i=0;i<20;i++) {
			for(int j=0;j<20;j++) {
				bis[i][j] = bi1;
			}
		}
		//左下
		for(int i=30;i<50;i++) {
			for(int j=0;j<20;j++) {
				bis[i][j] = bi2;
			}
		}
		//右上
		for(int i=0;i<20;i++) {
			for(int j=30;j<50;j++) {
				bis[i][j] = bi3;
			}
		}
		//右下
		for(int i=30;i<50;i++) {
			for(int j=30;j<50;j++) {
				bis[i][j] = bi4;
			}
		}
		//中
		for(int i=20;i<30;i++) {
			for(int j=0;j<50;j++) {
				bis[i][j] = bi5;
			}
		}
		for(int i=0;i<50;i++) {
			for(int j=20;j<30;j++) {
				bis[i][j] = bi5;
			}
		}
		
		ProcessTools.joinImageMatrix(bis);
	}
	
	/**
	 * 通过test2和test3及其结果，得出结论：可以用图片的rgb均值代替一张图片。在图片缩小时，这个均值呈现的最好。
	 * @throws IOException 
	 */
	
	
//	@Test
	public void test_4() throws IOException {
		System.out.println("test4:");
		
		BufferedImage bi = ImageIO.read(new File("src/t3.bmp"));
		Color[][] cs = ProcessTools.averageMatrix(ProcessTools.splitImage(bi, 50, 50));
		
		System.out.println(cs[0][0]);
		System.out.println(cs[49][49]);
	}
	
}
