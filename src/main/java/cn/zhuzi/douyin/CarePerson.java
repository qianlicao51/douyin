package cn.zhuzi.douyin;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import cn.zhuzi.douyin.bean.FollPerson;
import cn.zhuzi.douyin.bean.MyCareBean;
import cn.zhuzi.douyin.bean.PserWork;
import cn.zhuzi.douyin.bean.WorkList;

import com.alibaba.fastjson.JSON;

/**
 * 获取主页里面的作品列表
 * 
 * @author grq
 *
 */
public class CarePerson {
	/**
	 * 个人主页。忘记怎么找到了了 https://www.douyin.com/share/user/58900737309
	 */

	public static String perHost = "https://www.douyin.com/aweme/v1/aweme/post/?user_id=";
	public static String fexHost = "&count=21&max_cursor=";
	public static String max_cursor = "&aid=1128";

	public MyCareBean myCareBean;

	public String getPerHost() {
		return perHost;
	}

	public void setPerHost(String perHost) {
		this.perHost = perHost;
	}

	public MyCareBean getMyCareBean() {
		return myCareBean;
	}

	public void setMyCareBean(MyCareBean myCareBean) {
		this.myCareBean = myCareBean;
	}

	public static String getBaseUrlForPer() {
		return baseUrlForPer;
	}

	public static void setBaseUrlForPer(String baseUrlForPer) {
		CarePerson.baseUrlForPer = baseUrlForPer;
	}

	/**
	 * 个人主页基本路径
	 * https://www.douyin.com/aweme/v1/aweme/post/?user_id=58900737309&count
	 * =21&max_cursor=0&aid=1128
	 */
	public static String baseUrlForPer = "https://www.douyin.com/share/user/";

	/**
	 * 获取每个关注者的 作品列表(包括超过20个之后的)
	 * 
	 * @return
	 */
	public List<FollPerson> getPserWork() {
		FollPerson[] followings = myCareBean.getFollowings();
		List<FollPerson> asList = Arrays.asList(followings);
		
		//需要滚动的位移
		String max_cursor=DouYinConStant.MAX_CURSOR;
		for (FollPerson follPer : asList) {
			try {
				String url = CarePerson.perHost + follPer.getUid() + CarePerson.fexHost + max_cursor + CarePerson.max_cursor;
				Connection connect = Jsoup.connect(url);
				Document document;

				document = connect.ignoreContentType(true).get();

				String html = document.body().html();
				PserWork parsework = JSON.parseObject(html, PserWork.class);
				List<WorkList> aweme_list = parsework.getAweme_list();
				
				///////判断是否还有更多
				boolean has_more =DouYinConStant.HAS_MORE.equals(parsework.getHas_more())?true:false;
				
				/////////////////////////////////////////////////
				//主页超过 20 个视频的还需要处理
				max_cursor=parsework.getMax_cursor();
				while (has_more) {
					String url1 = CarePerson.perHost + follPer.getUid() + CarePerson.fexHost + max_cursor + CarePerson.max_cursor;
					String html2 = Jsoup.connect(url1).ignoreContentType(true).get().body().html();
					PserWork parsework_more = JSON.parseObject(html2, PserWork.class);
					aweme_list.addAll(parsework_more.getAweme_list());
					
					//判断是否停止 和下次滚动位移
					has_more=DouYinConStant.HAS_MORE.equals(parsework_more.getHas_more())?true:false;
					max_cursor=parsework_more.getMax_cursor();
				} 
				
				
				//主页超过 20 个视频的处理结束
				/////////////////////////////////////////////////

				follPer.setShDetails(aweme_list);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		System.out.println("关注的 抖音主 的信息如下");
		System.out.println(JSON.toJSONString(asList));
		return asList;
	}

	public static void main(String[] args) {
		String mycatString = "[{'nickname':'一珺、','uid':'52616983119'},{'nickname':'Imperia_小然然','uid':'61141281259'},{'nickname':'光哥','uid':'58900737309'}]";
		List<FollPerson> parseArray = JSON.parseArray(mycatString, FollPerson.class);
		// 个人主页
		String url = CarePerson.perHost + parseArray.get(0).getUid() + CarePerson.fexHost;
		Connection connect = Jsoup.connect(url);
		Document document;
		try {
			document = connect.ignoreContentType(true).get();
			String html = document.body().html();
			PserWork parsework = JSON.parseObject(html, PserWork.class);
			List<WorkList> aweme_list = parsework.getAweme_list();
			for (WorkList workList : aweme_list) {
				System.out.println(JSON.toJSON(workList));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 测试主页超过20个的情况
	 * @throws IOException
	 */
	@Test
	public void test() throws IOException {
		String url = CarePerson.perHost + "52616983119" + CarePerson.fexHost + "0" + CarePerson.max_cursor;
		Connection connect = Jsoup.connect(url);
		Document document;

		document = connect.ignoreContentType(true).get();

		String html = document.body().html();
		PserWork parsework = JSON.parseObject(html, PserWork.class);
		List<WorkList> aweme_list = parsework.getAweme_list();
		System.out.println(aweme_list.size());

		if ("1".equals(parsework.getHas_more())) {
			Document document2 = Jsoup.connect(CarePerson.perHost + "52616983119" + CarePerson.fexHost + parsework.getMax_cursor() + CarePerson.max_cursor).ignoreContentType(true).get();

			PserWork pserWork = JSON.parseObject(document2.body().html(), PserWork.class);
			List<WorkList> aweme_list2 = pserWork.getAweme_list();
			aweme_list.addAll(aweme_list2);
		}
		System.out.println("________________");
		System.out.println(aweme_list.size());
	}
}
