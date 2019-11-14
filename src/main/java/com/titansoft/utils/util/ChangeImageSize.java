package com.titansoft.utils.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

public class ChangeImageSize {

	/** */
	/**
	 * 缩放图像
	 * 
	 * @param srcImageFile
	 *            源图像文件地址
	 * @param result
	 *            缩放后的图像地址
	 * @param scale
	 *            缩放比例
	 * @param flag
	 *            缩放选择:true 放大; false 缩小;
	 */
	public static void scale(String srcImageFile, String result, int scale,
			boolean flag) {
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件
			int width = src.getWidth(); // 得到源图宽
			int height = src.getHeight(); // 得到源图长
			if (flag) {
				// 放大
				width = width * scale;
				height = height * scale;
			} else {
				// 缩小
				width = width / scale;
				height = height / scale;
			}
			Image image = src.getScaledInstance(width, height,
					Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 缩放图片并且加密
	 * @param file
	 * @param result
	 * @param scale
	 * @param flag
	 */
	public static void scale(File file, String result, int scale,
			boolean flag) {
		try {
			BufferedImage src = ImageIO.read(file); // 读入文件
			int width = src.getWidth(); // 得到源图宽
			int height = src.getHeight(); // 得到源图长
			if (flag) {
				// 放大
				width = width * scale;
				height = height * scale;
			} else {
				// 缩小
				width = width / scale;
				height = height / scale;
			}
			Image image = src.getScaledInstance(width, height,
					Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** */
	/**
	 * 缩放图像
	 * 
	 * @param srcImageFile
	 *            源图像文件地址
	 * @param result
	 *            缩放后的图像地址
	 * @param scale
	 *            缩放比例
	 * @param flag
	 *            缩放选择:true 放大; false 缩小;
	 */
	public static boolean scale(String srcImageFile, String result,
			int scalewidth, int scaleheight) {
		boolean isscalesucc = true;
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件
			// int width = src.getWidth(); // 得到源图宽
			// int height = src.getHeight(); // 得到源图长

			/*
			 * if (flag) { // 放大 width = width * scale; height = height * scale; }
			 * else { // 缩小 width = width / scale; height = height / scale; }
			 */

			Image image = src.getScaledInstance(scalewidth, scaleheight,
					Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(scalewidth, scaleheight,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流
		} catch (IOException e) {
			isscalesucc = false;
			e.printStackTrace();
		}
		return isscalesucc;
	}

	/**
	 * 返回文件的文件后缀名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExtension(String fileName) {
		try {
			return fileName.split("\\.")[fileName.split("\\.").length - 1];
		} catch (Exception e) {
			return null;
		}
	}

	/** */
	/**
	 * 图像切割
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param descDir
	 *            切片目标文件夹
	 * @param destWidth
	 *            目标切片宽度
	 * @param destHeight
	 *            目标切片高度
	 */
	public static void cut(String srcImageFile, String descDir, int destWidth,
			int destHeight) {
		try {
			Image img;
			ImageFilter cropFilter;
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // 源图宽度
			int srcHeight = bi.getWidth(); // 源图高度
			if (srcWidth > destWidth && srcHeight > destHeight) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);
				destWidth = 200; // 切片宽度
				destHeight = 150; // 切片高度
				int cols = 0; // 切片横向数量
				int rows = 0; // 切片纵向数量
				// 计算切片的横向和纵向数量
				if (srcWidth % destWidth == 0) {
					cols = srcWidth / destWidth;
				} else {
					cols = (int) Math.floor(srcWidth / destWidth) + 1;
				}
				if (srcHeight % destHeight == 0) {
					rows = srcHeight / destHeight;
				} else {
					rows = (int) Math.floor(srcHeight / destHeight) + 1;
				}
				// 循环建立切片
				// 改进的想法:是否可用多线程加快切割速度
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						// 四个参数分别为图像起点坐标和宽高
						// 即: CropImageFilter(int x,int y,int width,int height)
						cropFilter = new CropImageFilter(j * 200, i * 150,
								destWidth, destHeight);
						img = Toolkit.getDefaultToolkit().createImage(
								new FilteredImageSource(image.getSource(),
										cropFilter));
						BufferedImage tag = new BufferedImage(destWidth,
								destHeight, BufferedImage.TYPE_INT_RGB);
						Graphics g = tag.getGraphics();
						g.drawImage(img, 0, 0, null); // 绘制缩小后的图
						g.dispose();
						// 输出为文件
						ImageIO.write(tag, "JPEG", new File(descDir
								+ "pre_map_" + i + "_" + j + ".jpg"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** */
	/**
	 * 图像类型转换 GIF->JPG GIF->PNG PNG->JPG PNG->GIF(X)
	 */
	public static void convert(String source, String result) {
		try {
			File f = new File(source);
			f.canRead();
			f.canWrite();
			BufferedImage src = ImageIO.read(f);
			ImageIO.write(src, "JPG", new File(result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** */
	/**
	 * 彩色转为黑白
	 * 
	 * @param source
	 * @param result
	 */
	public static void gray(String source, String result) {
		try {
			BufferedImage src = ImageIO.read(new File(source));
			ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
			ColorConvertOp op = new ColorConvertOp(cs, null);
			src = op.filter(src, null);
			ImageIO.write(src, "JPEG", new File(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// //高清缩略图转换
	public static boolean convertScale(String sourceimagefile,
			String targetpath, int width, int height) {
		boolean isconvertsucc = true;
		try {
			// 缩图
			Image img = ImageIO.read(new File(sourceimagefile));
			/*
			 * int src_w = img.getWidth(null); int src_h = img.getHeight(null);
			 * int tg_w = 220;// 压缩目标宽度 int tg_h = 220;// 高度 if (src_w > src_h) {
			 * tg_h = (int) (((double) src_h / (double) src_w) * tg_w); } else
			 * if (src_w < src_h) { tg_w = (int) (((double) src_w / (double)
			 * src_h) * tg_h); }
			 */
			BufferedImage bufImg = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			bufImg.getGraphics().drawImage(
					img.getScaledInstance(width, height, Image.SCALE_SMOOTH),
					0, 0, null);
			FileOutputStream fos = new FileOutputStream(targetpath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
			encoder.encode(bufImg); // 近JPEG编码
			// 高质量的图片
			JPEGEncodeParam encoderParam = JPEGCodec
					.getDefaultJPEGEncodeParam(bufImg);
			encoderParam.setQuality(1f, true);
			encoder.encode(bufImg, encoderParam);
			fos.close();
		} catch (Exception ex) {
			isconvertsucc = false;
			ex.printStackTrace();
		}
		return isconvertsucc;
	}

	/*
	 * 图片另存为
	 */
	public static boolean imageSaveAs(String sourceImagePath, String targePath) {
		boolean isSaveAs = true;
		try {
			/* tif转换到jpg格式 */
			String input2 = sourceImagePath;
			String output2 = targePath;
			RenderedOp src2 = JAI.create("fileload", input2);
			OutputStream os2 = new FileOutputStream(output2);
			// ///
			com.sun.media.jai.codec.JPEGEncodeParam param2 = new com.sun.media.jai.codec.JPEGEncodeParam();
			// 指定格式类型，jpg 属于 JPEG 类型
			ImageEncoder enc2 = ImageCodec.createImageEncoder("JPEG", os2,
					param2);
			enc2.encode(src2);
			os2.close();
		} catch (Exception ex) {
			isSaveAs = false;
			ex.printStackTrace();
		}
		return isSaveAs;
	}

	/***************************************************************************
	 * 视频截图图片的缩略图转换
	 **************************************************************************/
	public static boolean spImageConvertScale(String sourceimagefile,
			String targetpath, int width, int height, String username) {
		boolean isSucc = true;
		try {
			// 按普通图片的编码格式先视频截的图片转换成普通的图片
			/* tif转换到jpg格式 */
			String input2 = sourceimagefile;
			if (!new File("d:/tmpimage/" + username).exists()) {
				new File("d:/tmpimage/" + username).mkdirs();
			} else {
				new File("d:/tmpimage/" + username).delete();
				new File("d:/tmpimage/" + username).mkdirs();
			}
			// 通过格式化输出日期
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
					"yyyyMMddhhmmss");
			String currtime = format.format(Calendar.getInstance().getTime());
			// 获取原文件的扩展名
			String extname = getExtension(sourceimagefile);
			String output2 = "d:/tmpimage/" + username + "/" + currtime + "."
					+ extname;
			RenderedOp src2 = JAI.create("fileload", input2);
			OutputStream os2 = new FileOutputStream(output2);
			// ///
			com.sun.media.jai.codec.JPEGEncodeParam param2 = new com.sun.media.jai.codec.JPEGEncodeParam();
			// 指定格式类型，jpg 属于 JPEG 类型
			ImageEncoder enc2 = ImageCodec.createImageEncoder("JPEG", os2,
					param2);
			enc2.encode(src2);
			os2.close();

			// 另存为完成再进行缩略图转换
			// 缩图
			Image img = ImageIO.read(new File(output2));
			BufferedImage bufImg = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			bufImg.getGraphics().drawImage(
					img.getScaledInstance(width, height, Image.SCALE_SMOOTH),
					0, 0, null);
			FileOutputStream fos = new FileOutputStream(targetpath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
			encoder.encode(bufImg); // 近JPEG编码
			// 高质量的图片
			JPEGEncodeParam encoderParam = JPEGCodec
					.getDefaultJPEGEncodeParam(bufImg);
			encoderParam.setQuality(1f, true);
			encoder.encode(bufImg, encoderParam);
			fos.close();

			// 删除重新生成的文件
			new File(output2).delete();
			// //
		} catch (Exception ex) {
			isSucc = false;
			ex.printStackTrace();
		}
		return isSucc;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
/*		spImageConvertScale("C:/Users/jiaogan/Desktop/bak/泰坦移交数据包/201811190001/高碧峰440111681202009/图像数据/优化图像数据/1-1-001.jpg", 
				"C:/Users/jiaogan/Desktop/bak/泰坦移交数据包/201811190001/高碧峰440111681202009/图像数据/优化图像数据/1-1-002.jpg", 800, 600,
				"sysdba");*/
		scale("C:/Users/jiaogan/Desktop/bak/泰坦移交数据包/201811190001/高碧峰440111681202009/图像数据/优化图像数据/3-1-001.jpg", 
				"C:/Users/jiaogan/Desktop/bak/泰坦移交数据包/201811190001/高碧峰440111681202009/图像数据/优化图像数据/3-1-002.jpg",
				4, false);
		System.out.println("=============");
	}

}
