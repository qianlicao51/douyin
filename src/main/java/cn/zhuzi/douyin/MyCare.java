package cn.zhuzi.douyin;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import cn.zhuzi.douyin.bean.FollPerson;
import cn.zhuzi.douyin.bean.MyCareBean;

import com.alibaba.fastjson.JSON;

/**
 * 关注列表的获取
 * 
 * @author grq
 *
 */
public class MyCare {
	/**
	 * 我关注 这个地址是动态变化的额，此时采集一次 就已文本的形式保存 关注人信息
	 */
	static String care_url = "https://api.amemv.com/aweme/v1/user/following/list/?user_id=93267622308&max_time=1522542279&count=20&retry_type=no_retry&iid=29648784234&device_id=41459906457&ac=wifi&channel=meizu&aid=1128&app_name=aweme&version_code=179&version_name=1.7.9&device_platform=android&ssmix=a&device_type=m1+metal&device_brand=Meizu&language=zh&os_api=22&os_version=5.1&uuid=869014028487941&openudid=a60b54dc77755f2f&manifest_version_code=179&resolution=1080*1920&dpi=480&update_version_code=1792&_rticket=1522542279863&ts=1522542279&as=a165723c573c4a96706506&cp=2dc7a95e7205cf62e1btnv&mas=006ce86afb4332bfcd460be343eb756f146c0cac6c0cec6c9c862c";

	public static void getMyCare() {
		try {

			// 缺少 ignoreContentType(true) 会报错
			// UnsupportedMimeTypeException: Unhandled content type. Must be
			// text/*, application/xml,
			Document document = Jsoup.connect(care_url).ignoreContentType(true).get();
			String careStr = document.body().html();
			System.out.println(careStr);
			MyCareBean myCare = JSON.parseObject(careStr, MyCareBean.class);
			FollPerson[] followings = myCare.getFollowings();
			System.out.println(JSON.toJSONString(followings));
			for (FollPerson follPerson : followings) {
				System.out.println(follPerson.getNickname());
			}
			 
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
		getMyCare();
	}

}
