package obj;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
* @author sqh
* @version 创建时间：2019年2月23日 下午12:07:20
*/

/**
 * 封装了BI，添加了一个HM来存储一些可能需要的信息，例如平均rgb值
 * 由于具体需要些什么信息可能随着算法的变动会变，所以使用hm，这样性能可能会受影响。
 */
public class ImageInfo {
	public BufferedImage bi;
	public Color avgColor;
}
