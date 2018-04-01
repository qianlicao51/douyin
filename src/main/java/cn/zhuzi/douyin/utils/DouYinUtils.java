package cn.zhuzi.douyin.utils;

import cn.zhuzi.douyin.DouYinConStant;

public class DouYinUtils {

	public static void sleepForColl(Integer sleepTime) {
		try {
			Thread.sleep(sleepTime);
			System.out.println();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void printMsg(String msg) {
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(DouYinConStant.PRINT_SYMBOL).append(msg).append(DouYinConStant.PRINT_SYMBOL);
		System.out.println(sbBuffer.toString());
		System.out.println();
		sbBuffer = null;
	}
}
