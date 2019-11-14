package com.titansoft.utils.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrintQuality;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageTool {

	public static void resize(String srcImg, String targetImg, int maxWidth, int maxHeight) {
		resize(new File(srcImg), new File(targetImg), maxWidth, maxHeight);
	}

	public static void resize(File srcImg, File targetImg, int maxWidth, int maxHeight) {
		if (srcImg != null && maxWidth > 0 && maxHeight > 0) {
			try {
				// 文件不存在
				if (!srcImg.exists()) {
					return;
				}
				// 创建路径
				if (!targetImg.getParentFile().exists()) {
					targetImg.getParentFile().mkdirs();
				}
				// 读取图片信息
				Image srcFile = ImageIO.read(srcImg);
				// 按比例计算图片缩放大小
				int width = srcFile.getWidth(null);
				int height = srcFile.getHeight(null);
				if (width > maxWidth || height > maxHeight) {
					if (maxWidth / width > maxHeight / height) {
						width = width * maxHeight / height;
						height = maxHeight;
					} else {
						height = height * maxWidth / width;
						width = maxWidth;
					}
				}
				// 缩放图片
				BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);
				// 缩放输出
				FileOutputStream out = new FileOutputStream(targetImg);
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
				// 压缩质量
				jep.setQuality(1, true);
				encoder.encode(tag, jep);
				out.close();
				srcFile.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

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

	public static void drawImage(String fileName, int count) {
		try {
			DocFlavor dof = null;
			if (fileName.endsWith(".gif")) {
				dof = DocFlavor.INPUT_STREAM.GIF;
			} else if (fileName.endsWith(".jpg")) {
				dof = DocFlavor.INPUT_STREAM.JPEG;
			} else if (fileName.endsWith(".png")) {
				dof = DocFlavor.INPUT_STREAM.PNG;
			}

			PrintService ps = PrintServiceLookup.lookupDefaultPrintService();

			PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
			pras.add(OrientationRequested.PORTRAIT);

			pras.add(new Copies(count));
			pras.add(PrintQuality.HIGH);
			DocAttributeSet das = new HashDocAttributeSet();

			// 设置打印纸张的大小（以毫米为单位）
			das.add(new MediaPrintableArea(0, 0, 210, 296, MediaPrintableArea.MM));
			FileInputStream fin = new FileInputStream(fileName);

			Doc doc = new SimpleDoc(fin, dof, das);

			DocPrintJob job = ps.createPrintJob();

			job.print(doc, pras);
			fin.close();
		} catch (IOException ie) {
			ie.printStackTrace();
		} catch (PrintException pe) {
			pe.printStackTrace();
		}
	}

	/**
	 * 给图片添加单个文字水印、可设置水印文字旋转角度
	 * 
	 * @param source    需要添加水印的图片路径（如：F:/images/6.jpg）
	 * @param output    添加水印后图片输出路径（如：F:/images/）
	
	
	 * @param word      水印文字
	 */
	public static String addWaterMark(String source, String output, String word) {

		try {
			Color color = new Color(0, 0, 0);
			// 读取原图片信息
			File file = new File(source);
			if (!file.isFile()) {
				return file + " 不是一个图片文件!";
			}
			Image img = ImageIO.read(file);
			int width = img.getWidth(null);
			int height = img.getHeight(null);
			// 加水印
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bi.createGraphics();
			g.drawImage(img, 0, 0, width, height, null);
			// 设置水印字体样式
			Font font = new Font("宋体", Font.PLAIN, 10);
			// 根据图片的背景设置水印颜色
			g.setColor(color);
			/*
			 * if (null != degree) { //设置水印旋转 g.rotate(Math.toRadians(degree),(double)
			 * bi.getWidth() / 2, (double) bi.getHeight() / 2); }
			 */
			g.setFont(font);
			int x = width/2;
			int y =height/15;
			// 水印位置
			g.drawString(word, x, y);
			g.dispose();
			// 输出图片
			File sf = new File(output);
			ImageIO.write(bi, "jpg", sf); // 保存图片

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
		return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
	}

	public static void main(String[] args) throws Exception {
		System.out.println(2.00 - 1.10);
		// ImageTool.drawImage("D:/document/mediaeep/广东省公安厅/高碧峰440111681202009/1-1-0001.jpg",
		// 1);
		// ImageTool.drawImage("D:/document/mediasource/广东省公安厅/高碧峰440111681202009/0FA784D667021F2C180D5AE45BF24983/10-1-001.jpg",
		// 1);
		Font font = new Font("微软雅黑", Font.PLAIN, 35); // 水印字体
		String srcImgPath = "D:/333.jpg"; // 源图片地址
		String tarImgPath = "E:/333.jpg"; // 待存储的地址
		String waterMarkContent = "范凯文 13631265519"; // 水印内容

		ImageTool.addWaterMark(srcImgPath, tarImgPath, waterMarkContent);
	}

}
