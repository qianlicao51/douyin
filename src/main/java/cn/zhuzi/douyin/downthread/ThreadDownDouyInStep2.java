package cn.zhuzi.douyin.downthread;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import cn.zhuzi.douyin.DouYinConStant;
import cn.zhuzi.douyin.bean.Mp4Parent;
import cn.zhuzi.douyin.utils.DouYinUtils;

import com.alibaba.fastjson.JSON;

/**
 * 下载第二步
 * 
 * @author grq
 *
 */
public class ThreadDownDouyInStep2 extends Thread {
	File save;
	String url;

	public ThreadDownDouyInStep2(File save, String share_url) {
		this.save = save;
		this.url = share_url;
	}

	@Override
	public void run() {
		try {

			File downFile = new File(save, url.substring(url.lastIndexOf("/")) + DouYinConStant.EXTENSION_NAME);
			if (!downFile.exists()) {
				Connection connect = Jsoup.connect(url);
				Document pageContext = connect.followRedirects(true).get();

				/** 从 取到的页面解析 视频地址 **/
				Elements elementsByTag = pageContext.getElementsByTag("script");
				String eleStr = elementsByTag.get(elementsByTag.size() - 1).toString();
				String mp4urlText = eleStr.subSequence(eleStr.indexOf("[{") + 1, eleStr.lastIndexOf("}]") + 1).toString();
				Mp4Parent mp4Parent = JSON.parseObject(mp4urlText, Mp4Parent.class);
				String string = mp4Parent.getVideo().getPlay_addr().getUrl_list().get(0);
				/**
				 * 下载视频
				 */

				DouYinUtils.sleepForColl(30000);
				Response document = Jsoup.connect(string).ignoreContentType(true).timeout(15000000).execute();
				BufferedInputStream stream = document.bodyStream();
				FileUtils.copyInputStreamToFile(stream, downFile);
				DouYinUtils.printMsg("下载完成的是 》" + url + "。地址是： " + string);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
