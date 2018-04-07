package cn.zhuzi.douyin;

import java.io.IOException;

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
	 * https://api.amemv.com/aweme/v1/user/following/list/?user_id=93267622308&max_time=1523073786&count=20&retry_type=no_retry&iid=29648784234&device_id=41459906457&ac=wifi&channel=meizu&aid=1128&app_name=aweme&version_code=179&version_name=1.7.9&device_platform=android&ssmix=a&device_type=m1+metal&device_brand=Meizu&language=zh&os_api=22&os_version=5.1&uuid=869014028487941&openudid=a60b54dc77755f2f&manifest_version_code=179&resolution=1080*1920&dpi=480&update_version_code=1792&_rticket=1523073786880&ts=1523073786&as=a1c5648caa3f5a52984649&cp=46f2a856a38cc92ae1vnid&mas=00ce2709ef73b8028c89313a35171afca49c2c6c2c1c9c4cac86ac
	 */
	static String care_url = "https://api.amemv.com/aweme/v1/user/following/list/?user_id=93267622308&max_time=1522542279&count=20&retry_type=no_retry&iid=29648784234&device_id=41459906457&ac=wifi&channel=meizu&aid=1128&app_name=aweme&version_code=179&version_name=1.7.9&device_platform=android&ssmix=a&device_type=m1+metal&device_brand=Meizu&language=zh&os_api=22&os_version=5.1&uuid=869014028487941&openudid=a60b54dc77755f2f&manifest_version_code=179&resolution=1080*1920&dpi=480&update_version_code=1792&_rticket=1522542279863&ts=1522542279&as=a165723c573c4a96706506&cp=2dc7a95e7205cf62e1btnv&mas=006ce86afb4332bfcd460be343eb756f146c0cac6c0cec6c9c862c";
	static String care_url_second = "https://api.amemv.com/aweme/v1/user/following/list/?user_id=93267622308&max_time=1522593790&count=20&retry_type=no_retry&iid=29648784234&device_id=41459906457&ac=wifi&channel=meizu&aid=1128&app_name=aweme&version_code=179&version_name=1.7.9&device_platform=android&ssmix=a&device_type=m1+metal&device_brand=Meizu&language=zh&os_api=22&os_version=5.1&uuid=869014028487941&openudid=a60b54dc77755f2f&manifest_version_code=179&resolution=1080*1920&dpi=480&update_version_code=1792&_rticket=1522593790179&ts=1522593789&as=a1850e6cfdbfaa8fb04547&cp=eef2a75dde04c3ffe1mxzk&mas=00b4c57a289d0a018a3db1800f4a9a97daec2cac2c0c46661c8686";

	public static void getMyCare() {
		try {

			// 缺少 ignoreContentType(true) 会报错
			// UnsupportedMimeTypeException: Unhandled content type. Must be
			// text/*, application/xml,
			care_url_second = "https://api.amemv.com/aweme/v1/user/following/list/?user_id=93267622308&max_time=1523074883&count=20&retry_type=no_retry&iid=29648784234&device_id=41459906457&ac=wifi&channel=meizu&aid=1128&app_name=aweme&version_code=179&version_name=1.7.9&device_platform=android&ssmix=a&device_type=m1+metal&device_brand=Meizu&language=zh&os_api=22&os_version=5.1&uuid=869014028487941&openudid=a60b54dc77755f2f&manifest_version_code=179&resolution=1080*1920&dpi=480&update_version_code=1792&_rticket=1523074883340&ts=1523074883&as=a115044ce3c4dad7c82281&cp=444aa6533781cd73e1pmfv&mas=00dc040f6b725b9e8a4dc21f178f87e4e08c1c4c4c1cc6ec268626";
			Document document = Jsoup.connect(care_url_second).ignoreContentType(true).get();
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
