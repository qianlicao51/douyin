package cn.zhuzi.douyin.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.junit.Test;

public class TestDown {

	/**
	 * 这个会发生重定向 导致下载不全。使用下面的
	 */
	@Test
	public void test() {
		String url = " https://aweme.snssdk.com/aweme/v1/playwm/?video_id=377850b22ae84bc9a7db612f449e27e9&line=0";
		Response document;
		try {
			document = Jsoup.connect(url).ignoreContentType(true).timeout(15000000).execute();

			BufferedInputStream stream = document.bodyStream();
			File downFile = new File("c://heheda.mp4");
			FileUtils.copyInputStreamToFile(stream, downFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testFR() {
		String url = " https://aweme.snssdk.com/aweme/v1/playwm/?video_id=377850b22ae84bc9a7db612f449e27e9&line=0";
		try {
			Response response = Jsoup.connect(url).ignoreContentType(true).followRedirects(true).execute();
			URL url2 = response.url();
			System.out.println(url2);
			File downFile = new File("c://heheda.mp4");
			FileUtils.copyURLToFile(url2, downFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
