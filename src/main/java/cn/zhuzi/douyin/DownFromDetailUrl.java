package cn.zhuzi.douyin;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import cn.zhuzi.douyin.bean.FollPerson;
import cn.zhuzi.douyin.bean.Mp4Parent;
import cn.zhuzi.douyin.downthread.ThreadDownDouyInStep1;
import cn.zhuzi.douyin.utils.DouYinUtils;

import com.alibaba.fastjson.JSON;

public class DownFromDetailUrl {
	
	/**
	 * 下载视频
	 * @param list
	 */
	public static void downByFollList(List<FollPerson> list){
		for (FollPerson follPerson : list) {
			DouYinUtils.sleepForColl(5000);
			DouYinUtils.printMsg(follPerson.getNickname()+"开始下载,共有视频"+follPerson.getShDetails().size());
			new ThreadDownDouyInStep1(follPerson).start();
		}
	 
	}

	public static void main(String[] args) {
		try {
			String url = "https://www.douyin.com/share/video/6536877257548369155";
			Connection connect = Jsoup.connect(url);
			Document pageContext = connect.followRedirects(true).get();
			Elements elementsByTag = pageContext.getElementsByTag("script");
			String eleStr = elementsByTag.get(elementsByTag.size() - 1).toString();
			System.out.println(eleStr.indexOf("[{"));
			System.out.println(eleStr.lastIndexOf("}]"));
			String mp4urlText = eleStr.subSequence(eleStr.indexOf("[{") + 1, eleStr.lastIndexOf("}]") + 1).toString();

			Mp4Parent mp4Parent = JSON.parseObject(mp4urlText, Mp4Parent.class);
			String string = mp4Parent.getVideo().getPlay_addr().getUrl_list().get(0);
			System.out.println(string);
			// 得到url
			// https://aweme.snssdk.com/aweme/v1/playwm/?video_id=380e29ed5af54d22896d933c81980c31&line=0

			/**
			 * 下载视频
			 */
			Response document = Jsoup.connect(string).ignoreContentType(true).timeout(8000).execute();
			BufferedInputStream stream = document.bodyStream();
			FileUtils.copyInputStreamToFile(stream, new File("c://heheda.mp4"));
			

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
